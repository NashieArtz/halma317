package ca.uqam.info.solanum.students.halma.view;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.IllegalMoveException;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.view.InteractiveMoveSelector;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;
import ca.uqam.info.solanum.inf2050.f24halma.view.TextualVisualizer;
import ca.uqam.info.solanum.students.halma.controller.ControllerImpl;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;
import java.util.Arrays;

/**
 * Console launcher for Halma TP4, refactored to reduce cyclomatic complexity.
 */
public class DefaultConsoleLauncher {

  /**
   * Entry point of the application.
   *
   * @param args first argument is baseSize, followed by player names or "AI.
   */
  public static void main(String[] args) {
    LaunchConfig cfg = parseArgs(args);
    MoveSelector[] selectors = createSelectors(cfg.playerNames);
    playGame(cfg, selectors);
  }

  /**
   * Parses and validates command-line arguments.
   *
   * @param args input arguments
   * @return LaunchConfig containing baseSize and playerNames
   */
  private static LaunchConfig parseArgs(String[] args) {
    validateArgsLength(args);
    int baseSize = parseBaseSize(args[0]);
    String[] playerNames = extractPlayerNames(args);
    validatePlayerCount(baseSize, playerNames);
    return new LaunchConfig(baseSize, playerNames);
  }

  /**
   * test.
   *
   * @param args test
   */
  private static void validateArgsLength(String[] args) {
    if (args == null || args.length < 2) {
      usageAndExit();
    }
  }

  /**
   * test.
   *
   * @param arg test
   */
  private static int parseBaseSize(String arg) {
    try {
      return Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      System.err.println("Error: first argument must be an integer (baseSize).");
      usageAndExit();
      return -1; // unreachable
    }
  }

  /**
   * test.
   *
   * @param args test
   */
  private static String[] extractPlayerNames(String[] args) {
    return Arrays.copyOfRange(args, 1, args.length);
  }

  /**
   * test.
   *
   * @param baseSize test
   * @param names test
   */
  private static void validatePlayerCount(int baseSize, String[] names) {
    int expected = (baseSize == 1) ? 3 : (baseSize == 2) ? 6 : -1;
    if (expected < 0 || names.length != expected) {
      System.err.printf("Error: baseSize=%d requires %d players, got %d.%n",
          baseSize, expected, names.length);
      usageAndExit();
    }
  }

  /**
   * Instantiates MoveSelectors: human or dynamic AI.
   *
   * @param names player names or "AI"
   * @return array of MoveSelector
   */
  private static MoveSelector[] createSelectors(String[] names) {
    String aiClass = System.getProperty("ai.class");
    MoveSelector[] selectors = new MoveSelector[names.length];
    for (int i = 0; i < names.length; i++) {
      if ("AI".equalsIgnoreCase(names[i])) {
        selectors[i] = instantiateAi(aiClass);
      } else {
        selectors[i] = new InteractiveMoveSelector();
      }
    }
    return selectors;
  }

  /**
   * Runs the main game loop.
   *
   * @param cfg configuration of the launch
   * @param selectors MoveSelectors for each player
   */
  private static void playGame(LaunchConfig cfg, MoveSelector[] selectors) {
    Controller ctrl = new ControllerImpl(
        new StarModelFactory(), cfg.baseSize, cfg.playerNames);
    TextualVisualizer viz = new TextualVisualizer(true);

    while (!ctrl.isGameOver()) {
      viz.clearScreen();
      System.out.println(viz.stringifyModel(ctrl.getModel()));
      System.out.println(viz.getCurrentPlayerAnnouncement(ctrl.getModel()));
      System.out.println(viz.announcePossibleMoves(ctrl.getPlayerMoves()));
      performMove(ctrl, viz, selectors);
    }
    System.out.println(viz.stringifyModel(ctrl.getModel()));
    System.out.println("GAME OVER!");
  }

  /**
   * Selects and performs the next move.
   */
  private static void performMove(Controller ctrl, TextualVisualizer viz,
                                  MoveSelector[] selectors) {
    var moves = ctrl.getPlayerMoves();
    Move selected = (moves.size() > 1)
        ? selectors[ctrl.getModel().getCurrentPlayer()].selectMove(moves)
        : moves.getFirst();
    System.out.println(viz.getChoseMoveAnnouncement(
        selected, ctrl.getModel().getCurrentPlayer()));
    try {
      ctrl.performMove(selected);
    } catch (IllegalMoveException e) {
      System.err.println("Illegal move: " + e.getMessage());
      System.exit(1);
    }
    System.out.println("\n\n");
  }

  /**
   * Dynamically loads and instantiates an AI MoveSelector.
   *
   * @param aiClass fully qualified class name
   * @return MoveSelector instance
   */
  private static MoveSelector instantiateAi(String aiClass) {
    try {
      Class<?> cl = Class.forName(aiClass);
      return (MoveSelector) cl.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Cannot load AI class: " + aiClass, e);
    }
  }

  /**
   * Prints usage message and exits.
   */
  private static void usageAndExit() {
    System.err.println("Usage: java -jar halma.jar <baseSize> <player1> ... <playerN>");
    System.exit(1);
  }

  /**
   * Holder for launch configuration.
   */
  private record LaunchConfig(int baseSize, String[] playerNames) {}
}