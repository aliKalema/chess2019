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

public class Rook extends Piece{
    private final static int CANDIDATE_MOVE_VECTOR_COORDINATES[]={8,1,-1,-8};
	public Rook(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.ROOK,piecePosition, pieceAlliance,true);
	}
	public Rook(int piecePosition, Alliance pieceAlliance,boolean isFirstMove) {
		super(PieceType.KING,piecePosition, pieceAlliance,isFirstMove);
	}
	@Override
	public List<Move> calculateLegalMoves(Board board) {
        final List<Move>legalMoves = new ArrayList<>();
        for(int candidateOffset:CANDIDATE_MOVE_VECTOR_COORDINATES){
            int candidateDestinationCoordinate= this.piecePosition;
            while(BoardUtils.validTileCoordinate(candidateDestinationCoordinate)){
                if(isFirstColumnExclusion(candidateDestinationCoordinate,candidateOffset) 
                    || isEigthColumnExclusion(candidateDestinationCoordinate,candidateOffset)){
                    break;
                }
                candidateDestinationCoordinate += candidateOffset;
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
                    break;
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
 	   if(candidateOffset == -1 ) {
 		   secondCondition = true;
 	   }
 	   if(firstCondition == true & secondCondition == true) {
 		   conditionChecker = true;
 	   }
 	   return conditionChecker;
    }
      private boolean isEigthColumnExclusion(int currentPosition,int candidateOffset){
           boolean conditionChecker = false;
    	   boolean firstCondition=false;
    	   boolean secondCondition = false;
    	   if(BoardUtils.EIGHTH_COLUMN[currentPosition] == true) {
    		   firstCondition = true;
    	   }
    	   if(candidateOffset == 1 ) {
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
	  public Rook movePiece(Move move) {
	  	return  new Rook(move.getDestinationCoordinate(),move.getMovedPiece().pieceAlliance);
	  }
}
