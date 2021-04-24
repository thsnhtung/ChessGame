package com.chess.engine.board;
import java.util.*;

import com.chess.engine.player.PLayer;
import com.chess.engine.Alliance;
import com.chess.engine.pieces.Piece;
import com.chess.engine.player.BlackPlayer;
import com.chess.engine.player.WhitePlayer;
import com.chess.engine.pieces.*;


public class Board 
{
	private final List<Tile> gameBoard; 
	private final Collection<Piece> whitePieces ;
	private final Collection<Piece> blackPieces ;
	
	private final WhitePlayer whitePlayer;
	private final BlackPlayer blackPlayer;
	private final PLayer currentPlayer;
	
	private Board (final Builder builder)
	{
		this.gameBoard = createGameBoard(builder) ;  
		this.whitePieces = calculateActivePiece(this.gameBoard, Alliance.WHITE) ; 
		this.blackPieces = calculateActivePiece(this.gameBoard, Alliance.BLACK) ; 	
		
		final Collection<Move> whiteStandardLegalMoves = calculateLegalMoves(this.whitePieces) ; 
		final Collection<Move> blackStandardLegalMoves = calculateLegalMoves(this.blackPieces) ; 
		
		this.whitePlayer = new WhitePlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.blackPlayer = new BlackPlayer(this, whiteStandardLegalMoves, blackStandardLegalMoves);
		this.currentPlayer = builder.nextMoveMaker.choosePlayer(this.whitePlayer, this.blackPlayer);
	}
	
	public Collection<Piece> getBlackPiece()
	{
		return this.blackPieces ; 
	}
	
	public Collection<Piece> getWhitePiece()
	{
		return this.whitePieces ; 
	}
	
	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		for (int i = 0; i < BoardUtils.NUM_TILES; i++)
		{
			final String tileText = this.gameBoard.get(i).toString() ; 
			builder.append(String.format("%3s", tileText));
			if ((i+1) % BoardUtils.NUM_TILES_PER_ROW == 0)
			{
				builder.append("\n"); 
			}
		}
		return builder.toString();
	}
	

	private Collection<Move> calculateLegalMoves(final Collection<Piece> pieces) 
	{
		final List<Move> legalMoves = new ArrayList<>() ; 
		for (final Piece piece : pieces)
		{
			legalMoves.addAll(piece.CalculateLegalMoves(this)) ; 
		}
 		return Collections.unmodifiableList(legalMoves);
	}

	private static Collection<Piece> calculateActivePiece(final List<Tile> gameBoard,
														  final Alliance white) 
	{
		final List<Piece> activePieces = new ArrayList<>() ; 
		
		for (final Tile tile : gameBoard)
		{
			if (tile.isTileOccupied())
			{
				final Piece piece = tile.getPiece();
				if (piece.getPieceAlliance() == white)
				{
					activePieces.add(piece) ; 
				}
			}
		}
		
		return Collections.unmodifiableList(activePieces);
	}

	private static List<Tile> createGameBoard(Builder builder) {
		final List<Tile> tiles = new ArrayList<>() ;
		for (int i = 0 ; i < BoardUtils.NUM_TILES; i ++)
		{
			tiles.add(Tile.CreateTile(i, builder.BoardConfig.get(i))) ;  
		}
		
		return Collections.unmodifiableList(tiles);
	}
	
	public static Board createStandardBoard()
	{
		final Builder builder = new Builder();
        // Black Layout
        builder.setPiece(new Rook(Alliance.BLACK, 0));
        builder.setPiece(new Knight(Alliance.BLACK, 1));
        builder.setPiece(new Bishop(Alliance.BLACK, 2));
        builder.setPiece(new Queen(Alliance.BLACK, 3));
        builder.setPiece(new King(Alliance.BLACK, 4));
        builder.setPiece(new Bishop(Alliance.BLACK, 5));
        builder.setPiece(new Knight(Alliance.BLACK, 6));
        builder.setPiece(new Rook(Alliance.BLACK, 7));
        builder.setPiece(new Pawn(Alliance.BLACK, 8));
        builder.setPiece(new Pawn(Alliance.BLACK, 9));
        builder.setPiece(new Pawn(Alliance.BLACK, 10));
        builder.setPiece(new Pawn(Alliance.BLACK, 11));
        builder.setPiece(new Pawn(Alliance.BLACK, 12));
        builder.setPiece(new Pawn(Alliance.BLACK, 13));
        builder.setPiece(new Pawn(Alliance.BLACK, 14));
        builder.setPiece(new Pawn(Alliance.BLACK, 15));
        // White Layout
        builder.setPiece(new Pawn(Alliance.WHITE, 48));
        builder.setPiece(new Pawn(Alliance.WHITE, 49));
        builder.setPiece(new Pawn(Alliance.WHITE, 50));
        builder.setPiece(new Pawn(Alliance.WHITE, 51));
        builder.setPiece(new Pawn(Alliance.WHITE, 52));
        builder.setPiece(new Pawn(Alliance.WHITE, 53));
        builder.setPiece(new Pawn(Alliance.WHITE, 54));
        builder.setPiece(new Pawn(Alliance.WHITE, 55));
        builder.setPiece(new Rook(Alliance.WHITE, 56));
        builder.setPiece(new Knight(Alliance.WHITE, 57));
        builder.setPiece(new Bishop(Alliance.WHITE, 58));
        builder.setPiece(new Queen(Alliance.WHITE, 60));
        builder.setPiece(new King(Alliance.WHITE, 59));
        builder.setPiece(new Bishop(Alliance.WHITE, 61));
        builder.setPiece(new Knight(Alliance.WHITE, 62));
        builder.setPiece(new Rook(Alliance.WHITE, 63));
        //white to move
        builder.setMoveMaker(Alliance.WHITE);
        //build the board
        return builder.build();
	}
	
	public Tile getTile(final int tileCoordinate)
	{
		return this.gameBoard.get(tileCoordinate) ; 
	}
	
	
	
	public PLayer blackPlayer()
	{
		return this.blackPlayer;
	}
	
	public PLayer whitePlayer()
	{
		return this.whitePlayer ; 
	}
	
	public static class Builder
	{
		Map <Integer, Piece> BoardConfig;
		Alliance nextMoveMaker ; 
		
		public Builder()
		{
			BoardConfig = new HashMap<>() ;
		}
		
		public Builder setPiece(final Piece piece)
		{
			this.BoardConfig.put(piece.getPiecePosition(), piece);
			return this; 
		}
		
		public Builder setMoveMaker(final Alliance nextMoveMaker)
		{
			this.nextMoveMaker = nextMoveMaker; 
			return this ; 
		}
		
		public Board build()
		{
			return new Board(this);
		}
	}
}
