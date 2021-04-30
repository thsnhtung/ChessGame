package com.chess.engine.player;

public enum MoveStatus 
{
	DONE {
		@Override
		public boolean isDone() 
		{
			return true;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "DONE";
		}
	}, 
	ILLEGAL_MOVE {
		@Override
		public boolean isDone() 
		{
			
			return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "ILLEGAL_MOVE";
		}
	}, 
	LEAVE_PLAYER_IN_CHECK {
		@Override
		public boolean isDone() 
		{
			return false;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return "LEAVE_PLAYER_IN_CHECK";
		}
	};
	public abstract boolean isDone();
	public abstract String  toString();
	
}
