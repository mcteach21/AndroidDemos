package mc.apps.demos.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;

public class Intervention implements Serializable {
	private int id;
	private String commentaire;

	@SerializedName("date_debut_prevue")
	private Date dateDebutPrevue;
	@SerializedName("date_debut_reelle")
	private Date dateDebutReelle;
	@SerializedName("date_fin_reelle")
	private Date dateFinReelle;
	private String description;

	@SerializedName("heure_debut_prevue")
	private Time heureDebutPrevue;

	@SerializedName("heure_debut_reelle")
	private Time heureDebutReelle;

	@SerializedName("heure_fin_reelle")
	private Time heureFinReelle;

	@SerializedName("materiel_necessaire")
	private String materielNecessaire;

	@SerializedName("service_equip_cible")
	private String serviceEquipCible;


	private List<Affectation> affectations;
	private Client client;
	private Statut statut;
	private User user;

	public Intervention() {
	}

	public Intervention(int id, String commentaire, Date dateDebutPrevue, Date dateDebutReelle, Date dateFinReelle, String description, Time heureDebutPrevue, Time heureDebutReelle, Time heureFinReelle, String materielNecessaire, String serviceEquipCible) {
		this.id = id;
		this.commentaire = commentaire;
		this.dateDebutPrevue = dateDebutPrevue;
		this.dateDebutReelle = dateDebutReelle;
		this.dateFinReelle = dateFinReelle;
		this.description = description;
		this.heureDebutPrevue = heureDebutPrevue;
		this.heureDebutReelle = heureDebutReelle;
		this.heureFinReelle = heureFinReelle;
		this.materielNecessaire = materielNecessaire;
		this.serviceEquipCible = serviceEquipCible;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCommentaire() {
		return this.commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public Date getDateDebutPrevue() {
		return this.dateDebutPrevue;
	}

	public void setDateDebutPrevue(Date dateDebutPrevue) {
		this.dateDebutPrevue = dateDebutPrevue;
	}

	public Date getDateDebutReelle() {
		return this.dateDebutReelle;
	}

	public void setDateDebutReelle(Date dateDebutReelle) {
		this.dateDebutReelle = dateDebutReelle;
	}

	public Date getDateFinReelle() {
		return this.dateFinReelle;
	}

	public void setDateFinReelle(Date dateFinReelle) {
		this.dateFinReelle = dateFinReelle;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Time getHeureDebutPrevue() {
		return this.heureDebutPrevue;
	}

	public void setHeureDebutPrevue(Time heureDebutPrevue) {
		this.heureDebutPrevue = heureDebutPrevue;
	}

	public Time getHeureDebutReelle() {
		return this.heureDebutReelle;
	}

	public void setHeureDebutReelle(Time heureDebutReelle) {
		this.heureDebutReelle = heureDebutReelle;
	}

	public Time getHeureFinReelle() {
		return this.heureFinReelle;
	}

	public void setHeureFinReelle(Time heureFinReelle) {
		this.heureFinReelle = heureFinReelle;
	}

	public String getMaterielNecessaire() {
		return this.materielNecessaire;
	}

	public void setMaterielNecessaire(String materielNecessaire) {
		this.materielNecessaire = materielNecessaire;
	}

	public String getServiceEquipCible() {
		return this.serviceEquipCible;
	}

	public void setServiceEquipCible(String serviceEquipCible) {
		this.serviceEquipCible = serviceEquipCible;
	}

	public List<Affectation> getAffectations() {
		return this.affectations;
	}

	public void setAffectations(List<Affectation> affectations) {
		this.affectations = affectations;
	}

	public Affectation addAffectation(Affectation affectation) {
		getAffectations().add(affectation);
		affectation.setIntervention(this);

		return affectation;
	}

	public Affectation removeAffectation(Affectation affectation) {
		getAffectations().remove(affectation);
		affectation.setIntervention(null);

		return affectation;
	}

	public Client getClient() {
		return this.client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Statut getStatut() {
		return this.statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}