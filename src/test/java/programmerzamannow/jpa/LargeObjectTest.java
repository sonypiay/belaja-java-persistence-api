package programmerzamannow.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Test;
import programmerzamannow.jpa.entity.Images;
import programmerzamannow.jpa.util.JpaUtil;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Calendar;

public class LargeObjectTest {

    private EntityManagerFactory entityManagerFactory = JpaUtil.getEntityManagerFactory();

    @Test
    void testLargeObject() throws IOException, URISyntaxException {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();

        Images images = new Images();
        images.setName("Contoh Image");
        images.setDescription("Image contoh");

        URI uriPath = getClass().getClassLoader().getResource("images/sample.png").toURI();
        String uriString = Path.of(uriPath).toString();
        Path pathImage = Path.of(uriString);

        byte[] image = Files.readAllBytes(pathImage);
        images.setImage(image);
        entityManager.persist(images);

        entityTransaction.commit();
        entityManager.close();
        entityManagerFactory.close();
    }
}
