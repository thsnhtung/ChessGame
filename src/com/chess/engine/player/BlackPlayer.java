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

public class BlackPlayer extends PLayer
{
	public BlackPlayer(final Board board,
					   final Collection <Move> whiteStandardLegalMoves, 
					   final Collection <Move> blackStandardLegalMoves)
	{
		super(board, blackStandardLegalMoves, whiteStandardLegalMoves);
	}
	


	@Override
	public Collection<Piece> getActivePieces() 
	{

		return this.board.getBlackPiece();
	}



	@Override
	public Alliance getAlliance() 
	{
		return Alliance.BLACK;
	}

	@Override
	public String toString() 
	{
		return "Black";
	}

	@Override
	public PLayer getOpponent() 
	{
		return this.board.whitePlayer();
	}



	@Override
	public Collection<Move> calculateKingCastles(final Collection<Move> opponentsLegals) 
	{
		final List<Move> kingCastle = new ArrayList<>();
		if (this.playerKing.isFirstMove() && !this.isInCheck())
		{
			if (!this.board.getTile(5).isTileOccupied() 
			 && !this.board.getTile(6).isTileOccupied())
			{
				//black King site castle 
				final Tile rookTile = this.board.getTile(7) ; 
				
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					// castle move need to be added
					
					if(PLayer.calculateAttackOnTile(5, opponentsLegals).isEmpty()
					&& PLayer.calculateAttackOnTile(6, opponentsLegals).isEmpty()
					&& rookTile.getPiece().getPieceType().isRook())
					{
						
						if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) 
						{
							kingCastle.add(new Move.KingSideCastleMove(this.board, this.playerKing, 
								   								   6, 
								   								   (Rook)rookTile.getPiece(), 
								   								   rookTile.getTileCoordinate(), 
								   								   5)) ; 	
						}
					}			
				}
			}
			
			if(!this.board.getTile(1).isTileOccupied() 
			&& !this.board.getTile(2).isTileOccupied() 
			&& !this.board.getTile(3).isTileOccupied() )
			{
				final Tile rookTile = this.board.getTile(0) ; 
				
				if (rookTile.isTileOccupied() && rookTile.getPiece().isFirstMove())
				{
					if(PLayer.calculateAttackOnTile(2, opponentsLegals).isEmpty()
					&& PLayer.calculateAttackOnTile(3, opponentsLegals).isEmpty()
					&& rookTile.getPiece().getPieceType().isRook())
					{
						if (!BoardUtils.isKingPawnTrap(this.board, this.playerKing, 12)) 
						{
							kingCastle.add(new Move.QueenSideCastleMove(this.board, this.playerKing, 
								                                   2, 
								                                   (Rook)rookTile.getPiece(), 
								                                   rookTile.getTileCoordinate(), 
								                                   3)) ; 	
						}
					}
				}
			}
		}
		
		
		return Collections.unmodifiableList(kingCastle);
	}
}
