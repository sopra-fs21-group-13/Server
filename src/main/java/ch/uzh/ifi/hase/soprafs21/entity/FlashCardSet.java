package ch.uzh.ifi.hase.soprafs21.entity;


import javax.persistence.*;

@Entity
@Table(name = "flashcardset")
public class FlashCardSet {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne  //better solution with ForeignKey constraint
    @JoinColumn(name="user_id")
    private User user;

    // private Long user_id; //no dependencies between User & Flashcardset

    private String name;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getId() {
        return id;
    }
}
