package sfedu.xast.api;

import org.junit.jupiter.api.*;
import sfedu.xast.models.Address;
import sfedu.xast.models.TestEntity;
import sfedu.xast.utils.HibernateUtil;

import static org.junit.jupiter.api.Assertions.*;

class HibernateCRUDWithTestEntityTest {

    private static HibernateCRUDWithTestEntity crudService;

    @BeforeAll
    static void setUp() {
        crudService = new HibernateCRUDWithTestEntity(HibernateUtil.getSessionFactory());
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    @DisplayName("Test CREATE and READ operations")
    void testCreateAndRead() {
        // Создаем новую сущность
        Address address = new Address("Street", "City", "Zipcode");
        TestEntity newEntity = new TestEntity("Test Name", "Test Description", true, address);
        String id = crudService.createTestEntity(newEntity);

        // Проверяем, что ID был присвоен
        assertNotNull(id, "ID should not be null after creation");

        // Читаем сущность из БД
        TestEntity retrievedEntity = crudService.readTestEntity(id);

        // Проверяем, что данные совпадают
        assertNotNull(retrievedEntity, "Entity should not be null");
        assertEquals("Test Name", retrievedEntity.getName());
        assertEquals("Test Description", retrievedEntity.getDescription());
        assertTrue(retrievedEntity.getIs_check());
    }

    @Test
    @DisplayName("Test UPDATE operation")
    void testUpdate() {
        // Создаем сущность
        Address address = new Address("Street", "City", "Zipcode");
        TestEntity entity = new TestEntity("Test Name1", "Test Description1", true, address);
        String id = crudService.createTestEntity(entity);

        // Обновляем сущность
        TestEntity toUpdate = crudService.readTestEntity(id);
        toUpdate.setName("Updated Name");
        toUpdate.setDescription("Updated Desc");
        toUpdate.setIs_check(true);

        crudService.updateTestEntity(toUpdate);

        // Проверяем изменения
        TestEntity updatedEntity = crudService.readTestEntity(id);
        assertEquals("Updated Name", updatedEntity.getName());
        assertEquals("Updated Desc", updatedEntity.getDescription());
        assertTrue(updatedEntity.getIs_check());
    }

    @Test
    @DisplayName("Test DELETE operation")
    void testDelete() {
        Address address = new Address("Street", "City", "Zipcode");
        TestEntity newEntity = new TestEntity("To Delete", "Till be deleted", true, address);
        // Создаем сущность
        String id = crudService.createTestEntity(newEntity);

        // Удаляем сущность
        crudService.deleteTestEntity(id);

        // Проверяем, что сущности больше нет
        assertNull(crudService.readTestEntity(id), "Entity should be deleted");
        assertFalse(crudService.existsById(id), "Entity should not exist in DB");
    }

}