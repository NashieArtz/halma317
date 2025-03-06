package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;

/**
 * Fabrique le modèle étoile du jeu.
 */
public class StarModelFactory implements ModelFactory {
  /**
   * Constante de baseSize.
   */
  public static int baseSize;
  /**
   * Constante de playerNbr.
   */
  public static int playerNbr;

  /**
   * Constructeur par défaut de StarModelFactory.
   */
  public StarModelFactory() {
  }

  @Override
  public Model createModel(int baseSize, String[] playerNames) {
    return new ModelImpl(baseSize, playerNames);
  }
}
