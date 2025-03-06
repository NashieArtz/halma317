package ca.uqam.info.solanum.students.halma.model;

import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.Model;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Implémentation du modèle du jeu Halma.
 * La classe gère les opérations du modèle comme l'occupation des cases par les joueurs.
 */
public class ModelImpl implements Model, ModelReadOnly {
  private final BoardImpl board;
  private final String[] playerNames;
  private int currentPlayer = 0;
  // occupantMap: which player occupies each Field?
  // Key = Field, Value = playerIndex
  private final Map<Field, Integer> occupantMap = new HashMap<>();

  /**
   * Constructeur de ModelImpl qui prend baseSize et playerNames.
   *
   * @param baseSize    TO REPLACE.
   * @param playerNames TO REPLACE.
   */
  public ModelImpl(int baseSize, String[] playerNames) {
    this.board = new BoardImpl(baseSize);
    this.playerNames = playerNames != null ? playerNames.clone() : new String[0];
    this.currentPlayer = 0;
    // Optionally: pre-occupy fields for each player if the tests want
    // "Initial figure positions for player 0" to be (01,05), (00,06), ...
    // You can loop over board.getHomeFieldsForPlayer(...) and add them here:
    for (int p = 0; p < this.playerNames.length; p++) {
      for (Field f : board.getHomeFieldsForPlayer(p)) {
        occupantMap.put(f, p);
      }
    }
  }

  @Override
  public void occupyField(int playerIndex, Field field) throws FieldException {
    // Validate
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Field is not on the board: " + field);
    }
    // Maybe the tests want you to check if field is already occupied, etc.
    occupantMap.put(field, playerIndex);
  }

  @Override
  public void clearField(Field field) throws FieldException {
    if (!board.getAllFields().contains(field)) {
      throw new FieldException("Cannot clear a field off-board: " + field);
    }
    occupantMap.remove(field);
  }

  @Override
  public void setCurrentPlayer(int playerIndex) {
    // Some tests expect an exception if it’s out of range:
    if (playerIndex < 0 || playerIndex >= playerNames.length) {
      // The test might expect a ModelAccessConsistencyException
      // or something similar. Adjust as needed.
      throw new RuntimeException("Player index out of range: " + playerIndex);
    }
    this.currentPlayer = playerIndex;
  }

  @Override
  public String[] getPlayerNames() {
    return playerNames.clone();
  }

  @Override
  public Set<Field> getPlayerFields(int i) {
    return Set.of();
  }

  @Override
  public int getCurrentPlayer() {
    return 0;
  }

  @Override
  public BoardImpl getBoard() {
    return board;
  }

  @Override
  public boolean isClear(Field field) throws FieldException {
    return false;
  }
}

