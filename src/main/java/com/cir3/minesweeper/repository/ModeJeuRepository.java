package com.cir3.minesweeper.repository;

import com.cir3.minesweeper.domain.ModeJeu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeJeuRepository extends JpaRepository<ModeJeu,Long> {
}
