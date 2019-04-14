package visitor;

public class Node {

    private int typeLexeme;
    private String text;

    public Node(int typeLexeme) {
        this.typeLexeme = typeLexeme;
    }

    public Node(int type, String text) {
        this.typeLexeme = type;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Node " + text;
    }

    public String getText() {
        return text;
    }

    public Node add(Node node) {
        Double a = Double.parseDouble(text);
        Double b = Double.parseDouble(node.getText());
        return new Node(typeLexeme, String.valueOf(a + b));
    }

    public Node sub(Node node) {
        Double a = Double.parseDouble(text);
        Double b = Double.parseDouble(node.getText());
        return new Node(typeLexeme, String.valueOf(a - b));
    }

    public Node mul(Node node) {
        Double a = Double.parseDouble(text);
        Double b = Double.parseDouble(node.getText());
        return new Node(typeLexeme, String.valueOf(a * b));
    }

    public Node div(Node node) {
        Double a = Double.parseDouble(text);
        Double b = Double.parseDouble(node.getText());
        return new Node(typeLexeme, String.valueOf(a / b));
    }
}
