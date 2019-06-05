package renderer.processing;

import me.palla.BaseGame;

public class ProcessingGame extends BaseGame<ProcessingRenderManager> {

    public ProcessingGame() {
        super(ProcessingRenderManager.createInstance());
    }

    @Override
    public void shutdown() {
        renderManager.exit();
    }
}
