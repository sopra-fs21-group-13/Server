package ch.uzh.ifi.hase.soprafs21.controller;

import ch.uzh.ifi.hase.soprafs21.entity.Game;
import ch.uzh.ifi.hase.soprafs21.entity.Invitation;
import ch.uzh.ifi.hase.soprafs21.entity.Message;
import ch.uzh.ifi.hase.soprafs21.rest.dto.*;
import ch.uzh.ifi.hase.soprafs21.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs21.service.GameService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

/**
 * Game Controller
 * This class is responsible for handling all REST request that are related to the game.
 * The controller will receive the request and delegate the execution to the GameService and finally return the result.
 */


@RestController
public class GameController {

    private final GameService gameService;

    GameController(GameService gameService) {

        this.gameService = gameService;
    }


// Get Mapping

    @GetMapping("/games")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<GameGetDTO> getAllGames() {
        // fetch all users in the internal representation
        List<Game> games = gameService.getAllGames();
        List<GameGetDTO> gameGetDTOs = new ArrayList<>();

        // convert each user to the API representation
        for (Game game : games) {
            gameGetDTOs.add(DTOMapper.INSTANCE.convertEntityToGameGetDTO(game));
        }
        return gameGetDTOs;
    }

    @GetMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public GameGetDTO getGame(@PathVariable Long gameId) {
        // fetch all users in the internal representation
        Game game = gameService.getGameByGameID(gameId);

        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }


// Post Mapping

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public GameGetDTO createGame(@RequestBody GamePostDTO gamePostDTO) {
        // convert API game to internal representation
        Game gameInput = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);

        // create user
        Game createdGame = gameService.createGame(gameInput);

        // convert internal representation of user back to API
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(createdGame);
    }

    @PostMapping("/games/invitations")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public InvitationGetDTO createInvitation(@RequestBody InvitationPostDTO invitationPostDTO){
        // convert API invitation to internal representation
        Invitation invitation = DTOMapper.INSTANCE.convertInvitationPostDTOToEntity(invitationPostDTO);
        // Create the invitation
        Invitation newInvitation = gameService.createInvitation(invitation);
        //Return InvitationGetDTO
        return DTOMapper.INSTANCE.convertEntityToInvitationGetDTO(newInvitation);
    }


// Put Mapping

    //Normal update
    @PutMapping ("/games")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameGetDTO updateGame(@RequestBody GamePostDTO gamePostDTO) {
        Game game;
        if (!isNull(gamePostDTO.getGameId())) {
            game = DTOMapper.INSTANCE.convertGamePostDTOtoEntity(gamePostDTO);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No GameId is given!");
        }
        Game updateGame = gameService.updateGame(game);

        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(updateGame);
    }

    // Add Message to History
    @PutMapping("/games/{gameId}/histories")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public GameGetDTO addMessageToHistory(@PathVariable("gameId") Long gameId, @RequestBody MessagePostDTO messagePostDTO){

        Message message = DTOMapper.INSTANCE.convertMessagePostDTOtoEntity(messagePostDTO);
        // Add Message to Game History
        Game newGame = gameService.addMessageToHistory(gameId, message);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(newGame);
    }

    // Add Player to Game
    @PutMapping("/games/{gameId}/{userId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameGetDTO addMessageToHistory(@PathVariable("gameId") Long gameId, @PathVariable("userId") Long userId){
        // Add Message to Game History
        Game game = gameService.addPlayerToGame(gameId, userId);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }

    // Remove Player from Game
    @PutMapping("/games/{gameId}/{userId}/remover")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public GameGetDTO removePlayerFromGame(@PathVariable("gameId") Long gameId, @PathVariable("userId") Long userId){
        // Add Message to Game History
        Game game = gameService.removePlayerFromGame(gameId, userId);
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }


// Delete Mapping

    // Delete a specific User
    @DeleteMapping("/games/{gameId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteGame(@PathVariable Long gameId){
        gameService.deleteGame(gameId);
    }


    // Delete Invitation
    @DeleteMapping("/games/invitations/{invitationId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteInvitation(@PathVariable Long invitationId) {gameService.deleteInvitation(invitationId);}

}
