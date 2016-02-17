package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.Shooter;

public class KeyController implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Shooter shooter = (Shooter) Main.gameData.gameFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                shooter.translate(-1, 0);
                break;
            case KeyEvent.VK_RIGHT:
                shooter.translate(1, 0);
                break;
            case KeyEvent.VK_UP:
                shooter.translate(0, -1);
                break;
            case KeyEvent.VK_DOWN:
                shooter.translate(0, 1);
                break;
            case KeyEvent.VK_A:
                shooter.translate(-1, 0);
                break;
            case KeyEvent.VK_D:
                shooter.translate(1, 0);
                break;
            case KeyEvent.VK_W:
                shooter.translate(0, -1);
                break;
            case KeyEvent.VK_S:
                shooter.translate(0, 1);
                break;
            
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
