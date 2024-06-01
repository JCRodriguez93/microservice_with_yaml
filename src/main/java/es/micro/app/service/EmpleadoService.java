package es.micro.app.service;


import es.micro.app.entity.EmpleadoEntity;
import es.micro.app.mapper.EmpleadosMapper;
import es.micro.app.repository.EmpleadoRepository;
import es.swagger.codegen.models.Empleado;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Validated
@Service
@Transactional
public class EmpleadoService {

    private EmpleadosMapper mapper = EmpleadosMapper.INSTANCE;

    @Autowired
    private EmpleadoRepository repository;


    public List<Empleado> getEmpleados() {
        return mapper.toApiDomain(repository.findAll());
    }

    public HttpStatus putEmpleadoId(int id, Empleado empleado) {
        Optional<EmpleadoEntity> optEmpleado = repository.findById(id);
        if (optEmpleado.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        empleado.setIdEmpleado(id);
        repository.save(mapper.toEntity(empleado));
        return HttpStatus.OK;
    }

    public HttpStatus deleteEmpleadoId(int id) {
        Optional<EmpleadoEntity> optEmpleado = repository.findById(id);
        if (optEmpleado.isEmpty()) {
            return HttpStatus.NOT_FOUND;
        }
        repository.deleteById(id);
        return HttpStatus.NO_CONTENT;
    }

    public HttpStatus postEmpleado(Empleado empleado) {
        EmpleadoEntity entity = mapper.toEntity(empleado);
        repository.save(entity);
        return HttpStatus.CREATED;
    }

    public Empleado getEmpleadoById(String id) {
        Optional<EmpleadoEntity> optEmpleado = repository.findById(Integer.valueOf(id));
        return optEmpleado.map(mapper::toApiDomain).orElse(null);
    }


}