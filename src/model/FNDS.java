package model;

import java.util.ArrayList;
import java.util.List;

public class FNDS {

    public static List<List<Individuo>> execute(List<Individuo> individuos){
        List<Ponto> pontos = new ArrayList<>(individuos.size());

        for (Individuo individuo : individuos) {
            pontos.add(new Ponto(individuo));
        }

        List<List<Ponto>> fronteiras = new ArrayList<>();
        List<Ponto> fronteira_1 = new ArrayList<>();
        for (int i = 0; i < pontos.size(); i++) {
            Ponto ponto_p = pontos.get(i);
            ponto_p.S = new ArrayList<>();
            ponto_p.n = 0;
            for (int j = 0; j < pontos.size(); j++) {
                if (i != j){
                   Ponto ponto_q = pontos.get(j);
                   if (domina(ponto_p.individuo.getAvaliation(),ponto_q.individuo.getAvaliation())){
                        ponto_p.S.add(ponto_q);
                   }else if (domina(ponto_q.individuo.getAvaliation(),ponto_p.individuo.getAvaliation())){
                       ponto_p.n++;
                   }
                }
            }
            if(ponto_p.n == 0) {
                ponto_p.rank = 1;
                fronteira_1.add(ponto_p);
            }
        }
        fronteiras.add(fronteira_1);

        int i = 0;
        List<Ponto> fronteira_i = fronteiras.get(i);

        while (fronteira_i.size() != 0){

            List<Ponto> Q = new ArrayList<>();

            for (Ponto ponto_i : fronteira_i) {
                List<Ponto> Sp = ponto_i.S;
                for (Ponto ponto_q : Sp) {
                    ponto_q.n--;
                    if (ponto_q.n == 0){
                        ponto_q.rank = i+1;
                        Q.add(ponto_q);
                    }
                }
            }
            i++;
            fronteira_i = Q;
            fronteiras.add(fronteira_i);
        }

        List<List<Individuo>> finaList = new ArrayList<List<Individuo>>();

        for (int j = 0; j < fronteiras.size(); j++) {
            List<Ponto> fronteira_j = fronteiras.get(j);
            if (fronteira_j.size() > 0){
                List<Individuo> Find = new ArrayList<>();

                for (int k = 0; k < fronteira_j.size(); k++) {
                    Ponto p = fronteira_j.get(k);
                    Individuo ind = p.individuo;
                    Find.add(ind);
                }
                finaList.add(Find);
            }

        }
        return finaList;
    }
    public static boolean domina(double[] avalInd,double[] avalInd2){
        boolean domina = false;
        for (int i = 0; i < avalInd.length; i++) {
            if (avalInd[i] > avalInd2[i]) {
                return false;
            }

            if (avalInd[i] < avalInd2[i]) {
                domina = true;
            }
        }
        return domina;
    }
}

