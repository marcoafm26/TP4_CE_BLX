package mutation;

public class None implements Mutation{
    @Override
    public double[] getMutation(double[] x, double[] lowerBound, double[] upperBound) {
        return x;
    }
}
