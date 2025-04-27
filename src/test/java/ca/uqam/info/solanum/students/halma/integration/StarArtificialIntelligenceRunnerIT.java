package ca.uqam.info.solanum.students.halma.integration;

import ca.uqam.info.solanum.inf2050.f24halma.controller.Controller;
import ca.uqam.info.solanum.inf2050.f24halma.controller.ModelFactory;
import ca.uqam.info.solanum.inf2050.f24halma.integration.AbstractStarArtificialIntelligenceRunnerIntegrationTest;
import ca.uqam.info.solanum.inf2050.f24halma.view.MoveSelector;

/**
 * Integration test for AI runners (suffixe IT nécessaire).
 */
public class StarArtificialIntelligenceRunnerIT extends AbstractStarArtificialIntelligenceRunnerIntegrationTest {

  @Override
  public MoveSelector getKeksli() {
    return null;
  }

  @Override
  public MoveSelector getMadMax(int i) {
    return null;
  }

  @Override
  public ModelFactory getModelFactory() {
    return null;
  }

  @Override
  public Controller getHalmaController(ModelFactory modelFactory, int i, String[] strings) {
    return null;
  }

  @Override
  public boolean isWithTraces() {
    return false;
  }
}