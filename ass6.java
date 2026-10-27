Server
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

int main() {
    int sockfd;
    char buffer[1024];
    struct sockaddr_in server_addr, client_addr;
    socklen_t len = sizeof(client_addr);

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0) {
        perror("Socket creation failed");
        return 1;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(12345);
    server_addr.sin_addr.s_addr = INADDR_ANY;

    if (bind(sockfd, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Bind failed");
        close(sockfd);
        return 1;
    }

    printf("UDP Server is running...\n");

    while (1) {
        memset(buffer, 0, sizeof(buffer));
        int n = recvfrom(sockfd, buffer, sizeof(buffer) - 1, 0, (struct sockaddr *)&client_addr, &len);
        if (n < 0) {
            perror("Receive failed");
            break;
        }

        buffer[n] = '\0';
        printf("Received: %s\n", buffer);

        if (strcmp(buffer, "exit") == 0) {
            char *msg = "Server disconnected.";
            sendto(sockfd, msg, strlen(msg), 0, (struct sockaddr *)&client_addr, len);
            break;
        }

        // Echoing or processing the string (converting/sending response)
        printf("Sent: %s\n", buffer);
        sendto(sockfd, buffer, strlen(buffer), 0, (struct sockaddr *)&client_addr, len);
    }

    close(sockfd);
    printf("Server socket closed.\n");
    return 0;
}

Client
#include <stdio.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>

int main() {
    int sockfd;
    char buffer[1024];
    struct sockaddr_in server_addr;
    socklen_t len = sizeof(server_addr);

    sockfd = socket(AF_INET, SOCK_DGRAM, 0);
    if (sockfd < 0) {
        perror("Socket creation failed");
        return 1;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(12345);
    server_addr.sin_addr.s_addr = inet_addr("127.0.0.1");

    while (1) {
        printf("Enter a string (exit to quit): ");
        if (fgets(buffer, sizeof(buffer), stdin) == NULL)
            break;

        buffer[strcspn(buffer, "\n")] = '\0';

        sendto(sockfd, buffer, strlen(buffer), 0, (struct sockaddr *)&server_addr, len);

        memset(buffer, 0, sizeof(buffer));
        int n = recvfrom(sockfd, buffer, sizeof(buffer) - 1, 0, NULL, NULL);
        if (n < 0) {
            perror("Receive failed");
            break;
        }

        buffer[n] = '\0';
        printf("Server response: %s\n", buffer);

        if (strcmp(buffer, "Server disconnected.") == 0)
            break;
    }

    close(sockfd);
    printf("Client socket closed.\n");
    return 0;
}
