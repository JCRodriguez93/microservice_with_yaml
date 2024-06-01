package es.micro.app.error;

public class EmpleadoException extends RuntimeException {

    public static final EmpleadoException NO_EMPLOYEE_FOUND_EXCEPTION = new EmpleadoException("No employee found with the specified ID");
    public static final EmpleadoException INVALID_ID_EXCEPTION = new EmpleadoException("Invalid employee ID");
    public static final EmpleadoException NULL_BODY_EXCEPTION = new EmpleadoException("Null body provided");
    public static final EmpleadoException NULL_EMPLOYEE_NAME_EXCEPTION = new EmpleadoException("Employee name is required for updating");
    public static final EmpleadoException MISSING_ID_EXCEPTION = new EmpleadoException("Employee ID is required for updating");


    public EmpleadoException(String message) {
        super(message);
    }
}
