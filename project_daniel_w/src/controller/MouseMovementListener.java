package controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class MouseMovementListener implements MouseMotionListener {

    public static float mouseX;
    public static float mouseY;
    
    @Override
    public void mouseDragged(MouseEvent me) {
        
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        mouseX = me.getX();
        mouseY = me.getY();
    }
    
}
