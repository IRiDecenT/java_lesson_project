
public abstract class Algorithm {
    private String name;

    public Algorithm(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public abstract double Compute(double a, double b);
}