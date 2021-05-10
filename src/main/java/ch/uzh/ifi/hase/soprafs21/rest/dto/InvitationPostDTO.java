package ch.uzh.ifi.hase.soprafs21.rest.dto;


import ch.uzh.ifi.hase.soprafs21.entity.GameSetting;
import ch.uzh.ifi.hase.soprafs21.entity.User;

import java.util.List;


public class InvitationPostDTO {

    private Long invitationId;
    private Long gameId;
    private Long sentFromId;
    private List<User> receivers;
    private GameSetting gameSetting;


    // Getters & Setters

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getSentFromId() {
        return sentFromId;
    }

    public void setSentFromId(Long sentFromId) {
        this.sentFromId = sentFromId;
    }

    public List<User> getReceivers() {
        return receivers;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }

    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }
}
