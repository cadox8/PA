package es.projectalpha.pa.rage.utils;

import java.util.Comparator;

public class ScoreComparator implements Comparator {

    public ScoreComparator() {}

    public int compare(Object o, Object o2) {
        Comparable c1 = (int) o;
        Comparable c2 = (int) o2;

        return c1.compareTo(c2);
    }
}
