package com.rockpaperscissior.erik.Controller;


import com.rockpaperscissior.erik.Model.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Map;
import java.util.UUID;

/**
 * Controller class for the RockPaperScissorsApplication that handles all the requests
 * in the game.
 */
@RequestMapping("/*")
@Tag(name = "Rock, Paper Scissors API", description = "API Game for Rock, Paper Scissor")
@RestController
public class GameController {

        public Game game;
        public Player player1;
        public Player player2;
        public String gameIDNotFound = " The Game ID entered was not found, try again please. ";


        /**
         * POST-request method for a player to create a game, game ID will be generated.
         * @param body request body
         * @return player name and game id
         */

        @PostMapping("/api/games")
        public ResponseEntity<String> newGame(@RequestBody Map<String, String> body) {

                player1 = new Player(body.get("name"));
                player1.setMove(Move.DEFAULT);
                player1.setState(State.ONGOING);
                player1.setResult(Result.WAITING);
                game = new Game();
                game.setPlayer1(player1);


                return ResponseEntity.status(HttpStatus.CREATED).body("Player 1 joined: " + player1.getName() + " " + game.getGameID() + "\n");
        }

        /**
         * POST-request method for a player to join a game with game ID.
         * @param id game ID
         * @param body request body
         * @return player name
         */
        @PostMapping("/api/games/{id}/join")
        public ResponseEntity<String> joinGame(@PathVariable UUID id, @RequestBody Map<String, String> body) {

                player2 = new Player(body.get("name"));
                player2.setResult(Result.WAITING);
                player2.setMove(Move.DEFAULT);
                player2.setState(State.ONGOING);
                game.setGameState(State.STARTED);


                if(!id.equals(game.getGameID())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound + "\n");

                }
                game.setPlayer2(player2);
                return ResponseEntity.status(HttpStatus.OK).body("Player 2 joined: " + player2.getName()+ "\n");
        }

        /**
         * POST-request method for a player making a move.
         * @param id game ID
         * @param body request body
         * @return a player's name and move
         */
        @PostMapping("/api/games/{id}/move")
        public ResponseEntity<String> makeMove(@PathVariable UUID id, @RequestBody Map<String, String> body) {
                if(!id.equals(game.getGameID())) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound + "\n");
                }

                if (body.get("name").equals(player1.getName())) {
                        player1.setMove(Move.valueOf(body.get("move")));
                        player1.setState(State.ENDED);
                        game.setGameState(State.ONGOING);
                        return ResponseEntity.status(HttpStatus.OK).body("Player 1: " + player1.getName() + " made a move: " + player1.getMove().toString()+ "\n");

                }
                else if (body.get("name").equals(player2.getName())) {
                        player2.setMove(Move.valueOf(body.get("move")));
                        player2.setState(State.ENDED);
                        game.setGameState(State.ONGOING);
                        return ResponseEntity.status(HttpStatus.OK).body("Player 2: " + player2.getName() + " made a move " + player2.getMove().toString()+ "\n");
                }
                else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player is not part of this game"+ "\n");
                }
        }

        /**
         * GET-request method for the state of the game, each player and the result.
         * @param id game ID
         * @return Status of the game, player status and the result
         */
        @GetMapping("/api/games/{id}")
        public ResponseEntity<String> checkState(@PathVariable UUID id) {
                game.evaluatePlayerStatesAndSetGameState();
                if(!id.equals(game.getGameID())) {

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(gameIDNotFound + "\n");
                }
                else if (game.getPlayer2() == null){

                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Player: 2 needs to connect before any result is given"+ "\n");
                }
                else if (game.getGameState() != State.ENDED) {

                        return ResponseEntity.status(HttpStatus.OK).body(game.playersToString());
                }
                game.evaluateMoves(player1, player2);
                game.hasPlayersMadeMoves(player1, player2);


                return ResponseEntity.status(HttpStatus.OK).body(game.gameString());
        }
}