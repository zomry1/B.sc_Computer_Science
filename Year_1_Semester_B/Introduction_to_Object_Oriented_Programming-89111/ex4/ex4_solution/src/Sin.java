/**
 * Sin Object.
 * extends Unary Expression
 * implements Expression
 * @author Omry
 *
 */
public class Sin extends UnaryExpression implements Expression {

    /**
     * Constructor.
     * @param main - the Expression in sin
     */
    public Sin(Expression main) {
        super(main);
    }

    /**
     * Constructor.
     * @param num - the double that is Num Expression in sin
     */
    public Sin(double num) {
        super(new Num(num));
    }

    /**
     * Constructor.
     * @param var - the string that is Var Expression in sin
     */
    public Sin(String var) {
        super(new Var(var));
    }

    /**
     * Abstract function - implement - Get 2 Expression and return the same Expression type of them(the method called).
     * @param main - the left Expression
     * @param nothing - the right Expression - in unary is nothing
     * @return - the new Expression with the Expression in it
     */
    protected Expression type(Expression main, Expression nothing) {
        //Return new sin with the main Expression in it
        return new Sin(main);
    }

    /**
     * Abstract function - implement - each Expression type get 2 doubles and evaluate the Expression.
     * @param mainValue - first double (the left one)
     * @param nothing - second double (the right one) - in unary its nothing
     * @return - the value of the Expression
     */
    protected double calculate(double mainValue, double nothing) {
        //Return the sin of the double mainValue
        return Math.sin(Math.toRadians(mainValue));
    }

    /**
     * Abstract function - implement - Get the starting of the Expression.
     * @return string of the starting Expression
     */
    protected String startString() {
        return "sin";
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
        //Return cos with the diff of left in it multiple by the left
        return new Mult(
                new Cos(left), leftD);
    }
}
