package Modele.Zones;

import Modele.MGrille;

import java.util.ArrayList;
import java.util.Collection;

public abstract class MZone {
    int x;
    int y;
    final MGrille modele;

    public MZone(int x, int y, MGrille modele) {
        this.x = x;
        this.y = y;
        this.modele = modele;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public void setCoord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public ArrayList<MZone> getVoisins() {
        ArrayList<MZone> voisins = new ArrayList<>();

        // Vérifier la case en haut
        if (x > 0) {
            voisins.add(modele.getZone(x-1, y));
        }

        // Vérifier la case en bas
        if (x < 5 - 1) {
            voisins.add(modele.getZone(x + 1, y));
        }

        // Vérifier la case à gauche
        if (y > 0) {
            voisins.add(modele.getZone(x, y - 1));
        }

        // Vérifier la case à droite
        if (y < 5 - 1) {
            voisins.add(modele.getZone(x, y + 1));
        }

        return voisins;
    }


    public boolean isIn(Collection<MZone> t) {
        for (MZone z : t) {
            if(z.equals(this)) return true;
        }
        return false;
    }

    public abstract boolean isDejaExploree();

    @Override
    public String toString() {
        return "X";
    }

}

