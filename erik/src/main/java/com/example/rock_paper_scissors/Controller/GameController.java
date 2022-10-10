package com.example.rock_paper_scissors.Controller;


import com.example.rock_paper_scissors.Model.*;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

/**
 * Controller class for the RockPaperScissorsApplication that handles all the requests
 * in the game.
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Rock Paper Scissors Game",
                version = "1.0",
                description = "Http Game for the classic Rock, Paper Scissors"
        )
)

@Tag(name = "Rock, Paper Scissors API", description = "API Game for Rock, Paper Scissor")
@Controller
public class GameController {

        public Game game;
        public Player player1;
        public Player player2;
        public String gameIDNotFound = " The Game ID entered was not found. Please try again. ";


        /**
         * POST-request method for a player to create a game, game ID will be generated.
         * @param body request body
         * @return player name and game id
         */
        @Operation(
                summary = "Create a new Game",
                description = "Fill in your name in the request body, press execute and a new game will be created and a GameID generated"
        )
        @PostMapping("/api/games")
        public ResponseEntity<String> createGame(@RequestBody Map<String, String> body) {

                player1 = new Player(body.get("name"));
                player1.setState(State.ONGOING);
                player1.setResult(Result.WAITING);
                game = new Game();
                game.setPlayer1(player1);


                return ResponseEntity.status(HttpStatus.CREATED).body("Player 1 joined: " + player1.getName()
                        .substring(0,1).toUpperCase() + player1.getName().substring(1)
                        + "\n GameID Generated: " + game.getGameID());
        }

        /**
         * POST-request method for a player to join a game with game ID.
         * @param id game ID
         * @param body request body
         * @return player name
         */
        @Operation(
                summary = "Join an existing game with GameID",
                description = "Fill in the generated GameID and add your name in the request body"
        )
        @PostMapping("/api/games/{id}/join")
        public ResponseEntity<String> joinGame(@PathVariable UUID id, @RequestBody Map<String, String> body) {

                player2 = new Player(body.get("name"));
                player2.setResult(Result.WAITING);
                player2.setState(State.ONGOING);
                game.setGameState(State.STARTED);


                if(!id.equals(game.getGameID())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound);

                }
                else if (body.get("name").equals(player1.getName())) {
                        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please choose another name "
                                + player1.getName().substring(0,1).toUpperCase() + player1.getName().substring(1)+ " is already taken");
                }
                game.setPlayer2(player2);
                return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined: " + player2.getName()
                        .substring(0,1).toUpperCase() + player2.getName().substring(1));
        }

        /**
         * POST-request method for a player making a move.
         * @param id game ID
         * @param body request body
         * @return a player's name and move
         */
        @Operation(
                summary = "Make a move in an existing game",
                description = "Fill in the generated GameID and fill in name and move in the request body"
        )
        @PostMapping("/api/games/{id}/move")
        public ResponseEntity<String> chooseMove(@PathVariable UUID id, @RequestBody Map<String, String> body) {
                if(!id.equals(game.getGameID())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound);
                }

                if (body.get("name").equals(player1.getName())) {
                        player1.setMove(Move.valueOf(body.get("move")));
                        player1.setState(State.ENDED);
                        game.setGameState(State.ONGOING);
                        return ResponseEntity.status(HttpStatus.OK).body("Player 1: " + player1.getName().substring(0,1)
                                .toUpperCase() + player1.getName().substring(1) + "\n" + player1.getName().substring(0,1).toUpperCase()
                                + player1.getName().substring(1) + " Picks: " + player1.getMove().toString());

                }
                else if (body.get("name").equals(player2.getName())) {
                        player2.setMove(Move.valueOf(body.get("move")));
                        player2.setState(State.ENDED);
                        game.setGameState(State.ONGOING);
                        return ResponseEntity.status(HttpStatus.OK).body("Player 2: " + player2.getName().substring(0,1)
                                .toUpperCase() + player2.getName().substring(1) + "\n" + player2.getName().substring(0,1).toUpperCase()
                                + player2.getName().substring(1) + " 2 Picks: " + player2.getMove().toString());
                }
                else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player name " + body.get("name")
                                + " is not part of this game");
                }
        }

        /**
         * GET-request method for the state of the game, each player and the result.
         * @param id game ID
         * @return Status of the game, player status and the result or a bad request return.
         */

        @Operation(
                summary = "Get the current status of the game",
                description = "Shows the current status or result of the Game"
        )
        @GetMapping("/api/games/{id}")
        public ResponseEntity<String> checkStatus(@PathVariable UUID id) {

                game.evaluatePlayerStatesAndSetGameState();

                if(!id.equals(game.getGameID())) {

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound);
                }
                else if (game.getPlayer2() == null){

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player: 2 needs to connect" +
                                " before any result is returned");
                }
                else if (game.getGameState() != State.ENDED) {

                        return ResponseEntity.status(HttpStatus.OK).body(game.playerString());
                }
                game.evaluateMoves(player1, player2);

                return ResponseEntity.status(HttpStatus.OK).body(game.gameString());
        }
}