package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.AbstractStarModelFactoryTest;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import org.junit.Test;

import static org.junit.Assert.*;

public class StarModelFactoryTest extends AbstractStarModelFactoryTest {

  @Override
  public ModelFactory getModelFactory() {
    return new StarModelFactory();
  }

  /**
   * Vérifie que la création d'un modèle fonctionne.
   */
  @Test
  public void testModelCreation() {
    ModelFactory factory = getModelFactory();
    Model model = factory.createModel(1, new String[]{"Max", "Ryan", "Quentin"});
    assertNotNull(model);
    assertEquals(3, model.getPlayerNames().length);
  }

  /**
   * Vérifie que chaque joueur a au moins un homeField.
   */
  @Test
  public void testHomeFields() {
    ModelFactory factory = getModelFactory();
    Model model = factory.createModel(1, new String[]{"Max", "Ryan", "Quentin"});
    assertTrue(model.getPlayerFields(0).size() > 0);
    assertTrue(model.getPlayerFields(1).size() > 0);
    assertTrue(model.getPlayerFields(2).size() > 0);
  }

  /**
   * Vérifie que la forme du plateau est correcte.
   */
  @Test
  public void testBoardShape() {
    ModelFactory factory = getModelFactory();
    Model model = factory.createModel(1, new String[]{"Max", "Ryan", "Quentin"});
    assertNotNull(model.getBoard());
    assertEquals(7, model.getBoard().getAllFields().size());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidBaseSize() {
    new ControllerImpl(new StarModelFactory(), 0, new String[]{"A"});
  }

}

