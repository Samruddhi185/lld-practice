import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

enum PieceType {
    X,
    O
}

class Piece {
    private PieceType pieceType;
    
    Piece(PieceType pieceType) {
        this.pieceType = pieceType;
    }
    
    public PieceType getPieceType() {
        return pieceType;
    }
}

class XPiece extends Piece {
    XPiece() {
        super(PieceType.X);
    }
}

class OPiece extends Piece {
    OPiece() {
        super(PieceType.O);
    }
}

class Board {
    public Piece[][] board;
    public int size;
    
    Board(int size) {
        this.size = size;
        this.board = new Piece[size][size];
    }
    
    public Boolean isValidMove(int inputRow, int inputColumn) {
        System.out.println(inputRow + " " + inputColumn);
        if(inputRow < 0 || inputRow >= size || inputColumn < 0 || inputColumn >= size || board[inputRow][inputColumn] != null) {
            System.out.println(inputRow < 0);
            return false;
        }
        return true;
    }
    
    public void addEntry(int inputRow, int inputColumn, Piece piece) {
        board[inputRow][inputColumn] = piece;
    }
    
    public void printBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != null) {
                   System.out.print(board[i][j].getPieceType().name() + "   ");
                } else {
                    System.out.print("    ");
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }
}

class Player {
    private String name;
    private Piece piece;
    
    Player(String name, Piece piece) {
        this.name = name;
        this.piece = piece;
    }
    
    public Piece getPlayerPiece() {
        return piece;
    }
    
    public String getName() {
        return name;
    }
}

class TicTacToe {
    public Board gameBoard;
    public Queue<Player> players;
    public Boolean gameOver;
    public int freeSpots;
    
    TicTacToe(int size) {
        gameBoard = new Board(size);
        players = new LinkedList<>();
        gameOver = false;
        freeSpots = size * size;
        addPlayers();
    }
    
    public void addPlayers() {
        Piece pieceX = new XPiece();
        Player player1 = new Player("Player1", pieceX);
        players.add(player1);
        
        Piece pieceO = new OPiece();
        Player player2 = new Player("Player2", pieceO);
        players.add(player2);
    }
    
    public String startGame() {
        while(!gameOver) {
            if(freeSpots == 0) {
                return "It's a tie.";
            }
            Player currPlayer = players.remove();
            System.out.println("Player:" + currPlayer.getName());
            int inputRow = -1;
            int inputColumn = -1;
            
            System.out.println("Enter valid row,column: ");
                Scanner inputScanner = new Scanner(System.in);
                String s = inputScanner.nextLine();
                String[] values = s.split(",");
                inputRow = Integer.valueOf(values[0]);
                inputColumn = Integer.valueOf(values[1]);
            
            while(!gameBoard.isValidMove(inputRow, inputColumn)) {
                System.out.println("Enter valid row,column: ");
               inputScanner = new Scanner(System.in);
                s = inputScanner.nextLine();
                values = s.split(",");
                inputRow = Integer.valueOf(values[0]);
                inputColumn = Integer.valueOf(values[1]);
            }
            
            gameBoard.addEntry(inputRow, inputColumn, currPlayer.getPlayerPiece());
            gameBoard.printBoard();
            freeSpots--;
            gameOver = checkWin(inputRow, inputColumn, currPlayer.getPlayerPiece().getPieceType());
            if(gameOver){
                return currPlayer.getName() + " is a winner";
            }
            players.add(currPlayer);
        }
        return "";
    }
    
    public boolean checkWin(int row, int column, PieceType pieceType) {
        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antiDiagonalMatch = true;

        for(int i=0;i<gameBoard.size;i++) {
            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].getPieceType() != pieceType) {
                rowMatch = false;
            }
        }
        for(int i=0;i<gameBoard.size;i++) {
            if(gameBoard.board[i][column] == null || gameBoard.board[i][column].getPieceType() != pieceType) {
                columnMatch = false;
            }
        }
        for(int i=0, j=0; i<gameBoard.size;i++,j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].getPieceType() != pieceType) {
                diagonalMatch = false;
            }
        }
        for(int i=0, j=gameBoard.size-1; i<gameBoard.size;i++,j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].getPieceType() != pieceType) {
                antiDiagonalMatch = false;
            }
        }
        return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;
    }
}

public class Main {
    public static void main(String[] args) {
        TicTacToe game = new TicTacToe(3);
        System.out.println(game.startGame());
    }
}
