
package Q2_DataStructures;

public class PacketSymmetryAuditor {
    public static void main(String[] args) {
        String payload = "RADAR";

        if (payload.isEmpty()) {
            System.out.println("Packet payload is empty.");
            return;
        }

        TransactionStack stack = new TransactionStack(payload.length());
        RequestQueue queue = new RequestQueue(payload.length());

        System.out.println("Packet Payload: " + payload);

        for (int i = 0; i < payload.length(); i++) {
            char c = payload.charAt(i);
            stack.push(c);
            queue.enqueue(c);
        }

        boolean isPalindrome = true;

        while (!stack.isEmpty() && !queue.isEmpty()) {
            char stackChar = stack.pop();
            char queueChar = queue.dequeue();

            if (stackChar != queueChar) {
                isPalindrome = false;
                break;
            }
        }

        System.out.println("Stack and Queue Comparison Completed.");

        if (isPalindrome) {
            System.out.println("Result: Packet payload is symmetric (Palindrome).");
        } else {
            System.out.println("Result: Packet payload is not symmetric (Not a Palindrome).");
        }

        System.out.println("Packet Symmetry Audit Completed.");
    }
}
