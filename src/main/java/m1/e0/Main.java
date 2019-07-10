package m1.e0;

import m0.e2.GraphNode;
import m0.e2.XOField;
import m0.e3.GraphHelper;

public class Main {
    public static void main(String[] args) {
        final GraphNode root = new GraphBuilder().build(XOField.Figure.X, new XOField(), 0);
        System.out.println(root.getNode());
        System.out.println(GraphHelper.countNodes(root));
    }

}
