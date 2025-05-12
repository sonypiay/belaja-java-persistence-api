package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.util.JpaUtil;

import java.time.LocalDateTime;

public class ManagedEntityTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void managedEntity() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("apple");
        brand.setName("Apple");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(brand);

        brand.setName("Apple International");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findManagedEntity() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");
        brand.setName("Apple Indonesia");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void detachManagedEntity() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "apple");
        entityManager.detach(brand);
        brand.setName("Apple Test");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
