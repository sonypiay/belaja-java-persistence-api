package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Department;
import programmerzamannow.jpa.entity.DepartmentId;
import programmerzamannow.jpa.entity.Member;
import programmerzamannow.jpa.entity.Name;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.UUID;

public class EmbeddedTest {

    private EntityManagerFactory entityManagerFactory;

    @Test
    void testEmbedded() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Eko");
        name.setMiddleName("Kurniawan");
        name.setLastName("Khannedy");

        Member member = new Member();
        member.setName(name);
        member.setEmail("eko@email.com");
        entityManager.persist(member);
        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testEmbeddedId() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("pzn");
        departmentId.setDepartmentId("tech");

        Department department = new Department();
        department.setId(departmentId);
        department.setName("Technology");
        entityManager.persist(department);
        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void testEmbeddedFindId() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        DepartmentId departmentId = new DepartmentId();
        departmentId.setCompanyId("pzn");
        departmentId.setDepartmentId("tech");

        Department department = entityManager.find(Department.class, departmentId);
        Assertions.assertEquals("Technology", department.getName());
        entityTransaction.commit();

        entityManager.close();
        entityManagerFactory.close();
    }
}
