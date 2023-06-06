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

      makeOffspring(intermedPop, individuos,FUNCTION);
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
//      print(individuos);
    }

    return individuos.get(0);
  }

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

  public static void makeOffspring(List<Individuo> intermedPop, List<Individuo> individuos,int FUNCTION) {
    Random random = new Random();

    List<Individuo> popAux = new ArrayList<>(individuos.size());
    popAux.addAll(individuos);

      while (popAux.size() > 1) {
        int idxR1 = random.nextInt(popAux.size());
        Individuo p1 = popAux.remove(idxR1);
        int idxR2 = random.nextInt(popAux.size());
        Individuo p2 = popAux.remove(idxR2);


          List<Individuo> filhos = p1.recombinar(p2,FUNCTION);
        Individuo f1 = filhos.get(0);
          if (random.nextDouble() > 0.9) {
            f1.mutar();
          }
        Individuo f2 = filhos.get(1);
          if (random.nextDouble() > 0.9) {
            f2.mutar();
          }


          f1.avaliar(FUNCTION);
          f2.avaliar(FUNCTION);
          intermedPop.add(f1);
          intermedPop.add(f2);
        }
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