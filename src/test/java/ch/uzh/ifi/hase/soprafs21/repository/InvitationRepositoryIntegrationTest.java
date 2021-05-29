package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Invitation;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class InvitationRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InvitationRepository invitationRepository;

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
        Invitation invitation = new Invitation();
        invitation.setGameId(1L);
        invitation.setSentFromId(1L);
        invitation.setSentFromUserName("user");
        invitation.setSetTitle("title");

        entityManager.persist(invitation);
        entityManager.flush();

        // when
        Invitation found = invitationRepository.findByInvitationId(invitation.getInvitationId()).get();

        // then
        assertNotNull(found.getInvitationId());
        assertEquals(found.getGameId(), invitation.getGameId());
        assertEquals(found.getSentFromId(), invitation.getSentFromId());
        assertEquals(found.getSentFromUserName(), invitation.getSentFromUserName());
        assertEquals(found.getSetTitle(), invitation.getSetTitle());
    }


}
