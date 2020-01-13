package com.cir3.minesweeper.services;

import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.gameobject.Point;
import com.cir3.minesweeper.repository.CasesRepository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TraitementCases {

    private boolean bombClick = false;
    private boolean didHeWin = false;

    public TraitementCases(Cases select, CasesRepository bddCases){

        if (select.isEtat()){
            //bddCases.deleteAllByPartieId(select.getPartie().getId());
            List<Cases> loose = select.getPartie().getTable();

            //passage des cases bombes en visible
            for (int i = 0; i < loose.size(); i++){
                Cases trans = loose.get(i);
                if (trans.isEtat()){
                    trans.setAffichage("v");
                    bddCases.save(trans);
                }

            }
            bombClick = true;

        }else if (!select.isEtat()){
            this.floodFill(select, bddCases);
            this.didHeWin = checkWin(bddCases, select.getPartie().getId());

        }

    }

    public boolean isBombClick() {
        return bombClick;
    }

    public void setBombClick(boolean bombClick) {
        this.bombClick = bombClick;
    }

    public boolean isDidHeWin() {
        return didHeWin;
    }

    public void setDidHeWin(boolean didHeWin) {
        this.didHeWin = didHeWin;
    }

    /**
     * he win ?
     * @param bddCases
     * @param id
     * @return
     */
    private boolean checkWin(CasesRepository bddCases, Long id){

        List<Cases> all = bddCases.findAllByPartieId(id);

        for (Cases f : all){
            if (f.isEtat() && f.getAffichage().equals("v")){
                return false;
            }else if (!f.isEtat() && f.getAffichage().equals("nv")){
                return false;
            }
        }

        return true;

    }
    /**
     * Algorithme floodfill itératif
     * @param click
     * @param bddCases
     */
    public void floodFill(Cases click, CasesRepository bddCases){

        int maxX = click.getPartie().getGame().getLargeur();
        int maxY = click.getPartie().getGame().getLongueur();
        Long id = click.getPartie().getId();

        //ma pile
        Queue<Point> queue = new LinkedList<Point>();
        queue.add(new Point(click.getX(), click.getY()));

        while (!queue.isEmpty()){

            Point last = queue.remove();
            Cases trans = bddCases.findByPartieIdAndXAndY(id, last.getX(), last.getY());

            //dès qu'on tombe sur un nombre on s'arrète ou bombe
            if (floodFillVerif(last,trans,bddCases,maxX,maxY)){

                //System.out.println(trans.getX()+";"+trans.getY());

                //FLOODFILL BY 4
                //EST
                queue.add(new Point(last.getX()-1,last.getY()));
                //OUEST
                queue.add(new Point(last.getX()+1,last.getY()));
                //SUD
                queue.add(new Point(last.getX(),last.getY()+1));
                //NORD
                queue.add(new Point(last.getX(),last.getY()-1));

                //FLOODFILL BY 8
                //NORD-EST
                queue.add(new Point(last.getX()-1,last.getY()-1));
                //NORD-OUEST
                queue.add(new Point(last.getX()+1,last.getY()-1));
                //SUD-EST
                queue.add(new Point(last.getX()-1,last.getY()+1));
                //SUD-OUEST
                queue.add(new Point(last.getX()+1,last.getY()+1));



            }

        }


    }

    private static boolean floodFillVerif(Point last, Cases trans, CasesRepository bddCases, int maxX, int maxY){

                if (!last.isInbound(maxX, maxY)){ return false;}
               // System.out.println("1");
                //System.out.println("test"+trans.getX()+";"+trans.getY());
                if (trans.isEtat()){ return false;};
                //System.out.println("2");
                //System.out.println(trans.getAffichage());
                if (trans.getAffichage().equals("v")){return false;};
                //System.out.println("3");

                //Si on a passé toutes ces étapes c'est qu'on peut afficher la case
                 trans.setAffichage("v");
                 bddCases.save(trans);

                 //si cette case est un nombre on stop
                 if (trans.getDistanceMine() != 0){return false;};
                   // System.out.println("4");

                 return true;
    }
}
