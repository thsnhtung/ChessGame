package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Piece.PieceType;

public class Pawn extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {8, 16, 7, 9};
	public Pawn(final Alliance pieceAlliance, final int piecePosition) 
	{
		super(PieceType.PAWN , piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> CalculateLegalMoves(Board board) 
	{
		final List<Move> legalMoves = new ArrayList<>() ; 
		for (final int CurrentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			final int CandidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * CurrentCandidateOffset) ; 
			if(!BoardUtils.isValidTileCoordinate(CandidateDestinationCoordinate))
			{
				continue ; 
			}
			
			if (CurrentCandidateOffset == 8 && !board.getTile(CandidateDestinationCoordinate).isTileOccupied())
			{
				legalMoves.add(new Move.MajorMove(board, this, CandidateDestinationCoordinate));
			}
			else if (CurrentCandidateOffset == 16 && this.isFirstMove() && 
					(BoardUtils.SECOND_ROW[this.piecePosition] && this.getPieceAlliance().isBlack()) || 
					(BoardUtils.SEVENTH_ROW[this.piecePosition] && this.getPieceAlliance().isWhite()))
			{
				final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8) ;
				if ( !board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() && 
						!board.getTile(CandidateDestinationCoordinate).isTileOccupied() )
				{	
					legalMoves.add(new Move.MajorMove(board, this, CandidateDestinationCoordinate));
				}
			}
			else if (CurrentCandidateOffset == 7 && 
					((BoardUtils.EIGHTH_COLUMN[this.piecePosition] &&  this.pieceAlliance.isWhite())
				 || (BoardUtils.FIRST_COLUMN[this.piecePosition] &&  this.pieceAlliance.isBlack())))
			{
				if (board.getTile(CandidateDestinationCoordinate).isTileOccupied())
				{
					final Piece pieceOnCandidate = board.getTile(CandidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.pieceAlliance)
					{
						// en passant
						legalMoves.add(new Move.AttackMove(board, this, CandidateDestinationCoordinate, pieceOnCandidate));
					}
				}
			}
			else if (CurrentCandidateOffset== 9 &&
					((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())
				 || (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite())))	
			{
				if (board.getTile(CandidateDestinationCoordinate).isTileOccupied())
				{
					final Piece pieceOnCandidate = board.getTile(CandidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.pieceAlliance)
					{
						// en passant
						legalMoves.add(new Move.AttackMove(board, this, CandidateDestinationCoordinate, pieceOnCandidate));
					}
				}	
			}
			
			
		}
		
		return Collections.unmodifiableList(legalMoves);
	}
	
	@Override
	public String toString()
	{
		return PieceType.PAWN.toString() ; 
	}

	@Override
	public Pawn movePiece(final Move move) 
	{
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());

	}

}
