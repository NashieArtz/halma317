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
    // Protège état interne
    return Collections.unmodifiableSet(all);
  }

  /**
   * Vérifie si la coordonnée est dans l'étoile.
   *
   * @param x     coordonnée x
   * @param y     coordonnée y
   * @param cols  nombre de colonnes
   * @param lines nombre de lignes
   * @return true si à l'intérieur, false sinon
   */
  private boolean inStar(int x, int y, int cols, int lines) {
    // Partie gauche et droite de l'étoile
    return (x >= baseSize && y >= x - baseSize - 1 && y <= lines - 1 - x + baseSize)
        || (x <= cols - 1 - baseSize && y >= cols - 1 - baseSize - x - 1
        && y <= lines - 1 - (cols - 1 - baseSize - x));
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
    Set<Field> home = new HashSet<>();
    // Dimensions
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
    // Ajoute la pointe
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    // Direction verticale
    int dirY = top ? 1 : -1;
    // Ajoute la rangée suivante
    set.add(new Field(x - 1, y + dirY));
    set.add(new Field(x, y + 2 * dirY));
    set.add(new Field(x + 1, y + dirY));
  }

  /**
   * Configure une zone home ou target pour chaque joueur.
   *
   * @param set Le set où les fields seront ajoutés
   * @param x   Coordonnée X
   * @param y   Coordonnée Y
   */
  private void configureSideArea(Set<Field> set, int x, int y) {
    // Ajoute la case centrale
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    // Direction horizontale
    int dirX = (x == 0) ? 1 : -1;
    set.add(new Field(x + dirX, y + 1));
    set.add(new Field(x + dirX, y - baseSize + 1));
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
    int lines = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0 -> configurePointedArea(target, baseSize, lines - 1, false);
      case 1 -> configurePointedArea(target, cols - baseSize - 1, lines - 1, false);
      case 2 -> configureSideArea(target, 0, lines / 2);
      case 3 -> configurePointedArea(target, baseSize, 0, true);
      case 4 -> configurePointedArea(target, cols - baseSize - 1, 0, true);
      case 5 -> configureSideArea(target, cols - 1, lines / 2);
      default -> {
      }
    }
    return Collections.unmodifiableSet(target);
  }

  @Override
  public Set<Field> getNeighbours(Field field) throws FieldException {
    // Vérification validité de la case
    if (!getAllFields().contains(field)) {
      throw new FieldException("Field non présent sur le plateau " + field);
    }
    int x = field.x();
    int y = field.y();
    // Directions de déplacement autorisées
    int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, -1}, {1, 1},
        {-1, 1}, {1, -1}, {0, -2}, {0, 2}};
    Set<Field> neighbour = new HashSet<>();
    // Pour chaque direction, vérifier si la case existe
    for (int[] dir : directions) {
      Field field2 = new Field(x + dir[0], y + dir[1]);
      if (getAllFields().contains(field2)) {
        neighbour.add(field2);
      }
    }
    return Collections.unmodifiableSet(neighbour);
  }

  @Override
  public Field getExtendedNeighbour(Field fieldOrigin, Field fieldNeighbour) throws FieldException {
    // Vérifier l'existance des cases
    if (!getAllFields().contains(fieldOrigin) || !getAllFields().contains(fieldNeighbour)) {
      throw new FieldException(
          "Field ou NeighbhourField invalide: " + fieldOrigin + "/" + fieldNeighbour);
    }
    int dirX = fieldNeighbour.x() - fieldOrigin.x();
    int dirY = fieldNeighbour.y() - fieldOrigin.y();
    // Vérifier l'adjacence
    if (Math.abs(dirX) > 1 || Math.abs(dirY) > 1) {
      throw new FieldException("Via is not adjacent: " + fieldNeighbour);
    }
    // Calcul du saut
    Field jump = new Field(fieldOrigin.x() + 2 * dirX, fieldOrigin.y() + 2 * dirY);
    // Retourne jump si valide, sinon null
    return getAllFields().contains(jump) ? jump : null;
  }
}