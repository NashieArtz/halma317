package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.IllegalMoveException;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implementation of the game controller for Halma TP4.
 * Delegates game logic to ModelImpl and manages player moves.
 */
public class ControllerImpl implements Controller {

  private final ModelImpl model;

  /**
   * Constructs the ControllerImpl with a ModelFactory, board size, and player names.
   *
   * @param mf factory to create the game model
   * @param baseSize star board size parameter
   * @param names player names or "AI"
   * @throws IllegalArgumentException if mf is null
   */
  public ControllerImpl(ModelFactory mf, int baseSize, String[] names) {
    if (mf == null) {
      throw new IllegalArgumentException("ModelFactory cannot be null");
    }
    this.model = (ModelImpl) mf.createModel(baseSize, names);
  }

  @Override
  public ModelReadOnly getModel() {
    return model;
  }

  @Override
  public List<Move> getPlayerMoves() {
    Set<ca.uqam.info.solanum.inf2050.f24halma.model.Field> fields =
        model.getPlayerFields(model.getCurrentPlayer());
    List<Move> moves = new ArrayList<>();
    for (ca.uqam.info.solanum.inf2050.f24halma.model.Field f : fields) {
      for (ca.uqam.info.solanum.inf2050.f24halma.model.Field n :
          model.getBoard().getNeighbours(f)) {
        try {
          if (model.isClear(n)) {
            moves.add(new Move(f, n, false));
          } else {
            ca.uqam.info.solanum.inf2050.f24halma.model.Field j =
                model.getBoard().getExtendedNeighbour(f, n);
            if (j != null && model.isClear(j)) {
              moves.add(new Move(f, j, true));
            }
          }
        } catch (FieldException ex) {
          // ignore invalid neighbour
        }
      }
    }
    return moves;
  }

  @Override
  public void performMove(Move m) {
    try {
      model.occupyField(model.getCurrentPlayer(), m.target());
      model.clearField(m.origin());
    } catch (FieldException | ModelAccessConsistencyException ex) {
      throw new IllegalMoveException("Cannot perform move: " + ex.getMessage());
    }
  }

  @Override
  public boolean isGameOver() {
    for (int i = 0; i < model.getPlayerNames().length; i++) {
      Set<ca.uqam.info.solanum.inf2050.f24halma.model.Field> target =
          model.getBoard().getTargetFieldsForPlayer(i);
      if (target.containsAll(model.getPlayerFields(i))) {
        return true;
      }
    }
    return false;
  }
}