package ca.uqam.info.solanum.students.halma.model;


import ca.uqam.info.solanum.inf2050.f24halma.model.*;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;

import java.util.Set;

/**
 * Le type de Model
 */
public class ModelImpl implements Model, ModelReadOnly {

    @Override
    public void occupyField(int i, Field field) throws FieldException {}

    @Override
    public void clearField(Field field) throws FieldException {}

    @Override
    public void setCurrentPlayer(int i) {}

    /**
     * Retourne le nom des joueurs
     * @return tableau avec les noms
     */
    @Override
    public String[] getPlayerNames() {
        return new String[0];
    }

    /**
     * Retourne les cases occupés par un joueur
     * @param playerIndex indice du joueur.
     * @return ensemble contenant les cases occupées par le joueur
     */
    @Override
    public Set<Field> getPlayerFields(int playerIndex) {
        int baseSize = StarModelFactory.baseSize;

        Set<Field> player0Fields = Set.of(new Field(0, baseSize*3));
        Set<Field> player1Fields = Set.of(new Field(baseSize*3, 0));
        Set<Field> player2Fields = Set.of(new Field(baseSize*3, (baseSize*6)));

        return switch (playerIndex) {
            case 0 -> player0Fields;
            case 1 -> player1Fields;
            case 2 -> player2Fields;
            default -> Set.of();
        };
    }

    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    /**
     * Retourne l'instance du plateau
     * @return instance de BoardImpl
     */
    @Override
    public Board getBoard() {return new BoardImpl();}

    @Override
    public boolean isClear(Field field) throws FieldException {return false;}
}
