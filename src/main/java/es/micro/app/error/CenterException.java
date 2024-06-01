package es.micro.app.error;

public class CenterException extends RuntimeException {

    public static final CenterException NO_CENTER_FOUND_EXCEPTION = new CenterException("No center found with the specified ID");
    public static final CenterException INVALID_ID_EXCEPTION = new CenterException("Invalid ID");
    public static final CenterException NULL_BODY_EXCEPTION = new CenterException("Null body provided");
    public static final CenterException INVALID_CENTER_NUMBER_EXCEPTION = new CenterException("Invalid center number");
    public static final CenterException INVALID_CENTER_NAME_EXCEPTION = new CenterException("Invalid center name");
    public static final CenterException NO_CENTERS_FOUND_EXCEPTION = new CenterException("No centers found");

    public CenterException(String message) {
        super(message);
    }
}
