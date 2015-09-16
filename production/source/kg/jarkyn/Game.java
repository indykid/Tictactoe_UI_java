package kg.jarkyn;

public class Game {
    public Board getBoard() {
        return board;
    }

    public static void main(String[] args) {
        Game game = new Game(new Board(), new Player("x"), new Player("o"), new Ui(new Cli(System.out, System.in)));
        game.play();
    }

    private Board board;
    private final Player playerX;
    private final Player playerO;
    private final Ui ui;
    private Player currentPlayer;

    public Game(Board board, Player playerX, Player playerO, Ui ui) {
        this.board = board;
        this.playerX = playerX;
        this.playerO = playerO;
        this.ui = ui;
        this.currentPlayer = playerX;
    }

    public boolean isOver() {
        return isDrawn() || isWon();
    }

    public void addMove(int position, String mark) {
        this.board = board.addMove(position, mark);
    }

    public void playTurn() {
        ui.displayBoard(board);
        addMove(ui.getMove(currentPlayer.mark), currentPlayer.mark);
        swapPlayers();
    }

    public void play() {
        ui.greet();
        while (!isOver()) {
            playTurn();
        }
    }

    private boolean isWon() {
        return board.isWon();
    }

    private boolean isDrawn() {
        return board.isFull() && !isWon();
    }

    private void swapPlayers() {
        currentPlayer = currentPlayer == playerX ? playerO : playerX;
    }
}