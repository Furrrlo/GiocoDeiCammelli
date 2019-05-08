import me.palla.GiocoPalla;
import processing.core.PApplet;

public class Main {

    public static void main(String... args) {
        PApplet.main(GiocoPalla.class);
        new FakeAccellerometer().setVisible(true);
    }
}
