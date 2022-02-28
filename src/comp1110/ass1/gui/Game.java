package comp1110.ass1.gui;

import comp1110.ass1.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class Game extends Application {

    private static final int SQUARE_SIZE = 80;
    private static final int MARGIN_X = 60;
    private static final int MARGIN_Y = 30;
    private static final int BOARD_WIDTH = 450;
    private static final int BOARD_HEIGHT = 450;
    private static final int BOARD_MARGIN = 25;
    private static final int BOARD_Y = MARGIN_Y;
    private static final int BOARD_X = MARGIN_X;
    private static final int PLAY_AREA_Y = BOARD_Y + BOARD_MARGIN;
    private static final int PLAY_AREA_X = BOARD_X + BOARD_MARGIN;
    private static final int GAME_WIDTH = BOARD_X + BOARD_WIDTH + MARGIN_X + 280;
    private static final int GAME_HEIGHT = BOARD_Y + BOARD_HEIGHT + MARGIN_Y + 100;

    private final Group root = new Group();
    private final Group rabbits = new Group();
    private final Group foxes = new Group();
    private final Group mushrooms = new Group();
    private final Group board = new Group();
    private final Group controls = new Group();
    private final Group selectionPieces = new Group();
    private final Group solutionText = new Group();

    private final Position[] highlightedPositions = new Position[4];

    private static final String URI_BASE = "assets/";

    Jumpin jumpin;

    private final Slider difficulty = new Slider();
    private final Text completionText = new Text("Well done!\nAll of the rabbits are safe.");
    private final Text instructionText = new Text("Click the \"Solution\" button to display solution.\n" +
            "Click a piece to see where it can move, and drag it to on one of the highlighted cells to move the piece there\n +" +
            "A piece can only be moved to one of the highlighted cells shown.");

    RabbitPiece[] rabbitPieces = new RabbitPiece[3];
    FoxPiece[] foxPieces = new FoxPiece[2];
    GUIPiece highlightedPiece = null;

    class GUIPiece extends ImageView {

        double homeX, homeY;
        double mouseX, mouseY;
        Position loc;

        GUIPiece(String name, Position loc) {
            this.loc = loc;
            Image image = new Image(Game.class.getResource(URI_BASE + name + ".png").toString());
            setImage(image);
            setFitHeight(SQUARE_SIZE - 4);
            setFitWidth(SQUARE_SIZE - 4);
            this.homeX = (loc.getX()) * SQUARE_SIZE + PLAY_AREA_X;
            this.homeY = (loc.getY()) * SQUARE_SIZE + PLAY_AREA_Y;
            setLayoutX(this.homeX);
            setLayoutY(this.homeY);
        }

        public void snapTo(Position loc) {
            this.setLayoutX((loc.getX()) * SQUARE_SIZE + PLAY_AREA_X);
            this.setLayoutY((loc.getY()) * SQUARE_SIZE + PLAY_AREA_Y);
        }
    }

    class RabbitPiece extends GUIPiece {

        State rabbitType;

        RabbitPiece(String name, Position loc) {
            super(getRabbitFromString(name), loc);
            this.rabbitType = State.fromChar(name.charAt(0));
            setOnMousePressed(event -> {
                resetSelectionCircles();
                Position[][] rabbitPositions = jumpin.getValidNextRabbitPositions();
                for (int i = 0; i < 3; i++) {
                    if (jumpin.getRabbits()[i].getType() == this.rabbitType) {
                        setSelectionCircles(rabbitPositions[i]);
                        break;
                    }
                }
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(getLayoutX() + diffX);
                this.setLayoutY(getLayoutY() + diffY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseReleased(event -> {
                Position resultingLoc = getSnapLocation(this.getLayoutX(), this.getLayoutY());
                if (resultingLoc.getX() != this.loc.getX() && resultingLoc.getY() != this.loc.getY()) {
                    snapTo(this.loc);
                }
                else {
                    Direction d;
                    if (resultingLoc.getX() < this.loc.getX())      d = Direction.WEST;
                    else if (resultingLoc.getX() > this.loc.getX()) d = Direction.EAST;
                    else if (resultingLoc.getY() > this.loc.getY()) d = Direction.SOUTH;
                    else if (resultingLoc.getY() < this.loc.getY()) d = Direction.NORTH;
                    else                                       d = null;
                    if (d == null) snapTo(this.loc);
                    else {
                        boolean isValid = false;
                        for (Position validPos : highlightedPositions) {
                            if (validPos != null) {
                                if (resultingLoc.isOnBoard() && validPos.equals(resultingLoc)) {
                                    snapTo(validPos);
                                    this.getCorrespondingRabbit().makeMove(d, jumpin);
                                    this.loc = this.getCorrespondingRabbit().getPosition();
                                    isValid = true;
                                }
                            }
                        }
                        if (!isValid) {
                            snapTo(this.loc);
                        }
                    }
                }
                if (jumpin.isSolution()) {
                    showCompletion();
                    jumpin.printBoardState();
                }
                resetSelectionCircles();
            });
        }

        public static String getRabbitFromString(String id) {
            return switch (id) {
                case "C" -> "cream-rabbit";
                case "B" -> "brown-rabbit";
                case "G" -> "gray-rabbit";
                default -> "";
            };
        }

        public Rabbit getCorrespondingRabbit() {
            for (Rabbit rabbit : jumpin.getRabbits()) {
                if (rabbit != null && rabbit.getType() == this.rabbitType) {
                    return rabbit;
                }
            }
            return null;
        }
    }

    class FoxPiece extends GUIPiece {

        Direction foxDirection;

        FoxPiece(Position loc, Direction dir) {
            super("fox", loc);
            this.foxDirection = dir;
            setFitHeight(2 * SQUARE_SIZE - 4);
            setRotate(getRotation());
            setLayoutX(getLayoutX() + getXOffset());
            setLayoutY(getLayoutY() + getYOffset());
            setOnMousePressed(event -> {
                resetSelectionCircles();
                Position[][] foxPositions = jumpin.getValidNextFoxPositions();
                for (int i = 0; i < 2; i++) {
                    if (jumpin.getFoxes()[i].getDirection() == this.foxDirection) {
                        setSelectionCircles(foxPositions[i]);
                        break;
                    }
                }
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseDragged(event -> {
                double diffX = event.getSceneX() - mouseX;
                double diffY = event.getSceneY() - mouseY;
                this.setLayoutX(this.getLayoutX() + diffX);
                this.setLayoutY(this.getLayoutY() + diffY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });
            setOnMouseReleased(event -> {
                Position resultingLoc = getSnapLocation(this.getLayoutX(), this.getLayoutY());
                Fox fox = getCorrespondingFox();
                if (!resultingLoc.equals(fox.getHeadPosition().applyDirection(fox.getDirection())) &&
                  !resultingLoc.equals(fox.getHeadPosition().applyDirection(fox.getDirection().getOpposite()))) {
                    snapTo(this.loc);
                }
                else {
                    Direction d;
                    if (resultingLoc.getX() < this.loc.getX())      d = Direction.WEST;
                    else if (resultingLoc.getX() > this.loc.getX()) d = Direction.EAST;
                    else if (resultingLoc.getY() > this.loc.getY()) d = Direction.SOUTH;
                    else if (resultingLoc.getY() < this.loc.getY()) d = Direction.NORTH;
                    else                                            d = null;
                    if (d == null) snapTo(this.loc);
                    else {
                        if (resultingLoc.isOnBoard() && fox.canMove(d, jumpin)) {
                            snapTo(resultingLoc);
                            fox.makeMove(d, jumpin);
                            this.loc = fox.getHeadPosition();
                        }
                        else {
                            snapTo(this.loc);
                        }
                    }
                }
                resetSelectionCircles();
            });
        }

        Fox getCorrespondingFox() {
            for (Fox fox : jumpin.getFoxes()) {
                if (fox.getDirection() == this.foxDirection) {
                    return fox;
                }
            }
            return null;
        }

        double getXOffset() {
            return switch (this.foxDirection) {
                case NORTH, SOUTH -> 0.0;
                case EAST -> -SQUARE_SIZE / 2.0;
                case WEST -> SQUARE_SIZE / 2.0;
            };
        }

        double getYOffset() {
            return switch (this.foxDirection) {
                case NORTH -> 0.0;
                case SOUTH -> -SQUARE_SIZE;
                case EAST, WEST -> -SQUARE_SIZE/2.0;
            };
        }

        double getRotation() {
            return switch (this.foxDirection) {
                case NORTH -> 0.0;
                case EAST -> 90.0;
                case SOUTH -> 180.0;
                case WEST -> 270.0;
            };
        }

        @Override
        public void snapTo(Position loc) {
            super.snapTo(loc);
            setLayoutX(getLayoutX() + getXOffset());
            setLayoutY(getLayoutY() + getYOffset());
        }
    }

    static class SelectionPiece extends Circle {

        Position loc;

        SelectionPiece(Position loc) {
            super((SQUARE_SIZE - 4)/2.0);
            this.loc = loc;
            this.setFill(Color.RED);
            this.setOpacity(0.5);
            double x = (loc.getX()) * SQUARE_SIZE + PLAY_AREA_X + SQUARE_SIZE/2.0;
            double y = (loc.getY()) * SQUARE_SIZE + PLAY_AREA_Y + SQUARE_SIZE/2.0;
            setLayoutX(x);
            setLayoutY(y);
        }

    }

    public Position getSnapLocation(double x, double y) {
        int positionX = (int) Math.round((x - PLAY_AREA_X) / SQUARE_SIZE);
        int positionY = (int) Math.round((y - PLAY_AREA_Y) / SQUARE_SIZE);
        return new Position(positionX, positionY);
    }

    public void makeBoard() {
        int currentX = PLAY_AREA_X;
        int currentY = PLAY_AREA_Y;
        for (int col = 0; col < 5; col++) {
            for (int row = 0; row < 5; row++) {
                String imageToPutDown = URI_BASE;
                boolean correctCol = (col == 0 || col == 4);
                boolean correctRow = (row == 0 || row == 4);
                boolean middleHole = col == 2 && row == 2;
                if ((correctCol && correctRow) || middleHole) {
                    imageToPutDown += "rabbit-hole.png";
                }
                else {
                    imageToPutDown += "cell-normal.png";
                }
                Image cellImage = new Image(Game.class.getResource(imageToPutDown).toString());
                ImageView cell = new ImageView(cellImage);
                cell.setFitWidth(SQUARE_SIZE);
                cell.setFitHeight(SQUARE_SIZE);
                cell.setLayoutX(currentX);
                cell.setLayoutY(currentY);
                this.board.getChildren().add(cell);
                currentX += SQUARE_SIZE;
            }
            currentY += SQUARE_SIZE;
            currentX = PLAY_AREA_X;
        }
    }

    private void setSelectionCircles(Position[] positions) {
        for (int i = 0; i < positions.length; i++) {
            SelectionPiece selecPiece = new SelectionPiece(positions[i]);
            selectionPieces.getChildren().add(selecPiece);
            highlightedPositions[i] = positions[i];
        }
    }

    private void makeCompletion() {
        completionText.setFill(Color.BLACK);
        completionText.setCache(true);
        completionText.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 60));
        completionText.setLayoutX(0);
        completionText.setLayoutY(200);
        completionText.setTextAlignment(TextAlignment.CENTER);
        root.getChildren().add(completionText);
        hideCompletion();
    }

    private void showCompletion() {
        completionText.toFront();
        completionText.setFill(Color.BLACK);
    }

    private void hideCompletion() {
        completionText.toBack();
        completionText.setFill(Color.WHITE);
    }

    public void resetSelectionCircles() {
        this.selectionPieces.getChildren().clear();
        for (int i = 0; i < 4; i++) {
            highlightedPositions[i] = null;
        }
    }

    public void instantiatePieces() {
        int i = 0;
        for (Rabbit rabbit : jumpin.getRabbits()) {
            if (rabbit != null) {
                RabbitPiece rabbitPiece = new RabbitPiece(rabbit.getType().toString(), rabbit.getPosition());
                rabbitPieces[i] = rabbitPiece;
                this.rabbits.getChildren().add(rabbitPiece);
                i++;
            }
        }
        i = 0;
        for (Fox fox : jumpin.getFoxes()) {
            if (fox != null) {
                FoxPiece foxPiece = new FoxPiece(fox.getHeadPosition(), fox.getDirection());
                foxPieces[i] = foxPiece;
                this.foxes.getChildren().add(foxPiece);
                i++;
            }
        }
        for (Position mushroom : jumpin.getMushrooms()) {
            if (mushroom != null) {
                GUIPiece mushroomPiece = new GUIPiece("mushroom", mushroom);
                mushrooms.getChildren().add(mushroomPiece);
            }
        }
    }

    public void makeControls() {
        Button restart = new Button("Restart");
        restart.setLayoutX(BOARD_X + BOARD_MARGIN + 300);
        restart.setLayoutY(GAME_HEIGHT - 55);
        restart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                restart();
            }
        });
        Button newGame = new Button("New Game");
        newGame.setLayoutX(BOARD_X + BOARD_MARGIN + 200);
        newGame.setLayoutY(GAME_HEIGHT - 55);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                newGame();
            }
        });
        Button solution = new Button("Solution");
        solution.setLayoutX(BOARD_X + BOARD_MARGIN + 400);
        solution.setLayoutY(GAME_HEIGHT - 55);
        solution.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                makeSolution();
            }
        });
        Button help = new Button("Help");
        help.setLayoutX(BOARD_X + BOARD_MARGIN + 500);
        help.setLayoutY(GAME_HEIGHT - 55);
        help.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION,
                        instructionText.getText());
                alert.show();
            }
        });
        controls.getChildren().add(restart);
        controls.getChildren().add(newGame);
        controls.getChildren().add(solution);
        controls.getChildren().add(help);

        difficulty.setMin(1);
        difficulty.setMax(5);
        difficulty.setValue(0);
        difficulty.setShowTickLabels(true);
        difficulty.setShowTickMarks(true);
        difficulty.setMajorTickUnit(1);
        difficulty.setMinorTickCount(0);
        difficulty.setSnapToTicks(true);
        controls.getChildren().add(difficulty);
    }

    public void restart() {
        if (this.jumpin != null) {
            this.jumpin = new Jumpin(jumpin.getChallenge());
            this.reset();
            this.instantiatePieces();
        }
    }

    public void newGame() {
        this.jumpin = new Jumpin((int) difficulty.getValue() - 1);
        this.reset();
        this.instantiatePieces();
    }

    public void makeSolution() {
        if (!solutionText.getChildren().isEmpty()) {
            solutionText.getChildren().clear();
        }
        if (jumpin != null) {
            State[] originalState = new State[25];
            for (int i = 0; i < 25; i++) {
                originalState[i] = jumpin.getState()[i];
            }
            String[] sol = jumpin.solve();
            jumpin.setState(originalState);
            jumpin.initialiseBoardState();
            Text solution = new Text(sol[1]);
            solution.setFill(Color.RED);
            solution.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
            solution.setLayoutX(0);
            solution.setLayoutY(GAME_HEIGHT - 110);
            solution.setTextAlignment(TextAlignment.CENTER);
            solutionText.getChildren().add(solution);
        }
    }

    private void reset() {
        this.resetSelectionCircles();
        this.mushrooms.getChildren().clear();
        this.rabbits.getChildren().clear();
        this.foxes.getChildren().clear();
        this.rabbitPieces = new RabbitPiece[3];
        this.foxPieces = new FoxPiece[2];
        this.solutionText.getChildren().clear();
        this.highlightedPiece = null;
        hideCompletion();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(this.root, GAME_WIDTH, GAME_HEIGHT);
        stage.setScene(scene);
        this.makeControls();
        this.makeBoard();
        this.makeCompletion();
        this.root.getChildren().add(this.controls);
        this.root.getChildren().add(this.board);
        this.root.getChildren().add(this.mushrooms);
        this.root.getChildren().add(this.rabbits);
        this.root.getChildren().add(this.foxes);
        this.root.getChildren().add(this.selectionPieces);
        this.root.getChildren().add(this.solutionText);
        stage.show();
    }
}
