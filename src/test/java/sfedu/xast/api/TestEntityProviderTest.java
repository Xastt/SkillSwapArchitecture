package sfedu.xast.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import sfedu.xast.models.Address;
import sfedu.xast.models.TestEntity;
import sfedu.xast.utils.HibernateUtil;

import java.util.Date;

class TestEntityProviderTest {

    private static TestEntityProvider testEntityProvider;

    @BeforeAll
    static void setUp() {
        testEntityProvider = new TestEntityProvider(HibernateUtil.getSessionFactory());
    }

    @AfterAll
    static void tearDown() {
        HibernateUtil.shutdown();
    }

    @Test
    void testSaveEntity() {
        // Создание объекта TestEntity
        TestEntity entity = createTestEntity();

        // Сохранение объекта
        testEntityProvider.saveEntity(entity);

        // Проверка, что ID был присвоен
        assertNotNull(entity.getId());
    }

    @Test
    void testGetEntityById() {
        // Создание и сохранение объекта
        TestEntity entity = createTestEntity();
        testEntityProvider.saveEntity(entity);

        // Получение объекта по ID
        TestEntity retrievedEntity = testEntityProvider.getEntityById(entity.getId());

        // Проверка, что объект был найден
        assertNotNull(retrievedEntity);

        // Проверка корректности данных
        assertEquals(entity.getName(), retrievedEntity.getName());
        assertEquals(entity.getDescription(), retrievedEntity.getDescription());
        assertEquals(entity.getAddress().getStreet(), retrievedEntity.getAddress().getStreet());
    }

    @Test
    void testUpdateEntity() {
        // Создание и сохранение объекта
        TestEntity entity = createTestEntity();
        testEntityProvider.saveEntity(entity);

        // Обновление объекта
        entity.setName("Updated Name");
        testEntityProvider.updateEntity(entity);

        // Получение обновленного объекта
        TestEntity updatedEntity = testEntityProvider.getEntityById(entity.getId());

        // Проверка, что имя было обновлено
        assertEquals("Updated Name", updatedEntity.getName());
    }

    @Test
    void testDeleteEntity() {
        // Создание и сохранение объекта
        TestEntity entity = createTestEntity();
        testEntityProvider.saveEntity(entity);

        // Удаление объекта
        testEntityProvider.deleteEntity(entity);

        // Попытка получить удаленный объект
        TestEntity deletedEntity = testEntityProvider.getEntityById(entity.getId());

        // Проверка, что объект был удален
        assertNull(deletedEntity);
    }

    // Вспомогательный метод для создания тестового объекта
    private TestEntity createTestEntity() {
        TestEntity entity = new TestEntity();
        entity.setName("Test Name");
        entity.setDescription("Test Description");
        entity.setDateCreated(new Date());
        entity.setCheck(true);

        Address address = new Address();
        address.setStreet("123 Main St");
        address.setCity("New York");
        address.setZipcode("10001");
        entity.setAddress(address);

        return entity;
    }
}