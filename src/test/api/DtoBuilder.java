package fr.harvest.vic.espacesuivi.api.espacesuivi.api;

import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.*;
import lombok.Builder;

import java.util.Arrays;
import java.util.List;

@Builder
public class DtoBuilder {

	public final DossierDTO initDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("109887");
		SuiviDTO suivi = new SuiviDTO();
		suivi.setAvisDossier(new DecisionDTO());
		dto.setSuivi(suivi);
		return dto;
	}

	public final DossierDTO initWaitingDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("109887");
		dto.setSuivi(new SuiviDTO());
		dto.setAssurances(initWaitingAssurances());
		return dto;
	}

	public final DossierDTO initAcceptedDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("109887");
		dto.setSuivi(new SuiviDTO());
		dto.setAssurances(initAcceptedAssurances());
		return dto;
	}

	public final DossierDTO initAcceptedRejectedDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("109887");
		dto.setSuivi(new SuiviDTO());
		dto.setAssurances(initAcceptedRejectedAssurances());
		return dto;
	}

	public final DossierDTO initRejectedDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("109887");
		dto.setSuivi(new SuiviDTO());
		dto.setAssurances(initRejectedAssurances());
		return dto;
	}

	private final List<AssuranceDTO> initWaitingAssurances()
	{
		AssuranceDTO assurance = new AssuranceDTO();
		assurance.setCivilite("Mr");
		assurance.setIntervenantId("123456");
		assurance.setNom("Test");
		assurance.setPrenom("Test1");
		assurance.setAssurancesCategorie(initWaitingAssuranceCategorie());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceDTO> initAcceptedAssurances()
	{
		AssuranceDTO assurance = new AssuranceDTO();
		assurance.setCivilite("Mr");
		assurance.setIntervenantId("123456");
		assurance.setNom("Test");
		assurance.setPrenom("Test1");
		assurance.setAssurancesCategorie(initAcceptedAssuranceCategorie());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceDTO> initAcceptedRejectedAssurances()
	{
		AssuranceDTO assurance = new AssuranceDTO();
		assurance.setCivilite("Mr");
		assurance.setIntervenantId("123456");
		assurance.setNom("Test");
		assurance.setPrenom("Test1");
		assurance.setAssurancesCategorie(initAcceptedRejectedAssuranceCategorie());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceCategorieDTO> initWaitingAssuranceCategorie()
	{
		AssuranceCategorieDTO assurance = new AssuranceCategorieDTO();
		assurance.setCategorie(AssuranceCategorieDTO.CategorieEnum.DELEGATION_EN_ATTENTE);
		assurance.setDetails(initDetailWaitingAssurance());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceCategorieDTO> initAcceptedRejectedAssuranceCategorie()
	{
		AssuranceCategorieDTO assurance = new AssuranceCategorieDTO();
		assurance.setCategorie(AssuranceCategorieDTO.CategorieEnum.DELEGATION_EN_ATTENTE);
		assurance.setDetails(initDetailAcceptedRejectedAssurance());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceCategorieDTO> initAcceptedAssuranceCategorie()
	{
		AssuranceCategorieDTO assurance = new AssuranceCategorieDTO();
		assurance.setCategorie(AssuranceCategorieDTO.CategorieEnum.ACCORDE);
		assurance.setDetails(initDetailAcceptedAssurance());
		return Arrays.asList(assurance);
	}

	private final List<DetailAssuranceDTO> initDetailWaitingAssurance()
	{
		DetailAssuranceDTO assurance = new DetailAssuranceDTO();
		assurance.setType(DetailAssuranceDTO.TypeEnum.GROUPE);
		assurance.setStatut(DetailAssuranceDTO.StatutEnum.ACCORDE);
		assurance.setLibelle("assurance1");
		DetailAssuranceDTO assurance2 = new DetailAssuranceDTO();
		assurance2.setType(DetailAssuranceDTO.TypeEnum.DELEGATION);
		assurance2.setStatut(DetailAssuranceDTO.StatutEnum.EN_ATTENTE);
		assurance2.setLibelle("assurance2");
		return Arrays.asList(assurance, assurance2);
	}

	private final List<DetailAssuranceDTO> initDetailAcceptedRejectedAssurance()
	{
		DetailAssuranceDTO assurance = new DetailAssuranceDTO();
		assurance.setType(DetailAssuranceDTO.TypeEnum.GROUPE);
		assurance.setStatut(DetailAssuranceDTO.StatutEnum.ACCORDE);
		assurance.setLibelle("assurance1");
		DetailAssuranceDTO assurance2 = new DetailAssuranceDTO();
		assurance2.setType(DetailAssuranceDTO.TypeEnum.DELEGATION);
		assurance2.setStatut(DetailAssuranceDTO.StatutEnum.REFUSE);
		assurance2.setLibelle("assurance2");
		return Arrays.asList(assurance, assurance2);
	}

	private final List<DetailAssuranceDTO> initDetailAcceptedAssurance()
	{
		DetailAssuranceDTO assurance = new DetailAssuranceDTO();
		assurance.setType(DetailAssuranceDTO.TypeEnum.GROUPE);
		assurance.setStatut(DetailAssuranceDTO.StatutEnum.ACCORDE);
		assurance.setLibelle("assurance1");
		return Arrays.asList(assurance);
	}

	private final List<AssuranceDTO> initRejectedAssurances()
	{
		AssuranceDTO assurance = new AssuranceDTO();
		assurance.setCivilite("Mr");
		assurance.setIntervenantId("123456");
		assurance.setNom("Test");
		assurance.setPrenom("Test1");
		assurance.setAssurancesCategorie(initRejectedAssuranceCategorie());
		return Arrays.asList(assurance);
	}

	private final List<AssuranceCategorieDTO> initRejectedAssuranceCategorie()
	{
		AssuranceCategorieDTO assurance = new AssuranceCategorieDTO();
		assurance.setCategorie(AssuranceCategorieDTO.CategorieEnum.ACCORDE);
		assurance.setDetails(initDetailRejectedAssurance());
		return Arrays.asList(assurance);
	}

	private final List<DetailAssuranceDTO> initDetailRejectedAssurance()
	{
		DetailAssuranceDTO assurance = new DetailAssuranceDTO();
		assurance.setType(DetailAssuranceDTO.TypeEnum.GROUPE);
		assurance.setStatut(DetailAssuranceDTO.StatutEnum.REFUSE);
		assurance.setLibelle("assurance1");
		return Arrays.asList(assurance);
	}

}
