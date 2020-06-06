/**
 * Advanced simplified check.
 */
public class SimplificationDemo {
/**
 * Advanced simplified check.
 * @param args - none
 */
    public static void main(String[] args) {
        Expression ex;

        ex = new Div(0, "x");
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Plus("x", "x");
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Pow("x", 1);
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Pow("x", 0);
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Pow("x", new Pow("y", "z"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Neg(new Neg("x"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Neg(-5);
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Minus(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Div(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Log(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());

        System.out.println();

        ex = new Mult(new Plus("x", "y"), new Plus("y", "x"));
        System.out.println(ex.toString());
        System.out.println(ex.advancedsimplify().toString());
    }

}
