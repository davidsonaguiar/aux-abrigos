package com.compass.center;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class CenterEntityTest {
    private CenterEntity center;

    @BeforeEach
    public void setUp() {
        center = new CenterEntity();
    }

    @Test
    public void testCenterEntity() {
        center.setName("Centro de Teste");
        center.setAddress("Rua de Teste, 123");
        center.setCapacity(1000);

        assertEquals("Centro de Teste", center.getName(), "O nome do centro deve ser 'Centro de Teste'");
        assertEquals("Rua de Teste, 123", center.getAddress(), "O endereço do centro deve ser 'Rua de Teste, 123'");
        assertEquals(1000, center.getCapacity(), "A capacidade do centro deve ser 1000");
    }

    @Test
    public void testCenterEntityInvalidId() {
        try {
            center.setId(-1L);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "ID do centro deve ser um número positivo",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'ID do centro deve ser um número positivo'");
        }

        try {
            center.setId(0L);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "ID do centro deve ser um número positivo",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'ID do centro deve ser um número positivo'");
        }

        try {
            center.setId(null);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "ID do centro é obrigatório",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'ID do centro é obrigatório'");
        }
    }

    @Test
    public void testCenterEntityInvalidName() {
        try {
            center.setName("Ce");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Nome do centro deve ter entre 3 e 100 caracteres",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Nome do centro deve ter entre 3 e 100 caracteres'");
        }

        try {
            center.setName("");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Nome do centro é obrigatório",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Nome do centro é obrigatório'");
        }

        try {
            center.setName(null);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Nome do centro é obrigatório",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Nome do centro é obrigatório'");
        }

        try {
            center.setName("2".repeat(101));
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Nome do centro deve ter entre 3 e 100 caracteres",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Nome do centro deve ter entre 3 e 100 caracteres'");
        }
    }

    @Test
    public void testCenterEntityInvalidCapacity() {
        try {
            center.setCapacity(50);
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "Capacidade mínima do centro é 100",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Capacidade mínima do centro é 100'");
        }

        try {
            center.setCapacity(null);
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "Capacidade do centro é obrigatória",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Capacidade do centro é obrigatória'");
        }

        try {
            center.setCapacity(0);
        } catch (IllegalArgumentException e) {
            assertEquals(
                    "Capacidade mínima do centro é 100",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Capacidade mínima do centro é 100'");
        }
    }

    @Test
    public void testCenterEntityInvalidAddress() {
        try {
            center.setAddress("");
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Endereço do centro é obrigatório",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Endereço do centro é obrigatório'");
        }

        try {
            center.setAddress(null);
        }
        catch (IllegalArgumentException e) {
            assertEquals(
                    "Endereço do centro é obrigatório",
                    e.getMessage(),
                    "Deve lançar uma exceção com a mensagem 'Endereço do centro é obrigatório'");
        }
    }

    @Test
    public void testCenterEntityGetters() {
        center.setId(1L);
        center.setName("Centro de Teste");
        center.setAddress("Rua de Teste, 123");
        center.setCapacity(1000);

        assertEquals(1L, center.getId(), "O ID do centro deve ser 1");
        assertEquals("Centro de Teste", center.getName(), "O nome do centro deve ser 'Centro de Teste'");
        assertEquals("Rua de Teste, 123", center.getAddress(), "O endereço do centro deve ser 'Rua de Teste, 123'");
        assertEquals(1000, center.getCapacity(), "A capacidade do centro deve ser 1000");
    }

    @Test
    public void testCenterEntityEquals() {
        CenterEntity center1 = new CenterEntity();
        center1.setId(1L);
        center1.setName("Centro de Teste");
        center1.setAddress("Rua de Teste, 123");
        center1.setCapacity(1000);

        CenterEntity center2 = new CenterEntity();
        center2.setId(1L);
        center2.setName("Centro de Teste");
        center2.setAddress("Rua de Teste, 123");
        center2.setCapacity(1000);

        assertTrue(center1.equals(center2), "Os centros devem ser iguais");
    }

    @Test
    public void testCenterEntityHashCode() {
        CenterEntity center1 = new CenterEntity();
        center1.setId(1L);
        center1.setName("Centro de Teste");
        center1.setAddress("Rua de Teste, 123");
        center1.setCapacity(1000);

        CenterEntity center2 = new CenterEntity();
        center2.setId(1L);
        center2.setName("Centro de Teste");
        center2.setAddress("Rua de Teste, 123");
        center2.setCapacity(1000);

        assertEquals(center1.hashCode(), center2.hashCode(), "Os códigos de hash dos centros devem ser iguais");
    }

    @Test
    public void testCenterEntityToString() {
        center.setId(1L);
        center.setName("Centro de Teste");
        center.setAddress("Rua de Teste, 123");
        center.setCapacity(1000);

        String expected = "CenterEntity(id=1, name=Centro de Teste, address=Rua de Teste, 123, capacity=1000, items=null)";
        assertEquals(expected, center.toString(), "A representação em String do centro deve ser 'CenterEntity(id=1, name=Centro de Teste, address=Rua de Teste, 123, capacity=1000, items=null)'");
    }

    @Test
    public void testCenterEntityNoArgsConstructor() {
        CenterEntity center = new CenterEntity();
        assertTrue(center instanceof CenterEntity, "Deve ser possível criar um objeto CenterEntity com o construtor padrão");
    }

    @Test
    public void testCenterEntityAllArgsConstructor() {
        CenterEntity center = new CenterEntity(1L, "Centro de Teste", "Rua de Teste, 123", 1000, null);
        assertTrue(center instanceof CenterEntity, "Deve ser possível criar um objeto CenterEntity com o construtor com argumentos");
    }

}
