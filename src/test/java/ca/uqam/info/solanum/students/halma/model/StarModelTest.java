package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.AbstractModelTest;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;
import java.util.Set;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Classe de test pour Model.
 */
public class StarModelTest extends AbstractModelTest {
  @Override
  public Model getModel(int baseSize) {
    return new StarModelFactory().createModel(baseSize, new String[] {"Max", "Ryan", "Quentin"});
  }

  /**
   * Vérifie l'initialisation du jeu. Avec une taille à 1, 3 joueurs et au moins 1 homeField
   */
  @Test
  public void testInitialisation() {
    Model model = getModel(1);
    assertEquals(3, model.getPlayerNames().length);
    assertFalse(model.getPlayerFields(0).isEmpty());
  }

  /**
   * Vérifie la possibilité d'occuper un field
   *
   * @throws FieldException the field exception
   */
  @Test
  public void testFieldOccupy() throws FieldException {
    Model model = getModel(1);
    Field field = new Field(1, 0);
    model.occupyField(0, field);
    assertFalse(model.isClear(field));
  }

  /**
   * Vérifie qu'une exception est levée si un joueur tente d'occuper un field invalide.
   *
   * @throws FieldException the field exception
   */
  @Test(expected = FieldException.class)
  public void testInvalidFieldOccupy() throws FieldException {
    Model model = getModel(1);
    Field field = new Field(10, 10);
    model.occupyField(0, field);
  }

  /**
   * Vérifie si un field peut être libéré.
   *
   * @throws FieldException                  the field exception
   * @throws ModelAccessConsistencyException the modelaccess exception
   */
  @Test
  public void testClearField() throws FieldException, ModelAccessConsistencyException {
    Model model = getModel(1);
    Field field = new Field(1, 0);
    model.occupyField(0, field);
    model.clearField(field);
    assertTrue(model.isClear(field));
  }

  /**
   * Vérifie qu'une exception est levée si on tente de libérer un field déjà libre.
   *
   * @throws FieldException                  the field exception
   * @throws ModelAccessConsistencyException the model access consistency exception
   */
  @Test(expected = ModelAccessConsistencyException.class)
  public void testClearUnoccupiedField() throws FieldException, ModelAccessConsistencyException {
    Model model = getModel(1);
    Field field = new Field(1, 0);
    model.clearField(field);
  }

  /**
   * Vérifie que le joueur courant peut être correctement défini.
   */
  @Test
  public void testSetCurrentPlayer() {
    Model model = getModel(1);
    model.setCurrentPlayer(1);
    assertEquals(1, model.getCurrentPlayer());
  }

  /**
   * Vérifie qu'une exception est levée si on tente de définir un joueur invalide.
   */
  @Test(expected = ModelAccessConsistencyException.class)
  public void testSetInvalidCurrentPlayer() {
    Model model = getModel(1);
    model.setCurrentPlayer(10);
  }

  /**
   * Vérifie que les fields d'un joueur sont récupérés.
   */
  @Test
  public void testGetPlayerFields() {
    Model model = getModel(1);
    Set<Field> playerFields = model.getPlayerFields(0);
    assertNotNull(playerFields);
    assertTrue(!playerFields.isEmpty());
  }

  /**
   * Vérifie qu'un field est initialement libre, puis qu'il est occupé après avoir été assigné
   * à un joueur.
   *
   * @throws FieldException the field exception
   */
  @Test
  public void testIsClear() throws FieldException {
    Model model = getModel(1);
    Field field = new Field(1, 0);
    assertTrue(model.isClear(field));
    model.occupyField(0, field);
    assertFalse(model.isClear(field));
  }

  /**
   * Vérifie qu'une exception est levée si on tente de vérifier l'état d'un field invalide.
   *
   * @throws FieldException the field exception
   */
  @Test(expected = FieldException.class)
  public void testIsClearInvalidField() throws FieldException {
    Model model = getModel(1);
    Field field = new Field(10, 10);
    model.isClear(field);
  }

  @Test(expected = ModelAccessConsistencyException.class)
  public void testSetCurrentPlayerDuringMove() throws ModelAccessConsistencyException {
    Model model = getModel(1);
    model.setCurrentPlayer(0);
    model.setCurrentPlayer(1);
  }
}
