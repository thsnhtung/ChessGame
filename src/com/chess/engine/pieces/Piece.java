package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece 
{
	
	protected final PieceType pieceType;
	protected final int piecePosition;
	protected final Alliance pieceAlliance ; 
	protected final boolean isFirstMove ; 
	private final int cachedHashCode ;
	
	Piece(final PieceType pieceType, final int piecePosition, final Alliance pieceAlliance, final boolean isFirstMove)
	{
		this.pieceType = pieceType;
		this.piecePosition = piecePosition; 
		this.pieceAlliance = pieceAlliance;
		//
		this.isFirstMove = isFirstMove ; 
		this.cachedHashCode = computeHashCode();
	}
	
	@Override
	public boolean equals(final Object other)
	{
		if (this == other)
		{
			return true;
		}
		if (! (other instanceof Piece))
		{
			return false;
		}
		final Piece otherPiece = (Piece) other ;
		return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType()
				&& pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove() ; 
	}
	

	private int computeHashCode()
	{
		int result = pieceType.hashCode(); 
		result = 31 * result + pieceAlliance.hashCode() ; 
		result = 31 * result + piecePosition ; 
		result = 31 * result + (isFirstMove ? 1 : 0);
		return result;
	}
	
	public int getPieceValue()
	{
		return this.getPieceType().getPieceValue();
	}
	
	@Override 
	public int hashCode()
	{
		return this.cachedHashCode ;
	}
	
	public boolean isFirstMove()
	{
		return this.isFirstMove ; 
	}
	
	public Alliance getPieceAlliance()
	{
		return this.pieceAlliance; 
	}
	
	public abstract Collection<Move> CalculateLegalMoves(final Board board);

	public Integer getPiecePosition() 
	{
		return this.piecePosition;
	}	
	
	public PieceType getPieceType()
	{
		return this.pieceType ; 
	}
	
	public abstract Piece movePiece(Move move) ; 
	
	
	
	public enum PieceType
	{
		PAWN("P", 100) {
			@Override
			public boolean isKing() 
			{
				return false;
			}

			@Override
			public boolean isRook() 
			{
				return false;
			}

		}, 
		KNIGHT("N", 300) {
			@Override
			public boolean isKing() 
			{
				return false;
			}

			@Override
			public boolean isRook() 
			{
				return false;
			}

		},
		BISHOP("B", 300) {
			@Override
			public boolean isKing() 
			{
				return false;
			}

			@Override
			public boolean isRook() 
			{
				return false;
			}
		},
		ROOK("R", 500) {
			@Override
			public boolean isKing() 
			{
				return false;
			}

			@Override
			public boolean isRook() 
			{
				return true;
			}
		},
		QUEEN("Q", 900) {
			@Override
			public boolean isKing() 
			{
				return false;
			}

			@Override
			public boolean isRook() 
			{
				return false;
			}
		},
		KING("K", 10000) {
			@Override
			public boolean isKing() 
			{
				return true;
			}

			@Override
			public boolean isRook() 
			{
				return false;
			}
		};
		
		private String pieceName; 
		private int pieceValue ;
		
		PieceType(String pieceName, final int pieceValue)
		{
			this.pieceName = pieceName;
			this.pieceValue = pieceValue;	
		}
		
		@Override
		public String toString()
		{
			return this.pieceName ; 
		}

		
		public int getPieceValue()
		{
			return this.pieceValue ;
		}
		
		public abstract boolean isKing();
		public abstract boolean isRook();
		
	}
}
