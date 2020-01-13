package com.cir3.minesweeper.repository;

import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Partie;
import com.cir3.minesweeper.domain.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByPseudo(String pseudo);
    boolean existsAllByPseudo(String pseudo);
}
