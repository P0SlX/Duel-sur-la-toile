import javafx.scene.shape.Circle;
import util.Pair;

public class FourInARowCircle extends Circle {

    private Pair<Integer, Integer> coords;

    public FourInARowCircle(int x, int y) {
        super();
        this.coords = new Pair<>(x, y);
    }

    public FourInARowCircle() {
        super();
    }

    public Pair<Integer, Integer> getCoords() {
        return coords;
    }

    public void setCoords(Pair<Integer, Integer> coords) {
        this.coords = coords;
    }
}
