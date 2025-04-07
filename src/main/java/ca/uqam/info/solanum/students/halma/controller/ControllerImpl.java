package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Le type de controlleur.
 */
public class ControllerImpl implements Controller {
  private final ModelImpl model;

  /**
   * Constructeur qui initialise le contrôleur en utilisant une fabrique de modèles.
   *
   * @param modelFactory la fabrique de modèles utilisée pour créer un modèle.
   * @param baseSize     la taille de base du plateau.
   * @param playerNames  les noms des joueurs participant au jeu.
   * @throws IllegalArgumentException si la fabrique ou les paramètres sont invalides.
   */
  public ControllerImpl(ModelFactory modelFactory, int baseSize, String[] playerNames) {
    if (modelFactory == null) {
      throw new IllegalArgumentException("La fabrique de modèle ne peut pas être nulle.");
    }
    if (baseSize <= 0) {
      throw new IllegalArgumentException("La taille de base doit être positive.");
    }
    if (playerNames == null || playerNames.length < 2) {
      throw new IllegalArgumentException("Au moins deux joueurs doivent être fournis.");
    }
    this.model = (ModelImpl) modelFactory.createModel(baseSize, playerNames);
  }

  @Override
  public ModelReadOnly getModel() {
    return this.model;
  }

  @Override
  public List<Move> getPlayerMoves() {
    int currentPlayerIndex = model.getCurrentPlayer();
    Set<Field> playerFields = model.getPlayerFields(currentPlayerIndex);
    List<Move> possibleMoves = new ArrayList<>();
    for (Field field : playerFields) {
      Set<Field> neighbours = model.getBoard().getNeighbours(field);
      for (Field neighbour : neighbours) {
        if (model.isClear(neighbour)) {
          possibleMoves.add(new Move(field, neighbour, false));
        }
      }
    }
    return possibleMoves;
  }

  @Override
  public void performMove(Move move) {
    try {
      model.occupyField(model.getCurrentPlayer(), move.target());
      model.clearField(move.origin());
    } catch (FieldException e) {
      throw new IllegalStateException("Impossible d'effectuer le mouvement : " + e.getMessage(), e);
    }
  }

  @Override
  public boolean isGameOver() {
    for (int playerIndex = 0; playerIndex < model.getPlayerNames().length; playerIndex++) {
      Set<Field> targetFields = model.getBoard().getTargetFieldsForPlayer(playerIndex);
      Set<Field> playerFields = model.getPlayerFields(playerIndex);
      if (targetFields.containsAll(playerFields)) {
        return true;
      }
    }
    return false;
  }
}
