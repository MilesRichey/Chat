import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ChatApp {
    private static ChatClient cli;
    private static ListView<String> chatHistoryView = new ListView<>();
    private static ObservableList<String> chatHistory = FXCollections.observableArrayList();

    //https://docs.oracle.com/javafx/2/ui_controls/list-view.htm
    public static void updateChat(String msg) {
        Platform.runLater(() -> {
            chatHistory.add(msg);
            chatHistoryView.setItems(chatHistory);
        });
    }

    public static void start(String ip, String port, String username) {
        // Initialize the ChatClient
        cli = new ChatClient(ip, port, username);

        // Initialize the interface
        Stage chatStage = new Stage();
        GridPane grid = new GridPane();
        grid.prefWidthProperty().bind(chatStage.widthProperty());
        grid.prefHeightProperty().bind(chatStage.widthProperty());
        grid.setAlignment(Pos.BASELINE_LEFT);
        grid.setHgap(5);
        grid.setVgap(10);
        //grid.setPadding(new Insets(0, 0, 0, 0));
        chatHistoryView.prefWidthProperty().bind(chatStage.widthProperty());
        chatHistoryView.prefHeightProperty().bind(chatStage.heightProperty());
        chatHistoryView.setItems(chatHistory);
        /*chatHistory.addListener((ListChangeListener<String>) c ->
                chatHistoryView.scrollTo(chatHistory.size()-1));*/
        grid.add(chatHistoryView, 0, 0, 2, 1);

        Button btn = new Button("Send");
        TextField msgField = new TextField();
        msgField.setOnKeyReleased((e) -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                btn.fire();
            }
        });
        grid.add(msgField, 0, 1);

        // Connect Button
        btn.setOnAction((e) -> {
            //System.out.println(msgField.getText());
            String msg = msgField.getText();
            if (msg != null && msg.length() > 0) {
                cli.sendMessage(msgField.getText());
                msgField.setText("");
            }
        });
        grid.add(btn, 1, 1);

        Scene scene = new Scene(grid, 300, 275);
        chatStage.setTitle("Chat App");
        chatStage.setScene(scene);
        chatStage.setOnCloseRequest((e) -> cli.stop());
        chatStage.show();
    }
}
