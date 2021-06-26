package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;




public abstract class PLayer 
{
	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	private final boolean isInCheck ; 
	
	
	
	PLayer(final Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves  )
	{
		this.board = board ;
		this.playerKing = establishKing();	

		this.legalMoves = Collections.unmodifiableCollection(Stream.concat(legalMoves.stream(),
				calculateKingCastles(opponentMoves).stream()).collect(Collectors.toList()));
		this.isInCheck = !PLayer.calculateAttackOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty() ; 
	}
	
	public King getPlayerKing()
	{
		return playerKing;
	}
	
	public Collection<Move> getLegalMoves()
	{
		return this.legalMoves;
	}

	protected static Collection<Move> calculateAttackOnTile(final Integer piecePosition, final Collection<Move> opponentMoves) 
	{
		final List<Move> attackMoves = new ArrayList<>(); 
		for (final Move move : opponentMoves)
		{
			if (piecePosition == move.getDestinationCoordinate())
			{
				attackMoves.add(move);
			}
		}
		return Collections.unmodifiableCollection(attackMoves);
	}

	private King establishKing() 
	{
		for (final Piece piece : getActivePieces())
		{
			if(piece.getPieceType().isKing())
			{
				return (King) piece;
			}
		}
		throw  new RuntimeException("Should not reach here! King is missing..");
	}

	
	public boolean isMoveLegal (final Move move)
	{
		return this.legalMoves.contains(move); 
	}
	
	public boolean isInCheckMate()
	{
		return this.isInCheck && !hasEscapeMove();
	}
	
	protected boolean hasEscapeMove() 
	{
		for (final Move move : this.legalMoves)
		{
			final MoveTransition transition = makeMove(move);
			if (transition.getMoveStatus().isDone())
			{
				return true;
			}
		}
		return false;
	}

	public boolean isInStaleMate()
	{
		return !this.isInCheck && !hasEscapeMove();
	}
	
	public boolean isCastled()
	{
		return false;
	}
	
	public MoveTransition makeMove(final Move move)
	{
		if (!isMoveLegal(move))
		{
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		final Board transitionBoard = move.execute();
		
		if (transitionBoard.currentPlayer().getOpponent().isInCheck() == true)
		{
			return new MoveTransition(this.board, move, MoveStatus.LEAVE_PLAYER_IN_CHECK);
		}
		else
			return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
	public boolean isInCheck()
	{
		return this.isInCheck ;
	}
	
	
	public boolean isKingSideCastleCapable() 
	{
        return this.playerKing.isKingSideCastleCapable();
    }

    public boolean isQueenSideCastleCapable() 
    {
        return this.playerKing.isQueenSideCastleCapable();
    }
    
    protected boolean hasCastleOpportunities() {
        return !this.isInCheck && !this.playerKing.isCastled() &&
                (this.playerKing.isKingSideCastleCapable() || this.playerKing.isQueenSideCastleCapable());
    }
    

	
	public abstract Collection<Piece> getActivePieces();
	public abstract Alliance getAlliance();
	public abstract PLayer getOpponent() ;
	public abstract Collection<Move> calculateKingCastles(final Collection<Move> opponentsLegals);
	
}
