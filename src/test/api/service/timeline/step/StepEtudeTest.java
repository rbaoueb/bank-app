package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.PieceService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DecisionDTO;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepEtudeTest {

	@Autowired
	private StepEtude stepEtude;

	@MockBean
	@Qualifier("DossierService")
	private PieceService pieceService;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier() {
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(2).getQualifier());
	}

	@Test
	public void step_etude_test()
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		dossierDTO.getSuivi().getAvisDossier().setAvis(DecisionDTO.AvisEnum.AJOURNE);

		StepDTO step = this.stepEtude.build(dossierDTO, stepYamlModel.getEtapes().get(2));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ETUDE);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.UNLOCKED);
	}
}