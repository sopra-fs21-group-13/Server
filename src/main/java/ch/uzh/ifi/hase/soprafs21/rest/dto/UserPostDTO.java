package ch.uzh.ifi.hase.soprafs21.rest.dto;

import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;

public class UserPostDTO {

    private long userId;
    private String name;
    private String username;
    private UserStatus status;
    private String password;
    private String token;
    private String email;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() { return token; }

    public void setToken(String token) { this.token = token; }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
