package com.rockpaperscissior.erik.Model;

/**
 * Representation of a player
 */

public class Player {

        private String name;
        private Move move;
        private State state;
        private Result result;

        public Player(String name) {
                this.name = name;
        }

        public String getName() {
                return name;
        }

        public Move getMove() {
                return move;
        }

        public void setMove(Move move) {
                this.move = move;
        }
        public State getState () {
                return state;
        }
        public void setState(State state) {
                this.state = state;
        }

        public Result getResult() {
                return result;
        }

        public void setResult(Result result) {
                this.result = result;
        }
}
