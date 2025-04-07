package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.AbstractStarControllerTest;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import org.junit.Test;

/**
 *  test.
 */
public class StarControllerTest extends AbstractStarControllerTest {

  @Override
  public  Controller getController(int baseSize, String[] playerNames) {
    return new ControllerImpl(new StarModelFactory(), baseSize, playerNames);
  }

}
