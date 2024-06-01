package es.micro.app.repository;

import es.micro.app.entity.EmpleadoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpleadoRepository extends JpaRepository<EmpleadoEntity, Integer> {

    @Query("SELECT e FROM EmpleadoEntity e WHERE e.nombre LIKE CONCAT('%', :nombre, '%')")
    List<EmpleadoEntity> findByNombre(String nombre);

    @Query("SELECT e FROM EmpleadoEntity e WHERE e.idCentro = :idCentro")
    List<EmpleadoEntity> findByIdCentro(Integer idCentro);
}

