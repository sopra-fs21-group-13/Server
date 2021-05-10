package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GAMESETTING")

public class GameSetting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long gameSettingId;

    @Column
    private Long time;

    @Column
    private Long numberOfCards;

    @Column
    private Long numberOfPlayers;


    // Getters & Setters

    public Long getGameSettingId() {
        return gameSettingId;
    }

    public void setGameSettingId(Long gameSetingId) {
        this.gameSettingId = gameSetingId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public Long getNumberOfCards() {
        return numberOfCards;
    }

    public void setNumberOfCards(Long numberOfCards) {
        this.numberOfCards = numberOfCards;
    }

    public Long getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Long numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

}
