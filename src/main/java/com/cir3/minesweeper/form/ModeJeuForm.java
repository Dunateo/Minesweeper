package com.cir3.minesweeper.form;


import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class ModeJeuForm {

    private Long id;

    @Size(min=2, max = 30)
    private String titre;

    @NotNull
    @Min(0)
    private int largeur;

    @NotNull
    @Min(0)
    private int longueur;

    @NotNull
    private int mines;

    private Set<Long> partie = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getLongueur() {
        return longueur;
    }

    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    public int getMines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }

    public Set<Long> getPartie() {
        return partie;
    }

    public void setPartie(Set<Long> partie) {
        this.partie = partie;
    }
}
