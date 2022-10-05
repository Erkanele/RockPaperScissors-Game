package com.rockpaperscissior.erik.Model;

import java.util.Arrays;

/**
 * The allowed moves for a player
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

                public boolean winsOver(Move move) { return(DEFAULT == move);}
        };

        private final String label;

        Move(String label) {
                this.label = label;
        }
        public static Move defaultMove(String stringToMatch) {
                return Arrays.stream(Move.values()).filter(aEnum -> aEnum.label.equals(stringToMatch)).findFirst().orElse(Move.DEFAULT);
        }

        public abstract boolean winsOver(Move move);
}
