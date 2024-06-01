package es.micro.app.repository;


import es.micro.app.entity.CentroEntity;
import es.micro.app.entity.EmpleadoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmpleadoRepositoryMockTest {

    @MockBean
    private EmpleadoRepository empleadoRepository;

    @BeforeEach
    public void setUp() {
        EmpleadoEntity empleadoMock = EmpleadoEntity.builder()
                .idEmpleado(100)
                .nombre("test user")
                .centro(CentroEntity.builder()
                        .numCentro(100)
                        .nombreCentro("A Center")
                        .build())
                .build();

        Mockito.when(empleadoRepository.findById(empleadoMock.getIdEmpleado())).thenReturn(Optional.of(empleadoMock));

        List<EmpleadoEntity> listaSimulada = new ArrayList<>();
        listaSimulada.add(empleadoMock);
        Mockito.when(empleadoRepository.findByNombre(empleadoMock.getNombre())).thenReturn(listaSimulada);

        Mockito.when(empleadoRepository.findByIdCentro(empleadoMock.getCentro().getNumCentro())).thenReturn(listaSimulada);
    }

    @Test
    public void getEmployeeByIdTest() {
        Optional<EmpleadoEntity> encontrado = empleadoRepository.findById(100);
        Assertions.assertTrue(encontrado.isPresent(), "Se esperaba encontrar un empleado con el ID proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrado.get();
        Assertions.assertEquals(100, empleadoEncontrado.getIdEmpleado(),
                "El ID del empleado encontrado no coincide con el ID del empleado buscado");
    }

    @Test
    public void findEmployeeByNameTest() {
        String nombreBuscado = "test user";
        List<EmpleadoEntity> encontrados = empleadoRepository.findByNombre(nombreBuscado);
        Assertions.assertFalse(encontrados.isEmpty(), "Se esperaba encontrar al menos un empleado con el nombre proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrados.get(0);
        Assertions.assertEquals(nombreBuscado, empleadoEncontrado.getNombre(),
                "El nombre del empleado encontrado no coincide con el nombre del empleado buscado");
    }

    @Test
    public void findEmployeeByIdCenterTest() {
        List<EmpleadoEntity> encontrados = empleadoRepository.findByIdCentro(100);
        Assertions.assertFalse(encontrados.isEmpty(), "Se esperaba encontrar al menos un empleado con el centro proporcionado");
        EmpleadoEntity empleadoEncontrado = encontrados.get(0);
        Assertions.assertEquals(100, empleadoEncontrado.getCentro().getNumCentro(),
                "El número del centro del empleado encontrado no coincide con el número del centro del empleado buscado");
    }
}
