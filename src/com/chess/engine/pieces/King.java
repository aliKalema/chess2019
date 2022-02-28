package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Tile;
import com.chess.engine.players.Alliance;
import com.google.common.collect.ImmutableList;

public class King extends Piece {
	private final static int[] CANDIDATE_MOVE_COORDINATES = {9,8,7,1,-1,-7,-8,-9};
	public King(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.KING,piecePosition, pieceAlliance,true);
	}
	public King(int piecePosition, Alliance pieceAlliance,boolean isFirstMove) {
		super(PieceType.KING,piecePosition, pieceAlliance,isFirstMove);
	}
	 @Override
	    public List<Move> calculateLegalMoves(Board board) {
	        List<Move>legalMoves = new ArrayList<>();
	        for(int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES){
	            int candidateDestinationCoordinate = this.piecePosition + currentCandidateOffset;
	             if(isFirstColumnExclusion(this.piecePosition,currentCandidateOffset)
	                    || isEighthColumnExclusion(this.piecePosition,currentCandidateOffset)){
	                continue;
	                }
	            if(BoardUtils.validTileCoordinate(candidateDestinationCoordinate)){
	                final Tile candidateDestination = board.getTile(candidateDestinationCoordinate);
	                if(!candidateDestination.isTileOccupied()){
	                    legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
	                }else{
	                    final Piece pieceAtDestination = candidateDestination.getPiece();
	                    final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();
	                    if(this.pieceAlliance != pieceAlliance){
	                        legalMoves.add(new AttackMove(board,this,candidateDestinationCoordinate,pieceAtDestination));
	                      }
	                    }
	            }
	        }
	        return ImmutableList.copyOf(legalMoves);
	    }
	    private boolean isFirstColumnExclusion(int currentPosition,int candidateOffset){
	       boolean conditionChecker = false;
	   	   boolean firstCondition=false;
	   	   boolean secondCondition = false;
	   	   if(BoardUtils.FIRST_COLUMN[currentPosition] == true) {
	   		   firstCondition = true;
	   	   }
	   	   if(candidateOffset ==-9 || candidateOffset ==-1 ||candidateOffset == 7) {
	   		   secondCondition = true;
	   	   }
	   	   if(firstCondition == true & secondCondition == true) {
	   		   conditionChecker = true;
	   	   }
	   	   return conditionChecker;
	    }
	    private boolean isEighthColumnExclusion(int currentPosition,int candidateOffset){
	    	   boolean conditionChecker = false;
		   	   boolean firstCondition=false;
		   	   boolean secondCondition = false;
		   	   if(BoardUtils.EIGHTH_COLUMN[currentPosition] == true) {
		   		   firstCondition = true;
		   	   }
		   	   if(candidateOffset ==-7 || candidateOffset ==1 ||candidateOffset == 9) {
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
		  public King movePiece(Move move) {
	  	  	return  new King(move.getDestinationCoordinate(),move.getMovedPiece().pieceAlliance);
	  	  }

}
