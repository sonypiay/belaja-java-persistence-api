package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.enums.CustomerType;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.UUID;

public class EnumeratedTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void testEnumerated() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setName("Sony");
        customer.setId(UUID.randomUUID().toString());
        customer.setPrimaryEmail("sony@email.com");
        customer.setAge((byte) 28);
        customer.setMarried(false);
        customer.setType(CustomerType.REGULAR);
        entityManager.persist(customer);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
