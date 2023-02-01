package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.PieceService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DossierDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.StepDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepAssuranceTest
{

	@Autowired
	private StepAssurance stepAssurance;

	@MockBean
	private StepEtude stepEtude;

	@MockBean
	@Qualifier("DossierService")
	private PieceService pieceService;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier()
	{
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(4).getQualifier());
	}

	@Test
	public void step_assurance_all_waiting()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initWaitingDossier();
		when(stepEtude.computeStateForEtudeStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);

		StepDTO step = this.stepAssurance.build(dossierDTO, stepYamlModel.getEtapes().get(4));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ASSURANCE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.UNLOCKED);
	}

	@Test
	public void step_assurance_all_accepted()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initAcceptedDossier();
		when(stepEtude.computeStateForEtudeStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);

		StepDTO step = this.stepAssurance.build(dossierDTO, stepYamlModel.getEtapes().get(4));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ASSURANCE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
	}

	@Test
	public void step_assurance_all_rejected()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initRejectedDossier();
		when(stepEtude.computeStateForEtudeStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);

		StepDTO step = this.stepAssurance.build(dossierDTO, stepYamlModel.getEtapes().get(4));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ASSURANCE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.REJECTED);
	}

	@Test
	public void step_assurance_with_accepted_and_rejected()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initAcceptedRejectedDossier();
		when(stepEtude.computeStateForEtudeStep(dossierDTO)).thenReturn(StepDTO.StateEnum.ACCEPTED);

		StepDTO step = this.stepAssurance.build(dossierDTO, stepYamlModel.getEtapes().get(4));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ASSURANCE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.UNLOCKED);
	}

	@Test
	public void step_assurance_with_non_accepted_etude()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initAcceptedDossier();
		dossierDTO.setAssurances(Collections.emptyList());
		when(stepEtude.computeStateForEtudeStep(dossierDTO)).thenReturn(StepDTO.StateEnum.UNLOCKED);

		StepDTO step = this.stepAssurance.build(dossierDTO, stepYamlModel.getEtapes().get(4));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ASSURANCE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.LOCKED);
	}


}