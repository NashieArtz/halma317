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
  final int[][] occupant;
  private int baseSizeBoard;

  /**
   * Constructeur de ModelImpl qui prend baseSize et playerNames.
   *
   * @param baseSize    taille du plataeau.
   * @param playerNames list des noms des joueurs.
   */
  public ModelImpl(int baseSize, String[] playerNames) {
    this.board = new BoardImpl(baseSize);
    this.playerNames = playerNames.clone();
    this.occupant = new int[4 * baseSize + 1][6 * baseSize + 1];
    for (int[] row : occupant) {
      Arrays.fill(row, -1);
    }
    this.currentPlayer = 0;
    for (int p = 0; p < playerNames.length; p++) {
      for (Field f : board.getHomeFieldsForPlayer(p)) {
        occupant[f.x()][f.y()] = p;
      }
    }
  }

  @Override
  public void occupyField(int playerNum, Field field) throws FieldException {
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Invalid field: " + field);
    }
    occupant[field.x()][field.y()] = playerNum;
  }

  @Override
  public void clearField(Field field) throws FieldException, ModelAccessConsistencyException {
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Invalid field: " + field);
    }
    if (occupant[field.x()][field.y()] < 0) {
      throw new ModelAccessConsistencyException("Field not occupied: " + field);
    }
    occupant[field.x()][field.y()] = -1;
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
  public Set<Field> getPlayerFields(int playerIndex) {
    Set<Field> result = new HashSet<>();
    for (Field f : board.getAllFields()) {
      if (occupant[f.x()][f.y()] == playerIndex) {
        result.add(f);
      }
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
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Invalid field: " + field);
    }
    return occupant[field.x()][field.y()] < 0;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ModelImpl)) {
      return false;
    }
    ModelImpl that = (ModelImpl) o;
    return Arrays.deepEquals(occupant, that.occupant)
        && Objects.equals(board, that.board)
        && Arrays.equals(playerNames, that.playerNames);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(board, Arrays.hashCode(playerNames));
    result = 31 * result + Arrays.deepHashCode(occupant);
    return result;
  }
}


