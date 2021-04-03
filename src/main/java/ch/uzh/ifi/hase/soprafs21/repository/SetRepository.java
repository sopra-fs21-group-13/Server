package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("setRepository")
public interface SetRepository extends JpaRepository<Set, Long> {

    //Implemented:
    Optional<Set> findBySetId(Long id);






    // Future implementation -> searchbar / Display created Sets

    List<Set> findByUser(User user);

    Set findBySetName(String setName);

    Set findByCategory(SetCategory setCategory);
}
