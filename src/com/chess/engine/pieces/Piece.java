package com.chess.engine.pieces;

import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.players.Alliance;

public abstract class Piece {
	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected  final PieceType pieceType;
	private final int cachedHashCode;
    public Piece(final PieceType pieceType,final int piecePosition, final Alliance pieceAlliance,final boolean isFirstMove) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		//TO DO
		this.isFirstMove = isFirstMove;
		this.pieceType = pieceType;
		this.cachedHashCode =computeHashCode();
	    }
    private int computeHashCode() {
    	int result = pieceType.hashCode();
    	result = 31 * result + pieceAlliance.hashCode();
    	result = 31 * result + piecePosition;
    	result = 31 * result + (isFirstMove? 1:0);
    	return result;
	}
	public abstract List<Move>calculateLegalMoves(final Board board);
    public abstract Piece movePiece(Move move);
    public Alliance getPieceAlliance() {
	   return this.pieceAlliance;
    }
    public int getPiecePosition() {
    	return this.piecePosition;
    }
    public final boolean isFirstMove() {
    	return this.isFirstMove;
    }
    public PieceType getPieceType() {
    	return this.pieceType;
    }
    public int getPieceValue() {
    	return this.pieceType.getPieceValue();
    }
    @Override
    public boolean equals(final Object other) {
    	if(this == other) {
    		return true;
    	}
    	if(!(other instanceof Piece)) {
    		return false;
    	}
    	final Piece otherPiece = (Piece) other;
    	return piecePosition == otherPiece.getPiecePosition() && pieceType == otherPiece.getPieceType() &&
    			                pieceAlliance == otherPiece.getPieceAlliance() && isFirstMove == otherPiece.isFirstMove();
    }
    @Override 
    public int hashCode() {;
    	return this.cachedHashCode;
    }
    public enum PieceType{
     ROOK("R",500) {
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isRook() {
			return true;
		}
	},
     KNIGHT("K",300) {
		@Override
		public boolean isKing() {
		return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
     BISHOP("B",300) {
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
     QUEEN("Q",900) {
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
     KING("A",10000) {
		@Override
		public boolean isKing() {
			return true;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	},
     PAWN("p",100) {
		@Override
		public boolean isKing() {
			return false;
		}
		@Override
		public boolean isRook() {
			return false;
		}
	};
     private final String pieceName;
     private int pieceValue;
     public abstract boolean isKing();
     public abstract boolean isRook();
     PieceType(final String pieceName,final int PieceValue) {
    	this.pieceName = pieceName;	
    	this.pieceValue = pieceValue;
    	}
        public int getPieceValue() {
    	    return this.getPieceValue();
        }
        public String toString() {
        	return this.pieceName;
        }
    }
}
