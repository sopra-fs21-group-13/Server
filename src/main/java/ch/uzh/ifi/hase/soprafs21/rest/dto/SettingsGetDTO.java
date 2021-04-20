package ch.uzh.ifi.hase.soprafs21.rest.dto;


import java.util.ArrayList;
import java.util.List;

public class SettingsGetDTO {

    private Long id;

    private Long userID;

    private Long setID;

    private Boolean cardsShuffled;

    private Boolean studyStarred;

    private Long lastCardID;

    private ArrayList<Long> starredCards;

    private ArrayList<Integer> cardOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getLastCardID() {
        return lastCardID;
    }

    public void setLastCardID(Long lastCardID) {
        this.lastCardID = lastCardID;
    }

    public ArrayList<Integer> getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(ArrayList<Integer> cardOrder) {
        this.cardOrder = cardOrder;
    }

    public ArrayList<Long> getStarredCards() {
        return starredCards;
    }

    public void setStarredCards(ArrayList<Long> starredCards) {
        this.starredCards = starredCards;
    }
}


