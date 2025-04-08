package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class ControllerImplTest {

  @Test(expected = IllegalStateException.class)
  public void testInvalidMoveExecution() {
    ControllerImpl ctrl = new ControllerImpl(new StarModelFactory(), 1, new String[] {"A", "B"});
    ctrl.performMove(new Move(new Field(0, 0), new Field(10, 10), false));
  }

  @Test
  public void testEarlyGameOverDetection() {
    ControllerImpl ctrl = new ControllerImpl(new StarModelFactory(), 1, new String[] {"A", "B"});
    assertFalse(ctrl.isGameOver());
  }
}
