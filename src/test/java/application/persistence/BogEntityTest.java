package application.persistence;

import application.entity.Bog;
import application.repository.BogRepository;
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
class BogEntityTest extends AbstractEntityTest {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BogRepository bogRepository;

    @Test
    void injectedComponentsAreNotNull(){
        assertNotNull(dataSource);
        assertNotNull(jdbcTemplate);
        assertNotNull(entityManager);
        assertNotNull(bogRepository);
    }

    @Test
    void bogKanGemmesOgLoades() {
        Bog bog = opretBog();
        Bog bogGemt = bogRepository.save(bog);
        assertNotNull(bogGemt);

        Optional<Bog> bogOptional = bogRepository.findBogByTitel(bog.getTitel());
        Assertions.assertTrue(bogOptional.isPresent());

        String nyTitel = UUID.randomUUID().toString();
        Optional<Bog> nyBogOptional = bogRepository.findBogByTitel(nyTitel);
        assertFalse(nyBogOptional.isPresent());
    }
}