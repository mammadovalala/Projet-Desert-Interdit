package Desert_Interdit;

import Control.MouseListenerGrille;
import Modele.MGrille;
import Vue.VControleur;
import Vue.VGrille;
import Vue.VInformation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Main {
    public static Random random;
    static JFrame fenetre;
    static JPanel contenu;
    static MGrille modele;
    static VGrille vueGrille;
    static VControleur vueControleur;
    public final static int taille = 5;
    public static void main(String[] args) throws Exception {
        random = new Random();
        modele = new MGrille();
        initcontenu();
    }

    private static void initcontenu() {
        vueGrille = new VGrille(modele);
        vueControleur = new VControleur(modele);
        VInformation vueInfo = new VInformation(modele);
        MouseListenerGrille mouseListenerGrille = new MouseListenerGrille(modele, vueGrille);
        vueGrille.addMouseListener(mouseListenerGrille);
        fenetre = new JFrame("Desert Interdit");
        contenu = new JPanel();
        contenu.add(vueInfo);
        contenu.add(vueGrille);
        contenu.add(vueControleur);
        contenu.setLayout(new FlowLayout());
        fenetre.getContentPane().add(contenu);
        fenetre.pack();
        fenetre.setVisible(true);
        fenetre.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        contenu.setBackground(new Color(77, 38, 0));
    }
}
