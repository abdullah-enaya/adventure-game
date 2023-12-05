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


public class Snake {

    private AdventureGameView view;
    private Stage primaryStage;
    private boolean hasWon;
    private Timeline timeline;
    private static final int WIDTH = 800, HEIGHT = 800; // size of view
    private static final int ROWS = 20, COLS = 20; // num of rows = cols = 20
    private static final int SQUARE_SIZE = WIDTH / ROWS; // size of each square (for gui)
    private static final String FOOD_IMAGE = "Minigame/Resources/img.png"; // image of the food object

    private static final int RIGHT = 0, LEFT = 1, UP = 2, DOWN = 3;

    private GraphicsContext gc;
    private List<Point> snakeBody = new ArrayList();
    private Point snakeHead;
    private Image foodImage;
    private int foodX;
    private int foodY;
    private boolean gameOver;
    private int currentDirection;
    private int score = 0;

    private int goal;

    public Snake(AdventureGameView view, int goal) {
        this.view = view;
        this.goal = goal;

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
        this.startGame();
    }

    public void startGame() {
        this.hasWon = false;

        for (int i = 0; i < 3; i++) {
            snakeBody.add(new Point(3, ROWS / 2));
        }

        snakeHead = snakeBody.get(0);
        generateFood();

        timeline = new Timeline(new KeyFrame(Duration.millis(130), e -> {
            run(gc);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void run(GraphicsContext gc) {
        if (gameOver) {
            gc.setFill(Color.RED);
            gc.setFont(new Font("Arial", 70));
            gc.fillText("Game Over", WIDTH / 3.5, HEIGHT / 2);
            timeline.stop();

            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(actionEvent -> primaryStage.close());
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

        initGUI(gc);
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

    private void initGUI(GraphicsContext gc) {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if ((i + j) % 2 == 0) {
                    gc.setFill(Color.web("FFB6C1")); // PINK!!!
                } else {
                    gc.setFill(Color.web("E0BFB8"));
                }
                gc.fillRect(i * SQUARE_SIZE, j * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
            }
        }
    }

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

            foodImage = new Image(FOOD_IMAGE);
            break;
        }
    }

    private void drawFood(GraphicsContext gc) {
        gc.drawImage(foodImage, foodX * SQUARE_SIZE, foodY * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
    }

    private void drawSnake(GraphicsContext gc) {
        gc.setFill(Color.web("673147"));
        gc.fillRoundRect
                (snakeHead.getX() * SQUARE_SIZE, snakeHead.getY() * SQUARE_SIZE, SQUARE_SIZE - 1, SQUARE_SIZE - 1, 35, 35);

        for (int i = 1; i < snakeBody.size(); i++) {
            gc.fillRoundRect
                    (snakeBody.get(i).getX() * SQUARE_SIZE, snakeBody.get(i).getY() * SQUARE_SIZE, SQUARE_SIZE - 1,
                    SQUARE_SIZE - 1, 20, 20);
        }
    }

    private void moveRight() {
        snakeHead.x++;
    }

    private void moveLeft() {
        snakeHead.x--;
    }

    private void moveUp() {
        snakeHead.y--;
    }

    private void moveDown() {
        snakeHead.y++;
    }

    public void gameOver() {
        if (snakeHead.x < 0 || snakeHead.y < 0 || snakeHead.x * SQUARE_SIZE >= WIDTH || snakeHead.y * SQUARE_SIZE >= HEIGHT) {
            gameOver = true;
        }

        //destroy itself
        for (int i = 1; i < snakeBody.size(); i++) {
            if (snakeHead.x == snakeBody.get(i).getX() && snakeHead.getY() == snakeBody.get(i).getY()) {
                gameOver = true;
                break;
            }
        }
    }

    private void eatFood() {
        if (snakeHead.getX() == foodX && snakeHead.getY() == foodY) {
            snakeBody.add(new Point(-1, -1));
            generateFood();
            score += 1;
        }
    }

    private void drawScore() {
        gc.setFill(Color.WHITE);
        gc.setFont(new Font("Arial", 35));
        gc.fillText("Score: " + score, 10, 35);
    }

    public boolean hasWon() {
        return this.hasWon;
    }
}