package com.chess.engine.players;

public enum Alliance {
    WHITE {
		@Override
		public int getDirection() {
			return -1;
		}
		@Override
		public boolean isWhite() {
			return true;
		}
		@Override
		public boolean isBlack() {
			return false;
		}
		 @Override
	     public Player choosePlayer(WhitePlayer whiteplayer,BlackPlayer blackplayer){
	        return whiteplayer;
	    }
		 @Override
		 public String toString() {
			 return "WHITE";
		 }
	},
    BLACK {
		@Override
		public int getDirection() {
			return 1;
		}
		@Override
		public boolean isWhite() {
			return false;
		}

		@Override
		public boolean isBlack() {
			return true;
		}
		 @Override
	        public Player choosePlayer(WhitePlayer whiteplayer,BlackPlayer blackplayer){
	         return blackplayer;
	   }
		 @Override
		 public String toString() {
			 return "BLACK";
		 }
	};
	public abstract int getDirection();
	public abstract boolean isWhite();
	public abstract boolean isBlack();
	public abstract Player choosePlayer(WhitePlayer whiteplayer, BlackPlayer blackplayer);
	
}
