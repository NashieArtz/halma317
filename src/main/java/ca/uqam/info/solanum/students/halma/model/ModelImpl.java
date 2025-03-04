package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import java.util.Set;

/**
 * Implémentation du modèle du jeu Halma.
 * La classe gère les opérations du modèle comme l'occupation des cases par les joueurs.
 */
public class ModelImpl implements Model, ModelReadOnly {

  /**
   * Constructeur par défaut de ModelImpl.
   */
  public ModelImpl() {
    // Default constructor
  }

  @Override
  public void occupyField(int i, Field field) throws FieldException {
  }

  @Override
  public void clearField(Field field) throws FieldException {
  }

  @Override
  public void setCurrentPlayer(int i) {
  }

  @Override
  public String[] getPlayerNames() {
    return new String[0];
  }

  @Override
  public Set<Field> getPlayerFields(int i) {
    return Set.of();
  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public BoardImpl getBoard() {
    return new BoardImpl();
  }

  @Override
  public boolean isClear(Field field) throws FieldException {
    return false;
  }
}

