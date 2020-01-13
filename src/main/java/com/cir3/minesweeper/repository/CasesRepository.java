package com.cir3.minesweeper.repository;

import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.domain.ModeJeu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface CasesRepository extends JpaRepository<Cases,Long> {
    ArrayList<Cases> findAllByPartieId(Long id);
    Cases findByPartieIdAndXAndY(Long id, int x, int y);
    void deleteAllByPartieId(Long id);
    ArrayList<Cases> findAllByPartieIdOrderByYAsc(Long id);
}
