package com.example.stockgame;

import javafx.application.Application;
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
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StockMarketGameInterface extends Application {
    private final BorderPane rootLayout = new BorderPane();
    private final List<Stock> allStocks = new ArrayList<>();
    private final List<Stock> boughtStocks = new ArrayList<>();
    private int dayNumber = 1;
    private double portfolioCost;
    private double availableCash = 1000;
    private double previousAvailableCash = 1000; 
    private double previousPortfolioCost = 0; 

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Stock Market Game");
        initializeStockList();
        setupMainMenu();
        primaryStage.setScene(new Scene(rootLayout, 600, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
   
    /**
     * Sets up the main menu interface.
     * Displays available cash, portfolio button, next day button, and a list of all stocks.
     */
  
    private void setupMainMenu() {
        Text availableCashText = new Text("Available cash: $" + String.format("%.2f", availableCash));
        availableCashText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Indicator for available cash change
        String cashIndicator = availableCash > previousAvailableCash ? "↑" : (availableCash < previousAvailableCash ? "↓" : "");
        Text cashChangeText = new Text(cashIndicator);
        cashChangeText.setFill(availableCash > previousAvailableCash ? Color.GREEN : (availableCash < previousAvailableCash ? Color.RED : Color.BLACK));
        cashChangeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button myPortfolioButton = new Button("My Portfolio");
        myPortfolioButton.setPrefSize(100, 20);
        myPortfolioButton.setOnAction(e -> setupPortfolioScreen());

        Button nextDayButton = new Button("Next Day");
        nextDayButton.setPrefSize(80, 20);
        nextDayButton.setOnAction(e -> updateAllStocks());

        HBox topLayout = new HBox(10);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.setPadding(new Insets(10));
        topLayout.getChildren().addAll(availableCashText, cashChangeText, myPortfolioButton, nextDayButton);

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
        layout.getChildren().addAll(topLayout, headerGrid, displayStockTable(allStocks, false));
        layout.setPadding(new Insets(10));

        VBox centerLayout = new VBox(layout, createFooter());
        centerLayout.setPadding(new Insets(10));

        rootLayout.setCenter(centerLayout);
    }
    /**
     * Sets up the portfolio screen.
     * Displays the total value of the user's portfolio, the current day, a main menu button,
     * and a list of stocks that the user has bought.
     */

    private void setupPortfolioScreen() {
        updatePortfolioPrice();

        Text portfolioCosteText = new Text(String.format("Portfolio price: %.2f", portfolioCost));
        portfolioCosteText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        // Indicator for portfolio cost change
        String portfolioIndicator = portfolioCost > previousPortfolioCost ? "↑" : (portfolioCost < previousPortfolioCost ? "↓" : "");
        Text portfolioChangeText = new Text(portfolioIndicator);
        portfolioChangeText.setFill(portfolioCost > previousPortfolioCost ? Color.GREEN : (portfolioCost < previousPortfolioCost ? Color.RED : Color.BLACK));
        portfolioChangeText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Text dayNumberText = new Text(String.format("Day %s", dayNumber));
        dayNumberText.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        Button mainMenuButton = new Button("Main Menu");
        mainMenuButton.setOnAction(e -> setupMainMenu());

        HBox topLayout = new HBox(10);
        topLayout.setAlignment(Pos.CENTER);
        topLayout.getChildren().addAll(portfolioCosteText, portfolioChangeText, mainMenuButton, dayNumberText);

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
        layout.getChildren().addAll(topLayout, headerGrid, displayStockTable(boughtStocks, true));
        layout.setPadding(new Insets(10));

        VBox centerLayout = new VBox(layout, createFooter());
        centerLayout.setPadding(new Insets(10));
        rootLayout.setCenter(centerLayout);
    }

    //Creates and returns a stylized header.
    private Text createHeader(String headerTitle) {
        Text header = new Text(headerTitle);
        header.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        header.setUnderline(true);

        return header;
    }
    // Creates and returns the footer section containing filter buttons for stock risk levels.
    private HBox createFooter() {
        Button allButton = new Button("All");
        Button lowRiskButton = new Button("Low Risk");
        Button mediumRiskButton = new Button("Medium Risk");
        Button highRiskButton = new Button("High Risk");

        //Add actions to each button
        allButton.setOnAction(event -> {});
        lowRiskButton.setOnAction(event -> {});
        mediumRiskButton.setOnAction(event -> {});
        highRiskButton.setOnAction(event -> {});

        HBox footerButtons = new HBox(10, allButton, lowRiskButton, mediumRiskButton, highRiskButton);
        footerButtons.setAlignment(Pos.CENTER);
        footerButtons.setPadding(new Insets(12, 0, 12, 0));

        return footerButtons;
    }

    /**
     * Generates a scrollable table of stocks.
     * If displaying the portfolio, it includes total price and owned amount for each stock, plus a sell button.
     * Otherwise, it displays available stocks with buy buttons.
     */
    private ScrollPane displayStockTable(List<Stock> stocks, boolean isPortfolio) {
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
                stockGrid.add(new Text(String.format("$%.2f", (stock.getPrice() * stock.getAmountOwned()))), 2, rowIndex);
                stockGrid.add(new Text(String.format("%.4f",stock.getAmountOwned())), 3, rowIndex);
                stockGrid.add(sellButton, 4, rowIndex);
                checkGameOver();
            } else {
                Button buyButton = new Button("Buy");
                buyButton.setPrefSize(80, 20);
                buyButton.setOnAction(e -> showBuySellStockWindow(stock, false));
                stockGrid.add(new Text(String.format("$%.2f", stock.getPrice())), 2, rowIndex);
                stockGrid.add(buyButton, 3, rowIndex);
                checkGameOver(); 
            }
            rowIndex++;
        }
        ScrollPane scrollPane = new ScrollPane(stockGrid);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    // Initializes the list of random stocks
    private void initializeStockList() {
        Random random = new Random();
        for (int i = 1; i <= 1; i++) {
            allStocks.add(new LowRiskStock("LowRisk-" + i, random.nextDouble() * 1000, 0));
            allStocks.add(new MidRiskStock("MidRisk-" + i, random.nextDouble() * 1000, 0));
            allStocks.add(new HighRiskStock("HighRisk-" + i, random.nextDouble() * 1000, 0));
        }
    }

    /**
     * Opens a new window for buying or selling the selected stock
     * Where user sees the name of the stock, price and amount of shares they are buying
     */
    private void showBuySellStockWindow(Stock stock, boolean isPortfolio) {
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
            }
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(100);

        HBox amountFieldAndShares = new HBox(5);
        amountFieldAndShares.getChildren().addAll(spacer, amountField, sharesText);
        amountFieldAndShares.setAlignment(Pos.CENTER_LEFT);

        Button actionButton = new Button(isPortfolio ? "Sell" : "Buy");
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

                        if (newAmount == 0) {
                            boughtStocks.remove(ownedStock);
                        }

                        ownedStock.setAmountOwned(newAmount);
                        newWindow.close();
                        setupPortfolioScreen();
                    } else {
                        System.out.println(stock.getName());
                        System.out.println("Not enough stock to sell.");
                    }
                } else {
                    // Buying logic
                    if (availableCash >= totalCost && totalCost > 0) {
                        availableCash -= totalCost;
                        Stock ownedStock = findOwnedStockByName(stock.getName());
                        if (ownedStock == null) {
                            ownedStock = new LowRiskStock(stock.getName(), stock.getPrice(), stockAmount);
                            boughtStocks.add(ownedStock);
                        }

                        newWindow.close();
                        setupMainMenu();
                    } else {
                        System.out.println("Not enough cash to buy.");
                    }
                }
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input");
            }
            finally {
                checkGameOver();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(stockNameText, priceText, amountFieldAndShares, actionButton);
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(60);

        Scene scene = new Scene(layout, 400, 300);
        newWindow.setScene(scene);
        newWindow.showAndWait();
    }

    // Searches the list of bought stocks for a stock with the specified name and returns it.
    private Stock findOwnedStockByName(String stockName) {
        for (Stock stock : boughtStocks) {
            if (stock.getName().equals(stockName)) {
                return stock;
            }
        }
        return null;
    }

    // Calculates the total value of the user's portfolio based on the owned stocks and updates the portfolio cost.
    private void updatePortfolioPrice(){
        portfolioCost = 0;

        for (Stock stock : boughtStocks){
            portfolioCost += stock.getPrice() * stock.getAmountOwned();
        }
    }

    /**
     * Updates the prices of all stocks for a new day.
     * And synchronizes the prices of stocks in the user's portfolio with those in the general stock list.
     */
    private void updateAllStocks(){
    	 previousAvailableCash = availableCash;
    	    previousPortfolioCost = portfolioCost;

    	for (Stock stock : allStocks) {
            stock.updatePrice();
        }

        for (Stock boughtStock : boughtStocks) {
            for (Stock availableStock : allStocks) {
                if (boughtStock.getName().equals(availableStock.getName())) {
                    boughtStock.setPrice(availableStock.getPrice());
                }
            }
        }
        checkGameOver(); 

        dayNumber++;
        updatePortfolioPrice();
        setupMainMenu();
    }
    private void checkGameOver() {
        if (availableCash <= 0 && boughtStocks.isEmpty()) {
            setupGameOverScreen();
        } 
    }
    private void setupGameOverScreen() {

    	VBox layout = new VBox(20);
            layout.setAlignment(Pos.CENTER);
            Text gameOverText = new Text("Game Over");
            gameOverText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

            Button restartButton = new Button("Restart");
            restartButton.setOnAction(e -> restartGame());

            Button exitButton = new Button("Exit");
            exitButton.setOnAction(e -> System.exit(0));

            layout.getChildren().addAll(gameOverText, restartButton, exitButton);
            rootLayout.setCenter(layout);
        };
    

    private void restartGame() {
        availableCash = 1000; 
        previousAvailableCash = 1000;
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