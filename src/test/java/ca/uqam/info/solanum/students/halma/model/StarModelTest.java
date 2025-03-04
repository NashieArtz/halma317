package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.AbstractModelTest;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;

/**
 * Classe de test pour Model.
 */
public class StarModelTest extends AbstractModelTest {
  @Override
  public Model getModel(int baseSize) {
    return new StarModelFactory().createModel(baseSize, new String[] {"Max", "Ryan", "Quentin"});
  }
}
