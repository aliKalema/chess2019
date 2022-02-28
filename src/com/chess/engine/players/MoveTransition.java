package com.chess.engine.players;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus movestatus;
	public MoveTransition(Board board, Move move, MoveStatus movestatus) {
		this.transitionBoard = board;
		this.move = move;  
		this.movestatus = movestatus;
	}
	public MoveStatus getMoveStatus() {
		return this.movestatus;
	}
	public Board getTransitionBoard() {
		return this.transitionBoard;
	}
}
