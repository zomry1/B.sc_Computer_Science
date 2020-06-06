import javax.management.RuntimeErrorException;

/**
 * Abstract class - for unary Expression (Cos,Neg,Sin).
 * Extend the Base Expression
 * Implements Expression
 * @author Omry
 *
 */
public abstract class UnaryExpression extends BaseExpression implements Expression {

    /**
     * Constructor for unary Expression.
     * @param main - the expression (in the expression)
     */
    protected UnaryExpression(Expression main) {
        super(main);
    }

    /**
     * Abstract function - each Expression type get 2 doubles and evaluate the Expression.
     * @param mainValue - first double (the left one)
     * @param nothing - second double (the right one) - in unary its nothing
     * @return - the value of the Expression
     * @throws Exception - if there is an error in the calculation
     */
    protected abstract double calculate(double mainValue, double nothing) throws Exception;

     /**
     * Abstract function -Get the starting of the Expression.
     * @return string of the starting Expression
     */
    protected abstract String startString();

    /**
     * Get the symbol of the Expression.
     * in unary Expression its ""
     * @return string of the symbol
     */
    protected String symbol() {
        return "";
    }

    /**
     * Abstract function - Get 2 Expression and return the same Expression type of them (the method called).
     * @param expression - the left Expression
     * @param nothing - the right Expression - in unary is nothing
     * @return - the new Expression with the Expression in it
     */
    protected abstract Expression type(Expression expression, Expression nothing);

    /**
     * Abstract function - to calculate the differentiate of each Expression type.
     * for plus and minus just return the different , the other are override.
     * @param leftD - the differentiate of the left Expression
     * @param nothingD - the differentiate of the right Expression - in unary is nothing
     * @param left - the left Expression
     * @param nothing - the right Expression - in unary is nothing
     * @return - the differentiate of the Expression
     */
    protected abstract Expression dCal(Expression leftD, Expression nothingD, Expression left, Expression nothing);

    /**
     * Abstract function - implement -  check if there is a place to simplify in the Expression (if yes, simplify it).
     * @param leftSideS - the left Expression
     * @param rightSideS - the right Expression - in unary is nothing
     * @return - the simplified Expression
     */
    protected Expression simplifyCheck(Expression leftSideS, Expression rightSideS) {
        if (leftSideS.getVariables().isEmpty()) {
            try {
                 return new Num(type(new Num(leftSideS.evaluate()), new Num(0)).evaluate());
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
            return type(leftSideS.simplify(), new Num(0));
        }
    }

    /**
     * Abstract function - implement -  check if there is a place to simplify in the Expression (if yes, simplify it).
     * @param leftSideS - the left Expression
     * @param rightSideS - the right Expression - in unary is nothing
     * @return - the simplified Expression
     */
    protected Expression simplifyCheckAdvanced(Expression leftSideS, Expression rightSideS) {
        if (leftSideS.getVariables().isEmpty()) {
            try {
                 return new Num(type(new Num(leftSideS.evaluate()), new Num(0)).evaluate());
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
            return simplifyFunctionAdvanced(leftSideS, rightSideS);
        }
    }

    /**
     * Abstract class - Simplify the Expression.
     * @param leftSide - the left side of the Expression
     * @param rightSide - the right side to the Expression - none
     * @return the simplified Expression
     */
    protected Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide) {
        return type(leftSide.advancedsimplify(), new Num(0));
    }
}
