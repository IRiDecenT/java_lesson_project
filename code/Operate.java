import java.util.HashMap;
import java.util.Map;

public class Operate {
    private Map<String, Algorithm> opList;

    public Operate() {
        opList = new HashMap<String, Algorithm>();

        Algorithm Plus = new Algorithm("+"){

            @Override
            public double Compute(double a, double b) {
                return b + a;
            }
        };

        this.addAlgorithm(Plus);

        Algorithm Minus = new Algorithm("-"){

            @Override
            public double Compute(double a, double b) {
                return b - a;
            }
        };

        this.addAlgorithm(Minus);

        Algorithm Multiple = new Algorithm("*"){

            @Override
            public double Compute(double a, double b) {
                return b * a;
            }
        };

        this.addAlgorithm(Multiple);

        Algorithm Divide = new Algorithm("/"){

            @Override
            public double Compute(double a, double b) {
                return b / a;
            }
        };

        this.addAlgorithm(Divide);

        Algorithm Pow = new Algorithm("^"){

            @Override
            public double Compute(double a, double b) {
                return Math.pow(b, a);
            }
        };

        this.addAlgorithm(Pow);

        //System.out.println(opList.toString());
    }

    public void addAlgorithm(Algorithm ali) {
        opList.put(ali.getName(), ali);
    }

    public Algorithm getAlgorithm(String name) {
        return (Algorithm)opList.get(name);
    }
}