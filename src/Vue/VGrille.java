package Vue;

import Desert_Interdit.Main;
import Modele.Joueur;
import Modele.MGrille;
import Modele.Pieces;
import Modele.Zones.*;
import Tools.Observer;

import javax.swing.*;
import java.awt.*;

public class VGrille extends JPanel implements Observer {
    public final int cote;
    public final int decallage;
    private final MGrille modele;
    public VGrille(MGrille modele) {
        this.modele = modele;
        this.cote = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()/7;
        this.decallage = cote/20;
        this.setPreferredSize(new Dimension(cote * 5 + decallage * 4, cote * 5 + decallage * 4));
        this.setBackground(new Color(77, 38, 0));
        modele.addObserver(this);
    }
    @Override
    public void update() {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!modele.cestLaFinDeJeu()) {
            for (int i = 0; i < Main.taille; i++) {
                for (int j = 0; j < Main.taille; j++) {
                    try {
                        paintZone(g, modele.getZone(i, j));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            paintJoueur(g);
        } else {
            JLabel fin = new JLabel("PERDU!!");
            fin.setForeground(Color.WHITE);
            Font font = fin.getFont();
            fin.setFont(new Font(font.getFontName(), Font.BOLD, 45));
            this.add(fin);
        }
    }

    private void paintZone(Graphics g, MZone mZone) throws Exception {
        if(!(mZone instanceof Oeil)) {
            ZoneExplorable zone = (ZoneExplorable) mZone;
            if (zone.isDejaExploree()) {
                if (zone instanceof Crash) {
                    g.setColor(new Color(230 - Math.min(130, zone.getSable() * 230/4),
                                         50 - Math.min(130, zone.getSable() * 50/4),
                                         50 - Math.min(130, zone.getSable() * 50/4)));
                    g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                } else if (zone instanceof Indice) {
                    g.setColor(new Color(230 - Math.min(230, zone.getSable() * 230/4),
                                         120 - Math.min(120, zone.getSable() * 120/4),
                                         120 - Math.min(120, zone.getSable() * 120/4)));
                    g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                    int numeroPiece = Pieces.pieceToInt(((Indice) zone).getPieceConcernee());
                    g.setColor(new Color(numeroPiece>1?255:0,  numeroPiece>0&&numeroPiece<3?255:0, numeroPiece==0?255:0));
                    if(((Indice) zone).getOrientation() == Indice.Vertical)
                        g.fillRect(zone.getX() * (cote + decallage) + cote/3, zone.getY() * (cote + decallage), cote/3, cote);
                    if(((Indice) zone).getOrientation() == Indice.Horizontal)
                        g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage) + cote/3, cote, cote/3);
                } else if (zone instanceof Oasis) {
                    if(!((Oasis) zone).mirage) {
                        g.setColor(new Color(50 - Math.min(50, zone.getSable() * 50/4),
                                             100 - Math.min(100, zone.getSable() * 100/4),
                                             150 - Math.min(150, zone.getSable() * 150/4)));
                        g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                    } else {
                        g.setColor(new Color(160 - Math.min(160, zone.getSable() * 160/4),
                                             200 - Math.min(200, zone.getSable() * 200/4),
                                             230 - Math.min(230, zone.getSable() * 230/4)));
                        g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                    }
                } else if (zone instanceof Piste) {
                    g.setColor(new Color(100 - Math.min(100, zone.getSable() * 100/4),
                                         200 - Math.min(200, zone.getSable() * 200/4),
                                         100 - Math.min(100, zone.getSable() * 100/4)));
                    g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                } else if (zone instanceof Tunnel) {
                    g.setColor(new Color(30, 30, 30));
                    g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                } else if (zone instanceof Desert) {
                    g.setColor(new Color(255 - Math.min(255, zone.getSable() * 255/4),
                                         224 - Math.min(224, zone.getSable() * 224/4),
                                         102 - Math.min(102, zone.getSable() * 102/4)));
                    g.fillRect(zone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
                }
            } else {
                g.setColor(new Color(255 - Math.min(255, zone.getSable() * 255/4),
                        224 - Math.min(224, zone.getSable() * 224/4),
                        102 - Math.min(102, zone.getSable() * 102/4)
                ));
                g.fillRect(mZone.getX() * (cote + decallage), zone.getY() * (cote + decallage), cote, cote);
            }
            g.setFont(new Font("IMPACT", Font.BOLD, 20));
            if(zone.getSable() < 3 ) g.setColor(Color.BLACK);
            else g.setColor(Color.WHITE);
            g.drawString(zone.getSable() + "", mZone.getX() * (cote + decallage), zone.getY() * (cote + decallage)+cote/7);
        }
    }

    private void paintJoueur(Graphics g) {
        int diametre = cote/3;
        for (Joueur j : modele.getJoueurs()) {
            g.setColor(new Color(
                    255 - 255 * j.getId()/MGrille.getNbJoueur(),
                    255 * j.getId()/ MGrille.getNbJoueur(),
                    128 + 128 * (j.getId() - MGrille.getNbJoueur()/2)/MGrille.getNbJoueur()/2));
            g.fillOval(j.getX() * (cote + decallage) + (int) ((j.getId() == 2 ? 0.5:(j.getId() % 2)) * 2 * diametre),
                       j.getY() * (cote + decallage) +(j.getId() < 2?0:(j.getId() > 2?2:1)) * diametre,
                          diametre, diametre);
        }
    }
}

