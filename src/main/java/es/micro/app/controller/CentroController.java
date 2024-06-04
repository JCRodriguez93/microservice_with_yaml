package es.micro.app.controller;


import es.micro.app.entity.CentroEntity;
import es.micro.app.error.CentroException;
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
    public ResponseEntity<Void> deleteCentroId(String id) throws CentroException {
        CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
        if (centroEntity == null) {
            log.error("Center with id {} not found", id);
            throw CentroException.NO_CENTER_FOUND_EXCEPTION;
        }
        log.info("Center with id {} found, proceeding with deletion", id);
        centroService.deleteCentroId(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Centro> getCentroId(String id) throws CentroException {
        CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
        if (centroEntity == null) {
            log.error("Center with id {} not found", id);
            throw CentroException.NO_CENTER_FOUND_EXCEPTION;
        }
        Centro centro = new Centro();
        centro.setNumCentro(centroEntity.getNumCentro());
        centro.setNombreCentro(centroEntity.getNombreCentro());
        return ResponseEntity.ok(centro);
    }

    @Override
    public ResponseEntity<CentrosResponse> getCentros() throws CentroException {
        List<CentroEntity> centroEntities = centroService.getAllCenters();


        if (centroEntities.isEmpty()) {
            log.error("No centers found");
            throw CentroException.NO_CENTERS_FOUND_EXCEPTION;
        }


        CentrosResponse response = new CentrosResponse();
        for (CentroEntity centroEntity : centroEntities) {
            Centro centro = new Centro();
            centro.setNumCentro(centroEntity.getNumCentro());
            centro.setNombreCentro(centroEntity.getNombreCentro());
            response.addCentrosItem(centro);
        }


        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> postCentro(Centro body) throws CentroException {
        try {

            if (body == null) {
                log.error("Null body provided for postCentro request");
                throw CentroException.NULL_BODY_EXCEPTION;
            }


            if (body.getNumCentro() == null || body.getNumCentro() < 0) {
                log.error("Invalid center number provided: {}", body.getNumCentro());
                throw CentroException.INVALID_CENTER_NUMBER_EXCEPTION;
            }


            if (body.getNombreCentro() == null || body.getNombreCentro().isEmpty()) {
                log.error("Invalid center name provided");
                throw CentroException.INVALID_CENTER_NAME_EXCEPTION;
            }


            CentroEntity centroEntity = new CentroEntity();
            centroEntity.setNumCentro(body.getNumCentro());
            centroEntity.setNombreCentro(body.getNombreCentro());
            centroService.saveCentro(centroEntity);

            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            log.error("Error creating center: {}", e.getMessage());
            throw new CentroException("Error creating center");
        }
    }


    @Override
    public ResponseEntity<Void> putCentroId(String id, Centro body) throws CentroException {
        try {

            if (!isValidId(id)) {
                log.error("Invalid ID provided: {}", id);
                throw CentroException.INVALID_ID_EXCEPTION;
            }

            CentroEntity centroEntity = centroService.getCenterByIdCenter(Integer.parseInt(id));
            if (centroEntity == null) {
                log.error("Center with ID {} not found", id);
                throw CentroException.NO_CENTER_FOUND_EXCEPTION;
            }

            centroEntity.setNumCentro(Integer.parseInt(id));
            centroEntity.setNombreCentro(body.getNombreCentro());
            centroService.updateCentro(centroEntity);

            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            log.error("Invalid ID format: {}", id);
            throw CentroException.INVALID_ID_EXCEPTION;
        } catch (Exception e) {
            log.error("Error updating center with ID {}: {}", id, e.getMessage());
            throw new CentroException("Error updating center");
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
