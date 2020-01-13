package com.cir3.minesweeper.services;

import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.repository.CasesRepository;

public class TraitementFlags {

    public TraitementFlags(Cases select, CasesRepository bddCases){

        if (select.isFlag()){
                select.setFlag(false);
                bddCases.save(select);
        }else if (!select.isFlag()){
                select.setFlag(true);
                bddCases.save(select);
        }

    }
}
