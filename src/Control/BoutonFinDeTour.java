package Control;

import Modele.MGrille;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonFinDeTour implements ActionListener {
    final MGrille modele;

    public BoutonFinDeTour(MGrille modele) {
        super();
        this.modele = modele;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!modele.cestLaFinDeJeu()) modele.finDeTour();
    }
}
