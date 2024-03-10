package Modele.Zones;

import Modele.Joueur;
import Modele.MGrille;

public abstract class ZoneExplorable extends MZone {
    private int sable;

    protected Joueur[] joueurs;

    protected boolean dejaExploree;
    public ZoneExplorable(int x, int y, MGrille modele) {
        super(x, y, modele);
        dejaExploree = false;
        joueurs = new Joueur[MGrille.nbJoueur];
    }
    public boolean isDejaExploree() {
        return dejaExploree;
    }

    public void addSable(int quantite) {
        this.sable += quantite;
    }

    public void removeSable(int quantite) {
        this.sable = Math.max(this.sable - quantite, 0);
    }

    public int getSable() {
        return sable;
    }

    public void explore() {
        dejaExploree = true;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(this.getClass().equals(obj.getClass()))) {
            return false;
        }
        ZoneExplorable tmp = (ZoneExplorable) obj;
        return tmp.x == x && tmp.y == y &&
                (tmp.dejaExploree == dejaExploree) &&
                tmp.sable == sable;
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }

    public void addJoueur(Joueur j) {
        joueurs[j.getId()] = j;
    }

    public void removeJoueur(Joueur j) {
        joueurs[j.getId()] = null;
    }
}
