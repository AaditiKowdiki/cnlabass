import java.util.*;

class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter an IP address = ");
        String ip = input.nextLine();

        System.out.println("Defination of Subnetting\nSubnetting is the logical process of " +
                           "splitting a single large network into smaller, distinct parts called subnets which helps in " +
                           "managing the networks and is easier to run");
        System.out.println();

        String[] nums = ip.split("\\.");
        int firstNum = Integer.parseInt(nums[0]);

        if (firstNum >= 1 && firstNum <= 126) {
            classA();
        } else if (firstNum == 127) {
            System.out.println("Class: Loopback Address");
            System.out.println("A loopback address is a special, virtual IP address that a " +
                               "computer uses to send network traffic to itself.");
        } else if (firstNum >= 128 && firstNum <= 191) {
            classB();
        } else if (firstNum >= 192 && firstNum <= 223) {
            classC();
        } else if (firstNum >= 224 && firstNum <= 239) {
            classD();
        } else if (firstNum >= 240 && firstNum <= 255) {
            classE();
        } else {
            System.out.println("Invalid IP Address range (Must be 1-255).");
        }

        input.close();
    }

    public static void classA() {
        System.out.println("Class A IP address");
        System.out.println("Number of bits: 24");
        System.out.println("First octet decimal range: 0-127");
        System.out.println("First octet binary range: 0XXXXXXX");
        System.out.println("IP range: 0.0.0.0 - 127.255.255.255");
        System.out.println("Subnet mask: 255.0.0.0");
        System.out.println("Hosts per network ID: 2^24 - 2");
        System.out.println("The -2 reason: One is network ID and the other one is broadcast ID");
        System.out.println("Number of networks: 2^7");
    }

    public static void classB() {
        System.out.println("Class B IP address");
        System.out.println("Number of bits: 16");
        System.out.println("First octet decimal range: 128-191");
        System.out.println("First octet binary range: 10XXXXXX");
        System.out.println("IP range: 128.0.0.0 - 191.255.255.255");
        System.out.println("Subnet mask: 255.255.0.0");
        System.out.println("Hosts per network ID: 2^16 - 2");
        System.out.println("The -2 reason: One is network ID and the other one is broadcast ID");
        System.out.println("Number of networks: 2^14");
    }

    public static void classC() {
        System.out.println("Class C IP address");
        System.out.println("Number of bits: 8");
        System.out.println("First octet decimal range: 192-223");
        System.out.println("First octet binary range: 110XXXXX");
        System.out.println("IP range: 192.0.0.0 - 223.255.255.255");
        System.out.println("Subnet mask: 255.255.255.0");
        System.out.println("Hosts per network ID: 2^8 - 2");
        System.out.println("The -2 reason: One is network ID and the other one is broadcast ID");
        System.out.println("Number of networks: 2^21");
    }

    public static void classD() {
        System.out.println("Class D IP address");
        System.out.println("First octet decimal range: 224-239");
        System.out.println("First octet binary range: 1110XXXX");
        System.out.println("IP range: 224.0.0.0 - 239.255.255.255");
    }

    public static void classE() {
        System.out.println("Class E IP address");
        System.out.println("First octet decimal range: 240-255");
        System.out.println("First octet binary range: 1111XXXX");
        System.out.println("IP range: 240.0.0.0 - 255.255.255.255");
    }
}
