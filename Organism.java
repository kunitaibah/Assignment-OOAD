import java.util.Vector; 

public abstract class Organism{
    
    protected int x, y;
    protected int moveCount;
	Vector<Integer> emptyCell = new Vector<Integer>(4);
	Vector<Integer> gotAnt = new Vector<Integer>(4);
	protected int Size = 20;
	protected boolean moved;
	protected int move;
	
//constructor
    public Organism(int x, int y, int moveCount){
        this.x = x;
        this.y = y;
        this.moveCount = moveCount;
		moved = false;
    }
    
    public Organism(){
        this.x = x;
        this.y = y;
        this.moveCount = moveCount;
		moved = false;
    }
// getter
    public int getCurrentX(){
        return x;
    }
    public int getCurrentY(){
        return y;
    }

    public int getMoveCount(){
        return moveCount;
    }
	
	public boolean getMoved(){
        return moved;
    }
// setter
    public void setCurrentX(int x){
        this.x = x;
    }
    public void setCurrentY(int y){
        this.y = y;
    }
    public void setMoveCount(int moveCount){
        this.moveCount = moveCount;
    }   
	
	public void setMoved(boolean moved){
        this.moved = moved;
    }
    
    public void moveToNewLocation(int newX, int newY){
        this.x = newX;
        this.y = newY;
    }
	
	
	public void checkValidMove(int x, int y, Vector<Integer> emptyCell, Organism[][] occupied){
		if(getCurrentX() - 1 >= 0 ){ //up
			if (!(occupied[getCurrentX() -1][getCurrentY()] instanceof Organism))
				emptyCell.add(1);
		}
		if(getCurrentY() + 1 <= Size-1 ){ // right
			if (!(occupied[getCurrentX()][getCurrentY()+1]  instanceof Organism))
				emptyCell.add(2);
		}
		if(getCurrentX() + 1 <= Size-1 ){ //bottom
			if (!(occupied[getCurrentX() +1][getCurrentY()]  instanceof Organism))
				emptyCell.add(3);
		}
		if(getCurrentY() - 1 >= 0 ){ //left
			if (!(occupied[getCurrentX()][getCurrentY()-1]  instanceof Organism))
				emptyCell.add(4);
		}
		if (emptyCell.isEmpty())
			emptyCell.add(0);
	}
	
    public abstract void moveUp(int x, int y);   
    public abstract void moveDown(int x, int y);   
    public abstract void moveLeft(int x, int y);   
    public abstract void moveRight(int x, int y);  
	public abstract void checkValidEat(int x, int y, Vector<Integer> gotAnt, Organism[][] occupied);
	public abstract void starve();
	public abstract boolean getStarve();
	
    
}