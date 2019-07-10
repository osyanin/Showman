package m0.e2;

import java.util.Arrays;

public class XOField {

    private final Figure[][] figures;

    public XOField() {
        this.figures = new Figure[3][3];
    }

    public XOField(final XOField xoField) {
        figures = new Figure[3][3];
        for (int i = 0; i < 3; i++) {
            System.arraycopy(xoField.figures[i], 0, figures[i], 0, 3);
        }
    }

    public void setFigure(final int x, final  int y, final Figure figure) {
        figures[x][y] = figure;
    }

    public Figure getFigure(final int x, final int y) {
        return figures[x][y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        XOField xoField = (XOField) o;
        return Arrays.deepEquals(figures, xoField.figures);
    }

    @Override
    public int hashCode() { return Arrays.deepHashCode(figures); }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                sb.append(figures[x][y] != null ? figures[x][y] : " ");
                if (x != 2) {
                    sb.append("|");
                } else {
                    sb.append("\n");
                }
            }
            if (y != 2) {
                sb.append("-----\n");
            }
        }
        return sb.toString();
    }

    public enum Figure {
        X, O
    }
}
