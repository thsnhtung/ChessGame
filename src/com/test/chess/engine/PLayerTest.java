package com.test.chess.engine;

import static org.junit.Assert.*;

import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move.MoveFactory;
import com.chess.engine.player.MoveTransition;

public class PLayerTest {

	@Test
	public void testSimpleEvaluation() {
        final Board board = Board.createStandardBoard();
        final MoveTransition t1 = board.currentPlayer()
                .makeMove(MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("e2"),
                                BoardUtils.getCoordinateAtPosition("e4")));
        assertTrue(t1.getMoveStatus().isDone());
        final MoveTransition t2 = t1.getTransitionBoard()
                .currentPlayer()
                .makeMove(MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"),
                        BoardUtils.getCoordinateAtPosition("e5")));
        assertTrue(t2.getMoveStatus().isDone());
        //assertEquals(StandardBoardEvaluator.get().evaluate(t2.getTransitionBoard(), 0), 0);
    }


}
