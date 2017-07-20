package es.projectalpha.pa.core.utils;

import lombok.Getter;
import lombok.Setter;

public enum GameState {

    LOBBY, COUNTDOWN, INGAME, FINISHED;

    @Getter
    @Setter
    private static GameState state;
}
