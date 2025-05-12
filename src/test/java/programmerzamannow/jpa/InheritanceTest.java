package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.*;
import programmerzamannow.jpa.util.JpaUtil;

import java.time.LocalDateTime;

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

    @Test
    void tablePerClassInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Transaction transaction = new Transaction();
        transaction.setId("t1");
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setBalance(1_000_000L);
        entityManager.persist(transaction);

        TransactionDebit debit = new TransactionDebit();
        debit.setId("t2");
        debit.setDebitAmount(2_000_000L);
        debit.setBalance(1_000_000L);
        debit.setCreatedAt(LocalDateTime.now());
        entityManager.persist(debit);

        TransactionCredit credit = new TransactionCredit();
        credit.setId("t3");
        credit.setCreditAmount(2_000_000L);
        credit.setBalance(1_000_000L);
        credit.setCreatedAt(LocalDateTime.now());
        entityManager.persist(credit);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void tablePerClassFindChild() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TransactionDebit debit = entityManager.find(TransactionDebit.class, "t2");
        TransactionCredit credit = entityManager.find(TransactionCredit.class, "t3");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void tablePerClassFindParent() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Transaction transaction = entityManager.find(Transaction.class, "t1");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void mappedSuperClassInsert() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Brand brand = new Brand();
        brand.setId("xiamoi");
        brand.setName("Xiaomi");
        brand.setDescription("Xiaomi Phone");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());
        entityManager.persist(brand);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
