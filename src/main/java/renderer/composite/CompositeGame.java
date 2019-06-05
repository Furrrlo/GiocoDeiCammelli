package renderer.composite;

import me.palla.BaseGame;
import me.palla.renderer.RenderManager;

import java.util.Map;

public class CompositeGame extends BaseGame<CompositeRenderManager> {

    public CompositeGame(Map<RenderManager, Class<?>> renderManagersToContext) {
        super(new CompositeRenderManager(renderManagersToContext));
    }
}
