package GameLazTermProject;


import GameLazTermProject.Game.GameObject;

import java.awt.image.BufferedImage;
import java.util.Observable;
import java.util.Observer;


public class endGameStopButton extends GameObject implements Observer {

    public endGameStopButton(BufferedImage[] sprite) {

        super(sprite);
    }
    
    public endGameStopButton(BufferedImage[] sprite, int x, int y) {
        super(sprite);
        xCoordinateSet(x);
        yCoordinateSet(y);
    }
    
    @Override
    public void update(Observable o, Object arg) {}
    
}

