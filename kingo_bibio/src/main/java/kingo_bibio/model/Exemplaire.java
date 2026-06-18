package kingo_bibio.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "exemplaires")
public class Exemplaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Le code-barres doit être unique pour pouvoir identifier un seul exemplaire physique
    @Column(name = "code_barre", nullable = false, unique = true)
    private String codeBarre;

    // @Enumerated(EnumType.STRING) stocke le nom de l'enum (ex: "NEUF") en texte dans MySQL
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatExemplaire etat;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatutExemplaire statut;

    // Plusieurs exemplaires physiques appartiennent à la même fiche descriptive (Livre)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "livre_id", nullable = false)
    private Livre livre;

    // --- CONSTRUCTEURS ---
    public Exemplaire() {}

    public Exemplaire(String codeBarre, EtatExemplaire etat, StatutExemplaire statut, Livre livre) {
        this.codeBarre = codeBarre;
        this.etat = etat;
        this.statut = statut;
        this.livre = livre;
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCodeBarre() { return codeBarre; }
    public void setCodeBarre(String codeBarre) { this.codeBarre = codeBarre; }

    public EtatExemplaire getEtat() { return etat; }
    public void setEtat(EtatExemplaire etat) { this.etat = etat; }

    public StatutExemplaire getStatut() { return statut; }
    public void setStatut(StatutExemplaire statut) { this.statut = statut; }

    public Livre getLivre() { return livre; }
    public void setLivre(Livre livre) { this.livre = livre; }
}