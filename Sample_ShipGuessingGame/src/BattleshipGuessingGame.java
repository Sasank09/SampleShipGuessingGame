import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class BattleshipGuessingGame {
    static final int BOARDSIZE = 10;
    static final char BOARDCELL ='.';
    static final char HIT = 'X';
    static final char MISS = 'O';
    static final char SHIPVALUE = 'x';

    static int xp1BowCordinate, yp1BowCordinate, xp1SternCordinate, yp1SternCordinate;
    static int wrongGuess =5;
    static int correctGuess =4;
    static int totalCount =8;
    static char[][] p1Board = new char[BOARDSIZE][BOARDSIZE];
    static char[][] p2Board = new char[BOARDSIZE][BOARDSIZE];

    private static int getIntegerInput(){
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if(input>0 && input<BOARDSIZE) {
        }
        else{
            System.out.println("Invalid Input, please try again");
            input =getIntegerInput();
        }
        return input;
    }
    private static char[][] createInitGameBoard() {
        char[][] initGameBoard = new char[BOARDSIZE][BOARDSIZE];
        for (int i = 0; i < initGameBoard.length; i++) {
            for (int j = 0; j < initGameBoard.length; j++) {
                initGameBoard[i][j] = '.';
            }
        }
        printBoard(initGameBoard, false);
        return initGameBoard;
    }

    private static void generateShipCordsInBoard(char[][] initGameBoard) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the x coordinate of bow: ");
        xp1BowCordinate = getIntegerInput();
        System.out.println("Enter the y coordinate of bow: ");
        yp1BowCordinate = getIntegerInput();
        System.out.println("Enter the x coordinate of stern: ");
        xp1SternCordinate = getIntegerInput();
        System.out.println("Enter the y coordinate of stern: ");
        yp1SternCordinate = getIntegerInput();
        boolean isShipLocationValid = isValidShip(xp1BowCordinate, yp1BowCordinate, xp1SternCordinate, yp1SternCordinate);
        if(!isShipLocationValid) {
            System.out.println("Invalid Coordinates. Please make sure same values for bow & stern either for x or y and size of ship 4");
            generateShipCordsInBoard(initGameBoard);
        }
        else {
            p1Board = generateUpdatedBoard(initGameBoard);
        }
    }

    private static char[][] generateUpdatedBoard(char[][] initGameBoard) {
        int x1 = xp1BowCordinate;
        int y1 = yp1BowCordinate;
        int x2 = xp1SternCordinate;
        int y2 = yp1SternCordinate;
        if(x1==x2 && y1<y2) {
            for(int temp = y1; temp<=y2; temp++) {
                initGameBoard[x1][temp] = SHIPVALUE;
            }
        }
        if(x1==x2 && y2<y1) {
            for(int temp = y2; temp<=y1; temp++) {
                initGameBoard[x1][temp] = SHIPVALUE;
            }
        }
        if(y1==y2 && x1<x2) {
            for(int temp = x1; temp<=x2; temp++) {
                initGameBoard[temp][y1] = SHIPVALUE;
            }
        }
        if(y1==y2 && x1>x2) {
            for(int temp = x2; temp<=x1; temp++) {
                initGameBoard[temp][y1] = SHIPVALUE;
            }
        }
        return initGameBoard;
    }

    private static boolean isValidShip(int x1, int y1, int x2, int y2) {
        if(x1==x2 && 3==Math.abs(y2-y1) || y1==y2 && 3==Math.abs(x2-x1)) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isValidGuess(int x, int y, char[][] board) {
        if(board[x][y]==SHIPVALUE) {
            p2Board[x][y] = HIT;
            totalCount--;
            correctGuess--;
            System.out.println("Correct, please find remaining coordinates!!");
            System.out.println("Remaining Cordinatess to find "+correctGuess);
            return true;
        }
        else {
            wrongGuess--;
            p2Board[x][y]=MISS;
            totalCount--;
            System.out.println("Wrong, please try again");
            System.out.println("Wrong Chances Remaining "+wrongGuess);
            System.out.println("Total Chances Remaining "+totalCount);
            return false;
        }
    }

    private static void printBoard(char[][] board, boolean isFinal) {
        if(isFinal){
            System.out.println("x|y0 1 2 3 4 5 6 7 8 9");
            for(int i=0; i< board.length; i++) {
                System.out.print(i+"| ");
                for(int j=0; j< board.length; j++) {
                    if(board[i][j]==HIT || board[i][j]==MISS) {
                        System.out.print(board[i][j]+" ");
                    }
                    else if(board[i][j]!=p1Board[i][j]) {
                        System.out.print(p1Board[i][j]+" ");
                    }
                    else{
                        System.out.print(p2Board[i][j]+" ");
                    }
                }
                System.out.println();
            }
        }
        else {
            System.out.println("x|y0 1 2 3 4 5 6 7 8 9");
            for(int i=0; i< board.length; i++) {
                System.out.print(i+"| ");
                for(int j=0; j< board.length; j++) {
                    System.out.print(board[i][j]+" ");
                }
                System.out.println();
            }
        }
    }

    public static void guessPosition(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Player 2 Enter the Guessing Coordinates for the Ship to be placed in 10*10 array with 0-9 values");
        System.out.println("Enter the x coordinate of your guess: ");
        int xp2BowCordinate = getIntegerInput();
        System.out.println("Enter the y coordinate of your guess: ");
        int yp2BowCordinate = getIntegerInput();
        boolean isGuessCorrect = isValidGuess(xp2BowCordinate,yp2BowCordinate,p1Board);
    }


    public static void main(String[] args) {
        try {
            char[][] initGameBoard = createInitGameBoard();
            Scanner sc = new Scanner(System.in);

            System.out.println("Player 1 Enter the Coordinates for the Ship to be placed in 10*10 array with 0-9 values & have 4 space");
            generateShipCordsInBoard(initGameBoard);
            printBoard(p1Board, false);
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            p2Board = createInitGameBoard();
            while (wrongGuess > 0) {
                if (totalCount > 0 && correctGuess > 0) {
                    guessPosition();
                    printBoard(p2Board,false);
                } else {
                    if(wrongGuess == 0 || totalCount == 0) {
                        System.out.println("You lost, Better luck next time");
                    } else if (correctGuess==0) {
                        System.out.println("Congratulation, you have won the game");
                    }
                    break;
                }

            }
            System.out.println("\nGame Over, Results are as below");
            printBoard(p2Board, true);
        }
        catch (Exception ex) {
            System.out.println(ex);
        }

    }
}
