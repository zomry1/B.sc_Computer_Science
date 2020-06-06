import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Num object.
 * implements Expression
 * @author Omry
 *
 */
public class Num implements Expression {
    //Member
    private double num;

    /**
     * Constructor.
     * @param num - the double of the num
     */
    public Num(double num) {
        this.num = num;
    }

    /**
     * Evaluate the Expression with specific map (values for variables).
     * @param assignment - specific map of values and variables
     * @throws Exception -  if there is an error in the evaluation
     * @return - the value of the Expression
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return this.num;
    }

    /**
     *  Evaluate the Expression.
     *  @throws Exception -  if there is an error in the evaluation
     *   @return - the value of the Expression
     */
    public double evaluate() throws Exception {
        return this.num;
    }

    /**
     * return the variables in the Expression.
     * @return - list with all the variables in the Expression
     */
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        return list;
    }

    /**
     * Return the Expression in string format.
     * @return - the string of the Expression
     */
    @Override
    public String toString() {
        return Double.toString(this.num);
    }

    /**
     * assign the Expression instead of the variable.
     * @param var - the variable we want to replace
     * @param expression - the expression we want to replace to
     * @return - new Expression with the replaced variable
     */
    public Expression assign(String var, Expression expression) {
        return new Num(this.num);
    }

    /**
     * calculate the differentiate of the Expression.
     * @param var - the variable that we differentiate according to
     * @return - the differentiate
     */
    public Expression differentiate(String var) {
        return new Num(0);
    }

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression simplify() {
        return new Num(num);
    }

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression advancedsimplify() {
        return new Num(num);
    }

}
