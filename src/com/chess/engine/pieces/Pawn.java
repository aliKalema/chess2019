package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.board.Move.PawnJump;
import com.chess.engine.board.Move.PawnAttackMove;
import com.chess.engine.players.Alliance;
import com.google.common.collect.ImmutableList;


public class Pawn extends Piece{
	private final static int[] CANDIDATE_MOVE_COORDINATES = {8,16,7,9};
	public Pawn(int piecePosition, Alliance pieceAlliance) {
		super(PieceType.PAWN,piecePosition, pieceAlliance,true);
	}
	public Pawn(int piecePosition, Alliance pieceAlliance,boolean isFirstMove) {
		super(PieceType.KING,piecePosition, pieceAlliance,isFirstMove);
	}
	@Override
	public List<Move> calculateLegalMoves(Board board) {
        final List<Move>legalMoves = new ArrayList<>();
        for(final int currentCandidateOffset:CANDIDATE_MOVE_COORDINATES){
            int candidateDestinationCoordinate = this.piecePosition + (this.getPieceAlliance().getDirection() * currentCandidateOffset) ;
            if(!BoardUtils.validTileCoordinate(candidateDestinationCoordinate)){
                continue;
             }
           if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
               //TO DO SOME WORK...
               legalMoves.add(new MajorMove(board,this,candidateDestinationCoordinate));
           }
           else if (currentCandidateOffset == 16 && this.isFirstMove() && 
                   (BoardUtils.SEVENTH_RANK[this.piecePosition] && this.pieceAlliance.isBlack() ||
                    BoardUtils.SECOND_RANK[this.piecePosition] && this.pieceAlliance.isWhite())){
               final int behindCandidateDestinationCoordinate = this.piecePosition + (this.pieceAlliance.getDirection() * 8); //check the tile to jumped if it is occupied
                       if(!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied() &&
                          !board.getTile(candidateDestinationCoordinate).isTileOccupied()){
                   legalMoves.add(new PawnJump(board,this,candidateDestinationCoordinate));
                }
           }
           else if(currentCandidateOffset == 7){
               if(
               (!(BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite())) ||
               (!(BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))
                      {
               if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
               final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
               if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                   //TODO WORK HERE!
                   legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                  }
                 }
               }
           }
           else if((currentCandidateOffset == 9) &&
                   !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()) ||
                      (BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
                if(board.getTile(candidateDestinationCoordinate).isTileOccupied()){
               final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
               if(this.pieceAlliance != pieceOnCandidate.getPieceAlliance()){
                   //TODO WORK HERE!
                   legalMoves.add(new PawnAttackMove(board,this,candidateDestinationCoordinate,pieceOnCandidate));
                  }
                }
            }
        }
        return ImmutableList.copyOf(legalMoves);
    }

    @Override
    public String toString() {
    	return this.pieceType.toString();
    }	
    @Override
	public Pawn movePiece(Move move) {
	  	return  new Pawn(move.getDestinationCoordinate(),move.getMovedPiece().pieceAlliance);
	}


}
