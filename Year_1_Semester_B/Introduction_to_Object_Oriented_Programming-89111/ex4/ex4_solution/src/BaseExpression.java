import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
/**
 * The Base method of the Expressions.
 * handle both the unary and binary Expressions
 * @author Omry
 *
 */
public abstract class  BaseExpression implements Expression {
    //Initialize the Expressions
    private Expression leftSide = null;
    private Expression rightSide = null;

    /**
     * Constructor for binary Expression.
     * @param leftSide - the left side of the expression (before the symbol)
     * @param rightSide - the right side of the expression (after the symbol)
     */
    public BaseExpression(Expression leftSide, Expression rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    /**
     * Constructor for the unary Expression.
     * @param main - the Expression in the Expression
     */
    public BaseExpression(Expression main) {
        this.leftSide = main;
        //Put null in the second Expression place
        this.rightSide = null;
    }

    /**
     * Evaluate the Expression with specific map (values for variables).
     * @param assigment - specific map of values and variables
     * @throws Exception -  if there is an error in the evaluation
     * @return - the value of the Expression
     */

    public double evaluate(Map<String, Double> assigment) throws Exception {
        //If its unary Expression
        if (this.rightSide == null) {
            return calculate(this.leftSide.evaluate(assigment), 0);
        }
        //Its binary Expression
        return calculate(this.leftSide.evaluate(assigment), this.rightSide.evaluate(assigment));
    }

    /**
     *  Evaluate the Expression.
     *  @throws Exception -  if there is an error in the evaluation
     *   @return - the value of the Expression
     */
    public double evaluate() throws Exception {
        if (this.rightSide == null) {
            return calculate(this.leftSide.evaluate(), 0);
        }
        return calculate(this.leftSide.evaluate(), this.rightSide.evaluate());
    }

    /**
     * Abstract function - each Expression type get 2 doubles and evaluate the Expression.
     * @param d1 - first double (the left one)
     * @param d2 - second double (the right one)
     * @return - the value of the Expression
     * @throws Exception - if there is an error in the calculation
     */
    protected abstract double calculate(double d1, double d2) throws Exception;

    /**
     * return the variables in the Expression.
     * @return - list with all the variables in the Expression
     */
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        //Get the variables in the left Expression
        list.addAll(this.leftSide.getVariables());
        //If there is right Expression get the variables in the right Expression
        if (this.rightSide != null) {
            list.addAll(this.rightSide.getVariables());
        }
        //return list;
        return list.stream().distinct().collect(Collectors.toList());
    }

    /**
     * Return the Expression in string format.
     * @return - the string of the Expression
     */
    @Override
    public String toString() {
        if (this.rightSide == null) {
            return this.startString() + "(" + this.symbol() + this.leftSide.toString() + ")";
        }
        return this.startString() + "(" + this.leftSide.toString() +  this.symbol()
                                  + this.rightSide.toString() + ")";
    }

    /**
     * Get the symbol of the Expression.
     * @return string of the symbol
     */
    protected abstract String symbol();

    /**
     * Get the starting of the Expression. (all the Expression is "" but log is with Log).
     * @return string of the starting Expression
     */
    protected String startString() {
        return "";
    }

    /**
     * assign the Expression instead of the variable.
     * @param var - the variable we want to replace
     * @param expression - the expression we want to replace to
     * @return - new Expression with the replaced variable
     */
    public Expression assign(String var, Expression expression) {
        //If its unary Expression
        if (this.rightSide == null) {
            return type(this.leftSide.assign(var, expression), new Num(0));
        }
        //Its binary Expression
        return type(this.leftSide.assign(var, expression), this.rightSide.assign(var, expression));
    }

    /**
     * Abstract function - Get 2 Expression and return the same Expression type of them (the method called).
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the new Expression with the 2 Expression in it
     */
    protected abstract Expression type(Expression left, Expression right);

    /**
     * calculate the differentiate of the Expression.
     * @param var - the variable that we differentiate according to
     * @return - the differentiate
     */
    public Expression differentiate(String var) {
        //If its unary Expression
        if (this.rightSide == null) {
            return dCal(this.leftSide.differentiate(var), new Num(0), this.leftSide, new Num(0));
        }
        //Its binary Expression
        return dCal(this.leftSide.differentiate(var), this.rightSide.differentiate(var),
                    this.leftSide, this.rightSide);
        }

    /**
     * Abstract function - to calculate the differentiate of each Expression type.
     * @param leftD - the differentiate of the left Expression
     * @param rightD - the differentiate of the right Expression
     * @param left - the left Expression
     * @param right - the right Expression
     * @return - the differentiate of the Expression
     */
    protected abstract Expression dCal(Expression leftD, Expression rightD, Expression left, Expression right);

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression simplify() {
      //If its unary Expression
        if (this.rightSide == null) {
            return simplifyCheck(this.leftSide, new Num(0));
        }
        //Its binary Expression
        return simplifyCheck(this.leftSide, this.rightSide);
    }

    /**
     * Abstract function - check if there is a place to simplify in the Expression (if yes, simplify it).
     * @param leftSideS - the left Expression
     * @param rightSideS - the right Expression
     * @return - the simplified Expression
     */
    protected abstract Expression simplifyCheck(Expression leftSideS, Expression rightSideS);

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression advancedsimplify() {
        //If its unary Expression
          if (this.rightSide == null) {
              return simplifyCheckAdvanced(this.leftSide, new Num(0));
          }
          //Its binary Expression
          return simplifyCheckAdvanced(this.leftSide, this.rightSide);
      }

      /**
       * Abstract function - check if there is a place to simplify in the Expression (if yes, simplify it).
       * @param leftSideS - the left Expression
       * @param rightSideS - the right Expression
       * @return - the simplified Expression
       */
      protected abstract Expression simplifyCheckAdvanced(Expression leftSideS, Expression rightSideS);

      /*
       * For Bonus
       */
      /**
       * get right Expression.
       * @return - right Expression
       */
      public Expression getRight() {
          return rightSide;
      }

      /**
       * get left Expression.
       * @return - left Expression
       */
      public Expression getLeft() {
          return leftSide;
      }
}
