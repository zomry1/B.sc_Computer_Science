import java.util.Map;
import java.util.TreeMap;
/**
 * Main check class.
 * @author Omry
 *
 */
public class ExpressionsTest {

    /**
     * Main check class.
     * @author Omry
     * @param args - none
     */
    public static void main(String[] args) {
        //Create the Expression
        Expression ex = new Plus(new Plus(new Mult(2, "x"), new Sin(new Mult(4, "y"))), new Pow("e", "x"));
        //Print the Expression
        System.out.println(ex.toString());
        //Create the map of the values
        Map<String, Double> assigment = new TreeMap<String, Double>();
        assigment.put("x", 2.0);
        assigment.put("y", 0.25);
        assigment.put("e", 2.71);
        try {
            //Print the value of the Expression
            System.out.println(ex.evaluate(assigment));
        } catch (Exception e) {
            //print error if error caught
            System.out.println(e.getMessage());
        }

        //Print the differentiate
        System.out.println(ex.differentiate("x"));

        try {
            //Print the value of the differentiate
            System.out.println(ex.differentiate("x").evaluate(assigment));
        } catch (Exception e) {
            //Print error if error caught
            System.out.println(e.getMessage());
        }

        //Print the simplified differentiate
        System.out.println(ex.differentiate("x").simplify());

    }
}
