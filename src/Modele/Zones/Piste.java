package Modele.Zones;

import Modele.MGrille;

public class Piste extends ZoneExplorable {
    static final int quantity = 1;
    static int restant = quantity;
    public Piste(int x, int y, MGrille modele) throws TropDInstance {
        super(x, y, modele);
        restant--;
        if(restant < 0) throw(new TropDInstance("Trop de classe du type \" " + this.getClass() + " \""));
    }
}
