package project;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class InputHandlerTest {
    private ArrayList<String> field;

    @BeforeEach
    public void setup() {
        field = new ArrayList<>();
        InputHandler.setField(field);
    }

    @Test
    @DisplayName("test message")
    public void testMessage() {
        InputHandler.handleInput("test");
        Assertions.assertEquals("You: test", InputHandler.getLastMessage());
    }

    @Test
    @DisplayName("test connect")
    public void testConnect() {
        InputHandler.handleInput(":connect localhost");
        Assertions.assertEquals("localhost/127.0.0.1", InputHandler.getAddress());
    }

    @Test
    @DisplayName("test save and load")
    public void testSaveLoad() {
        InputHandler.handleInput("testing Save");
        InputHandler.handleInput(":save testFile");
        InputHandler.handleInput(":clear");
        InputHandler.handleInput(":load testFile");
        Assertions.assertEquals("You: testing Save", InputHandler.getLastMessage());
    }

    @Test
    @DisplayName("test file exists")
    public void testFileExists() {
        InputHandler.handleInput("testing Save2");
        InputHandler.handleInput(":save testFile2");
        InputHandler.handleInput("testing Save3");
        InputHandler.handleInput(":save testFile2");
        Assertions.assertEquals("*** File already exists", InputHandler.getLastMessage());
    }

    @Test
    @DisplayName("test clear")
    public void testClear() {
        InputHandler.handleInput("testing Clear");
        InputHandler.handleInput(":clear");
        Assertions.assertEquals(0, field.size());
    }

    @Test
    @DisplayName("test Invalid command")
    public void testInvalidCommand() {
        InputHandler.handleInput(":notacommand");
        Assertions.assertEquals("*** Unknown command: :notacommand", InputHandler.getLastMessage());
    }

    @Test
    @DisplayName("test start")
    public void testStart() {
        InputHandler.handleInput(":start");
        InputHandler.handleInput(":start");
        Assertions.assertEquals("*** Receiver already started", InputHandler.getLastMessage());
    }
}
