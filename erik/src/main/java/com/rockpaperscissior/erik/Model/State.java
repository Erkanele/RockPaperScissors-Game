package com.rockpaperscissior.erik.Model;

import java.util.Arrays;

/**
 * The game states
 */

public enum State {

        STARTED("STARTED"),
        ONGOING("ONGOING"),
        ENDED("ENDED");

        private final String label;

        State(String label) {

                this.label = label;
        }

        public static State defaultState(String stringToMatch) {
                return Arrays.stream(State.values()).filter(aEnum -> aEnum.label.equals(stringToMatch)).findFirst().orElse(State.ONGOING);
        }
}
