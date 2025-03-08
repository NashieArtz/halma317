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
    // Validation du field
    if (!getAllFields().contains(field)) {
      throw new FieldException("Field pas sur le jeu " + field.toString());
    }
    // Coordonnées du field
    int x = field.x();
    int y = field.y();
    // Directions possibles
    int[][] directions = {
        // Gauche
        {-1, 0},
        // Droite
        {1, 0},
        // Haut
        {0, -1},
        // Bas
        {0, 1},
        // Haut-Gauche
        {-1, -1},
        // Haut-Droite
        {1, -1},
        // Bas-Droite
        {1, 1},
        // Bas-Gauche
        {-1, 1}
    };
    Set<Field> voisin = new HashSet<>();
    for (int[] d : directions) {
      // Calculer les coordoonées du voisin
      int newX = x + d[0];
      int newY = y + d[1];
      Field voisinValide = new Field(newX, newY);
      if (getAllFields().contains(voisinValide)) {
        voisin.add(voisinValide);
      }
    }
    return Collections.unmodifiableSet(voisin);
  }

  @Override
  public Field getExtendedNeighbour(Field originField, Field voisinField) {
    // Check validité originField et voisinField
    if (!getAllFields().contains(originField)) {
      throw new FieldException("Field originel invalide " + originField);
    }
    if (!getAllFields().contains(voisinField)) {
      throw new FieldException("Field voisin invalide " + voisinField);
    }
    // Direction du saut
    int direcX = voisinField.x() - originField.x();
    int direcY = voisinField.y() - originField.y();
    // Coordonnées du voisin étendu
    int etenduX = originField.x() + 2 * direcX;
    int etenduY = originField.y() + 2 * direcY;
    Field saut = new Field(etenduX, etenduY);
    if (getAllFields().contains(saut)) {
      return saut;
    } else {
      return null;
    }
  }
}
