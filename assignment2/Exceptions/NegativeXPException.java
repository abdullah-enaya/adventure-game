package Exceptions;

public class NegativeXPException extends RuntimeException {
    public NegativeXPException() {
    }

    @Override
    public String getMessage() {
        return "XP cannot be negative.";
    }
}
