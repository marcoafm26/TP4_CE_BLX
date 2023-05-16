package model;

import java.util.ArrayList;

public class Ponto {
    public double[] genes;
    public ArrayList<Ponto> S;
    public int n;
    public int rank;

    public Individuo individuo;

    public Ponto(Individuo individuo){
        this.individuo = individuo;
        this.genes = individuo.getGenes();
    }


}
