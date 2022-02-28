package com.chess.engine.board;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chess.engine.pieces.Bishop;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Queen;
import com.chess.engine.pieces.Knight;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;
import com.chess.engine.players.Alliance;
import com.chess.engine.players.BlackPlayer;
import com.chess.engine.players.Player;
import com.chess.engine.players.WhitePlayer;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
public class Board {
	public final List<Piece>whitePieces;
	private final List<Piece>blackPieces;
	private  WhitePlayer whiteplayer;
	public  BlackPlayer blackplayer;
	private  Player currentplayer;
	private  final List<Tile>gameBoard;
	public final List<Move>blackStandardLegalMoves;
	public final List<Move>whiteStandardLegalMoves;
    private Board(final Builder builder) {
	this.gameBoard = createGameBoard(builder);
	this.whitePieces = calculateActivePieces(Alliance.WHITE,this.gameBoard);
    this.blackPieces = calculateActivePieces(Alliance.BLACK,this.gameBoard);
    this.whiteStandardLegalMoves= calculateLegalMoves(this.whitePieces,this);
    this.blackStandardLegalMoves= calculateLegalMoves(this.blackPieces,this);
    this.whiteplayer = new WhitePlayer(this,whiteStandardLegalMoves,blackStandardLegalMoves);
    this.blackplayer = new BlackPlayer(this,blackStandardLegalMoves,whiteStandardLegalMoves);
    this.currentplayer = builder.nextMoveMaker.choosePlayer(this.whiteplayer,this.blackplayer);
    }  
	public Tile getTile(int tileCoordinate) {
		return gameBoard.get(tileCoordinate);
	}
	public Collection<Piece>getBlackPieces(){
		return this.blackPieces;
	}
	public Collection<Piece>getWhitePieces(){
		return this.whitePieces;
	} 
	private List<Tile> createGameBoard(Builder builder) {
	   	 Tile tiles[]= new Tile[64];
	        for(int i=0;i<64;i++){
	        	Piece piece = builder.boardConfig.get(i);
	            tiles[i]=Tile.createTiles(i, piece);
	        }
	        return ImmutableList.copyOf(tiles);
	   	}
    public static List<Piece>calculateActivePieces(Alliance alliance,List<Tile>gameBoard) {
        List<Piece> activePieces = new ArrayList<>();
        for(Tile tile : gameBoard) {
        	 if (tile.isTileOccupied() && tile.getPiece().getPieceAlliance() == alliance) {
        		 activePieces.add(tile.getPiece());
             }
       }
       return ImmutableList.copyOf(activePieces);
    }
    private  static List<Move> calculateLegalMoves(List<Piece>pieces,Board board) {
    	List<Move> legalMoves = new ArrayList<>();
        for(final Piece piece : pieces) {
            legalMoves.addAll(piece.calculateLegalMoves(board));
        }
        return legalMoves;
    }
    public Player currentPlayer(){
        return this.currentplayer;
    }
    public Player whitePlayer() {
    	return this.whiteplayer;
    }
    public Player blackPlayer() {
    	return this.blackplayer;
    }
    public Iterable<Move>getAllLegalMoves(){
    	return Iterables.unmodifiableIterable(Iterables.concat(this.whiteplayer.getLegalMoves(),this.blackplayer.getLegalMoves()));
    }
    private static String prettyPrint(Tile tile) {
    	return tile.toString();
    }
    @Override
    public String toString() {
    	       StringBuilder stringbuilder = new StringBuilder();
    	       for (int i = 0; i < 64; i++) {
    	           String tileText = prettyPrint(this.gameBoard.get(i));
    	           stringbuilder.append(String.format("%3s", tileText));
    	          if ((i + 1) % 8 == 0) {
    	              stringbuilder.append("\n");
    	          }
    	      }
    	   return stringbuilder.toString();
    }
    public static Board createStandardBoard(){
        Builder builder = new Builder();
        builder.setPiece(new Rook(0,Alliance.BLACK));
        builder.setPiece(new Knight(1,Alliance.BLACK));
        builder.setPiece(new Bishop(2,Alliance.BLACK));
        builder.setPiece(new Queen(3,Alliance.BLACK));
        builder.setPiece(new King(4,Alliance.BLACK));
        builder.setPiece(new Bishop(5,Alliance.BLACK));
        builder.setPiece(new Knight(6,Alliance.BLACK));
        builder.setPiece(new Rook(7,Alliance.BLACK));
        builder.setPiece(new Pawn(8,Alliance.BLACK));
        builder.setPiece(new Pawn(9,Alliance.BLACK));
        builder.setPiece(new Pawn(10,Alliance.BLACK));
        builder.setPiece(new Pawn(11,Alliance.BLACK));
        builder.setPiece(new Pawn(12,Alliance.BLACK));  
        builder.setPiece(new Pawn(13,Alliance.BLACK));
        builder.setPiece(new Pawn(14,Alliance.BLACK));
        builder.setPiece(new Pawn(15,Alliance.BLACK));
        
        builder.setPiece(new Pawn(48,Alliance.WHITE));
        builder.setPiece(new Pawn(49,Alliance.WHITE));
        builder.setPiece(new Pawn(50,Alliance.WHITE));
        builder.setPiece(new Pawn(51,Alliance.WHITE));
        builder.setPiece(new Pawn(52,Alliance.WHITE));
        builder.setPiece(new Pawn(53,Alliance.WHITE));
        builder.setPiece(new Pawn(54,Alliance.WHITE));
        builder.setPiece(new Pawn(55,Alliance.WHITE));
        builder.setPiece(new Rook(56,Alliance.WHITE));
        builder.setPiece(new Knight(57,Alliance.WHITE));
        builder.setPiece(new Bishop(58,Alliance.WHITE));
        builder.setPiece(new Queen(59,Alliance.WHITE));
        builder.setPiece(new King(60,Alliance.WHITE));
        builder.setPiece(new Bishop(61,Alliance.WHITE));
        builder.setPiece(new Knight(62,Alliance.WHITE));
        builder.setPiece(new Rook(63,Alliance.WHITE));
        builder.setMoveMaker(Alliance.WHITE);
      return builder.build();
   }

public static class Builder{
	 Alliance nextMoveMaker;
	 Map<Integer,Piece>boardConfig;
	 Pawn enPassantPawn;
	  public Builder() {
		  this.boardConfig = new HashMap<>();
	  }
	  public Builder setPiece(final Piece piece) {
		  this.boardConfig.put(piece.getPiecePosition(),piece);
		  return this;
	  }
	  public Builder setMoveMaker(final Alliance nextMoveMaker) {
		  this.nextMoveMaker= nextMoveMaker;
		  return this;
	  }
	  public Board build() {
		  Board board = new Board(this);
		  return board;
	  }
	  public void setEnPassantPawn(Pawn enPassantPawn) {
		this.enPassantPawn = enPassantPawn; 
	 }
  }

}
