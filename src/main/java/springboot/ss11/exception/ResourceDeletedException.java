package springboot.ss11.exception;

public class ResourceDeletedException extends RuntimeException {
    public ResourceDeletedException(String message) {
        super(message);
    }
}
