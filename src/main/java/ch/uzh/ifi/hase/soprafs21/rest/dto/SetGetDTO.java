package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;

import java.util.List;

//import ch.uzh.ifi.hase.soprafs21.constant.SetOrder;

public class SetGetDTO {

    private Long setId;
    private String setName;
    private Long userId;
    private List<Card> cards;
    //private SetOrder setOrder;
    private SetCategory setCategory;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    /**
    public SetOrder getSetOrder() {
        return setOrder;
    }

    public void setSetOrder(SetOrder setOrder) {
        this.setOrder = setOrder;
    }
     */

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
