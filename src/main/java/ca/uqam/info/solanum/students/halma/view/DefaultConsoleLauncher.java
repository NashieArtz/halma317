package ca.uqam.info.solanum.students.halma.view;

import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelReadOnly;
import ca.uqam.info.solanum.inf2050.f24halma.view.TextualVisualizer;

// TODO: create something implementing ModelFactory in controller package, and import it here

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
    //    runTP01();
    //    runTP01();
  }

  private static void runTp01() {
    // Set default parameters
    int baseSize = 3;
    String[] playerNames = new String[] {"Max", "Maram", "Roman"};

    // Create a model (read only access) for the provided game parameters
    ModelFactory modelFactory =
        new ModelFactory(); // TODO: Create this class in YOUR package and import/use it here.
    ModelReadOnly model = modelFactory.createModel(baseSize, playerNames);

    // Visualize initial model state
    boolean useColours = false;
    TextualVisualizer visualizer = new TextualVisualizer(useColours);
    System.out.println(visualizer.stringifyModel(model));
  }

  private static void runTp02() {
    // Will be released with TP02 instructions.
  }

  private static void runTp03() {
    // Will be released with TP02 instructions.
  }
}