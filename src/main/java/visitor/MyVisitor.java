package visitor;

import antlr.Java8BaseVisitor;
import antlr.Java8Parser;

import java.util.HashMap;
import java.util.Map;

import static antlr.Java8Parser.DOUBLE;
import static antlr.Java8Parser.VOID;

public class MyVisitor extends Java8BaseVisitor<Node> {

    private Map<String, Node> vars = new HashMap<String, Node>();

    @Override
    public Node visitMethodInvocation(Java8Parser.MethodInvocationContext ctx) {
        System.out.println(super.visit(ctx.getChild(4)).getText());
        return new Node(VOID);
    }

    @Override
    public Node visitLiteral(Java8Parser.LiteralContext ctx) {
        Node node = new Node(DOUBLE, ctx.getText());
        return node;
    }

    @Override
    public Node visitAdditiveExpression(Java8Parser.AdditiveExpressionContext ctx) {
        if (ctx.getChildCount() == 1) {
            return super.visit(ctx.getChild(0));
        } else {
            Node a = super.visit(ctx.getChild(0));
            Node b = super.visit(ctx.getChild(2));
            if (ctx.getChild(1).getText().equals("+")) {
                return a.add(b);
            } else {
                return a.sub(b);
            }
        }
    }

    @Override
    public Node visitMultiplicativeExpression(Java8Parser.MultiplicativeExpressionContext ctx) {
        if (ctx.getChildCount() == 1) {
            return super.visit(ctx.getChild(0));
        } else {
            Node a = super.visit(ctx.getChild(0));
            Node b = super.visit(ctx.getChild(2));
            if (ctx.getChild(1).getText().equals("*")) {
                return a.mul(b);
            } else {
                return a.div(b);
            }
        }
    }

    @Override
    public Node visitLocalVariableDeclaration(Java8Parser.LocalVariableDeclarationContext ctx) {
        for (int i = 0; i < ctx.getChild(1).getChildCount(); i += 2) {
            String value = "0";
            String name = ctx.getChild(1).getChild(i).getChild(0).getText();
            if (ctx.getChild(1).getChild(i).getChildCount() > 1) {
                value = super.visit(ctx.getChild(1).getChild(i).getChild(2)).getText();
            }
            Node node = new Node(DOUBLE, value);
            if (vars.get(name) == null) {
                vars.put(name, node);
            } else {
                System.out.println("Переменная " + name + " уже определена!");
            }
        }
        return new Node(VOID);
    }

    @Override
    public Node visitExpressionName(Java8Parser.ExpressionNameContext ctx) {
        String name = ctx.getChild(0).getText();
        Node node = vars.get(name);
        if (node == null) {
            System.out.println("Переменная " + name + " не определена!");
        }
        return node;
    }

    @Override
    public Node visitRelationalExpression(Java8Parser.RelationalExpressionContext ctx) {
        Node trueNode = new Node(DOUBLE, "1");
        Node falseNode = new Node(DOUBLE, "0");
        if (ctx.getChildCount() > 1) {
            if (ctx.getChild(1).getText().equals("<=")) {
                if (super.visit(ctx.getChild(0)).lessEqual(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            } else if (ctx.getChild(1).getText().equals(">=")) {
                if (super.visit(ctx.getChild(0)).largerEqual(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            } else if (ctx.getChild(1).getText().equals(">")) {
                if (super.visit(ctx.getChild(0)).larger(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            } else if (ctx.getChild(1).getText().equals("<")) {
                if (super.visit(ctx.getChild(0)).less(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            }
        }
        return super.visitRelationalExpression(ctx);
    }

    @Override
    public Node visitAssignment(Java8Parser.AssignmentContext ctx) {
        String name = ctx.getChild(0).getText();
        String value = super.visit(ctx.getChild(2)).getText();
        Node node = new Node(DOUBLE, value);
        vars.put(name, node);
        return node;
    }

    @Override
    public Node visitWhileStatement(Java8Parser.WhileStatementContext ctx) {
        while (Double.parseDouble(super.visit(ctx.getChild(2)).getText()) != 0) {
            super.visit(ctx.getChild(4));
        }
        return new Node(VOID);
    }

    @Override
    public Node visitEqualityExpression(Java8Parser.EqualityExpressionContext ctx) {
        Node trueNode = new Node(DOUBLE, "1");
        Node falseNode = new Node(DOUBLE, "0");
        if (ctx.getChildCount() > 1) {
            if (ctx.getChild(1).getText().equals("==")) {
                if (super.visit(ctx.getChild(0)).equal(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            } else if (ctx.getChild(1).getText().equals("!=")) {
                if (super.visit(ctx.getChild(0)).notEqual(super.visit(ctx.getChild(2)))) {
                    return trueNode;
                } else {
                    return falseNode;
                }
            }
        }
        return super.visitEqualityExpression(ctx);
    }
}
