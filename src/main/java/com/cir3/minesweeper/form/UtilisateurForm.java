package com.cir3.minesweeper.form;

import com.cir3.minesweeper.domain.Partie;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class UtilisateurForm {

    private Long id;

    @NotNull
    @Size(min=2, max = 30)
    private String pseudo;

    private Set<Long> partie = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public Set<Long> getPartie() {
        return partie;
    }

    public void setPartie(Set<Long> partie) {
        this.partie = partie;
    }
}
