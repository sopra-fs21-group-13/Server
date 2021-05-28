package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
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
public class GameRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private GameRepository gameRepository;

    @Test
    public void findByGameId_success() {
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

        // when
        Game found = gameRepository.findByGameId(game.getGameId()).get();

        // then
        assertNotNull(found.getGameId());
        assertEquals(found.getStatus(), game.getStatus());
        assertEquals(found.getInviter(), game.getInviter());
        assertEquals(found.getPlaySetId(), game.getPlaySetId());
        assertEquals(found.getPlayers(), game.getPlayers());
        assertEquals(found.getCountDown(), game.getCountDown());
        assertEquals(found.getTimer(), game.getTimer());
    }

}
