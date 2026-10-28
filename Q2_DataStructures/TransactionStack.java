
package Q2_DataStructures;

public class TransactionStack {
    private final char[] stack;
    private int top;

    public TransactionStack(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }

        stack = new char[capacity];
        top = -1;
    }

    public void push(char c) {
        if (top == stack.length - 1) {
            throw new IllegalStateException("Stack is full.");
        }

        stack[++top] = c;
    }

    public char pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }

        return stack[top--];
    }

    public char peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }

        return stack[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }
}
