package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.IllegalMoveException;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import org.junit.Before;
import org.junit.Test;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import static org.junit.Assert.*;

public class ControllerImplTest {
  private ControllerImpl ctrl;

  @Before
  public void setup() {
    ctrl = new ControllerImpl(new StarModelFactory(), 1, new String[]{"A","B","C"});
  }

  @Test
  public void testInitialMovesNotEmpty() {
    assertFalse(ctrl.getPlayerMoves().isEmpty());
  }

  @Test
  public void testPerformMoveUpdatesModel() throws Exception {
    var move = ctrl.getPlayerMoves().get(0);
    ctrl.performMove(move);
    assertFalse(ctrl.getModel().isClear(move.target()));
    assertTrue(ctrl.getModel().isClear(move.origin()));
  }

  @Test(expected = IllegalMoveException.class)
  public void testInvalidPerformMoveThrows() {
    ctrl.performMove(new Move(new Field(0,0), new Field(10,10), false));
  }
}