package com.cir3.minesweeper.comparator;

import com.cir3.minesweeper.domain.Partie;

import java.util.Comparator;

public class TimeComparator implements Comparator<Partie> {

    @Override
    public int compare(Partie o1, Partie o2) {
        Integer trans1 = o1.getTemps();
        Integer trans2 = o2.getTemps();
        return trans1.compareTo(trans2);
    }
}
