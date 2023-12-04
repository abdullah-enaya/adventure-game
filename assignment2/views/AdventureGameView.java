package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.character.Character;
import AdventureModel.character.CharacterFactory;
import SpeechToText.SpeechToText;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent; //you will need these!
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import javafx.scene.AccessibleRole;

import java.io.File;
import java.util.ArrayList;
import java.io.*;


import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

import SpeechToText.*;

/**
 * Class AdventureGameView.
 *
 * This is the Class that will visualize the model.
 *
 */
public class AdventureGameView {

    AdventureGame model; //model of the game
    Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton, deleteButton; //buttons
    Boolean helpToggle = false; //is help on display?

    Timeline timeline;
    GridPane gridPane; //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input
    Label levelLabel, xpLabel;
    ProgressBar xpBar;
    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing

    SPTContext sptContext;



    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) throws FileNotFoundException {
        this.model = model;
        this.stage = stage;
        this.sptContext = new SPTContext();
        intiUI();
    }

    /**
     * Adventure Game View Bare Constructor
     * __________________________
     * Initializes only stage, model still needs to be set and intiUI called.
     */
    public AdventureGameView(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initialize the UI
     */
    public void intiUI() throws FileNotFoundException {

        // setting up the stage
        this.stage.setTitle("Group 74's Adventure Game"); //Replace <YOUR UTORID> with your UtorID
        this.gridPane = new GridPane();

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf("#000000"),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1, row1 );


        if (this.model.getPlayer().character == null){
            initCharacterUI();
            return;
        }

        // Buttons
        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        deleteButton = new Button("Delete");
        deleteButton.setId("Delete");
        customizeButton(deleteButton, 100, 50);
        makeButtonAccessible(deleteButton, "Delete Button", "This button deletes a game from a file.", "This button deletes the game from a file. Click it in order to delete a game that you saved at a prior date.");
        addDeleteEvent();

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(saveButton, loadButton, deleteButton, helpButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: white;");
        objLabel.setFont(new Font("Arial", 16));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: white;");
        invLabel.setFont(new Font("Arial", 16));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        // Level and XP bar
        levelLabel = new Label( "You are at Level (" + this.model.player.getLevel().getLevel() + ")    |    XP: ");
        levelLabel.setStyle("-fx-text-fill: white;");
        levelLabel.setFont(new Font("Arial", 16));

        xpBar = new ProgressBar();
        xpBar.setMaxWidth(Double.MAX_VALUE);

        xpLabel = new Label();
        xpLabel.setStyle("-fx-text-fill: white;");
        xpLabel.setFont(new Font("Arial", 16));

        HBox levelView = new HBox();
        levelView.setStyle("-fx-background-color: #000000;");
        levelView.setPadding(new Insets(20, 20, 20, 20));
        levelView.getChildren().addAll(levelLabel, xpBar, xpLabel);
        HBox.setHgrow(xpBar, Priority.ALWAYS);
        levelView.setSpacing(10);
        gridPane.add( levelView, 0, 2, 3, 1 );

        updateScene("You have selected: " + this.model.player.character.title); //method displays an image and whatever text is supplied
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(actionEvent -> updateScene(null));
        pause.play();
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 3, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        this.stage.setScene(scene);
        scene.setFill(Color.BLACK);
        this.stage.setResizable(false);

        // set-up Speech-To-Text (strategy pattern)
        this.sptContext.setStrategy(new BasicGameStrategy(this));
        this.sptContext.executeStrategy();
        this.stage.show();
    }

    /**
     *  For first launch of the game, initialize UI for character selection.
     */
    private void initCharacterUI() throws FileNotFoundException {

        VBox dwarf = new VBox();

        Image imageDwarf = new Image (new FileInputStream("Assets" + File.separator + "characterImages" + File.separator + "Dwarf.png"));

        ImageView imageViewDwarf = new ImageView(imageDwarf);
        imageViewDwarf.setPreserveRatio(true);
        imageViewDwarf.setFitWidth(190);

        Label dwarfLabel = new Label("Dwarf");
        dwarfLabel.setAlignment(Pos.TOP_CENTER);
        dwarfLabel.setStyle("-fx-text-fill: white;");
        dwarfLabel.setFont(new Font("Arial", 16));

        Label descriptionDwarf = new Label("The Dwarf is a tank type class. Born from ironforge, " +
                "the dwarf specializes in taking damage. He has very high hp with low attack.");
        descriptionDwarf.setWrapText(true);
        descriptionDwarf.setAlignment(Pos.TOP_CENTER);
        descriptionDwarf.setStyle("-fx-text-fill: white;");
        descriptionDwarf.setFont(new Font("Arial", 16));

        dwarf.setSpacing(20);
        dwarf.getChildren().addAll(imageViewDwarf, dwarfLabel, descriptionDwarf);
        dwarf.setAlignment(Pos.TOP_CENTER);

        VBox mage = new VBox();

        Image imageMage = new Image (new FileInputStream( "Assets" + File.separator + "characterImages" + File.separator + "Mage.png"));
        ImageView imageViewMage = new ImageView(imageMage);
        imageViewMage.setPreserveRatio(true);
        imageViewMage.setFitWidth(200);

        Label mageLabel = new Label("Mage");
        mageLabel.setAlignment(Pos.TOP_CENTER);
        mageLabel.setStyle("-fx-text-fill: white;");
        mageLabel.setFont(new Font("Arial", 16));

        Label descriptionMage = new Label("The Mage is an elf with the innate ability to control the elements. Through his practise and elvin blood, the mage specializes in medium attack damage as well as medium health.");
        descriptionMage.setWrapText(true);
        descriptionMage.setAlignment(Pos.TOP_CENTER);
        descriptionMage.setStyle("-fx-text-fill: white;");
        descriptionMage.setFont(new Font("Arial", 16));

        mage.setSpacing(20);
        mage.getChildren().addAll(imageViewMage, mageLabel, descriptionMage);
        mage.setAlignment(Pos.TOP_CENTER);

        VBox damage = new VBox();

        Image imageDamage = new Image (new FileInputStream("Assets" + File.separator + "characterImages" + File.separator + "Damage.png"));
        ImageView imageViewDamage = new ImageView(imageDamage);
        imageViewDamage.setPreserveRatio(true);
        imageViewDamage.setFitWidth(200);

        Label damageLabel = new Label("Damage");
        damageLabel.setAlignment(Pos.TOP_CENTER);
        damageLabel.setStyle("-fx-text-fill: white;");
        damageLabel.setFont(new Font("Arial", 16));

        Label descriptionDamage = new Label("Natural born killers, the damage is a human assassin raised from a young age to kill. The damage class specializes in high damage but has low hp.");
        descriptionDamage.setWrapText(true);
        descriptionDamage.setAlignment(Pos.TOP_CENTER);
        descriptionDamage.setStyle("-fx-text-fill: white;");
        descriptionDamage.setFont(new Font("Arial", 16));

        damage.setSpacing(20);
        damage.getChildren().addAll(imageViewDamage, damageLabel, descriptionDamage);
        damage.setAlignment(Pos.TOP_CENTER);


        HBox characters = new HBox();
        characters.getChildren().addAll(mage, damage, dwarf);
        characters.setAlignment(Pos.TOP_CENTER);
        characters.setSpacing(100);
        mage.setMaxWidth(100);
        HBox.setHgrow(mage, Priority.NEVER);
        HBox.setHgrow(dwarf, Priority.ALWAYS);
        HBox.setHgrow(damage, Priority.ALWAYS);

        gridPane.add(characters, 0, 0, 3, 2);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", 16));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent2(); //attach an event to this input field

        Label commandLabel = new Label("Type the character you would like to play!");
        commandLabel.setStyle("-fx-text-fill: white;");
        commandLabel.setFont(new Font("Arial", 16));

        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: #000000;");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.TOP_CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        this.stage.setScene(scene);
        scene.setFill(Color.BLACK);
        this.stage.setResizable(false);

        // set-up Speech-To-Text (strategy pattern)
        this.sptContext.setStrategy(new ChooseCharacterStrategy(this));
        this.sptContext.executeStrategy();
        this.stage.show();
    }


    /**
     * makeButtonAccessible
     * __________________________
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
     * customizeButton
     * __________________________
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
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute
     *
     * Your event handler should respond when users
     * hits the ENTER or TAB KEY. If the user hits
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus
     * of the scene onto any other node in the scene
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        this.inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                this.submitEvent(this.inputTextField.getText().strip());
                this.inputTextField.clear();
            } else if (keyEvent.getCode().equals(KeyCode.TAB)) {
                this.saveButton.requestFocus();
            }
        });
    }

    /**
     * second text handling event for the initialization of character prompt
     */
    private void addTextHandlingEvent2(){
        this.inputTextField.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    this.submitEvent2(this.inputTextField.getText().strip());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.inputTextField.clear();
            } else if (keyEvent.getCode().equals(KeyCode.TAB)) {
                this.saveButton.requestFocus();
            }
        });
    }

    /**
     * second submit event to handle the character creation prompt
     */
    public void submitEvent2(String text) throws IOException {
        text = text.strip().toUpperCase(); //get rid of white space
        stopArticulation(); //if speaking, stop is

        CharacterFactory characterFactory = new CharacterFactory();
        Character character = characterFactory.getCharacter(text);
        if (character != null) {
            this.model.getPlayer().character = character;
            intiUI();
        }
    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    public void submitEvent(String text) {

        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        }

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            this.updateScene(null);
            this.updateItems();
            this.inputTextField.setDisable(true);
            this.objectsInRoom.setDisable(true);
            this.objectsInInventory.setDisable(true);
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            pause.setOnFinished(event -> {
                this.inputTextField.setDisable(false);
                this.objectsInRoom.setDisable(false);
                this.objectsInInventory.setDisable(false);
                this.submitEvent("FORCED");
            });
            pause.play();
        }
    }

    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the 
     * current room.
     */
    private void showCommands() {
        this.roomDescLabel.setText(this.model.player.getCurrentRoom().getCommands());
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     *
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: #000000;");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        updateLevel();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) articulateRoomDescription();
    }

    /**
     * updateLevel
     * __________________________
     * Update the level and xp displayed on the screen.
     */
    public void updateLevel() {
        String levelText = "You are at Level (" + this.model.player.getLevel().getLevel() + ")    |    XP: ";
        String oldLevel = levelLabel.getText();
        levelLabel.setText(levelText);

        double xpRatio = (double) this.model.player.getLevel().getXP() / this.model.player.getLevel().getXPToNextLevel();
        xpBar.setProgress(xpRatio);

        xpLabel.setText("(" + this.model.player.getLevel().getXPString() + ")");
        xpLabel.setStyle("-fx-text-fill: white;");
        xpLabel.setFont(new Font("Arial", 16));

        if (!levelText.equals(oldLevel)) {
            String oldRoomLabel = this.roomDescLabel.getText();
            this.roomDescLabel.setText(this.model.player.getLevel().levelUpText());
            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(actionEvent -> {
                this.roomDescLabel.setText(oldRoomLabel);
            });
            pause.play();
        }
    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     *
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: white;");
        roomDescLabel.setFont(new Font("Arial", 16));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place 
     * it in the roomImageView 
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + File.separator + "room-images" + File.separator + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     * Update the items with buttons for items in room and in inventory.
     */
    public void updateItems() {
        this.objectsInInventory.getChildren().clear();
        this.objectsInRoom.getChildren().clear();

        //write some code here to add images of objects in a given room to the objectsInRoom Vbox
        for (AdventureObject object: this.model.player.getCurrentRoom().objectsInRoom) {
            Image image = new Image("file:" + this.model.getDirectoryName() + File.separator + "objectImages" + File.separator + object.getName() + ".jpg");
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            Button button = new Button(object.getName(), imageView);
            button.setContentDisplay(ContentDisplay.TOP);
            makeButtonAccessible(button, object.getName(), "Take " + object.getName() + ".", "Take " + object.getName() + ". Press this button to take the object into your inventory.");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
                this.submitEvent("TAKE " + object.getName());
            });
            this.objectsInRoom.getChildren().add(button);
            if (this.model.player.getLevel().getLevel() < object.getLevel()) {
                button.setOpacity(0.4);
            }
        }

        //write some code here to add images of objects in a player's inventory room to the objectsInInventory Vbox
        for (AdventureObject object: this.model.player.inventory) {
            Image image = new Image("file:" + this.model.getDirectoryName() + File.separator + "objectImages" + File.separator + object.getName() + ".jpg");
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(100);
            imageView.setPreserveRatio(true);

            Button button = new Button(object.getName(), imageView);
            button.setContentDisplay(ContentDisplay.TOP);
            makeButtonAccessible(button, object.getName(), "Drop " + object.getName() + ".", "Drop " + object.getName() + ". Press this button to drop the object from your inventory to the current room.");
            button.addEventHandler(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
                this.model.player.dropObject(object.getName());
                this.updateItems();
                this.updateScene("YOU HAVE DROPPED: " + object.getName());
            });
            this.objectsInInventory.getChildren().addAll(button);
        }

        //please use setAccessibleText to add "alt" descriptions to your images!
        //the path to the image of any is as follows:
        //this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";

        ScrollPane scO = new ScrollPane(objectsInRoom);
        scO.setPadding(new Insets(10));
        scO.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        scO.setFitToWidth(true);
        gridPane.add(scO,0,1);

        ScrollPane scI = new ScrollPane(objectsInInventory);
        scI.setFitToWidth(true);
        scI.setStyle("-fx-background: #000000; -fx-background-color:transparent;");
        gridPane.add(scI,2,1);

        updateLevel();
    }

    /**
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (!this.helpToggle) {
            Label instructions = new Label();
            instructions.setText(this.model.getInstructions());
            instructions.setTextFill(Color.WHITE);
            instructions.setWrapText(true);

            ObservableList<Node> children = gridPane.getChildren();
            ArrayList<Node> listchild = new ArrayList<>(children);
            for (Node node: listchild) {
                if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1) {
                    gridPane.getChildren().remove(node);
                }
            }
            this.gridPane.add(instructions, 1, 1);
            this.helpToggle = true;
        } else {
            ObservableList<Node> children = gridPane.getChildren();
            ArrayList<Node> listchild = new ArrayList<>(children);
            for (Node node: listchild) {
                if (GridPane.getRowIndex(node) == 1 && GridPane.getColumnIndex(node) == 1) {
                    gridPane.getChildren().remove(node);
                }
            }
            this.updateScene(null);
            this.helpToggle = false;
        }
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();

        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {

        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the
     * delete button.
     */
    public void addDeleteEvent() {
        deleteButton.setOnAction(e -> {
            gridPane.requestFocus();
            DeleteView deleteView = new DeleteView(this);
        });
    }

    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3";
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    public AdventureGame getModel() {
        return model;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Stage getStage() {
        return stage;
    }
}
