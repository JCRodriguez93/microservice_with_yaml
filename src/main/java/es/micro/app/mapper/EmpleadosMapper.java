package es.micro.app.mapper;
import es.micro.app.entity.EmpleadoEntity;
import es.swagger.codegen.models.Empleado;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;




@Mapper(componentModel = "spring")
public interface EmpleadosMapper {

    EmpleadosMapper INSTANCE = Mappers.getMapper(EmpleadosMapper.class);

    @Mapping(source = "idEmpleado", target = "idEmpleado")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "idCentro", target = "idCentro")
    @Mapping(source = "centro.nombreCentro", target = "centro")
    Empleado toApiDomain(EmpleadoEntity source);

    List<Empleado> toApiDomain(List<EmpleadoEntity> source);


    @Mapping(source = "idEmpleado", target = "idEmpleado")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "idCentro", target = "idCentro")
    @Mapping(target = "centro", ignore=true)
    EmpleadoEntity toEntity(Empleado source);

}