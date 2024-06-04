package es.micro.app.controller;


import es.micro.app.entity.CentroEntity;
import es.micro.app.service.CentroService;
import es.swagger.codegen.models.Centro;
import es.swagger.codegen.models.CentrosResponse;
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

class CentroControllerMockTest {

    @Mock
    private CentroService centroService;

    @InjectMocks
    private CentroController centroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deleteCentroId_Success() {
        CentroEntity centro = CentroEntity
                .builder()
                .nombreCentro("BBVA")
                .numCentro(200).build();
        // Simula que el servicio devuelve un centro cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenReturn(centro);
        // Llama al método del controlador para eliminar un centro con ID "200".
        ResponseEntity<Void> response = centroController.deleteCentroId("200");
        // Verifica que el estado de la respuesta sea HttpStatus.NO_CONTENT.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        // Verifica que el método deleteCentroId del servicio fue llamado exactamente una vez.
        verify(centroService, times(1)).deleteCentroId(anyString());
    }

    @Test
    void deleteCentroId_NotFound() {
        // Simula que el servicio devuelve null cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenReturn(null);
        // Llama al método del controlador para intentar eliminar un centro con ID "200".
        ResponseEntity<Void> response = centroController.deleteCentroId("200");
        // Verifica que el estado de la respuesta sea HttpStatus.NOT_FOUND.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        // Verifica que el método deleteCentroId del servicio nunca fue llamado.
        verify(centroService, never()).deleteCentroId(anyString());
    }

    @Test
    void deleteCentroId_InternalServerError() {
        // Simula que el servicio lanza una excepción cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenThrow(new RuntimeException("Error"));
        // Llama al método del controlador para intentar eliminar un centro con ID "1".
        ResponseEntity<Void> response = centroController.deleteCentroId("1");
        // Verifica que el estado de la respuesta es HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        // Verifica que el método deleteCentroId del servicio nunca fue llamado debido a la excepción lanzada.
        verify(centroService, never()).deleteCentroId(anyString());
    }

    @Test
    void getCentroId_NotFound() {
        // Simula que el servicio devuelve null cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenReturn(null);
        // Llama al método del controlador para intentar obtener un centro con ID "1".
        ResponseEntity<Centro> response = centroController.getCentroId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.NOT_FOUND.
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getCentroId_Success() {
        CentroEntity centroEntity = CentroEntity.builder()
                .numCentro(1)
                .nombreCentro("BBVA").build();
        // Simula que el servicio devuelve el centro cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenReturn(centroEntity);
        // Llama al método del controlador para obtener un centro con ID "1".
        ResponseEntity<Centro> response = centroController.getCentroId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el nombre del centro de la respuesta sea "BBVA".
        assertEquals("BBVA", response.getBody().getNombreCentro());
    }

    @Test
    void getCentroId_BadRequest() {
        // Llama al método del controlador para obtener un centro con un ID inválido ("invalid").
        ResponseEntity<Centro> response = centroController.getCentroId("invalid");
        // Verifica que el estado de la respuesta sea HttpStatus.BAD_REQUEST.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void getCentroId_InternalServerError() {
        // Simula que el servicio lanza una excepción cuando se le proporciona un ID.
        when(centroService.getCenterByIdCenter(anyInt())).thenThrow(new RuntimeException("Error"));
        // Llama al método del controlador para intentar obtener un centro con ID "1".
        ResponseEntity<Centro> response = centroController.getCentroId("1");
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void getCentros_NoContent() {
        // Simula que el servicio devuelve una lista vacía de centros.
        when(centroService.getAllCenters()).thenReturn(new ArrayList<>());
        // Llama al método del controlador para obtener todos los centros.
        ResponseEntity<CentrosResponse> response = centroController.getCentros();
        // Verifica que el estado de la respuesta sea HttpStatus.NO_CONTENT.
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void getCentros_Success() {
        List<CentroEntity> centroEntities = new ArrayList<>();
        CentroEntity centroEntity = new CentroEntity();
        centroEntity.setNumCentro(1);
        centroEntity.setNombreCentro("Centro Test");
        centroEntities.add(centroEntity);
        // Simula que el servicio devuelve una lista con un centro.
        when(centroService.getAllCenters()).thenReturn(centroEntities);
        // Llama al método del controlador para obtener todos los centros.
        ResponseEntity<CentrosResponse> response = centroController.getCentros();
        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que la lista de centros en la respuesta tiene un tamaño de 1.
        assertEquals(1, response.getBody().getCentros().size());
        // Verifica que el nombre del centro en la respuesta sea "Centro Test".
        assertEquals("Centro Test", response.getBody().getCentros().get(0).getNombreCentro());
    }

    @Test
    void getCentros_InternalServerError() {
        // Simula que el servicio lanza una excepción cuando intenta obtener todos los centros.
        when(centroService.getAllCenters()).thenThrow(new RuntimeException("Error"));
        // Llama al método del controlador para obtener todos los centros.
        ResponseEntity<CentrosResponse> response = centroController.getCentros();
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void postCentro_Success() {
        Centro centro = new Centro();
        // Simula que el servicio guarda el centro sin lanzar ninguna excepción.
        doNothing().when(centroService).saveCentro(any(CentroEntity.class));

        // Llama al método del controlador para guardar un nuevo centro.
        ResponseEntity<Void> response = centroController.postCentro(centro);

        // Verifica que el estado de la respuesta sea HttpStatus.CREATED.
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        // Verifica que el método saveCentro del servicio fue llamado exactamente una vez.
        verify(centroService, times(1)).saveCentro(any(CentroEntity.class));
    }



    @Test
    void postCentro_InternalServerError() {
        // Simula que el servicio lanza una excepción cuando intenta guardar un centro.
        doThrow(new RuntimeException("Error")).when(centroService).saveCentro(any(CentroEntity.class));
        Centro centro = new Centro();
        centro.setNumCentro(1);
        centro.setNombreCentro("Centro Test");
        // Llama al método del controlador para guardar un nuevo centro.
        ResponseEntity<Void> response = centroController.postCentro(centro);
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }


    // A PARTIR DE AQUÍ LA COSA TA CHUNGA
    @Test
    void putCentroId_Success() {

        // Simula que el servicio actualiza el centro sin lanzar ninguna excepción.
        doNothing().when(centroService).updateCentro(any(CentroEntity.class));
        Centro centro = new Centro();
        centro.setNombreCentro("Centro Test");
        // Llama al método del controlador para actualizar un centro con ID "1".
        ResponseEntity<Void> response = centroController.putCentroId("1", centro);
        // Verifica que el estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // Verifica que el método updateCentro del servicio fue llamado exactamente una vez.
        verify(centroService, times(1)).updateCentro(any(CentroEntity.class));
    }

    @Test
    void putCentroId_BadRequest() {
        Centro centro = new Centro();
        // Llama al método del controlador para actualizar un centro con un ID inválido ("invalid").
        ResponseEntity<Void> response = centroController.putCentroId("invalid", centro);
        // Verifica que el estado de la respuesta sea HttpStatus.BAD_REQUEST.
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void putCentroId_InternalServerError() {
        // Simula que el servicio lanza una excepción cuando intenta actualizar un centro.
        doThrow(new RuntimeException("Error")).when(centroService).updateCentro(any(CentroEntity.class));
        Centro centro = new Centro();
        // Llama al método del controlador para actualizar un centro con ID "1".
        ResponseEntity<Void> response = centroController.putCentroId("1", centro);
        // Verifica que el estado de la respuesta sea HttpStatus.INTERNAL_SERVER_ERROR.
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
