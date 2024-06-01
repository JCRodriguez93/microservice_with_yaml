package es.micro.app.controller;

import es.micro.app.service.EmpleadoService;
import es.swagger.codegen.models.Empleado;
import es.swagger.codegen.models.EmpleadosResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class EmpleadoControllerMockTest {

    @Mock
    private EmpleadoService empleadoService;

    @InjectMocks
    private EmpleadoController empleadoController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteEmpleadoId_Success() {
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1);
        //simula que el servicio devuelve un empleado cuando se le proporciona un ID.
        when(empleadoService.getEmpleadoById(anyString())).thenReturn(empleado);
        ResponseEntity<Void> response = empleadoController.deleteEmpleadoId("1");
        // se verifica que el estado de la respuesta
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
       //verifica que el método deleteEmpleadoId del servicio fue llamado exactamente una vez.
        verify(empleadoService, times(1)).deleteEmpleadoId(anyInt());
    }
    @Test
    void deleteEmpleadoId_NotFound() {
        //simula que el servicio devuelve null cuando se le proporciona un ID.
        when(empleadoService.getEmpleadoById(anyString())).thenReturn(null);

        ResponseEntity<Void> response = empleadoController.deleteEmpleadoId("1");
        //verifica que el estado de la respuesta es
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        //verifica que el método deleteEmpleadoId del servicio nunca fue llamado
        verify(empleadoService, never()).deleteEmpleadoId(anyInt());
    }

    @Test
    void deleteEmpleadoId_InternalServerError() {
        // simula que el servicio lanza una excepción cuando se le proporciona un ID
        when(empleadoService.getEmpleadoById(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Void> response = empleadoController.deleteEmpleadoId("1");
        // verifica que el estado de la respuesta es HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // verifica que el método deleteEmpleadoId del servicio nunca fue llamado debido a la excepción lanzada.
        verify(empleadoService, never()).deleteEmpleadoId(anyInt());
    }


    @Test
    void getEmpleadoId_NotFound() {
        // Simula que el servicio devuelve null cuando se le proporciona un ID.
        when(empleadoService.getEmpleadoById(anyString())).thenReturn(null);

        ResponseEntity<Empleado> response = empleadoController.getEmpleadoId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.NOT_FOUND.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getEmpleadoId_Success() {
        Empleado empleado = new Empleado();
        // Simula que el servicio devuelve un empleado cuando se le proporciona un ID.
        when(empleadoService.getEmpleadoById(anyString())).thenReturn(empleado);

        ResponseEntity<Empleado> response = empleadoController.getEmpleadoId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta sea el empleado esperado.
        assertEquals(empleado, response.getBody());
    }

    @Test
    void getEmpleadoId_InternalServerError() {
        // Simula que el servicio lanza una excepción.
        when(empleadoService.getEmpleadoById(anyString())).thenThrow(new RuntimeException("Error"));

        ResponseEntity<Empleado> response = empleadoController.getEmpleadoId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getEmpleados_NoContent() {
        // Simula que el servicio devuelve una lista vacía de empleados.
        when(empleadoService.getEmpleados()).thenReturn(new ArrayList<>());

        ResponseEntity<EmpleadosResponse> response = empleadoController.getEmpleados();
        // Verifica que el estado de la respuesta sea HttpStatus.NO_CONTENT.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getEmpleados_Success() {
        List<Empleado> empleados = new ArrayList<>();
        empleados.add(new Empleado());
        // Simula que el servicio devuelve una lista con empleados.
        when(empleadoService.getEmpleados()).thenReturn(empleados);

        ResponseEntity<EmpleadosResponse> response = empleadoController.getEmpleados();
        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el cuerpo de la respuesta contenga la lista de empleados.
        assertEquals(empleados, response.getBody().getEmpleados());
    }

    @Test
    void getEmpleados_InternalServerError() {
        // Simula que el servicio lanza una excepción.
        when(empleadoService.getEmpleados()).thenThrow(new RuntimeException("Error"));

        ResponseEntity<EmpleadosResponse> response = empleadoController.getEmpleados();
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void postEmpleado_Success() {
        Empleado empleado = new Empleado();
       // Simula la creacción de un empleado.
        when(empleadoService.postEmpleado(any(Empleado.class))).thenReturn(HttpStatus.CREATED);

        ResponseEntity<Void> response = empleadoController.postEmpleado(empleado);
        // Verifica que el estado de la respuesta sea HttpStatus.CREATED
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        //verifica que el método postEmpleado del servicio fue llamado exactamente una vez.
        verify(empleadoService, times(1)).postEmpleado(any(Empleado.class));
    }

    @Test
    void postEmpleado_InternalServerError() {
        Empleado empleado = new Empleado();
        // Simula una excepción lanzada por el servicio al intentar crear un empleado.
        when(empleadoService.postEmpleado(any(Empleado.class))).thenThrow(new RuntimeException("Error al crear empleado"));

        ResponseEntity<Void> response = empleadoController.postEmpleado(empleado);

        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // Verifica que el método postEmpleado del servicio fue llamado exactamente una vez.
        verify(empleadoService, times(1)).postEmpleado(any(Empleado.class));
    }

    @Test
    void putEmpleadoId_Success() {
        // Simula que el servicio actualiza con éxito un empleado y devuelve HttpStatus.OK.
        when(empleadoService.putEmpleadoId(anyInt(), any(Empleado.class))).thenReturn(HttpStatus.OK);

        // Llama al método para actualizar un empleado.
        ResponseEntity<Void> response = empleadoController.putEmpleadoId("1", new Empleado());

        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void putEmpleadoId_InternalServerError() {
        // Simula una excepción lanzada por el servicio al intentar actualizar un empleado.
        when(empleadoService.putEmpleadoId(anyInt(), any(Empleado.class))).thenThrow(new RuntimeException("Error"));

        // Llama al método para actualizar un empleado.
        ResponseEntity<Void> response = empleadoController.putEmpleadoId("1", new Empleado());

        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void putEmpleadoId_NotFound() {
        // Simula que el servicio devuelve HttpStatus.NOT_FOUND al intentar actualizar un empleado que no existe.
        when(empleadoService.putEmpleadoId(anyInt(), any(Empleado.class))).thenReturn(HttpStatus.NOT_FOUND);

        // Llama al método para actualizar un empleado.
        ResponseEntity<Void> response = empleadoController.putEmpleadoId("1", new Empleado());

        // Verifica que el estado de la respuesta sea HttpStatus.NOT_FOUND.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}