package Modele;

import Modele.Zones.MZone;
import Modele.Zones.Oasis;
import Modele.Zones.Oeil;
import Modele.Zones.ZoneExplorable;

import java.util.ArrayList;

public class Joueur {
    private int x, y, nbAction;
    final int id;
    private int niveauEau;
    ArrayList<Pieces> piecesRamassees;
    final MGrille modele;
    Joueur(int x, int y, int id, MGrille modele) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.modele = modele;
        nbAction = 4;
        niveauEau = 4;
        piecesRamassees = new ArrayList<>();
    }

    public int getNiveauEau() {
        return niveauEau;
    }

    public void resetNbAction() {
        nbAction = 4;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public int getNbAction() {
        return nbAction;
    }

    public void explore() {
        if(nbAction > 0) {
            if (getZoneActuel() instanceof ZoneExplorable && !(getZoneActuel().isDejaExploree())) {
                ((ZoneExplorable) getZoneActuel()).explore();
                nbAction--;
                if (getZoneActuel() instanceof Oasis && !((Oasis) getZoneActuel()).mirage) {
                    ((Oasis) getZoneActuel()).hydrate();
                }
            }
        }
    }

    public void boisUnCoup() {
        niveauEau--;
    }

    void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public void deplaceJoueur(MZone destination) {
        this.setCoord(destination.getX(), destination.getY());
        nbAction--;
    }

    public void enleveSable(MZone cible) {
        if(nbAction > 0 &&
           cible.isIn(getZoneActuel().getVoisins()) ||
           cible.equals(this.getZoneActuel()) &&
           !(cible instanceof Oeil)
        ) {
            ((ZoneExplorable) cible).removeSable(1);
            nbAction--;
        }
    }

    public void ramasse(Pieces p) {
        piecesRamassees.add(p);
        nbAction--;
    }

    public ArrayList<Pieces> getPiecesRamassees() {
        return piecesRamassees;
    }

    public void reprendEau() {
        niveauEau = Math.min(6, niveauEau + 2);
        System.out.println("j'ai repris de l'eau, j'ai " + niveauEau);
    }

    public void setNiveauEau(int niveauEau) {
        this.niveauEau = niveauEau;
    }

    public MZone getZoneActuel() {
        return modele.getZone(x, y);
    }
    public void poser() {
        piecesRamassees.clear();
        nbAction--;
    }
}
