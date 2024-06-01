package es.micro.app.controller;


import es.micro.app.entity.CentroEntity;
import es.micro.app.error.CenterException;
import es.micro.app.service.CentroService;
import es.swagger.codegen.api.CentrosApi;
import es.swagger.codegen.models.Centro;
import es.swagger.codegen.models.CentrosResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class CentroController implements CentrosApi {

    @Autowired
    private CentroService centroService;

    @Override
    public ResponseEntity<Void> deleteCentroId(String id) throws CenterException {
        CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
        if (centroEntity == null) {
            log.error("Center with id {} not found", id);
            throw CenterException.NO_CENTER_FOUND_EXCEPTION;
        }
        log.info("Center with id {} found, proceeding with deletion", id);
        centroService.deleteCentroId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Centro> getCentroId(String id) throws CenterException {
        CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
        if (centroEntity == null) {
            log.error("Center with id {} not found", id);
            throw CenterException.NO_CENTER_FOUND_EXCEPTION;
        }
        Centro centro = new Centro();
        centro.setNumCentro(centroEntity.getNumCentro());
        centro.setNombreCentro(centroEntity.getNombreCentro());
        return ResponseEntity.ok(centro);
    }

    @Override
    public ResponseEntity<CentrosResponse> getCentros() throws CenterException {
        // Obtener la lista de todos los centros
        List<CentroEntity> centroEntities = centroService.getAllCenters();

        // Verificar si la lista de centros está vacía
        if (centroEntities.isEmpty()) {
            log.error("No centers found");
            throw CenterException.NO_CENTERS_FOUND_EXCEPTION;
        }

        // Crear la respuesta con los centros obtenidos
        CentrosResponse response = new CentrosResponse();
        for (CentroEntity centroEntity : centroEntities) {
            Centro centro = new Centro();
            centro.setNumCentro(centroEntity.getNumCentro());
            centro.setNombreCentro(centroEntity.getNombreCentro());
            response.addCentrosItem(centro);
        }

        // Devolver la respuesta con los centros
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> postCentro(Centro body) throws CenterException {
        try {
            // Verificar si el cuerpo de la solicitud es nulo
            if (body == null) {
                log.error("Null body provided for postCentro request");
                throw CenterException.NULL_BODY_EXCEPTION;
            }

            // Verificar si el número de centro es nulo o menor que cero
            if (body.getNumCentro() == null || body.getNumCentro() < 0) {
                log.error("Invalid center number provided: {}", body.getNumCentro());
                throw CenterException.INVALID_CENTER_NUMBER_EXCEPTION;
            }

            // Verificar si el nombre del centro es nulo o vacío
            if (body.getNombreCentro() == null || body.getNombreCentro().isEmpty()) {
                log.error("Invalid center name provided");
                throw CenterException.INVALID_CENTER_NAME_EXCEPTION;
            }

            // Crear y guardar el centro
            CentroEntity centroEntity = new CentroEntity();
            centroEntity.setNumCentro(body.getNumCentro());
            centroEntity.setNombreCentro(body.getNombreCentro());
            centroService.saveCentro(centroEntity);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Error creating center: {}", e.getMessage());
            throw new CenterException("Error creating center");
        }
    }


    @Override
    public ResponseEntity<Void> putCentroId(String id, Centro body) throws CenterException {
        try {
            // Verificar si el ID es un número válido
            if (!isValidId(id)) {
                log.error("Invalid ID provided: {}", id);
                throw CenterException.INVALID_ID_EXCEPTION;
            }
            // Verificar si el centro existe
            CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
            if (centroEntity == null) {
                log.error("Center with ID {} not found", id);
                throw CenterException.NO_CENTER_FOUND_EXCEPTION;
            }
            // Actualizar el centro
            centroEntity.setNumCentro(Integer.parseInt(id));
            centroEntity.setNombreCentro(body.getNombreCentro());
            centroService.updateCentro(centroEntity);

            return ResponseEntity.ok().build();
        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", id);
            throw CenterException.INVALID_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating center with ID {}: {}", id, e.getMessage());
            throw new CenterException("Error updating center");
        }
    }

    // Método para verificar si el ID es un número válido
    private boolean isValidId(String id) {
        try {
            Integer.parseInt(id);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


}
