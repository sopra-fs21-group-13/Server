package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.entity.GameSetting;
import ch.uzh.ifi.hase.soprafs21.entity.Message;
import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.List;

public class GameGetDTO {

    private Long gameId;
    private GameStatus status;
    private GameSetting gameSettings;
    private User inviter;
    private Long playSetId;
    private List<Card> playCards;
    private List<User> players;
    private Boolean countDown;
    private List<Message> history; // Change to Object Message with timeStamp / creator etc.
    private Long timer;
    private Boolean player1Ready;
    private Boolean player2Ready;



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

    
    public Boolean getPlayer1Ready(){
        return player1Ready;
    }

    public Boolean getPlayer2Ready(){
        return player2Ready;
    }

    public void setPlayer1Ready(Boolean player1Ready){
        this.player1Ready = player1Ready;
    }

    public void setPlayer2Ready(Boolean player2Ready){
        this.player2Ready = player2Ready;
    }

    public User getInviter() {
        return inviter;
    }

    public void setInviter(User inviter) {
        this.inviter = inviter;
    }

    public GameSetting getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSetting gameSettings) {
        this.gameSettings = gameSettings;
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

    public Long getTimer() {
        return timer;
    }

    public void setTimer(Long timer) {
        this.timer = timer;
    }
}
