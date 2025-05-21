package programmerzamannow.jpa;

import jakarta.persistence.*;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Brand;
import programmerzamannow.jpa.entity.Product;
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

    @Test
    void criteriaQueryJoinClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        criteria.select(p);
        criteria.where(
                builder.equal(b.get("name"), "Xiaomi")
        );

        final TypedQuery<Product> query = entityManager.createQuery(criteria);
        List<Product> products = query.getResultList();

        for(Product product : products) {
            System.out.println(product.getId() + " - " + product.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaQueryNamedParameter() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        final ParameterExpression<String> brandNameParameter = builder.parameter(String.class, "brandName");

        criteria.select(p);
        criteria.where(
                builder.equal(b.get("name"), brandNameParameter)
        );

        final TypedQuery<Product> query = entityManager.createQuery(criteria);
        query.setParameter(brandNameParameter, "Xiaomi");
        List<Product> products = query.getResultList();

        for(Product product : products) {
            System.out.println(product.getId() + " - " + product.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaAggregateQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Object[]> criteria = builder.createQuery(Object[].class);
        Root<Product> p = criteria.from(Product.class);
        Join<Product, Brand> b = p.join("brand");

        criteria.select(builder.array(
                b.get("id"),
                builder.min(p.get("price")),
                builder.max(p.get("price")),
                builder.avg(p.get("price"))
        ));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b

        criteria.groupBy(b.get("id"));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id

        criteria.having(builder.greaterThan(
                builder.min(p.get("price")),
                500_000L
        ));
        // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > 500000

        TypedQuery<Object[]> query = entityManager.createQuery(criteria);
        List<Object[]> objects = query.getResultList();

        for(Object[] object : objects) {
            System.out.println("Brand: " + object[0]);
            System.out.println("Min: " + object[1]);
            System.out.println("Max: " + object[2]);
            System.out.println("Avg: " + object[3]);
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void criteriaNonQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaUpdate<Brand> criteriaUpdate = builder.createCriteriaUpdate(Brand.class);
        Root<Brand> b = criteriaUpdate.from(Brand.class);

        criteriaUpdate.set(b.get("name"), "Apple");
        criteriaUpdate.set(b.get("description"), "Apple Corporation");
        criteriaUpdate.where(
                builder.equal(b.get("id"), "apple")
        );

        Query query = entityManager.createQuery(criteriaUpdate);
        int result = query.executeUpdate();

        System.out.println("Success update " + result + " rows");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
