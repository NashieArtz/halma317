package ca.uqam.info.solanum.students.halma.model;

import org.junit.Test;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import static org.junit.Assert.*;
import java.util.Set;

public class BoardImplTest {

  @Test
  public void testAllFieldsNonEmpty() {
    assertFalse(new BoardImpl(1).getAllFields().isEmpty());
  }

  @Test
  public void testNeighboursCenter() throws Exception {
    BoardImpl b = new BoardImpl(1);
    Set<Field> n = b.getNeighbours(new Field(1,1));
    assertFalse(n.isEmpty());
  }

  @Test(expected = FieldException.class)
  public void testNeighboursInvalid() throws Exception {
    new BoardImpl(1).getNeighbours(new Field(10,10));
  }

  @Test
  public void testExtendedNeighbour() throws Exception {
    BoardImpl b = new BoardImpl(1);
    Field o = new Field(1,1), via = new Field(2,2);
    assertEquals(new Field(3,3), b.getExtendedNeighbour(o,via));
  }

  @Test(expected = FieldException.class)
  public void testExtendedNeighbourInvalid() throws Exception {
    new BoardImpl(1).getExtendedNeighbour(new Field(0,0), new Field(2,2));
  }
}