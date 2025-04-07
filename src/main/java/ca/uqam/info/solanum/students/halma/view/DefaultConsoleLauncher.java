package ca.uqam.info.solanum.students.halma.view;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.controller.Move;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import ca.uqam.info.solanum.inf2050.f24halma.view.InteractiveMoveSelector;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;
import ca.uqam.info.solanum.inf2050.f24halma.view.TextualVisualizer;
import ca.uqam.info.solanum.students.halma.controller.ControllerImpl;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;
import java.util.List;

/**
 * Sample console launcher, to start TP code.
 */
public class DefaultConsoleLauncher {
  /**
   * Default Constructor.
   */
  public DefaultConsoleLauncher() {
  }

  /**
   * Main class for the console launcher.
   *
   * @param args no arguments required.
   */
  public static void main(String[] args) {
    runTp01();
    runTp03(args);
  }

  private static void runTp01() {
    // Set default parameters.
    int baseSize = 2;
    String[] playerNames = new String[] {"Max", "Maram", "Roman"};
    // Create a model (read only access) for the provided game parameters.
    ModelFactory modelFactory = new StarModelFactory();
    ModelReadOnly model = modelFactory.createModel(baseSize, playerNames);
    // Visualize initial model state.
    boolean useColours = false;
    TextualVisualizer visualizer = new TextualVisualizer(useColours);
    System.out.println(visualizer.stringifyModel(model));

  }

  private static void runTp03(String[] args) {
    // Parse runtime parameters
    int baseSize = Integer.parseInt(args[0]);
    String[] playerNames = null;
    // Appellez votre implementation "StarModelFactory" ici.
    ModelFactory modelFactory = new StarModelFactory();
    // Set move selectors // ... plus tard on va utiliser des IA pour choisier des actions.
    // Pour l'instant le choix des options est interactif (et l'implémentation est fournie)
    MoveSelector[] moveSelectors = playerNamesToMoveSelectors(playerNames);
    // Initialize controller
    Controller controller = new ControllerImpl(modelFactory, baseSize, playerNames);
    // Initialize visualizer
    boolean useColours = true; // Set to false if you're on windows and textual output looks weird.
    TextualVisualizer visualizer = new TextualVisualizer(useColours);
    // Proceed until game end
    while (!controller.isGameOver()) { // Voila la boucle prinicipale... continuer le jeu jusqu'à
      // une personne a gagné
      printAndRequestAndPerformAction(controller, visualizer, moveSelectors);
    }
    System.out.println(visualizer.stringifyModel(controller.getModel()));
    System.out.println("GAME OVER!");
  }

  // Plus tard (TP4), quand on utilise des IAs, il y aura des autres "MoveSelectors" qui ne sont
  // pas interactifs mais font leur propre choix.
  private static MoveSelector[] playerNamesToMoveSelectors(String[] playerNames) {
    MoveSelector[] moveSelectors = new MoveSelector[playerNames.length];
    for (int i = 0; i < moveSelectors.length; i++) {
      moveSelectors[i] = new InteractiveMoveSelector();
    }
    return moveSelectors;
  }

  private static void printAndRequestAndPerformAction(Controller controller,
                                                      TextualVisualizer visualizer,
                                                      MoveSelector[] moveSelectors) {
    // Clear the screen (Works only on native ANSI terminals, not in IDE / windows)
    visualizer.clearScreen(); // Comment out this line if you're on windows.
    // Retrieve and visualize model
    System.out.println(visualizer.stringifyModel(controller.getModel()));
    // Print all possible actions:
    System.out.println(visualizer.getCurrentPlayerAnnouncement(controller.getModel()));
    System.out.println(visualizer.announcePossibleMoves(controller.getPlayerMoves()));
    // Request action and visualize choice (for current player)
    int currentPlayer = controller.getModel().getCurrentPlayer();
    List<Move> availableMoves = controller.getPlayerMoves();
    // if more than one move, ask selector
    Move selectedMove = null;
    if (availableMoves.size() > 1) {
      selectedMove = moveSelectors[currentPlayer].selectMove(availableMoves);
    } else {
      // If only one move available, directly apply it.
      selectedMove = availableMoves.getFirst();
    }
    System.out.println(visualizer.getChoseMoveAnnouncement(selectedMove, currentPlayer));
    // Perform selected action:
    controller.performMove(selectedMove);
    System.out.println("\n\n");
  }
}