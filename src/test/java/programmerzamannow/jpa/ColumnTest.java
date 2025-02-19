package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Customer;
import programmerzamannow.jpa.util.JpaUtil;

public class ColumnTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void testColumn() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Customer customer = new Customer();
        customer.setName("Sony");
        customer.setId("1");
        customer.setPrimaryEmail("sony@email.com");
        entityManager.persist(customer);

        Customer findCustomer = entityManager.find(Customer.class, "1");

        Assertions.assertNotNull(findCustomer);
        Assertions.assertEquals("1", findCustomer.getId());
        Assertions.assertEquals("Sony", findCustomer.getName());
        Assertions.assertEquals("sony@email.com", findCustomer.getPrimaryEmail());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
