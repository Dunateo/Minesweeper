package com.cir3.minesweeper.controller;

import com.cir3.minesweeper.comparator.TimeComparator;
import com.cir3.minesweeper.comparator.XComparator;
import com.cir3.minesweeper.domain.Cases;
import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Partie;
import com.cir3.minesweeper.domain.Utilisateur;
import com.cir3.minesweeper.form.PartieForm;
import com.cir3.minesweeper.repository.CasesRepository;
import com.cir3.minesweeper.repository.ModeJeuRepository;
import com.cir3.minesweeper.repository.PartieRepository;
import com.cir3.minesweeper.repository.UtilisateurRepository;
import com.cir3.minesweeper.services.CreationCases;
import com.cir3.minesweeper.services.TraitementCases;
import com.cir3.minesweeper.services.TraitementFlags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

import java.util.Date;

import static java.lang.Boolean.TRUE;


@Controller
@RequestMapping("/play")
public class PartieController {

    static final int Perdu = 10000000;

    @Autowired
    private UtilisateurRepository utilis;

    @Autowired
    private PartieRepository partie;

    @Autowired
    private ModeJeuRepository gamemodes;

    @Autowired
    private CasesRepository cases;

    @GetMapping({"","/new"})
    public String partnew(Model model, HttpSession session ){
        //redirection si il n'y a pas de connexion
        if (session.getAttribute("user-id") == null ){
            return "redirect:/utilisateur/login";
        }

        //récupération de la session
        Utilisateur user =  utilis.findById( (Long)session.getAttribute("user-id")).get();
        PartieForm partienew = new PartieForm();
        partienew.setUser(user.getId());

        //reprise de partie
        for ( Partie f : user.getPartie()){
            if (f.isEtat()){
                return "redirect:/play/"+f.getId();
            }
        }

        model.addAttribute("utili", user);
        model.addAttribute("gamemodes", gamemodes.findAll() );
        model.addAttribute("parties", partienew );


        return "partie-new";
    }
    @PostMapping({"/new"})
    public String partnewForm(@ModelAttribute("parties") PartieForm form, HttpSession request, Model model){

        Partie c = new Partie();
        Utilisateur user = utilis.findById((Long)request.getAttribute("user-id")).get();
        ModeJeu gameMode = gamemodes.findById(form.getGame()).get();
        Date previous = new Date();

        c.setGame(gameMode);
        c.setScore(0);
        c.setEtat(TRUE);
        c.setUser(user);
        c.setTemps(0);
        c.setFixtemps(previous);
        //affectation aux Set des utilisateurs
        Partie newGame = partie.save(c);
        //user.setPartieUnit(newGame);

        //Création des cases
        CreationCases tableCase = new CreationCases(gameMode, cases, newGame);
        tableCase.randomMines(gameMode,newGame, cases);


        return "redirect:/play/"+newGame.getId();
    }

    @GetMapping({"/{id}/{idcase}","/{id}"})
    public String affichageJeu(@PathVariable(required = true) Long id, @PathVariable(required = false) Long idcase, Model model, HttpSession session ){
        //redirection si il n'y a pas de connexion
        if (session.getAttribute("user-id") == null){
            return "redirect:/utilisateur/login";
        }

        Partie encours = partie.findById(id).get();

        if (idcase != null){
            Cases select = cases.findById(idcase).get();
            TraitementCases click = new TraitementCases(select, cases);

            //click sur la bomb enlève la partie
            if (click.isBombClick()){
                //partie.deleteById(id);

                //changement du temps
                encours.setTemps(Perdu);
                encours.setEtat(false);
                partie.save(encours);

                return "redirect:/play/loose/"+id;
            }else if (click.isDidHeWin()){

                int time = encours.dateBetween(new Date(), encours.getFixtemps());
                System.out.println("recup"+time);
                encours.setTemps(time);
                encours.setEtat(false);
                partie.save(encours);
                return "redirect:/play/win/"+id;
            }
        }

        //Pour actualiser le temps
        Date current = new Date();
        int time = encours.dateBetween(current, encours.getFixtemps());
        //System.out.println("date current"+current);
        //System.out.println("date previous"+ encours.getFixtemps());
        //System.out.println(encours.getTemps() + "le temps");
        //System.out.println("recup"+time);
        encours.setTemps(time);
        partie.save(encours);

        encours = partie.findById(id).get();
        encours.getTable().sort(new XComparator());
        //encours.setTable( cases.findAllByPartieIdOrderByYAsc(encours.getId()));

        model.addAttribute("Partie",encours );


        return "partie";
    }

    @GetMapping({"/flags/{id}/{idcase}","/flags/{id}"})
    public String affichageDecouverte(@PathVariable(required = true) Long id, @PathVariable(required = false) Long idcase, Model model, HttpSession session ){
        //redirection si il n'y a pas de connexion
        if (session.getAttribute("user-id") == null ){
            return "redirect:/utilisateur/login";
        }

        Partie encours = partie.findById(id).get();

        //selection d'une case
        if (idcase != null){
            Cases select = cases.findById(idcase).get();
            TraitementFlags click = new TraitementFlags(select, cases);
            return "redirect:/play/flags/"+id;

        }

        encours.getTable().sort(new XComparator());

        model.addAttribute("Partie",encours );


        return "partie-flags";
    }


    @GetMapping({"/abandon/{id}"})
    public String abandon(@PathVariable(required = true) Long id){
        Partie abandon = partie.findById(id).get();
        //renvoie sur la page play si la partie n'est pas en cours
        if (!abandon.isEtat()){
            return "redirect:/play";
        }

        abandon.setEtat(false);
        abandon.setTemps(Perdu);
        partie.save(abandon);
        return "redirect:/play";
    }

    @GetMapping({"/win/{id}"})
    public String win(@PathVariable(required = true) Long id, Model model){
        Partie win = partie.findById(id).get();
        if (!win.isEtat()){
            model.addAttribute("partie", win);
            return "win";
        }
        return "redirect:/play";
    }

    @GetMapping({"/loose/{id}"})
    public String loose(@PathVariable(required = true) Long id, Model model){
        Partie loose = partie.findById(id).get();

        if (!loose.isEtat() && loose.getTemps() == Perdu){
            loose.getTable().sort(new XComparator());
            model.addAttribute("Partie", loose);
            model.addAttribute("Display", true);
            return "loose";
        }else if (!loose.isEtat() && loose.getTemps() != Perdu){
            loose.getTable().sort(new XComparator());
            loose.getGame().getPartie().sort(new TimeComparator());
            model.addAttribute("Partie", loose);
            model.addAttribute("Display", false);
            return "loose";
        }

        return "redirect:/play";
    }

}
