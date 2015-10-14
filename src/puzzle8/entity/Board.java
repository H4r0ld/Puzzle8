package puzzle8.entity;

import java.util.Arrays;
import java.util.ArrayList;

public class Board implements Cloneable{

    private int n; //The matrix is n*n
    private byte matrix[][];

    // Posicion del espacio vacio
    private int blankX;
    private int blankY;
    //Historial de movimientos
    private ArrayList<Direction> history;

    public Board(int n){
        if(n<8) n=8;
        this.matrix = new byte[n][n];
        history = new ArrayList<Direction>();
    }

    public Board(int n, byte matrix[][]){
        this.n = n;
        this.matrix = matrix;
        history = new ArrayList<Direction>();
        findBlankPosition();
    }

    /**
     * Calcula la posicion del espacio vacio.
     * El espacio vacio se representa por un cero.
     */
    private void findBlankPosition(){
        for(int y = 0; y < n; y++) {
            for(int x = 0; x < n; x++) {
                if(matrix[x][y] == 0) {
                    blankX = x;
                    blankY = y;
                    return;
                }
            }
        }
    }

    public int getBlankX() {
        return blankX;
    }

    public int getBlankY() {
        return blankY;
    }
    
    public byte getValue(int x, int y){
        return matrix[x][y];
    }

    public ArrayList<Direction> getHistory(){
        return history;
    }

    /**
    * Verifica si un movimiento se puede realizar
    *
    */
    public boolean checkMove(Direction dir){
        switch(dir){
            case UP: return (blankY-1)>=0;
            case DOWN: return (blankY+1)<n;
            case LEFT: return (blankX-1)>=0;
            case RIGHT: return (blankX+1)<n;
            default: return false;
        }
    }

    //Hace un movimiento en el tablero actual y regresa un
    //valor booleano indicando si tuvo exito la operacion
    public boolean move(Direction dir){
        int newX = blankX;
        int newY = blankY;
        //Verificando si se puede realizar el movimiento
        if(!checkMove(dir)) return false;
        // Calcula la nueva posicion del espacio vacio
        switch(dir) {
            case UP:
                newY--;
                System.out.println("UP"); // Debug
                break;
            case DOWN:
                newY++;
                System.out.println("DOWN"); // Debug
                break;
            case LEFT:
                newX--;
                System.out.println("LEFT");  // Debug
                break;
            case RIGHT:
                newX++;
                System.out.println("RIGHT");  // Debug
                break;
            default:
                return false;
        }
        //Moviendo el tablero
        matrix[blankX][blankY] = matrix[newX][newY];
        matrix[newX][newY] = 0;
        //Cambiando las coordenadas del cero
        blankX = newX;
        blankY = newY;
        //Agregando a la lista de movimientos el que se acaba de hacer
        history.add(dir);
        System.out.println(history); //Debug
        return true;
    }

    public ArrayList<Board> expand(){
	ArrayList<Board> s = new ArrayList<Board>();
	Board temp = null;
	//Si se puede mover a la izquierda
	if(checkMove(Direction.LEFT) && (history.size()==0 || history.get(history.size()-1)!=Direction.RIGHT)){
	    try{
		temp = (Board)this.clone();
	    }catch(Exception e){}
	    temp.move(Direction.LEFT);
	    s.add(temp);
	}
	//Si se puede mover a la derecha
	if(checkMove(Direction.RIGHT) && (history.size()==0 || history.get(history.size()-1)!=Direction.LEFT)){
	    try{
		temp = (Board)this.clone();
	    }catch(Exception e){}
	    temp.move(Direction.RIGHT);
	    s.add(temp);
	}
	//Si se puede mover a abajo
	if(checkMove(Direction.DOWN) && (history.size()==0 || history.get(history.size()-1)!=Direction.UP)){
	    try{
		temp = (Board)this.clone();
	    }catch(Exception e){}
	    temp.move(Direction.DOWN);
	    s.add(temp);
	}
	//Si se puede mover a arriba
	if(checkMove(Direction.UP) && (history.size()==0 || history.get(history.size()-1)!=Direction.DOWN)){
	    try{
		temp = (Board)this.clone();
	    }catch(Exception e){}
	    temp.move(Direction.UP);
	    s.add(temp);	
	}
	return s;
    }
    
    @Override
    public Object clone(){
	Board b = new Board(n);
	b.history = (ArrayList<Direction>)history.clone();
	b.blankX = blankX;
	b.blankY = blankY;
	for(int i=0; i<n; i++){
	    for(int j=0; j<n; j++){
		b.matrix[i][j] = matrix[i][j];
	    }
	}
	return (Object)b;
    }

    public boolean equals(Board board) {
        boolean result = true;
        for(int i = 0; i < n; i++)
            result = result && Arrays.equals(matrix[i], board.matrix[i]);
        return result;
    }
}
