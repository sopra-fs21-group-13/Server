package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;

import java.util.List;


public class SetGetDTO {

    private Long setId;
    private String title;
    private Long userId;
    private List<Long> memberIds;
    private List<Card> cards;
    private SetCategory setCategory;
    private SetStatus setStatus;
    private String explain;
    private String photo;
    private Long liked;

    // Setters & Getters

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Long> getMemberIds() {
        return memberIds;
    }

    public void setMemberIds(List<Long> memberIds) {
        this.memberIds = memberIds;
    }

    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
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

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
