import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SplashScreen extends Application {
    private static String[] argz;

    public static void main(String[] args) {
        argz = args;
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        Button btn = new Button("Connect");
        EventHandler<KeyEvent> onEnter = (KeyEvent e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                btn.fire();
            }
        };

        // Welcome header text
        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        // IP Field
        Label ipLabel = new Label("IP:");
        grid.add(ipLabel, 0, 1);

        TextField ipField = new TextField();
        ipField.setPromptText("127.0.0.1:8217");
        ipField.setOnKeyReleased(onEnter);
        grid.add(ipField, 1, 1);

        //Username Field
        Label userLabel = new Label("Username:");
        grid.add(userLabel, 0, 2);

        TextField userField = new TextField();
        userField.setPromptText("Default User");
        userField.setOnKeyReleased(onEnter);
        grid.add(userField, 1, 2);

        // Connect Button
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction((e) -> {
            primaryStage.hide();
            String[] ipSplit = ipField.getText().split(":");
            // Default: localhost:8817, Default User
            String ip = ipSplit[0].length() == 0 ? "127.0.0.1" : ipSplit[0];
            String port = ipSplit.length > 1 ? ipField.getText().split(":")[1] : "8817";
            String user = userField.getText();
            user = user == null || user.length() == 0 ? "Default User" : user;
            System.out.printf("{'ip':'%s', 'port':'%s', 'user':'%s'}\n", ip, port, user);
            ChatApp.start(ip, port, user);
        });
        grid.add(hbBtn, 1, 4);

        Scene scene = new Scene(grid, 300, 275);
        primaryStage.setTitle("Chat App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
