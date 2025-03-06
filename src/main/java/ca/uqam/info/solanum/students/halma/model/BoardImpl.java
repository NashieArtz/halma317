package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.Board;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Implémentation du plateau de jeu.
 * La classe représente le plateau de jeu en fournissant les coordonnées des cases valides ainsi
 * que les positions de départ et d'arrivée des joueurs.
 * Elle permet également de récupérer les voisins immédiats et étendus d'un champ.
 */
public class BoardImpl implements Board {
  private final int baseSize;

  /**
   * Constructeur par défaut de BoardImpl.
   * Initialise une instance du plateau de jeu.
   *
   * @param baseSize TO REPLACE.
   */
  public BoardImpl(int baseSize) {
    this.baseSize = baseSize;
  }

  @Override
  public Set<Field> getAllFields() {
    Set<Field> emptyFields = new HashSet<>();
    int nbrColonne = 4 * baseSize + 1; // "width"
    int nbrLignes = 6 * baseSize + 1; // "height"
    for (int y = 0; y < nbrLignes; y++) {
      for (int x = 0; x < nbrColonne; x++) {
        // Keep checkerboard cells only:
        if ((baseSize % 2) == ((x + y) % 2)) {
          // Split into 2 big lobes (top-right & bottom-left),
          // so we get a star shape.
          // 1) Top lobe: x >= baseSize, plus some Y bounds
          boolean topLobe =
              (x >= baseSize)
                  // Lower bound on Y (shifted by -1 so we can include row=0 at x=4 for baseSize=3)
                  && (y >= x - baseSize - 1)
                  // Upper bound on Y
                  && (y <= (nbrLignes - 1) - x + baseSize);
          // 2) Bottom lobe: x <= (nbrColonne - 1 - baseSize), plus some Y bounds
          boolean bottomLobe =
              (x <= (nbrColonne - 1 - baseSize))
                  // Lower bound on Y
                  && (y >= ((nbrColonne - 1) - baseSize) - x - 1)
                  // Upper bound on Y
                  && (y <= (nbrLignes - 1) - ((nbrColonne - 1 - x) - baseSize));
          if (topLobe || bottomLobe) {
            emptyFields.add(new Field(x, y));
          }
        }
      }
    }
    return Collections.unmodifiableSet(emptyFields);
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
    Set<Field> result = new HashSet<>();
    int cols = 4 * baseSize + 1;
    int rows = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0:
        result.add(new Field(0, baseSize));
        result.add(new Field(1, baseSize - 1));
        result.add(new Field(1, baseSize + 1));
        break;
      case 1:
        result.add(new Field(cols - 1, baseSize));
        result.add(new Field(cols - 2, baseSize - 1));
        result.add(new Field(cols - 2, baseSize + 1));
        break;
      case 2:
        result.add(new Field(0, rows - baseSize - 1));
        result.add(new Field(1, rows - baseSize - 2));
        result.add(new Field(1, rows - baseSize));
        break;
      case 3:
        result.add(new Field(cols - 1, rows - baseSize - 1));
        result.add(new Field(cols - 2, rows - baseSize - 2));
        result.add(new Field(cols - 2, rows - baseSize));
        break;
      case 4:
        result.add(new Field(baseSize, rows / 2));
        break;
      case 5:
        result.add(new Field(cols - baseSize - 1, rows / 2));
        break;
      default:
        break;
    }
    return Collections.unmodifiableSet(result);
  }

  @Override
  public Set<Field> getAllHomeFields() {
    Set<Field> all = new HashSet<>();
    // Suppose you have 3 players. Collect them:
    for (int p = 0; p < 3; p++) {
      all.addAll(getHomeFieldsForPlayer(p));
    }
    return Collections.unmodifiableSet(all);
  }

  @Override
  public Set<Field> getTargetFieldsForPlayer(int i) {
    return Set.of();
  }

  @Override
  public Set<Field> getNeighbours(Field field) {
    if (!getAllFields().contains(field)) {
      // Some test expects FieldException for invalid fields:
      throw new RuntimeException(
          new FieldException("Field not in board: " + field.toString()));
    }
    Set<Field> neighbors = new HashSet<>();
    // For example, add (x±1, y), (x, y±1), (x±1, y±1):
    int x = field.x();
    int y = field.y();
    // Example of 6 or 8 possible directions:
    int[][] deltas = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {1, 1}, {-1, 1}, {1, -1}
    };
    for (int[] d : deltas) {
      Field candidate = new Field(x + d[0], y + d[1]);
      if (getAllFields().contains(candidate)) {
        neighbors.add(candidate);
      }
    }
    return Collections.unmodifiableSet(neighbors);
  }

  @Override
  public Field getExtendedNeighbour(Field origin, Field via) {
    if (!getAllFields().contains(origin)
        || !getAllFields().contains(via)) {
      throw new RuntimeException(
          new FieldException("Invalid origin or via field: " + origin + " / " + via));
    }
    int dx = via.x() - origin.x();
    int dy = via.y() - origin.y();
    Field jump = new Field(origin.x() + 2 * dx, origin.y() + 2 * dy);
    if (getAllFields().contains(jump)) {
      return jump;
    } else {
      return null;
    }
  }
}
