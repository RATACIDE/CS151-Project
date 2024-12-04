package mancala;

import java.util.ArrayList; import javax.swing.event.*;
public class Model{
    private int currentBoard[]; //0-5 are player A's pits, 6 is player A's mancala, 7-12 are player B's pits, 13 is player B's mancala
    private int previousBoard[];
    private ArrayList<ChangeListener> listeners;
    private char currentPlayer;
    private char previousPlayer;
    private int undoCounter;
    private int moveCounter; //number of moves made since start of the game/last undo
    public char endState; //if game ends, will contain which player won (would be C if game tied); else has null char
    public Model(int num){
        currentBoard = new int[14];
        for(int i = 0; i < 6; i++){
            currentBoard[i] = num;
            currentBoard[i+7] = num;
        }
        previousBoard = new int[14];
        currentPlayer = 'A';
        previousPlayer = 0;
        undoCounter = 0;
        moveCounter = 0;
        endState = 0;
        listeners = new ArrayList<ChangeListener>();

    }

    public int[] getCurrentBoard(){
        return currentBoard;
    }
    
    public int getCurrentBoardSpecific(int i) {
    	return currentBoard[i];
    }

    public void addChangeListener(ChangeListener listener)
    {
        listeners.add(listener);
    }

    public int getPreviousBoard(int index){
        return previousBoard[index];
    }

    public char getCurrentPlayer(){
        return currentPlayer;
    }

    public char getPreviousPlayer(){
        return previousPlayer;
    }

    public int getUndoCounter(){
        return undoCounter;
    }

    public int getMoveCounter(){
        return moveCounter;
    }

    public void setUndoCounter(int num){
        undoCounter = num;
    }

    public void setMoveCounter(int num){
        moveCounter = num;
    }

    public void setCurrentPlayer(char c){
        currentPlayer = c;
    }

    public void setPreviousPlayer(char c){
        previousPlayer = c;
    }

    public int getYourMancala(){
        if(currentPlayer == 'A'){
            return 6;
        }
        else{
            return 13;
        }
    }

    public int getOthersMancala(){
        if(currentPlayer == 'B'){
            return 6;
        }
        else{
            return 13;
        }
    }

    public void printBoard(){
        for(int i = 12; i >= 7; i--){
            System.out.print(currentBoard[i] + " ");
        }
        System.out.println();
        System.out.println(currentBoard[13] + "                 " + currentBoard[6]);
        for(int i = 0; i < 6; i++){
            System.out.print(currentBoard[i] + " ");
        }
        System.out.println();
    }

    public boolean undo(){
        if(moveCounter == 0 || undoCounter == 3){ //illegal undo, either game has just started, or player hasn't made a move since they last pressed undo, or player has made 3 undos in a row
            return false;
        }
        else{
            for(int i : currentBoard){
                currentBoard[i] = previousBoard[i];
            }
            currentPlayer = previousPlayer;
            if(moveCounter != 1){ //if player's last move wasn't undone, undoCounter gets reset
                undoCounter = 0;
            }
            moveCounter = 0;
            undoCounter++;
          //notify listeners
            ChangeEvent event = new ChangeEvent(this);
            for(ChangeListener listener : listeners){
                listener.stateChanged(event);
            }
            return true;
        }

    }
    
    public boolean makeMove(String pitId) {
        int index = getPitIndexFromId(pitId);  // Convert pitId (e.g., "A1") into the corresponding index
        if (index == -1) {
            return false;  // Invalid pitId
        }

        // Validate if the move is allowed (e.g., it's not an empty pit, it's the current player's turn, etc.)
        if (!distributeStones(index)) {
            return false;
        }

        // Save the current board state before making the move (for undo purposes)
        for (int i = 0; i < currentBoard.length; i++) {
            previousBoard[i] = currentBoard[i];
        }

        // Get the number of stones in the selected pit
        int numStones = currentBoard[index];

        // Empty the selected pit
        currentBoard[index] = 0;

        // Distribute the stones
        int currentPit = index;
        while (numStones > 0) {
            currentPit++;  // Move to the next pit
            currentPit %= 14;  // Wrap around the board if we reach the end (index 13 should wrap to 0)
            
            // Skip the opponent's Mancala (pit 6 for Player A and pit 13 for Player B)
            if (currentPlayer == 'A' && currentPit == 13) {
                currentPit++;  // Skip Player B's Mancala
                currentPit %= 14;
            } else if (currentPlayer == 'B' && currentPit == 6) {
                currentPit++;  // Skip Player A's Mancala
                currentPit %= 14;
            }

            currentBoard[currentPit]++;  // Place one stone in the current pit
            numStones--;  // Decrease the number of stones to distribute
        }

        // After distributing stones, check if the last pit landed in the player's Mancala
        if (isYourMancala(currentPit)) {
            // If the last pit was in the player's Mancala, the player gets another turn
            // No need to switch players
        } else {
            // Otherwise, switch to the other player
            switchPlayer();
        }

        // Check if the game is over
        endGame();

        // Notify listeners to update the view
        ChangeEvent event = new ChangeEvent(this);
        for (ChangeListener listener : listeners) {
            listener.stateChanged(event);  // Notify the listeners (the view will listen to this)
        }

        return true;  // The move was successfully made
    }
    
    public int getPitIndexFromId(String pitId) {
    	int num;
    	if(pitId.contains("A")) {
    		num = Integer.parseInt(pitId.substring(pitId.length() - 1)) - 1;
    		return num;
    	}
    	else if(pitId.contains("B")) {
    		num = Integer.parseInt(pitId.substring(pitId.length() - 1)) - 1;
    		return num + 7;
    	}
    	else {
    		return -1;
    	}
    }

    public boolean isYourPit(int index){
        if(index <= 6 && currentPlayer == 'A'){
            return true;
        }
        else if(index >= 7 && currentPlayer == 'B'){
            return true;
        }
        else{
            return false;
        }
    }

    public void switchPlayer(){
        if(currentPlayer == 'A'){
            currentPlayer = 'B';
        }
        else{
            currentPlayer = 'A';
        }

    }

    public boolean distributeStones(int index){//returns whether move was valid or not
        if(index == 6 || index == 13){ //if a mancala was picked, not a valid move
            return false;
        }
        else if(currentBoard[index] == 0){ //if empty pit was picked, not a valid move
            return false;
        }
        else if(currentPlayer == 'A' && index >= 7){//if other player's pit was picked, not a valid move
            return false;
        }
        else if(currentPlayer == 'B' && index <= 5){//if other player's pit was picked, not a valid move
            return false;
        }
        for(int i : currentBoard){//update previousBoard
            previousBoard[i] = currentBoard[i];
        }
        moveCounter++;
        int currentPit = index; int numStones = currentBoard[index];
        while(numStones > 0){
            currentPit++;
            currentPit%=14;
            currentBoard[currentPit]++;
            numStones--;
        }

        checkOppositePit(currentPit);
        previousPlayer = currentPlayer;
        if(!isYourMancala(currentPit)){
            switchPlayer();
        }
        endGame();

        //notify listeners
        ChangeEvent event = new ChangeEvent(this);
        for(ChangeListener listener : listeners){
            listener.stateChanged(event);
        }
        return true;

    }

    public boolean isYourMancala(int index){
        if(currentPlayer == 'A' && index == 6){
            return true;
        }
        else if(currentPlayer == 'B' && index == 13){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkOppositePit(int currentPit){
        if(currentPit == 6 || currentPit == 13){
            return false;
        }
        else if(currentBoard[currentPit] != 0){
            return false;
        }
        else{
            if(currentPlayer == 'A' && currentPit < 6){
                if(currentBoard[currentPit + 7] != 0){
                    int addStones = currentBoard[currentPit + 7] + 1;
                    currentBoard[currentPit + 7] = 0;
                    currentBoard[currentPit] = 0;
                    currentBoard[6] += addStones;
                    return true;

                }
            }
            else if(currentPlayer == 'B' && currentPit >= 7){
                if(currentBoard[currentPit%7] != 0){
                    int addStones = currentBoard[currentPit%7] + 1;
                    currentBoard[currentPit%7] = 0;
                    currentBoard[currentPit] = 0;
                    currentBoard[13] += addStones;
                    return true;

                }
            }
            return false;
        }
    }
    
    public boolean isGameOver() {
    	for(int i = 0; i < 6; i++) {
    		if(currentBoard[i] != 0 ) {
    			return false;
    		}
    	}
    	for(int a = 7; a < 13; a++) {
    	if(currentBoard[a] != 0 ) {
			return false;
			}
    	}
    	return true;
    }
    

    public void endGame(){//to update endState if game has ended
        boolean isEmptyA = true; boolean isEmptyB = true; int addA = 0; int addB = 0;
        for(int i = 0; i < 6; i++){
            if(currentBoard[i] != 0){
                isEmptyA = false;
                addA += currentBoard[i];
            }
            if(currentBoard[i+7] != 0){
                isEmptyB = false;
                addB += currentBoard[i+7];
            }
        }

        if(isEmptyA || isEmptyB){
            currentBoard[6] += addA;
            currentBoard[13] += addB;
            for(int i = 0; i < 6; i++){
                currentBoard[i] = 0;
                currentBoard[i+7] = 0;
            }
            if(currentBoard[6] > currentBoard[13]){
                endState = 'A';
            }
            else if(currentBoard[6] < currentBoard[13]){
                endState = 'B';
            }
            else{
                endState = 'C'; //tie game
            }
        }

    }
    
    public char getEndState() {
    	return endState;
    }

}
