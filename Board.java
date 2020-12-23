import java.util.*;
import java.util.Vector; 
import java.util.Random.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.*;
import java.io.*;


public class Board extends JFrame implements KeyListener{
	
	private final int SIZE = 20;
	private int b = 5; //bug
	private int a = 100; //ant 
	private boolean occupied;
	private int X;
	private int Y;
	private int currentMove = 0;
	private boolean reset = false;
	private Organism creature[][] = new Organism[SIZE][SIZE];
	
	
	JButton[][] button = new JButton[SIZE][SIZE];
	JPanel topBoard = new JPanel(new FlowLayout());	//for start and quit button
	JPanel mainBoard = new JPanel(new GridLayout(SIZE,SIZE)); // for grid 20x20
	JPanel bottomBoard = new JPanel(new FlowLayout()); // for JLabel moveCount ant and bug

	JButton startBtn = new JButton("Start");
	JButton quitBtn = new JButton("Quit");
	JButton restartBtn = new JButton("Restart");
		
	Random rand = new Random();
	Vector<Integer> emptyPosition = new Vector<Integer>(SIZE*SIZE);// to ensure empty postion for initialization
	Vector<Integer> emptyCell = new Vector<Integer>(4);
	Vector<Integer> gotAnt = new Vector<Integer>(4);
	
	ImageIcon antIcon = new ImageIcon("ant.png");
	ImageIcon bugIcon = new ImageIcon("bug.png");
	
	public Board(){
		super("Lets Play the Game");

		startBtn.setBackground(Color.LIGHT_GRAY);
		quitBtn.setBackground(Color.LIGHT_GRAY);
		restartBtn.setBackground(Color.LIGHT_GRAY);
	
		topBoard.add(startBtn);
		topBoard.add(quitBtn);
		
		generateEmpty();
		
		startBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				initializeGame();
				startBtn.setVisible(false);
				topBoard.add(restartBtn);
			}
		});
		quitBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				int selectedOption = JOptionPane.showConfirmDialog(null,
									"Do you wanna quit this game?", "Choose",
									JOptionPane.YES_NO_OPTION);
				if(selectedOption == JOptionPane.YES_OPTION)
					System.exit(0);
				else if (selectedOption == JOptionPane.NO_OPTION){
					return; // cannot continue the game
				}	
			}
		});
		restartBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				reset = true;
				
				for (int X = 0; X < SIZE; X++) { 
					for (int Y = 0; Y < SIZE; Y++) { 
						creature[X][Y] = null;
						button[X][Y].setIcon(null);
					}
				}
				Integer i =0;
				emptyPosition.removeAllElements();
				while(i<SIZE*SIZE){
					emptyPosition.add(i);
					i++;
				}
				initializeGame();
			}
		});
		
		addKeyListener(this);
		restartBtn.addKeyListener(this);
		setFocusable(true);
		
		add(topBoard, BorderLayout.NORTH); //adding the topBoard panel to JFrame
		add(mainBoard,BorderLayout.CENTER); //adding the mainBoard panel to JFrame
		add(bottomBoard,BorderLayout.SOUTH); //adding the bottomBoard panel to JFrame
		
		setSize(700,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public void initializeGame(){
		
		int i = b;
		while(i!=0){
			int randomNum = rand.nextInt(emptyPosition.size());
			X = randomNum / SIZE;
			Y = randomNum % SIZE;
			
			if (!(creature[X][Y] instanceof Organism)){
				Organism bug = new Bug(X,Y,0);
				creature[X][Y] = bug;
				button[X][Y].setIcon(null);
				button[X][Y].setIcon(bugIcon);
				emptyPosition.removeElement(randomNum);
				emptyPosition.trimToSize(); 
				i--;
			}
			
		}
	
		int j = a;
		while(j!=0){
			int randomNum = rand.nextInt(emptyPosition.size());
			X = randomNum / SIZE;
			Y = randomNum % SIZE;
			if (!(creature[X][Y] instanceof Organism)){
				Organism ant = new Ant(X,Y,0);
				creature[X][Y] = ant;
				button[X][Y].setIcon(antIcon);	
				emptyPosition.removeElement(randomNum);
				emptyPosition.trimToSize(); 
				j--;
			}
			
		}
	}
	
	public void generateEmpty(){
		for (int X = 0; X < SIZE; X++) { 
            for (int Y = 0; Y < SIZE; Y++) {
				button[X][Y] = new JButton();
				button[X][Y].setBackground(Color.WHITE);
				mainBoard.add(button[X][Y]);
            } 
        }
		Integer i =0;
		while(i<SIZE*SIZE){
			emptyPosition.add(i);
			i++;
		}
		
	}
	
	public void playingAnt(Organism[][] creature){ 
		int antCount = 0;
		int bugCount = 0;
		
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Ant){
					creature[X][Y].setMoved(false);
				}
			}
		}
		
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Ant){ //to know the current position is Ant
					creature[X][Y].checkValidMove(X,Y, emptyCell, creature);
					int antNewLocation = rand.nextInt(emptyCell.size()) ;
					int newX, newY;
					if (emptyCell.get(antNewLocation)>0 && creature[X][Y].getMoved()== false){
						if (emptyCell.get(antNewLocation)== 1){
							creature[X][Y].moveUp(X,Y); 
							newX = creature[X][Y].getCurrentX();
							creature[newX][Y] = creature[X][Y];
						}
						else if(emptyCell.get(antNewLocation) == 2){
							creature[X][Y].moveRight(X,Y);
							newY = creature[X][Y].getCurrentY();
							creature[X][newY] = creature[X][Y];
						}	
						else if(emptyCell.get(antNewLocation) == 3){
							creature[X][Y].moveDown(X,Y);
							newX = creature[X][Y].getCurrentX();
							creature[newX][Y] = creature[X][Y];
						}
						 
						else if (emptyCell.get(antNewLocation) == 4)	{	
							creature[X][Y].moveLeft(X,Y); 
							newY = creature[X][Y].getCurrentY();
							creature[X][newY] = creature[X][Y];
							
						}
						creature[X][Y] = null; 
						button[X][Y].setIcon(null);
					}
					emptyCell.clear();
				} 
			}
		}
		
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Ant && creature[X][Y].getMoveCount() == 3){
					creature[X][Y].checkValidMove(X,Y, emptyCell, creature);
					for(int i = 0; i< emptyCell.size(); i++){
						if (emptyCell.get(i)==1)
							creature[X-1][Y] = new Ant(X-1,Y,0);
						if (emptyCell.get(i)==2)
							creature[X][Y+1] = new Ant(X,Y+1,0);
						if (emptyCell.get(i)==3)
							creature[X+1][Y] = new Ant(X+1,Y,0);
						if (emptyCell.get(i)==4)
							creature[X][Y-1] = new Ant(X,Y-1,0);
					}
					emptyCell.clear();
					creature[X][Y].setMoveCount(0);
				}
			}
		}
	}
	
	public void playingBug(Organism[][] creature){
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Bug){
					creature[X][Y].setMoved(false);
				}
			}
		}
		
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Bug){ //to know the current position is bug
					creature[X][Y].checkValidEat(X,Y, gotAnt, creature);
					int newX, newY, bugStarve;
					int antToEat = rand.nextInt(gotAnt.size()) ;
					if (gotAnt.get(antToEat)>0 && creature[X][Y].getMoved()== false){
							if (gotAnt.get(antToEat) == 1 ){
								creature[X][Y].moveUp(X,Y); 
								newX = creature[X][Y].getCurrentX();
								creature[newX][Y] = creature[X][Y];
							}
							else if(gotAnt.get(antToEat) == 2){
								creature[X][Y].moveRight(X,Y);
								newY = creature[X][Y].getCurrentY();
								creature[X][newY] = creature[X][Y];
							}	
							else if(gotAnt.get(antToEat) == 3){
								creature[X][Y].moveDown(X,Y);
								newX = creature[X][Y].getCurrentX();
								creature[newX][Y] = creature[X][Y];
							}
							 
							else if (gotAnt.get(antToEat) == 4)	{	
								creature[X][Y].moveLeft(X,Y); 
								newY = creature[X][Y].getCurrentY();
								creature[X][newY] = creature[X][Y];
							}
							
							creature[X][Y] = null; 
							button[X][Y].setIcon(null);
						//}
					}
					else if (antToEat<=0 && creature[X][Y].getMoved()== false){
						creature[X][Y].checkValidMove(X,Y, emptyCell, creature);
						int bugNewLocation = rand.nextInt(emptyCell.size());
						if (emptyCell.get(bugNewLocation) == 1 ){
							creature[X][Y].moveUp(X,Y); 
							newX = creature[X][Y].getCurrentX();
							creature[newX][Y] = creature[X][Y];
							creature[newX][Y].starve();
						}
						else if(emptyCell.get(bugNewLocation) == 2){
							creature[X][Y].moveRight(X,Y);
							newY = creature[X][Y].getCurrentY();
							creature[X][newY] = creature[X][Y];
							creature[X][newY].starve();
						}	
						else if(emptyCell.get(bugNewLocation) == 3){
							creature[X][Y].moveDown(X,Y);
							newX = creature[X][Y].getCurrentX();
							creature[newX][Y] = creature[X][Y];
							creature[newX][Y].starve();
						}
						 
						else if (emptyCell.get(bugNewLocation) == 4)	{	
							creature[X][Y].moveLeft(X,Y); 
							newY = creature[X][Y].getCurrentY();
							creature[X][newY] = creature[X][Y];
							creature[X][newY].starve();
						}
						creature[X][Y] = null; 
						button[X][Y].setIcon(null);
						
					}
					gotAnt.clear();
					emptyCell.clear();
				
				}//end if bug
			}
		}
		emptyCell.clear();
					
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++) {
				if (creature[X][Y] instanceof Bug && creature[X][Y].getMoveCount() == 8){
					creature[X][Y].checkValidMove(X,Y, emptyCell, creature);
					for(int i = 0; i< emptyCell.size(); i++){
						if (emptyCell.get(i)==1)
							creature[X-1][Y] = new Bug(X-1,Y,0);
						if (emptyCell.get(i)==2)
							creature[X][Y+1] = new Bug(X,Y+1,0);
						if (emptyCell.get(i)==3)
							creature[X+1][Y] = new Bug(X+1,Y,0);
						if (emptyCell.get(i)==4)
							creature[X][Y-1] = new Bug(X,Y-1,0);
					}
					emptyCell.clear();
					creature[X][Y].setMoveCount(0);
				}
				if (creature[X][Y] instanceof Bug){
					boolean removeBug = creature[X][Y].getStarve();
					if (removeBug){
						creature[X][Y] = null;
						button[X][Y].setIcon(null);
					}
				}
			}
		}
		
	}
	
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode () == KeyEvent.VK_ENTER) {
			if (reset == true){
				currentMove =0;
				reset = false;
			}
			currentMove++;
			if (currentMove%2 == 1){
				playingBug(creature);
				
			}
			else if (currentMove%2 == 0){
				playingAnt(creature);
				
			} 
			display();
		}
	}
	public void keyReleased(KeyEvent e){}
	public void keyTyped(KeyEvent e){}	
	
	public void display(){
		int antCount = 0;
		int bugCount = 0;
		System.out.println(' ');
		for (int X = 0; X < SIZE; X++) { 
			for (int Y = 0; Y < SIZE; Y++){
				if (creature[X][Y] instanceof Ant){
					button[X][Y].setIcon(antIcon);
				}
				if (creature[X][Y] instanceof Bug){
					button[X][Y].setIcon(bugIcon);
				}
			}
		}
		
	}
}
