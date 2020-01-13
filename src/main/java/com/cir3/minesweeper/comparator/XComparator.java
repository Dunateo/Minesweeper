package com.cir3.minesweeper.comparator;

import com.cir3.minesweeper.domain.Cases;

import java.util.Comparator;

/**
 * Sorting the List in ASC by Y and X
 */
public class XComparator implements Comparator<Cases> {

    @Override
    public int compare(Cases o1, Cases o2) {

        Integer transY1 = o1.getY();
        Integer transY2 = o2.getY();

        Integer transX1 = o1.getX();
        Integer transX2 = o2.getX();

        //comparaison
        int Xcompare = transX1.compareTo(transX2);
        int Ycompare = transY1.compareTo(transY2);


        if (Ycompare == 0) {
            return ((Xcompare == 0) ? Ycompare : Xcompare);
        } else {
            return Ycompare;
        }

    }
}
