package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.students.halma.model.BoardImpl;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;

public class StarModelFactory implements ModelFactory {
    /**
     * One and only model instantiation method that must be implemented by whatever model factory
     * implementing class.
     *
     * @param baseSize as the side-length along all player bases. The size of the resulting model's
     *                 board must adapt to the baseSize provided to this method. The bases take up
     *                 a third of fields on the board's longest horizontal line.
     * @param players  as the player names involved on the board. Must be an even number and
     *                 compatible to created layout. Note: the created model's board always contains
     *                 home-zones for the maximum allowed amount * of players for the requested
     *                 layout.
     * @return a Model conforming object, representing the characteristics of the given factory.
     */
    @Override
    public Model createModel(int baseSize, String[] players) {
        ModelImpl model = new ModelImpl();
        BoardImpl board = new BoardImpl();

        board.getAllFields().forEach(field -> {});
        model.getCurrentPlayer();
        return null;
    }
}
