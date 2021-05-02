package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.chess.engine.Alliance;
import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public class WhitePlayer extends PLayer
{
	public WhitePlayer(final Board board,
			   final Collection <Move> whiteStandardLegalMoves, 
			   final Collection <Move> blackStandardLegalMoves)
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

	@Override
	public Collection<Move> calculateKingCastles(final Collection<Move> opponentsLegals) 
	{
		final List<Move> kingCastle = new ArrayList<>();
		if (this.playerKing.isFirstMove() && !this.isInCheck())
		{
			if (!this.board.getTile(61).isTileOccupied() 
			 && !this.board.getTile(62).isTileOccupied())
			{
				//white King site castle 
				final Tile rookTile = this.board.getTile(63) ; 
				
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					
					if(PLayer.calculateAttackOnTile(61, opponentsLegals).isEmpty()
					&& PLayer.calculateAttackOnTile(62, opponentsLegals).isEmpty()
					&& rookTile.getPiece().getPieceType().isRook())
					{
						if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) 
						{
							kingCastle.add(new Move.KingSideCastleMove(this.board, this.playerKing, 
								  								   62, 
								  								   (Rook)rookTile.getPiece(), 
								  								   rookTile.getTileCoordinate(), 
								  								   61)) ; 	
						}
					}			
				}
			}
			
			if(!this.board.getTile(59).isTileOccupied() 
			&& !this.board.getTile(58).isTileOccupied() 
			&& !this.board.getTile(57).isTileOccupied() )
			{
				final Tile rookTile = this.board.getTile(56) ; 
				
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(PLayer.calculateAttackOnTile(58, opponentsLegals).isEmpty()
					&& PLayer.calculateAttackOnTile(59, opponentsLegals).isEmpty()
					&& rookTile.getPiece().getPieceType().isRook())
					{
						if(!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 52)) 
						{
							kingCastle.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 
								  58, 
								  (Rook)rookTile.getPiece(),
								  rookTile.getTileCoordinate(), 
								  59)) ; 
						}
					}
				}
			}
		}
		
		
		return Collections.unmodifiableList(kingCastle);
	}
}
