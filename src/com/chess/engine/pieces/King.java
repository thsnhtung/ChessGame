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
import com.chess.engine.board.Move.MajorMove;

public class King extends Piece
{
	private final static int[] CANDIDATE_MOVE_COORDINATE = {-9, -8, -7, -1, 1, 7, 8, 9};
	private final boolean isCastled;
    private final boolean kingSideCastleCapable;
    private final boolean queenSideCastleCapable;
	
	
	
	public King(final Alliance pieceAlliance, final int piecePosition, final boolean kingSideCastleCapable, final boolean queenSideCastleCapable) 
	{
		super(PieceType.KING, piecePosition, pieceAlliance, true);
		this.isCastled = false;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
	}
	
	public King(final Alliance pieceAlliance, final int piecePosition, final boolean isFirstMove, final boolean isCastled, final boolean kingSideCastleCapable,
            final boolean queenSideCastleCapable) 
	{	
		super(PieceType.KING, piecePosition, pieceAlliance, isFirstMove);
		this.isCastled = isCastled;
        this.kingSideCastleCapable = kingSideCastleCapable;
        this.queenSideCastleCapable = queenSideCastleCapable;
	}
	
	
	public boolean isCastled() 
	{
        return this.isCastled;
    }

    public boolean isKingSideCastleCapable() 
    {
        return this.kingSideCastleCapable;
    }
    

    public boolean isQueenSideCastleCapable() 
    {
        return this.queenSideCastleCapable;
    }
    


	@Override
	public Collection<Move> CalculateLegalMoves(Board board) 
	{
		final List<Move> legalMoves = new ArrayList<>();
		
		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE)
		{
			if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
	                isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) 
			{
	                continue;
			}
			final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset ;
			
			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate))
			{
				final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate) ; 
				

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
						legalMoves.add(new Move.MajorAttackMove(board, this, candidateDestinationCoordinate, PieceAtDestination));
					} 
				}
			}
		}
		
		return Collections.unmodifiableList(legalMoves) ;
	}
	
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -9 || candidateOffset == -1 || 
													candidateOffset == 7 );
	}
	
	
	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset)
	{
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == -7 || candidateOffset == 1 || 
													candidateOffset == 9 );
	}
	
	@Override
	public String toString()
	{
		return PieceType.KING.toString() ; 
	}

	@Override
	public King movePiece(final Move move) 
	{
		return new King(this.pieceAlliance, move.getDestinationCoordinate(), false, move.isCastlingMove(), false, false);
	}

}
