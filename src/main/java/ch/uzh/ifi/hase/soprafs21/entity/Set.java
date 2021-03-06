package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Internal Set Representation
 * This class composes the internal representation of the set and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unique across the database -> composes the primary key
 */

@Entity
@Table(name = "SET")
public class    Set implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long setId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 512)
    private String explain;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<User> members;

    @Column
    @OneToMany(cascade = CascadeType.ALL) // Many cards to one set
    @ElementCollection(targetClass = Card.class) //Since there is no connection back to Set
    private List<Card> cards;

    @Column(nullable = false)
    private SetCategory setCategory;

    @Column(nullable = false)
    private SetStatus setStatus;

    @Column
    private String photo;

    @Column
    private Long liked;

    // Getters & Setters

    public Long getSetId() {
        return setId;
    }

    public void setSetId(Long setId) {
        this.setId = setId;
    }

    public Long getUser() {
        return user.getUserId();
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Long getLiked() {
        return liked;
    }

    public void setLiked(Long liked) {
        this.liked = liked;
    }

    public List<Long> getMembers() {
        List<Long> memberIds =  new ArrayList<>();
        for (User member: members){
            memberIds.add(member.getUserId());
        }
        return memberIds;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
