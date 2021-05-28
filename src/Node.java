import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Node {
    List<Node> children = new ArrayList<>();
    private double f;
    private int g;
    private double h;
    private Integer[][] matrix = new Integer[3][3];

    public Node(int g, Integer[][] matrix, Integer[][] goalMatrix) {
        this.g = g;
        this.matrix = matrix;

        this.f = f(goalMatrix);
        this.h = h(goalMatrix);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        Node other = (Node) obj;
        return Arrays.deepEquals(matrix, other.matrix);
    }

    public List<Node> expand(Integer[][] goalMatrix) {
        SimpleEntry<Integer, Integer> cur = find();

        Integer[][] moves = { { cur.getKey(), cur.getValue() - 1 }, { cur.getKey(), cur.getValue() + 1 },
                { cur.getKey() - 1, cur.getValue() }, { cur.getKey() + 1, cur.getValue() } };

        for (Integer[] element : moves) {
            Integer[][] childMatrix = shuffle(cur.getKey(), cur.getValue(), element[0], element[1]);

            if (childMatrix != null) {
                Node child = new Node(g + 1, childMatrix, goalMatrix);

                children.add(child);
            }
        }

        return children;
    }

    public double f(Integer[][] goalMatrix) {
        return h2(goalMatrix) + g;
    }

    public SimpleEntry<Integer, Integer> find() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == null) {
                    return new SimpleEntry<>(i, j);
                }
            }
        }
        return null;
    }

    public double getF() {
        return f;
    }

    public int getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public int h(Integer[][] goalMatrix) {
        int temp = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if ((matrix[i][j] != goalMatrix[i][j]) && (matrix[i][j] != null)) {
                    temp += 1;
                }
            }

        }
        return temp;
    }

    public double h2(Integer[][] goalMatrix) {
        double temp = 0;
        for (int x1 = 0; x1 < 3; x1++) {
            for (int y1 = 0; y1 < 3; y1++) {
                for (int x2 = 0; x2 < 3; x2++) {
                    for (int y2 = 0; y2 < 3; y2++) {
                        if (matrix[x1][y1] == goalMatrix[x2][y2]) {
                            temp += Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
                            break;
                        }
                    }

                }
            }

        }

        return temp;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + Arrays.deepHashCode(matrix);
        return result;
    }

    private Integer[][] shuffle(int x1, int y1, int x2, int y2) {
        if ((x2 >= 0) && (x2 < 3) && (y2 >= 0) && (y2 < 3)) {
            Integer[][] tempMatrix = Arrays.stream(matrix).map((Integer[] row) -> row.clone())
                    .toArray((int length) -> new Integer[length][]);
            Integer temp = tempMatrix[x2][y2];
            tempMatrix[x2][y2] = tempMatrix[x1][y1];
            tempMatrix[x1][y1] = temp;
            return tempMatrix;
        }
        return null;
    }

    @Override
    public String toString() {
        String print = "|f = " + (Math.round(f * Math.pow(10, 2)) / Math.pow(10, 2)) + ", g = " + g + ", h = "
                + (Math.round(h * Math.pow(10, 2)) / Math.pow(10, 2)) + "|\n";

        int head = print.length();

        for (int i = 0; i < 3; i++) {
            String line = "|          ";
            for (int j = 0; j < 3; j++) {
                if (matrix[i][j] == null) {
                    line += "  ";
                } else {
                    line += matrix[i][j] + " ";
                }
            }
            print += line.substring(0, line.length() - 1);
            for (int j = 0; j < (head - line.length() - 1); j++) {
                print += " ";
            }
            print += "|\n";
        }
        print = print.substring(0, print.length() - 1);

        return print;
    }

}