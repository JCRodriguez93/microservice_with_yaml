package es.micro.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;


@Validated
@Entity
@Table(name = "EMPLEADOS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpleadoEntity {

    @Id
    @Column(name = "ID_EMPLEADO")
    private Integer idEmpleado;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "ID_CENTRO")
    private Integer idCentro;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name = "ID_CENTRO", referencedColumnName = "NUM_CENTRO", insertable = false, updatable = false)
    private CentroEntity centro;

}
