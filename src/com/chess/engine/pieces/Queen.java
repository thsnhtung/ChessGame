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

public class Queen extends Piece
{
	final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9 };
	
	
	public Queen (final Alliance pieceAlliance, final int piecePosition) 
	{
		super(PieceType.QUEEN ,piecePosition, pieceAlliance);
	}

	@Override
	public Collection<Move> CalculateLegalMoves(Board board) {
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
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7 || candidateOffset == -1 ); 
	}
	
	private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7 || candidateOffset == 1); 
	}
	
	
	@Override
	public String toString()
	{
		return PieceType.QUEEN.toString() ; 
	}

	@Override
	public Queen movePiece(final Move move) {
		// TODO Auto-generated method stub
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}
}
