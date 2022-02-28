package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;

public abstract class Move {
	Board board;
    Piece movedPiece;
    int destinationCoordinate;
    protected final boolean isFirstMove;
    public static final Move NULL_MOVE = new NullMove();
    private Move(Board board,Piece movedPiece,int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = movedPiece;
        this.isFirstMove = movedPiece.isFirstMove();
    }
    private Move(Board board,int destinationCoordinate){
        this.board = board;
        this.destinationCoordinate = destinationCoordinate;
        this.movedPiece = null;
        this.isFirstMove = false;
    }
    public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
    public Piece getMovedPiece() {
    	return this.movedPiece;
    }
	public Board execute() {
	    final Builder builder = new Builder();
	    for(final Piece piece:this.board.currentPlayer().getActivePieces()){
	        if(!this.movedPiece.equals(piece)){
	            builder.setPiece(piece);
	        }
	    }
	    for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
	       builder.setPiece(piece);
	    }
	    builder.setPiece(this.movedPiece.movePiece(this));
	    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
	    return builder.build();
	}
	public int getCurrentCoordinate() {
		return this.movedPiece.getPiecePosition();
	}
	public boolean isAttack() {
		return false;
	}
	public boolean isCastlingMove() {
		return false;
	}
	public Piece getAttackedPiece() {
		return null;
	}
	@Override 
	public int hashCode() {
    	int prime = 31;
    	int result = 1;
    	result = prime * result + this.destinationCoordinate;
    	result = prime * result + movedPiece.hashCode();
    	result = prime * result + this.movedPiece.getPiecePosition();
    	return result;
	}
	@Override 
	public boolean equals(final Object other) {
		if(this == other) {
    		return true;
    	}
    	if(!(other instanceof Piece)) {
    		return false;
    	}
    	final Move otherMove = (Move) other;
    	return this.getCurrentCoordinate() == otherMove.getCurrentCoordinate() && 
    			                              getDestinationCoordinate()== otherMove.getDestinationCoordinate() &&
    			                              getMovedPiece().equals(otherMove.getMovedPiece());
	}
	@Override
	public String toString() {
		return this.movedPiece.getPieceAlliance() + " " + this.movedPiece +" AT(" + Integer.toString(this.movedPiece.getPiecePosition()) +") " +"move to (" + this.destinationCoordinate +")" ;
		 
	}

	public static class MajorMove extends Move{
		public MajorMove(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		@Override
		public boolean equals(final Object other) {
			return this == other || other instanceof MajorMove && super.equals(other);
		}
		@Override 
		public String toString() {
			 return this.movedPiece.getPieceAlliance() + " " + this.movedPiece +" AT(" + Integer.toString(this.movedPiece.getPiecePosition()) +") " +"move to (" + this.destinationCoordinate +") ";
		}
    }
    
     public static class AttackMove extends Move{
    	Piece attackedPiece;
		public AttackMove(Board board, Piece movedPiece, int destinationCoordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece = attackedPiece;
		}
		@Override
		 public Board execute() {
		 return null;
		 }
		@Override 
		public boolean isAttack() {
			return true; 
		}
		@Override 
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}
		@Override 
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}
		@Override 
		public boolean equals(final Object other) {
			if(this == other) {
	    		return true;
	    	}
	    	if(!(other instanceof Piece)) {
	    		return false;
	    	}
	    	final AttackMove otherAttackeMove = (AttackMove) other;
	    	return super.equals(otherAttackeMove) && getAttackedPiece().equals(otherAttackeMove.getAttackedPiece());
	    }
     }
     
 	public static class PawnMove extends Move{
		public PawnMove(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
    }
 	
 	public static class PawnAttackMove extends AttackMove{
		public PawnAttackMove(Board board, Piece movedPiece, int destinationCoordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate,attackedPiece);
		}
    }
 	
 	public static class PawnEnPassantAttackMove extends PawnAttackMove{
		public PawnEnPassantAttackMove(Board board, Piece movedPiece, int destinationCoordinate,Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate,attackedPiece);
		}
    }
 	
	public static class PawnJump extends Move{
		public PawnJump(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		@Override
		public Board execute() {
		    final Builder builder = new Builder();
		    for(final Piece piece:this.board.currentPlayer().getActivePieces()){
		        if(!this.movedPiece.equals(piece)){
		            builder.setPiece(piece);
		        }
		    }
		    for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
		       builder.setPiece(piece);
		    }
		    final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
		    builder.setPiece(movedPawn);
		    builder.setEnPassantPawn(movedPawn);
		    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		    return builder.build();
		}
		@Override
		public String toString() {
			return BoardUtils.getPositionAtCoordinate(this.destinationCoordinate);
		}
    }
	public static class PawnPromotion extends Move{
		public PawnPromotion(Board board, Piece movedPiece, int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
    }
	
	public static abstract class CastleMove extends Move{
		protected final Rook castleRook;
		protected final int castleRookStart;
		protected final int castleRookDestination;
		public CastleMove(Board board, Piece movedPiece, int destinationCoordinate,Rook castleRook,int castleRookStart,int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate);
			this.castleRook = castleRook;
			this.castleRookStart = castleRookStart;
			this.castleRookDestination = castleRookDestination;
		}
		public Rook getCAstleRook() {
			return this.castleRook;
		}
		@Override
		public boolean isCastlingMove() {
			return true;
		}
		@Override
		public Board execute() {
			final Builder builder = new Builder();
		    for(final Piece piece:this.board.currentPlayer().getActivePieces()){
		        if(!this.movedPiece.equals(piece) && !this.castleRook.equals(piece)){
		            builder.setPiece(piece);
		        }
		    }
		    for(final Piece piece:this.board.currentPlayer().getOpponent().getActivePieces()){
		       builder.setPiece(piece);
		    }
		    builder.setPiece(this.movedPiece.movePiece(this));
		    //to do look into the first move on normal pieces
		    builder.setPiece(new Rook(this.castleRookDestination,this.castleRook.getPieceAlliance()));
		    builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		    return builder.build();
		}
    }
	
	public static final class KingSideCastleMove extends CastleMove{
		public KingSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook,
				int castleRookStart, int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		@Override 
		public String toString() {
			return "0-0";
		}
    }
	
	public static final class QueenSideCastleMove extends CastleMove{
		public QueenSideCastleMove(Board board, Piece movedPiece, int destinationCoordinate, Rook castleRook,
				int castleRookStart, int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookStart, castleRookDestination);
		}
		@Override 
		public String toString() {
			return "0-0-0";
		}
    }
	
	public static final class NullMove extends Move{
		public NullMove() {
			super(null, -1);
		}
		@Override
		public Board execute() {
			throw new RuntimeException("Can not execute a null move");
		}
    }
	
	public static class MoveFactory{
		private MoveFactory() {
			throw new RuntimeException("Not instatiable");
		}
		public static Move createMove(final Board board,int currentCoordinate,int destinationCoordinate) {
			for(final Move move:board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate()==currentCoordinate && move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			return NULL_MOVE;
		}
	}

 }
