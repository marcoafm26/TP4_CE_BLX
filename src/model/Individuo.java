package model;

import mutation.BLXAlpha;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individuo implements  Comparable<Individuo> {

    private double[] genes;
    private double[] avaliacao;
    private double Cd;
    private Random random;

    public Individuo(int QTD_GENES,int QTD_AVALIACAO){
        this.genes = new double[QTD_GENES];
        this.avaliacao = new double[QTD_AVALIACAO];
        this.random = new Random();
    }

    public BLXAlpha blxAlpha;
    public Mutation mutation;

    public Individuo(double[] values, int qntAvaliation) {
        this( values, qntAvaliation, new BLXAlpha(0.1), new MutationNone());
    }
    public Individuo(double[] values, double[] qntAvaliation) {
        this( values, qntAvaliation, new BLXAlpha(0.1), new MutationNone());
    }
    public Individuo(double[]  values, int qntAvaliation, BLXAlpha blxAlpha, Mutation mutation){
        values = values;
        avaliation = new double[qntAvaliation];
        this.blxAlpha = blxAlpha;
        this.mutation = mutation;
    }

    public Individuo(double[]  values, double[] qntAvaliation, BLXAlpha blxAlpha, Mutation mutation){
        values = values;
        avaliation = qntAvaliation;
        this.blxAlpha = blxAlpha;
        this.mutation = mutation;
    }
    public List<Individuo> recombinar(Individuo p2){
        List<Individuo> filhos = new ArrayList<>(2);

        double[] varsP1 = this.genes;
        double[] varsP2 = p2.genes;

        double[][] filhosMat = blxAlpha.getOffSpring(this.genes, p2.genes, new double[]{-10, -10}, new double[]{10, 10});

        Individuo f1 = new Individuo(this.genes, filhosMat[0]);
        Individuo f2 = new Individuo(this.genes, filhosMat[1]);

        filhos.add(f1);
        filhos.add(f2);

        return  filhos;
    }

    public void mutar(){
        this.values = mutation.getMutation(this.values, new double[]{-10, -10}, new double[]{10, 10});
    }

    public void avaliar(int FUNCTION){
        switch (FUNCTION){
            case 1:
                // problema 1
                this.avaliacao[0] = (Math.pow(this.genes[0],2));
                this.avaliacao[1] = Math.pow(this.genes[0]-1,2);
                break;
            case 2:
                // problema 2
                this.avaliacao[0] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1],2));
                this.avaliacao[1] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1]-2,2));
                break;
            case 3:
                // problema 3

                this.avaliacao[0] = (Math.pow(this.genes[0]-1,2))+(Math.pow(this.genes[1],2))+(Math.pow(this.genes[2],2));
                this.avaliacao[1] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1]-1,2))+(Math.pow(this.genes[2],2));
                this.avaliacao[2] = (Math.pow(this.genes[0],2))+(Math.pow(this.genes[1],2))+(Math.pow(this.genes[2]-1,2));
                break;
        }



    }

    public void gerarGenes(double a, double b){
        for (int i = 0; i < this.genes.length; i++) {
            this.genes[i] = random.nextDouble() * b * 2 + a;
        }
    }
    public double[] getAvaliation() {
        return avaliacao;
    }
    public void setAvaliacao(double[] avaliacao) {
        this.avaliacao = avaliacao;
    }
    public double[] getGenes() {
        return genes;
    }

    public void setGenes(double[] genes) {
        this.genes = genes;
    }

    public double getCrowdingDistance() {
        return Cd;
    }

    public void setCrowdingDistance(double crowdingDistance) {
        this.Cd = crowdingDistance;
    }

    @Override
    public int compareTo(Individuo i2) {
        return Double.compare(i2.Cd, this.Cd);
    }

    @Override
    public String toString() {
        return "\nGenes: " +
                Arrays.toString(this.genes) +
                "\nAvaliação: " +
                Arrays.toString(this.avaliacao);
    }
}



