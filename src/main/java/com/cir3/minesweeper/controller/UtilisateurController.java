package com.cir3.minesweeper.controller;

import com.cir3.minesweeper.domain.ModeJeu;
import com.cir3.minesweeper.domain.Utilisateur;
import com.cir3.minesweeper.form.ModeJeuForm;
import com.cir3.minesweeper.form.UtilisateurForm;
import com.cir3.minesweeper.repository.ModeJeuRepository;
import com.cir3.minesweeper.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/utilisateur")
public class UtilisateurController {

    @Autowired
    private UtilisateurRepository utilis;

    //affichage du profil
    @GetMapping({"/profil"})
    public String profil(Model model, HttpSession session ){
        //redirection si il n'y a pas de connexion
        if (session.getAttribute("user-id") == null ){
            return "redirect:/utilisateur/login";
        }

        //récupération de la session
        Utilisateur user =  utilis.findById( (Long)session.getAttribute("user-id")).get();

        //System.out.println("Recup "+user.getPseudo() + user.getId());
        model.addAttribute("utilis", user);

        return "utilisateur-profil";
    }

    @GetMapping({"/login", ""})
    public String login( Model model, HttpSession session){
        UtilisateurForm form = new UtilisateurForm();

        if (session.getAttribute("user-id") != null ){
            return "redirect:/utilisateur/profil";
        }
        model.addAttribute("utilis", form);

        return "utilisateur-login";
    }

    @PostMapping("/login")
    public String loginForm(@Valid @ModelAttribute("utilis") UtilisateurForm form, BindingResult result, HttpSession request, Model model){

        if (result.hasErrors()){
            model.addAttribute("utilis", form);
            return "utilisateur-login";
        }

        Utilisateur c =  new Utilisateur();
        //login affectation de la variable de session
        try {
            c = utilis.findByPseudo(form.getPseudo());

            //System.out.println("ps:   "+c.getPseudo()+ c.getId());
            //request de session
            request.setAttribute("user-id",c.getId());

        }catch (Exception e){
            System.out.println(e);
        }


        return "redirect:/utilisateur/profil";
    }

    //mapping pour ajouter un mode de Jeu
    @GetMapping({"/add", "edit/{id}"})
    public String add(@PathVariable(required = false) Long id, Model model){
        UtilisateurForm form = new UtilisateurForm();
        model.addAttribute("utilis", form);

        if (id != null){
            Utilisateur c = utilis.findById(id).get();
            form.setId(c.getId());
            form.setPseudo(c.getPseudo());


        }


        return "utilisateur-register";
    }

    @PostMapping("/add")
    public String addForm(@Valid @ModelAttribute("utilis") UtilisateurForm form, BindingResult result, Model model){

        if (result.hasErrors()){
            model.addAttribute("utilis", form);
            return "utilisateur-register";
        }else if (utilis.existsAllByPseudo(form.getPseudo())){
            model.addAttribute("utilis", form);
            model.addAttribute("exist", "Le pseudo existe déja !");
            return "utilisateur-register";
        }

        Utilisateur c =  new Utilisateur();
        if (form.getId() != null){
            c = utilis.findById(form.getId()).get();
            c.setPartie(c.getPartie());
        }

        c.setPseudo(form.getPseudo());

        utilis.save(c);

        return "redirect:/utilisateur/login";
    }

    @GetMapping("/logout")
    public String logout( HttpServletRequest request){
        HttpSession session = request.getSession();

        //si on pas connecté redirection
        if (session.getAttribute("user-id") == null ){
            return "redirect:/utilisateur/login";
        }
        //logout
        session.removeAttribute("user-id");
        return "redirect:/utilisateur/login";
    }
}
