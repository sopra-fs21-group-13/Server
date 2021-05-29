package ch.uzh.ifi.hase.soprafs21.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "INVITATION")
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invitationId;

    @Column
    private Long gameId;

    @Column
    private Long sentFromId;

    @Column
    private String sentFromUserName;

    @Column
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private List<User> receivers;

    @Column
    private String setTitle;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id")
    private GameSetting gameSetting;

    // Setters & Getters

    public Long getInvitationId() {
        return invitationId;
    }

    public void setInvitationId(Long invitationId) {
        this.invitationId = invitationId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getSentFromId() {
        return sentFromId;
    }

    public void setSentFromId(Long sentFromId) {
        this.sentFromId = sentFromId;
    }

    public String getSentFromUserName() {
        return sentFromUserName;
    }

    public void setSentFromUserName(String sentFromUserName) {
        this.sentFromUserName = sentFromUserName;
    }

    public List<Long> getReceivers() {
        List<Long> userIds = new ArrayList<>();
        for (User receiver:receivers){
            userIds.add(receiver.getUserId());
        }
        return userIds;
    }

    public void setReceivers(List<User> receivers) {
        this.receivers = receivers;
    }

    public GameSetting getGameSetting() {
        return gameSetting;
    }

    public void setGameSetting(GameSetting gameSetting) {
        this.gameSetting = gameSetting;
    }

    public String getSetTitle() {
        return setTitle;
    }

    public void setSetTitle(String setTitle) {
        this.setTitle = setTitle;
    }
}
