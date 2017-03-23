package be.ae.services.exceptions;

public class UnknownResourceException extends ServiceException {
    public UnknownResourceException() {
        super(ErrorCode.UNKNOWN_RESOURCE);
    }
}
