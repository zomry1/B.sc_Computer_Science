/**
 * Log Object.
 * extends Unary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Log extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(Expression leftSide, String rightSide) {
        super(leftSide, new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(Expression leftSide, double rightSide) {
        super(leftSide, new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(String leftSide, Expression rightSide) {
        super(new Var(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(String leftSide, String rightSide) {
        super(new Var(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(String leftSide, double rightSide) {
        super(new Var(leftSide), new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(double leftSide, Expression rightSide) {
        super(new Num(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(double leftSide, String rightSide) {
        super(new Num(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Log
     * @param rightSide - the right Expression in Log
     */
    public Log(double leftSide, double rightSide) {
        super(new Num(leftSide), new Num(rightSide));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression left, Expression right) {
        //Return new Log with the Expressions in it
        return new Log(left, right);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @throws Exception - if the resule is NaN or Infinite throw exception error
     * @return - the value of the Expression
     */
    protected double calculate(double d1, double d2) throws Exception {
        if (d1 <= 0 || d1 == 1) {
            throw new Exception("Log invaild base");
        }
        if (d2 <= 0) {
            throw new Exception("Log invaild number");
        }
        if (Double.isInfinite((Math.log(d2)) / Math.log(d1))) {
            throw new Exception("Log infinite number");
        }
        if (Double.isNaN((Math.log(d2)) / Math.log(d1))) {
            throw new Exception("Log base invalid number");
        }
        return Math.log(d2) / Math.log(d1);
    }

    /**
    * Return the symbol of Log.
    * @return the string of the symbol
    */
    protected String symbol() {
        return ", ";
    }

    /**
     * Abstract function - implement - Get the starting of the Expression.
     * @return string of the starting Expression
     */
    @Override
    protected String startString() {
        return "log";
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
        //Return the diff by log rule
        Mult bottom = new Mult(right, new Log("e", left));
        return new Div(rightD, bottom);
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunction(Expression leftSide, Expression rightSide) {
        //If both sides are equal return 1
        if (leftSide.toString().equals(rightSide.toString())) {
            return new Num(1);
        }
        return new Log(leftSide, rightSide);
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        //If both sides are equal return 1
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
        return new Log(leftSide, rightSide);
    }

}
