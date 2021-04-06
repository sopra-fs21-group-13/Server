package ch.uzh.ifi.hase.soprafs21.rest.mapper;

import ch.uzh.ifi.hase.soprafs21.entity.Card;
import ch.uzh.ifi.hase.soprafs21.entity.Set;
import ch.uzh.ifi.hase.soprafs21.entity.User;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.SetPostDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserGetDTO;
import ch.uzh.ifi.hase.soprafs21.rest.dto.UserPostDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
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

    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "password", target = "password")
    //@Mapping(source = "id", target = "id")
    User convertUserPostDTOtoEntity(UserPostDTO userPostDTO);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "username", target = "username")
    @Mapping(source = "status", target = "status")
    UserGetDTO convertEntityToUserGetDTO(User user);



    @Mapping(source = "setName", target = "setName")
    @Mapping(source = "user", target = "user", qualifiedByName = "User") // Custom Mapper with an Annotation for the card array
    @Mapping(source = "cards", target = "cards", qualifiedByName = "Card") // Custom Mapper with an Annotation for the card array
    @Mapping(source = "setCategory",target = "setCategory")
    @Mapping(source = "setStatus",target = "setStatus")
    Set convertSetPostDTOtoEntity(SetPostDTO setPostDTO);


    @Named("User")
    default User jsonToUser(String userString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert Json array to user entity
        User user = mapper.readValue(userString, User.class );
        return user;
    }

    @Named("Card")
    default List<Card> jsonToCardsI(String cardsString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        // Convert Json array to List of Card entities
        List<Card> cardEntities = Arrays.asList(mapper.readValue(cardsString, Card[].class));
        return cardEntities;
    }

    // Same as Named just hardcoded
 /*
    @CardCreatorMapper
    default List<Card> jsonToCards(String cardsString) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
            // Convert Json array to List of Card entities
            List<Card> cardEntities = Arrays.asList(mapper.readValue(cardsString, Card[].class));

            return cardEntities;
    }

*/

    // Not configured yet !!!
    @Mapping(source = "setId", target = "setId")
    @Mapping(source = "setName", target = "setName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.username",target = "username" )
    @Mapping(source = "cards", target = "cards")
    @Mapping(source = "setOrder",target = "setOrder")
    @Mapping(source = "setCategory",target = "setCategory")
    @Mapping(source = "setStatus",target = "setStatus")
    SetGetDTO convertEntityToSetGetDTO(Set set);


}
