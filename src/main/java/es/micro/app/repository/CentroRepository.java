package es.micro.app.repository;

import es.micro.app.entity.CentroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CentroRepository extends JpaRepository<CentroEntity, Integer> {

}
