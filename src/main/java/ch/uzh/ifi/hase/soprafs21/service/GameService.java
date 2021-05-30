package ch.uzh.ifi.hase.soprafs21.service;

import ch.uzh.ifi.hase.soprafs21.constant.GameStatus;
import ch.uzh.ifi.hase.soprafs21.entity.*;
import ch.uzh.ifi.hase.soprafs21.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Set Service
 * This class is the "worker" and responsible for all functionality related to the flashcard set
 * (e.g., it creates, modifies, deletes, finds). The result will be passed back to the caller.
 */

@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);
    private final SetRepository setRepository;
    private final UserRepository userRepository;
    private final GameRepository gameRepository;
    private final MessageRepository messageRepository;
    private final InvitationRepository invitationRepository;
    private final GameSettingRepository gameSettingRepository;

    @Autowired
    public GameService(@Qualifier("setRepository") SetRepository setRepository, @Qualifier("userRepository") UserRepository userRepository, @Qualifier("gameRepository") GameRepository gameRepository,@Qualifier("messageRepository") MessageRepository messageRepository,
                       @Qualifier("invitationRepository") InvitationRepository invitationRepository, @Qualifier("gameSettingRepository") GameSettingRepository gameSettingRepository) {
        this.setRepository = setRepository;
        this.userRepository = userRepository;
        this.gameRepository = gameRepository;
        this.messageRepository = messageRepository;
        this.invitationRepository = invitationRepository;
        this.gameSettingRepository = gameSettingRepository;
    }

    // Get all sets available -> not useful though
    public List<Game> getAllGames() {
        return this.gameRepository.findAll();
    }

    // Get set by setId
    public Game getGameByGameID(Long gameId){
        Optional<Game> checkGame = gameRepository.findByGameId(gameId);
        if (checkGame.isPresent()){
            return checkGame.get();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ain't no game with gameId");
        }
    }

    // Create a flashcard set
    public Game createGame(Game newGame){

        if (checkGame(newGame)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Game is Empty");
        }
        newGame.setStatus(GameStatus.OPEN);

        // Create empty Player List
        List<User> initialPlayers = new ArrayList<>();
        newGame.setPlayers(initialPlayers);

        // Create empty History List
        List<Message> emptyHistory = new ArrayList<>();
        newGame.setHistory(emptyHistory);

        // Add Creator to the InviterList for proper updating
        newGame.getPlayers().add(newGame.getInviter());

        //set Timer
        newGame.setTimer(30L);

        //Add GameSettings to Repo
        GameSetting gameSetting = gameSettingRepository.save(newGame.getGameSettings());
        gameSettingRepository.flush();
        newGame.setGameSettings(gameSetting);

        // saves the given entity but data is only persisted in the database once flush() is called
        newGame = gameRepository.save(newGame);
        gameRepository.flush();

        //Logger
        log.debug("Created Information for Game: {}", newGame);
        return newGame;
    }

    // Check for completeness of set
    public boolean checkGame(Game game){
        if (isNull(game.getInviter())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game has no Inviter");
        } if ( isNull(game.getPlaySetId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game has no PlaySetId");
        } if (isNull(game.getCountDown())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Countdown is not set");
        }
        return false;
    }

    // Edit a Game
    public Game updateGame(Game game){
        Game updatedGame = getGameByGameID(game.getGameId());

        if (game.getStatus() != null) {
                updatedGame.setStatus(game.getStatus());
        }
        if (game.getPlayCards() != null) {
                updatedGame.setPlayCards(game.getPlayCards());
        }
        if (game.getPlayers() != null) {
                updatedGame.setPlayers(game.getPlayers());
        }
        if (game.getCountDown() != null) {
                updatedGame.setCountDown(game.getCountDown());
        }
        if (game.getTimer() != null) {
            updatedGame.setTimer(game.getTimer());
        }

        if (game.getPlayer1Ready() != null) {
            updatedGame.setPlayer1Ready(game.getPlayer1Ready());
        }

        if (game.getPlayer2Ready() != null) {
            updatedGame.setPlayer2Ready(game.getPlayer2Ready());
        }

        if (game.getPlayer1Score() != null) {
            updatedGame.setplayer1Score(game.getPlayer1Score());
        }

        if (game.getPlayer2Score() != null) {
            updatedGame.setplayer2Score(game.getPlayer2Score());
        }

        // save & flush
        updatedGame = gameRepository.save(updatedGame);
        gameRepository.flush();

        return updatedGame;
    }

    // Add Message to History
    public Game addMessageToHistory(Long gameId, Message message){
        // Get Game by gameId
        Optional<Game> checkGame = gameRepository.findByGameId(gameId);
        Game game = new Game();
        if (checkGame.isPresent()){
            game = checkGame.get();
        }

        // message save&flush
        message = messageRepository.save(message);
        messageRepository.flush();

        //Check whether senderId is in players
        Optional<User> checkUser = userRepository.findById(message.getSenderId());
        User user = new User();
        if(checkUser.isPresent()){
            user = checkUser.get();
        }
        if (!game.getPlayers().contains(user)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SenderId not in player list of game");
        }
        // Add Message to History
        game.getHistory().add(message);
        // Save & Flush the game
        game = gameRepository.save(game);
        gameRepository.flush();
        // return modified game
        return game;
    }

    // Add Players to Game
    public Game addPlayerToGame(Long gameId, Long userId){
        // Get Game by gameId
        Optional<Game> checkGame = gameRepository.findByGameId(gameId);
        Game game = new Game();
        if(checkGame.isPresent()){
            game = checkGame.get();
        }
        // Get User by userId
        Optional<User> checkUser = userRepository.findById(userId);
        User user = new User();
        if (checkUser.isPresent()){
            user = checkUser.get();
        }
        //Check if player is already in Game
        if(game.getPlayers().contains(user)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Player is already in that game");
        }
        // Add Message to History
        game.getPlayers().add(user);
        // return modified game
        return game;
    }

    // Remove Player from Game
    public Game removePlayerFromGame(Long gameId, Long userId){
        // Get Game by gameId
        Optional<Game> checkGame = gameRepository.findByGameId(gameId);
        Game game = new Game();
        if(checkGame.isPresent()){
            game = checkGame.get();
        }
        // Get User by userId
        Optional<User> checkUser = userRepository.findById(userId);
        User user = new User();
        if (checkUser.isPresent()){
            user = checkUser.get();
        }
        // Add Message to History

        /**
        if (user.equals(game.getInviter())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot remove creator from game");
        }else {
            game.getPlayers().remove(user);
        }
        */

        game.getPlayers().remove(user);
        // return modified game
        return game;
    }

    // Add the invitation to the repository
    public Invitation createInvitation(Invitation invitation){
        invitation = invitationRepository.save(invitation);
        invitationRepository.flush();
        return invitation;
    }

    // Delete a Game and its corresponding GameSetting
    public void deleteGame(Long gameId){
        Game game = getGameByGameID(gameId);
        invitationRepository.deleteByGameId(gameId);
        gameSettingRepository.deleteById(game.getGameSettings().getGameSettingId());
        gameRepository.deleteById(gameId);
    }

    // Delete a invitation by its id (when game is closed)
    public void deleteInvitation(Long invitationId){
        invitationRepository.deleteByInvitationId(invitationId);
    }

}
