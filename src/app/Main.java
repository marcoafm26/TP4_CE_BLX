package app;

import model.Individuo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import model.FNDS;
import model.CrowdingDistance;
import model.GenerateFile;
public class Main {
   final int FUNCTION = 2;
   int QTD_GENES;
   int QTD_AVALIACAO;
   final int MAX_GEN = 1000;
   final int NUM_INDIVIDUOS = 20;
   final double X_BOUND = 10 ;// 20 e -20
   final double F = 0.5;
   final double Cr = 0.8;
   final List<Integer> genToPrint = Arrays.asList(1,20, 40, 60, 80,100, MAX_GEN);

  public static void main(String[] args) {
      Main main = new Main();
      main.inicialization();
  }

  private Individuo inicialization(){

    switch (FUNCTION){
      case 1:
        QTD_GENES = 1;
        QTD_AVALIACAO = 2;
        break;
      case 2:
        QTD_GENES = 2;
        QTD_AVALIACAO = 2;
        break;
      case 3:
        QTD_GENES = 3;
        QTD_AVALIACAO = 3;
        break;
    }

    List<Individuo> individuos = new ArrayList<>(NUM_INDIVIDUOS);
    individuos.addAll(initPop(NUM_INDIVIDUOS,X_BOUND));
    avaliaIndividuos(individuos);

    int INICIAL_GEN = 1;
    while (INICIAL_GEN <= MAX_GEN) {
      List<Individuo> newPop = new ArrayList<>(NUM_INDIVIDUOS);
      List<Individuo> intermedPop = new ArrayList<>(individuos);

      for (int i = 0; i < NUM_INDIVIDUOS; i++) {
        Individuo u = generateUInd(individuos, i);

        Individuo exp = recombinar(individuos.get(i), u);
        exp.avaliar(FUNCTION);

        intermedPop.add(exp);
      }

      List<List<Individuo>> borders = FNDS.execute(intermedPop);

      for (List<Individuo> border : borders) {
        if (newPop.size() >= NUM_INDIVIDUOS) break;

        if (border.size() + newPop.size() > NUM_INDIVIDUOS) {
          List<Individuo> individuoCD = new CrowdingDistance().cdAvaliar(border);
          for (Individuo individual : individuoCD) {
            if (newPop.size() < NUM_INDIVIDUOS) {
              newPop.add(individual);
            } else break;
          }

        } else {
          newPop.addAll(border);
        }
      }

      individuos = newPop;

      if (genToPrint.contains(INICIAL_GEN)) {
        generatePonts(individuos, INICIAL_GEN);
      }

      INICIAL_GEN++;
      print(individuos);
    }

    return individuos.get(0);
  }
  public  Individuo recombinar(Individuo individuo, Individuo u){
    Individuo son = new Individuo(QTD_GENES, QTD_AVALIACAO);
    boolean verifyGenes = false;
    Random random = new Random();

    for (int i = 0; i < individuo.getGenes().length; i++) {
      double r = random.nextDouble();

      if (r <  Cr) {
        son.getGenes()[i] = individuo.getGenes()[i];
      } else {
        verifyGenes = true;
        son.getGenes()[i] = u.getGenes()[i];
      }
    }

    if (!verifyGenes){
      int r = random.nextInt(individuo.getGenes().length);
      son.getGenes()[r] = u.getGenes()[r];
    }
    return son;
  }


//  private Individuo generateUInd(List<Individuo> popInd, int i) {
//    Random random = new Random();
//    int randomIndex1 = random.nextInt(NUM_INDIVIDUOS - 1);
//    while(randomIndex1 == i){
//      randomIndex1 = random.nextInt(NUM_INDIVIDUOS - 1);
//    }
//
//    int randomIndex2 = random.nextInt(NUM_INDIVIDUOS - 1);
//    while(randomIndex2 == i || randomIndex2 == randomIndex1){
//      randomIndex2 = random.nextInt(NUM_INDIVIDUOS - 1);
//    }
//
//    int randomIndex3 = random.nextInt(NUM_INDIVIDUOS - 1);
//    while(randomIndex3 == i || randomIndex3 == randomIndex1 || randomIndex3 == randomIndex2){
//      randomIndex3 = random.nextInt(NUM_INDIVIDUOS - 1);
//    }
//
//    Individuo u = new Individuo(QTD_GENES,QTD_AVALIACAO);
//    Individuo ind1 = popInd.get(randomIndex1);
//    Individuo ind2 = popInd.get(randomIndex2);
//    Individuo ind3 = popInd.get(randomIndex3);
//
//    double[] val = new double[ind1.getGenes().length];
//
//    for (int j = 0; j < val.length; j++) {
//      val[j] = ind3.getGenes()[j] + (F * (ind1.getGenes()[j] - ind2.getGenes()[j]));
//    }
//    u.setGenes(val);
//    u.avaliar(FUNCTION);
//    return u;
//  }

  public void avaliaIndividuos(List<Individuo> individuos){
    for (Individuo individuo :
            individuos) {
      individuo.avaliar(FUNCTION);
    }
  }

  public List<Individuo> initPop(int NUM_INDIVIDUOS, double X_BOUND){
    List<Individuo> popInicial = new ArrayList<>(NUM_INDIVIDUOS);
    for (int i = 0; i < NUM_INDIVIDUOS; i++) {
      Individuo individuo = new Individuo(QTD_GENES,QTD_AVALIACAO);
      individuo.gerarGenes(-X_BOUND,X_BOUND);
      popInicial.add(individuo);
    }
    return popInicial;
  }

  public static void generatePonts(List<Individuo> individuals, int numGen) {
    try {
      createGensFile(individuals, numGen);
      generateFunctionResults(individuals, numGen);
    } catch (IOException erro){
      System.out.printf("Erro: %s", erro.getMessage());
    }
  }

  public static void createGensFile(List<Individuo> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_genes.txt";
    GenerateFile.createFile(path, individuals, false);
  }

  public static void generateFunctionResults(List<Individuo> individuals, int numGen) throws IOException {
    String path = "./src/files/gen_"+numGen+"_values.txt";
    GenerateFile.createFile(path, individuals, true);
  }

  public static void print(List<Individuo> individuos){
    for (int i = 0; i < individuos.size(); i++) {
      String dadosDoIndividuo = "models.Individuo " + (i+1);
      for (int j = 0; j < individuos.get(i).getGenes().length ; j++) {
        dadosDoIndividuo += "\t Genes " + (j+1)+": " +  individuos.get(i).getGenes()[j];
      }
      for (int j = 0; j <individuos.get(i).getAvaliation().length ; j++) {
        dadosDoIndividuo += "\t Avaliacao " + (j+1)+": " +  individuos.get(i).getAvaliation()[j];
      }
      System.out.println(dadosDoIndividuo);
    }
  }

}