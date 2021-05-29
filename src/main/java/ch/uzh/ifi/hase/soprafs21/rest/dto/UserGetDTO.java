package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Invitation;
import ch.uzh.ifi.hase.soprafs21.entity.Set;

import java.util.List;

public class UserGetDTO {

    private Long userId;
    private String name;
    private String username;
    private UserStatus status;
    private List<Set> createdSets;
    private List<Set> learnSets;
    private String token;
    private String email;
    private String password;
    private String photo;
    private boolean inGame;
    private int numberOfWins;
    private List<Invitation> invitations;

    // Getters & Setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public List<Set> getCreatedSets() {
        return createdSets;
    }

    public void setCreatedSets(List<Set> createdSets) {
        this.createdSets = createdSets;
    }

    public List<Set> getLearnSets() {
        return learnSets;
    }

    public void setLearnSets(List<Set> learnSets) {
        this.learnSets = learnSets;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public List<Invitation> getInvitations() {
        return invitations;
    }

    public void setInvitations(List<Invitation> invitations) {
        this.invitations = invitations;
    }
}
