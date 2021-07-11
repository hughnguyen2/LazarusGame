package GameLazTermProject;


import java.awt.event.KeyEvent;
import java.util.Observable;


public class Notification extends Observable {

  
    public Object eObject;

    public void setValue(KeyEvent event1) {
              
        eObject = event1;
        setChanged();        
        notifyObservers(this);
    }

  
}


