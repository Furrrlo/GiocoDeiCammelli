package renderer.composite;

import me.palla.Game;
import me.palla.GameReferencer;
import me.palla.norender.NoRenderGame;
import me.palla.renderer.RenderManager;
import me.palla.renderer.Renderer;
import me.palla.util.ScaledResolution;

import java.util.HashMap;
import java.util.Map;

public class CompositeRenderManager implements RenderManager, GameReferencer {

    private Game game;

    private final Map<RenderManager, Class<?>> renderManagersToContext;
    private final Map<Class<?>, Renderer<?>> rendererHashMap;

    private final RenderManager first;

    public CompositeRenderManager(Map<RenderManager, Class<?>> renderManagersToContext) {
        this.renderManagersToContext = new HashMap<>(renderManagersToContext);
        setGame(NoRenderGame.instance());

        this.first = !this.renderManagersToContext.isEmpty()
                ? renderManagersToContext.keySet().iterator().next()
                : null;
        this.rendererHashMap = new HashMap<>();
    }

    @Override
    public void setGame(Game game) {
        this.game = game;
        renderManagersToContext.keySet().stream()
                .filter(GameReferencer.class::isInstance)
                .map(GameReferencer.class::cast)
                .forEach(r -> r.setGame(game));
    }

    @Override
    public ScaledResolution getScaledResolution() {
        return first.getScaledResolution();
    }

    @Override
    public float getMouseX() {
//        for(RenderManager renderManager : renderManagersToContext.keySet()) {
//            final float mouseX = renderManager.getMouseX();
//            System.out.println(renderManager.getClass() + ": " + mouseX);
//            if(mouseX >= 0 && mouseX <= renderManager.getScaledResolution().getWidth())
//                return mouseX;
//        }
//        return -1;
        return first.getMouseX();
    }

    @Override
    public float getMouseY() {
//        for(RenderManager renderManager : renderManagersToContext.keySet()) {
//            final float mouseY = renderManager.getMouseY();
//            System.out.println(renderManager.getClass() + ": " + mouseY);
//            if(mouseY >= 0 && mouseY <= renderManager.getScaledResolution().getWidth())
//                return mouseY;
//        }
//        return -1;
        return first.getMouseY();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Renderer<? super T> getRendererFor(Class<T> classType) {
        final Renderer<? super T> renderer = (Renderer<? super T>) rendererHashMap.get(classType);
        if(renderer != null)
            return renderer;

        // Couldn't find it, make it

        final Map<Class<?>, Renderer<T>> renderers = new HashMap<>();
        for(Map.Entry<RenderManager, Class<?>> entry : renderManagersToContext.entrySet()) {
            final Renderer<T> toAdd = (Renderer<T>) entry.getKey().getRendererFor(classType);
            if(toAdd != null)
                renderers.put(entry.getValue(), toAdd);
        }

        final Renderer<? super T> newRenderer = new CompositeRenderer<>(this, renderers);
        rendererHashMap.put(classType, newRenderer);

        return newRenderer;
    }
}
