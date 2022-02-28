package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;

public abstract class Tile {
    protected final int  tileCoordinate;
    private static final Map<Integer,EmptyTile>EMPTY_TILE_CACHE = createAllPossibleEmptyTiles();
    private Tile(int tileCoordinate) {
	this.tileCoordinate= tileCoordinate;
    }
    public static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {
		final Map<Integer,EmptyTile>emptyTileMap = new HashMap<>();
		for(int i=0;i<64;i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}
		return emptyTileMap;
	}
	public abstract Piece getPiece();
    public abstract boolean isTileOccupied();
    public static Tile createTiles(int tileCoordinate,Piece piece) {
    	return piece != null ? new OccupiedTile(tileCoordinate,piece):EMPTY_TILE_CACHE.get(tileCoordinate);
    }
    public int getTileCoordinate() {
    	return this.tileCoordinate;
    }
    
  public static final class EmptyTile extends Tile{
	private EmptyTile(final int tileCoordinate) {
		super(tileCoordinate);
	}
	@Override
	public Piece getPiece() {
		return null;
	}
	@Override
	public boolean isTileOccupied() {
		return false;
	}
    @Override 
    public String toString() {
    	return "-";
    }
  }
  
  public static final class OccupiedTile extends Tile{
	  private final Piece pieceOnTile;
	  private OccupiedTile(final int tileCoordinate,Piece pieceOnTile) {
		super(tileCoordinate);
		this.pieceOnTile = pieceOnTile;
	}
	@Override
	public Piece getPiece() {
		return this.pieceOnTile;
	}
	@Override
	public boolean isTileOccupied() {
		return true;
	}
    @Override 
    public String toString() {
    return this.getPiece().getPieceAlliance().isWhite()?this.getPiece().toString().toLowerCase():this.getPiece().toString();
    }
  }
}

