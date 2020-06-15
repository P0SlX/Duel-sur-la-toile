import javafx.scene.control.Button;
import util.Pair;

public class FourInARowButton extends Button {

    private Pair<Integer, Integer> coords;

    public FourInARowButton(int x, int y) {
        super();
        this.coords = new Pair<>(x, y);
    }

    public FourInARowButton() {
        super();
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }
}
