package es.micro.app.error;

public class CentroException extends RuntimeException {

    public static final CentroException NO_CENTER_FOUND_EXCEPTION = new CentroException("No center found with the specified ID");
    public static final CentroException INVALID_ID_EXCEPTION = new CentroException("Invalid ID");
    public static final CentroException NULL_BODY_EXCEPTION = new CentroException("Null body provided");
    public static final CentroException INVALID_CENTER_NUMBER_EXCEPTION = new CentroException("Invalid center number");
    public static final CentroException INVALID_CENTER_NAME_EXCEPTION = new CentroException("Invalid center name");
    public static final CentroException NO_CENTERS_FOUND_EXCEPTION = new CentroException("No centers found");

    public CentroException(String message) {
        super(message);
    }
}
