package es.micro.app.service;


import es.micro.app.entity.CentroEntity;
import es.micro.app.repository.CentroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CentroService {

    private final CentroRepository centroRepository;

    public CentroEntity getCenterByIdCenter(Integer id) {
        return centroRepository.findById(id).orElse(null);
    }

    public List<CentroEntity> getAllCenters() {
        return centroRepository.findAll();
    }

    public void deleteCentroId(String id) {
        centroRepository.deleteById(Integer.parseInt(id));
    }

    public void saveCentro(CentroEntity centroEntity) {
        centroRepository.save(centroEntity);
    }

    public void updateCentro(CentroEntity centroEntity) {
        centroRepository.save(centroEntity);
    }
}
