import javax.management.RuntimeErrorException;

/**
 * Abstract class - for binary Expression (Div,Log,Minus,Mult,Plus,Pow).
 * Extend the Base Expression
 * Implements Expression
 * @author Omry
 *
 */
public abstract class BinaryExpression extends BaseExpression implements Expression {

    /**
     * Constructor for binary Expression.
     * @param leftSide - the left side of the expression (before the symbol)
     * @param rightSide - the right side of the expression (after the symbol)
     */
    protected BinaryExpression(Expression leftSide, Expression rightSide) {
        //Call the construcor in the BaseExpression class
        super(leftSide, rightSide);
    }

    /**
     * Abstract function - Get 2 Expression and return the same Expression type of them (the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the 2 Expressions in it
     */
    protected abstract Expression type(Expression left, Expression right);

    /**
     * Abstract function - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @return - the value of the Expression
     * @throws Exception - if there is an error in the calculation
     */
    protected abstract double calculate(double d1, double d2) throws Exception;

    /**
     * Get the symbol of the Expression.
     * @return string of the symbol
     */
    protected abstract String symbol();

    /**
     * Get the starting of the Expression. (all the Expression is "" but log is with Log and the unary expression).
     * @return string of the starting Expression
     */
    protected String startString() {
        return "";
    }

    /**
     * Abstract function - to calculate the differentiate of each Expression type.
     * for plus and minux just return the different , the other are override.
     * @param leftD - the differentiate of the left Expression
     * @param rightD - the differentiate of the right Expression
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the differentiate of the Expression
     */
    protected Expression dCal(Expression leftD, Expression rightD, Expression left, Expression right) {
        return type(leftD, rightD);
    }

    /**
     * Abstract function - implement -  check if there is a place to simplify in the Expression (if yes, simplify it).
     * @param leftSideS - the left Expression
     * @param rightSideS - the right Expression
     * @return - the simplified Expression
     */

    protected Expression simplifyCheck(Expression leftSideS, Expression rightSideS) {
        Expression rightToSimplify;
        Expression leftToSimplify;
        boolean leftIsCalculated = false;
        boolean rightIsCalculated = false;
        //If the leftSide has no variables - calculate it and set the flag to true
        if (leftSideS.getVariables().isEmpty()) {
            try {
                 leftToSimplify = new Num(leftSideS.evaluate());
                 leftIsCalculated = true;
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
             leftToSimplify = leftSideS;
        }
        //If the rightSide has no variables - calculate it and set the flag to true
        if (rightSideS.getVariables().isEmpty()) {
            try {
                 rightToSimplify = new Num(rightSideS.evaluate());
                 rightIsCalculated = true;
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
             rightToSimplify = rightSideS;
        }
        //If both side without variables, calculate
        if (leftIsCalculated && rightIsCalculated) {
            try {
                return new Num(this.evaluate());
            } catch (Exception ex) {
                throw new  RuntimeErrorException(null, ex.getMessage());
            }
        }
        //Else simplify them
        return simplifyFunction(leftToSimplify.simplify(), rightToSimplify.simplify());
    }
    /**
     * Abstract class - Simplify the Expression.
     * @param leftSide - the left side of the Expression
     * @param rightSide - the right side to the Expression
     * @return the simplified Expression
     */
    protected abstract Expression simplifyFunction(Expression leftSide, Expression rightSide);

    /**
     * Abstract function - implement -  check if there is a place to simplify in the Expression (if yes, simplify it).
     * @param leftSideS - the left Expression
     * @param rightSideS - the right Expression
     * @return - the simplified Expression
     */
    protected Expression simplifyCheckAdvanced(Expression leftSideS, Expression rightSideS) {
        Expression rightToSimplify;
        Expression leftToSimplify;
        boolean leftIsCalculated = false;
        boolean rightIsCalculated = false;
        //If the leftSide has no variables - calculate it and set the flag to true
        if (leftSideS.getVariables().isEmpty()) {
            try {
                 leftToSimplify = new Num(leftSideS.evaluate());
                 leftIsCalculated = true;
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
             leftToSimplify = leftSideS;
        }
        //If the rightSide has no variables - calculate it and set the flag to true
        if (rightSideS.getVariables().isEmpty()) {
            try {
                 rightToSimplify = new Num(rightSideS.evaluate());
                 rightIsCalculated = true;
            } catch (Exception ex) {
                throw new RuntimeErrorException(null, ex.getMessage());
            }
        } else {
             rightToSimplify = rightSideS;
        }
        //If both side without variables, calculate
        if (leftIsCalculated && rightIsCalculated) {
            try {
                return new Num(this.evaluate());
            } catch (Exception ex) {
                throw new  RuntimeErrorException(null, ex.getMessage());
            }
        }
        //Else simplify them
        return simplifyFunctionAdvanced(leftToSimplify.advancedsimplify(), rightToSimplify.advancedsimplify());
    }
    /**
     * Abstract class - Simplify the Expression.
     * @param leftSide - the left side of the Expression
     * @param rightSide - the right side to the Expression
     * @return the simplified Expression
     */
    protected abstract Expression simplifyFunctionAdvanced(Expression leftSide, Expression rightSide);

    /*
     * For Bonus
     */
    /**
     * Swap between the left side and the right side.
     * @param ex - BinaryExpression
     * @return - swaped Expression
     */
    protected Expression swap(Expression ex) {
        //If ex is Plus
        if (ex instanceof Plus) {
            return new Plus(((BaseExpression) ex).getRight(), ((BaseExpression) ex).getLeft());
        }
        //If ex is Mult
        if (ex instanceof Mult) {
            return new Mult(((BaseExpression) ex).getRight(), ((BaseExpression) ex).getLeft());
        }
        //If ex is Minus
        if (ex instanceof Minus) {
            return new Minus(((BaseExpression) ex).getRight(), ((BaseExpression) ex).getLeft());
        }
        //none commutative
        return ex;
    }
}
