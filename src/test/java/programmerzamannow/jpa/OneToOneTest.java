package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Credential;
import programmerzamannow.jpa.entity.User;
import programmerzamannow.jpa.entity.Wallet;
import programmerzamannow.jpa.util.JpaUtil;

public class OneToOneTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void insertUser() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Credential credential = new Credential();
        credential.setId("sony");
        credential.setEmail("sony@email.com");
        credential.setPassword("rahasia");
        entityManager.persist(credential);

        User user = new User();
        user.setId("sony");
        user.setName("Sony Darmawan");
        entityManager.persist(user);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findUser() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "sony");

        Assertions.assertNotNull(user.getCredential());
        Assertions.assertEquals("sony@email.com", user.getCredential().getEmail());
        Assertions.assertEquals("rahasia", user.getCredential().getPassword());

        Assertions.assertNotNull(user.getWallet());
        Assertions.assertEquals(1_000_000L, user.getWallet().getBalance());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void saveWallet() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        User user = entityManager.find(User.class, "sony");
        Wallet wallet = new Wallet();
        wallet.setBalance(1_000_000L);
        wallet.setUser(user);
        entityManager.persist(wallet);



        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
