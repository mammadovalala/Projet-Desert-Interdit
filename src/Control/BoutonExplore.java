package Control;

import Modele.MGrille;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoutonExplore implements ActionListener {
    final MGrille modele;

    public BoutonExplore(MGrille modele) {
        super();
        this.modele = modele;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!modele.cestLaFinDeJeu()) modele.explore();
    }
}
