package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("setRepository")
public interface SetRepository extends JpaRepository<Set, Long> {
    Optional<Set> findById(Long id);

    Set findByName(String setName);

    Set findByCategory(SetCategory setCategory);
}
