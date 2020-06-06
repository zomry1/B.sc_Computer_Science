import java.util.Map;
import java.util.TreeMap;
import java.text.DecimalFormat;
import java.util.Collections;

import java.util.ArrayList;
import java.util.List;

public class mainTester {

	public static void main(String[] args) throws Exception {
		
		System.out.println("Start of checking...");
        Expression ex;
        
        ex = new Plus(10, 10);
        if (!ex.toString().equals("(10.0 + 10.0)"))
            System.out.println("#1:Error in" + ex);
        ex = new Plus(10, "x");
        if (!ex.toString().equals("(10.0 + x)"))
            System.out.println("#2:Error in" + ex);
        ex = new Plus("x", 10);
        if (!ex.toString().equals("(x + 10.0)"))
            System.out.println("#3:Error in" + ex);
        ex = new Plus("x", "x");
        if (!ex.toString().equals("(x + x)"))
            System.out.println("#4:Error in" + ex);
        //minus check
        ex = new Minus(10, 10);
        if (!ex.toString().equals("(10.0 - 10.0)"))
            System.out.println("#5:Error in" + ex);
        ex = new Minus(10, "x");
        if (!ex.toString().equals("(10.0 - x)"))
            System.out.println("#6:Error in" + ex);
        ex = new Minus("x", 10);
        if (!ex.toString().equals("(x - 10.0)"))
            System.out.println("#7:Error in" + ex);
        ex = new Minus("x", "x");
        if (!ex.toString().equals("(x - x)"))
            System.out.println("#8:Error in" + ex);
 
        //log check
        ex = new Log(10, 10);
        if (!ex.toString().equals("log(10.0, 10.0)"))
            System.out.println("#9:Error in" + ex);
        ex = new Log(10, "x");
        if (!ex.toString().equals("log(10.0, x)"))
            System.out.println("#10:Error in" + ex);
        ex = new Log("x", 10);
        if (!ex.toString().equals("log(x, 10.0)"))
            System.out.println("#11:Error in" + ex);
        ex = new Log("x", "x");
        if (!ex.toString().equals("log(x, x)"))
            System.out.println("#12:Error in" + ex);
 
        //div check
        ex = new Div(10, 10);
        if (!ex.toString().equals("(10.0 / 10.0)"))
            System.out.println("#13:Error in" + ex);
        ex = new Div(10, "x");
        if (!ex.toString().equals("(10.0 / x)"))
            System.out.println("#14:Error in" + ex);
        ex = new Div("x", 10);
        if (!ex.toString().equals("(x / 10.0)"))
            System.out.println("#15:Error in" + ex);
        ex = new Div("x", "x");
        if (!ex.toString().equals("(x / x)"))
            System.out.println("#16:Error in" + ex);
 
        //Mult check
        ex = new Mult(10, 10);
        if (!ex.toString().equals("(10.0 * 10.0)"))
            System.out.println("#17:Error in" + ex);
        ex = new Mult(10, "x");
        if (!ex.toString().equals("(10.0 * x)"))
            System.out.println("#18:Error in" + ex);
        ex = new Mult("x", 10);
        if (!ex.toString().equals("(x * 10.0)"))
            System.out.println("#19:Error in" + ex);
        ex = new Mult("x", "x");
        if (!ex.toString().equals("(x * x)"))
            System.out.println("#20:Error in" + ex);
        //Pow check
        ex = new Pow(10, 10);
        if (!ex.toString().equals("(10.0^10.0)"))
            System.out.println("#21:Error in" + ex);
        ex = new Pow(10, "x");
        if (!ex.toString().equals("(10.0^x)"))
            System.out.println("#22:Error in" + ex);
        ex = new Pow("x", 10);
        if (!ex.toString().equals("(x^10.0)"))
            System.out.println("#23:Error in" + ex);
        ex = new Pow("x", "x");
        if (!ex.toString().equals("(x^x)"))
            System.out.println("#24:Error in" + ex);
 
        //Cos check
        ex = new Cos(10);
        if (!ex.toString().equals("cos(10.0)"))
            System.out.println("#25:Error in" + ex);
        ex = new Cos("x");
        if (!ex.toString().equals("cos(x)"))
            System.out.println("#26:Error in" + ex);
 
        //sin check
        ex = new Sin(10);
        if (!ex.toString().equals("sin(10.0)"))
            System.out.println("#27:Error in radian:" + ex);
        ex = new Sin("x");
        if (!ex.toString().equals("sin(x)"))
            System.out.println("#28:Error in" + ex);
 
        //neg check
        ex = new Neg(10);
        if (!ex.toString().equals("(-10.0)"))
            System.out.println("#29:Error in" + ex);
        ex = new Neg("x");
        if (!ex.toString().equals("(-x)"))
            System.out.println("#30:Error in" + ex);
        
        //simplify checking
        //x*1 or 1*x
        ex = new Mult("x", 1);
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#31 :Error in simplify of" + ex.simplify() + "its: " + ex.simplify().toString());
        ex = new Mult(new Mult("x", 8), 1);
        if (!(ex.simplify().toString().equals("(x * 8.0)") || ex.simplify().toString().equals("(8.0 * x)")))
            System.out.println("#32:Error in simplify of" + ex.simplify());
        ex = new Mult(1, new Mult("x", 8));
        if (!(ex.simplify().toString().equals("(x * 8.0)") || ex.simplify().toString().equals("(8.0 * x)")))
            System.out.println("#33:Error in simplify of" + ex.simplify());
        ex = new Mult(1, "x");
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#34:Error in simplify of" + ex.simplify());
        //x*0
        ex = new Mult("x", 0);
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#35:Error in simplify of" + ex.simplify());
        ex = new Mult(new Pow(new Mult(3, 2), 150), 0);
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#36:Error in simplify of" + ex.simplify());
        ex = new Mult(0, "x");
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#37:Error in simplify of" + ex.simplify());
        ex = new Mult(0, new Pow(new Mult(10, 50), 50));
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#38:Error in simplify of" + ex.simplify());
        //x+0
        ex = new Plus("x", 0);
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#39:Error in simplify of" + ex.simplify());
        ex = new Plus(new Pow("x", "y"), 0);
        if (!ex.simplify().toString().equals("(x^y)"))
            System.out.println("#40:Error in simplify of" + ex.simplify());
        ex = new Plus(0, new Pow("x", "y"));
        if (!ex.simplify().toString().equals("(x^y)"))
            System.out.println("#41:Error in simplify of" + ex.simplify());
        ex = new Plus(0, "x");
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#42:Error in simplify of" + ex.simplify());
        //x/x
        ex = new Div("x", "x");
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#43:Error in simplify of" + ex.simplify());
        ex = new Div(new Mult(9, "x"), new Mult(9, "x"));
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#44:Error in simplify of" + ex.simplify());
        ex = new Div(5, 5);
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#45:Error in simplify of" + ex.simplify());
        //x/1
        ex = new Div("x", 1);
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#46:Error in simplify of" + ex.simplify());
        ex = new Div(new Pow(10, "y"), 1);
        if (!ex.simplify().toString().equals("(10.0^y)"))
            System.out.println("#47:Error in simplify of" + ex.simplify());
        //x-0
        ex = new Minus("x", 0);
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#48:Error in simplify of" + ex.simplify());
        ex = new Minus(new Div(10, "x"), 0);
        if (!ex.simplify().toString().equals("(10.0 / x)"))
            System.out.println("#49:Error in simplify of" + ex.simplify());
        //0-x
        ex = new Minus(0, "x");
        if (!ex.simplify().toString().equals("(-x)"))
            System.out.println("#50:Error in simplify of" + ex.simplify());
        ex = new Minus(0, new Cos("y"));
        if (!ex.simplify().toString().equals("(-cos(y))"))
            System.out.println("#51:Error in simplify of" + ex.simplify());
        //x-x
        ex = new Minus("x", "x");
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#52:Error in simplify of" + ex.simplify());
        ex = new Minus(new Div(10, "x"), new Div(10, "x"));
        if (!ex.simplify().toString().equals("0.0"))
            System.out.println("#53:Error in simplify of" + ex.simplify());
        //log (x,x)
        ex = new Log("x", "x");
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#54:Error in simplify of" + ex.simplify());
        ex = new Log(105, 105);
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#55:Error in simplify of" + ex.simplify());
        ex = new Log(new Sin(new Pow(10, "x")), new Sin(new Pow(10, "x")));
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#56:Error in simplify of" + ex.simplify());
        //x^1
        ex = new Pow("x", 1);
        if (!ex.simplify().toString().equals("x"))
            System.out.println("#57:Error in simplify of" + ex.simplify());
        ex = new Pow(new Pow("x", "y"), 1);
        if (!ex.simplify().toString().equals("(x^y)"))
            System.out.println("#58:Error in simplify of" + ex.simplify());
        //x^0
        ex = new Pow(new Pow("x", "y"), 0);
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#59:Error in simplify of" + ex.simplify());
        //result
        ex = new Plus(10, new Minus(50, new Pow(3, new Div(10, new Sin(new Cos(0))))));
        if (!ex.simplify().toString().equals("-2.422264153431509E273"))
            System.out.println("#60:Error in simplify of" + ex.simplify());
        ex = new Cos(0);
        if (ex.evaluate() != 1)
            System.out.println("#61:Error in:" + ex.evaluate());
        //evaluate of cos(x) is NAN
        ex = new Cos("x");
        try {
            double d = ex.evaluate();
            System.out.println("#62:Error in:" + ex);
        } catch (Exception e) {
        }
        //neg
        ex = new Neg(-1);
        if (!ex.toString().equals("(--1.0)"))
            System.out.println("#63:Error in" + ex);
        ex = new Neg(-1);
        if (!ex.simplify().toString().equals("1.0"))
            System.out.println("#64:Error in" + ex);
        ex = new Neg(1);
        if (!ex.toString().equals("(-1.0)"))
            System.out.println("#65:Error in" + ex);
        ex = new Neg("x");
        if (!ex.toString().equals("(-x)"))
            System.out.println("#66:Error in" + ex);
        //log rules
        ex = new Log(2, 8);
        if (!ex.simplify().toString().equals("3.0"))
            System.out.println("#67:Error in simplify of" + ex.simplify());
        ex = new Log(2, "x");
        if (!ex.simplify().toString().equals("log(2.0, x)"))
            System.out.println("#68:Error in simplify of" + ex.simplify());
        //base>0
        ex = new Log(-1, 10);
        try {
            System.out.println("#69:Error in log(0, x)" + ex.evaluate());
        } catch (Exception e) {
        }
        //base !=1
        ex = new Log(1, 10);
        try {
            System.out.println("#70:Error in log(1, x)" + ex.evaluate());
        } catch (Exception e) {
        }
        //expression >0
        ex = new Log(5, 0);
        try {
            System.out.println("#71:Error in log(x, 0)" + ex.evaluate());
        } catch (Exception e) {
        }
        
//Differentitation
        
        ex = new Num(10);
        if (!ex.differentiate("x").toString().equals("0.0"))
            System.out.println("#72:Error in Differentitation of:" + ex);
        ex = new Var("x");
        if (!ex.differentiate("x").toString().equals("1.0"))
            System.out.println("#73:Error in Differentitation of:" + ex);
        ex = new Var("y");
        if (!ex.differentiate("x").toString().equals("0.0"))
            System.out.println("#74:Error in Differentitation of:" + ex);
        ex = new Cos(10);
        if (!(ex.differentiate("x").toString().equals("(-(sin(10.0) * 0.0))")))
            System.out.println("#75:Error in Differentitation of:" + ex + " its: " + ex.differentiate("x").toString());
        ex = new Cos("x");
        if (!ex.differentiate("x").toString().equals("(-(sin(x) * 1.0))"))
            System.out.println("#76:Error in Differentitation of:" + ex);
        ex = new Div(10, "x");
        if (!ex.differentiate("x").toString().equals("(((0.0 * x) - (10.0 * 1.0)) / (x^2.0))"))
            System.out.println("#77:Error in Differentitation of:" + ex);
        ex = new Log(10, "x");
        if (!ex.differentiate("x").toString().equals("(1.0 / (x * log(e, 10.0)))"))
            System.out.println("#78:Error in Differentitation of:" + ex + " its: " + ex.differentiate("x").toString());
        ex = new Minus(10, "x");
        if (!ex.differentiate("x").toString().equals("(0.0 - 1.0)"))
            System.out.println("#79:Error in Differentitation of:" + ex);
        ex = new Mult(10, "x");
        if (!ex.differentiate("x").toString().equals("((0.0 * x) + (10.0 * 1.0))"))
            System.out.println("#80:Error in Differentitation of:" + ex + " its: " + ex.differentiate("x").toString());
        ex = new Plus(10, "x");
        if (!ex.differentiate("x").toString().equals("(0.0 + 1.0)"))
            System.out.println("#81:Error in Differentitation of:" + ex);
        ex = new Pow(10, "x");
        if (!ex.differentiate("x").toString().equals("((10.0^x) * ((0.0 * (x / 10.0)) + (1.0 * log(e, 10.0))))"))
            System.out.println("#82:Error in Differentitation of:" + ex + "its: " + ex.differentiate("x").toString());
        ex = new Pow("x", "x");
        if (!ex.differentiate("x").toString().equals("((x^x) * ((1.0 * (x / x)) + (1.0 * log(e, x))))"))
            System.out.println("#83:Error in Differentitation of:" + ex);
        ex = new Pow("x", 3);
        if (!ex.differentiate("x").toString().equals("((x^3.0) * ((1.0 * (3.0 / x)) + (0.0 * log(e, x))))"))
            System.out.println("#84:Error in Differentitation of:" + ex);
 
        //Yoav tests/
        
        ex = new Sin(new Pow(new Mult(new Plus(new Mult(new Num(2), new Var("x")), new Var("y")), new Num(4)), new Var("x")));
        if (!ex.toString().equals("sin(((((2.0 * x) + y) * 4.0)^x))"))
            System.out.println("#85:Error in simplify of" + ex);
        List<String> vars = ex.getVariables();
        if (!(vars.get(0).equals("x") && vars.get(1).equals("y"))) {
            System.out.println("#86:Error in getVarList");
        }
 
        Expression ex2 = ex.assign("x", new Num(10));
        System.out.println(ex.assign("x",new Num(10)).toString());
        if (!ex2.toString().equals("sin(((((2.0 * 10.0) + y) * 4.0)^10.0))")) {
            System.out.println("#87:Error in:" + ex2);
        }
        ex2 = ex.assign("x", ex);
        if (!ex2.toString().equals("sin(((((2.0 * sin(((((2.0 * x) + y) * 4.0)^x))) + y) * 4.0)^sin(((((2.0 * x) + y) * 4.0)^x))))")) {
            System.out.println("#88:Error in:" + ex2);
        }
// map checking
        ex = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x", 2.0);
        assignment.put("y", 4.0);
        double value = ex.evaluate(assignment);
        if (value != 36) {
            System.out.println("#89:Error in map");
        }
        ex = new Sin(180);
        if (ex.evaluate() != 1.2246467991473532E-16)
            System.out.println("#90:Error in Radians:" + ex);
        ex = new Cos(180);
        if (ex.evaluate() != -1.0)
            System.out.println("#91:Error in Radians:" + ex);
 
//more tests
        ex = new Pow(new Var("x"), new Num(4)).differentiate("x");
        if (!ex.toString().equals("((x^4.0) * ((1.0 * (4.0 / x)) + (0.0 * log(e, x))))")) {
            System.out.println("#92:Error in:" + ex);
        }
        ex = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
        if (!ex.differentiate("x").toString().equals("(((x + y)^2.0) * (((1.0 + 0.0) * (2.0 / (x + y))) + (0.0 * log(e, (x + y)))))")) {
            System.out.println("#93:Error in diff:" + ex.differentiate("x"));
        }
        ex = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
        Expression ex5 = ex.differentiate("x");
        System.out.println(ex5.simplify().toString());
        if (!ex.differentiate("x").simplify().toString().equals("(((x + y)^2.0) * (2.0 / (x + y)))")) {
            System.out.println("#94:Error in simplify of:" + ex.differentiate("x") + " its: " + ex.differentiate("x").simplify().toString());
        }
 
        ex = new Pow(new Var("e"), new Var("x"));
        if (!ex.differentiate("x").toString().equals("((e^x) * ((0.0 * (x / e)) + (1.0 * log(e, e))))")) {
            System.out.println("#95:Error in diff of:" + ex);
        }
        ex = ex.simplify();
        if (!ex.toString().equals("(e^x)")) {
            System.out.println("#96:Error in simplify of:" + ex);
        }
 
        ex = new Minus(new Plus("x", "y"), new Plus("y", "x"));
        if (!ex.simplify().toString().equals("0.0")) {
            System.out.println("#97:Error in simplify of:" + ex);
        }
 
        ex = new Div(new Plus("x", "y"), new Plus("y", "x"));
        if (!ex.simplify().toString().equals("1.0")) {
            System.out.println("#98:Error in simplify of:" + ex);
        }
 
        ex = new Log(new Plus("x", "y"), new Plus("y", "x"));
        if (!ex.simplify().toString().equals("1.0")) {
            System.out.println("#99:Error in simplify of:" + ex);
        }
 
        ex = new Plus(new Mult(new Plus(3, 6), "x"), new Mult(new Mult(4, "x"), new Sin(0)));
        if (!ex.simplify().toString().equals("(9.0 * x)")) {
            System.out.println("#100:Error in simplify of:" + ex);
        }
        try {
            ex = new Num(10);
        } catch (Exception e) {
            System.out.println("#101:you have to do int constructor");
        }
        ex = new Cos(new Plus("x", "y"));
        if (!(ex.differentiate("x").toString().equals("(-(sin((x + y)) * (1.0 + 0.0)))")) || (ex.differentiate("x").toString().equals("((-sin((x + y)) * (1.0 + 0.0)))"))) {
            System.out.println("#102:Error in diff of:" + ex);
        }
        //bonus checking
 
        ex = new Pow(new Pow("x", "y"), "z");
        if (!ex.simplify().toString().equals("(x^(y * z))")) {
            System.out.println("#103:Error in bonus simplify of:" + ex.simplify());
        }
        ex = new Div(new Pow("x", 2), new Pow("y", 2));
        if (!ex.simplify().toString().equals("((x / y)^2.0)")) {
            System.out.println("#104:Error in bonus simplify of:" + ex);
        }
        ex = new Mult(new Pow("x", 2), new Pow("x", 3));
        if (!ex.simplify().toString().equals("(x^5.0)")) {
            System.out.println("#105:Error in bonus simplify of:" + ex);
        }
        ex = new Div(new Pow("x", 5), new Pow("x", 3));
        if (!ex.simplify().toString().equals("(x^2.0)")) {
            System.out.println("#106:Error in bonus simplify of:" + ex);
        }
        ex = new Div(new Pow("x", 2), new Pow("x", 3));
        if (!ex.simplify().toString().equals("(1.0 / x)")) {
            System.out.println("#107:Error in bonus simplify of:" + ex);
        }
        ex = new Mult(new Pow("x", 3), new Pow("y", 3));
        if (!ex.simplify().toString().equals("((x * y)^3.0)")) {
            System.out.println("#108:Error in bonus simplify of:" + ex);
        }
        ex = new Plus(new Mult("x", 2), new Mult("x", 4));
        if (!ex.simplify().toString().equals("(6.0 * x)")) {
            System.out.println("#109:Error in bonus simplify of:" + ex);
        }
        ex = new Mult(new Mult(2, "x"), new Mult("x", 3));
        if (!ex.simplify().toString().equals("(6.0 * (x^2.0))")) {
            System.out.println("#110:Error in bonus simplify of:" + ex);
        }
        ex = new Div(new Div(2, "x"), new Div("x", 3));
        if (!ex.simplify().toString().equals("(6.0 / (x^2.0))")) {
            System.out.println("#111:Error in bonus simplify of:" + ex);
        }
        ex = new Pow(-2, 0.5);
        if (!ex.toString().equals("(-2.0^0.5)")) {
            System.out.println("#112:Error in:" + ex);
        }
        try {
            System.out.println("#113:Error in evaluate:" + ex.evaluate());
        } catch (Exception e) {
        }
        try {
            System.out.println("#114:Error in simplify:" + ex.simplify() + "its: " + ex.simplify().toString());
        } catch (Exception e) {
        }
        
        
        System.out.println("...End of checking!");
        
        System.out.println("start checking 2!");
        Expression e2 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
        String s = e2.toString();
        System.out.println(s);
        //((x + y)^2.0)
        List<String> vars1 = e2.getVariables();
        for (String v : vars1) {
            System.out.println(v);
        }
        //x
        //y
        Expression e3 = e2.assign("y", e2);
        System.out.println(e3);
        //((x + ((x + y)^2.0))^2.0)
        Expression e4 = e3.assign("y", e3);
        System.out.println(e4);
        // (x + ((x + y)^2))^2
        e3 = e3.assign("x", new Num(1));
        System.out.println(e3);        // (1 + ((1 + y)^2))^2 - his answer
        //((1.0 + ((1.0 + y)^2.0))^2.0)
        Map<String, Double> assignment1 = new TreeMap<String, Double>();
        assignment1.put("x", 2.0);
        assignment1.put("y", 4.0);
        double value1 = e2.evaluate(assignment1);
        System.out.println("The result is: " + value1);
        //The result is: 36.0
        System.out.println(new Minus(new Num(1), new Num(2)).evaluate());
        //-1.0
        Expression e5 = new Pow(new Plus(new Var("x"), new Var("x")), new Var("y"));
        System.out.println(e5.getVariables());
        //[x,y]
        e2 = new Var("e");
        //System.out.println(e2.evaluate());//2.718281828459045
        //System.out.print(Math.pow(0, 0));
        System.out.println("end checking 2!");
        
        System.out.println("start checking 3!");
        
        Expression e = new Pow(new Var("x"), new Num(4));
        Expression de = e.differentiate("x");
        System.out.println(e);
        //x ^ 4
        System.out.println(de);
        //((x^4.0) * ((1.0 * (4.0 / x)) + (0.0 * log(e, x))))
        Expression e1 = new Sin(new Pow(new Var("x"), new Num(4)));
        Expression de1 = e1.differentiate("x");
        System.out.println(e1);
        //sin((x^4.0))
        System.out.println(de1.simplify());// his answer - 4 * (x ^ 3) * Cos(x ^ 4)
        //(cos((x^4.0)) * ((x^4.0) * (4.0 / x)))
        e1 = e1.assign("x", new Num(3));
        System.out.println(e1.evaluate());
        //0.9876883405951378
        Expression e21 = new Mult(new Num(3), new Var("x"));
        System.out.println(e21);// his answer - 3x
        //(3.0 * x)
        Expression e31 = new Plus(new Num(3), new Num(0));
        System.out.println(e31.simplify());
        //3.0
        Expression e41 = new Sin(new Plus(new Num(3), new Num(0)));
        System.out.println(e41.simplify()); //his answer - 0.1411200080598672
        //0.052335956242943835
        Expression e51 = new Log(new Sin(new Mult(new Num(3), new Num(0))), new Mult(4, "x"));
        System.out.println(e51);// his answer - Log((Sin(3*0)),4x)
        //log(sin((3.0 * 0.0)), (4.0 * x))
        Expression e6 = new Plus(new Pow("x", 2), new Pow("y", 2));
        System.out.println(e6.differentiate("x"));//his answer - 2*(x^(2 - 1)) + 0)
        //(((x^2.0) * ((1.0 * (2.0 / x)) + (0.0 * log(e, x)))) + ((y^2.0) * ((0.0 * (2.0 / y)) + (0.0 * log(e, y)))))
        System.out.println(e6.differentiate("x").simplify());//his answer - 2x
        //((x^2.0) * (2.0 / x))
        e6 = new Log(0, 1);
        try {
            System.out.println(e6.evaluate());// Exception!
        } catch (Exception e11) {
            System.out.println(e11.getMessage());
        }
        e6 = new Pow(0, 0);
        try {
            System.out.println(e6.evaluate());// Exception!
        } catch (Exception e11) {
            System.out.println(e11.getMessage());
        }
        
        
        System.out.println("end checking 3!");
        
        
        System.out.println("start checking 4!");
        Expression e11 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(2));
        System.out.println(e11);
        //((x + y)^2.0)
        System.out.println(e11.differentiate("x"));
        //(((x + y)^2.0) * (((1.0 + 0.0) * (2.0 / (x + y))) + (0.0 * log(e, (x + y)))))
        System.out.println(e11.differentiate("x").simplify());
        //(((x + y)^2.0) * (2.0 / (x + y)))
        e11 = new Pow(new Var("e"), new Var("x"));
        System.out.println(e11);
        //(e^x)
        System.out.println(e11.differentiate("x"));
        //((e^x) * ((0.0 * (x / e)) + (1.0 * log(e, e))))
        System.out.println(e11.differentiate("x").simplify());
        //(e^x)
        e11 = new Plus(new Sin(new Pow(new Var("e"), new Var("x"))), new Sin(new Var("y")));
        System.out.println(e11.differentiate("x"));
        //((cos((e^x)) * ((e^x) * ((0.0 * (x / e)) + (1.0 * log(e, e))))) + (cos(y) * 0.0))
        System.out.println(e11.differentiate("x").simplify());
        //(cos((e^x)) * (e^x))
        System.out.println(e11.differentiate("y"));//his answer - 1*Cos(y)
        //((cos((e^x)) * ((e^x) * ((0.0 * (x / e)) + (0.0 * log(e, e))))) + (cos(y) * 1.0))
        System.out.println(e11.differentiate("y").simplify());
        //cos(y)
        e11 = new Sin("y");
        System.out.println(e11.differentiate("x"));
        //(cos(y) * 0.0)
        System.out.println(e11.differentiate("x").simplify());
        //0.0
        e11 = new Sin(10);
        System.out.println(e11.differentiate("x"));
        //(cos(10.0) * 0.0)
        System.out.println(e11.differentiate("x").simplify());
        //0.0
        e11 = new Minus(5, -4);
        System.out.println(e11.simplify());
        //9.0
        e11 = new Mult(5, -4);
        System.out.println(e11);
        //(5.0 * -4.0)
        System.out.println(e11.simplify());
        //-20.0
        e11 = new Pow(3, new Var("x"));
        System.out.println(e11);
        //(3.0^x)
        System.out.println(e11.differentiate("x"));//his answer - ((e ^ x) * ((0.0 * (x / e)) + (1.0 * log(e, e))))
        //((3.0^x) * ((0.0 * (x / 3.0)) + (1.0 * log(e, 3.0))))
        System.out.println(e11.differentiate("x").simplify());
        //((3.0^x) * log(e, 3.0))
        e11 = new Pow("x", "x");
        System.out.println(e11);
        //(x^x)
        System.out.println(e11.differentiate("x")); //his answer - (x^x)*(1*Log(e,x))+(x*((1*1)/(x*Log(e,e))))
        //((x^x) * ((1.0 * (x / x)) + (1.0 * log(e, x))))
        System.out.println(e11.differentiate("x").simplify()); //his answer - (x^x)*(Log(e,x)+(x*(1/x)))
        //((x^x) * (1.0 + log(e, x)))
        e11 = new Pow("x", new Plus("x", "y"));
        System.out.println(e11);
        //(x^(x + y))
        System.out.println(e11.differentiate("x")); //his answer - (x^(x+y))*(((1+0)*Log(e,x))+((x+y)*((1*1)/(x*Log(e,e)))))
        //((x^(x + y)) * ((1.0 * ((x + y) / x)) + ((1.0 + 0.0) * log(e, x))))
        System.out.println(e11.differentiate("x").simplify()); //his answer - (x^(x+y))*(Log(e,x)+((x+y)*(1/x)))
        //((x^(x + y)) * (((x + y) / x) + log(e, x)))
        e11 = new Div(0, "y");
        System.out.println(e11);
        //(0.0 / y)
        System.out.println(e11.simplify());//his answer - 0
        //(0.0 / y)
        /*bonus
        e11 = new Div(new Plus("y", "x"), new Plus("x", "y"));
        System.out.println(e11); //(y+x)/(x+y)
        System.out.println(e11.simplify()); //1
        e11 = new Pow("x", "y");
        System.out.println(e11); //(y+x)/(x+y)
        System.out.println(e11.differentiate("x")); //1
        e11 = new Div(new Plus(new Plus("x", new Log(2, new Plus("x", "y"))), "z"), new Plus("z", new Plus(new Log(2, new Plus("y", "x")), "x")));
        System.out.println(e11); //((x + y) + z) / (z + (y + x))
        System.out.println(e11.simplify()); //1
        e11 =  new Div(new Cos(new Plus("x", "y")), new Cos(new Plus("y", "x")));
        System.out.println(e11); //((x + y) + z) / (z + (y + x))
        System.out.println(e11.simplify()); //1
        e11 =  new Log(new Mult(2, "x"), new Pow("x", 2));
        System.out.println(e11); //((x + y) + z) / (z + (y + x))
        System.out.println(e11.differentiate("x").simplify()); //1
        */
        e11 = new Div(5, 0);
        System.out.println(e11);
        //(5.0 / 0.0)
        try {
            System.out.println(e11.evaluate()); //RuntimeException
        } catch (Exception e111) {
            System.out.println(e111.getMessage());
        }
        
        System.out.println("end checking 4!");
        
        System.out.println("start test 1!");
        
     // part 1
        
        // Expression
        // getVariables
        // assign
        // evaluate
        // Var , Sin , Plus , Mult , Pow

        int gradePart1 = 0; 
        Expression e14 = null;
        Expression e22 = null;
        double value11 = 0;
        String valString = null;
        String s1 = null;
        Expression e23 = null;
        Map<String, Double> assignment11 = new TreeMap<String, Double>();
        List<String> vars11 = null;
 
 
//      System.out.println("-----------test 1----------------------------------");
 
 
        try
        {
            // ans is : (((x + y) + z)^2.0)
            e22 = new Pow(new Plus (new Plus(new Var("x"), new Var("y")) ,new Var("z")), new Num(2));
            s1 = e22.toString().toLowerCase().replaceAll("\\s+","").replaceAll("\\s+","");
 
            if (s1.equals("(((x+y)+z)^2.0)"))
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 1 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
//      System.out.println("-----------test 2----------------------------------");
 
 
        try
        {
            // ans is : (((x + y) + (z + w))^4.0)
            e23 = new Pow(new Plus (new Plus(new Var("x"), new Var("y")) , new Plus(new Var("z"), new Var("w")) ), new Num(4));
            s1 = e23.toString().toLowerCase().replaceAll("\\s+","");
 
            // (((x+y)+(z+w))^4.0)
            if (s1.equals("(((x+y)+(z+w))^4.0)"))
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 2 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
//      System.out.println("-----------test 3----------------------------------");
        // ans is : 234256.0
 
 
        try
        {
            // The result is: 234256.0
            assignment11.put("x",  2.0);
            assignment11.put("y",  4.0);
            assignment11.put("z",  6.0);
            assignment11.put("w",  10.0);
            value11 = e23.evaluate(assignment11);
 
            if (value11 == 234256.0)
            {
                gradePart1++;
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 3 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
//      System.out.println("-----------test 4----------------------------------");
 
        try
        {
            // get vars of e23
            // ans is (the order is not important) :
            // x
            // y
            // z
            // w
            vars11 = e23.getVariables();
            Collections.sort(vars11);
 
            List<String> correct = new ArrayList<String>();
            correct.add("x");
            correct.add("y");
            correct.add("z");
            correct.add("w");
 
            boolean ans = correct.retainAll(vars11);
            if (!ans)
            {
                gradePart1++;
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 4 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
//      System.out.println("-----------test 5----------------------------------");
 
        try
        {
            // (x + y)^0 = 1 , always !
            // ans is : 1.0 or 1
            Expression e211 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(0));
            value11 = e211.evaluate(assignment11);
            if (value11 == 1)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 5 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //      System.out.println("-----------test 6----------------------------------");
 
        try
        {
            // (x + y)^(-1)
            // Accepted answers :
            // 0.167
            // 0.166
 
            // ans is : (value >= 0.16 && value <= 0.17)
            // here we accept 0.16 + epsilon
 
            Expression e411 = new Pow(new Plus(new Var("x"), new Var("y")), new Num(-1));
            value11 = e411.evaluate(assignment11);
            value11  = Double.parseDouble(new DecimalFormat("##.###").format(value11));
 
            if (value11 >= 0.16 && value11 <= 0.17)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 6 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 7----------------------------------");
 
        try
        {
            // -(x)
            // ans is : -2 (the assignment is above)
 
            Expression e511 = new Neg(new Var("x"));
            valString =  e511.toString().toLowerCase().replaceAll("\\s+","");
            value11 = e511.evaluate(assignment11);
 
            if (value11 == -2)
            {
                gradePart1++;
            }
 
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 7 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 8----------------------------------");
        // ans is : x , where x = 2
        // final ans is : 2
        try
        {
            // -(-x) = x
            Expression e61 = new Neg(new Neg( new Var("x")));
            value11 = e61.evaluate(assignment11);
            if (value11 == 2)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 8 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 9----------------------------------");
 
 
        try
        {
            // -(-(-(-y))) = y
            // y equals to 4
            // final ans : 4.0
            Expression e7 = new Neg( new Neg(new Neg(new Neg(new Var("y")))));
            value11 = e7.evaluate(assignment11);
            if (value11 == 4)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 9 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 10----------------------------------");
 
 
        try
        {
            //   [ (-x) - (-y) ] ^ 2 = [ -x + y ] ^ 2 =  [ -2 + 4 ] ^ 2 = 2^2 = 4
            // ans is : 4.0
            Expression e8 = new Pow(new Minus(new Neg (new Var("x")), new Neg(new Var("y"))), new Num(2));
            valString = e8.toString().toLowerCase().replaceAll("\\s+","");
 
            assignment11.clear();
            assignment11.put("x",  -2.0);
            assignment11.put("y",  -4.0);
            value11 = e8.evaluate(assignment11);
            if (value11 == 4)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 10 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 11----------------------------------");
 
        try
        {
            // (-x) * (-(-y)) = (-)*x*y = -xy
            // -2 * 4 = - 8
            // ans is : -8
            Expression e9 = new Mult(new Neg(new Var("x")) , new Neg(new Neg(new Var("y"))));
            assignment11.clear();
            assignment11.put("x",  (double) -2);
            assignment11.put("y",  (double) -4);
            value11 = e9.evaluate(assignment11);
            if (value11 == -8)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 11 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 12----------------------------------");
 
        try
        {
            // log of 1 with base 2
            // ans is : 0
            Expression e10 = new Log(new Num(2) ,new Num(1));
            value11 = e10.evaluate(assignment11);
            if (value11 == 0)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 12 failed");
        }
 
 
        //      System.out.println("-----------test 13----------------------------------");
 
        try
        {
            // (cos(0) - cos(0)) - (cos0 + cos0)
            // 0 - (1 + 1) = 0 - 2 = -2
            // ans is : -2
            Expression e111 = new Cos(new Num (0));
            e111 = new Minus( new Minus(new Cos(new Num (0)) ,new Cos(new Num (0)) ) , new Plus(new Cos(new Num (0)) ,new Cos(new Num (0)) ));
            value11 = e111.evaluate();
            if (value11 == -2)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 13 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //      System.out.println("-----------test 14----------------------------------");
 
        try
        {
            // sin45 + cos 45
            // Accepted answer :
            // 1.414
            // ans is : 1.4 + epsilon
            Expression e12 = new Plus (new Cos(new Num (45)) , new Sin(new Num(45)));
            e12.toString().toLowerCase().replaceAll("\\s+","");
            value11 = e12.evaluate();
            value11  = Double.parseDouble(new DecimalFormat("##.###").format(value11));
 
            if (value11 >= 1.4 && value11 <= 1.5)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 14 failed");
        }
 
        //      System.out.println("-----------test 15----------------------------------");
 
        try
        {
            // sin(45)^2 + cos(45)^2 = 1 , trigonometric identity
            // ans is : 1.0
            Expression e13 = new Plus (new Pow(new Cos(new Num (45)) , new Num(2)) ,
                    new Pow(new Sin(new Num (45)) , new Num(2)));
            value11 = e13.evaluate();
 
            if (value11 == 1)
            {
                gradePart1++;
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 15 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 16----------------------------------");
 
        try
        {
            // -----(-2) plus -----(1) = 1
            // ans is : 1
            e14 = new Plus(
                    new Neg(new Neg(new Neg(new Neg(new Neg(new Num(-2)))))) ,
                    new Neg(new Neg(new Neg(new Neg(new Neg(new Num(1))))))
            );
            e14.toString().toLowerCase().replaceAll("\\s+","");
            value11 = e14.evaluate();
 
            if (value11 == 1)
            {
                gradePart1++;
            }
 
 
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 16 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 17----------------------------------");
 
        try
        {
            // get the var of e14 ---> should return none
            // ans is "null" or "0"
            vars11 = e14.getVariables();
 
            if (vars11 == null || vars11.size() == 0)
            {
                gradePart1++;
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 17 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        System.out.println(gradePart1);
        
        
        System.out.println("end test 1!");
        
        System.out.println("start test 2!");
        
         int gradePart2 = 17;
        Map<String, Double> assignment111 = new TreeMap<String, Double>();
        
        Expression e111 = null;
        Expression de11 = null;
        Expression simp = null;
        String simpStr = null;
        double value111 = 0;
        double vv = 0;
 
        //  System.out.println("-----------test 18----------------------------------");
        // given : x^10
        // after differentiate : 10x^9 , assigning x=10 , produces :
        // ans : 2621440
 
        try
        {
            e111 = new Pow(new Var("x"), new Num(10));
            de11 = e111.differentiate("x");
            de11 = de11.assign("x", new Num(4));
 
            value111 = de11.evaluate();
            if (value111 == 2621440)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 18");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 18 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 19----------------------------------");
 
        try
        {
            // exp = 1 , differentiate by x , return 0
            // ans is : 0
 
            e111 = new Num(1);
            de11 = e111.differentiate("x");
            value111 = de11.evaluate();
            if (value111 == 0)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 19");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 19 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 20----------------------------------");
        // -----(-2) , with differentiate , returns 0
        // ans is : 0
 
        try
        {
            e111 = new Neg(new Neg(new Neg(new Neg(new Neg(new Num(-2))))));
            de11 = e111.differentiate("x");
            value111 = de11.evaluate();
 
            if (value111 == 0)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 20");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 20 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 21----------------------------------");
 
        try
        {
            // x^4 , differentiate by y
            // ans : 0
            e111 = new Pow(new Var("x"), new Num(4));
            de11 = e111.differentiate("y");
            simp = de11.simplify();
            simpStr = simp.toString().toLowerCase().replaceAll("\\s+","");
 
            if (simpStr.equals("0.0") || simpStr.equals("0"))
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 21");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 21 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 22----------------------------------");
        // x*y , differentiate by y , result is : x
        // ans : x
        // final : 2
 
        try
        {
            assignment111.put("x",  2.0);
            assignment111.put("y",  4.0);
 
            e111 = new Mult(new Var("x") , new Var("y"));
            de11 = e111.differentiate("y");
            value111 = de11.evaluate(assignment111);
 
            if (value111 == 2)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 22");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 22 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 23----------------------------------");
        // e = 1/x
        // differentiate by x
        // e' = - (1 / x^2)  , assign x = 2
        // produces : - (1 / 4) = -0.25
        // should return 0
        // ans : -1/4 = -0.25
 
        try
        {
 
            e111 = new Div(new Num(1) , new Var("x"));
            e111 = e111.differentiate("x");
 
            assignment111.clear();
            assignment111.put("x", 2.0);
            vv = e111.evaluate(assignment111);
 
            if (vv == -0.25)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 23");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 23 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 24----------------------------------");
        // sin(x) / sin(x) , no differentiate
        // here we won't differentiate by x
        // ans : 1
 
        try
        {
 
            e111 = new Div( new Sin(new Var("x"))
                    ,new Sin(new Var("x")) );
            value111 = e111.evaluate(assignment111);
            if (value111 == 1)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 24");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 24 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 25----------------------------------");
        // x/x , with differentiate : x/x = 1 ===> (1)' = 0
        // ans is : 0
 
        try
        {
 
            e111 = new Div(new Var("x") , new Var("x"));
            e111 = e111.differentiate("x");
            value111 = e111.evaluate(assignment111);
            if (value111 == 0)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 25");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 25 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 26----------------------------------");
        // x + x
        // ans : 1 +1 = 2
 
        try
        {
 
            e111 = new Plus(new Var("x") , new Var("x"));
            e111 =  e111.differentiate("x");
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 2)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 26");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 26 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 27----------------------------------");
 
        try
        {
            // f(x) = x - x
            // f'(x) = 1 - 1 = 0
            // ans : 0
            e111 = new Minus(new Var("x") , new Var("x"));
            e111 = e111.differentiate("x");
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 0)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong!");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 27 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 28----------------------------------");
 
        try
        {
            // differentiate Sin(x) ===> return Cos(x)
            // cos(0) = 1
            // ans : 1
 
            e111 = new Sin(new Var("x"));  // sin(x)
            e111 = e111.differentiate("x");  // cos(x)
 
            assignment111.clear();
            assignment111.put("x", (double) 0);  // cos(0) = 1
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 1)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 28");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 28 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 29----------------------------------");
        // differentiate Cos(x) ===> return -Sin(x)
        // ans : -1
 
        try
        {
 
            e111 = new Cos(new Var("x"));      // cos(x)
            e111 = e111.differentiate("x");       // -sin(x)
            assignment111.clear();
            assignment111.put("x",  90.0);     // -sin(90) = -1
            value111 = e111.evaluate(assignment111);
 
            if (value111 == -1)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong!");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 29 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 30----------------------------------");
 
        try
        {
            // differentiate [Cos(x)]^2
            // -2 * cos(x) * sin(x)
            // after assignment ---> -0.866025403784
            // we will accept answers that are between : value >= -0.9 && value <= -0.8
 
            e111 = new Pow(new Cos(new Var("x")) , new Num(2));        // [cos(x)]^2
            e111 = e111.differentiate("x");       // 2 * cos(x) * (-sin(x)) = -2 * sin(x) * cos(x)
            assignment111.clear();
            assignment111.put("x",  60.0);     // -2 * sin(60) * cos(60) =  -0.866025403784
            assignment111.put("e", 2.71);
            value111 = e111.evaluate(assignment111);
 
            if (value111 >= -0.9 && value111 <= -0.8 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 30");
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 30 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 31----------------------------------");
 
        try
        {
            // differentiate [Cos(x)]^3
            // after : 3 * [cos(x)]^2 * (-sin(x))
            // assigning x = 60 =====> 3 * cos(60)^2 * (-sin(60))
            // answer : -0.649519052838
            // however : we will accept answers that are in the range of : value >= -0.7 && value <= -0.6
 
            e111 = new Pow(new Cos(new Var("x")) , new Num(3));  // [Cos(x)]^3
            e111 = e111.differentiate("x");                         // 3 * [cos(x)]^2 * (-sin(x))
            assignment111.clear();
            assignment111.put("x", 60.0);
            assignment111.put("e", 2.71);
            value111 = e111.evaluate(assignment111);                   // 3 * cos(60)^2 * (-sin(60)) = -0.649519052838
 
            if (value111 >= -0.7 && value111 <= -0.6 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 31");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 31 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //      System.out.println("-----------test 32----------------------------------");
        // differentiate : [Sin(x^2)] , by x
        // cos(x^2) * 2x
 
        try
        {
 
            e111 = new Sin(new Pow (new Var("x") , new Num(2)));       // sin(x^2)
            e111 = e111.differentiate("x");                               // cos(x^2) * 2x
            assignment111.clear();
            assignment111.put("x", 90.0);                              // cos(90^2) * 2 * 90 = -180
            assignment111.put("e", 2.71);
            value111 = e111.evaluate(assignment111);
 
            if (value111 == -180 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 32");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 32 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 33----------------------------------");
 
 
        try
        {
            // differentiate : x^2 * x^2 , assign 30
            // so : x^4 ====> differentiate =====> 4 * x^3 ====> 4 * (13^3)
            Expression ee3 = new Mult(new Pow(new Var("x") , new Num(2)) , new Pow(new Var("x") , new Num(2)));   // x^2 * x^2
            ee3 = ee3.differentiate("x"); // 4 * x^3
            assignment111.clear();
            assignment111.put("x", 13.0);
            assignment111.put("e", 2.71);
            value111 = ee3.evaluate(assignment111);   // 4 * 13^3
 
            if (value111 == 8788.0 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 33");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 33 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 34----------------------------------");
 
        try
        {
            // 4x * 20x = 80 * x^2
            // after differentiate --> 160x
            // with x = 1 , result is : 160
 
            e111 = new Mult(new Mult(new Var("x") , new Num(4)) ,
                    new Mult(new Var("x") , new Num(20)));
 
            e111 = e111.differentiate("x").simplify();
 
            assignment111.clear();
            assignment111.put("x", 1.0);
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 160)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 34");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 34 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 35----------------------------------");
 
        try
        {
            // 100x + 20x
            // = 120x
            // differentiate by "y"
            // produces 0
            e111 = new Plus(new Mult(new Var("x") , new Num(100)) ,
                    new Mult(new Var("x") , new Num(20))
            );
 
            e111 = e111.differentiate("y").simplify();
            value111 = e111.evaluate();
            if (value111 == 0 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 35");
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 35 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 36----------------------------------");
        // [-cos(x)] + [-sin(x)] + cos(x) + sin(x) = 0
 
        try
        {
            e111 = new Plus( new Plus(new Neg(new Cos(new Var("x"))) , new Neg(new Sin(new Var("x"))) ) ,
                    new Plus(new Cos(new Var("x")) , new Sin(new Var("x")))
            );
 
            assignment111.clear();
            assignment111.put("x", 0.0);
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 0 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 36");
            }
 
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 36 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 37----------------------------------");
        // -cos(x) * (-sin(x)) = cos(x) * sin(x)
        // From infi : (-sin(x)) * sin(x) + cos(x) * cos(x) = -sin(x)^2 + cos(x)^2 = cos(x)^2 - sin(x)^2
        // Note that : cos(x)^2 = [cos(x)]^2
        // ans is : cos(x)^2 - sin(x)^2
        // assign x = 0 =======> cos(0)^2 - sin(0)^2 = 1 - 0 = 1
        // ans is : 1
 
        try
        {
 
 
            e111 = new Mult(new Neg(new Cos(new Var("x"))) , new Neg(new Sin(new Var("x"))) );
            e111 = e111.differentiate("x");
            assignment111.clear();
            assignment111.put("x", 0.0);
            value111 = e111.evaluate(assignment111);
 
            if (value111 == 1 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 37");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 37 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 38----------------------------------");
 
        try
        {
            // cos(-x) + sin(-x)
            // result : -sin(x) -cos(x)
            // should return -1
 
            e111 = new Plus(new Cos(new Neg(new Var("x"))) , new Sin(new Neg(new Var("x"))));  // cos(-x) + sin(-x)
            e111 = e111.differentiate("x");                                                       // -1 * (-1) * sin(-x) + cos(-x) * (-1) = sin(-x) - cos(-x)
            // note that : sin(-x) = -sin(x)
            // also : cos(-x) = cos(x)
            // hence : sin(-x) - cos(-x)  = -sin(x) - cos(x)
 
            assignment111.clear();
            assignment111.put("x", 0.0);       // sin(-0) - cos(-0) = 0 - 1 = -1
 
            value111 = e111.evaluate(assignment111);
 
            if (value111 == -1 )
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 38");
            }
        }
 
 
        catch(Exception exp)
        {
            System.out.println("Test 38 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //  System.out.println("-----------test 39----------------------------------");
 
        try
        {
            // -e^(-x) , Euler's e number :) Some respect baby !
            // after differentiate : (-) * (-1) * e^(-x) = e^(-x)
            // final ans : we accepted answers in the range : value >= 2.7 && value <= 2.8
 
 
            e111 = new Mult(new Num(-1) , new Pow(new Var("e"), new Mult(new Var("x") , new Num(-1))));  // -e^(-x)
            e111 = e111.differentiate("x");                                                                 // (-) * (-1) * e^(-x) = e^(-x)
            e111 = e111.assign("e", new Num(2.71));
            e111 = e111.assign("x", new Num(-1));  // e^(--1) = e^1 2.718...
            value111 = e111.evaluate();
 
 
            if (value111 >= 2.7 && value111 <= 2.8)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 39");
            }
 
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 39 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 40----------------------------------");
 
        try
        {
            // e^2x
            // should return : 2 *e^2x
            // with value "1" , returns : 14.778112197861299
            // 14.778
            // We'll accept answers that are in the range : value >= 14 && value <= 15
 
            e111 = new Pow(new Var("e"), new Mult(new Var("x") , new Num(2)));     // e^(2x)
            e111 = e111.differentiate("x").simplify();                                // 2 * e^(2x)
 
            assignment111.clear();
            assignment111.put("x", 1.0);       // 2 * e^2 = 14.7781121978613005
            assignment111.put("e", 2.71);
            value111 = e111.evaluate(assignment111);
 
            if (value111 >= 14 && value111 <= 15)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong!");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 40 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 41----------------------------------");
 
 
        try
        {
            // e^x / e^x = 1
            // Ans : 1
            e111 = new Div(new Pow(new Var("e"), new Var("x")) , new Pow(new Var("e"), new Var("x")) );
            simpStr = e111.simplify().toString();
 
            if (simpStr.equals("1.0") || simpStr.equals("1"))
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong! 41");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 41 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 42----------------------------------");
 
        try
        {
            // e^3x / e^x = e^2x
            // x = 0.5
            // result is : e^1 = 2.718
            // Accepted answers are in the range : value >= 2.7 && value <= 2.8
 
            e111 = new Div(        new Pow(new Var("e"), new Mult(new Var("x") , new Num(3))) ,
                    new Pow(new Var("e"), new Var("x")) );
            assignment111.clear();
            assignment111.put("x", 0.5);
            assignment111.put("e", 2.71);
            value111 = e111.evaluate(assignment111);
            value111  = Double.parseDouble(new DecimalFormat("##.###").format(value111));
 
            if (value111 >= 2.7 && value111 <= 2.8)
            {
                gradePart2++;
            }
            else
            {
                System.out.println("Wrong!");
            }
        }
 
        catch(Exception exp)
        {
            System.out.println("Test 42 failed");
        }
 
        catch(StackOverflowError t)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        System.out.println(gradePart2);
        
        System.out.println("end test 2!");
        
        System.out.println("start test 3!");
        int gradePart3 = 42;
        
        Map<String, Double> assignment1111 = new TreeMap<String, Double>();
        Expression e1111 = null;
        double value1111 = 0;
        String s11 = null;
 
        //  System.out.println("-----------test 43----------------------------------");
 
        try
        {
            // ((2*8)-6)^2 =====> 100
            e1111 = new Mult(new Num(2) , new Num(8));
            e1111 = new Pow( new Minus(new Mult(new Num(2) , new Num(8)) , new Num(6)), new Num(2));
            value1111 = e1111.evaluate();
            if (value1111 == 100)
            {
                gradePart3++;
            }
        }
 
 
        catch(Exception exp1)
        {
            System.out.println("Test 43 failed");
        }
 
        catch(StackOverflowError t1)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 44----------------------------------");
 
        try
        {
            // ((((2.0 * 8.0) - 6.0)^2.0) * 0.0) = 100 * 0 = 0
            // ans : 0
            e1111 = new Mult( new Pow( new Minus(new Mult(new Num(2) , new Num(8)) , new Num(6)), new Num(2))
                    , new Num(0));
            value1111 = e1111.evaluate();
 
            if (value1111 == 0)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp11)
        {
            System.out.println("Test 44 failed");
        }
 
        catch(StackOverflowError t11)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 45----------------------------------");
 
        try
        {
            // ((((2.0 * 8.0) - 6.0)^2.0) / (((2.0 * 8.0) - 6.0)^2.0)) =
            // 100 / 100 = 1
            e1111 = new Div(new Pow( new Minus(new Mult(new Num(2) , new Num(8)) , new Num(6)), new Num(2)) ,
                    new Pow( new Minus(new Mult(new Num(2) , new Num(8)) , new Num(6)), new Num(2)));
            e1111 = e1111.simplify();
            if (e1111.toString().equals("1.0") || e1111.toString().equals("1"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp111)
        {
            System.out.println("Test 45 failed");
        }
 
        catch(StackOverflowError t111)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 46----------------------------------");
 
        try
        {
 
            // ((((x * 2.0) + (x * 2.0)) + ((x * 2.0) + (x * 2.0))) * 1.0)
            // x = 2
            // 8x * 1 = 8x
            // ans : 16
 
            e1111 = new Mult(new Plus(new Plus(new Mult(new Var("x") , new Num(2)) ,new Mult(new Var("x") , new Num(2)) ) , new Plus(new Mult(new Var("x") , new Num(2)) ,new Mult(new Var("x") , new Num(2)) )) , new Num(1));
            e1111 = e1111.assign("x", new Num(2));
            value1111 = e1111.evaluate();
 
            if (value1111 == 16)
            {
                gradePart3++;
            }
 
        }
 
        catch(Exception exp12)
        {
            System.out.println("Test 46 failed");
        }
 
        catch(StackOverflowError t12)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //  System.out.println("-----------test 47----------------------------------");
 
 
        try
        {
 
            // ((e^(((2.0 * 8.0) - 6.0)^2.0)) * 1.0)
            // (e^100.0) * 1 = (e^100.0)
 
            e1111 = new Mult(new Pow(new Var("e"), new Pow( new Minus(new Mult(new Num(2) , new Num(8)) , new Num(6)), new Num(2))) , new Num(1));
            e1111 = e1111.simplify();
            s11 = e1111.toString().toLowerCase().replaceAll("\\s+","").replaceAll("\\s+","");
            s11 = s11.toLowerCase();
 
            if (s11.equals("(e^100.0)") || s11.equals("e^100.0") || s11.equals("e^100") || s11.equals("(e^100)"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp13)
        {
            System.out.println("Test 47 failed");
        }
 
        catch(StackOverflowError t13)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //      System.out.println("-----------test 48----------------------------------");
 
        try
        {
            // ((((12.0 - 3.0) * 8.0) + 20.0) / 4.0)
            // ans ---> 23
 
            e1111 = new Div(new Plus(new Mult(new Minus(new Num(12) , new Num(3)) , new Num(8)) , new Num(20)) , new Num(4));
            s11 = e1111.simplify().toString();
            if (s11.equals("23.0") || s11.equals("23"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp14)
        {
            System.out.println("Test 48 failed");
        }
 
        catch(StackOverflowError t14)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 49----------------------------------");
        // (((-1.0 + 1.0) + (-1.0 + 1.0)) + ((-1.0 + 1.0) + (-1.0 + 1.0)))
        // ans = 0
        try
        {
            Expression a1 = new Plus( new Plus( new Plus(new Num(-1) , new Num(1)) , new Plus(new Num(-1) , new Num(1)))
                    ,new Plus( new Plus(new Num(-1) , new Num(1)) , new Plus(new Num(-1) , new Num(1))) );
 
            double d = a1.evaluate();
            if (d == 0)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp15)
        {
            System.out.println("Test 49 failed");
        }
 
        catch(StackOverflowError t15)
        {
            System.out.println("Caught StackOverflowError");
        }
        //      System.out.println("-----------test 50----------------------------------");
 
 
        try
        {
            // like : X - X = 0
            // ((x^5.0) - (x^5.0))
            // 5x^4 - 5x^4
 
            e1111 = new Minus(new Pow(new Var("x"), new Num(5)) ,new Pow(new Var("x"), new Num(5)) );
            Expression de111 = e1111.differentiate("x");
            Expression simp1 = de111.simplify();  // 0.0
 
            assignment1111.clear();
            assignment1111.put("x", 13.0);
            value1111 = simp1.evaluate(assignment1111);
 
            if (value1111 == 0)
            {
                gradePart3++;
            }
 
        }
 
        catch(Exception exp16)
        {
            System.out.println("Test 50 failed");
        }
 
        catch(StackOverflowError t16)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 51----------------------------------");
 
        try
        {
            // like : 0 - X = -X
            // (0.0 - (e^(x * 2.0)))
            // should return : -7.389
            // We accepted answers in the range : value >= -7.4 && value <= -7.3
 
            e1111 = new Minus(new Num(0) , new Pow(new Var("e"), new Mult(new Var("x") , new Num(2))));
            assignment1111.clear();
            assignment1111.put("x", 1.0);
            assignment1111.put("e", 2.71);
            value1111 = e1111.evaluate(assignment1111);
            value1111  = Double.parseDouble(new DecimalFormat("##.###").format(value1111));
 
            if (value1111 >= -7.4 && value1111 <= -7.3 )
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp17)
        {
            System.out.println("Test 51 failed");
        }
 
        catch(StackOverflowError t17)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 52----------------------------------");
 
        try
        {
            // like : 0 - X = -X
            // ((0.0 - 0.0) - (0.0 - (e^(x * 2.0))))
            // 0 - 0 - (-e^2x)  = e^(2x)
            // after assignment ---> 7.389
            e1111 = new Minus( new Minus(new Num(0) , new Num(0)),
                    new Minus(new Num(0) , new Pow(new Var("e"), new Mult(new Var("x") , new Num(2)))));
 
            value1111 = e1111.evaluate(assignment1111);
            // some cosmetics - uncomment this for some cosmetics to your answer
            // value  = Double.parseDouble(new DecimalFormat("##.###").format(value));
 
            if (value1111 >= 7.3 && value1111 <= 7.4)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp18)
        {
            System.out.println("Test 52 failed");
        }
 
        catch(StackOverflowError t18)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 53----------------------------------");
 
        try
        {
            // ((Log((2.0 * z), (2.0 * z))) * x)
            // Log(2z,2z) * x = x
            e1111 = new Mult(new Log(new Mult(new Num(2) , new Var("z")) , new Mult(new Num(2) , new Var("z"))) ,
                    new Var("x"));
 
            if (e1111.simplify().toString().equals("x"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp19)
        {
            System.out.println("Test 53 failed");
        }
 
        catch(StackOverflowError t19)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 54----------------------------------");
 
        try
        {
            // (((Log((2.0 * z), (2.0 * z))) * (Cos(0.0))) * (Sin(90.0)))
            // Log(2z,z2) * Cos(0) * sin(90) = 1
            // 1 * 1 * 1 = 1
 
            e1111 = new Mult(new Mult(new Log(new Mult(new Num(2) , new Var("z")) , new Mult(new Num(2) , new Var("z"))) , new Cos(new Num(0))) , new Sin(new Num(90)) );
 
            if (e1111.simplify().toString().equals("1") || e1111.simplify().toString().equals("1.0"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp2)
        {
            System.out.println("Test 54 failed");
        }
 
        catch(StackOverflowError t2)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        // System.out.println("-----------test 55----------------------------------");
 
 
        try
        {
            // (((3.0 + 10.0) * x) + ((y * 15.0) * (Sin(0.0))))
 
            e1111 = new Plus (new Mult(new Plus(new Num(3) , new Num(10))  , new Var("x"))  ,new Mult( new Mult(new Var("y") , new Num(15)) , new Sin(new Num(0))) );
            // no need of "y" anymore ...
 
            e1111 = e1111.simplify();
            e1111 = e1111.assign("x", new Num(4));
 
            if (e1111.evaluate() == 52.0  || e1111.evaluate() == 52)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp22)
        {
            System.out.println("Test 55 failed");
        }
 
        catch(StackOverflowError t22)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
 
        //      System.out.println("-----------test 56----------------------------------");
        // check if it still can be here ...
 
        try
        {
            // ((((20.0 * (x^5.0)) * (3.0 * (e^x))) * (Cos((x^2.0)))) * (x - x))
            // (20*x^5 * 3e^x ) * Cos(x^2) * (x - x)
            // this little beauty equals 0
 
            e1111 = new Mult(new Mult( new Mult( new Mult(new Num(20) ,new Pow(new Var("x") , new Num(5))) , new Mult(new Num(3) , new Pow(new Var("e"), new Var("x"))) )
                    , new Cos(new Pow(new Var("x") , new Num(2)))) , new Minus(new Var("x") , new Var("x")));
 
 
            if (e1111.simplify().toString().equals("0.0") || e1111.simplify().toString().equals("0"))
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp23)
        {
            System.out.println("Test 56 failed");
        }
 
        catch(StackOverflowError t23)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 57----------------------------------");
 
        try
        {
            // ((((20.0 * (x^5.0)) + (3.0 * (e^x))) * (Cos((x^2.0)))) + (x - x))
            // answer is : 3.0
 
            e1111 = new Plus(new Mult( new Plus( new Mult(new Num(20) ,new Pow(new Var("x") , new Num(5))) , new Mult(new Num(3) , new Pow(new Var("e"), new Var("x"))) )
                    , new Cos(new Pow(new Var("x") , new Num(2)))) , new Minus(new Var("x") , new Var("x")));
            assignment1111.clear();
            assignment1111.put("x", 0.0);
            assignment1111.put("e", 2.71);
            value1111 = e1111.evaluate(assignment1111);
 
            if (value1111 == 3.0 || value1111 == 3)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp25)
        {
            System.out.println("Test 57 failed");
        }
 
        catch(StackOverflowError t25)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 58----------------------------------");
 
        // new one :
 
        // x * 0 = 0
 
        try
        {
            // ((((e^x) * (e^x)) * ((e^x) * (e^x))) * 0.0)
            // ans : 0
 
            e1111 = new Mult(new Mult(new Mult (new Pow(new Var("e"), new Var("x")) , new Pow(new Var("e"), new Var("x"))) , new Mult (new Pow(new Var("e"), new Var("x")) , new Pow(new Var("e"), new Var("x")))) , new Num(0));
            e1111 = e1111.assign("x", new Num(4));
            e1111 = e1111.assign("e", new Num(2.71));
            value1111 = e1111.evaluate();
            if (value1111 == 0)
            {
                gradePart3++;
            }
 
        }
 
        catch(Exception exp26)
        {
            System.out.println("Test 57 failed");
        }
 
        catch(StackOverflowError t26)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 59----------------------------------");
 
        // new one : x = 0 , e = 2.71
        // your output should be : 1
        // [ (e^x * e^2x) / (e^3x) ]  * [(e^4x * e^3x) / e^7x] * [e^15x / (e^7x * e^8x)]
 
        try
        {
            // ((((e^x) * ((e^x)^2.0)) / ((e^x)^3.0)) * ((((e^x)^4.0) * ((e^x)^3.0)) / ((e^x)^7.0)))
            // ans : 1
 
            e1111 = new Mult(new Div(new Mult(new Pow(new Var("e"), new Var("x")) ,new Pow(new Pow(new Var("e"), new Var("x")) , new Num(2))) , new Pow(new Pow(new Var("e"), new Var("x")) , new Num(3)))
                    , new Div( new Mult(new Pow(new Pow(new Var("e"), new Var("x")) , new Num(4)) , new Pow(new Pow(new Var("e"), new Var("x")) , new Num(3)) ) ,e1111 = new Pow(new Pow(new Var("e"), new Var("x")) , new Num(7)) ));
 
            e1111 = e1111.assign("e", new Num(2.71));
            e1111 = e1111.assign("x", new Num(0));
            value1111 = e1111.evaluate();
 
            if (value1111 == 1)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp27)
        {
            System.out.println("Test 57 failed");
        }
 
        catch(StackOverflowError t27)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
 
 
        //  System.out.println("-----------test 60----------------------------------");
 
        // (((((e^x) * ((e^x)^2.0)) / ((e^x)^3.0)) / ((((e^x)^4.0) * ((e^x)^3.0)) / ((e^x)^7.0))) * (Sin(0.0)))
        // x = 0
        // your output should be : 0
 
        try
        {
            e1111 = new Mult(new Div( new Div(new Mult(new Pow(new Var("e"), new Var("x")) ,new Pow(new Pow(new Var("e"), new Var("x")) , new Num(2))) , new Pow(new Pow(new Var("e"), new Var("x")) , new Num(3)))
                    , new Div( new Mult(new Pow(new Pow(new Var("e"), new Var("x")) , new Num(4)) , new Pow(new Pow(new Var("e"), new Var("x")) , new Num(3)) ) ,e1111 = new Pow(new Pow(new Var("e"), new Var("x")) , new Num(7)) )) , new Sin(new Num(0)));
 
            e1111 = e1111.assign("e", new Num(2.71));
            e1111 = e1111.assign("x", new Num(0));
            value1111 = e1111.evaluate();
 
            if (value1111 == 0)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp29)
        {
            System.out.println("Test 57 failed");
        }
 
        catch(StackOverflowError t29)
        {
            System.out.println("Caught StackOverflowError");
        }
 
        //      System.out.println("-----------test 61----------------------------------");
 
        try
        {
            // (Sin(-(-(-(-(-(0.0))))))) = 0
            // -0 is also ok
 
            e1111 = new Sin(new Neg(new Neg(new Neg(new Neg(new Neg(new Num(0)))))));
            value1111 = e1111.evaluate();
 
            if (value1111 == 0)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp3)
        {
            System.out.println("Test 61 failed");
        }
 
        catch(StackOverflowError t31)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
        //      System.out.println("-----------test 62 ----------------------------------");
 
        try
        {
            // (x + y)
            // check if an exception is thrown when there is a missing assignment
            e1111 = new Plus(new Var("x") , new Var("y"));
            e1111 = e1111.assign("x", new Num(2));
            // e = e.assign("y", new Num(2));
            value1111 = e1111.evaluate();
        }
 
        catch(Exception exp32)
        {
            // System.out.println("Test 62  succeeded");
            gradePart3++;
        }
 
        catch(StackOverflowError t32)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
 
        //      System.out.println("-----------test 63 ----------------------------------");
 
        try
        {
            // (((x * 2.0) - ((x * 2.0) + (y * 3.0))) * ((-(9.0) / -(9.0)) + ((-(9.0) / -(9.0)) * -(-1.0))))
            // (((2.0 * 2.0) - ((2.0 * 2.0) + (3.0 * 3.0))) * ((-(9.0) / -(9.0)) + ((-(9.0) / -(9.0)) * -(-1.0))))
            e1111 = new Mult(new Minus(new Mult("x" , 2) , new Plus(new Mult("x" , 2) , new Mult("y" , 3))) ,new Plus(new Div(new Neg(9) , new Neg(9)) , new Mult(new Div(new Neg(9) , new Neg(9)) , new Neg(-1))) );
            e1111 = e1111.assign("x", new Num(2));
            e1111 = e1111.assign("y", new Num(3));
            value1111 = e1111.evaluate();
            if (value1111 == -18)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp33)
        {
            System.out.println("Test 63  failed");
        }
 
        catch(StackOverflowError t33)
        {
            System.out.println("Caught StackOverflowError");
        }
 
 
 
        try
        {
            //      System.out.println("-----------test 64 ----------------------------------");
            // ((1.0 + -(-9.0)) * -(-(-(-(-(-(-(-(-(-(-(-(-1.0))))))))))))) = -10.0
            e1111 = new Mult(new Plus("x" , new Neg(-9)) ,new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(new Neg(-1)))))))))))) );
            e1111 = e1111.assign("x", new Num(1));
            value1111 = e1111.evaluate();
 
 
            if (value1111 == -10)
            {
                gradePart3++;
            }
        }
 
        catch(Exception exp34)
        {
            System.out.println("Test 64  failed");
        }
 
        catch(StackOverflowError t34)
        {
            System.out.println("Caught StackOverflowError");
        }
 
       System.out.println(gradePart3);
       //Jerbi
       ex = new Minus(new Minus(new Mult(0, "x"), new Mult(1, 10)), "x");
       System.out.println("test " + ex.simplify());
       //Jhonatan check
       Expression e33=new Log( new Num(4) , new Pow( new Var("w") , new Pow( new Div( new Num(0) , new Div( new Plus( new Div( new Pow( new Var("x") , new Num(1) ) , new Num(2) ) , new Plus( new Plus( new Var("w") , new Var("y") ) , new Num(0) ) ) , new Var("x") ) ) , new Num(0) ) ) );
       Map<String, Double> assigment33 = new TreeMap<String, Double>();
       assigment33.put("x", 0.1);
       assigment33.put("y", 0.2);
       assigment33.put("z", 0.3);
       assigment33.put("w", 0.4);
       System.out.println("Expression " + e33.toString());
       Expression e331 = e33.assign("x", new Num(0.1));
       Expression e332 = e331.assign("y", new Num(0.2));
       Expression e333 = e332.assign("z", new Num(0.3));
       Expression e334 = e333.assign("w", new Num(0.4));
       System.out.println("Expression with assign " + e334.toString());
       System.out.println("only evaluate " + Double.toString(e33.evaluate(assigment33)));
       System.out.println("with simplifye " + e33.simplify().evaluate(assigment33));
       
	}
	
	      
}    
