package views;

import AdventureModel.AdventureGame;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SelectModelView {
    /**
     * Class SelectModelView.
     * <p>
     * Selects the game model.
     */
    private Stage stage;

    private GridPane gridPane;
    private Label selectGameLabel;
    private Button selectGameButton;
    private ListView<String> GameList;

    private AdventureGame adventureGame;

    private AdventureGameView adventureGameView;

    private Button loadButton;

    /**
     * Adventure Select Model View Constructor
     *
     * Initializes attributes
     */
    public SelectModelView(Stage stage) {
        this.stage = stage;
        this.gridPane = new GridPane();
        initUI();
    }

    /**
     * Initialize the UI
     */
    public void initUI() {
        this.stage.setTitle("Select your game!");
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        selectGameButton = new Button("Select Game Model");
        selectGameButton.setId("Select");
        customizeButton(selectGameButton, 200, 50);
        makeButtonAccessible(selectGameButton, "Select Model", "This button selects a game model.", "This button selects the game model. Click it in order to load a game model that you wish to play.");

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 150, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);

        column3.setHgrow(Priority.SOMETIMES); //let some columns grow to take any extra space
        column1.setHgrow(Priority.SOMETIMES);

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints(550);
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow(Priority.SOMETIMES);
        row3.setVgrow(Priority.SOMETIMES);

        gridPane.getColumnConstraints().addAll(column1, column2, column1);
        gridPane.getRowConstraints().addAll(row1, row2, row1);

        final Stage dialog = new Stage(); //dialogue box
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(this.stage);

        selectGameLabel = new Label(String.format(""));

        GameList = new ListView<>(); //to hold all the file names

        VBox dialogVbox = new VBox(20);
        dialogVbox.setPadding(new Insets(20, 20, 20, 20));
        dialogVbox.setStyle("-fx-background-color: #121212;");
        selectGameLabel.setId("CurrentGame"); // DO NOT MODIFY ID
        GameList.setId("GameList");  // DO NOT MODIFY ID
        GameList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        getFiles(GameList); //get files for file selector

        //on selection, do something
        selectGameButton.setOnAction(e -> {
            try {
                selectGame(selectGameLabel, GameList);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        gridPane.add(GameList, 1, 1);
        //gridPane.add(selectGameButton, 1, 2);
        HBox hBox = new HBox();
        hBox.getChildren().addAll(selectGameButton, loadButton);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        gridPane.add(hBox, 1, 2);

        var scene = new Scene(gridPane, 1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(new AdventureGameView(stage));
        });
    }

    /**
     * customizeButton
     *
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", 16));
        inputButton.setStyle("-fx-background-color: #17871b; -fx-text-fill: white;");
    }

    /**
     * makeButtonAccessible
     *
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * Get Files to display in the on screen ListView
     * Populate the listView attribute with .ser file names
     * Files will be located in the Games/Saved directory
     *
     * @param listView the ListView containing all the .ser files in the Games/Saved directory.
     */
    private void getFiles(ListView<String> listView) {
        File folder = new File("Games");
        File[] files = folder.listFiles();
        ObservableList<String> filesStrings = FXCollections.observableArrayList();

        for (File file : files) {
            String fileName = file.getName();
            if (!fileName.equals("Saved") && !fileName.equals(".DS_Store")) {
                filesStrings.add(fileName);
            }
        }
        listView.setItems(filesStrings);
    }

    /**
     * Select the Game
     * Try to load a game from the Games/Saved
     * If successful, stop any articulation and put the name of the loaded file in the selectGameLabel.
     * If unsuccessful, stop any articulation and start an entirely new game from scratch.
     * In this case, change the selectGameLabel to indicate a new game has been loaded.
     *
     * @param selectGameLabel the label to use to print errors and or successes to the user.
     * @param GameList the ListView to populate
     */
    private void selectGame(Label selectGameLabel, ListView<String> GameList) throws IOException {
        //saved games will be in the Games/Saved folder!
        String selectedGame = GameList.getSelectionModel().getSelectedItem();
        try {
            AdventureGame adventureGame = this.selectModel(selectedGame);
            AdventureGameView view = new AdventureGameView(adventureGame, stage);
            this.adventureGame = adventureGame;
            this.adventureGameView = view;
        } catch (ClassNotFoundException | IOException e) {
            selectGameLabel.setText("Error!");
        }
    }

    /**
     * Select the Game Model
     *
     * @param GameModel game model to load
     * @return new AdventureGame
     */
    public AdventureGame selectModel(String GameModel) throws IOException, ClassNotFoundException {
        if (GameModel != null) {
            return new AdventureGame(GameModel);
        } else {
            throw new FileNotFoundException();
        }
    }

    public AdventureGame getAdventureGame() {
        return adventureGame;
    }

    /**
     *
     * @return the current Adventure Game View
     */
    public AdventureGameView getAdventureGameView() {
        return adventureGameView;
    }

}

