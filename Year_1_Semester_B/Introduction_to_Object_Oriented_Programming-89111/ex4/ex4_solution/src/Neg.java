/**
 * Neg Object.
 * extends Unary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Neg extends UnaryExpression implements Expression {

    /**
     * Constructor.
     * @param main - the Expression in neg
     */
    public Neg(Expression main) {
        super(main);
    }

    /**
     * Constructor.
     * @param num - the double that is Num Expression in neg
     */
    public Neg(double num) {
        super(new Num(num));
    }

    /**
     * Constructor.
     * @param var - the string that is Var Expression in neg
     */
    public Neg(String var) {
        super(new Var(var));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param main - the left Expression
     * @param nothing - the right Expression - in unary is nothing
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression main, Expression nothing) {
      //Return new neg with the main Expression in it
        return new Neg(main);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param mainValue - first double (the left one)
     * @param nothing - second double (the right one) - in unary its nothing
     * @return - the value of the Expression
     */
    protected double calculate(double mainValue, double nothing) {
        //Return the negative of the mainValue
        return mainValue * -1;
    }

    /**
     * Abstract function - implement - Get the starting of the Expression.
     * @return string of the starting Expression
     */
    protected String startString() {
        return "";
    }

    /**
     * Return the symbol of negative "-".
     * @return the string of the symbol
     */
    @Override
    protected String symbol() {
        return "-";
    }

    /**
     * Abstract function - to calculate the differentiate of each Expression type.
     * for plus and minus just return the different , the other are override.
     * @param leftD - the differentiate of the left Expression
     * @param nothingD - the differentiate of the right Expression - in unary is nothing
     * @param left - the left Expression
     * @param nothing - the right Expression - in unary is nothing
     * @return - the differentiate of the Expression
     */
    protected Expression dCal(Expression leftD, Expression nothingD, Expression left, Expression nothing) {
        return new Neg(leftD);
    }

    //Bonus
    @Override
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        //If there is number in neg and it negative
        if (leftSide.getVariables().isEmpty()) {
            try {
                if (leftSide.evaluate() < 0) {
                    return new Num(leftSide.evaluate() * -1);
                }
            } catch (Exception e) {
                //we dont do nothing - but check style forces to write statment
                System.out.print("");
            }
        }
        //if there is double negative
        if (leftSide instanceof Neg) {
            return leftSide;
        }
        return type(leftSide.simplify(), new Num(0));
    }
}
