package Vue;

import Modele.MGrille;
import Modele.Pieces;
import Tools.Observer;

import javax.swing.*;
import java.awt.*;

public class VInformation extends JPanel implements Observer {
    MGrille modele;
    JLabel tour;
    JLabel nbAction;
    JLabel info;
    JLabel objects;
    JLabel piecesTrouvees;

    public VInformation(MGrille modele) {
        this.modele = modele;
        tour = new JLabel();
        nbAction = new JLabel();
        info = new JLabel();
        objects = new JLabel();
        piecesTrouvees = new JLabel();
        //soif.setBackground(new Color(200, 200, 200));
        tour.setForeground(new Color(255, 255, 255));
        nbAction.setForeground(new Color(255, 255, 255));
        info.setForeground(new Color(255, 255, 255));
        objects.setForeground(new Color(255, 255, 255));
        piecesTrouvees.setForeground(new Color(255, 255, 255));
        setPreferredSize(
                new Dimension ((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5,
                              (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()*5/7));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        modele.addObserver(this);
        add(piecesTrouvees);
        add(tour);
        add(nbAction);
        add(info);
        add(objects);
        setBackground(new Color(77, 38, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        paintPiecesTrouvees();
        paintTour();
        paintNbAction();
        paintSoif(g);
        try {
            paintInfo();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        paintObjects();
    }

    private void paintPiecesTrouvees() {
        String res = "<html>Niveau de tempete : " + modele.getNiveauTempete() + "<br>Pièces posé sur la piste : <br>";
        for (Pieces p : modele.getPiecesTrouvees()) {
            res += p + "<br>";
        }
        res += "</html>";
        piecesTrouvees.setText(res);
    }

    private void paintObjects() {
        objects.setForeground(new Color(
                255 - 255 * modele.getJoueurActuel().getId()/MGrille.getNbJoueur(),
                255 * modele.getJoueurActuel().getId()/ MGrille.getNbJoueur(),
                128 + 128 * (modele.getJoueurActuel().getId() - MGrille.getNbJoueur()/2)/MGrille.getNbJoueur()/2));
        String res = "<html><font color='white'>Objet du</font> Joueur " + (modele.getNumeroTourJoueur() + 1) + " :<br>";
        for (Pieces p : modele.getJoueurActuel().getPiecesRamassees()) {
            res += "<font color='white'>" + p + "</font><br>";
        }
        res += "</html>";
        objects.setText(res);
    }

    private void paintInfo() throws Exception {
        if(modele.getPeutRamasser() != -1) info.setText("Tu peux ramasser " + Pieces.intToPiece(modele.getPeutRamasser()));
        else info.setText("");
    }

    private void paintTour() {
        tour.setForeground(new Color(
                255 - 255 * modele.getJoueurActuel().getId()/MGrille.getNbJoueur(),
                255 * modele.getJoueurActuel().getId()/ MGrille.getNbJoueur(),
                128 + 128 * (modele.getJoueurActuel().getId() - MGrille.getNbJoueur()/2)/MGrille.getNbJoueur()/2));
        tour.setText("<html><font color='white'>Tour du</font> Joueur " + (modele.getNumeroTourJoueur() + 1) + "</html>");
    }
    private void paintNbAction() {

        nbAction.setText("Nombre d'action restant : " + modele.getJoueurActuel().getNbAction());
    }

    private void paintSoif(Graphics g) {
        int nbJoueur = MGrille.getNbJoueur();
        int largeur = this.getWidth();
        int hauteur = this.getHeight()*5/7;
        int largeurEau = largeur/(nbJoueur*2-1);
        int hauteurEau = hauteur/6;
        int y = this.getHeight()*2/7;

        g.setColor(new Color(200, 200, 200));
        g.fillRect(0, y,
                largeur,
                hauteur);
        g.setColor(new Color(50, 187, 252));
        for(int i = 0; i < nbJoueur; i++) {
            g.fillRect(i*largeurEau*2, y + hauteurEau * (6 - modele.getJoueur(i).getNiveauEau()),
                          largeurEau, hauteurEau*modele.getJoueur(i).getNiveauEau());
        }
        g.setColor(Color.BLACK);
        for(int i = 0; i < 6; i++) {
            g.drawLine(0, y + hauteurEau * i, largeur, y + hauteurEau * i);
        }
    }
    @Override
    public void update() {
        repaint();
    }
}
