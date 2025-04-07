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
    Set<Field> emptyFields = new HashSet<>();
    int colonnes = 4 * baseSize + 1;
    int lignes = 6 * baseSize + 1;
    for (int y = 0; y < lignes; y++) {
      for (int x = 0; x < colonnes; x++) {
        if (isValidField(x, y, colonnes, lignes)) {
          emptyFields.add(new Field(x, y));
        }
      }
    }
    return Collections.unmodifiableSet(emptyFields);
  }


  /**
   * Vérifie la position d'un champ sur le plateau.
   *
   * @param x        Coordonnée x
   * @param y        Coordonnée y
   * @param colonnes Nombre total de colonnes
   * @param lignes   Nombre total de lignes
   * @return true si le champ a une condition valide et est dans une zone du plateau
   */
  private boolean isValidField(int x, int y, int colonnes, int lignes) {
    return hasValidCondition(x, y)
        && (isUpperTriangle(x, y, lignes)
        || isLowerTriangle(x, y, colonnes, lignes));
  }

  /**
   * Vérifie la partié selon la taille de la base.
   *
   * @param x Coordonnée x
   * @param y Coordonnée y
   * @return true si (x+y) a la même valeur que baseSize
   */
  private boolean hasValidCondition(int x, int y) {
    return (baseSize % 2) == ((x + y) % 2);
  }

  /**
   * Vérifie si un champ fait partie du triangle supérieur.
   *
   * @param x      Coordonnée x
   * @param y      Coordonnée y
   * @param lignes   Nombre total de lignes
   * @return true si le champ est dans le triangle supérieur
   */
  private boolean isUpperTriangle(int x, int y, int lignes) {
    return x >= baseSize
        && (y >= x - baseSize - 1)
        && (y <= ((lignes - 1) - x + baseSize));
  }

  /**
   * Vérifie si un champ fait partie du triangle inférieur.
   *
   * @param x        Coordonnée x
   * @param y        Coordonnée y
   * @param colonnes Nombre total de colonnes
   * @param lignes   Nombre total de lignes
   * @return true si le champ est dans le triangle inférieur
   */
  private boolean isLowerTriangle(int x, int y, int colonnes, int lignes) {
    return x <= (colonnes - baseSize - 1)
        && y >= (((colonnes - 1) - baseSize) - x - 1)
        && y <= ((lignes - 1) - ((colonnes - x - 1) - baseSize));
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
    Set<Field> homeField = new HashSet<>();
    int colonnes = 4 * baseSize + 1;
    int lignes = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0:
        configurePointedArea(homeField, baseSize, 0, colonnes, lignes, true);
        break;
      case 1:
        configurePointedArea(homeField, colonnes - baseSize - 1, 0, colonnes, lignes, true);
        break;
      case 2:
        configureSideArea(homeField, 0, lignes / 2, colonnes, lignes);
        break;
      case 3:
        configurePointedArea(homeField, baseSize, lignes - 1, colonnes, lignes, false);
        break;
      case 4:
        configurePointedArea(homeField, colonnes - baseSize - 1, lignes - 1, colonnes, lignes,
            false);
        break;
      case 5:
        configureSideArea(homeField, colonnes - 1, lignes / 2, colonnes, lignes);
        break;
      default:
        break;
    }
    return Collections.unmodifiableSet(homeField);
  }

  /**
   * test.
   *
   * @param set      test.
   * @param x        test.
   * @param y        test.
   * @param colonnes test.
   * @param lignes   test.
   * @param isTop    test.
   */
  private void configurePointedArea(Set<Field> set, int x, int y, int colonnes, int lignes,
                                    boolean isTop) {
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    int modifierY = isTop ? 1 : -1;
    set.add(new Field(x - 1, y + modifierY));
    set.add(new Field(x, y + 2 * modifierY));
    set.add(new Field(x + 1, y + modifierY));
  }

  /**
   * test.
   *
   * @param set      test.
   * @param x        test.
   * @param y        test.
   * @param colonnes test.
   * @param lignes   test.
   */
  private void configureSideArea(Set<Field> set, int x, int y, int colonnes, int lignes) {
    set.add(new Field(x, y));
    if (baseSize < 2) {
      return;
    }
    set.add(new Field(x + (x == 0 ? 1 : -1), y + 1));
    set.add(new Field(x + (x == 0 ? 1 : -1), y - baseSize + 1));
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
  public Set<Field> getTargetFieldsForPlayer(int i) {
    return Set.of();
  }

  @Override
  public Set<Field> getNeighbours(Field field) {
    if (!getAllFields().contains(field)) {
      throw new RuntimeException(
          new FieldException("Field pas dans le plateau: " + field.toString()));
    }
    // Coordonnées du field
    int x = field.x();
    int y = field.y();
    // Directions possibles
    int[][] directions = {
        {-1, 0}, {1, 0}, {0, -1}, {0, 1},
        {-1, -1}, {1, 1}, {-1, 1}, {1, -1},
        {0, -2}, {0, 2}
    };
    Set<Field> voisin = new HashSet<>();
    for (int[] d : directions) {
      // Calculer les coordonées du voisin
      Field voisinValide = new Field(x + d[0], y + d[1]);
      if (getAllFields().contains(voisinValide)) {
        voisin.add(voisinValide);
      }
    }
    return Collections.unmodifiableSet(voisin);
  }

  @Override
  public Field getExtendedNeighbour(Field originField, Field voisinField) {
    if (!getAllFields().contains(originField)
        || !getAllFields().contains(voisinField)) {
      throw new RuntimeException(
          new FieldException("Invalid origin or via field: " + originField + " / " + voisinField));
    }
    // Direction du saut
    int directX = voisinField.x() - originField.x();
    int directY = voisinField.y() - originField.y();
    if (directX > 1 || directY > 1) {
      throw new FieldException("Via field is not a direct neighbor of origin: " + voisinField);
    }
    // Coordonnées du voisin étendu
    Field saut = new Field(originField.x() + 2 * directX, originField.y() + 2 * directY);
    if (getAllFields().contains(saut)) {
      return saut;
    } else {
      return null;
    }
  }
}