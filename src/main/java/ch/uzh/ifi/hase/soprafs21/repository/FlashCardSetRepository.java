package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.FlashCardSet;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("FlashCardSetRepository")
public interface FlashCardSetRepository extends JpaRepository<FlashCardSet, Long> {

    List<FlashCardSet> findByUser(User user);
}
