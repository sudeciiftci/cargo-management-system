import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CreatePackageScreen extends Application {

    private PackageService packageService;

    public CreatePackageScreen(PackageService packageService) {
        this.packageService = packageService;
    }

    private TextField senderField;
    private TextField receiverField;
    private TextField weightField;
    private TextField distanceField;
    private ComboBox<String> typeCombo;

    @Override
    public void start(Stage stage) {
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
            boolean active = i == 0;
            nav.setPrefWidth(Double.MAX_VALUE);
            nav.setPadding(new Insets(10, 14, 10, 14));
            nav.setStyle(active
                    ? "-fx-background-color: #2E2D29; -fx-text-fill: #FFFFFF; -fx-font-size: 13px; -fx-background-radius: 8;"
                    : "-fx-text-fill: #888780; -fx-font-size: 13px; -fx-background-radius: 8;");

            if (i == 1) {
                nav.setOnMouseClicked(e -> {
                    Stage listStage = new Stage();
                    new PackageListScreen(packageService).show(listStage);
                });
            }

            navList.getChildren().add(nav);
        }

        sidebar.getChildren().addAll(brand, navList);
        root.setLeft(sidebar);

        VBox main = new VBox(24);
        main.setPadding(new Insets(40, 48, 40, 48));

        Label pageTitle = new Label("Create New Package");
        pageTitle.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #1C1B18;");

        Label pageSubtitle = new Label("Fill in the form below to create a new shipment.");
        pageSubtitle.setStyle("-fx-font-size: 13px; -fx-text-fill: #888780;");

        VBox header = new VBox(4, pageTitle, pageSubtitle);

        VBox card = new VBox(20);
        card.setPadding(new Insets(28, 32, 28, 32));
        card.setStyle(
                "-fx-background-color: #FFFFFF;" +
                "-fx-background-radius: 12;" +
                "-fx-border-color: #E0DED6;" +
                "-fx-border-width: 0.5;" +
                "-fx-border-radius: 12;"
        );

        senderField   = buildField("Full Name");
        receiverField = buildField("Full Name");
        weightField   = buildField("0.00");
        distanceField = buildField("0");

        HBox row1 = new HBox(20);
        row1.getChildren().addAll(
                fieldGroup("Sender Name",   senderField),
                fieldGroup("Receiver Name", receiverField)
        );

        HBox row2 = new HBox(20);
        row2.getChildren().addAll(
                fieldGroup("Weight (kg)",   weightField),
                fieldGroup("Distance (km)", distanceField)
        );

        VBox typeGroup = new VBox(6);
        Label typeLabel = new Label("Package Type");
        typeLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #5F5E5A; -fx-font-weight: bold;");

        typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll("Standard Package", "Express Package", "Fragile Package");
        typeCombo.setPromptText("Select package type...");
        typeCombo.setPrefWidth(Double.MAX_VALUE);
        typeCombo.setPrefHeight(38);
        typeCombo.setStyle(
                "-fx-background-color: #F5F4F0;" +
                "-fx-border-color: #D3D1C7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #1C1B18;"
        );
        typeGroup.getChildren().addAll(typeLabel, typeCombo);

        Separator sep = new Separator();

        HBox buttons = new HBox(12);
        buttons.setAlignment(Pos.CENTER_RIGHT);

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefHeight(38);
        cancelBtn.setPadding(new Insets(0, 20, 0, 20));
        cancelBtn.setStyle(
                "-fx-background-color: transparent;" +
                "-fx-border-color: #D3D1C7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #5F5E5A;"
        );
        cancelBtn.setOnAction(e -> clearForm());

        Button createBtn = new Button("Create Package  \u2192");
        createBtn.setPrefHeight(38);
        createBtn.setPadding(new Insets(0, 20, 0, 20));
        createBtn.setStyle(
                "-fx-background-color: #1C1B18;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #FFFFFF;" +
                "-fx-font-weight: bold;"
        );
        createBtn.setOnAction(e -> handleCreate());

        buttons.getChildren().addAll(cancelBtn, createBtn);
        card.getChildren().addAll(row1, row2, typeGroup, sep, buttons);
        main.getChildren().addAll(header, card);
        root.setCenter(main);

        Scene scene = new Scene(root, 860, 520);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private void handleCreate() {
        try {
            String sender = senderField.getText().trim();
            String receiver = receiverField.getText().trim();
            double weight = Double.parseDouble(weightField.getText().trim());
            double distance = Double.parseDouble(distanceField.getText().trim());
            String type = typeCombo.getValue();

            if (type == null) {
                showAlert(Alert.AlertType.WARNING, "Please select a package type.");
                return;
            }

            boolean success = packageService.createPackage(sender, receiver, weight, distance, type);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Package created successfully!");
                clearForm();
            } else {
                showAlert(Alert.AlertType.WARNING, "Please fill in all fields correctly.");
            }

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Weight and distance must be valid numbers.");
        }
    }

    private void clearForm() {
        senderField.clear();
        receiverField.clear();
        weightField.clear();
        distanceField.clear();
        typeCombo.setValue(null);
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private TextField buildField(String placeholder) {
        TextField field = new TextField();
        field.setPromptText(placeholder);
        field.setPrefHeight(38);
        field.setStyle(
                "-fx-background-color: #F5F4F0;" +
                "-fx-border-color: #D3D1C7;" +
                "-fx-border-radius: 8;" +
                "-fx-background-radius: 8;" +
                "-fx-font-size: 13px;" +
                "-fx-text-fill: #1C1B18;" +
                "-fx-prompt-text-fill: #B4B2A9;"
        );
        return field;
    }

    private VBox fieldGroup(String label, TextField field) {
        VBox group = new VBox(6);
        group.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(group, Priority.ALWAYS);

        Label lbl = new Label(label);
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #5F5E5A; -fx-font-weight: bold;");

        group.getChildren().addAll(lbl, field);
        return group;
    }

    public static void main(String[] args) {
        launch(args);
    }
}