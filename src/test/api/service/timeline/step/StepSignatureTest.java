package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.exception.HvsDossierException;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.enums.AvancementDossierEnum;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.DossierService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.PieceService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepSignatureTest
{
	@Autowired
	private StepSignature stepSignature;

	@MockBean
	@Qualifier("PieceService")
	private PieceService pieceService;

	@MockBean
	@Qualifier("DossierService")
	private DossierService dossierService;

	@MockBean
	@Autowired
	private StepAssurance stepAssurance;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier()
	{
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(5).getQualifier());
	}

	@Test
	public void step_signature_accepted() throws HvsDossierException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		dossierDTO.setOffres(initOffres());
		when(stepAssurance.computeStateForAssuranceStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);
		when(dossierService.findAvancement(dossierDTO.getNumeroDossier())).thenReturn(AvancementDossierEnum.OFFRE_A_EDITER);
		StepDTO step = this.stepSignature.build(dossierDTO, stepYamlModel.getEtapes().get(5));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.SIGNATURE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.LOCKED);
	}

	@Test
	public void step_signature_unlocked() throws HvsDossierException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		dossierDTO.setOffres(initOffres());
		dossierDTO.getOffres().get(0).setDateAcceptation(null);
		when(stepAssurance.computeStateForAssuranceStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);
		when(dossierService.findAvancement(dossierDTO.getNumeroDossier())).thenReturn(AvancementDossierEnum.OFFRE_A_EDITER);
		StepDTO step = this.stepSignature.build(dossierDTO, stepYamlModel.getEtapes().get(5));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.SIGNATURE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.LOCKED);
	}

	@Test
	public void step_signature_locked() throws HvsDossierException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		dossierDTO.setOffres(Collections.emptyList());
		when(stepAssurance.computeStateForAssuranceStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);
		when(dossierService.findAvancement(dossierDTO.getNumeroDossier())).thenReturn(AvancementDossierEnum.OFFRE_A_EDITER);
		StepDTO step = this.stepSignature.build(dossierDTO, stepYamlModel.getEtapes().get(5));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.SIGNATURE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.LOCKED);
	}

	@Test
	public void step_signature_with_non_valid_assurance() throws HvsDossierException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		dossierDTO.setOffres(Collections.emptyList());
		when(stepAssurance.computeStateForAssuranceStep(dossierDTO)).thenReturn(StepDTO.StateEnum.REJECTED);
		when(dossierService.findAvancement(dossierDTO.getNumeroDossier())).thenReturn(AvancementDossierEnum.OFFRE_A_EDITER);
		StepDTO step = this.stepSignature.build(dossierDTO, stepYamlModel.getEtapes().get(5));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.SIGNATURE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.LOCKED);
	}

	private final List<OffreDTO> initOffres()
	{
		OffreDTO offre = new OffreDTO();
		offre.setSansSuite(false);
		offre.setRefusee(false);
		offre.setRetourner(false);
		offre.setNonConforme(false);
		offre.setDateEdition("date edition");
		offre.setDateAcceptation("une date");
		return Arrays.asList(offre);
	}
}