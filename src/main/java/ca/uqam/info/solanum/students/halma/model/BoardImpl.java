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
        if ((baseSize % 2) == ((x + y) % 2)) {
          boolean pointeHaut =
              (x >= baseSize)
                  && (y >= x - baseSize - 1)
                  && (y <= (lignes - 1) - x + baseSize);
          boolean pointeBas =
              (x <= (colonnes - 1 - baseSize))
                  && (y >= ((colonnes - 1) - baseSize) - x - 1)
                  && (y <= (lignes - 1) - ((colonnes - 1 - x) - baseSize));
          if (pointeHaut || pointeBas) {
            emptyFields.add(new Field(x, y));
          }
        }
      }
    }
    return Collections.unmodifiableSet(emptyFields);
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int playerIndex) {
    Set<Field> homeField = new HashSet<>();
    int colonnes = 4 * baseSize + 1;
    int lignes = 6 * baseSize + 1;
    switch (playerIndex) {
      case 0:
        // Pointe haut-gauche
        homeField.add(new Field(baseSize, 0));
        if (baseSize >= 2) {
          homeField.add(new Field(baseSize - 1, 1));
          homeField.add(new Field(baseSize, 2));
          homeField.add(new Field(baseSize + 1, 1));
        }
        break;
      case 1:
        // Pointe haut-droite
        homeField.add(new Field(colonnes - baseSize - 1, 0));
        if (baseSize >= 2) {
          homeField.add(new Field(colonnes - baseSize - 2, 1));
          homeField.add(new Field(colonnes - baseSize - 1, 2));
          homeField.add(new Field(colonnes - baseSize, 1));
        }
        break;
      case 2:
        // Pointe gauche
        homeField.add(new Field(0, lignes / 2));
        if (baseSize >= 2) {
          homeField.add(new Field(1, lignes / 2 + 1));
          homeField.add(new Field(1, lignes / 2 - baseSize + 1));
        }
        break;
      case 3:
        // Pointe bas-gauche
        homeField.add(new Field(baseSize, lignes - 1));
        if (baseSize >= 2) {
          homeField.add(new Field(baseSize - 1, lignes - 2));
          homeField.add(new Field(baseSize, lignes - 3));
          homeField.add(new Field(baseSize + 1, lignes - 2));
        }
        break;
      case 4:
        // Pointe bas-droite
        homeField.add(new Field(colonnes - baseSize - 1, lignes - 1));
        if (baseSize >= 2) {
          homeField.add(new Field(colonnes - baseSize - 2, lignes - 2));
          homeField.add(new Field(colonnes - baseSize - 1, lignes - 3));
          homeField.add(new Field(colonnes - baseSize, lignes - 2));
        }
        break;
      case 5:
        // Pointe droite
        homeField.add(new Field(colonnes - 1, lignes / 2));
        if (baseSize >= 2) {
          homeField.add(new Field(colonnes - 2, lignes / 2 + 1));
          homeField.add(new Field(colonnes - 2, lignes / 2 - baseSize + 1));
        }
        break;
      default:
        break;
    }
    return Collections.unmodifiableSet(homeField);
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
