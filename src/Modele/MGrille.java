package Modele;

import Desert_Interdit.Main;
import Modele.Zones.*;
import Tools.Observable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MGrille extends Observable {
    private final MZone[][] grille;
    int crashX, crashY;
    int oeilX, oeilY;
    int[][] directions;
    int totalSable;
    float niveauTempete;
    boolean finDeJeu;

    private int numeroTourJoueur;

    static public int nbJoueur;
    Joueur[] joueurs;

    Indice[][] indices;
    int peutRamasser;
    ArrayList<Pieces> piecesTrouvees;

    public MGrille() throws Exception {
        niveauTempete = 2;

        piecesTrouvees = new ArrayList<>();
        indices = new Indice[4][2];
        peutRamasser = -1;

        Scanner scanner = new Scanner(System.in);

        // Demander à l'utilisateur de saisir une chaîne de caractères
        System.out.print("Veuillez saisir le nombre de joueur : ");
        boolean correct = true;
        do {
            if(!correct)
                System.out.print("Le nombre de joueur doit être compris entre 2 et 5\nVeuillez ressaisir le nombre de joueur : ");
            nbJoueur = scanner.nextInt();
            correct = false;
        } while (nbJoueur < 2 || nbJoueur > 5);
        scanner.close();

        finDeJeu = false;
        numeroTourJoueur = 0;
        grille = new MZone[5][5];
        ArrayList<MZone> allZone = new ArrayList<>();
        allZone.add(new Crash(0, 0, this));
        allZone.add(1, new Piste(0, 0, this));
        for(int i = 2; i < 24; i++) {
            if(i < 8+2) {
                Indice tmp = new Indice(0, 0, this);
                allZone.add(tmp);
                indices[Pieces.pieceToInt(tmp.getPieceConcernee())][tmp.getOrientation()] = tmp;
            } else if(i < 8+2+8) allZone.add( new Desert(0, 0, this));
            else if(i < 8+2+8+3) allZone.add( new Oasis(0, 0, this));
            else allZone.add( new Tunnel(0, 0, this));
        }
        insertMZoneListIntoGrille(allZone, grille);
        grille[2][2] = new Oeil(2, 2, this);
        for (int i = 0; i < Desert_Interdit.Main.taille; i++) {
            for (int j = 0; j < Desert_Interdit.Main.taille; j++) {
                if(grille[i][j] instanceof Crash) {
                    crashX = i;
                    crashY = j;
                }
                if(grille[i][j] instanceof Oeil) {
                    oeilX = i;
                    oeilY = j;
                }
                if (i == 0 && j == 2 ||
                        i == 1 && j == 1 ||
                        i == 1 && j == 3 ||
                        i == 2 && j == 0 ||
                        i == 2 && j == 4 ||
                        i == 3 && j == 1 ||
                        i == 3 && j == 3 ||
                        i == 4 && j == 2)
                    addSable((ZoneExplorable) grille[i][j], 1);
            }
        }

        joueurs = new Joueur[nbJoueur];
        for (int i = 0; i < nbJoueur; i++) {
            joueurs[i] = new Joueur(crashX, crashY, i, this);
            ((ZoneExplorable) grille[crashX][crashY]).addJoueur(joueurs[i]);
        }


        directions = new int[4][2];
        directions[0] = new int[]{-1, 0};
        directions[1] = new int[]{1, 0};
        directions[2] = new int[]{0, -1};
        directions[3] = new int[]{0, 1};
    }

    public static int getNbJoueur() {
        return nbJoueur;
    }

    void addSable(ZoneExplorable zone, int quantite) {
        zone.addSable(quantite);
        totalSable += quantite;
    }

    public void finDeTour() {
        int rnd = Main.random.nextInt(31);
        for (int i = 0; i < (int) niveauTempete; i++) {
            if(rnd < 24) ventSouffle();
            else if(rnd < 28) vagueDeChaleur();
            else tempeteDechaine();
        }
        joueurs[numeroTourJoueur].resetNbAction();
        if(numeroTourJoueur < nbJoueur - 1) numeroTourJoueur++;
        else numeroTourJoueur = 0;
        checkFinDeJeu();
        checkPeutRamasser();
        notifyObservers();
    }
    private void checkPeutRamasser() {
        peutRamasser = -1;
        for (int i = 0; i < indices.length; i++) {
            if(getJoueurActuel().getZoneActuel().getX() == indices[i][0].getX() &&
                    getJoueurActuel().getZoneActuel().getY() == indices[i][1].getY() &&
                    indices[i][0].isDejaExploree() && indices[i][1].isDejaExploree() &&
                    !indices[i][0].aEteRamassee())
                peutRamasser = i;
        }
    }

    private void checkFinDeJeu() {
        for (Joueur j : joueurs) {
            if (j.getNiveauEau() <= 0) {
                finDeJeu = true;
                break;
            }
        }
        if(totalSable >= 43) finDeJeu = true;
    }

    private void tempeteDechaine() {
        niveauTempete+=0.5f;
    }

    private void vagueDeChaleur() {
        for (Joueur j : joueurs) {
            if(!(j.getZoneActuel() instanceof Tunnel && j.getZoneActuel().isDejaExploree())) j.boisUnCoup();
        }
    }

    private void ventSouffle() {
        int f = Desert_Interdit.Main.random.nextInt(1, 4);
        int[] d = directions[Desert_Interdit.Main.random.nextInt(0, 4)];
        MZone tmp;
        int x, y;
        if (d[1] != 0) {
            for (int i = 1; i <= f; i++) {
                y = oeilY + d[1];
                if (y >= 0 && y < 5) {
                    addSable((ZoneExplorable) grille[oeilX][y], 1);

                    tmp = grille[oeilX][y];
                    grille[oeilX][y] = grille[oeilX][oeilY];
                    grille[oeilX][oeilY] = tmp;

                    tmp.setCoord(oeilX, oeilY);
                    grille[oeilX][y].setCoord(oeilX, y);
                    oeilY = y;

                    for (Joueur j: ((ZoneExplorable) tmp).getJoueurs()) {
                        if(j != null) j.setCoord(tmp.getX(), tmp.getY());
                    }
                }
            }
        } else {
            for (int i = 1; i <= f; i++) {
                x = oeilX + d[0];
                if (x >= 0 && x < 5) {
                    addSable((ZoneExplorable) grille[x][oeilY], 1);

                    tmp = grille[x][oeilY];
                    grille[x][oeilY] = grille[oeilX][oeilY];
                    grille[oeilX][oeilY] = tmp;

                    tmp.setCoord(oeilX, oeilY);
                    grille[x][oeilY].setCoord(x, oeilY);
                    oeilX = x;

                    for (Joueur j: ((ZoneExplorable) tmp).getJoueurs()) {
                        if(j != null) j.setCoord(tmp.getX(), tmp.getY());
                    }
                }
            }
        }
        checkPeutRamasser();
    }

    public boolean cestLaFinDeJeu() {
        return finDeJeu;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                res.append(grille[j][i].toString());
            }
            res.append("\n");
        }
        return res.toString();
    }

    public MZone getZone(int x, int y) {
        return grille[x][y];
    }

    public Joueur[] getJoueurs() {
        return joueurs;
    }
    public Joueur getJoueur(int indice) {
        return joueurs[indice];
    }

    public void deplacement(MZone cible) {
        if(!(cible instanceof Oeil) && ((ZoneExplorable) cible).getSable() < 2) {
            if(getJoueurActuel().getNbAction() > 0 &&
               cible.isIn(getJoueurActuel().getZoneActuel().getVoisins()) ||
              (getJoueurActuel().getZoneActuel() instanceof Tunnel && getJoueurActuel().getZoneActuel().isDejaExploree() &&
               cible instanceof Tunnel && cible.isDejaExploree())
            ) {
                ((ZoneExplorable) cible).addJoueur(joueurs[numeroTourJoueur]);
                ((ZoneExplorable) grille[joueurs[numeroTourJoueur].getX()][joueurs[numeroTourJoueur].getY()]).removeJoueur(joueurs[numeroTourJoueur]);
                joueurs[numeroTourJoueur].deplaceJoueur(cible);
                checkPeutRamasser();
                notifyObservers();
            }
        }
    }

    public ArrayList<Pieces> getPiecesTrouvees() {
        return piecesTrouvees;
    }

    public void ramasse() throws Exception {
        if(peutRamasser > -1) {
            getJoueurActuel().ramasse(Pieces.intToPiece(peutRamasser));
            indices[peutRamasser][0].ramasse();
            indices[peutRamasser][1].ramasse();
            peutRamasser = -1;
            notifyObservers();
        }
    }

    public void pose() {
        if(getJoueurActuel().getZoneActuel().isDejaExploree()) {
            for (Pieces p : getJoueurActuel().getPiecesRamassees()) {
                piecesTrouvees.add(p);
            }
            getJoueurActuel().poser();
        }
        notifyObservers();
    }

    public void retireSable(MZone cible) {
        if(!(cible instanceof Oeil) && ((ZoneExplorable) cible).getSable() > 0) {
            joueurs[numeroTourJoueur].enleveSable(cible);
            notifyObservers();
        }
    }

    public Joueur getJoueurActuel() {
        return joueurs[numeroTourJoueur];
    }

    public int getNumeroTourJoueur() {
        return numeroTourJoueur;
    }

    public static void insertMZoneListIntoGrille(ArrayList<MZone> mZoneList, MZone[][] grille) {
        Collections.shuffle(mZoneList);
        int index = 0;
        for (int i = 0; i < grille.length; i++) {
            for (int j = 0; j < grille[i].length; j++) {
                if (i != 2 || j != 2) {
                    grille[i][j] = mZoneList.get(index);
                    grille[i][j].setCoord(i, j);
                    index++;
                }
            }
        }
    }

    public void explore() {
        if(((ZoneExplorable) getJoueurActuel().getZoneActuel()).getSable() == 0)
            joueurs[numeroTourJoueur].explore();
        notifyObservers();
    }

    public float getNiveauTempete() {
        return niveauTempete;
    }

    public int getPeutRamasser() {
        return peutRamasser;
    }

    public void partageEau() {
        for (Joueur j: ((ZoneExplorable) getJoueurActuel().getZoneActuel()).getJoueurs()) {
            if (j != getJoueurActuel() && j != null &&
                j.getNiveauEau() < 6) {
                getJoueurActuel().setNiveauEau(getJoueurActuel().getNiveauEau() - 1);
                j.setNiveauEau(j.getNiveauEau() + 1);
            }
        }
        notifyObservers();
    }
}
