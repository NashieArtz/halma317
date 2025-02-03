package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.Board;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;

import java.util.Set;

public class BoardImpl implements Board {
    /**
     * Getter for all fields contained in the board instance.
     *
     * @return unmodifiable set of all board fields.
     */
    @Override
    public Set<Field> getAllFields() {
        return Set.of();
    }

    /**
     * Getter for all home fields on the given board instance, for a given player specified by index.
     *
     * @param playerIndex as the player for whom we want to look up the home fields. Consecutive pairs
     *                    of an even then an odd player indexes always have opposing home zones.
     * @return unmodifiable set of all the player's home fields.
     */
    @Override
    public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
        return Set.of();
    }

    /**
     * Getter for all home fields on the board, regardless of player affiliation.
     *
     * @return unmodifiable set of all home fields
     */
    @Override
    public Set<Field> getAllHomeFields() {
        return Set.of();
    }

    /**
     * Getter for all target fields on the given board instance, for a given player specified by
     * index.
     *
     * @param playerIndex as the player for whom we want to look up the target fields. Consecutive
     *                    pairs of an even then an odd player indexes always have opposing target
     *                    zones.
     * @return unmodifiable set of all the player's target fields.
     */
    @Override
    public Set<Field> getTargetFieldsForPlayer(int playerIndex) {
        return Set.of();
    }

    /**
     * Getter for neighboured fields, that is, fields that are directly connected to the given field
     * object.
     *
     * @param field as entity on the board for which a set of neighbours must be determined.
     * @return unmodifiable set of all directly connected field objects.
     */
    @Override
    public Set<Field> getNeighbours(Field field) {
        return Set.of();
    }

    /**
     * Getter for extended neighbour, that is the neighbour of a neighbour that lies on a straight
     * line, starting from the given field.
     *
     * @param origin    as field on the board for which a potential extended neighbour is determined.
     * @param neighbour as a direct neighbour of the origin field, defining direction of the extended
     *                  neighbour.
     * @return Field defining extended neighbour of origin. May be null.
     */
    @Override
    public Field getExtendedNeighbour(Field origin, Field neighbour) {
        return null;
    }
}
