package programmerzamannow.jpa;

import jakarta.persistence.*;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.*;
import programmerzamannow.jpa.util.JpaUtil;

import java.util.List;

public class JpaQueryLanguageTest {
    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void selectQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b", Brand.class);
        List<Brand> brands = query.getResultList();

        for(Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void whereClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        String jpql = "select m from Member m where " + "m.name.firstName = :firstName and m.name.lastName = :lastName";
        TypedQuery<Member> query = entityManager.createQuery(jpql, Member.class);
        query.setParameter("firstName", "Eko");
        query.setParameter("lastName", "Khannedy");

        List<Member> members = query.getResultList();

        for (Member member : members) {
            System.out.println(member.getId() + " - " + member.getFullName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        String jpql = "select p from Product p join p.brand b where b.name = :brandName";
        TypedQuery<Product> query = entityManager.createQuery(jpql, Product.class);
        query.setParameter("brandName", "samsung");

        List<Product> products = query.getResultList();

        for(Product product : products) {
            System.out.println(product.getName() + " - " + product.getBrand().getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void joinFetchClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        String jpql = "select u from User u join fetch u.likes p where p.name = :productName";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("productName", "P2");

        List<User> users = query.getResultList();

        for(User user : users) {
            System.out.println("User: " + user.getName());

            for (Product product : user.getLikes()) {
                System.out.println("Product: " + product.getName());
            }
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void orderByClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.name asc", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void insertRandomBrand() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        for (int i = 1; i <= 100; i++) {
            Brand brand = new Brand();
            brand.setId(String.valueOf(i));
            brand.setName("Brand " + String.valueOf(i));
            entityManager.persist(brand);
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void limitOffsetClause() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createQuery("select b from Brand b order by b.id asc", Brand.class);
        query.setFirstResult(0);
        query.setMaxResults(10);

        List<Brand> resultList = query.getResultList();

        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Brand> query = entityManager.createNamedQuery("Brand.findAllByName", Brand.class);
        query.setParameter("brandName", "samsung");
        List<Brand> resultList = query.getResultList();

        for (Brand brand : resultList) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void selectSomeFields() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select b.id, b.name from Brand b where b.name = :brandName", Object[].class);
        query.setParameter("brandName", "samsung");
        final List<Object[]> resultList = query.getResultList();

        for(Object[] item : resultList) {
            System.out.println(item[0] + " - " + item[1]);
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void selectNewConstructor() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<SimpleBrand> query = entityManager.createQuery("select new programmerzamannow.jpa.entity.SimpleBrand(b.id, b.name) from Brand b where b.name = :brandName", SimpleBrand.class);
        query.setParameter("brandName", "samsung");

        List<SimpleBrand> resultList = query.getResultList();

        for (SimpleBrand item : resultList) {
            System.out.println(item.getId() + " - " + item.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void aggregateFunction() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        TypedQuery<Object[]> query = entityManager.createQuery("select min(p.price), max(p.price), avg(p.price) from Product p", Object[].class);

        Object[] resultList = query.getSingleResult();

        System.out.println("Min: " + resultList[0]);
        System.out.println("Max: " + resultList[1]);
        System.out.println("Avg: " + resultList[2]);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void aggregateFunctionWithGroupBy() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        String jpql = "select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > :min";
        TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
        query.setParameter("min", 500_000L);

        List<Object[]> resultList = query.getResultList();

        for (Object[] item : resultList) {
            System.out.println("Brand: " + item[0]);
            System.out.println("Min: " + item[1]);
            System.out.println("Max: " + item[2]);
            System.out.println("Avg: " + item[3]);
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nativeQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Query query = entityManager.createNativeQuery("select * from brands where brands.created_at is not null", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void namedNativeQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Query query = entityManager.createNamedQuery("Brand.native.findAll", Brand.class);
        List<Brand> brands = query.getResultList();

        for (Brand brand : brands) {
            System.out.println(brand.getId() + " - " + brand.getName());
        }

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }

    @Test
    void nonQuery() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Query query = entityManager.createQuery("update Brand b set b.name = :newName where b.id = :id");
        query.setParameter("id", "samsung");
        query.setParameter("newName", "Samsung Updated");
        int impacedRecord = query.executeUpdate();

        System.out.println("Success update " + impacedRecord + " record(s) in database.");

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
