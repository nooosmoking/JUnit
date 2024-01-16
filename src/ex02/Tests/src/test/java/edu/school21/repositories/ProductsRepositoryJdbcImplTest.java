package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepository repository;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(new Product(1, "Grape", 200),
            new Product(2, "Bread", 50),
            new Product(3, "Kinder", 50),
            new Product(4, "Milk", 70),
            new Product(5, "Cheese", 350));
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2, "Bread", 50);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(4, "Milk", 80);
    final Product EXPECTED_SAVED_PRODUCT = new Product(6, "Egg", 120);

    @BeforeEach
    void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    public void findAllTest() {
        assertEquals(EXPECTED_FIND_ALL_PRODUCTS, repository.findAll());
    }

    @Test
    public void findIdTest() {
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repository.findById(2L).get());
        assertFalse(repository.findById(6L).isPresent());
    }

    @Test
    public void updateTest() {
        repository.update(EXPECTED_UPDATED_PRODUCT);
        assertEquals(EXPECTED_UPDATED_PRODUCT, repository.findById(4L).get());
    }

    @Test
    public void saveTest() {
        repository.save(EXPECTED_SAVED_PRODUCT);
        assertEquals(EXPECTED_SAVED_PRODUCT, repository.findById(6L).get());
    }

    @Test
    public void deleteTest() {
        repository.delete(2L);
        assertFalse(repository.findById(2L).isPresent());
    }
}
