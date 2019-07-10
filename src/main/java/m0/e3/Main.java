package m0.e3;

import m0.e2.GraphNode;
import m0.e2.XOField;

public class Main {
    public static void main(String[] args) {
        final GraphNode root = new GraphBuilder().build(XOField.Figure.X, new XOField());
        System.out.println(root.getNode());
        GraphHelper.show(root, 0);
        System.out.println(GraphHelper.countNodes(root));
    }
}
