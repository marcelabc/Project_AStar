import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Puzzle_8Numbers {

    private static Set<Node> close = new LinkedHashSet<>();
    private static Set<Node> open = new LinkedHashSet<>();

    public static void main(String[] args) {
        Integer[][] startMatrix = { { 1, 8, 2 }, { null, 4, 3 }, { 7, 6, 5 } };
        Integer[][] goalMatrix = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, null } };

        Node startNode = new Node(0, startMatrix, goalMatrix);

        open.add(startNode);
        int i = 0;
        while (true) {
            System.out.println("Interaction " + i++);
            Node cur = open.stream().findFirst().get();

            System.out.println("\nCurrent");
            System.out.println(cur);

            if (cur.h2(goalMatrix) == 0) {
                break;
            }

            System.out.println("\nExpand");

            System.out.println(printColumns(cur.expand(goalMatrix).stream().map(Node::toString)
                    .collect(Collectors.toList()).toArray(new String[] {})));

            for (Node expand : cur.expand(goalMatrix)) {
                if (!close.contains(expand)) {
                    open.add(expand);
                }
            }

            close.add(cur);

            open.remove(cur);

            open = open.stream()
                    .sorted(Comparator.comparingDouble(Node::getF).thenComparing(Comparator.comparingDouble(Node::getH))
                            .thenComparing(Comparator.comparingInt(Node::getG)))
                    .collect(Collectors.toCollection(LinkedHashSet::new));

            System.out.println("Frontier");

            System.out.println(printColumns(
                    open.stream().map(Node::toString).collect(Collectors.toList()).toArray(new String[] {})));
        }
    }

    public static String printColumns(String[] input) {
        String result = "";

        String[][] columns = new String[input.length][];
        int maxLines = 0;
        for (int i = 0; i < input.length; i++) {
            columns[i] = input[i].split("\n");
            if (columns[i].length > maxLines) {
                maxLines = columns[i].length;
            }
        }

        // Store an array of column widths
        int[] widths = new int[input.length];
        // calculate column widths
        for (int i = 0; i < input.length; i++) {
            int maxWidth = 0;
            for (int j = 0; j < columns[i].length; j++) {
                if (columns[i][j].length() > maxWidth) {
                    maxWidth = columns[i][j].length();
                }
            }
            widths[i] = maxWidth + 1;
        }

        // "Print" all lines
        for (int line = 0; line < maxLines; line++) {
            for (int column = 0; column < columns.length; column++) {
                String s = line < columns[column].length ? columns[column][line] : "";
                result += String.format("%-" + widths[column] + "s", s);
            }
            result += "\n";
        }
        return result;
    }
}
