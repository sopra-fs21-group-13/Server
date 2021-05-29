package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SettingsRepository extends JpaRepository<Settings,Long> {

    // Implemented:
    Settings findByUserIDAndSetID(Long userId, Long setID);  //While loading the setPage zou can just call this api passing userID and the setID thats been clicked

    List<Settings> findAllBySetID(Long setId);

    /**
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN 'true' ELSE 'false' END FROM Settings s WHERE s.userID = ?1 AND s.setID ")
    public Boolean existsByUsername(String username);
    */

    boolean existsByUserIDAndSetID(Long userID, Long setID);

    void deleteBySetID(Long setID);

    void deleteByUserIDAndSetID(Long userID, Long setID);

}
