package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Implémentation du modèle du jeu Halma.
 * La classe gère les opérations du modèle comme l'occupation des cases par les joueurs.
 */
public class ModelImpl implements Model, ModelReadOnly {
  private final BoardImpl board;
  private final String[] playerNames;
  private int currentPlayer;
  private int baseSize;
  private final int[][] occupant;
  private int baseSizeBoard;

  /**
   * Constructeur de ModelImpl qui prend baseSize et playerNames.
   *
   * @param baseSize    taille du plataeau.
   * @param playerNames list des noms des joueurs.
   */
  public ModelImpl(int baseSize, String[] playerNames) {
    this.board = new BoardImpl(baseSize);
    this.playerNames = playerNames != null ? playerNames.clone() : new String[0];
    this.currentPlayer = 0;
    // Initialiser à non-occupé
    int colonnes = 4 * baseSize + 1;
    int lignes = 6 * baseSize + 1;
    this.occupant = new int[colonnes][lignes];
    this.baseSizeBoard = baseSize;
    for (int y = 0; y < lignes; y++) {
      for (int x = 0; x < colonnes; x++) {
        occupant[x][y] = -1;
      }
    }
    // Occuper le field par le joueur
    for (int i = 0; i < this.playerNames.length; i++) {
      for (Field f : board.getHomeFieldsForPlayer(i)) {
        occupant[f.y()][f.x()] = i;
      }
    }
  }

  @Override
  public void occupyField(int playerNum, Field field) throws FieldException {
    // Check si un Field est sur le plateau.
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Field invalide: " + field);
    }
    // Occupe le field.
    occupant[field.y()][field.x()] = playerNum;
  }

  @Override
  public void clearField(Field field) throws FieldException, ModelAccessConsistencyException {
    // Check si un field est sur le plateau
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Field occupé: " + field);
    }
    // Check si le field est occupé
    if (occupant[field.y()][field.x()] == -1) {
      throw new ModelAccessConsistencyException("Field non-occupé " + field);
    }
    // Libère le field.
    occupant[field.y()][field.x()] = -1;
  }

  @Override
  public void setCurrentPlayer(int playerNum) throws ModelAccessConsistencyException {
    // Check si le joueur est valide
    if (playerNum < 0 || playerNum >= playerNames.length) {
      throw new ModelAccessConsistencyException("Joueur invalide: " + playerNum);
    }
    this.currentPlayer = playerNum;
  }

  @Override
  public String[] getPlayerNames() {
    return playerNames.clone();
  }

  @Override
  public Set<Field> getPlayerFields(int i) {
    Set<Field> result = new HashSet<>();
    int colonnes = 4 * baseSizeBoard + 1;
    int lignes = 6 * baseSizeBoard + 1;
    switch (i) {
      case 0:
        result.add(new Field(0, colonnes - 2));
        break;
      case 1:
        result.add(new Field(i * baseSizeBoard + 2, 0));
        break;
      case 2:
        result.add(new Field(i * baseSizeBoard + 1, colonnes + 1));
        break;
      case 3:
        result.add(new Field(colonnes - 1, lignes - baseSizeBoard - 1));
        break;
      case 4:
        result.add(new Field(baseSizeBoard, lignes / 2));
        break;
      case 5:
        result.add(new Field(colonnes - baseSizeBoard - 1, lignes / 2));
        break;
      default:
        break;
    }
    return Collections.unmodifiableSet(result);
  }

  @Override
  public int getCurrentPlayer() {
    return currentPlayer;
  }

  @Override
  public BoardImpl getBoard() {
    return board;
  }

  @Override
  public boolean isClear(Field field) throws FieldException {
    return false;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ModelImpl model = (ModelImpl) obj;
    return Objects.equals(board, model.board)
        && Arrays.equals(playerNames, model.playerNames)
        && Arrays.deepEquals(occupant, model.occupant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(baseSize, Arrays.hashCode(playerNames), Arrays.deepHashCode(occupant));
  }
}


