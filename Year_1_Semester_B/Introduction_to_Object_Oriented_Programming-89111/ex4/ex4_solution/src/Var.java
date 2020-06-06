import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * Var object.
 * implements Expression
 * @author Omry
 *
 */
public class Var implements Expression {
    //Member
    private String varString;

    /**
     * Constructor.
     * @param var - the string of the var
     */
    public Var(String var) {
        this.varString = var;
    }

    /**
     * Evaluate the Expression with specific map (values for variables).
     * @param assignment - specific map of values and variables
     * @throws Exception -  if there is an error in the evaluation
     * @return - the value of the Expression
     */
    public double evaluate(Map<String, Double> assignment) throws Exception {
        if (assignment.containsKey(this.varString)) {
            return assignment.get(this.varString);
        } else {
            throw new Exception("There is a variable in the eqaution");
        }
    }

    /**
     *  Evaluate the Expression.
     *  @throws Exception -  if there is an error in the evaluation
     *   @return - the value of the Expression
     */
    public double evaluate() throws Exception {
        throw new Exception("There is a variable in the eqaution");
    }

    /**
     * return the variables in the Expression.
     * @return - list with all the variables in the Expression
     */
    public List<String> getVariables() {
        List<String> list = new LinkedList<String>();
        list.add(this.varString);
        return list;
    }

    /**
     * Return the Expression in string format.
     * @return - the string of the Expression
     */
    @Override
    public String toString() {
        return this.varString;
    }

    /**
     * assign the Expression instead of the variable.
     * @param var - the variable we want to replace
     * @param expression - the expression we want to replace to
     * @return - new Expression with the replaced variable
     */
    public Expression assign(String var, Expression expression) {
        Var returnVar = new Var(this.varString);
        if (returnVar.varString == var) {
            return expression;
        }
        return returnVar;
    }

    /**
     * calculate the differentiate of the Expression.
     * @param var - the variable that we differentiate according to
     * @return - the differentiate
     */
    public Expression differentiate(String var) {
        if (this.varString.equals(var)) {
            return new Num(1);
        }
        return new Num(0);
    }

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression simplify() {
        return new Var(this.varString);
    }

    /**
     * Simplify the Expression.
     * @return - the simplified Expression
     */
    public Expression advancedsimplify() {
        return new Var(this.varString);
    }

}
