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

public class Bishop extends Piece {
	final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -7, 7, 9};
	
	
	Bishop(int piecePosition, Alliance pieceAlliance) 
	{
		super(piecePosition, pieceAlliance);
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
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == 7); 
	}
	
	private static boolean isEighthColumnExclusion (final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 9 || candidateOffset == -7); 
	}

}
	

