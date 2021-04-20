package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;

import java.util.List;

@Entity
@Table(name = "SETTINGS")

public class Settings {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    @ElementCollection(targetClass=Integer.class)
    private List<Long> starredCards;

    @Column
    @ElementCollection(targetClass=Integer.class)
    private List<Integer> cardOrder;

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

    public List<Integer> getCardOrder() {
        return cardOrder;
    }

    public void setCardOrder(List<Integer> cardOrder) {
        this.cardOrder = cardOrder;
    }

    public List<Long> getStarredCards() {
        return starredCards;
    }

    public void setStarredCards(List<Long> starredCards) {
        this.starredCards = starredCards;
    }
}
