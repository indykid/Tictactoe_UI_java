package kg.jarkyn.GUI;

import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;
import kg.jarkyn.Core.GameOption;
import kg.jarkyn.GUI.ViewComponents.GameSelectionButton;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GraphicalUITest {
    @Test
    public void displaysGameSelector() {
        new JFXPanel();
        GraphicalUI ui = new GraphicalUI();

        ui.displayGameSelector();

        List<Node> controls = getChildren(ui);
        assertEquals(3, controls.size());
        assertThat(((GameSelectionButton) controls.get(0)).getGameOption(), is(GameOption.AI_FIRST));
        assertThat(((GameSelectionButton) controls.get(1)).getGameOption(), is(GameOption.AI_SECOND));
        assertThat(((GameSelectionButton) controls.get(2)).getGameOption(), is(GameOption.HUMAN_ONLY));

    }

    private List<Node> getChildren(GraphicalUI ui) {
        return ui.getScene().getRoot().getChildrenUnmodifiable();
    }
}