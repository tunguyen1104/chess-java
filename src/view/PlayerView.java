package view;

public class PlayerView {
	private int mouseX,mouseY,dragX,dragY,fromCol,fromRow;
	private boolean isDrag,activeValid_move;
	private boolean who_turn;
	private boolean check_in;
	public PlayerView()
	{
		this.who_turn=true;
		this.activeValid_move=false;
		this.isDrag=false;
		this.fromCol=-1;
		this.fromRow=-1;
		this.mouseX=-1;
		this.mouseY=-1;
		this.dragX=-1;
		this.dragY=-1;
	}	
	public boolean isCheck_in() {
		return check_in;
	}
	public void setCheck_in(boolean check_in) {
		this.check_in = check_in;
	}
	public boolean isWho_turn() {
		return who_turn;
	}
	public void setWho_turn(boolean who_turn) {
		this.who_turn = who_turn;
	}
	public boolean isActiveValid_move() {
		return activeValid_move;
	}
	public void setActiveValid_move(boolean activeValid_move) {
		this.activeValid_move = activeValid_move;
	}
	public boolean isDrag() {
		return isDrag;
	}
	public int getMouseX() {
		return mouseX;
	}
	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}
	public int getMouseY() {
		return mouseY;
	}
	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	public int getDragX() {
		return dragX;
	}
	public void setDragX(int dragX) {
		this.dragX = dragX;
	}
	public int getDragY() {
		return dragY;
	}
	public void setDragY(int dragY) {
		this.dragY = dragY;
	}
	public int getFromCol() {
		return fromCol;
	}
	public void setFromCol(int fromCol) {
		this.fromCol = fromCol;
	}
	public int getFromRow() {
		return fromRow;
	}
	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}
	public void setDrag(boolean isDrag) {
		this.isDrag = isDrag;
	}
	
}
