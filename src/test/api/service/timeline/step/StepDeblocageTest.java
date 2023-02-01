package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.exception.HvsConfigurationException;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.exception.HvsDossierException;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.Pair;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.enums.AvancementDossierEnum;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.DossierService;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepDeblocageTest {
	@Autowired
	private StepDeblocage stepDeblocage;

	@Autowired
	@MockBean
	private StepSignature stepSignature;

	@MockBean
	@Qualifier("DossierService")
	private PieceService pieceService;

	@MockBean
	@Qualifier("DossierService")
	private DossierService dossierService;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier() {
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(6).getQualifier());
	}

	@Test
	public void step_acces_test() throws HvsDossierException, HvsConfigurationException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(stepSignature.computeStateForSignatureStep(dossierDTO)).thenReturn(new Pair<>(StepDTO.StateEnum.ACCEPTED, ""));
		when(dossierService.findAvancement(dossierDTO.getNumeroDossier())).thenReturn(AvancementDossierEnum.EN_GESTION);
		StepDTO step = this.stepDeblocage.build(dossierDTO, stepYamlModel.getEtapes().get(6));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.DEBLOCAGE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
	}
}