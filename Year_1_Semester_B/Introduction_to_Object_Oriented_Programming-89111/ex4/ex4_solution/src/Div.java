/**
 * Div Object.
 * extends binary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Div extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(Expression leftSide, String rightSide) {
        super(leftSide, new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(Expression leftSide, double rightSide) {
        super(leftSide, new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(String leftSide, Expression rightSide) {
        super(new Var(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(String leftSide, String rightSide) {
        super(new Var(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(String leftSide, double rightSide) {
        super(new Var(leftSide), new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(double leftSide, Expression rightSide) {
        super(new Num(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(double leftSide, String rightSide) {
        super(new Num(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Div
     * @param rightSide - the right Expression in Div
     */
    public Div(double leftSide, double rightSide) {
        super(new Num(leftSide), new Num(rightSide));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression left, Expression right) {
        //Return new Div with the Expressions in it
        return new Div(left, right);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @throws Exception - if the right double is 0 throw exception error
     * @return - the value of the Expression
     */
    protected double calculate(double d1, double d2) throws Exception {
        if (d2 == 0) {
            throw new Exception("Divide by 0");
        }
        return d1 / d2;
    }

     /**
     * Return the symbol of Div.
     * @return the string of the symbol
     */
    protected String symbol() {
        return " / ";
    }

    /**
     * Abstract function - implement -  to calculate the differentiate of each Expression type.
     * for plus and minus just return the different , the other are override.
     * @param leftD - the differentiate of the left Expression
     * @param rightD - the differentiate of the right Expression
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the differentiate of the Expression
     */
    @Override
    protected Expression dCal(Expression leftD, Expression rightD, Expression left, Expression right) {
        //Return the diff by the divide rule
        return new Div(
                new Minus(
                        new Mult(
                                leftD, right), new Mult(
                                        left, rightD)), new Pow(
                                                right, 2));
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunction(Expression leftSide, Expression rightSide) {
        //If both Sides are equals return 1
        if (leftSide.toString().equals(rightSide.toString())) {
            return new Num(1);
        }
        //If the right side is 1 return the left side
        if (rightSide.toString().equals("1.0")) {
            return leftSide;
        }
        return new Div(leftSide, rightSide);
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        //If left side is 0 - 0/x
        if (leftSide.toString().equals("0.0")) {
            return new Num(0);
        }
        //If both Sides are equals return 1
        if (leftSide.toString().equals(rightSide.toString())) {
            return new Num(1);
        }
        if (leftSide instanceof BinaryExpression) {
            if (swap(leftSide).toString().equals(rightSide.toString())) {
                return new Num(1);
            }
        }
        if (rightSide instanceof BinaryExpression) {
            if (swap(rightSide).toString().equals(leftSide.toString())) {
                return new Num(1);
            }
        }
        //If the right side is 1 return the left side
        if (rightSide.toString().equals("1.0")) {
            return leftSide;
        }
        return new Div(leftSide, rightSide);
    }

}
