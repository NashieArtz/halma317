package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.AbstractStarControllerTest;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import org.junit.Test;

import static org.junit.Assert.*;


/**
 *  test.
 */
public class StarControllerTest extends AbstractStarControllerTest {

  @Override
  public Controller getController(int baseSize, String[] playerNames) {
    return new ControllerImpl(new StarModelFactory(), baseSize, playerNames);
  }

  @Test
  public void testComplexMoveSequence() {
    Controller ctrl = getController(2, new String[]{"A","B","C"});
    ctrl.performMove(new Move(new Field(2,0), new Field(3,1), true));
    assertTrue(ctrl.getPlayerMoves().size() > 5);
  }


}
