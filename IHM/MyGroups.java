package IHM;

import javafx.scene.Parent;

/**
 *  Classe MyGroups qui hérite de Parent
 *  MyGroups est une classe intermédiare dans l'héritage entre les IHM et Parent 
 *
 * @author ludo
 * @version 1.0
 */
public class MyGroups extends Parent { 
//MyGroups est une classe intermédiare dans l'héritage entre les IHM et Parent 

    protected MainIHM mainApp;

    public MyGroups(MainIHM mainApp) {
        this.mainApp = mainApp;
    }

}
