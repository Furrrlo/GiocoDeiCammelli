package me.palla.norender;

import me.palla.BaseGame;
import me.palla.entity.EntityManager;
import me.palla.input.InputManager;
import me.palla.value.ValueManager;

public class NoRenderGame extends BaseGame {

    private static final NoRenderGame INSTANCE = new NoRenderGame();

    private NoRenderGame() {
        super(
                new ValueManager(),
                new InputManager(),
                new EntityManager(),
                NoRenderManager.instance(),
                NoGui.instance()
        );
    }

    public static NoRenderGame instance() {
        return INSTANCE;
    }
}
