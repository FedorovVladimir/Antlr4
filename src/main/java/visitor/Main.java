package visitor;

import antlr.Java8Lexer;
import antlr.Java8Parser;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

public class Main {

    private static String code = "" +
            "class Main {" +
            "   public static void main(String[] args) {" +
            "       double f;" +
            "       while (f != 100) {" +
            "           System.out.println(f);" +
            "           f = f + 10;" +
            "       }" +
            "   }" +
            "}";

    public static void main(String[] args) {
        Java8Lexer lexer = new Java8Lexer(CharStreams.fromString(code));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        Java8Parser parser = new Java8Parser(tokens);
        ParseTree tree = parser.compilationUnit();
        new MyVisitor().visit(tree);
    }
}
