import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

public class SnakeGame {

    private static final int BOARD_SIZE = 10;
    private static final char EMPTY_CELL = '.';
    private static final char SNAKE_BODY = 'o';
    private static final char FOOD = '*';

    private char[][] board;
    private Queue<Point> snake;
    private Point food;

    public SnakeGame() {
        board = new char[BOARD_SIZE][BOARD_SIZE];
        initializeBoard();
        initializeSnake();
        placeFood();
    }

    private void initializeBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    private void initializeSnake() {
        snake = new LinkedList<>();
        Point head = new Point(0, 0);
        snake.add(head);
        board[head.x][head.y] = SNAKE_BODY;
    }

    private void placeFood() {
        Random random = new Random();
        int x, y;
        do {
            x = random.nextInt(BOARD_SIZE);
            y = random.nextInt(BOARD_SIZE);
        } while (board[x][y] != EMPTY_CELL);

        food = new Point(x, y);
        board[x][y] = FOOD;
    }

    private void printBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private void moveSnake(String direction) {
        Point head = snake.peek();
        Point newHead = new Point(head);

        switch (direction.toLowerCase()) {
            case "up":
                newHead.x--;
                break;
            case "down":
                newHead.x++;
                break;
            case "left":
                newHead.y--;
                break;
            case "right":
                newHead.y++;
                break;
        }

        
        if (newHead.x < 0 || newHead.x >= BOARD_SIZE || newHead.y < 0 || newHead.y >= BOARD_SIZE) {
            System.out.println("Game Over! Snake hit the wall.");
            System.exit(0);
        }

        
        if (board[newHead.x][newHead.y] == SNAKE_BODY) {
            System.out.println("Game Over! Snake collided with itself.");
            System.exit(0);
        }

        
        if (newHead.equals(food)) {
            snake.add(newHead);
            board[newHead.x][newHead.y] = SNAKE_BODY;
            placeFood();
        } else {
            
            Point tail = snake.poll();
            board[tail.x][tail.y] = EMPTY_CELL;

            snake.add(newHead);
            board[newHead.x][newHead.y] = SNAKE_BODY;
        }
    }

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            snakeGame.printBoard();
            System.out.print("Enter direction (up/down/left/right): ");
            String direction = scanner.nextLine();

            snakeGame.moveSnake(direction);
        }
    }

    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        
        public Point(Point point) {
            this.x = point.x;
            this.y = point.y;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;
            Point point = (Point) obj;
            return x == point.x && y == point.y;
        }
    }
}
