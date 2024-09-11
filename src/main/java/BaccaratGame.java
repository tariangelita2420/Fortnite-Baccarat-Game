import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.text.Text;

import javafx.util.Duration;
import javafx.util.converter.DoubleStringConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

public class BaccaratGame extends Application {
    HashMap<String, Scene> sceneMap;
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    BaccaratDealer theDealer;
    BaccaratGameLogic gameLogic;
    double currentBet = 0.0;	// Bid default to 0.0
    double totalWinnings;
    String betOn;	// Holds value for who we're currently bidding on
    PauseTransition pause = new PauseTransition(Duration.seconds(3));

    // Constructor
    public BaccaratGame() {
        playerHand = new ArrayList<Card>();
        bankerHand = new ArrayList<Card>();
        theDealer = new BaccaratDealer();
        gameLogic = new BaccaratGameLogic();
    }

    // This method will determine if the user won or lost their bet and return the amount won or
    // lost based on the value in currentBet
    public double evaluateWinnings() {
        double winnings = 0.0;
        String winner = gameLogic.whoWon(bankerHand, playerHand);

        if (betOn.equals(winner)) {
            if (winner.equals("Banker")) {
                // Banker bet won, with 5% commission
                winnings += currentBet * 0.95;
            } else if (winner.equals("Draw")) {
                // Draw bet won, bets 8:1
                winnings += currentBet * 8;
            } else if (winner.equals("Player")) {
                // Player bet won
                winnings += currentBet;
            }
        } else {    // lost the bet
            winnings -= currentBet;
        }

        return winnings;
    }

    // Scene Building -----------------------------------------------------

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        primaryStage.setTitle("Welcome to Baccarat Game by Taria and Sebastian :}");
        VBox theVBox = new VBox(); //creating VBox
        sceneMap = new HashMap<String, Scene>(); //creating scene hash map
        BorderPane borderPane = new BorderPane(); //creating BorderPane

        Button playButton = new Button("Play"); //creating play button
        Button exitButton = new Button("Exit"); //creating exit button
        playButton.setStyle("-fx-background-color: #c59ed3; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        exitButton.setStyle("-fx-background-color: #409bd5; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        theVBox.getChildren().addAll(playButton,exitButton); //adding buttons to VBox

        //Pane to place buttons in center
        Pane root = new Pane();
        playButton.setLayoutX(520);
        playButton.setLayoutY(-160);
        exitButton.setLayoutX(520);
        exitButton.setLayoutY(-110);
        root.getChildren().addAll(playButton, exitButton);
        borderPane.setBottom(root); //set buttons in the center
        theVBox.setAlignment(Pos.CENTER);

        //creating background image
        BackgroundImage backgroundImage = new BackgroundImage(
                new Image("Blue_Background.jpg", 1000, 650, false, false),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );

        borderPane.setBackground(new Background(backgroundImage));

        //adding images
        Image image = new Image("gameTitle.png");
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        borderPane.setCenter(imageView);

        Image image2 = new Image("llamaGif.gif");
        ImageView imageView2 = new ImageView();
        imageView2.setImage(image2);
        borderPane.setRight(imageView2);

        Image image3 = new Image("llamaGif.gif");
        ImageView imageView3 = new ImageView();
        imageView3.setImage(image3);
        borderPane.setLeft(imageView3);

        // Play Button to move to the playing scene
        playButton.setOnAction(e -> primaryStage.setScene(sceneMap.get("playingScene")));

        sceneMap.put("playingScene", playingScene());
        // Exit button to exit application
        exitButton.setOnAction(e -> primaryStage.close());

        primaryStage.setScene(new Scene(borderPane,1200,650));
        primaryStage.show();
    }


    public Scene playingScene() {
        BorderPane borderPane = new BorderPane(); //creating BorderPane

        //creating background image
        BackgroundImage backgroundImage2 = new BackgroundImage(
                new Image("storm.JPG", 1800, 1000, false, false),
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        borderPane.setBackground(new Background(backgroundImage2));

        // Create Menu
        Menu m = new Menu("Options");
        // Create Menu Items
        MenuItem m1 = new MenuItem("Fresh Start");
        MenuItem m2 = new MenuItem("Exit");
        // Adding Menu items to menu
        m.getItems().add(m1);
        m.getItems().add(m2);
        // Set action for Exit button
        m2.setOnAction (e -> {
            Platform.exit();
            System.exit(0);
        });

        // Create a menubar
        MenuBar mb = new MenuBar();
        // Adding menu to menubar
        mb.getMenus().add(m);
        // Vbox for menubar
        VBox vbMenu = new VBox(mb);
        // Set Vbox Menu in BorderPane
        borderPane.setTop(vbMenu);


        // Text Fields
        Text totalWinningsText = new Text();
        TextField bidAmount = new TextField();
        totalWinningsText.setText("TOTAL WINNINGS:\n$" + totalWinnings);
        totalWinningsText.setStyle("-fx-fill: #e193df;-fx-font: bold 25 Calibri;  -fx-fill: #eeeaea; -fx-text-alignment: center;");

        // Format TextField to accept only double values for Bid
        TextFormatter<Double> formatter1 = new TextFormatter<>(new DoubleStringConverter(), 0.0, c -> Pattern.matches("\\d*", c.getText()) ? c : null);
        bidAmount.setTextFormatter(formatter1);


        // Initiate main buttons for gameplay
        Button playerButton = new Button("Player");
        playerButton.setStyle("-fx-background-color: #cf8fe3; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        Button drawButton = new Button("Draw");
        drawButton.setStyle("-fx-background-color: #cf8fe3; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        Button bankerButton = new Button("Banker");
        bankerButton.setStyle("-fx-background-color: #cf8fe3; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        bidAmount.setStyle("-fx-fill: #e193df;-fx-font: bold 25 Calibri;  -fx-fill: #010101; -fx-text-alignment: center;");
        Button dealButton = new Button("Deal");
        dealButton.setStyle("-fx-background-color: #cf8fe3; -fx-text-fill: #E6E6E6; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 24 Calibri; ");
        Text bidAmountText = new Text("ENTER BID AMOUNT:");
        bidAmountText.setStyle("-fx-background-color: #cf8fe3; -fx-text-fill: #eeeaea; -fx-pref-height: 20px; -fx-pref-width: 150px;  -fx-font: bold 20 Calibri; ");
        Button moneySign =  new Button("$");
        moneySign.setStyle("-fx-background-color: #ffffff; -fx-text-fill: #050505; -fx-pref-height: 20px; -fx-pref-width: 6px;  -fx-font: bold 24 Calibri; ");


        // Pane to help layout Buttons and Text Fields
        Pane root1 = new Pane();
        playerButton.setLayoutX(5);
        playerButton.setLayoutY(-5);
        drawButton.setLayoutX(160);
        drawButton.setLayoutY(-5);
        bankerButton.setLayoutX(315);
        bankerButton.setLayoutY(-5);
        bidAmount.setLayoutX(520);
        bidAmount.setLayoutY(-5);
        moneySign.setLayoutX(490);
        moneySign.setLayoutY(-4);
        bidAmountText.setLayoutX(550);
        bidAmountText.setLayoutY(-20);
        dealButton.setLayoutX(840);
        dealButton.setLayoutY(-5);

        // Add objects to root1 to be set to the bottom pane
        root1.getChildren().addAll(playerButton, drawButton, bankerButton, bidAmount, dealButton, bidAmountText, moneySign);
        borderPane.setBottom(root1);

        // Display results with ListView
        ListView displayResults = new ListView();
        displayResults.getItems().add("Welcome to Baccarat :D!");
        displayResults.getItems().add("Place a bid of your choice!");
        displayResults.setMinHeight(200);
        displayResults.setMaxHeight(200);
        displayResults.setMinWidth(240);
        displayResults.setMaxWidth(240);
        displayResults.setStyle("-fx-control-inner-background: rgb(148,146,210);");
        displayResults.setOpacity(1000);


        Pane root2 = new Pane();
        totalWinningsText.setLayoutX(250);
        totalWinningsText.setLayoutY(20);
        displayResults.setLayoutX(210);
        displayResults.setLayoutY(90);

        // Load the GIF
        Image gif1 = new Image("TakeTheL.gif");
        ImageView showGif1 = new ImageView(gif1);
        showGif1.setVisible(false);	// make it invisible
        showGif1.setFitHeight(170);
        showGif1.setFitWidth(170);
        showGif1.setTranslateX(260);
        showGif1.setTranslateY(320);
        showGif1.setPreserveRatio(true);

        Image gif2 = new Image("LetItRain.gif");
        ImageView showGif2 = new ImageView(gif2);
        showGif2.setVisible(false);	// make it invisible
        showGif2.setFitHeight(270);
        showGif2.setFitWidth(270);
        showGif2.setTranslateX(175);
        showGif2.setTranslateY(275);
        showGif2.setPreserveRatio(true);

        root2.getChildren().addAll(totalWinningsText,displayResults, showGif1, showGif2);
        borderPane.setCenter(root2);

        // Player cards Hbox to the LEFT
        HBox playerCards = new HBox();
        playerCards.setAlignment(Pos.CENTER);

        Text playerText = new Text("");
        playerText.setText("PLAYER \uD83C\uDFAE");
        playerText.setStyle("-fx-fill: #e193df;-fx-font: bold 25 Calibri;  -fx-fill: #eeeaea; -fx-text-alignment: center;");

        Pane root3 = new Pane();
        playerText.setLayoutX(80);
        playerText.setLayoutY(180);
        root3.getChildren().addAll(playerCards);
        root3.getChildren().addAll(playerText);
        borderPane.setLeft(root3);

        // Banker cards Hbox to the RIGHT
        HBox bankerCards = new HBox();
        bankerCards.setAlignment(Pos.CENTER);

        Text bankerText = new Text("");
        bankerText.setText("BANKER \uD83D\uDCB0");
        bankerText.setStyle("-fx-fill: #e193df;-fx-font: bold 25 Calibri;  -fx-fill: #eeeaea; -fx-text-alignment: center;");


        Pane root4 = new Pane();
        bankerText.setLayoutX(-30);
        bankerText.setLayoutY(180);
        root4.getChildren().addAll(bankerCards);
        root4.getChildren().addAll(bankerText);
        borderPane.setRight(root4);


        // Player and Banker Card objects Set Up
        PauseTransition playerCard1 = new PauseTransition(Duration.seconds(1));
        playerCard1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + playerHand.get(0).suite + playerHand.get(0).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url, true);

                ImageView playerCard = new ImageView(imageCard);
                playerCard.setImage(imageCard);
                playerCard.setTranslateX(7);
                playerCard.setTranslateY(200);
                playerCard.setCache(true);
                playerCard.setPreserveRatio(true);
                playerCard.setFitHeight(175);
                playerCard.setFitWidth(80);

                playerCards.getChildren().addAll(playerCard);
            }
        });

        PauseTransition playerCard2 = new PauseTransition(Duration.seconds(2));
        playerCard2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + playerHand.get(1).suite + playerHand.get(1).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url, true);

                ImageView playerCard = new ImageView(imageCard);
                playerCard.setImage(imageCard);
                playerCard.setTranslateX(10);
                playerCard.setTranslateY(200);
                playerCard.setCache(true);
                playerCard.setPreserveRatio(true);
                playerCard.setFitHeight(175);
                playerCard.setFitWidth(80);

                playerCards.getChildren().addAll(playerCard);
            }
        });

        PauseTransition playerCard3 = new PauseTransition(Duration.seconds(4));
        playerCard3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + playerHand.get(2).suite + playerHand.get(2).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url, true);

                ImageView playerCard = new ImageView(imageCard);
                playerCard.setImage(imageCard);
                playerCard.setTranslateX(13);
                playerCard.setTranslateY(200);
                playerCard.setCache(true);
                playerCard.setPreserveRatio(true);
                playerCard.setFitHeight(175);
                playerCard.setFitWidth(80);

                playerCards.getChildren().addAll(playerCard);
            }
        });

        PauseTransition bankerCard1 = new PauseTransition(Duration.seconds(1));
        bankerCard1.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + bankerHand.get(0).suite + bankerHand.get(0).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url, true);

                ImageView bankerCard = new ImageView(imageCard);
                bankerCard.setImage(imageCard);
                bankerCard.setTranslateX(9);
                bankerCard.setTranslateY(200);
                bankerCard.setCache(true);
                bankerCard.setPreserveRatio(true);
                bankerCard.setFitHeight(170);
                bankerCard.setFitWidth(80);

                bankerCards.getChildren().addAll(bankerCard);
            }
        });

        PauseTransition bankerCard2 = new PauseTransition(Duration.seconds(2));
        bankerCard2.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + bankerHand.get(1).suite + bankerHand.get(1).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url, true);

                ImageView bankerCard = new ImageView(imageCard);
                bankerCard.setImage(imageCard);
                bankerCard.setTranslateX(12);
                bankerCard.setTranslateY(200);
                bankerCard.setCache(true);
                bankerCard.setPreserveRatio(true);
                bankerCard.setFitHeight(175);
                bankerCard.setFitWidth(80);

                bankerCards.getChildren().addAll(bankerCard);
            }
        });

        PauseTransition bankerCard3 = new PauseTransition(Duration.seconds(6));
        bankerCard3.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String url =  "Cards/" + bankerHand.get(2).suite + bankerHand.get(2).value + ".jpg";
                System.out.println(url);
                Image imageCard = new Image(url,true);

                ImageView bankerCard = new ImageView(imageCard);
                bankerCard.setImage(imageCard);
                bankerCard.setTranslateX(15);
                bankerCard.setTranslateY(200);
                bankerCard.setCache(true);
                bankerCard.setPreserveRatio(true);
                bankerCard.setFitHeight(175);
                bankerCard.setFitWidth(80);

                bankerCards.getChildren().addAll(bankerCard);
            }
        });


        // Deal button is initially disabled
        dealButton.setDisable(true);

        // Player button Action Event, set betOn Player
        playerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playerButton.setOpacity(1);
                drawButton.setOpacity(0.5);
                bankerButton.setOpacity(0.5);

                // Enable Deal Button
                dealButton.setDisable(false);

                betOn = "Player";
            }
        });

        // Banker button Action Event, set betOn Banker
        bankerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playerButton.setOpacity(0.5);
                drawButton.setOpacity(0.5);
                bankerButton.setOpacity(1);

                // Enable Deal Button
                dealButton.setDisable(false);

                betOn = "Banker";
            }
        });

        // Draw button Action Event, set betOn Draw
        drawButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playerButton.setOpacity(0.5);
                drawButton.setOpacity(1);
                bankerButton.setOpacity(0.5);

                // Enable Deal Button
                dealButton.setDisable(false);

                betOn = "Draw";
            }
        });

        // Save bid amount to currentBet when value is entered
        bidAmount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                currentBet = Double.parseDouble(bidAmount.getText());
            }
        });

        // Deal button Action Event, starts game and shows game actions
        dealButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                dealButton.setDisable(true);
                bidAmount.setDisable(true);
                bankerButton.setDisable(true);
                drawButton.setDisable(true);
                playerButton.setDisable(true);

                showGif1.setVisible(false);
                showGif2.setVisible(false);

                // Remove cards at the start of each game if any
                int playerCardsSize = playerCards.getChildren().size();
                if (playerCardsSize > 0) {
                    for (int i = 0; i < playerCardsSize; i++) {
                        playerCards.getChildren().remove(0);
                    }
                }
                int bankerCardsSize = bankerCards.getChildren().size();
                if (bankerCardsSize > 0) {
                    for (int i = 0; i < bankerCardsSize; i++) {
                        bankerCards.getChildren().remove(0);
                    }
                }
                // reset and shuffle deck
                theDealer.generateDeck();
                theDealer.shuffleDeck();

                // Deal new cards
                playerHand = theDealer.dealHand();
                bankerHand = theDealer.dealHand();
                playerCard1.play();
                playerCard2.play();
                bankerCard1.play();
                bankerCard2.play();

                // Deal a third card if required
                boolean playerDrew = false;
                if (gameLogic.evaluatePlayerDraw(playerHand)) { // should player draw 3rd card?
                    playerHand.add(theDealer.drawOne());
                    playerDrew = true;
                    playerCard3.play();
                }
                if (playerDrew) { // if player drew, then see if banker draws too
                    if (gameLogic.evaluateBankerDraw(bankerHand,playerHand.get(2))) {
                        bankerHand.add(theDealer.drawOne());
                        bankerCard3.play();
                    }

                } else if (!playerDrew) { // if player didn't draw
                    if (gameLogic.evaluateBankerDraw(bankerHand,null)) {
                        bankerHand.add(theDealer.drawOne());
                        bankerCard3.play();
                    }
                }

                // Display current bet
                displayResults.getItems().add("");
                displayResults.getItems().add("Current bet is $" + currentBet + " on " + betOn + ".");
                displayResults.scrollTo(displayResults.getItems().size());	// Scrolls to bottom to show latest update

                String winner = gameLogic.whoWon(bankerHand, playerHand);
                // Display Card totals
                PauseTransition pause1 = new PauseTransition(Duration.seconds(6.5));
                pause1.setOnFinished (e-> {
                    displayResults.getItems().add("");
                    displayResults.getItems().add("Player Total: " + gameLogic.handTotal(playerHand) + "  Banker Total: " + gameLogic.handTotal(bankerHand));
                    displayResults.scrollTo(displayResults.getItems().size());
                });
                pause1.play();

                // Display who won
                PauseTransition pause2 = new PauseTransition(Duration.seconds(7));
                pause2.setOnFinished (e-> {
                    displayResults.getItems().add(winner + " wins");
                    displayResults.scrollTo(displayResults.getItems().size());
                });
                pause2.play();

                // Display if bet is won and update total winnings display
                PauseTransition pause3 = new PauseTransition(Duration.seconds(8));
                pause3.setOnFinished (e-> {
                    if (betOn == winner) {
                        displayResults.getItems().add("Congrats, you bet " + winner + "! YOU WIN!");
                        showGif2.setVisible(true);
                    } else {
                        displayResults.getItems().add("Sorry, you bet " + betOn + "! You lost your bet!");
                        showGif1.setVisible(true);
                    }
                    displayResults.scrollTo(displayResults.getItems().size());
                    totalWinnings += evaluateWinnings();
                    totalWinningsText.setText("TOTAL WINNINGS:\n$" + totalWinnings);
                });
                pause3.play();

                // Display to make another bid and enable game buttons
                PauseTransition pause4 = new PauseTransition(Duration.seconds(9));
                pause4.setOnFinished (e-> {
                    displayResults.getItems().add("");
                    displayResults.getItems().add("Make another bid?");
                    displayResults.scrollTo(displayResults.getItems().size());

                    playerButton.setOpacity(1);
                    drawButton.setOpacity(1);
                    bankerButton.setOpacity(1);
                    bankerButton.setDisable(false);
                    drawButton.setDisable(false);
                    playerButton.setDisable(false);
                    bidAmount.setDisable(false);
                });
                pause4.play();

            }
        });

        // Fresh Start, reset key game components
        m1.setOnAction (e -> {
            // Remove cards if any
            int playerCardsSize = playerCards.getChildren().size();
            for(int i = 0; i < playerCardsSize; i++) {
                playerCards.getChildren().remove(0);
            }

            int bankerCardsSize = bankerCards.getChildren().size();
            for(int i = 0; i < bankerCardsSize; i++) {
                bankerCards.getChildren().remove(0);
            }

            showGif1.setVisible(false);
            showGif2.setVisible(false);
            dealButton.setDisable(true);
            bankerButton.setDisable(false);
            drawButton.setDisable(false);
            playerButton.setDisable(false);
            bidAmount.setDisable(false);
            bidAmount.clear();

            currentBet = 0.0;
            totalWinnings = 0.0;
            totalWinningsText.setText("TOTAL WINNINGS:\n$" + totalWinnings);
            displayResults.getItems().clear();
            displayResults.getItems().add("Welcome to Baccarat!");
        });

        return new Scene(borderPane,1200,650);
    }
    // Getters for testing
    public ArrayList<Card> getPlayerHand() {
        return playerHand;
    }
    public ArrayList<Card> getBankerHand() {
        return bankerHand;
    }
    public BaccaratDealer getTheDealer() {
        return theDealer;
    }
    public BaccaratGameLogic getGameLogic() {
        return gameLogic;
    }

}