package es.micro.app.controller;


import es.micro.app.error.EmpleadoException;
import es.micro.app.service.EmpleadoService;
import es.swagger.codegen.api.EmpleadosApi;
import es.swagger.codegen.models.Empleado;
import es.swagger.codegen.models.EmpleadosResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@Slf4j
@RestController
public class EmpleadoController implements EmpleadosApi {


    @Autowired
    private EmpleadoService empleadoService;


    @Override
    public ResponseEntity<Void> deleteEmpleadoId(String id) throws EmpleadoException {
        if (!isValidId(id)) {
            log.error("Invalid employee ID format: {}", id);
            throw EmpleadoException.INVALID_ID_EXCEPTION;
        }

        Empleado deletedEmployee = empleadoService.getEmpleadoById(id);

        if (deletedEmployee == null) {
            log.error("Employee with id {} not found", id);
            throw EmpleadoException.NO_EMPLOYEE_FOUND_EXCEPTION;
        }

        try {
            empleadoService.deleteEmpleadoId(Integer.parseInt(id));
            log.info("Employee with id {} deleted successfully", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            log.error("Error deleting employee with id {}: {}", id, e.getMessage());
            throw new EmpleadoException("Error deleting employee with id " + id);
        }
    }




    @Override
    public ResponseEntity<Empleado> getEmpleadoId(String id) {
        try {
            if (!isValidId(id)) {
                log.error("Invalid employee ID format: {}", id);
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            Empleado empleado = empleadoService.getEmpleadoById(id);

            if (empleado == null) {
                log.info("Employee with id {} not found", id);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                log.info("Employee with id {} retrieved successfully", id);
                return new ResponseEntity<>(empleado, HttpStatus.OK);
            }
        } catch (Exception e) {
            log.error("Error retrieving employee with id {}: {}", id, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ResponseEntity<EmpleadosResponse> getEmpleados() {
        try {
            log.info("Fetching all employees");
            List<Empleado> empleados = empleadoService.getEmpleados();

            if (empleados.isEmpty()) {
                log.info("No employees found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            EmpleadosResponse response = new EmpleadosResponse();
            response.setEmpleados(empleados);
            log.info("Successfully fetched all employees");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error fetching all employees: {}", e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

@Override
public ResponseEntity<Void> postEmpleado(Empleado body) throws EmpleadoException {
    if (body == null) {
        log.error("Null body provided");
        throw new EmpleadoException("Null body provided");
    }

    if (body.getNombre() == null || body.getNombre().isEmpty()) {
        log.error("Invalid employee name");
        throw  EmpleadoException.NULL_EMPLOYEE_NAME_EXCEPTION;
    }

    empleadoService.postEmpleado(body);

    log.info("Employee created successfully: {}", body.getIdEmpleado());

    return new ResponseEntity<>(HttpStatus.CREATED);
}



    @Override
    public ResponseEntity<Void> putEmpleadoId(String id, Empleado body) throws EmpleadoException {
        try {
            if (body == null) {
                log.error("Null body provided");
                throw EmpleadoException.NULL_BODY_EXCEPTION;
            }
           if (body.getIdEmpleado() == null) {
                log.error("Employee ID is required for updating");
                throw EmpleadoException.MISSING_ID_EXCEPTION;
            }

            Empleado existingEmployee = empleadoService.getEmpleadoById(id);
            if (existingEmployee == null) {
                log.error("No employee found with ID {}", id);
                throw EmpleadoException.NO_EMPLOYEE_FOUND_EXCEPTION;
            }
            HttpStatus status = empleadoService.putEmpleadoId(Integer.parseInt(id), body);
            log.info("Employee with id {} updated successfully", id);
            return new ResponseEntity<>(status);

        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", id);
            throw EmpleadoException.INVALID_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating employee with id {}: {}", id, e.getMessage());
            throw new EmpleadoException("Error updating employee");
        }
    }



    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}

