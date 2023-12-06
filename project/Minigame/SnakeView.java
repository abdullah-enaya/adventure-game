package Minigame;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import views.AdventureGameView;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Class SnakeView. Handles all the necessary tasks to run the minigame Snake.
 */
public class SnakeView {

    private AdventureGameView view;
    private Stage primaryStage;
    private boolean hasWon;
    private Timeline timeline;
    private static final int WIDTH = 800, HEIGHT = 800; // size of view
    private static final int ROWS = 20, COLS = 20; // num of rows = cols = 20
    private static final int SQUARE_SIZE = WIDTH / ROWS; // size of each square (for gui)
    private static final String FOOD_IMAGE = "Minigame/Resources/img.gif"; // image of the food object

    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody;
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;

    private final int goal;

    /**
     * SnakeView Constructor
     *
     * Initializes attributes
     *
     * @param view the view of the adventure game
     * @param goal the needed score to win the minigame
     */
    public SnakeView(AdventureGameView view, int goal) {
        this.view = view;
        this.goal = goal;
        this.snakeBody = new ArrayList<>();

        primaryStage = new Stage(); //dialogue box
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.initOwner(this.view.stage);

        primaryStage.setTitle("Snake");
        Group root = new Group();
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        gc = canvas.getGraphicsContext2D();

        scene.setOnKeyPressed(event -> {
            KeyCode code = event.getCode();
            if (code == KeyCode.RIGHT || code == KeyCode.D) {
                if (currentDirection != LEFT) {
                    currentDirection = RIGHT;
                }
            } else if (code == KeyCode.LEFT || code == KeyCode.A) {
                if (currentDirection != RIGHT) {
                    currentDirection = LEFT;
                }
            } else if (code == KeyCode.UP || code == KeyCode.W) {
                if (currentDirection != DOWN) {
                    currentDirection = UP;
                }
            } else if (code == KeyCode.DOWN || code == KeyCode.S) {
                if (currentDirection != UP) {
                    currentDirection = DOWN;
                }
            }
        });

        primaryStage.show();
        PauseTransition buffer = new PauseTransition(Duration.seconds(4));
        buffer.setOnFinished(actionEvent -> {
            this.startGame();
        });
        buffer.play();
    }

    /**
     * Starts the Snake minigame.
     */
    public void startGame() {
        this.hasWon = false;

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(3, ROWS / 2));
        }

        snakeHead = snakeBody.get(0);
        generateFood();

        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> {
            run(gc);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**
     * Update the UI given a specific move.
     * @param gc the graphic context that is being updated
     */
    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            timeline.stop();

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(actionEvent -> primaryStage.close());
            pause.play();

            view.updateScene("YOU LOST.");
            pause.play();
            return;
        }

        if (this.score == this.goal) {
            gc.setFill(Color.GREEN);
            gc.setFont(new Font("Arial", 70));
            gc.fillText("You Win!", WIDTH / 3.5, HEIGHT / 2);
            timeline.stop();

            this.hasWon = true;
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(actionEvent ->  {
                primaryStage.close();
                this.view.minigameWin();
            });
            pause.play();
            return;
        }

        initGrid(gc);
        drawFood(gc);
        drawSnake(gc);
        drawScore();

        for (int i = snakeBody.size() - 1; i >= 1; i--) {
            snakeBody.get(i).x = snakeBody.get(i - 1).x;
            snakeBody.get(i).y = snakeBody.get(i - 1).y;
        }

        switch (currentDirection) {
            case RIGHT:
                moveRight();
                break;
            case LEFT:
                moveLeft();
                break;
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
        }

        gameOver();
        eatFood();
    }

    /**
     * Initialize the background grid.
     * @param gc the graphics context to be updated
     */
    private void initGrid(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("FFB6C1")); // PINK!!!
                } else {
                    gc.setFill(Color.web("cfd7fd"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

    /**
     * Generate the food on the grid.
     */
    private void generateFood() {
        while (true) {
            boolean generateAgain = false;

            foodX = (int) (Math.random() * ROWS);
            foodY = (int) (Math.random() * COLS);

            for (Point snake : snakeBody) {
                if (snake.getX() == foodX && snake.getY() == foodY) {
                    generateAgain = true;
                    break;
                }
            }

            if (generateAgain) {
                continue;
            }

            foodImage = new Image(FOOD_IMAGE, 70, 50, false, false);
            break;
        }
    }

    /**
     * Draw the food on the grid.
     * @param gc the graphics context to be updated
     */
    private void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    /**
     * Draw the snake on the grid.
     * @param gc the graphics context to be updated
     */
    private void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("673147"));
        gc.fillRoundRect
                (snakeHead.getX() * SQUARE_SIZE,
                        snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                        SQUARE_SIZE - 1, 35, 35);

        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect
                    (snakeBody.get(i).getX() * SQUARE_SIZE,
                            snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }

    /**
     * Update the x coordinate by increasing it by 1.
     */
    private void moveRight() {
        snakeHead.x++;
    }

    /**
     * Update the x coordinate by decreasing it by 1.
     */
    private void moveLeft() {
        snakeHead.x--;
    }

    /**
     * Update the y coordinate by decreasing it by 1.
     */
    private void moveUp() {
        snakeHead.y--;
    }

    /**
     * Update the y coordinate by decreasing it by 1.
     */
    private void moveDown() {
        snakeHead.y++;
    }

    /**
     * Sets the attribute gameOver to true iff
     * the snake goes out of the grid, or
     * the snake destroys itself.
     */
    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 ||
                snakeHead.x * SQUARE_SIZE >= WIDTH ||
                snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() &&
                    snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    /**
     * Increase the body of the snake, consume the food and
     * generate new food.
     */
    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 1;
        }
    }

    /**
     * Draw the current score on the grid.
     */
    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 35));
        gc.fillText("Score: " + score, 10, 35);
    }
}