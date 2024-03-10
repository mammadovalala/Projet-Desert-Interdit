package Modele;

public enum Pieces {
    Helice, BoiteDeVitesse, CristalEnergie, SystemeNavigation;

    public static Pieces intToPiece(int numero) throws Exception {
        if (numero == 0) {
            return Helice;
        }
        if (numero == 1) {
            return BoiteDeVitesse;
        }
        if (numero == 2) {
            return CristalEnergie;
        }
        if (numero == 3) {
            return SystemeNavigation;
        }
        throw (new Exception("Piece numero " + numero +" inextistant"));
    }

    public static int pieceToInt(Pieces piece) throws Exception {
        switch (piece) {
            case Helice -> {return 0;}
            case BoiteDeVitesse -> {return 1;}
            case CristalEnergie ->  {return 2;}
            case SystemeNavigation -> {return 3;}
        }
        throw (new Exception("Piece non existante"));
    }

    @Override
    public String toString() {
        switch (this) {
            case Helice -> {return "l'Hélice";}
            case BoiteDeVitesse -> {return "la Boîte de Vitesse";}
            case CristalEnergie ->  {return "le Cristal d'Energie";}
            case SystemeNavigation -> {return "le Système de Navigation";}
        }
        return "";
    }
}
