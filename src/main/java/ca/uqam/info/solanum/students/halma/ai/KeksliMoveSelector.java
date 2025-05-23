package ca.uqam.info.solanum.students.halma.ai;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;
import java.util.Collections;
import java.util.List;

/**
 * IA Keksli : trie la liste des coups et renvoie le premier coup dans l'ordre trié.
 */
public class KeksliMoveSelector implements MoveSelector {
  /**
   * Constructeur par défaut.
   */
  public KeksliMoveSelector() {
  }

  /**
   * Sélectionne un mouvement parmi une liste de mouvements possibles.
   *
   * @param allPossibleMoves la liste des mouvements possibles
   * @return le coup sélectionné ou null si la liste est vide ou null
   */
  @Override
  public Move selectMove(List<Move> allPossibleMoves) {
    if (allPossibleMoves == null || allPossibleMoves.isEmpty()) {
      return null;
    }
    Collections.sort(allPossibleMoves);
    return allPossibleMoves.get(0);
  }
}