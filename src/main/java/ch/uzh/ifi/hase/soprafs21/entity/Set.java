package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetOrder;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes the primary key
 */
@Entity
@Table(name = "SET")
public class Set implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long setId;

    @Column(nullable = false)
    private String setName;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    @OneToMany(cascade = CascadeType.ALL) // Many cards to one set
    @ElementCollection(targetClass = Card.class) //Since there is no connection back to Set
    private List<Card> cards;

    @Column(nullable = false)
    private SetOrder setOrder;

    @Column()
    private SetCategory setCategory;

    @Column(nullable = false)
    private SetStatus setStatus;

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getSetName() {
        return setName;
    }

    public void setSetName(String setName) {
        this.setName = setName;
    }

    public Long getUser() {
        return user.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public SetOrder getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(SetOrder setOrder) {
        this.setOrder = setOrder;
    }

    public SetCategory getSetCategory() {
        return setCategory;
    }

    public void setSetCategory(SetCategory setCategory) {
        this.setCategory = setCategory;
    }

    public SetStatus getSetStatus() {
        return setStatus;
    }

    public void setSetStatus(SetStatus setStatus) {
        this.setStatus = setStatus;
    }
}
