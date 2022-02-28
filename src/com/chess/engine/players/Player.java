package com.chess.engine.players;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;


public abstract class Player {
  protected final Board board;
  protected final King playerKing;
  protected final Collection<Move>legalMoves;
  private final boolean isInCheck;
public Player(final Board board, final Collection<Move> legalMoves, final Collection<Move> opponentMoves) {
	super();
	this.board = board;
	establishKing();
	this.playerKing = establishKing();
	this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves,calculateKingCastle(legalMoves,opponentMoves)));
	this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(),opponentMoves).isEmpty();
}
/*
 * the below method takes in a piece position(King) and opponent moves coordinates it checks if 
 * any of the move destination coordinate over laps with the Piece(King) and then returns those that 
 * overlap
 */
protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move>moves) {
	List<Move>attackMoves = new ArrayList<>();
	for(final Move move:moves) {
		if(piecePosition == move.getDestinationCoordinate()) {
			attackMoves.add(move);
		}
	}
	return attackMoves;
}
public King establishKing() {
	for(final Piece piece:getActivePieces()) {
		if(piece.getPieceType().isKing()) {
			return  (King) piece;
		}
	}
	throw new RuntimeException("Should not reach here! Not a valid Board without the king!");
}
public King getPlayerKing() {
	return this.playerKing;
}
public Collection<Move>getLegalMoves(){
	return this.legalMoves;
}
public boolean isMoveLegal(final Move move) {
	return this.legalMoves.contains(move);
}
public boolean isInCheck() {
	return this.isInCheck;
}
//CheckMate means that the King is in check and has no legal moves
public boolean isInCheckMate() {
	return this.isInCheck & !hasEscapeMoves();
}
public boolean isInStaleMate() {
	return !this.isInCheck & !hasEscapeMoves();
}
public boolean isCastle() {
	return false;
}
protected boolean hasEscapeMoves() {
	for(final Move move: this.legalMoves) {
		final MoveTransition transition = makeMove(move);
		if(transition.getMoveStatus().isDone()) {
			return true;
		}
	}
	return false;
}
public MoveTransition makeMove(final Move move) {
	if(!isMoveLegal(move)) {
		return new MoveTransition(this.board,move,MoveStatus.ILLEGAL_MOVE);
	}
	final Board transitionBoard = move.execute();
	Collection<Move>kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer().getOpponent().getPlayerKing().getPiecePosition(), 
                                                                transitionBoard.currentPlayer().getLegalMoves());
	 if(!kingAttacks.isEmpty()){
         return new MoveTransition(this.board,move,MoveStatus.LEAVES_PLAYER_IN_CHECK);
     }
     return new MoveTransition(this.board,move,MoveStatus.DONE);
}  
public abstract Collection<Piece>getActivePieces();
public abstract Alliance getAlliance();
public abstract Player getOpponent();
protected abstract Collection<Move>calculateKingCastle(Collection<Move>playerLegals,Collection<Move>opponentLegals);
}
