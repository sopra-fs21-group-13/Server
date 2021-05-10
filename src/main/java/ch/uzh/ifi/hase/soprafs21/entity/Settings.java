package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "SETTINGS")

public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long settingsId;

    @Column
    private Long userID;

    @Column
    private Long setID;

    @Column
    private Boolean cardsShuffled;

    @Column
    private Boolean studyStarred;

    @Column
    private Long lastCard;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> markedCards;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> savedOrder;


    // Getters & Setters

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

    public List<Long> getMarkedCards() {
        return markedCards;
    }

    public void setMarkedCards(List<Long> markedCards) {
        this.markedCards = markedCards;
    }

    public List<Long> getSavedOrder() {
        return savedOrder;
    }

    public void setSavedOrder(List<Long> savedOrder) {
        this.savedOrder = savedOrder;
    }
}
