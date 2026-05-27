import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private PackageService packageService;

    @Override
    public void start(Stage primaryStage) {

        packageService = new PackageService();

        CreatePackageScreen screen = new CreatePackageScreen(packageService);
        screen.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}