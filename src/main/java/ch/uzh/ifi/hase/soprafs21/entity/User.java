package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;

import javax.persistence.*;
import java.util.List;


/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */

@Entity
@Table(name = "USER")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true, length = 512)
    private String token;

    @Column(nullable = false)
    private UserStatus status;

    @Column(nullable = false, unique = true)
    private String password;

    @Column
    private Boolean inGame;

    @Column
    private String photo;

    @Column
    private Integer numberOfWins;

    @Column
    private String email;

    // This column contains all sets a users has access to learn from
    // aka not equal to the created sets!
    @Column
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Set> createdSets;

    // All sets that a user can learn with
    @Column
    @ManyToMany(mappedBy = "members", cascade = CascadeType.ALL)
    private List<Set> learnSets;

    // List of Invitations for open games
    @Column
    @ManyToMany(mappedBy = "receivers", cascade = CascadeType.ALL)
    private List<Invitation> invitations;

    // Getters & Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Boolean getInGame() {
        return inGame;
    }

    public void setInGame(Boolean inGame) {
        this.inGame = inGame;
    }

    public Integer getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(Integer numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public List<Set> getCreatedSets() {
        return createdSets;
    }

    public void setCreatedSets(List<Set> createdSets) {
        this.createdSets = createdSets;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Set> getLearnSets() {
        return learnSets;
    }

    public void setLearnSets(List<Set> learnSets) {
        this.learnSets = learnSets;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }

}
