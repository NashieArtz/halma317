package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.*;

import java.util.Set;

public class ModelImpl implements Model, ModelReadOnly {
    /**
     * Occupies the given target field to the given player, as specified by their id.
     *
     * @param playerIndex as the index of the player who will occupy the field. Must be in valid
     *                    range, i.e. not below 0 and not exceeding player indexes.
     * @param field       as the field instance to assign to the player.
     * @throws FieldException in case the specified field is already occupied.
     */
    @Override
    public void occupyField(int playerIndex, Field field) throws FieldException {

    }

    /**
     * Clears a given field from any potential player occupation.
     *
     * @param field as the target field to clear from any potential occupation.
     * @throws FieldException in case the specified field is not currently occupied.
     */
    @Override
    public void clearField(Field field) throws FieldException {

    }

    /**
     * Changes the pointer to the current player.
     *
     * @param playerIndex as the index of the player who will be new current player. Must be in valid
     *                    range, i.e. not below 0 and not exceed player indexes.
     */
    @Override
    public void setCurrentPlayer(int playerIndex) {

    }

    /**
     * Returns the plain string names of all players in order participating in this model instance.
     *
     * @return copy of player string names in order.
     */
    @Override
    public String[] getPlayerNames() {
        return new String[0];
    }

    /**
     * Returns all fields currently occupied by the requested player, identified by player index.
     *
     * @param playerIndex as the player for whom the occupied fields are to be identified.
     * @return unmodifiable set of all fields currently occupied by a player.
     */
    @Override
    public Set<Field> getPlayerFields(int playerIndex) {
        return Set.of();
    }

    /**
     * Getter for the index of the current player.
     *
     * @return int representing the index of the current player.
     */
    @Override
    public int getCurrentPlayer() {
        return 0;
    }

    /**
     * Returns read only representation of board, excluding position of game elements.
     *
     * @return board instance of this model.
     */
    @Override
    public Board getBoard() {
        return null;
    }

    /**
     * Returns true if the specified field is unoccupied.
     *
     * @param field the field to look up. Must be a valid field on the board.
     * @return true if the provided field exists and is unoccupied.
     * @throws FieldException if the provided field is not a valid board position
     */
    @Override
    public boolean isClear(Field field) throws FieldException {
        return false;
    }
}
