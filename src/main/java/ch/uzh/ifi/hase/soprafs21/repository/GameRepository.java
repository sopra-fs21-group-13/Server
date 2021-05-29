package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository("gameRepository")
public interface GameRepository extends JpaRepository<Game, Long> {

    //Implemented:
    Optional<Game> findByGameId(Long id);
}

