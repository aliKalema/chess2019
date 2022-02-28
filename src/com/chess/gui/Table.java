package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece;
import com.chess.engine.players.MoveTransition;
import com.google.common.collect.Lists;

public class Table{
	private Board chessBoard;
	private final JFrame gameFrame;
	private final GameHistoryPanel gameHistoryPanel;
	private final TakenPiecesPanel takenPiecesPanel;
	private final BoardPanel boardPanel;
	//private final JPanel edge;
	private final MoveLog moveLog;
	private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
	private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(400,350);
	private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10,10);
	//private static final Dimension EDGE_FRAME_DIMENSION = new Dimension(450,400);
	private static String defaultPieceImagesPath = "art/simple/";
	private BoardDirection boardDirection; 
	private boolean highlightLegalMoves = false;
	private Tile sourceTile;
	private Tile destinationTile;
	private Piece humanMovedPiece;
	public Table() {
		this.chessBoard = Board.createStandardBoard();
		this.moveLog = new MoveLog();
		//this.edge = new JPanel(new GridBagLayout());
		//Color color = new Color(160,82,45);
		//edge.setBackground(color);
		//edge.setSize(EDGE_FRAME_DIMENSION);
		this.gameFrame = new JFrame("JChess");
		this.gameFrame.setLayout(new BorderLayout());
		final JMenuBar tableMenuBar = createTableMenuBar();
		this.gameHistoryPanel = new GameHistoryPanel();
		this.takenPiecesPanel = new TakenPiecesPanel();
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.add(takenPiecesPanel,BorderLayout.WEST);
		this.gameFrame.add(gameHistoryPanel,BorderLayout.EAST);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.boardPanel = new BoardPanel();
		this.boardDirection = BoardDirection.NORMAL;
		this.gameFrame.add(boardPanel,BorderLayout.CENTER);
		this.gameFrame.setVisible(true);
	}
    private JMenuBar createTableMenuBar(){
		final JMenuBar tableMenuBar = new JMenuBar();
		tableMenuBar.add(createFileMenu());
		tableMenuBar.add(createPreferencesMenu());
		return tableMenuBar;
    }
    private JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("File");
        JMenuItem openPGN = new JMenuItem("Load PGN File");
        fileMenu.add(openPGN);
        openPGN.addActionListener(new ActionListener(){ 
            @Override
            public void actionPerformed(ActionEvent ae) {
              JOptionPane.showMessageDialog(null,"FUNCTIONALITY NOT SUPPORTED YET");
              System.out.println("PGN loading...");
            }
        });
        JMenuItem exitGame = new JMenuItem("EXIT");
        fileMenu.add(exitGame);
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(JOptionPane.showConfirmDialog(new JFrame(),JOptionPane.YES_NO_OPTION, "ARE YOU SURE YOU WANT TO EXIT?", 1) == JOptionPane.YES_OPTION){
                   System.exit(0);
                 }
            }
        });
        JMenuItem newGame = new JMenuItem("NEWGAME");
        fileMenu.add(newGame);
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	if(JOptionPane.showConfirmDialog(new JFrame(),JOptionPane.YES_NO_OPTION, "ARE YOU SURE YOU WANT TO RESTART THE GAME?", 1) == JOptionPane.YES_OPTION){
                    chessBoard = Board.createStandardBoard();
            		boardPanel.drawBoard(chessBoard);
                  }
            }
        });
        return fileMenu ;
    }
    private JMenu createPreferencesMenu() {
    	final JMenu preferencesMenu = new JMenu("Preferences");
    	final JMenuItem flipBoardItem = new JMenuItem("Flip Board");
    	preferencesMenu.add(flipBoardItem);
    	flipBoardItem.addActionListener(new ActionListener() {
    		 @Override
             public void actionPerformed(ActionEvent ae) {
             	boardDirection = boardDirection.opposite();
             	boardPanel.drawBoard(chessBoard);
             }
    	});
    	preferencesMenu.addSeparator();
    	final JCheckBoxMenuItem legalMoveHighLighter = new JCheckBoxMenuItem("Highlight legal Moves",false);
    	legalMoveHighLighter.addActionListener(new ActionListener() {
    		 @Override
             public void actionPerformed(ActionEvent ae) {
             	highlightLegalMoves = legalMoveHighLighter.isSelected();
             }
    	});
    	preferencesMenu.add(legalMoveHighLighter);
    	return preferencesMenu;
    }
    
    private class BoardPanel extends JPanel{
    	final List<TilePanel>boardTiles;
    	BoardPanel(){
    		super(new GridLayout(8,8));
    		this.boardTiles = new ArrayList<>();
    		for(int i =0;i<64;i++) {
    			final TilePanel tilePanel = new TilePanel(this,i);
    			this.boardTiles.add(tilePanel);
    			this.add(tilePanel);
    		}
    		this.setPreferredSize(BOARD_PANEL_DIMENSION);
    	}
		public void drawBoard(final Board chessBoard) {
			removeAll();
			for(final TilePanel tilePanel:boardDirection.traverse(boardTiles)) {
				tilePanel.drawTile(chessBoard);
				add(tilePanel);
			}
			System.out.println(chessBoard);
			validate();
			repaint();
		}
    }
    public static class MoveLog{
    	private final List<Move>moves;
    	public MoveLog() {
    		this.moves = new ArrayList<>();
    	}
    	public List<Move>getMoves(){
    		return this.moves;
    	}
    	public void addMove(final Move move) {
    		this.moves.add(move);
    	}
    	public int size() {
    		return this.moves.size();
    	}
    	public void clear() {
    		this.moves.clear();
    	}
    	public Move removeMove(final int index) {
    		return this.moves.remove(index);
    	}
    	public boolean removeMove(final Move move) {
    		return this.moves.remove(move);
    	}
    }
    
    private class TilePanel extends JPanel{
    	private final int tileId;
    	TilePanel(final BoardPanel boardPanel,final int tileId){
    		super(new GridBagLayout());
    		this.tileId = tileId;
    		this.setPreferredSize(TILE_PANEL_DIMENSION);
    		assignTileColor();
    		assignTilePieceIcon(chessBoard);
    		highlightLegals(chessBoard);
    		this.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (SwingUtilities.isRightMouseButton(e)) {
						sourceTile = null;
						destinationTile = null;
						humanMovedPiece = null;
					}
					else if(SwingUtilities.isLeftMouseButton(e)) {
						if(sourceTile == null) {  //First click
							sourceTile = chessBoard.getTile(tileId);
							humanMovedPiece = sourceTile.getPiece();
							boardPanel.drawBoard(chessBoard);
							if(humanMovedPiece == null) {
								sourceTile = null; 
							}
						}else {
							   //second click
							destinationTile = chessBoard.getTile(tileId);
							final Move move = Move.MoveFactory.createMove(chessBoard, sourceTile.getTileCoordinate(),destinationTile.getTileCoordinate());
							final MoveTransition transition = chessBoard.currentPlayer().makeMove(move);
							if(transition.getMoveStatus().isDone()) {
								chessBoard = move.execute();
								moveLog.addMove(move);
							}
							sourceTile = null;
							destinationTile = null;
							humanMovedPiece = null;
						 }
						SwingUtilities.invokeLater(new Runnable() {
							@Override
							public void run() {
								gameHistoryPanel.redo(chessBoard, moveLog);
								takenPiecesPanel.redo(moveLog);
								boardPanel.drawBoard(chessBoard);
							}
						});
					} 
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					
				}
				@Override
				public void mouseExited(MouseEvent e) {
					
				}
				@Override
				public void mousePressed(MouseEvent e) {
					
				}
				@Override
				public void mouseReleased(MouseEvent e) {
					
				}
    		});
    		validate();
    	}
		public void drawTile(Board chessBoard) {
			assignTileColor();
			assignTilePieceIcon(chessBoard);
			validate();
			repaint();
		}
		private void assignTileColor() {
			if(BoardUtils.EIGHTH_RANK[this.tileId] ||
			   BoardUtils.SIXTH_RANK[this.tileId] ||
			   BoardUtils.FOUTH_RANK[this.tileId] ||
			   BoardUtils.SECOND_RANK[this.tileId]) {
				this.setBackground(this.tileId %2 !=0 ?Color.white : Color.LIGHT_GRAY);
			}
			else if(BoardUtils.SEVENTH_RANK[this.tileId] ||
					BoardUtils.FIFTH_RANK[this.tileId] ||
					BoardUtils.THIRD_RANK[this.tileId] ||
					BoardUtils.FIRST_RANK[this.tileId]) {
				    this.setBackground(this.tileId %2 == 0 ?Color.white : Color.LIGHT_GRAY);
					}
		}
    	private void assignTilePieceIcon(final Board board) {
    		this.removeAll();
    		if(board.getTile(this.tileId).isTileOccupied()) {
    			try {
					final BufferedImage image = 
						    ImageIO.read(new File(defaultPieceImagesPath + 
						    		              board.getTile(this.tileId).getPiece().getPieceAlliance().toString().substring(0, 1).toLowerCase() +          
						    		              board.getTile(this.tileId).getPiece() +
						    		              ".png"));
					this.add(new JLabel(new ImageIcon(image)));
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    		highlightLegals(chessBoard);
    	}
    	private void highlightLegals(final Board board) {
    		if(highlightLegalMoves) {
    			for(Move move: pieceLegals(board)) {
    				if(move.getDestinationCoordinate() == this.tileId) {
    					if(board.getTile(this.tileId).isTileOccupied()) {
    						this.setBackground(Color.yellow);
    					}
    					else if(!board.getTile(this.tileId).isTileOccupied()) {
    						try {
    	    					   TilePanel tilepanel = boardPanel.boardTiles.get(move.getDestinationCoordinate());
    	    					   tilepanel.add(new JLabel(new ImageIcon(ImageIO.read(new File(defaultPieceImagesPath+"green_dot.png")))));
    	    				    }catch(Exception e) {
    	    					    e.printStackTrace();
    	    				    }
    					 }
    			    }
    			}
    		}
    	}
    	private Collection<Move>pieceLegals(final Board board){
    		if(humanMovedPiece != null &&humanMovedPiece.getPieceAlliance()== board.currentPlayer().getAlliance() ) {
    			return humanMovedPiece.calculateLegalMoves(board);
    		}
    		return Collections.emptyList();
    	}
    }
    public enum BoardDirection{
    	NORMAL {
			@Override
			public BoardDirection opposite() {
				return FLIPPED;
			}
			@Override
			public List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return boardTiles;
			}
		},
    	FLIPPED {
			@Override
			public List<TilePanel> traverse(List<TilePanel> boardTiles) {
				return Lists.reverse(boardTiles);
			} 
			@Override
			public BoardDirection opposite() {
				return NORMAL;
			}
		};
    	public abstract List<TilePanel>traverse(final List<TilePanel>boardTiles);
    	public abstract BoardDirection opposite();
    }
}




