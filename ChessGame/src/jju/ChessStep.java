package jju;

public class ChessStep {
	int row;//�к�
	int col;//�к�
	int chessColor;//������ɫ
	
	//����һ��set/get����
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
	public int getChessColor() {
		return chessColor;
	}
	
	
	public void setChessColor(int chessColor) {
		this.chessColor = chessColor;
	}
	//��������ķ���
	public ChessStep() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ChessStep(int row, int col, int chessColor) {
		super();
		this.row = row;
		this.col = col;
		this.chessColor = chessColor;
	}
	
	
	

}
