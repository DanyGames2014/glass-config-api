package net.glasslauncher.mods.api.gcapi.screen;

public interface HasDrawable {

    void draw();
    void mouseClicked(int mouseX, int mouseY);
    void setXYWH(int x, int y, int width, int height);
    void tick();
    void keyPressed(char character, int key);
}
