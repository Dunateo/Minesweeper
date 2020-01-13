package com.cir3.minesweeper.repository;

import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartieRepository extends JpaRepository<Partie,Long> {
    //void deleteAllByGameId(Long id);
}
