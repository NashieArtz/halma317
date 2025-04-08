package ca.uqam.info.solanum.students.halma.model;

import static org.junit.Assert.*;
import org.junit.Test;
import ca.uqam.info.solanum.inf2050.f24halma.model.Field;
import ca.uqam.info.solanum.inf2050.f24halma.model.FieldException;
import java.util.Set;

public class BoardImplTest {

  @Test
  public void testBaseSize1Validation() {
    BoardImpl board = new BoardImpl(1);
    assertEquals(25, board.getAllFields().size());
  }

  @Test
  public void testBaseSize2Validation() {
    BoardImpl board = new BoardImpl(2);
    assertTrue(board.getAllFields().size() > 40);
  }

  @Test
  public void testHomeFieldsPlayer0() {
    BoardImpl board = new BoardImpl(2);
    Set<Field> fields = board.getHomeFieldsForPlayer(0);
    assertTrue(fields.contains(new Field(2, 0)));
    assertEquals(4, fields.size());
  }

  @Test(expected = RuntimeException.class)
  public void testInvalidFieldNeighbours() {
    BoardImpl board = new BoardImpl(1);
    board.getNeighbours(new Field(10, 10));
  }

  @Test
  public void testExtendedJumpValid() {
    BoardImpl board = new BoardImpl(2);
    Field origin = new Field(4, 4);
    Field via = new Field(5, 5);
    Field target = board.getExtendedNeighbour(origin, via);
    assertEquals(new Field(6, 6), target);
  }

  @Test(expected = FieldException.class)
  public void testInvalidExtendedJump() {
    BoardImpl board = new BoardImpl(1);
    board.getExtendedNeighbour(new Field(0,0), new Field(2,2));
  }

}