package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;

/**
 * Fabrique le modèle étoile du jeu
 */
public class StarModelFactory implements ModelFactory {

    public static int baseSize;
    public static int playerNbr;

    /**
     *
     * @param i as the side-length along all player bases. The size of the resulting model's
     *                 board must adapt to the baseSize provided to this method. The bases take up
     *                 a third of fields on the board's longest horizontal line.
     * @param strings  as the player names involved on the board. Must be an even number and
     *                 compatible to created layout. Note: the created model's board always contains
     *                 home-zones for the maximum allowed amount * of players for the requested
     *                 layout.
     * @return une nouvelle instance du modèle du jeu
     */
    @Override
    public Model createModel(int i, String[] strings) {
        baseSize = i;
        playerNbr = strings.length;

        return new ModelImpl();
    }
}
