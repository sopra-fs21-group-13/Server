package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Message;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class MessageRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private MessageRepository messageRepository;

    @Test
    public void findByMessageId_success() {
        // create list of cards
        List<Card> emptyList = new ArrayList<>();

        // create User
        User user = new User();
        user.setName("Firstname Lastname");
        user.setUsername("firsty");
        user.setEmail("first@email.com");
        user.setPassword("password");
        user.setStatus(UserStatus.OFFLINE);
        user.setToken("1");

        entityManager.persist(user);
        entityManager.flush();

        // create setPostDTO
        Game game = new Game();
        game.setStatus(GameStatus.OPEN);
        game.setInviter(user);
        game.setPlaySetId(1L);
        game.setPlayCards(new ArrayList<>());
        game.setPlayers(Collections.singletonList(user));
        game.setCountDown(false);
        game.setTimer(10L);

        entityManager.persist(game);
        entityManager.flush();

        // create Message
        Message message = new Message();
        message.setTimeStamp(LocalDateTime.now());
        message.setMessage("message");
        message.setCardId(1L);
        message.setScore(10L);
        message.setSenderId(1L);

        entityManager.persist(message);
        entityManager.flush();

        // when
        Message found = messageRepository.findByMessageId(message.getMessageId()).get();

        // then
        assertNotNull(found.getMessageId());
        assertEquals(found.getMessage(), message.getMessage());
        assertEquals(found.getCardId(), message.getCardId());
        assertEquals(found.getScore(), message.getScore());
        assertEquals(found.getSenderId(), message.getSenderId());
    }


}
