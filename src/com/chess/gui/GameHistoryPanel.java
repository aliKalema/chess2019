package com.chess.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.gui.Table.MoveLog;

public class GameHistoryPanel extends JPanel{
	private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100,400);
	private final DataModel model;
	private final JScrollPane scrollPane;
	GameHistoryPanel(){
		this.setLayout(new BorderLayout());
		this.model = new DataModel();
		final JTable table = new JTable(model);
		table.setRowHeight(15);
		this.setBackground(Color.white);
		this.scrollPane = new JScrollPane(table);
		scrollPane.setColumnHeaderView(table.getTableHeader());
		this.add(scrollPane,BorderLayout.CENTER);
		this.setPreferredSize(HISTORY_PANEL_DIMENSION);
		this.setVisible(true);
	}
void redo(final Board board,final MoveLog moveHistory) {
	int currentRow =0;
	this.model.clear();
	for(final Move move:moveHistory.getMoves()) {
		final String movetext = move.toString();
		if(move.getMovedPiece().getPieceAlliance().isWhite()) {
			this.model.setValueAt(movetext, currentRow, 0);
		}
		else if(move.getMovedPiece().getPieceAlliance().isBlack()) {
			this.model.setValueAt(movetext, currentRow, 1);
			currentRow++;
		}
	}
	if(moveHistory.getMoves().size()>0) {
		final Move lastMove =moveHistory.getMoves().get(moveHistory.size()-1);
		final String moveText = lastMove.toString();
		if(lastMove.getMovedPiece().getPieceAlliance().isWhite()) {
			this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board),currentRow,0);
		}
		else if(lastMove.getMovedPiece().getPieceAlliance().isBlack()) {
			this.model.setValueAt(moveText + calculateCheckAndCheckMateHash(board),currentRow-1,1);
		}
	}
	final JScrollBar verticle = scrollPane.getVerticalScrollBar();
	verticle.setValue(verticle.getMaximum());
}
private String calculateCheckAndCheckMateHash(Board board) {
	if(board.currentPlayer().isInCheckMate()) {
		return "#";
	}
	else if(board.currentPlayer().isInCheck()) {
		return "+";
	}
	return "";
}
private static class DataModel extends DefaultTableModel{
	private final List<Row>values;
	private static final String[]NAMES = {"white","black"};
	DataModel(){
		this.values = new ArrayList<>();
	}
	public void clear() {
		this.values.clear();
		setRowCount(0);
	}
	@Override
	public int getRowCount() {
		if(this.values == null) {
			return 0;
		}
		return this.values.size();
	}
	@Override 
	public int getColumnCount() {
		return NAMES.length;
	}
	@Override
	public Object getValueAt(final int row,final int column) {
		final Row currentRow = this.values.get(row);
		if(column == 0) {
			return currentRow.getWhiteMove();
		}
		else if(column == 1) {
			return currentRow.getWhiteMove();
		}
		return null;
	}
	@Override
	public void setValueAt(final Object aValue,final int row, final int column) {
		final Row currentRow;
		if(this.values.size() <=row) {
			currentRow = new Row();
			this.values.add(currentRow);
		}else {
			currentRow = this.values.get(row);
		}
		if(column == 0) {
			currentRow.setWhiteMove((String)aValue);
			fireTableCellUpdated(row,column);
		}
		else if(column == 1) {
			currentRow.setBlackMove((String)aValue);
			fireTableCellUpdated(row,column);
		}
	}
	@Override 
	public Class<?>getColumnClass(final int column){
		return Move.class;
	}
	@Override
	public String getColumnName(final int column) {
		return NAMES[column];
	}
	
}
private static class Row{
	private String  whiteMove;
	private String blackMove;
	Row(){
		
	}
	public String getWhiteMove() {
		return whiteMove;
	}
	public void setWhiteMove(String whiteMove) {
		this.whiteMove = whiteMove;
	}
	public String getBlackMove() {
		return blackMove;
	}
	public void setBlackMove(String blackMove) {
		this.blackMove = blackMove;
	}
	
}
}
