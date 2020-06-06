/**
 * Minus Object.
 * extends binary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Minus extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(Expression leftSide, String rightSide) {
        super(leftSide, new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(Expression leftSide, double rightSide) {
        super(leftSide, new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(String leftSide, Expression rightSide) {
        super(new Var(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(String leftSide, String rightSide) {
        super(new Var(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(String leftSide, double rightSide) {
        super(new Var(leftSide), new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(double leftSide, Expression rightSide) {
        super(new Num(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(double leftSide, String rightSide) {
        super(new Num(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Minus
     * @param rightSide - the right Expression in Minus
     */
    public Minus(double leftSide, double rightSide) {
        super(new Num(leftSide), new Num(rightSide));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression left, Expression right) {
        //Return new Minus with the Expressions in it
        return new Minus(left, right);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @return - the value of the Expression
     */
    protected double calculate(double d1, double d2) {
        return d1 - d2;
    }

    /**
     * Return the symbol of Minus.
     * @return the string of the symbol
     */
    protected String symbol() {
        return " - ";
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunction(Expression leftSide, Expression rightSide) {
        //If both sides are equal return 0
        if (leftSide.toString().equals(rightSide.toString())) {
            return new Num(0);
        }
        //If the left side is 0 return negative right side
        if (leftSide.toString().equals("0.0")) {
            return new Neg(rightSide);
        }
        //If right side is 0 return the left side
        if (rightSide.toString().equals("0.0")) {
            return leftSide;
        }
        return new Minus(leftSide, rightSide);
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        //If both sides are equal return 0
        if (leftSide.toString().equals(rightSide.toString())) {
            return new Num(0);
        }
        if (leftSide instanceof BinaryExpression) {
            if (swap(leftSide).toString().equals(rightSide.toString())) {
                return new Num(0);
            }
        }
        if (rightSide instanceof BinaryExpression) {
            if (swap(rightSide).toString().equals(leftSide.toString())) {
                return new Num(0);
            }
        }
        //If the left side is 0 return negative right side
        if (leftSide.toString().equals("0.0")) {
            return new Neg(rightSide);
        }
        //If right side is 0 return the left side
        if (rightSide.toString().equals("0.0")) {
            return leftSide;
        }
        return new Minus(leftSide, rightSide);
    }
}
