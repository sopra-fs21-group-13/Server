package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class SetRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SetRepository setRepository;

    @Test
    public void findBySetId_success() {
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
        Set set = new Set();
        set.setTitle("title");
        set.setUser(user);
        set.setCards(emptyList);
        set.setSetCategory(SetCategory.BIOLOGY);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setExplain("explain");
        set.setPhoto("photo");

        entityManager.persist(set);
        entityManager.flush();

        // when
        Set found = setRepository.findBySetId(set.getSetId()).get();

        // then
        assertNotNull(found.getSetId());
        assertEquals(found.getTitle(), set.getTitle());
        assertEquals(found.getUser(), set.getUser());
        assertEquals(found.getCards(), set.getCards());
        assertEquals(found.getSetCategory(), set.getSetCategory());
        assertEquals(found.getSetStatus(), set.getSetStatus());
        assertEquals(found.getExplain(), set.getExplain());
        assertEquals(found.getPhoto(), set.getPhoto());
    }

    @Test
    public void findBySetStatus_success() {
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
        Set set = new Set();
        set.setTitle("title");
        set.setUser(user);
        set.setCards(emptyList);
        set.setSetCategory(SetCategory.BIOLOGY);
        set.setSetStatus(SetStatus.PUBLIC);
        set.setExplain("explain");
        set.setPhoto("photo");

        entityManager.persist(set);
        entityManager.flush();

        // when
        List<Set> foundList = setRepository.findBySetStatus(set.getSetStatus());

        // then
        assertNotNull(foundList.get(0).getSetId());
        assertEquals(foundList.get(0).getTitle(), set.getTitle());
        assertEquals(foundList.get(0).getUser(), set.getUser());
        assertEquals(foundList.get(0).getCards(), set.getCards());
        assertEquals(foundList.get(0).getSetCategory(), set.getSetCategory());
        assertEquals(foundList.get(0).getSetStatus(), set.getSetStatus());
        assertEquals(foundList.get(0).getExplain(), set.getExplain());
        assertEquals(foundList.get(0).getPhoto(), set.getPhoto());
    }


}
