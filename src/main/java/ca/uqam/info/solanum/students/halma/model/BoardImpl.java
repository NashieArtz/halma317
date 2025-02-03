package ca.uqam.info.solanum.students.halma.model;


import ca.uqam.info.solanum.inf2050.f24halma.model.Board;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;

import java.util.HashSet;

import java.util.Set;

import static ca.uqam.info.solanum.students.halma.controller.StarModelFactory.baseSize;


/**
 * Implémentation du plateau de jeu
 */
public class BoardImpl implements Board {

    /**
     * Retourne les cases jouables du plateau
     * @return un ensemble de cases valides
     */
    @Override
    public Set<Field> getAllFields() {
        Set<Field> EmptyFields = new HashSet<>();

        int nbrColonne = 4 * baseSize + 1;
        int nbrLignes = 6 * baseSize + 1;

        String[][] board = new String[nbrColonne][nbrLignes];

        // Parcours les cases possibles du plateau
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {

                if (baseSize % 2 == (x + y) % 2) {
                    // Vérifie si la case est dans la zone de jeu
                    if ((x > baseSize - 1 && (y >= x - baseSize && y <= board[0].length - x + baseSize)) ||
                            (x <= board.length - baseSize - 1 && (y >= board.length - baseSize - x - 1 &&
                                    y <= board[0].length - (board.length - x - baseSize)))) {
                        EmptyFields.add(new Field(x, y));
                    }
                }
            }
        }
        return EmptyFields;
    }

    @Override
    public Set<Field> getHomeFieldsForPlayer(int i) {return Set.of();}

    /**
     * Retourne les cases de départ
     * @return ensemble de cases de départ
     */
    @Override
    public Set<Field> getAllHomeFields() {
        Set<Field> PlayerFields = new HashSet<>();

        // nbr de colonne
        int x = baseSize * 4 + 1;
        // nbr de ligne
        int y = baseSize * 6 + 1;

        // Définit les positions de départ des joueurs
        PlayerFields.add(new Field(0, y/2));
        PlayerFields.add(new Field(x/3, y-1));
        PlayerFields.add(new Field(x/3, 0));
        PlayerFields.add(new Field(x-1,baseSize*3));
        PlayerFields.add(new Field(baseSize*3, baseSize*6));
        PlayerFields.add(new Field(x/3 + baseSize * 2,0));

        return PlayerFields;
    }

    @Override
    public Set<Field> getTargetFieldsForPlayer(int i) {return Set.of();}

    @Override
    public Set<Field> getNeighbours(Field field) {return Set.of();}

    @Override
    public Field getExtendedNeighbour(Field field, Field field1) {return null;}
}
