package com.cir3.minesweeper.controller;

import com.cir3.minesweeper.domain.Partie;
import com.cir3.minesweeper.domain.Utilisateur;
import com.cir3.minesweeper.repository.PartieRepository;
import com.cir3.minesweeper.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(value = "/update")
public class UpdateController {

    @Autowired
    private PartieRepository parties;

    /**
     * recoit une rêquete avec l'id de la partie update le temps
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/timer/{id}", method = RequestMethod.GET)
    public ResponseEntity<String> updateTimer(@PathVariable(required = true) Long id) {

        Partie encours =  parties.findById(id).get();

        //vérification que la partie soit en cours
        if (!encours.isEtat()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //actualisation du temps
        Date current = new Date();
        int time = encours.dateBetween(current, encours.getFixtemps());
        //System.out.println(time);
        String result = time+ " s";

       // HttpHeaders headers = new HttpHeaders();
       // return new ResponseEntity<>(result,headers ,HttpStatus.OK);
        //return new ResponseEntity<Object>(result, HttpStatus.OK);
        return ResponseEntity.ok(result);
        //return Optional.ofNullable(result).map(item -> new ResponseEntity<>(item, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
