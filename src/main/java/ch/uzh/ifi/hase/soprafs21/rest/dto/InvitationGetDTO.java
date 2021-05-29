package ch.uzh.ifi.hase.soprafs21.rest.dto;


import ch.uzh.ifi.hase.soprafs21.entity.GameSetting;

import java.util.List;


public class InvitationGetDTO {

    private Long invitationId;
    private Long gameId;
    private Long sentFromId;
    private String sentFromUserName;
    private List<Long> receivers;
    private String setTitle;
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

    public List<Long> getReceivers() {
        return receivers;
    }

    public String getSentFromUserName() {
        return sentFromUserName;
    }

    public void setSentFromUserName(String sentFromUserName) {
        this.sentFromUserName = sentFromUserName;
    }

    public void setReceivers(List<Long> receivers) {
        this.receivers = receivers;
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }

    public String getSetTitle() {
        return setTitle;
    }

    public void setSetTitle(String setTitle) {
        this.setTitle = setTitle;
    }

    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }
}
