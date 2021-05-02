package com.chess.engine;

import com.chess.engine.board.BoardUtils;
import com.chess.engine.player.*;

public enum Alliance 
{
	WHITE
	{
		@Override
		public int getDirection() 
		{
			return -1;
		}

		@Override
		public boolean isWhite() 
		{
			return true;
		}

		@Override
		public boolean isBlack() 
		{
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public PLayer choosePlayer(WhitePlayer whitePlayer, BlackPlayer blackPlayer) 
		{			
			return whitePlayer;
		}

		@Override
		public Integer getOppositeDirection() 
		{

			return 1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) 
		{
			return BoardUtils.EIGHTH_RANK[position];
		}
		
	},
	BLACK
	{
		@Override
		public int getDirection() 
		{
			return 1;
		}

		@Override
		public boolean isWhite() 
		{
			return false;
		}

		@Override
		public boolean isBlack() 
		{
			return true;
		}

		@Override
		public PLayer choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer) 
		{
			return blackPlayer;
		}

		@Override
		public Integer getOppositeDirection() 
		{
			return -1;
		}

		@Override
		public boolean isPawnPromotionSquare(int position) 
		{
			return BoardUtils.FIRST_RANK[position];
		}	
	};
	
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract boolean isPawnPromotionSquare(int position);
	
	
	public abstract PLayer choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
	public abstract Integer getOppositeDirection();
}
