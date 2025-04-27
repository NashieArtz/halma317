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
   * @param baseSize largeur du jeu.
   */
  public BoardImpl(int baseSize) {
    this.baseSize = baseSize;
  }

  @Override
  public Set<Field> getAllFields() {
    Set<Field> all = new HashSet<>();
    int cols = 4 * baseSize + 1;
    int rows = 6 * baseSize + 1;
    for (int x = 0; x < cols; x++) {
      for (int y = 0; y < rows; y++) {
        if ((x + y) % 2 == baseSize % 2 && inStar(x, y, cols, rows)) {
          all.add(new Field(x, y));
        }
      }
    }
    return Collections.unmodifiableSet(all);
  }

  /**
   * test.
   *
   * @param x test
   * @param y test
   * @param c test
   * @param r test
   * @return test
   */
  private boolean inStar(int x, int y, int c, int r) {
    return (x >= baseSize && y >= x - baseSize - 1 && y <= r - 1 - x + baseSize)
        || (x <= c - 1 - baseSize && y >= c - 1 - baseSize - x - 1
        && y <= r - 1 - (c - 1 - baseSize - x));
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
    Set<Field> home = new HashSet<>();
    int cols = 4 * baseSize + 1;
    int rows = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0 -> configurePointedArea(home, baseSize, 0, true);
      case 1 -> configurePointedArea(home, cols - baseSize - 1, 0, true);
      case 2 -> configureSideArea(home, cols - 1, rows / 2);
      case 3 -> configurePointedArea(home, baseSize, rows - 1, false);
      case 4 -> configurePointedArea(home, cols - baseSize - 1, rows - 1, false);
      case 5 -> configureSideArea(home, 0, rows / 2);
      default -> {
      }
    }
    return Collections.unmodifiableSet(home);
  }

  /**
   * Configure une zone home ou target pour chaque joueur.
   *
   * @param set Le set où les fields seront ajoutés
   * @param x   Coordonnée X
   * @param y   Coordonnée Y
   * @param top Vérifier l'emplacement de la pointe
   */
  private void configurePointedArea(Set<Field> set, int x, int y, boolean top) {
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    int dy = top ? 1 : -1;
    set.add(new Field(x - 1, y + dy));
    set.add(new Field(x, y + 2 * dy));
    set.add(new Field(x + 1, y + dy));
  }

  /**
   * Configure une zone home ou target pour chaque joueur.
   *
   * @param set Le set où les fields seront ajoutés
   * @param x   Coordonnée X
   * @param y   Coordonnée Y
   */
  private void configureSideArea(Set<Field> set, int x, int y) {
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    int dx = (x == 0) ? 1 : -1;
    set.add(new Field(x + dx, y + 1));
    set.add(new Field(x + dx, y - baseSize + 1));
  }

  @Override
  public Set<Field> getAllHomeFields() {
    Set<Field> allHomeFields = new HashSet<>();
    for (int i = 0; i < 6; i++) {
      allHomeFields.addAll(getHomeFieldsForPlayer(i));
    }
    return Collections.unmodifiableSet(allHomeFields);
  }

  @Override
  public Set<Field> getTargetFieldsForPlayer(int playerIndex) {
    Set<Field> target = new HashSet<>();
    int cols = 4 * baseSize + 1;
    int rows = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0 -> configurePointedArea(target, baseSize, rows - 1, false);
      case 1 -> configurePointedArea(target, cols - baseSize - 1, rows - 1, false);
      case 2 -> configureSideArea(target, 0, rows / 2);
      case 3 -> configurePointedArea(target, baseSize, 0, true);
      case 4 -> configurePointedArea(target, cols - baseSize - 1, 0, true);
      case 5 -> configureSideArea(target, cols - 1, rows / 2);
      default -> {
      }
    }
    return Collections.unmodifiableSet(target);
  }

  @Override
  public Set<Field> getNeighbours(Field field) throws FieldException {
    if (!getAllFields().contains(field)) {
      throw new FieldException("Field not on board: " + field);
    }
    int x = field.x();
    int y = field.y();
    int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1},
        {-1, 1}, {1, -1}, {0, -2}, {0, 2}};
    Set<Field> neigh = new HashSet<>();
    for (int[] d : dirs) {
      Field f2 = new Field(x + d[0], y + d[1]);
      if (getAllFields().contains(f2)) {
        neigh.add(f2);
      }
    }
    return Collections.unmodifiableSet(neigh);
  }

  @Override
  public Field getExtendedNeighbour(Field origin, Field via) throws FieldException {
    if (!getAllFields().contains(origin) || !getAllFields().contains(via)) {
      throw new FieldException("Invalid origin or via: " + origin + "/" + via);
    }
    int dx = via.x() - origin.x();
    int dy = via.y() - origin.y();
    if (Math.abs(dx) > 1 || Math.abs(dy) > 1) {
      throw new FieldException("Via is not adjacent: " + via);
    }
    Field jump = new Field(origin.x() + 2 * dx, origin.y() + 2 * dy);
    return getAllFields().contains(jump) ? jump : null;
  }
}