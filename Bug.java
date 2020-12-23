import java.util.Vector; 

public class Bug extends Organism{
    
    private int bugStarve = 3;
	
    public Bug(int x,int y,int moveCount){
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
    public void moveRight(int x, int y){
        if(getCurrentY() + 1 <= Size-1 ){
			this.x = x;
			this.y = y +1;
			moved = true;
			moveCount++;
        }
	}
	
	@Override
	public void checkValidEat(int x, int y, Vector<Integer> gotAnt, Organism[][] occupied){
		if(getCurrentX() - 1 >= 0 ){ //up
			if (occupied[getCurrentX() -1][getCurrentY()] instanceof Ant) // to check if it does occupy Ant
				gotAnt.add(1);
		}		
		if(getCurrentY() + 1 <= Size-1 ){ // right
			if (occupied[getCurrentX()][getCurrentY()+1]  instanceof Ant) // to check if it does occupy Ant
				gotAnt.add(2);
		}
		if(getCurrentX() + 1 <= Size-1 ){ //bottom
			if ((occupied[getCurrentX() +1][getCurrentY()] instanceof Ant)) // to check if it does occupy Ant
				gotAnt.add(3);
		}
		if(getCurrentY() - 1 >= 0 ){ //left
			if (occupied[getCurrentX()][getCurrentY()-1] instanceof Ant)// to check if it does occupy Ant
				gotAnt.add(4);
		}
		if (gotAnt.isEmpty())
			gotAnt.add(0);
	}
	
	@Override
    public boolean getStarve(){
		if (bugStarve ==0){
			bugStarve =3;
			return true;
		}
		else 
			return false;
		
	}
	
	@Override
	public void starve(){
		bugStarve--;
	}
	
       
}
