import java.util.List;
import java.util.Map;
/**
 * Expresion interface.
 * @author Omry
 *
 */
public interface Expression {
    /**
     * Evaluate the expression.
     * @param assignment -  the variable values provided in the assignment
     * @throws Exception -   If the expression
     * contains a variable which is not in the assignment, an exception
     * is thrown.
     * @return Evaluate the expression
     */
     double evaluate(Map<String, Double> assignment) throws Exception;

     /**
      * Like the evaluate(assignment).
      * @return  Evaluate the expression
      * @throws Exception - If the expression
      * contains a variable which is not in the assignment, an exception
      * is thrown.
      */
     double evaluate() throws Exception;

     /**
      * return the variables in the Expression.
      * @return - list with all the variables in the Expression
      */
     List<String> getVariables();

     /**
      * Return the Expression in string format.
      * @return - the string of the Expression
      */
      String toString();

      /**
       * assign the Expression instead of the variable.
       * @param var - the variable we want to replace
       * @param expression - the expression we want to replace to
       * @return - new Expression with the replaced variable
       */
      Expression assign(String var, Expression expression);

      /**
       * calculate the differentiate of the Expression.
       * @param var - the variable that we differentiate according to
       * @return - the differentiate
       */
       Expression differentiate(String var);

       /**
        * Simplify the Expression.
        * @return - the simplified Expression
        */
       Expression simplify();

       /**
        * Simplify the Expression.
        * @return - the simplified Expression
        */
       Expression advancedsimplify();
}
