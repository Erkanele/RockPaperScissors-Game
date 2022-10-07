package com.rockpaperscissior.erik.Model;

import java.util.Arrays;

/**
 * Enum class that represent the possible moves in the game.
 */

public enum Move {

        ROCK("ROCK") {
                public boolean winsOver(Move move) {
                        return(SCISSORS == move);
                }
        },

        PAPER("PAPER") {
                public boolean winsOver(Move move) {
                        return(ROCK == move);
                }
        },

        SCISSORS("SCISSORS") {
                public boolean winsOver(Move move) {
                        return(PAPER == move);
                }

        },
        DEFAULT("DEFAULT") {

                public boolean winsOver(Move move) {
                        return(DEFAULT == move);}
        };

        private final String label;
        /**
         * Enum class that represent the possible moves in the game.
         */
        Move(String label) {
                this.label = label;
        }
        /**
         * Method to set a move to default so not any NPE is thrown.
         */
        public static Move defaultMove(String stringToMatch) {
                return Arrays.stream(Move.values()).filter(aEnum -> aEnum.label.equals(stringToMatch)).findFirst().orElse(Move.DEFAULT);
        }

        public abstract boolean winsOver(Move move);
}
