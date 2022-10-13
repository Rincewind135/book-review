package application.persistence;

import application.entity.Bog;
import application.entity.Review;
import application.repository.BogRepository;
import application.repository.ReviewRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@DataJpaTest
class ReviewEntityTest extends AbstractEntityTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BogRepository bogRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(reviewRepository);
    }

    @Test
    void bogKanGemmesOgLoades() {
        Bog bog = bogRepository.save(opretBog());

        Review review = opretReview(bog);
        Review reviewGemt = reviewRepository.save(review);
        assertNotNull(reviewGemt);

        Optional<Review> reviewOptional = reviewRepository.findById(review.getId());
        Assertions.assertTrue(reviewOptional.isPresent());

        String nytId = UUID.randomUUID().toString();
        Optional<Review> nytReviewOptional = reviewRepository.findById(nytId);
        assertFalse(nytReviewOptional.isPresent());
    }
}