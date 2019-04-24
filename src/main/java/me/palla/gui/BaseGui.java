package me.palla.gui;

import me.palla.gui.components.GuiComponent;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseGui implements Gui {

    protected final List<GuiComponent> components = new ArrayList<>();

    @Override
    public void onRender() {
        components.forEach(GuiComponent::onRender);
    }

    @Override
    public void onClick(float xPos, float yPos) {

        GuiComponent clicked = null;
        for(int i = components.size() - 1; i >= 0; i--) {
            final GuiComponent component = components.get(i);

            if(component.intersects(xPos, yPos)) {
                component.onClick(xPos, yPos);
                clicked = component;
                break;
            }
        }
        // Bring the clicked component up so it gets
        // rendered before others
        if(clicked != null) {
            components.remove(clicked);
            components.add(clicked);
        }
    }

    @Override
    public void onGuiClose() {
    }

    @Override
    public void onResize() {
    }
}
