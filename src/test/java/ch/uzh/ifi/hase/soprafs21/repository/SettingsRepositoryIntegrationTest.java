package ch.uzh.ifi.hase.soprafs21.repository;

import ch.uzh.ifi.hase.soprafs21.constant.SetCategory;
import ch.uzh.ifi.hase.soprafs21.constant.SetStatus;
import ch.uzh.ifi.hase.soprafs21.constant.UserStatus;
import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
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
public class SettingsRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private SettingsRepository settingsRepository;


    @Test
    public void findByUserIDAndSetID_success() {
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        entityManager.persist(settings);
        entityManager.flush();

        // when
        Settings found = settingsRepository.findByUserIDAndSetID(settings.getUserID(),settings.getSetID());

        // then
        assertNotNull(found.getSettingsId());
        assertEquals(found.getSetID(), settings.getSetID());
        assertEquals(found.getUserID(), settings.getUserID());
        assertEquals(found.getCardsShuffled(), settings.getCardsShuffled());
        assertEquals(found.getStudyStarred(), settings.getStudyStarred());
        assertEquals(found.getLastCard(), settings.getLastCard());
        assertEquals(found.getSavedOrder(), settings.getSavedOrder());
        assertEquals(found.getMarkedCards(), settings.getMarkedCards());
    }

    @Test
    public void findAllBySetID_success() {
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        entityManager.persist(settings);
        entityManager.flush();

        // create second settings
        Settings settings2 = new Settings();
        settings2.setSetID(1L);
        settings2.setUserID(0l);
        settings2.setCardsShuffled(false);
        settings2.setStudyStarred(false);
        settings2.setLastCard(0L);
        settings2.setSavedOrder(cardOrder);
        settings2.setMarkedCards(cardOrder);

        entityManager.persist(settings2);
        entityManager.flush();

        // when
        List<Settings> foundList = settingsRepository.findAllBySetID(1L);

        // then
        assertNotNull(foundList.get(0).getSettingsId());
        assertEquals(foundList.get(0).getSetID(), settings2.getSetID());
        assertEquals(foundList.get(0).getUserID(), settings2.getUserID());
        assertEquals(foundList.get(0).getCardsShuffled(), settings2.getCardsShuffled());
        assertEquals(foundList.get(0).getStudyStarred(), settings2.getStudyStarred());
        assertEquals(foundList.get(0).getLastCard(), settings2.getLastCard());
        assertEquals(foundList.get(0).getSavedOrder(), settings2.getSavedOrder());
        assertEquals(foundList.get(0).getMarkedCards(), settings2.getMarkedCards());
    }

    @Test
    public void existsByUserIDAndSetID_success() {
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        entityManager.persist(settings);
        entityManager.flush();

        // when
        Boolean found = settingsRepository.existsByUserIDAndSetID(settings.getUserID(),settings.getSetID());

        // then
        assertEquals(found, true);
    }

    @Test
    public void existsByUserIDAndSetID_fail() {
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        entityManager.persist(settings);
        entityManager.flush();

        // when
        Boolean found = settingsRepository.existsByUserIDAndSetID(settings.getUserID(), 1L);

        // then
        assertEquals(found, false);
    }

    @Test
    public void deleteBySetID_success(){
        // create cardOrderList
        ArrayList<Long> cardOrder = new ArrayList<>();
        cardOrder.add(0L);
        cardOrder.add(1L);

        // create settings
        Settings settings = new Settings();
        settings.setSetID(0L);
        settings.setUserID(0l);
        settings.setCardsShuffled(false);
        settings.setStudyStarred(false);
        settings.setLastCard(0L);
        settings.setSavedOrder(cardOrder);
        settings.setMarkedCards(cardOrder);

        entityManager.persist(settings);
        entityManager.flush();

        // when
        settingsRepository.deleteBySetID(settings.getSetID());
        Boolean found = settingsRepository.existsById(0L);

        // then
        assertEquals(found, false);
    }
}
