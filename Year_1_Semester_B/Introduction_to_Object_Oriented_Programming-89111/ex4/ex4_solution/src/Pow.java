/**
 * Pow Object.
 * extends binary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Pow extends BinaryExpression implements Expression {

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(Expression leftSide, Expression rightSide) {
        super(leftSide, rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(Expression leftSide, String rightSide) {
        super(leftSide, new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(Expression leftSide, double rightSide) {
        super(leftSide, new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(String leftSide, Expression rightSide) {
        super(new Var(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(String leftSide, String rightSide) {
        super(new Var(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(String leftSide, double rightSide) {
        super(new Var(leftSide), new Num(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(double leftSide, Expression rightSide) {
        super(new Num(leftSide), rightSide);
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(double leftSide, String rightSide) {
        super(new Num(leftSide), new Var(rightSide));
    }

    /**
     * Constructor.
     * @param leftSide - the left Expression in Pow
     * @param rightSide - the right Expression in Pow
     */
    public Pow(double leftSide, double rightSide) {
        super(new Num(leftSide), new Num(rightSide));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression left, Expression right) {
        //Return new Pow with the Expressions in it
        return new Pow(left, right);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @throws Exception -  if the resule is NaN or Infinite throw exception error
     * @return - the value of the Expression
     */
    protected double calculate(double d1, double d2) throws Exception {
        if (d1 == 0 && d2 == 0) {
            throw new Exception("Pow invalid number");
        }
        if (Double.isNaN(Math.pow(d1, d2))) {
            throw new Exception("Pow invalid number");
        }
          if (Double.isInfinite((Math.pow(d1, d2)))) {
                throw new Exception("Pow Infinite result");
            }
        return Math.pow(d1, d2);
    }

    /**
    * Return the symbol of Pow.
    * @return the string of the symbol
    */
    protected String symbol() {
        return "^";
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
        //Return the diff by the pow rule
        return new Mult(
                new Pow(
                        left, right), new Plus(
                                new Mult(
                                        leftD, new Div(
                                                right, left)), new Mult(
                                                        rightD, new Log(
                                                                "e", left))));
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunction(Expression leftSide, Expression rightSide) {
        //There is no simplify rule for pow
        return new Pow(leftSide, rightSide);
    }

    /**
     * Simplify the Expression by rules for each Expression type.
     * @param leftSide - the left Expression of the plus
     * @param rightSide - the right Expression of the plus
     * @return the simplified Expression
     */
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        //If the left side is 0 return negative right side
        if (rightSide.toString().equals("0.0")) {
            return new Num(1);
        }
        //If right side is 0 return the left side
        if (rightSide.toString().equals("1.0")) {
            return leftSide;
        }
        //If the power is power to - x^y^z = x^y*z
        if (rightSide instanceof Pow) {
            return new Pow(leftSide, new Mult(((Pow) rightSide).getRight(), ((BaseExpression) rightSide).getLeft()));
        }
        return new Pow(leftSide, rightSide);
    }

}
