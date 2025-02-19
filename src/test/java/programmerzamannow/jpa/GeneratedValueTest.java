package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Category;
import programmerzamannow.jpa.util.JpaUtil;

public class GeneratedValueTest {

    private final EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void insert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Category category = new Category();
        category.setName("Gadget");
        category.setDescription("Gadget Termurah");
        entityManager.persist(category);

        Assertions.assertNotNull(category.getId());
        Category findCategory = entityManager.find(Category.class, category.getId());
        Assertions.assertNotNull(findCategory);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
