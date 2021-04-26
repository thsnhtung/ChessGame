package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;

public class Rook extends Piece
{

	final static int[] CANDIDATE_MOVE_COORDINATE = {-8, -1, 1, 8};
	
	
	public Rook (Alliance pieceAlliance, int piecePosition) 
	{
		super(PieceType.ROOK , piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> CalculateLegalMoves(Board board) 
	{
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			int candidateDestinationCoordinate = this.piecePosition;
			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))							// check current position
			{
				if (isFirstColumnExclusion(candidateDestinationCoordinate , currentCandidateOffset) 
				|| isEighthColumnExclusion(candidateDestinationCoordinate, currentCandidateOffset))
					{
						break ; 
					}
				candidateDestinationCoordinate += currentCandidateOffset;										// check next position
				
				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
				{
					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);
					
					if (!candidateDestinationTile.isTileOccupied())
					{
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
					else
					{
						final Piece PieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = PieceAtDestination.getPieceAlliance(); 
						if(this.pieceAlliance != pieceAlliance)
						{
							legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate, PieceAtDestination));
						} 
						break ;
					}
					
				}
			}
		}
		return Collections.unmodifiableList(legalMoves);
	}
	
	private static boolean isFirstColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1 ); 
	}
	
	private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1); 
	}
	
	
	@Override
	public String toString()
	{
		return PieceType.ROOK.toString() ; 
	}

	@Override
	public Rook movePiece(final Move move) 
	{
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());

	}
}
