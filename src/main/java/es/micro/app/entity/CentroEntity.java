package es.micro.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Validated
@Entity
@Table(name = "CENTROS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CentroEntity {
    @Id
    @Column(name = "NUM_CENTRO")
    private Integer numCentro;
    @Column(name = "NOMBRE_CENTRO")
    private String nombreCentro;

}
