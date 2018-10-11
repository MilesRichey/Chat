import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChatApp {
    //https://docs.oracle.com/javafx/2/ui_controls/list-view.htm
    public static void start(String ip, String port, String username) {
        Stage chatStage = new Stage();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));


        TextField msgField = new TextField();
        grid.add(msgField, 0, 2);

        // Connect Button
        Button btn = new Button("Connect");
        btn.setOnAction((e) -> {
            System.out.println(msgField.getText());
        });
        grid.add(btn, 1, 2);

        Scene scene = new Scene(grid, 300, 275);
        chatStage.setTitle("Chat App");
        chatStage.setScene(scene);
        chatStage.show();
    }
}
