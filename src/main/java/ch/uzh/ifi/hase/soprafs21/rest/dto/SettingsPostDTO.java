package ch.uzh.ifi.hase.soprafs21.rest.dto;

import java.util.ArrayList;

public class SettingsPostDTO {

    private Long settingsId;
    private Long userID;
    private Long setID;
    private Boolean cardsShuffled;
    private Boolean studyStarred;
    private Long lastCard;
    private ArrayList<Long> markedCards;
    private ArrayList<Long> savedOrder;


    public Long getSettingsId() {
        return settingsId;
    }

    public void setSettingsId(Long settingsId) {
        this.settingsId = settingsId;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getSetID() {
        return setID;
    }

    public void setSetID(Long setID) {
        this.setID = setID;
    }

    public Boolean getCardsShuffled() {
        return cardsShuffled;
    }

    public void setCardsShuffled(Boolean cardsShuffled) {
        this.cardsShuffled = cardsShuffled;
    }

    public Boolean getStudyStarred() {
        return studyStarred;
    }

    public void setStudyStarred(Boolean studyStarred) {
        this.studyStarred = studyStarred;
    }

    public Long getLastCard() {
        return lastCard;
    }

    public void setLastCard(Long lastCard) {
        this.lastCard = lastCard;
    }

    public ArrayList<Long> getMarkedCards() {
        return markedCards;
    }

    public void setMarkedCards(ArrayList<Long> markedCards) {
        this.markedCards = markedCards;
    }

    public ArrayList<Long> getSavedOrder() {
        return savedOrder;
    }

    public void setSavedOrder(ArrayList<Long> savedOrder) {
        this.savedOrder = savedOrder;
    }
}
