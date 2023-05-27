package mutation;

import java.util.Random;

public class BLXAlpha {

    public double standardDeviation;
    public boolean invertGens;
    public BLXAlpha(double standardDeviation){
        this.standardDeviation = standardDeviation;
    }
    public BLXAlpha(double standardDeviation, boolean invertGens){
        this.standardDeviation = standardDeviation;
        this.invertGens = invertGens;
    }
    public double[][] getOffSpring(double[] values_1, double[] values_2, double[] lowerBounds, double[] upperBounds){

        double[] child_1 = new double[values_1.length];
        double[] child_2 = new double[values_1.length];

        Random random = new Random();

        for(int i = 0; i < values_1.length; i++){
            double c1 = 0;
            double c2 = 0;

            if(invertGens){
                if(random.nextDouble() > 0.5){
                    c1 = values_1[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
                    c2 = values_2[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
                }else{
                    c1 = values_2[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
                    c2 = values_1[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
                }
            }else{
                c1 = values_1[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
                c2 = values_2[i] + (random.nextGaussian() * standardDeviation) * Math.abs(values_1[i] - values_2[i]);
            }

            c1 = repairSolution(c1, lowerBounds[i], upperBounds[i]);
            c2 = repairSolution(c2, lowerBounds[i], upperBounds[i]);

            child_1[i] = c1;
            child_2[i] = c2;
        }
        double[][] children = new double[2][];
        children[0] = child_1;
        children[1] = child_2;

        return children;
    }
        public double repairSolution(double value, double lowerBound, double upperBound){
            if(value < lowerBound){
                value = lowerBound;
            }else if(value > upperBound){
                value = upperBound;
            }
            return value;
        }
}
