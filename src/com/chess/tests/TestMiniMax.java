package com.chess.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.player.MoveTransition;
import com.chess.engine.player.ai.MiniMax;
import com.chess.engine.player.ai.MoveStrategy;

public class TestMiniMax {
	@Test
	public void testAI()
	{
		final Board board = Board.createStandardBoard();
		final MoveTransition t1 = board.currentPlayer()
				.makeMove(Move.MoveFactory.createMove(board, BoardUtils.getCoordinateAtPosition("f2"), 
				BoardUtils.getCoordinateAtPosition("f3")));
		
		assertFalse(!t1.getMoveStatus().isDone());
		
		final MoveTransition t2 = t1.getTransitionBoard().currentPlayer() 
						.makeMove(Move.MoveFactory.createMove(t1.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("e7"), 
						BoardUtils.getCoordinateAtPosition("e5")));
		
		assertFalse(!t2.getMoveStatus().isDone());
		
		
		final MoveTransition t3 = t2.getTransitionBoard().currentPlayer() 
				.makeMove(Move.MoveFactory.createMove(t2.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("g2"), 
				BoardUtils.getCoordinateAtPosition("g4")));

		assertFalse(!t3.getMoveStatus().isDone());
		
		final MoveStrategy strategy = new MiniMax(3);
		
		
		final Move aiMove = strategy.execute(t3.getTransitionBoard());
		
		final Move bestMove = Move.MoveFactory.createMove
				(t3.getTransitionBoard(), BoardUtils.getCoordinateAtPosition("d8"), BoardUtils.getCoordinateAtPosition("h4"));
		
		assertEquals(aiMove , bestMove);
		
	}

}
