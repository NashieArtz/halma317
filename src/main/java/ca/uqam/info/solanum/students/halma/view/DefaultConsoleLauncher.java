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
import java.util.List;

/**
 * Console launcher pour Halma TP4.
 */
public class DefaultConsoleLauncher {
  /**
   * Constructeur par défaut.
   */
  public DefaultConsoleLauncher() {
  }

  /**
   * Point d’entrée de l’application.
   *
   * @param args premier argument = taille de base, suivi des noms de joueurs ou "IA
   */
  public static void main(String[] args) {
    Tp4 config = analyserArguments(args);
    MoveSelector[] selecteurrs = createSelectors(config.playerNames);
    playGame(config, selecteurrs);
  }

  /**
   * Analyse et valide les arguments passés en ligne de commande.
   *
   * @param args arguments utilisateur
   * @return Tp4 contenant baseSize and playerNames
   */
  private static Tp4 analyserArguments(String[] args) {
    validateArgsLength(args);
    int baseSize = parserBaseSize(args[0]);
    String[] playerNames = extractPlayerNames(args);
    validatePlayerCount(baseSize, playerNames);
    return new Tp4(baseSize, playerNames);
  }

  /**
   * Vérifie que le tableau d’arguments est non nul et contient au moins deux éléments.
   *
   * @param args arguments utilisateur
   */
  private static void validateArgsLength(String[] args) {
    if (args == null || args.length < 2) {
      afficherEtSortir();
    }
  }

  /**
   * Convertit le premier argument en entier (baseSize).
   *
   * @param arg premier argument
   * @return baseSize
   */
  private static int parserBaseSize(String arg) {
    try {
      return Integer.parseInt(arg);
    } catch (NumberFormatException e) {
      System.err.println("Erreur: l'argument doit être un entier.");
      afficherEtSortir();
      return -1;
    }
  }

  /**
   * Extrait les noms de joueurs.
   *
   * @param args argument utilisateur
   * @return tableau noms des joueurs
   */
  private static String[] extractPlayerNames(String[] args) {
    return Arrays.copyOfRange(args, 1, args.length);
  }

  /**
   * Vérifie si le nombre de joueurs correspond à la taille de la base.
   *
   * @param baseSize    taille du jeu
   * @param playerNames tableau des noms des joueurs
   */
  private static void validatePlayerCount(int baseSize, String[] playerNames) {
    int expected = (baseSize == 1) ? 3 : (baseSize == 2) ? 6 : -1;
    if (expected < 0 || playerNames.length != expected) {
      System.err.printf("Erreur: baseSize=%d doit avoit %d players. Retour: %d.%n",
          baseSize, expected, playerNames.length);
      afficherEtSortir();
    }
  }

  /**
   * Créer un tableau des choix de mouvement, selon les noms.
   *
   * @param playerNames noms de joueurs ou AI
   * @return tableau de MoveSelector
   */
  private static MoveSelector[] createSelectors(String[] playerNames) {
    // Récupère la classe IA
    String aiName = System.getProperty("ai.class");
    MoveSelector[] selectors = new MoveSelector[playerNames.length];
    for (int i = 0; i < playerNames.length; i++) {
      if ("AI".equalsIgnoreCase(playerNames[i])) {
        // IA
        selectors[i] = instantiateAi(aiName);
      } else {
        // Humain
        selectors[i] = new InteractiveMoveSelector();
      }
    }
    return selectors;
  }

  /**
   * Lance la boucle du jeu.
   * Affiche l'état, demande au MoveSelector de choisir un mouvement,
   * exécute le mouvement, receommence.
   *
   * @param config    configuration du jeu
   * @param selectors MoveSelectors pour chaque joueur
   */
  private static void playGame(Tp4 config, MoveSelector[] selectors) {
    Controller controller = new ControllerImpl(
        new StarModelFactory(), config.baseSize, config.playerNames);
    TextualVisualizer textualVisualizer = new TextualVisualizer(true);
    // Boucle du jeu
    while (!controller.isGameOver()) {
      // Efface la console
      textualVisualizer.clearScreen();
      System.out.println(textualVisualizer.stringifyModel(controller.getModel()));
      System.out.println(textualVisualizer.getCurrentPlayerAnnouncement(controller.getModel()));
      System.out.println(textualVisualizer.announcePossibleMoves(controller.getPlayerMoves()));
      performMove(controller, textualVisualizer, selectors);
    }
    System.out.println(textualVisualizer.stringifyModel(controller.getModel()));
    System.out.println("GAME OVER!");
  }

  /**
   * Choisis le prochain mouvement.
   *
   * @param controller        controller de la partie
   * @param textualVisualizer affichage
   * @param selectors         tableau de MoveSelectors
   */
  private static void performMove(Controller controller, TextualVisualizer textualVisualizer,
                                  MoveSelector[] selectors) {
    // Liste des mouvements disponibles
    List<Move> moves = controller.getPlayerMoves();
    Move selector;
    if (moves.size() > 1) {
      int currentPlayer = controller.getModel().getCurrentPlayer();
      // Appel humain/AI
      selector = selectors[currentPlayer].selectMove(moves);
    } else {
      selector = moves.get(0);
    }
    // Annonce du choix
    System.out.println(
        textualVisualizer.getChoseMoveAnnouncement(
            selector,
            controller.getModel().getCurrentPlayer()
        )
    );
    try {
      controller.performMove(selector);
    } catch (IllegalMoveException e) {
      System.err.println("Mouvement illégal: " + e.getMessage());
      System.exit(1);
    }
    System.out.println("\n\n");
  }

  /**
   * Charge dynamiquement en instnacie l'IA.
   *
   * @param aiName nom complet de la classe IA
   * @return instance de MoveSelector
   * @throws RuntimeException si la classe n'existe pas ou ne peut pas être instanciée
   */
  private static MoveSelector instantiateAi(String aiName) {
    // Vérification de la classe AI
    if (aiName == null || aiName.isEmpty()) {
      throw new IllegalArgumentException("Propriété AI non définie");
    }
    try {
      // Charge la classe en mémoire
      Class<?> classe = Class.forName(aiName);
      // Nouvelle instance
      return (MoveSelector) classe.getConstructor().newInstance();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("IA introuvable: " + aiName, e);
    } catch (Exception e) {
      throw new RuntimeException("Erreur durant de la création de l'IA: " + aiName, e);
    }
  }

  /**
   * Imprime la commande d'usage.
   */
  private static void afficherEtSortir() {
    System.err.println("Commande: java -jar halma.jar <baseSize> <player1> ... <playerN>");
    System.exit(1);
  }

  /**
   * Stocker la configuration de lancement.
   */
  private record Tp4(int baseSize, String[] playerNames) {
  }
}