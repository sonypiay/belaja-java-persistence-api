package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.*;
import programmerzamannow.jpa.util.JpaUtil;

public class InheritanceTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void singleTableInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Employee employee = new Employee();
        employee.setId("sony");
        employee.setName("Sony Darmawan");
        entityManager.persist(employee);

        Manager manager = new Manager();
        manager.setId("joko");
        manager.setName("Joko");
        manager.setTotalEmployee(10);
        entityManager.persist(manager);

        VicePresident vicePresident = new VicePresident();
        vicePresident.setId("budi");
        vicePresident.setName("Budi Nugroho");
        vicePresident.setTotalManager(5);
        entityManager.persist(vicePresident);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findEntity() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Manager manager = entityManager.find(Manager.class, "joko");
        Assertions.assertEquals("Joko", manager.getName());

        Employee employee = entityManager.find(Employee.class, "budi");
        VicePresident vicePresident = (VicePresident) employee;
        Assertions.assertEquals("Budi Nugroho", vicePresident.getName());

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinTableInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        PaymentGopay paymentGopay = new PaymentGopay();
        paymentGopay.setId("gopay1");
        paymentGopay.setAmount(100_000L);
        paymentGopay.setGopayId("0899999");
        entityManager.persist(paymentGopay);

        PaymentCreditCard paymentCreditCard = new PaymentCreditCard();
        paymentCreditCard.setId("cc1");
        paymentCreditCard.setAmount(500_000L);
        paymentCreditCard.setBank("BCA");
        paymentCreditCard.setMaskedCard("4555-5555");
        entityManager.persist(paymentCreditCard);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void findJoinTable() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Payment payment = entityManager.find(Payment.class, "gopay1");
        PaymentGopay paymentGopay = entityManager.find(PaymentGopay.class, "gopay1");
        PaymentCreditCard paymentCreditCard = entityManager.find(PaymentCreditCard.class, "cc1");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
