package com.cir3.minesweeper.services;

import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Partie;
import com.cir3.minesweeper.gameobject.Point;
import com.cir3.minesweeper.repository.CasesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Boolean.FALSE;

public class CreationCases {


    public CreationCases(ModeJeu gameMode, CasesRepository cases, Partie newGame){

        //création des cases de la partie
        for (int i = 0; i < gameMode.getLargeur(); i++){
            for(int j = 0; j < gameMode.getLongueur(); j++){

                Cases trans = new Cases();
                trans.setX(i);
                trans.setY(j);
                trans.setPartie(newGame);
                trans.setAffichage("nv");
                trans.setFlag(FALSE);

                cases.save(trans);
            }
        }

    }

    /**
     * génere n bombes aléatoires et uniques
     * @param gameMode
     */
    public void randomMines(ModeJeu gameMode, Partie newGame, CasesRepository cases) {

        List<Cases> casesList = cases.findAllByPartieId(newGame.getId());
        ArrayList<Integer> mines = aleatoire(gameMode.getMines(), casesList.size());

        for (Integer f : mines){
            //System.out.println(f);
            Cases trans = casesList.get(f);
            trans.setEtat(true);
            cases.save(trans);
            calculuslengthMines(trans,cases);
        }

    }

    /**
     * Aléatoires sans remise
     * @param n
     * @param max
     * @return
     */
    private ArrayList<Integer> aleatoire(int n, int max) {
        ArrayList<Integer> r = new ArrayList<Integer>(); // Create an ArrayList object

        for (int i = 0; i < n; i++) {

            int nb = 0;
            do {
                nb = ThreadLocalRandom.current().nextInt(0, max );
            } while (r.contains(nb));

            r.add(nb);

        }
        return r;
    }

    /**
     * calcul des cases adjacentes de la mine
     * @param mine
     * @param cases
     */
    public void calculuslengthMines(Cases mine, CasesRepository cases){
        Point coordMine = new Point(mine.getX(), mine.getY());
        ArrayList<Point> coordAutour = new ArrayList<>();

        //on ne fait que le tour de la bombe c'est à dire les huits cases autour
        //au dessus de la mine
        coordAutour.add(new Point(coordMine.getX()-1, coordMine.getY()-1));
        coordAutour.add(new Point(coordMine.getX()-1, coordMine.getY()));
        coordAutour.add(new Point(coordMine.getX()-1, coordMine.getY()+1));

        //droite gauche mine
        coordAutour.add(new Point(coordMine.getX(), coordMine.getY()-1));
        coordAutour.add(new Point(coordMine.getX(), coordMine.getY()+1));

        //en dessous de la mine
        coordAutour.add(new Point(coordMine.getX()+1, coordMine.getY()-1));
        coordAutour.add(new Point(coordMine.getX()+1, coordMine.getY()));
        coordAutour.add(new Point(coordMine.getX()+1, coordMine.getY()+1));

        for (Point f : coordAutour){
            //System.out.println("coord autour "+f.getX()+";"+f.getY());
            if (f.isInbound(mine.getPartie().getGame().getLargeur(), mine.getPartie().getGame().getLongueur())){
                //on trouve notre case spécifique
                Cases trans = cases.findByPartieIdAndXAndY(mine.getPartie().getId(), f.getX(), f.getY());
                //System.out.println(trans.toString());
                //incrementation
                int length = trans.getDistanceMine()+1;
                //réinsertion save
                trans.setDistanceMine(length);
                cases.save(trans);
            }

        }

    }


}
