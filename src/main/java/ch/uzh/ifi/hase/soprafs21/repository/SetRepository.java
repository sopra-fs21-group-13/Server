package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository("setRepository")
public interface SetRepository extends JpaRepository<Set, Long> {

    //Implemented:
    Optional<Set> findBySetId(Long id);

    List<Set> findBySetStatus(SetStatus setStatus);


}

