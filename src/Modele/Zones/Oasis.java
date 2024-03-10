package Modele.Zones;

import Modele.Joueur;
import Modele.MGrille;

public class Oasis extends ZoneExplorable {
    static final int quantity = 3;
    public final boolean mirage;
    static int restant = quantity;
    public Oasis(int x, int y, MGrille modele) throws TropDInstance {
        super(x, y, modele);
        mirage = !(restant < 3);
        //mirage = false;
        restant--;
        if(restant < 0) throw(new TropDInstance("Trop de classe du type \" " + this.getClass() + " \""));
    }

    public void hydrate() {
        for (Joueur j : joueurs) {
            if(j != null) {
                j.reprendEau();
            }
        }
    }
}
