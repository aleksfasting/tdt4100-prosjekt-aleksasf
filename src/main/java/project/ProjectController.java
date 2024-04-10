package project;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ProjectController {
    @FXML
    private TextField messageField;

    @FXML
    private ListView<String> messageList;

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleButtonClick();
        }
    }

    @FXML
    private void handleButtonClick() {
        String message = messageField.getText();

        if (!message.isEmpty()) {
            InputHandler.setField(messageList);
            InputHandler.handleInput(message);
            messageField.clear();
            messageList.scrollTo(messageList.getItems().size() - 1);
        }
    }

}
