package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.util.JpaUtil;

public class DataTypeTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void dataType() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setName("Sony");
        customer.setId("1");
        customer.setPrimaryEmail("sony@email.com");
        customer.setAge((byte) 28);
        customer.setMarried(true);
        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
