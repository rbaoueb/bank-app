package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.exception.HvsConfigurationException;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.enums.PieceTypeEnum;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.PieceService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DossierDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.PieceDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.PieceJeuDTO;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepReserveTest {

	@Autowired
	private StepReserve stepReserve;

	@MockBean
	@Qualifier("PieceService")
	private PieceService pieceService;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier() {
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(3).getQualifier());
	}

	@Test
	public void step_reserve_with_empty_pieces_test() throws HvsConfigurationException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.RESERVE)).thenReturn(Collections.emptyList());
		StepDTO step = this.stepReserve.build(dossierDTO, stepYamlModel.getEtapes().get(3));
		assertThat(step.getActive()).isFalse();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.LEVEE_RESERVES);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
	}

	@Test
	public void step_reserve_with_valid_pieces_test() throws HvsConfigurationException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.RESERVE)).thenReturn(initValidPieces());
		StepDTO step = this.stepReserve.build(dossierDTO, stepYamlModel.getEtapes().get(3));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.LEVEE_RESERVES);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
	}

	@Test
	public void step_reserve_with_invalid_pieces_test() throws HvsConfigurationException
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.RESERVE)).thenReturn(initInValidPieces());
		StepDTO step = this.stepReserve.build(dossierDTO, stepYamlModel.getEtapes().get(3));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.LEVEE_RESERVES);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.UNLOCKED);
	}

	private final List<PieceJeuDTO> initValidPieces() {
		PieceJeuDTO piece = new PieceJeuDTO();
		PieceDTO option1 = new PieceDTO();
		option1.setConforme(true);
		option1.setDateReception("not null value1");
		PieceDTO option2 = new PieceDTO();
		option2.setConforme(true);
		option2.setDateReception("not null value2");
		piece.setOptions(Arrays.asList(option1,option2));
		return Arrays.asList(piece);
	}

	private final List<PieceJeuDTO> initInValidPieces() {
		PieceJeuDTO piece = new PieceJeuDTO();
		PieceDTO option1 = new PieceDTO();
		option1.setConforme(false);
		option1.setDateReception("not null value1");
		PieceDTO option2 = new PieceDTO();
		option2.setConforme(true);
		option2.setDateReception("not null value2");
		piece.setOptions(Arrays.asList(option1,option2));
		return Arrays.asList(piece);
	}
}