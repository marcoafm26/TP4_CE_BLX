package model;

import mutation.BLXAlpha;
import mutation.Mutation;
import mutation.None;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Individuo implements  Comparable<Individuo> {

    private double[] genes;
    private double[] avaliacao;
    private double Cd;
    private Random random;
    public BLXAlpha blxAlpha;
    public Mutation mutation;
    public Individuo(int QTD_GENES,int QTD_AVALIACAO){
        this.genes = new double[QTD_GENES];
        this.avaliacao = new double[QTD_AVALIACAO];
        this.random = new Random();
        this.blxAlpha = new BLXAlpha(0.1);
    }

    public Individuo(double[] genes){
        this.genes =genes;
    }
    public Individuo(double[] genes, int qntAvaliation) {
        this( genes, qntAvaliation, new BLXAlpha(0.1), new None());
    }
    public Individuo(double[] genes, double[] avaliacao) {
        this( genes, avaliacao, new BLXAlpha(0.1), new None());
    }
    public Individuo(double[]  genes, int qntAvaliation, BLXAlpha blxAlpha, Mutation mutation){
        this.genes = genes;
        this.avaliacao = new double[qntAvaliation];
        this.blxAlpha = blxAlpha;
        this.mutation = mutation;
    }

    public Individuo(double[]  genes, double[] avaliacao, BLXAlpha blxAlpha, Mutation mutation){
        this.genes = genes;
        this.avaliacao = avaliacao;
        this.blxAlpha = blxAlpha;
        this.mutation = mutation;
    }
    public List<Individuo> recombinar(Individuo p2,int FUNCTION){
        List<Individuo> filhos = new ArrayList<>(2);

        double[][] filhosMat = blxAlpha.getOffSpring(this.genes, p2.genes, new double[]{-10, -10}, new double[]{10, 10});
        Individuo f1 = new Individuo(filhosMat[0],FUNCTION);
        Individuo f2 = new Individuo(filhosMat[1],FUNCTION); // TODO Alterado o this para p2

        if (f1.genes.length == 0 || f2.genes.length == 0){
            int i = 0;
        }

//        f1.avaliar(FUNCTION);
//        f2.avaliar(FUNCTION);

        filhos.add(f1);
        filhos.add(f2);

        return  filhos;
    }

    public void mutar(){
        this.genes = mutation.getMutation(this.genes, new double[]{-10, -10}, new double[]{10, 10});
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



