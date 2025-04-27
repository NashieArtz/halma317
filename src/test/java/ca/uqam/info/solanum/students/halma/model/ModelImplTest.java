package ca.uqam.info.solanum.students.halma.model;

import org.junit.Test;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import ca.uqam.info.solanum.inf2050.f24halma.model.ModelAccessConsistencyException;
import static org.junit.Assert.*;

public class ModelImplTest {

  @Test
  public void testOccupyAndClear() throws Exception {
    ModelImpl m = new ModelImpl(1, new String[]{"P1","P2","P3"});
    Field f = m.getPlayerFields(0).iterator().next();
    m.clearField(f);
    assertTrue(m.isClear(f));
    m.occupyField(0, f);
    assertFalse(m.isClear(f));
  }

  @Test(expected = FieldException.class)
  public void testOccupyInvalid() throws Exception {
    new ModelImpl(1, new String[]{"P1","P2","P3"}).occupyField(0, new Field(10,10));
  }

  @Test(expected = ModelAccessConsistencyException.class)
  public void testClearUnoccupied() throws Exception {
    new ModelImpl(1, new String[]{"P1","P2","P3"}).clearField(new Field(0,0));
  }
}