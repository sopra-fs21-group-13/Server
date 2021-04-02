package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetOrder;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;

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

    @Column(nullable = false, unique = true)
    private Long userId;

    @Column()
    private ArrayList<Card> cards;

    @Column(nullable = false)
    private SetOrder setOrder;

    @Column()
    private SetCategory setCategory;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
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
}
