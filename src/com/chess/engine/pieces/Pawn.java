package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;

public class Pawn extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8}; 
	Pawn(int piecePosition, Alliance pieceAlliance) 
	{
		super(piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> CalculateLegalMoves(Board board) 
	{
		final List<Move> legalMoves = new ArrayList<>() ; 
		for (final int CurrentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			int CandidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * CurrentCandidateOffset) ; 
			if(!BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate))
			{
				continue ; 
			}
			
			if (CurrentCandidateOffset == 8 && !board.getTile(CandidateDestinationCoordinate).isTileOccupied())
			{
				legalMoves.add(new Move.MajorMove(board, this, CandidateDestinationCoordinate));
			}
			
		}
		
		return Collections.unmodifiableList(legalMoves);
	}

}
