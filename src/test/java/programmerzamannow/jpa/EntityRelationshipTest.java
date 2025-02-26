package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.util.JpaUtil;

public class EntityRelationshipTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void fetch() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Product product = entityManager.find(Product.class, "P2");
        Brand brand = product.getBrand();
//        Brand brand = entityManager.find(Brand.class, "samsung");

        Assertions.assertEquals("Samsung", brand.getName());
        Assertions.assertNotNull(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
