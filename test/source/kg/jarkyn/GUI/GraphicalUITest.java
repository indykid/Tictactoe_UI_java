package kg.jarkyn.GUI;

import javafx.embed.swing.JFXPanel;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import kg.jarkyn.Core.*;
import kg.jarkyn.GUI.ViewComponents.GameSelectionButton;
import kg.jarkyn.GUI.ViewComponents.GridCell;
import kg.jarkyn.GUI.ViewComponents.MainPane;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class GraphicalUITest {

    private Scene scene;
    private GraphicalUI ui;

    @Before
    public void setUp() throws Exception {
        scene = new Scene(new MainPane());
        ui = new GraphicalUI(scene);
    }

    @Test
    public void listenersAreSetOnGameSelectionButtons() {
        setupJFXEnvironment();

        ui.displayGameSelector();

        assertTrue(clickListenersAreSet(getChildren(scene)));
    }

    @Test
    public void setsUpGameOnSelection() {
        setupJFXEnvironment();
        ui.displayGameSelector();

        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertTrue(isGamePresent(ui));
    }

    @Test
    public void setsUpAiFirstGame() {
        setupJFXEnvironment();
        ui.displayGameSelector();
        Node aiFirstButton = findButton(scene, GameOption.AI_FIRST);

        aiFirstButton.fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertTrue(getCurrentPlayer(ui) instanceof AiPlayer);
    }

    @Test
    public void playsClickedPosition() {
        setupJFXEnvironment();
        Game game = GameMaker.makeGame(GameOption.HUMAN_ONLY, ui);
        ui.setGame(game);
        ui.displayBoard();

        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertEquals(Mark.X, game.getBoard().markAt(0));
    }

    @Test
    public void drawsMarkToTheClickedCell() {
        setupJFXEnvironment();
        ui.setGame(GameMaker.makeGame(GameOption.HUMAN_ONLY, ui));
        ui.displayBoard();

        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertEquals("X", ((GridCell) getFirstChild()).getText());
    }

    @Test
    public void doesNotSetListenerIfCellIsPlayed() {
        setupJFXEnvironment();
        ui.setGame(GameMaker.makeGame(GameOption.HUMAN_ONLY, ui));
        ui.displayBoard();

        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertNull(getFirstChild().onMouseClickedProperty().getValue());
    }

    @Test
    public void nothingHappensWhenClickingIntoPlayedCell() {
        setupJFXEnvironment();
        Game game = GameMaker.makeGame(GameOption.HUMAN_ONLY, ui);
        ui.setGame(game);
        ui.displayBoard();

        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));
        getFirstChild().fireEvent(new Event(MouseEvent.MOUSE_CLICKED));

        assertEquals(Mark.X, game.getBoard().markAt(0));
    }

    private void setupJFXEnvironment() {
        new JFXPanel();
    }

    private boolean clickListenersAreSet(List<Node> elements) {
        for (Node element : elements) {
            if (element.onMouseClickedProperty().getValue() == null) {
                return false;
            }
        }
        return true;
    }

    private Player getCurrentPlayer(GraphicalUI ui) {
        return ui.getGame().getCurrentPlayer();
    }

    private Node findButton(Scene scene, GameOption option) {
        for (Node button : getChildren(scene)) {
            if (((GameSelectionButton) button).getGameOption() == option) {
                return button;
            }
        }
        return null;
    }

    private boolean isGamePresent(GraphicalUI ui) {
        return ui.getGame() != null;
    }

    private List<Node> getChildren(Scene scene) {
        return scene.getRoot().getChildrenUnmodifiable();
    }

    private Node getFirstChild() {
        return getChildren(scene).get(0);
    }
}