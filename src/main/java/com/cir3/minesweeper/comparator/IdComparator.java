package com.cir3.minesweeper.comparator;

import com.cir3.minesweeper.domain.Cases;

import java.util.Comparator;

public class IdComparator implements Comparator<Cases> {

    @Override
    public int compare(Cases o1, Cases o2) {

        return o1.getId().compareTo(o2.getId());

    }
}
