package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.GameSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("gameSettingRepository")
public interface GameSettingRepository extends JpaRepository<GameSetting, Long> {

    //Implemented:
    Optional<GameSetting> findByGameSettingId(Long id);

}

