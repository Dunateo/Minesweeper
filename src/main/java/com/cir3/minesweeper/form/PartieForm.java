package com.cir3.minesweeper.form;

import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Utilisateur;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;


public class PartieForm {

    private Long id;

    private int temps;

    private int score;

    private boolean etat;

    @NotNull
    private Long user;

    @NotNull
    private Long game;

    private Set<Long> table = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }


    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getGame() {
        return game;
    }

    public void setGame(Long game) {
        this.game = game;
    }
}
