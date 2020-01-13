package com.cir3.minesweeper.controller;

import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Partie;
import com.cir3.minesweeper.form.ModeJeuForm;
import com.cir3.minesweeper.repository.ModeJeuRepository;
import com.cir3.minesweeper.repository.PartieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/gamemode")
public class ModeJeuController {

    @Autowired
    private ModeJeuRepository gamemodes;

    @Autowired
    private PartieRepository parties;

    //mapping pour affichage de la liste des gamemode
    @GetMapping({"","/list"})
    public String list(Model model, @PageableDefault(page=0, size = 10) Pageable pageable){
        model.addAttribute("gamemodes", gamemodes.findAll(pageable));

        return "gamemode-list";
    }

    //mapping pour ajouter un mode de Jeu
    @GetMapping({"/add", "edit/{id}"})
    public String add(@PathVariable(required = false) Long id, Model model){
        ModeJeuForm form = new ModeJeuForm();
        model.addAttribute("gamemode", form);

        if (id != null){
            ModeJeu c = gamemodes.findById(id).get();


            form.setId(c.getId());
            form.setTitre(c.getTitre());
            form.setLargeur(c.getLargeur());
            form.setLongueur(c.getLongueur());
            form.setMines(c.getMines());


        }

        return "gamemode-add";
    }

    @PostMapping("/add")
    public String addForm(@Valid @ModelAttribute("gamemode") ModeJeuForm form, BindingResult result, Model model){

        //Vérification des mines
        if (result.hasErrors() ){
            model.addAttribute("gamemode", form);
            return "gamemode-add";
        }else if (form.getMines() >= form.getLargeur()*form.getLongueur()){
            model.addAttribute("gamemode", form);
            model.addAttribute("mines", "nbmines >= largeurxHauteur");
            return "gamemode-add";
        }

        ModeJeu c =  new ModeJeu();
        if (form.getId() != null){
            c = gamemodes.findById(form.getId()).get();
            c.getPartie().clear();
        }

        c.setTitre(form.getTitre());
        c.setLargeur(form.getLargeur());
        c.setLongueur(form.getLongueur());
        c.setMines(form.getMines());

        gamemodes.save(c);



        return "redirect:/gamemode/list";
    }


    //Mapping pour delete un Mode de Jeu
    @GetMapping("/delete/{id}")
    public String remove(@PathVariable Long id){
        gamemodes.deleteById(id);

        return "redirect:/gamemode/list";
    }



}
