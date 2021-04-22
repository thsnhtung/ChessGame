package com.chess.engine.player;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece;

public class WhitePlayer extends PLayer
{
	public WhitePlayer(Board board,
			   Collection <Move> whiteStandardLegalMoves, 
			   Collection <Move> blackStandardLegalMoves)
	{
		super(board, whiteStandardLegalMoves, blackStandardLegalMoves);
	}

	@Override
	public Collection<Piece> getActivePieces() 
	{
		return this.board.getWhitePiece();
	}

	@Override
	public Alliance getAlliance() 
	{
		return Alliance.WHITE;
	}

	@Override
	public PLayer getOpponent() 
	{
		return this.board.blackPlayer();
	}
}
