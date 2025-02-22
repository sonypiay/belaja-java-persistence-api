package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Member;
import programmerzamannow.jpa.entity.Name;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.ArrayList;

public class CollectionTest {

    private EntityManagerFactory entityManagerFactory;

    @Test
    void insert() {
        EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Name name = new Name();
        name.setTitle("Mr");
        name.setFirstName("Sony");
        name.setLastName("Darmawan");

        Member member = new Member();
        member.setName(name);
        member.setEmail("sony@email.com");

        member.setHobbies(new ArrayList<>());
        member.getHobbies().add("Sports");
        member.getHobbies().add("Reading");
        member.getHobbies().add("Music");
        member.getHobbies().add("Programming");
        member.getHobbies().add("Coding");
        member.getHobbies().add("Gaming");
        entityManager.persist(member);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
