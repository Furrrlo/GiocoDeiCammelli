package me.palla.renderer;

public interface Renderer<T> {
    void onRender(RenderContext ctx, T toRender);

    default void onResize(T toRender) {
    }
}
