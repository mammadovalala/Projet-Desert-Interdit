package Vue;

import Control.BoutonDonnerEau;
import Control.BoutonExplore;
import Control.BoutonFinDeTour;
import Modele.MGrille;

import javax.swing.*;
import java.awt.*;

public class VControleur extends JPanel {
    MGrille modele;

    public VControleur(MGrille modele) {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(new Color(77, 38, 0));

        JButton boutonFDT = new JButton("Fin de tour");
        this.add(boutonFDT);
        BoutonFinDeTour ctrlFDT = new BoutonFinDeTour(modele);
        boutonFDT.addActionListener(ctrlFDT);

        JButton boutonExplore = new JButton("Explore");
        this.add(boutonExplore);
        BoutonExplore ctrlExplore = new BoutonExplore(modele);
        boutonExplore.addActionListener(ctrlExplore);

        JButton boutonPartageEau = new JButton("Partager sa gourde");
        this.add(boutonPartageEau);
        BoutonDonnerEau ctrlPartageEau = new BoutonDonnerEau(modele);
        boutonPartageEau.addActionListener(ctrlPartageEau);
    }
}
