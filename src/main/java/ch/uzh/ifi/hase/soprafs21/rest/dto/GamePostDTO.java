package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.GameSetting;
import ch.uzh.ifi.hase.soprafs21.entity.Message;
import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.List;

public class GamePostDTO {

    private Long gameId;
    private GameStatus status;
    private GameSetting gameSettings;
    private User inviter;
    private Long playSetId;
    private List<Card> playCards;
    private Boolean countDown;
    private List<Message> history;
    private Long timer;
    private Boolean player1Ready;
    private Boolean player2Ready;
    private Long player1Score;
    private Long player2Score;

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
    
    public Long getPlayer1Score() {
        return player1Score;
    }

    public void setplayer1Score(Long player1Score) {
        this.player1Score = player1Score;
    }

    public Long getPlayer2Score() {
        return player2Score;
    }

    public void setplayer2Score(Long player2Score) {
        this.player2Score = player2Score;
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
