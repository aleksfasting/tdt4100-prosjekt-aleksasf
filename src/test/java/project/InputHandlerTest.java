package project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javafx.scene.control.ListView;

public class InputHandlerTest {
    private ListView<String> field;

    @BeforeEach
    public void setup() {
        field = new ListView<>();
        InputHandler.setField(field);
    }

    @Test
    @DisplayName("test sendMessage")
    public void testSendMessage() {
        InputHandler.sendMessage("test");
        Assertions.assertEquals("You: test", field.getItems().get(field.getItems().size() - 1));
    }

    @Test
    @DisplayName("test connect")
    public void testConnect() {
        InputHandler.connect(":connect localhost");
        Assertions.assertEquals("localhost", InputHandler.getAddress());
}
