package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Employee;
import programmerzamannow.jpa.entity.Manager;
import programmerzamannow.jpa.entity.VicePresident;
import programmerzamannow.jpa.util.JpaUtil;

public class InheritanceTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void fetch() {
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
}
