import java.util.*;

class solution {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int[] binary = new int[4];
        
        System.out.println("Enter 4 bits binary code:");
        for (int i = 0; i < binary.length; i++) {
            System.out.print("Enter bit" + (i + 1) + ": ");
            int bit = input.nextInt();
            if (bit != 0 && bit != 1) {
                System.out.println("Invalid input: Code must only contain 0 and 1");
                return;
            }
            binary[i] = bit;
        }

        System.out.print("Your binary code is: ");
        for (int bit : binary) {
            System.out.print(bit);
        }
        System.out.println();

        int m = 4;
        int r = 0;

        while (true) {
            if (Math.pow(2, r) >= m + r + 1) {
                break;
            }
            r++;
        }

        System.out.println("Number of parity bits r: " + r);

        int totalBits = m + r;
        int[] finalBinary = new int[totalBits];

        int k = 0;
        for (int i = 0; i < finalBinary.length; i++) {
            if (k == r) {
                break;
            }
            int position = (int) Math.pow(2, k);
            finalBinary[finalBinary.length - position] = 0;
            k++;
        }

        int binaryIndex = 0;
        for (int i = finalBinary.length; i >= 1; i--) {
            int arrayIndex = finalBinary.length - i;
            if (i != 1 && i != 2 && i != 4) {
                finalBinary[arrayIndex] = binary[binaryIndex];
                binaryIndex++;
            }
        }

        System.out.print("Your binary code is with parity bits set to 0: ");
        for (int bit : finalBinary) {
            System.out.print(bit);
        }
        System.out.println();

        for (int parityIndex = 0; parityIndex < r; parityIndex++) {
            int parityPosition = (int) Math.pow(2, parityIndex);
            int parity = 0;
            for (int position = 1; position <= totalBits; position++) {
                if ((position & parityPosition) != 0) {
                    int arrayIndex = totalBits - position;
                    parity = parity ^ finalBinary[arrayIndex];
                }
            }
            finalBinary[totalBits - parityPosition] = parity;
        }

        System.out.print("Final signal: ");
        for (int bit : finalBinary) {
            System.out.print(bit);
        }
        System.out.println();
        System.out.println();

        System.out.print("Enter received codeword: ");
        String received = input.next();

        if (received.length() != totalBits) {
            System.out.println("Invalid codeword! Enter exactly " + totalBits + " bits.");
            return;
        }

        int[] receivedBinary = new int[totalBits];
        for (int i = 0; i < totalBits; i++) {
            char bit = received.charAt(i);
            if (bit != '0' && bit != '1') {
                System.out.println("Invalid input: Code must only contain 0 and 1");
                return;
            }
            receivedBinary[i] = bit - '0';
        }

        int errorPosition = 0;
        for (int parityIndex = 0; parityIndex < r; parityIndex++) {
            int parityPosition = (int) Math.pow(2, parityIndex);
            int parity = 0;
            for (int position = 1; position <= totalBits; position++) {
                if ((position & parityPosition) != 0) {
                    int arrayIndex = totalBits - position;
                    parity = parity ^ receivedBinary[arrayIndex];
                }
            }
            if (parity != 0) {
                errorPosition += parityPosition;
            }
        }

        System.out.println();
        System.out.println("-------------------- RECEIVER --------------------");

        if (errorPosition == 0) {
            System.out.println("No error detected.");
        } else {
            System.out.println("Error detected at bit position: " + errorPosition + " from the right");
            int errorArrayIndex = totalBits - errorPosition;
            receivedBinary[errorArrayIndex] = receivedBinary[errorArrayIndex] ^ 1;
        }

        System.out.print("Corrected Codeword: ");
        for (int bit : receivedBinary) {
            System.out.print(bit);
        }
        System.out.println();

        System.out.print("Recovered Data Bits: ");
        for (int position = totalBits; position >= 1; position--) {
            if (position != 1 && position != 2 && position != 4) {
                int arrayIndex = totalBits - position;
                System.out.print(receivedBinary[arrayIndex]);
            }
        }

        System.out.println();
        input.close();
    }
}
