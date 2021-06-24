package com.chess.engine.player.ai;

import com.chess.engine.board.Board;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.PLayer;

public final class StandardBoardEvaluator implements BoardEvaluator 
{
	
	private static final int CHECK_BONUS = 50 ;
	private static final int CHECK_MATE_BONUS = 10000;
	private static final int DEPTH_BONUS = 100;
	private static final int CASTLE_BONUS = 60;
	
	@Override
	public int evaluate(Board board, int depth)
	{
		return scorePlayer(board, board.whitePlayer(), depth) -
				scorePlayer(board, board.blackPlayer(), depth);
	}

	
	private int scorePlayer(final Board board, final PLayer player, final int depth)
	{
		return pieceValue(player) + 
				mobility(player) + 
				check(player) + 
				checkMate(player, depth) + 
				castled(player);
		
	}
	
	private int castled(PLayer player) 
	{
		return player.isCastled() ? CASTLE_BONUS : 0;
	}


	private static int checkMate(final PLayer player, int depth) 
	{
		return player.getOpponent().isInCheckMate() ? CHECK_MATE_BONUS* depthBonus(depth) : 0 ;
	}


	private static int depthBonus(int depth) 
	{
		return depth == 0 ? 1 : DEPTH_BONUS * depth;
	}


	private static int check(final PLayer player) 
	{
		return player.getOpponent().isInCheck() ? CHECK_BONUS : 0 ;
	}


	private static int mobility(final PLayer player) 
	{
		return player.getLegalMoves().size();
	}


	private static int pieceValue(final PLayer player) 
	{
		int pieceValueScore = 0 ;
		
		for (final Piece piece : player.getActivePieces())
		{
			pieceValueScore += piece.getPieceValue();
		}
		return pieceValueScore;
	}
	
	

}
