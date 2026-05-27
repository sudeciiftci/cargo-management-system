import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PackageListScreen {

    private final PackageService packageService;

    public PackageListScreen(PackageService packageService) {
        this.packageService = packageService;
    }

    public void show(Stage stage) {
        stage.setTitle("Cargo Management System");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F4F0;");

        // ── SIDEBAR ───────────────────────────────────────────
        VBox sidebar = new VBox(0);
        sidebar.setPrefWidth(220);
        sidebar.setStyle("-fx-background-color: #1C1B18;");

        Label brand = new Label("CargoSys");
        brand.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 16px; -fx-font-weight: bold;");
        brand.setPadding(new Insets(28, 24, 28, 24));

        String[] navItems = {"Create Package", "Package List", "View Details"};
        VBox navList = new VBox(2);
        navList.setPadding(new Insets(0, 12, 0, 12));

        for (int i = 0; i < navItems.length; i++) {
            Label nav = new Label(navItems[i]);
            boolean active = i == 1;
            nav.setPrefWidth(Double.MAX_VALUE);
            nav.setPadding(new Insets(10, 14, 10, 14));
            nav.setStyle(active
                    ? "-fx-background-color: #2E2D29; -fx-text-fill: #FFFFFF; -fx-font-size: 13px; -fx-background-radius: 8;"
                    : "-fx-text-fill: #888780; -fx-font-size: 13px; -fx-background-radius: 8;");

            if (i == 0) {
                nav.setOnMouseClicked(e -> {
                    new CreatePackageScreen(packageService).start(stage);
                });
            }

            navList.getChildren().add(nav);
        }

        sidebar.getChildren().addAll(brand, navList);
        root.setLeft(sidebar);

        // ── MAIN CONTENT ──────────────────────────────────────
        VBox main = new VBox(24);
        main.setPadding(new Insets(40, 48, 40, 48));

        Label pageTitle = new Label("Package List");
        pageTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1C1B18;");

        Label pageSubtitle = new Label("All created shipments are listed below.");
        pageSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888780;");

        VBox header = new VBox(4, pageTitle, pageSubtitle);

        // ── TABLE ─────────────────────────────────────────────
        TableView<Package> table = new TableView<>();
        table.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #E0DED6;" +
                "-fx-border-width: 0.5;" +
                "-fx-border-radius: 12;"
        );
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Package, String> trackingCol = new TableColumn<>("Tracking No");
        trackingCol.setCellValueFactory(new PropertyValueFactory<>("trackingNumber"));
        trackingCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Package, String> typeCol = new TableColumn<>("Type");
        typeCol.setCellValueFactory(data -> {
            String type = data.getValue().getClass().getSimpleName().replace("Package", "");
            return new javafx.beans.property.SimpleStringProperty(type);
        });
        typeCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Package, String> senderCol = new TableColumn<>("Sender");
        senderCol.setCellValueFactory(new PropertyValueFactory<>("senderName"));

        TableColumn<Package, String> receiverCol = new TableColumn<>("Receiver");
        receiverCol.setCellValueFactory(new PropertyValueFactory<>("receiverName"));

        TableColumn<Package, String> priceCol = new TableColumn<>("Price ($)");
        priceCol.setCellValueFactory(data -> {
            String price = String.format("%.2f", data.getValue().calculatePrice());
            return new javafx.beans.property.SimpleStringProperty(price);
        });
        priceCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Package, String> deliveryCol = new TableColumn<>("Delivery (days)");
        deliveryCol.setCellValueFactory(data -> {
            String days = String.valueOf(data.getValue().getDeliveryDays());
            return new javafx.beans.property.SimpleStringProperty(days);
        });
        deliveryCol.setStyle("-fx-alignment: CENTER;");

        table.setOnMouseClicked(e -> {
            Package selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                new PackageDetailScreen(packageService, selected).show(stage);
            }
        });

        table.getColumns().addAll(trackingCol, typeCol, senderCol, receiverCol, priceCol, deliveryCol);
        table.getItems().addAll(packageService.getAllPackages());

        VBox.setVgrow(table, Priority.ALWAYS);
        main.getChildren().addAll(header, table);
        root.setCenter(main);

        Scene scene = new Scene(root, 860, 520);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}