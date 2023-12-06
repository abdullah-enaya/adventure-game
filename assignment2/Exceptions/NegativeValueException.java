package Exceptions;

public class NegativeValueException extends RuntimeException {
    public NegativeValueException() {
    }

    @Override
    public String getMessage() {
        return "Value cannot be negative.";
    }
}
