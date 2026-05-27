import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class PackageDetailScreen {

    private final PackageService packageService;
    private final Package pkg;

    public PackageDetailScreen(PackageService packageService, Package pkg) {
        this.packageService = packageService;
        this.pkg = pkg;
    }

    public void show(Stage stage) {
        stage.setTitle("Cargo Management System");

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #F5F4F0;");

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
            boolean active = i == 2;
            nav.setPrefWidth(Double.MAX_VALUE);
            nav.setPadding(new Insets(10, 14, 10, 14));
            nav.setStyle(active
                    ? "-fx-background-color: #2E2D29; -fx-text-fill: #FFFFFF; -fx-font-size: 13px; -fx-background-radius: 8;"
                    : "-fx-text-fill: #888780; -fx-font-size: 13px; -fx-background-radius: 8;");

            if (i == 0) {
                nav.setOnMouseClicked(e -> new CreatePackageScreen(packageService).start(stage));
            }
            if (i == 1) {
                nav.setOnMouseClicked(e -> new PackageListScreen(packageService).show(stage));
            }

            navList.getChildren().add(nav);
        }

        sidebar.getChildren().addAll(brand, navList);
        root.setLeft(sidebar);

        VBox main = new VBox(24);
        main.setPadding(new Insets(40, 48, 40, 48));

        Label pageTitle = new Label("Package Details");
        pageTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1C1B18;");

        Label pageSubtitle = new Label("Full information about the selected shipment.");
        pageSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888780;");

        VBox header = new VBox(4, pageTitle, pageSubtitle);

        VBox card = new VBox(0);
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #E0DED6;" +
                "-fx-border-width: 0.5;" +
                "-fx-border-radius: 12;"
        );

        String type = pkg.getClass().getSimpleName().replace("Package", "") + " Package";
        String price = String.format("$%.2f", pkg.calculatePrice());
        String delivery = pkg.getDeliveryDays() + " days";

        String[][] rows = {
            {"Tracking Number", pkg.getTrackingNumber()},
            {"Package Type",    type},
            {"Sender",          pkg.getSenderName()},
            {"Receiver",        pkg.getReceiverName()},
            {"Weight",          pkg.getWeight() + " kg"},
            {"Distance",        pkg.getDistance() + " km"},
            {"Price",           price},
            {"Delivery Time",   delivery},
        };

        for (int i = 0; i < rows.length; i++) {
            HBox row = new HBox();
            row.setPadding(new Insets(14, 24, 14, 24));
            row.setAlignment(Pos.CENTER_LEFT);
            row.setStyle(i % 2 == 0
                    ? "-fx-background-color: #FFFFFF;"
                    : "-fx-background-color: #F9F8F5;");

            Label key = new Label(rows[i][0]);
            key.setPrefWidth(180);
            key.setStyle("-fx-font-size: 13px; -fx-text-fill: #888780;");

            Label value = new Label(rows[i][1]);
            value.setStyle("-fx-font-size: 13px; -fx-text-fill: #1C1B18; -fx-font-weight: bold;");

            row.getChildren().addAll(key, value);

            if (i == 0) {
                row.setStyle(row.getStyle() + "-fx-background-radius: 12 12 0 0;");
            }
            if (i == rows.length - 1) {
                row.setStyle(row.getStyle() + "-fx-background-radius: 0 0 12 12;");
            }

            card.getChildren().add(row);

            if (i < rows.length - 1) {
                Separator sep = new Separator();
                sep.setStyle("-fx-background-color: #F0EEE8;");
                card.getChildren().add(sep);
            }
        }

        main.getChildren().addAll(header, card);
        root.setCenter(main);

        Scene scene = new Scene(root, 860, 520);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}