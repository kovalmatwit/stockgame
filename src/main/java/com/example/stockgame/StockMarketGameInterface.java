package com.example.stockgame;

import javafx.application.Application;
import javafx.event.Event;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Main class of the stock market game, extending JavaFX Application.
 * It sets up the user interface and initializes the game logic.
 */
public class StockMarketGameInterface extends Application {
    // Layout for the primary content of the application.
    public final BorderPane rootLayout = new BorderPane();
    // Lists to manage all available stocks and stocks bought by the user.
    public final List<Stock> allStocks = new ArrayList<>();
    public final List<Stock> boughtStocks = new ArrayList<>();
    // Day counter and financial metrics.
    public int dayNumber = 1;
    public double portfolioCost;
    public double availableCash = 1000;
    // Flag to track if the portfolio screen is being displayed.
    public boolean isPortfolio = false;
    public double previousPortfolioCost = 0;

    /**
     * Start method to set up the primary stage of the application.
     * @param primaryStage The primary stage for this application, onto which
     *                     the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Market Game");
        Scene scene = new Scene(rootLayout, 600, 600);
        restartGame();
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
        showInstructions(primaryStage);
    }

    /**
     * Sets up the main menu interface displaying the current financial state, available stocks, and navigation options.
     */
    public void setupMainMenu() {
        isPortfolio = false;

        Text availableCashText = new Text("Available cash: $" + String.format("%.2f", availableCash));
        availableCashText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button myPortfolioButton = new Button("My Portfolio");
        myPortfolioButton.setPrefSize(100,20);
        myPortfolioButton.setOnAction(e -> setupPortfolioScreen());

        Button nextDayButton = new Button("Next Day");
        nextDayButton.setPrefSize(80,20);
        nextDayButton.setOnAction(e -> updateAllStocks());

        HBox topLayout = new HBox(10);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.setPadding(new Insets(10));
        topLayout.setSpacing(100);
        topLayout.getChildren().addAll(availableCashText, nextDayButton, myPortfolioButton);

        GridPane headerGrid = new GridPane();
        headerGrid.setHgap(10);
        ColumnConstraints col1 = new ColumnConstraints(120);
        ColumnConstraints col2 = new ColumnConstraints(120);
        ColumnConstraints col3 = new ColumnConstraints(100);
        headerGrid.getColumnConstraints().addAll(col1, col2, col3);

        headerGrid.add(createHeader("Name"), 0, 0);
        headerGrid.add(createHeader("Risk Level"), 1, 0);
        headerGrid.add(createHeader("Price"), 2, 0);

        VBox layout = new VBox(5);
        layout.getChildren().addAll(topLayout, headerGrid, displayStockTable(allStocks));
        layout.setPadding(new Insets(10));

        rootLayout.setCenter(layout);
    }

    /**
     * Sets up the portfolio screen showing stocks owned by the user and their total value.
     */
    public void setupPortfolioScreen() {
        isPortfolio = true;
        updatePortfolioCost();

        Text portfolioCostText = new Text(String.format("Portfolio cost: %.2f", portfolioCost));
        portfolioCostText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        String portfolioIndicator = portfolioCost > previousPortfolioCost ? "↑" : "↓";
        Text portfolioChangeText = new Text(portfolioIndicator);
        portfolioChangeText.setFill(portfolioCost > previousPortfolioCost ? Color.GREEN : Color.RED);
        portfolioChangeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text dayNumberText = new Text(String.format("Day %s", dayNumber));
        dayNumberText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> setupMainMenu());

        Region spacer = new Region();
        spacer.setMinWidth(175);

        HBox topLayout = new HBox(10);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.getChildren().addAll(portfolioCostText, portfolioChangeText, spacer, dayNumberText, mainMenuButton);

        GridPane headerGrid = new GridPane();
        headerGrid.setHgap(10);
        ColumnConstraints col1 = new ColumnConstraints(120);
        ColumnConstraints col2 = new ColumnConstraints(120);
        ColumnConstraints col3 = new ColumnConstraints(100);
        ColumnConstraints col4 = new ColumnConstraints(100);
        headerGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        headerGrid.add(createHeader("Name"), 0, 0);
        headerGrid.add(createHeader("Risk Level"), 1, 0);
        headerGrid.add(createHeader("Total Price"), 2, 0);
        headerGrid.add(createHeader("Owned"), 3, 0);

        VBox layout = new VBox(5);
        layout.getChildren().addAll(topLayout, headerGrid, displayStockTable(boughtStocks));
        layout.setPadding(new Insets(10));

        rootLayout.setCenter(layout);
    }

    public void showInstructions(Stage primaryStage) {
        Stage instructionsStage = new Stage();
        instructionsStage.initModality(Modality.WINDOW_MODAL);
        instructionsStage.initOwner(primaryStage);
        instructionsStage.setTitle("Game Instructions");

        String instructionsText = "You have $1000 to buy any stocks you would like. " +
                "Stocks have 3 different risk levels which reflect by how much the stock " +
                "falls down or goes up. The goal of the game is to get as much money as possible in 20 days.";
        Text text = new Text(instructionsText);
        text.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        text.setWrappingWidth(400);

        Button closeButton = new Button("Start Game");
        closeButton.setOnAction(e -> instructionsStage.close());

        VBox layout = new VBox(20, text, closeButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10));

        Scene instructionsScene = new Scene(layout, 450, 200);
        instructionsStage.setScene(instructionsScene);

        // Show and wait for the user to read and close
        instructionsStage.showAndWait();
    }

    /**
     * Creates a header Text object for displaying column titles in the stock tables.
     * @param headerTitle The text to be displayed as the header.
     * @return A Text object styled as a header.
     */
    public Text createHeader(String headerTitle) {
        Text header = new Text(headerTitle);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        return header;
    }

    /**
     * Generates a ScrollPane containing a table of stocks, either from all available stocks or the user's portfolio.
     * @param stocks The list of stocks to display in the table.
     * @return A ScrollPane containing the stocks table.
     */
    public ScrollPane displayStockTable(List<Stock> stocks) {
        GridPane stockGrid = new GridPane();
        stockGrid.setHgap(10);
        stockGrid.setVgap(5);
        stockGrid.setPadding(new Insets(10, 0, 10, 10));

        ColumnConstraints col1 = new ColumnConstraints(110);
        ColumnConstraints col2 = new ColumnConstraints(120);
        ColumnConstraints col3 = new ColumnConstraints(100);
        ColumnConstraints col4 = new ColumnConstraints(80);
        stockGrid.getColumnConstraints().addAll(col1, col2, col3, col4);

        int rowIndex = 0;
        for (Stock stock : stocks) {
            stockGrid.add(new Text(stock.getName()), 0, rowIndex);
            stockGrid.add(new Text(stock.getRiskLevel()), 1, rowIndex);
            if (isPortfolio) {
                Button sellButton = new Button("Sell");
                sellButton.setPrefSize(80, 20);
                sellButton.setOnAction(e -> showBuySellStockWindow(stock, true));
                stockGrid.add(new Text(String.format("$%.2f", stock.getPrice() * stock.getAmountOwned())), 2, rowIndex);
                stockGrid.add(new Text(String.format("%.4f", stock.getAmountOwned())), 3, rowIndex);
                stockGrid.add(sellButton, 4, rowIndex);
            } else {
                Button buyButton = new Button("Buy");
                buyButton.setPrefSize(80, 20);
                buyButton.setOnAction(e -> showBuySellStockWindow(stock, false));
                stockGrid.add(new Text(String.format("$%.2f", stock.getPrice())), 2, rowIndex);
                stockGrid.add(buyButton, 3, rowIndex);
            }
            rowIndex++;
        }
        checkGameOver();

        ScrollPane scrollPane = new ScrollPane(stockGrid);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    /**
     * Initializes the list of stocks available in the game. This includes generating random stocks of different risk levels.
     */
    public void initializeStockList() {
        allStocks.clear();
        Random random = new Random();
        for (int i = 1; i <= 33; i++) {
            allStocks.add(new LowRiskStock(generateRandomName(), random.nextDouble() * 1000, 0));
            allStocks.add(new MidRiskStock(generateRandomName(), random.nextDouble() * 1000, 0));
            allStocks.add(new HighRiskStock(generateRandomName(), random.nextDouble() * 1000, 0));
        }
    }

    /**
     * Opens a new window for buying or selling the selected stock, allowing the user to input the amount of money to spend or receive.
     * @param stock The stock being bought or sold.
     * @param isPortfolio A flag indicating if the stock is from the user's portfolio (true) or the market (false).
     */
    public void showBuySellStockWindow(Stock stock, boolean isPortfolio) {
        Stage newWindow = new Stage();
        newWindow.setTitle((isPortfolio ? "Sell" : "Buy") + " Stock: " + stock.getName());

        Text stockNameText = new Text(stock.getName());
        stockNameText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text priceText = new Text("Price: $" + String.format("%.2f", stock.getPrice()));
        priceText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        TextField amountField = new TextField("Enter amount of money");
        amountField.setMaxWidth(150);

        Text sharesText = new Text("= 0 shares");
        sharesText.setFont(Font.font("Arial", FontWeight.NORMAL, 16));

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                double enteredAmount = Double.parseDouble(newValue);
                double numberOfShares = enteredAmount / stock.getPrice();
                sharesText.setText(String.format("= %.4f shares", numberOfShares));
            } catch (NumberFormatException e) {
                sharesText.setText("Invalid input");
            } finally {
                checkGameOver();
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(100);

        HBox amountFieldAndShares = new HBox(5);
        amountFieldAndShares.getChildren().addAll(spacer, amountField, sharesText);
        amountFieldAndShares.setAlignment(Pos.CENTER_LEFT);

        Button actionButton = new Button(isPortfolio ? "Sell" : "Buy");
        Button sellAllButton = new Button("Sell all");
        sellAllButton.setPrefSize(80,20);
        actionButton.setPrefSize(80, 20);
        actionButton.setOnAction(e -> {
            try {
                double totalCost = Double.parseDouble(amountField.getText());
                double stockAmount = totalCost / stock.getPrice();
                if (isPortfolio) {
                    //Selling logic
                    Stock ownedStock = findOwnedStockByName(stock.getName());

                    if (ownedStock != null && ownedStock.getAmountOwned() >= stockAmount) {
                        availableCash += totalCost;
                        portfolioCost -= totalCost;
                        double newAmount = ownedStock.getAmountOwned() - stockAmount;

                        if (newAmount < 0.2) {
                            boughtStocks.remove(ownedStock);
                        }

                        ownedStock.setAmountOwned(newAmount);
                        newWindow.close();
                        setupPortfolioScreen();
                    } else {
                        errorWindow("You do not own enough shares");
                    }
                } else {
                    // Buying logic
                    if (availableCash >= totalCost && totalCost > 0) {
                        availableCash -= totalCost;
                        Stock ownedStock = findOwnedStockByName(stock.getName());
                        if (ownedStock == null) {
                            ownedStock = switch (stock.getRiskLevel()) {
                                case "Low" -> new LowRiskStock(stock.getName(), stock.getPrice(), stockAmount);
                                case "Medium" -> new MidRiskStock(stock.getName(), stock.getPrice(), stockAmount);
                                case "High" -> new HighRiskStock(stock.getName(), stock.getPrice(), stockAmount);
                                default -> throw new IllegalStateException("Unexpected value: " + stock.getRiskLevel());
                            };
                            boughtStocks.add(ownedStock);
                        } else {
                            double newAmount = ownedStock.getAmountOwned() + stockAmount;
                            ownedStock.setAmountOwned(newAmount);
                        }
                        newWindow.close();
                        setupMainMenu();
                    } else {
                        errorWindow("Not enough cash to buy.");
                    }
                }
            } catch (NumberFormatException ex) {
                errorWindow("Invalid input");
            }
        });

        sellAllButton.setOnAction(e -> {
            double totalCost = stock.getAmountOwned() * stock.getPrice();
            Stock ownedStock = findOwnedStockByName(stock.getName());

            availableCash += totalCost;
            portfolioCost -= totalCost;

            boughtStocks.remove(ownedStock);
            newWindow.close();
            setupPortfolioScreen();
        });

        HBox buttonsLayout = new HBox();
        if(isPortfolio)
        {
            buttonsLayout.getChildren().addAll(actionButton, sellAllButton);
        } else {
            buttonsLayout.getChildren().addAll(actionButton);
        }

        buttonsLayout.setAlignment(Pos.CENTER);
        buttonsLayout.setSpacing(60);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(stockNameText, priceText, amountFieldAndShares, buttonsLayout);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(60);

        Scene scene = new Scene(layout, 400, 300);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }

    /**
     * Displays an error window with a specified error message.
     * @param error The error message to display.
     */
    public void errorWindow(String error) {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Error");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 50, 10, 50));

        Text errorText = new Text(error);
        errorText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> popupStage.close());

        layout.getChildren().addAll(errorText, closeButton);

        Scene scene = new Scene(layout);
        popupStage.setScene(scene);
        popupStage.showAndWait();
    }

    /**
     * Finds a stock owned by the user by its name.
     * @param stockName The name of the stock to find.
     * @return The found stock, or null if not found.
     */
    public Stock findOwnedStockByName(String stockName) {
        return boughtStocks.stream()
                .filter(stock -> stock.getName().equals(stockName))
                .findFirst()
                .orElse(null);
    }

    /**
     * Updates the total cost of the user's portfolio.
     */
    public void updatePortfolioCost() {
        portfolioCost = boughtStocks.stream()
                .mapToDouble(stock -> stock.getPrice() * stock.getAmountOwned())
                .sum();
    }

    /**
     * Updates all stocks for a new day and checks if the game is over.
     */
    public void updateAllStocks() {
        previousPortfolioCost = portfolioCost;

        allStocks.forEach(Stock::updatePrice);
        boughtStocks.forEach(stock ->
                allStocks.stream()
                        .filter(availableStock -> stock.getName().equals(availableStock.getName()))
                        .findFirst()
                        .ifPresent(availableStock -> stock.setPrice(availableStock.getPrice())));

        checkGameOver();
        dayNumber++;
        updatePortfolioCost();
        setupMainMenu();
    }

    /**
     * Generates a random name for a stock.
     * @return A string representing a random stock name.
     */
    public static String generateRandomName() {
        Random random = new Random();
        int length = random.nextInt(5) + 1;
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            char letter = (char) ('A' + random.nextInt(26));
            sb.append(letter);
        }

        return sb.toString();
    }

    /**
     * Checks if the game should end based on the day number or other criteria.
     */
    public void checkGameOver() {
        if (dayNumber == 20) {
            setupGameOverScreen();
        }
    }

    /**
     * Sets up and displays the game over screen, offering options to restart or exit the game.
     */
    public void setupGameOverScreen() {
        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Game Over");
        popupStage.setResizable(false);

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(10, 50, 10, 50));

        Text gameOverText = new Text("Game Over");
        gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button restartButton = new Button("Restart");
        restartButton.setOnAction(e -> {
            popupStage.close();
            restartGame();
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> System.exit(0));

        layout.getChildren().addAll(gameOverText, restartButton, exitButton);

        Scene scene = new Scene(layout);
        popupStage.setScene(scene);

        popupStage.setOnCloseRequest(Event::consume);

        popupStage.showAndWait();
    }

    /**
     * Resets the game to its initial state, clearing all user progress and reinitializing the stock list.
     */
    public void restartGame() {
        availableCash = 1000;
        boughtStocks.clear();
        previousPortfolioCost = 0;
        dayNumber = 1;
        initializeStockList();
        setupMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
