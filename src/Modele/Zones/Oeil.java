package Modele.Zones;

import Modele.MGrille;

public class Oeil extends MZone {
    static final int quantity = 1;
    static int restant = 1;
    public Oeil(int x, int y, MGrille modele) throws TropDInstance {
        super(x, y, modele);
        restant--;
        if(restant < 0) throw(new TropDInstance("Trop de classe du type \" " + this.getClass() + " \""));
    }

    @Override
    public boolean isDejaExploree() {
        return true;
    }

    @Override
    public String toString() {
        return " ";
    }
}
