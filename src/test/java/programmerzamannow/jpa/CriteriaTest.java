package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.SimpleBrand;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.List;

public class CriteriaTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void criteriaQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteria = builder.createQuery(Brand.class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(b);

        TypedQuery<Brand> query = entityManager.createQuery(criteria);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaNonEntityQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(builder.array(b.get("id"), b.get("name")));
//        select b.id, b.name from Brand b

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> brands = query.getResultList();

        for (Object[] brand : brands) {
            System.out.println(brand[0] + " - " + brand[1]);
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryConstructor() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<SimpleBrand> criteria = builder.createQuery(SimpleBrand.class);
        Root<Brand> b = criteria.from(Brand.class);
        criteria.select(builder.construct(
                SimpleBrand.class,
                b.get("id"),
                b.get("name")
        ));

        TypedQuery<SimpleBrand> query = entityManager.createQuery(criteria);
        List<SimpleBrand> brands = query.getResultList();

        for (SimpleBrand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryWhereClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteria = builder.createQuery(Brand.class);
        Root<Brand> root = criteria.from(Brand.class);
        criteria.where(builder.equal(root.get("id"), "apple"));

        TypedQuery<Brand> query = entityManager.createQuery(criteria);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryOrWhereOperator() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Brand> criteria = builder.createQuery(Brand.class);
        Root<Brand> root = criteria.from(Brand.class);
        criteria.where(
                builder.or(
                        builder.equal(root.get("id"), "apple"),
                        builder.equal(root.get("id"), "samsung")
                )
        );

        TypedQuery<Brand> query = entityManager.createQuery(criteria);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
