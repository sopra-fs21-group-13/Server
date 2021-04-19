package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;

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
}
