import accelerometer.FakeAccellerometer;
import me.palla.norender.NoContext;
import me.palla.renderer.RenderManager;
import renderer.composite.CompositeGame;
import renderer.processing.ProcessingRenderManager;
import renderer.swing.SwingContext;
import renderer.swing.SwingRenderManager;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String... args) {
//        ProcessingGame game = new ProcessingGame();
//        SwingGame game2 = new SwingGame();

        final Map<RenderManager, Class<?>> renderManagersToContext = new HashMap<>();
        renderManagersToContext.put(new SwingRenderManager(), SwingContext.class);
        renderManagersToContext.put(ProcessingRenderManager.createInstance(), NoContext.class);
        CompositeGame game = new CompositeGame(renderManagersToContext);

        new FakeAccellerometer(game).setVisible(true);
    }
}
