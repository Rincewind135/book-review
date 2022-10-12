package application.repository;

import application.entity.Bog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BogRepository extends JpaRepository<Bog, String> { //}, QuerydslPredicateExecutor<Bog> {

    Optional<Bog> findBogByTitel(String titel);

    Optional<Bog> findBogById(String id);

}