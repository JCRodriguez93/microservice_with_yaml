package es.micro.app.mapper;



import es.micro.app.entity.CentroEntity;
import es.swagger.codegen.models.Centro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CentrosMapper {

    CentrosMapper INSTANCE = Mappers.getMapper(CentrosMapper.class);

    @Mapping(source = "numCentro", target = "numCentro")
    @Mapping(source = "nombreCentro", target = "nombreCentro")
    Centro toApiDomain(CentroEntity source);

    List<Centro> toApiDomain(List<CentroEntity> source);

    @Mapping(source = "numCentro", target = "numCentro")
    @Mapping(source = "nombreCentro", target = "nombreCentro")
    CentroEntity toEntity(Centro source);
}