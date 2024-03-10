package Modele.Zones;

import Modele.MGrille;
import Modele.Pieces;

public class Indice extends ZoneExplorable {
    static final int quantity = 8;
    static int restant = quantity;
    //0 si c'est l'indice X et 1 si c'est l'indice Y
    final int orientation;
    public static final int Vertical = 0;
    public static final int Horizontal = 1;
    final Pieces pieceConcernee;
    boolean ramassee;
    public Indice(int x, int y, MGrille modele) throws Exception {
        super(x, y, modele);
        ramassee = false;
        orientation = restant % 2;
        pieceConcernee = Pieces.intToPiece((restant-1)/2);
        restant--;
        if(restant < 0) throw(new TropDInstance("Trop de classe du type \" " + this.getClass() + " \""));
    }

    public int getOrientation() {
        return orientation;
    }

    public Pieces getPieceConcernee() {
        return pieceConcernee;
    }

    public boolean aEteRamassee() {
        return ramassee;
    }

    public void ramasse() {
        ramassee = true;
    }
}
