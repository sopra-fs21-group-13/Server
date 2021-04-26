package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.Settings;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;
import java.util.List;

/**
 * DTOMapper
 * This class is responsible for generating classes that will automatically transform/map the internal representation
 * of an entity (e.g., the User) to the external/API representation (e.g., UserGetDTO for getting, UserPostDTO for creating)
 * and vice versa.
 * Additional mappers can be defined for new entities.
 * Always created one mapper for getting information (GET) and one mapper for creating information (POST).
 */

@Mapper
public interface DTOMapper {

    DTOMapper INSTANCE = Mappers.getMapper(DTOMapper.class);

// User Mappings

    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "email", target = "email")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    //Get Mapping for user with learn sets
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "learnSets", target = "learnSets")
    @Mapping(source = "token", target = "token")
    @Mapping(source = "email", target = "email")
    UserGetDTO convertEntityToUserGetDTO(User user);



// Set Mappings

    @Mapping(source = "title", target = "title")
    @Mapping(source = "user", target = "user", qualifiedByName = "User") // Custom Mapper with an Annotation for the card array
    @Mapping(source = "cards", target = "cards", qualifiedByName = "Card") // Custom Mapper with an Annotation for the card array
    @Mapping(source = "setCategory",target = "setCategory")
    @Mapping(source = "setStatus",target = "setStatus")
    @Mapping(source = "explain",target = "explain")
    @Mapping(source = "photo",target = "photo")
    Set convertSetPostDTOtoEntity(SetPostDTO setPostDTO);

    // Qualifier for handling nested json and convert it into entity user
    @Named("User")
    default User jsonToUser(String userString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert Json array to user entity
        User user = mapper.readValue(userString, User.class );
        return user;
    }

    //Qualifier for handling json array -> conversion into list of objects
    @Named("Card")
    default List<Card> jsonToCardsI(String cardsString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert Json array to List of Card entities
        List<Card> cardEntities = Arrays.asList(mapper.readValue(cardsString, Card[].class));
        return cardEntities;
    }


    @Mapping(source = "setId", target = "setId")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "user", target = "userId")
    @Mapping(source = "cards", target = "cards")
    @Mapping(source = "setCategory",target = "setCategory")
    @Mapping(source = "setStatus",target = "setStatus")
    @Mapping(source = "explain",target = "explain")
    @Mapping(source = "photo",target = "photo")
    SetGetDTO convertEntityToSetGetDTO(Set set);


// Settings Mappings

    @Mapping(source = "settingsId", target = "settingsId")
    @Mapping(source = "userID", target = "userID")
    @Mapping(source = "setID", target = "setID")
    @Mapping(source = "cardsShuffled", target = "cardsShuffled")
    @Mapping(source = "studyStarred", target = "studyStarred")
    @Mapping(source = "lastCard", target = "lastCard")
    @Mapping(source = "savedOrder", target = "savedOrder")
    @Mapping(source = "markedCards", target = "markedCards")
    Settings convertSettingsPostDTOtoEntity(SettingsPostDTO settingsPostDTO);

    @Mapping(source = "settingsId", target = "settingsId")
    @Mapping(source = "userID", target = "userID")
    @Mapping(source = "setID", target = "setID")
    @Mapping(source = "cardsShuffled", target = "cardsShuffled")
    @Mapping(source = "studyStarred", target = "studyStarred")
    @Mapping(source = "lastCard", target = "lastCard")
    @Mapping(source = "savedOrder", target = "savedOrder")
    @Mapping(source = "markedCards", target = "markedCards")
    SettingsGetDTO convertEntityToSettingsGetDTO(Settings settings);

}
