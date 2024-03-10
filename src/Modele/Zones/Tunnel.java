package Modele.Zones;

import Modele.MGrille;

public class Tunnel extends ZoneExplorable {
    static final int quantity = 3;
    static int restant = quantity;
    public Tunnel(int x, int y, MGrille modele) throws TropDInstance {
        super(x, y, modele);
        restant--;
        if(restant < 0) throw(new TropDInstance("Trop de classe du type \" " + this.getClass() + " \""));
    }
}
