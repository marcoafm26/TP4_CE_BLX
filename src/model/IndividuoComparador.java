package model;

import java.util.Comparator;

public class IndividuoComparador implements Comparator<Individuo> {
    @Override
    public int compare(Individuo i1, Individuo i2) {
        return Double.compare(i2.getCrowdingDistance(), i1.getCrowdingDistance());
    }

}
