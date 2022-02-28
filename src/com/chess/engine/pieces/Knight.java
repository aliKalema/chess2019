package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.players.Alliance;
import com.google.common.collect.ImmutableList;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;

public  class Knight extends Piece {
	private final  int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };
	
	
	public Knight(final int piecePosition, final Alliance pieceAlliance) {
		super(PieceType.KNIGHT,piecePosition, pieceAlliance,true);
	}
	public Knight(int piecePosition, Alliance pieceAlliance,boolean isFirstMove) {
		super(PieceType.KING,piecePosition, pieceAlliance,isFirstMove);
	}
    @Override
    public List<Move> calculateLegalMoves(Board board) {
       List<Move>legalMoves = new ArrayList<>();
   for( int currentCandidateOffset : this.CANDIDATE_MOVE_COORDINATES) {
            if(isFirstColumnExclusion(this.piecePosition, currentCandidateOffset) ||
               isSecondColumnExclusion(this.piecePosition, currentCandidateOffset) ||
               isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset) ||
               isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
                continue;
            }
            final int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
            if (BoardUtils.validTileCoordinate(candidateDestinationCoordinate)) {//if the result after addition is between 0 & 64
                final Piece pieceAtDestination = board.getTile(candidateDestinationCoordinate).getPiece();
                if (pieceAtDestination == null) {
                    legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
                } else {
                    final Alliance pieceAtDestinationAllegiance = pieceAtDestination.getPieceAlliance();
                    if (this.pieceAlliance != pieceAtDestinationAllegiance) {//if the piece alliance and the pieceAtDestination alliance are not equal
                        legalMoves.add(new AttackMove(board, this, candidateDestinationCoordinate,pieceAtDestination));
                    }
                }
            } 
           }
        return ImmutableList.copyOf(legalMoves);
    }
   //if the knight piece is in the first column(a) then -17,-10,6,15 wont be legal moves candidate 
   private static boolean isFirstColumnExclusion(final int currentPosition,final int candidateOffset) {
	   boolean conditionChecker = false;
	   boolean firstCondition=false;
	   boolean secondCondition = false;
	   if(BoardUtils.FIRST_COLUMN[currentPosition] == true) {
		   firstCondition = true;
	   }
	   if(candidateOffset == -17 || candidateOffset == -10 || candidateOffset == 6 || candidateOffset == 15) {
		   secondCondition = true;
	   }
	   if(firstCondition == true & secondCondition == true) {
		   conditionChecker = true;
	   }
        return conditionChecker;
    }
    //if the knight piece is in the second column(b) then -10,6, wont be legal moves candidate 
    private static boolean isSecondColumnExclusion(final int currentPosition,final int candidateOffset) {
       boolean conditionChecker = false;
 	   boolean firstCondition=false;
 	   boolean secondCondition = false;
 	   if(BoardUtils.SECOND_COLUMN[currentPosition] == true) {
 		   firstCondition = true;
 	   }
 	   if(candidateOffset == -10 || candidateOffset == 6) {
 		   secondCondition = true;
 	   }
 	   if(firstCondition == true & secondCondition == true) {
 		   conditionChecker = true;
 	   }
 	   return conditionChecker;
    }
    //if the knight piece is in the seventh column(g) then 10,-6, wont be legal moves candidate 
    private static boolean isSeventhColumnExclusion(final int currentPosition,final int candidateOffset) {
       boolean conditionChecker = false;
  	   boolean firstCondition=false;
  	   boolean secondCondition = false;
  	   if(BoardUtils.SEVENTH_COLUMN[currentPosition] == true) {
  		   firstCondition = true;
  	   }
  	   if(candidateOffset == 10 || candidateOffset == -6) {
  		   secondCondition = true;
  	   }
  	   if(firstCondition == true & secondCondition == true) {
  		   conditionChecker = true;
  	   }
  	   return conditionChecker;
    }
    //if the knight piece is in the eighth column(h) then -15,-6,10,17 wont be legal moves candidate 
    private static boolean isEighthColumnExclusion(final int currentPosition,final int candidateOffset) {
        
       boolean conditionChecker = false;
  	   boolean firstCondition=false;
  	   boolean secondCondition = false;
  	   if(BoardUtils.EIGHTH_COLUMN[currentPosition] == true) {
  		   firstCondition = true;
  	   }
  	   if(candidateOffset == -15 || candidateOffset == -6 || candidateOffset == 10 || candidateOffset == 17) {
  		   secondCondition = true;
  	   }
  	   if(firstCondition == true & secondCondition == true) {
  		   conditionChecker = true;
  	   }
  	   return conditionChecker;
    }
    @Override
    public String toString() {
    	return this.pieceType.toString();    
    }	 
    @Override
	  public Knight movePiece(Move move) {
	  	return  new Knight(move.getDestinationCoordinate(),move.getMovedPiece().pieceAlliance);
	  }
	
    

}
