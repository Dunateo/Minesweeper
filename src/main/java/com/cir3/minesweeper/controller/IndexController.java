package com.cir3.minesweeper.controller;

import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.comparator.TimeComparator;
import com.cir3.minesweeper.repository.ModeJeuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private ModeJeuRepository gamemode;

    @GetMapping("/")
    public String welcome(Model model){

        List<ModeJeu> gamemodes = gamemode.findAll();
        //sorting all the game
        for (ModeJeu f : gamemodes){
            f.getPartie().sort(new TimeComparator());
        }

        model.addAttribute("h1", "Leaderboard");
        model.addAttribute("gamemodes", gamemodes);

        return "welcome";
    }

}
