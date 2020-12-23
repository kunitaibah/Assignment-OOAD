import java.util.Vector; 

public class Ant extends Organism{      

    private boolean canBreed;
    private int antBreed = 3;

    
    public Ant(int x, int y,int moveCount){
        super(x,y,moveCount);
    }	
    
    @Override
    public void moveUp(int x, int y){
        if(getCurrentX() - 1 >= 0 ){
			this.x = x - 1;
			this.y = y;
			moved = true;
			moveCount++;
        }
    }
    
	@Override
    public void moveRight(int x, int y){
        if(getCurrentY() + 1 <= Size-1 ){
			this.x = x;
			this.y = y +1;
			moved = true;
			moveCount++;
        }
	}
	
	@Override
    public void moveDown(int x, int y){
        if(getCurrentX() + 1 <= Size-1 ){
			this.x = x + 1;
			this.y = y;
			moved = true;
			moveCount++;
        }
    }
	
	@Override
    public void moveLeft(int x, int y){
        if(getCurrentY() - 1 >= 0 ){ 
			this.x = x;
			this.y = y -1;
			moved = true;
			moveCount++;
        }
    }
	
	@Override
	public void checkValidEat(int x, int y, Vector<Integer> gotAnt, Organism[][] occupied){
		return;
	}
	
	@Override
	public void starve(){
		return;
	}
	
	@Override
	public boolean getStarve(){
		return false;
	}

}
