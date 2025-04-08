package ca.uqam.info.solanum.students.halma.model;

import static org.junit.Assert.*;

import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import org.junit.Test;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;

public class ModelImplTest {

  @Test
  public void testOccupantMatrixInitialization() {
    ModelImpl model = new ModelImpl(1, new String[]{"P1", "P2"});
    assertEquals(-1, model.occupant[0][0]);
  }

  @Test(expected = ModelAccessConsistencyException.class)
  public void testClearNonOwnedField() throws Exception {
    ModelImpl model = new ModelImpl(1, new String[]{"P1", "P2"});
    Field f = model.getPlayerFields(0).iterator().next();
    model.clearField(f);
  }

  @Test
  public void testPlayerFieldsEdgeCase() {
    ModelImpl model = new ModelImpl(3, new String[]{"P1"});
    assertFalse(model.getPlayerFields(0).isEmpty());
  }

  @Test
  public void testEqualsHashCodeConsistency() {
    ModelImpl m1 = new ModelImpl(1, new String[]{"A"});
    ModelImpl m2 = new ModelImpl(1, new String[]{"A"});
    assertEquals(m1.hashCode(), m2.hashCode());
  }

}
