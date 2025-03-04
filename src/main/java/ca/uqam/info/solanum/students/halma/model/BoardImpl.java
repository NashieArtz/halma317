package ca.uqam.info.solanum.students.halma.model;

import static ca.uqam.info.solanum.students.halma.controller.StarModelFactory.baseSize;

import ca.uqam.info.solanum.inf2050.f24halma.model.Board;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Implémentation du plateau de jeu.
 * La classe représente le plateau de jeu en fournissant les coordonnées des cases valides ainsi
 * que les positions de départ et d'arrivée des joueurs.
 * Elle permet également de récupérer les voisins immédiats et étendus d'un champ.
 */
public class BoardImpl implements Board {

  /**
   * Constructeur par défaut de BoardImpl.
   * Initialise une instance du plateau de jeu.
   */
  public BoardImpl() {
    // Default constructor
  }

  @Override
  public Set<Field> getAllFields() {
    Set<Field> emptyFields = new HashSet<>();
    int nbrColonne = 4 * baseSize + 1;
    int nbrLignes = 6 * baseSize + 1;
    String[][] board = new String[nbrColonne][nbrLignes];
    for (int y = 0; y < board[0].length; y++) {
      for (int x = 0; x < board.length; x++) {
        if (baseSize % 2 == (x + y) % 2) {
          if ((x > baseSize - 1 && (y >= x - baseSize && y <= board[0].length - x + baseSize))
              || (x <= board.length - baseSize - 1 && (y >= board.length - baseSize - x - 1
              && y <= board[0].length - (board.length - x - baseSize)))) {
            emptyFields.add(new Field(x, y));
          }
        }
      }
    }
    return emptyFields;
  }

  @Override
  public Set<Field> getHomeFieldsForPlayer(int i) {
    return Set.of();
  }

  @Override
  public Set<Field> getAllHomeFields() {
    Set<Field> playerFields = new HashSet<>();
    // Nombres de colonnes.
    int x = baseSize * 4 + 1;
    // Nombres de lignes.
    int y = baseSize * 6 + 1;
    // Position de base.
    playerFields.add(new Field(0, y / 2));
    playerFields.add(new Field(x / 4, y - 1));
    playerFields.add(new Field(x / 4, 0));
    playerFields.add(new Field(x - 1, baseSize * 3));
    playerFields.add(new Field(baseSize * 3, baseSize * 6));
    playerFields.add(new Field(x / 4 + baseSize * 2, 0));
    return playerFields;
  }

  @Override
  public Set<Field> getTargetFieldsForPlayer(int i) {
    return Set.of();
  }

  @Override
  public Set<Field> getNeighbours(Field field) {
    return Set.of();
  }

  @Override
  public Field getExtendedNeighbour(Field field, Field field1) {
    return null;
  }
}
