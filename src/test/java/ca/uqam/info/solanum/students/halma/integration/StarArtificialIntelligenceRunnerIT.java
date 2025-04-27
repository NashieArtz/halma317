package ca.uqam.info.solanum.students.halma.integration;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.integration.AbstractStarArtificialIntelligenceRunnerIntegrationTest;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;
import ca.uqam.info.solanum.students.halma.ai.KeksliMoveSelector;
import ca.uqam.info.solanum.students.halma.ai.MadMaxMoveSelector;
import ca.uqam.info.solanum.students.halma.controller.StarModelFactory;
import ca.uqam.info.solanum.students.halma.controller.ControllerImpl;

/**
 * Integration test for AI runners (suffixe IT nécessaire).
 */
public class StarArtificialIntelligenceRunnerIT extends AbstractStarArtificialIntelligenceRunnerIntegrationTest {

  @Override
  public MoveSelector getKeksli() {
    return new KeksliMoveSelector();
  }

  @Override
  public MoveSelector getMadMax(int seed) {
    return new MadMaxMoveSelector(seed);
  }

  @Override
  public ModelFactory getModelFactory() {
    return new StarModelFactory();
  }

  @Override
  public Controller getHalmaController(ModelFactory modelFactory, int baseSize, String[] playerNames) {
    return new ControllerImpl(modelFactory, baseSize, playerNames);
  }

  @Override
  public boolean isWithTraces() {
    return false;
  }
}