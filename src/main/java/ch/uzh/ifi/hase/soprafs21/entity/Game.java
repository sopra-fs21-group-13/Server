package ch.uzh.ifi.hase.soprafs21.entity;


import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;

import javax.persistence.*;
import java.util.List;


/**
 * Internal Game Representation
 * This class composes the internal representation of the Game and defines how the game is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */

@Entity
@Table(name = "GAME")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long gameId;

    @Column
    private GameStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "GAMESETTING_ID", unique = true)
    private GameSetting gameSettings;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId")
    private User inviter;

    @Column(nullable = false)
    private Long playSetId;

    @Column
    @ElementCollection(targetClass = Card.class)
    private List<Card> playCards;

    @Column
    @ElementCollection(targetClass = User.class)
    private List<User> players;

    @Column
    private Boolean countDown;

    @Column
    @ElementCollection(targetClass = Message.class)
    private List<Message> history;

    @Column
    private Long score;



    // Getters & Setters

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public GameSetting getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSetting gameSettings) {
        this.gameSettings = gameSettings;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public Long getPlaySetId() {
        return playSetId;
    }

    public void setPlaySetId(Long playSetId) {
        this.playSetId = playSetId;
    }

    public List<Card> getPlayCards() {
        return playCards;
    }

    public void setPlayCards(List<Card> playCards) {
        this.playCards = playCards;
    }

    public List<User> getPlayers() {
        return players;
    }

    public void setPlayers(List<User> players) {
        this.players = players;
    }

    public Boolean getCountDown() {
        return countDown;
    }

    public void setCountDown(Boolean countDown) {
        this.countDown = countDown;
    }

    public List<Message> getHistory() {
        return history;
    }

    public void setHistory(List<Message> history) {
        this.history = history;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
