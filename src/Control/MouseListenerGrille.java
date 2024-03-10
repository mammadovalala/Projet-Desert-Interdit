package Control;

import Modele.MGrille;
import Modele.Zones.MZone;
import Modele.Zones.Piste;
import Modele.Zones.ZoneExplorable;
import Vue.VGrille;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerGrille implements MouseListener {
    final MGrille modele;
    final VGrille vue;
    public MouseListenerGrille(MGrille modele, VGrille vue) {
        this.modele = modele;
        this.vue = vue;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if(!modele.cestLaFinDeJeu()) {
            MZone cible = modele.getZone(e.getX() / (vue.cote + vue.decallage),
                                         e.getY() / (vue.cote + vue.decallage));

            if (e.getButton() == MouseEvent.BUTTON1) {
                //Clic gauche
                if(cible.getX() == modele.getJoueurActuel().getX() &&
                        cible.getY() == modele.getJoueurActuel().getY()) {
                    if(cible instanceof Piste) {
                        modele.pose();
                    } else {
                        try {
                            modele.ramasse();
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                } else if (((ZoneExplorable) modele.getJoueurActuel().getZoneActuel()).getSable() < 2)
                    modele.deplacement(cible);
            } else if (e.getButton() == MouseEvent.BUTTON3) {
                //Clic droit
                modele.retireSable(cible);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
