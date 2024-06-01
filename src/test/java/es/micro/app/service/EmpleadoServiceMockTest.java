package es.micro.app.service;


import es.micro.app.entity.EmpleadoEntity;
import es.micro.app.repository.CentroRepository;
import es.micro.app.repository.EmpleadoRepository;
import es.swagger.codegen.models.Empleado;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class EmpleadoServiceMockTest {

    @Autowired
    private EmpleadoService empleadoService;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private CentroRepository centroRepository;

    @Test
    public void getEmpleadosTest() {
        // Configuración del mock del repositorio
        List<EmpleadoEntity> empleadosEntities = new ArrayList<>();
        empleadosEntities.add(new EmpleadoEntity());
        Mockito.when(empleadoRepository.findAll()).thenReturn(empleadosEntities);

        // Ejecución del método a probar
        List<Empleado> empleados = empleadoService.getEmpleados();

        // Verificación del resultado
        Assertions.assertEquals(14, empleados.size());
    }
    @Test
    public void putEmpleadoIdTest() {

        // Configuración del mock del repositorio para que devuelva el empleado al buscar por ID
        EmpleadoEntity empleadoEntity = new EmpleadoEntity();
        empleadoEntity.setIdEmpleado(1);
        Mockito.when(empleadoRepository.save(empleadoEntity)).thenReturn(empleadoEntity);

        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(1);
        empleado.setNombre("test");
        // Ejecución del método a probar
        HttpStatus status = empleadoService.putEmpleadoId(1, new Empleado());

        // Verificación del resultado
        Assertions.assertEquals(HttpStatus.OK, status);
        Assertions.assertEquals("test",empleado.getNombre());
    }


    @Test
    public void deleteEmpleadoIdTest() {
        // Configuración del mock del repositorio
        EmpleadoEntity empleadoEntity = new EmpleadoEntity();
        empleadoEntity.setIdEmpleado(1);
        Mockito.when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleadoEntity));

        // Ejecución del método a probar
        HttpStatus status = empleadoService.deleteEmpleadoId(1);

        // Verificación del resultado
        Assertions.assertEquals(HttpStatus.NO_CONTENT, status);
    }

    @Test
    public void postEmpleadoTest() {
        // Configuración del mock del repositorio para devolver la entidad guardada
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(100);
        empleado.setNombre("test");
        // Ejecución del método a probar
        HttpStatus status = empleadoService.postEmpleado(empleado);

        // Verificación del resultado
        Assertions.assertEquals(HttpStatus.CREATED, status);
    }



    @Test
    public void getEmpleadoByIdTest() {
        // Configuración del mock del repositorio
        EmpleadoEntity empleadoEntity = new EmpleadoEntity();
        empleadoEntity.setIdEmpleado(1);
        Mockito.when(empleadoRepository.findById(1)).thenReturn(Optional.of(empleadoEntity));

        // Ejecución del método a probar
        Empleado empleado = empleadoService.getEmpleadoById("1");

        // Verificación del resultado
        Assertions.assertNotNull(empleado);
    }
}
