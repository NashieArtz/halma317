package ca.uqam.info.solanum.students.halma.controller;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.IllegalMoveException;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import ca.uqam.info.solanum.students.halma.model.ModelImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Implémentation du contrôleur du jeu.
 */
public class ControllerImpl implements Controller {
  private final ModelImpl model;

  /**
   * Constructeur du contrôleur.
   *
   * @param modelFactory usine pour créer le modèle du jeu
   * @param baseSize     taill du plateau
   * @param playerNames  noms des joeurs
   * @throws IllegalArgumentException modelFactory is null
   */
  public ControllerImpl(ModelFactory modelFactory, int baseSize, String[] playerNames) {
    if (modelFactory == null) {
      throw new IllegalArgumentException("ModelFactory cannot be null");
    }
    // Créer le modèle
    this.model = (ModelImpl) modelFactory.createModel(baseSize, playerNames);
  }

  @Override
  public ModelReadOnly getModel() {
    return model;
  }

  @Override
  public List<Move> getPlayerMoves() {
    // Récupération des positions des pions
    Set<Field> fields = model.getPlayerFields(model.getCurrentPlayer());
    // Stocker les mouvements
    List<Move> moves = new ArrayList<>();
    // Pour chaque pion, on teste les voisins
    for (Field originField : fields) {
      // Parcours les voisins immédiats
      for (Field neighbourField : model.getBoard().getNeighbours(originField)) {
        try {
          // Case vide = déplacement simple
          if (model.isClear(neighbourField)) {
            moves.add(new Move(originField, neighbourField, false));
          } else {
            // Tentative de saut
            Field jumpField = model.getBoard().getExtendedNeighbour(originField, neighbourField);
            // Saut possible et case libre
            if (jumpField != null && model.isClear(jumpField)) {
              moves.add(new Move(originField, jumpField, true));
            }
          }
        } catch (FieldException e) {
          // Ignore neighbours invalide
        }
      }
    }
    return moves;
  }

  @Override
  public void performMove(Move move) {
    try {
      // Place le pion du joueur sur la case target
      model.occupyField(model.getCurrentPlayer(), move.target());
      // Vide la case d'origine
      model.clearField(move.origin());
    } catch (FieldException | ModelAccessConsistencyException e) {
      throw new IllegalMoveException("Impossible d'effectuer le mouvement " + e.getMessage());
    }
  }

  @Override
  public boolean isGameOver() {
    // Vérifier chaque joueur
    for (int i = 0; i < model.getPlayerNames().length; i++) {
      Set<ca.uqam.info.solanum.inf2050.f24halma.model.Field> target =
          model.getBoard().getTargetFieldsForPlayer(i);
      // Fin de partie = tous les pions sont sur les targetFields
      if (target.containsAll(model.getPlayerFields(i))) {
        return true;
      }
    }
    return false;
  }
}