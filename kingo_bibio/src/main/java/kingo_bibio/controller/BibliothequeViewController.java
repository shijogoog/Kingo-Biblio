package kingo_bibio.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import kingo_bibio.service.BibliothequeService;

@Controller
@RequestMapping("/management")
public class BibliothequeViewController {

    private final BibliothequeService bibliothequeService;

    public BibliothequeViewController(BibliothequeService bibliothequeService) {
        this.bibliothequeService = bibliothequeService;
    }

    @GetMapping("/panel")
    public String afficherLePanneauDAdministration(Model model, @RequestParam(value = "search", required = false) String search) {
        model.addAttribute("listeDesEmpruntsActifs", bibliothequeService.recupererTousLesEmpruntsActifs());
        model.addAttribute("listeDesAlertesRetard", bibliothequeService.calculerLesAlertesDeRetardActuelles());
        model.addAttribute("listeDesLivresDuCatalogue", bibliothequeService.filtrerEtChercherLivres(search));
        model.addAttribute("motCleRecherche", search);
        
        return "tableau_bord_universitaire";
    }

    @PostMapping("/action/emprunter")
    public String executerFormulaireDEmprunt(@RequestParam("idDuLivre") Long livreId, 
                                             @RequestParam("idDuMembre") Long membreId) {
        try {
            bibliothequeService.enregistrerEmprunt(livreId, membreId, 14);
            return "redirect:/management/panel?success_pret";
        } catch (Exception exception) {
            return "redirect:/management/panel?error_pret=" + exception.getMessage();
        }
    }

    @PostMapping("/action/restituer/{empruntId}")
    public String executerFormulaireDeRetour(@PathVariable("empruntId") Long id) {
        try {
            bibliothequeService.restituerLivre(id);
            return "redirect:/management/panel?success_retour";
        } catch (Exception e) {
            return "redirect:/management/panel?error_retour=" + e.getMessage();
        }
    }
}