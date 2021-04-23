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
    private Long lastCardID;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> starredCards;

    @Column
    @ElementCollection(targetClass=Long.class)
    private List<Long> cardOrder;

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

    public Long getLastCardID() {
        return lastCardID;
    }

    public void setLastCardID(Long lastCardID) {
        this.lastCardID = lastCardID;
    }

    public List<Long> getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(List<Long> cardOrder) {
        this.cardOrder = cardOrder;
    }

    public List<Long> getStarredCards() {
        return starredCards;
    }

    public void setStarredCards(List<Long> starredCards) {
        this.starredCards = starredCards;
    }
}
