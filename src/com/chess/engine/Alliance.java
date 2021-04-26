package com.chess.engine;

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
	};
	
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	
	public abstract PLayer choosePlayer(final WhitePlayer whitePlayer, final BlackPlayer blackPlayer);
}
