package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.util.JpaUtil;

import java.time.LocalDateTime;

public class LockingTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void optimisticLocking() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("nokia");
        brand.setName("Nokia");
        brand.setDescription("Nokia Phone");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticLockingDemo1() throws InterruptedException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia Demo 1");
        brand.setDescription("Nokia Demo 1");

        Thread.sleep(10 * 1000L);
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void optimisticLockingDemo2() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia");
        brand.setName("Nokia Demo 2");
        brand.setDescription("Nokia Demo 2");
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void pessimisticLockingDemo1() throws InterruptedException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia Demo 1");
        brand.setDescription("Nokia Demo 1");

        Thread.sleep(10 * 1000L);
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void pessimisticLockingDemo2() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = entityManager.find(Brand.class, "nokia", LockModeType.PESSIMISTIC_WRITE);
        brand.setName("Nokia Demo 2");
        brand.setDescription("Nokia Demo 2");
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
