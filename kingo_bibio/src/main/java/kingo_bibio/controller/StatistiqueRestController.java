package kingo_bibio.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kingo_bibio.service.StatistiqueService;

@RestController
@RequestMapping("/api/statistiques")
public class StatistiqueRestController {

    private final StatistiqueService statistiqueService;

    public StatistiqueRestController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    @GetMapping("/dashboard")
    public Map<String, Object> obtenirDonneesTableauDeBord() {
        // On regroupe tous les KPIs dans une Map qui sera automatiquement convertie en un bel objet JSON
        Map<String, Object> dashboard = new HashMap<>();
        
        dashboard.put("empruntsDuMoisCourant", statistiqueService.obtenirNombreEmpruntsCeMois());
        dashboard.put("top5LivresLesPlusPopulaires", statistiqueService.obtenirTop5LivresPopulaires());
        
        return dashboard;
    }
}