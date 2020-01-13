package com.cir3.minesweeper.domain;



import javax.persistence.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Entity(name = "partie")
public class Partie {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private int temps;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date fixtemps;

    @Column
    private int score;

    @Column
    private boolean etat;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(  updatable = false)
    private Utilisateur user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn( updatable = false)
    private ModeJeu game;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE,
                    CascadeType.REMOVE
            },
            mappedBy = "partie")
    private List<Cases> table = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Utilisateur getUser() {
        return user;
    }

    public void setUser(Utilisateur user) {
        this.user = user;
    }

    public ModeJeu getGame() {
        return game;
    }

    public void setGame(ModeJeu game) {
        this.game = game;
    }

    public List<Cases> getTable() {
        return table;
    }

    public void setTable(List<Cases> table) {
        this.table = table;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    public Date getFixtemps() {
        return fixtemps;
    }

    public void setFixtemps(Date fixtemps) {
        this.fixtemps = fixtemps;
    }

    public int dateBetween(Date current, Date previous){
        //long diffInMillies = Math.abs(current.getTime() - this.getFixtemps().getTime());
        long diff = (current.getTime() - previous.getTime()) / 1000 ;
        return (int) diff;
    }
}
