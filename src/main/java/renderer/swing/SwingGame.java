package renderer.swing;

import me.palla.BaseGame;

public class SwingGame extends BaseGame<SwingRenderManager> {

    public SwingGame() {
        super(new SwingRenderManager());
    }
}
