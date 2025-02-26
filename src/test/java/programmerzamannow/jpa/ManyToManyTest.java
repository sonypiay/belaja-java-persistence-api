package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Product;
import programmerzamannow.jpa.entity.User;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.ArrayList;

public class ManyToManyTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void insert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "sony");
        user.setLikes(new ArrayList<>());

        Product product1 = entityManager.find(Product.class, "P1");
        Product product2 = entityManager.find(Product.class, "P2");

        user.getLikes().add(product1);
        user.getLikes().add(product2);
        entityManager.merge(user);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void find() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "sony");
        Product product = null;

        for(Product item : user.getLikes()) {
            product = item;
            break;
        }

        user.getLikes().remove(product);
        entityManager.merge(user);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
