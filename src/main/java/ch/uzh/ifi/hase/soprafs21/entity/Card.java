package ch.uzh.ifi.hase.soprafs21.entity;

import ch.uzh.ifi.hase.soprafs21.constant.CardStatus;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Internal User Representation
 * This class composes the internal representation of the user and defines how the user is stored in the database.
 * Every variable will be mapped into a database field with the @Column annotation
 * - nullable = false -> this cannot be left empty
 * - unique = true -> this value must be unqiue across the database -> composes the primary key
 */
@Entity
@Table(name = "CARD")
public class Card implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false, unique = false)
    private String answer;

    @Column(nullable = false)
    private CardStatus star;

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public CardStatus getStar() {
        return star;
    }

    public void setStar(CardStatus star) {
        this.star = star;
    }
}
