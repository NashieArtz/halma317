package ca.uqam.info.solanum.students.halma.ai;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * IA Mad Max : trie la liste des coups puis choisit aléatoirement un coup parmi la liste triée.
 */
public class MadMaxMoveSelector implements MoveSelector {
  private final Random random = new Random(42);


  /**
   * Constructeur par défaut.
   *
   * @param seed test
   */
  public MadMaxMoveSelector(int seed) {
  }

  /**
   * Sélectionne un mouvement aléatoire parmi une liste de mouvements possibles.
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
    return allPossibleMoves.get(random.nextInt(allPossibleMoves.size()));
  }
}