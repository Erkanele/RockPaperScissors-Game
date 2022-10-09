package com.rockpaperscissior.erik.Model;
import java.util.UUID;

/**
 * Class that have the game logics for the game
 */

public class Game {

        private UUID gameID;
        private State gameState;
        private Player player1;
        private Player player2;

        public Game() {

                this.gameID = generateUUID();
        }

        public UUID generateUUID() {
                return UUID.randomUUID();
        }

        public UUID getGameID() {
                return gameID;
        }

        public void setPlayer1(Player player1) {
                this.player1 = player1;
        }

        public void setPlayer2(Player player2) {
                this.player2 = player2;
        }

        public Player getPlayer2(){
                return player2;
        }

        public void setGameState(State gameState) {
                this.gameState = gameState;
        }

        public State getGameState() {
                return gameState;
        }


        /**
         * Method to Create a String representation of current game status
         * @return String representation of the current game state
         */

        public String gameString() {
                return  "GameID: " + gameID +
                        "\nGame State: " + gameState +
                        "\nPlayer 1: " + player1.getName().substring(0,1).toUpperCase() + player1.getName().substring(1) +
                        "\nPlayer 1 Status: " + player1.getState() +
                        "\nPlayer 1 Move: " + player1.getMove() +
                        "\nPlayer 2: " + player2.getName().substring(0,1).toUpperCase() + player2.getName().substring(1) +
                        "\nPlayer 2 Status: " + player2.getState() +
                        "\nPlayer 2 Move: " + player2.getMove() +
                        "\nResult: " + "Player 1 - " + player1.getResult() + ", Player 2 - " + player2.getResult() + "\n";

        }
        /**
         * Method to Create a String representation of current player status
         * @return String representation of the player status
         */

        public String playerString() {
                return "GameID: " + gameID +
                        "\nGame State: " + gameState +
                        "\nPlayer 1: " + player1.getName().substring(0,1).toUpperCase() + player1.getName().substring(1) +
                        "\nPlayer 1 Status: " + player1.getState() +
                        "\nPlayer 2: " + player2.getName().substring(0,1).toUpperCase() + player2.getName().substring(1) +
                        "\nPlayer 2 Status: " + player2.getState() +
                        "\nResult: " + "Player 1 - " + player1.getResult() + ", Player 2 - " + player2.getResult() + "\n";

        }

        /**
         * Method to Evaluate the moves of both players and set the result accordingly.
         * @param player1 Checking what move Player 1 has made and set the game result accordingly
         * @param player2 Checking what move Player 2 has made and set the game result accordingly
         */
        public void evaluateMoves(Player player1, Player player2) {

                if (player1.getMove() == Move.DEFAULT || player2.getMove() == Move.DEFAULT) {
                        player1.setResult(Result.WAITING);
                        player2.setResult(Result.WAITING);
                }
                else if (player1.getMove() == player2.getMove() && player1.getMove() != Move.DEFAULT )  {
                        player1.setResult(Result.DRAW);
                        player2.setResult(Result.DRAW);
                }

                else if (player1.getMove().winsOver(player2.getMove())) {
                        player1.setResult(Result.WIN);
                        player2.setResult(Result.LOSE);
                }
                else if (player2.getMove().winsOver(player1.getMove())) {
                        player2.setResult(Result.WIN);
                        player1.setResult(Result.LOSE);
                }
        }
        /**
         * Method to evaluates the status of the game. If both players made their moves the status is
         * set to ENDED.
         */
        public void evaluatePlayerStatesAndSetGameState(){
                if (player1.getState() == State.ENDED && player2.getState() == State.ENDED){
                        gameState = State.ENDED;
                }
        }
}
