package project;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ProjectController {
    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messageList;

    @FXML
    private void handleButtonClick() {
        String message = messageField.getText();

        if (!message.isEmpty()) {
            InputHandler.setField(messageList);
            InputHandler.handleInput(message);
            messageField.clear();
        }
    }

}
