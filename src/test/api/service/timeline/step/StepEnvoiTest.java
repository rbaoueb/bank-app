package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.DtoBuilder;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.StepServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.enums.PieceTypeEnum;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.PieceService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.*;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
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
public class StepEnvoiTest
{

	@Autowired
	private StepEnvoi stepEnvoi;

	@MockBean
	@Qualifier("PieceService")
	private PieceService pieceService;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Before
	public void check_wire_step_by_qualifier()
	{
		stepServiceFactory.wireService(stepYamlModel.getEtapes().get(1).getQualifier());
	}

	@Test
	public void step_envoi_with_valid_pieces() throws Exception
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.JUSTIFICATIVE)).thenReturn(initValidPieces());

		StepDTO step = this.stepEnvoi.build(dossierDTO, stepYamlModel.getEtapes().get(1));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ENVOI);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
		assertThat(dossierDTO.getSuivi().getNombrePieces()).isEqualTo(0);
		assertThat(dossierDTO.getSuivi().getNbValides()).isEqualTo(2);
	}

	@Test
	public void step_envoi_with_invalid_pieces() throws Exception
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.JUSTIFICATIVE)).thenReturn(initInValidPieces());

		StepDTO step = this.stepEnvoi.build(dossierDTO, stepYamlModel.getEtapes().get(1));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ENVOI);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.UNLOCKED);
		assertThat(dossierDTO.getSuivi().getNombrePieces()).isEqualTo(1);
	}

	@Test
	public void step_envoi_with_empty_pieces() throws Exception
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.JUSTIFICATIVE)).thenReturn(Collections.emptyList());

		StepDTO step = this.stepEnvoi.build(dossierDTO, stepYamlModel.getEtapes().get(1));
		assertThat(step.getActive()).isTrue();
		assertThat(step.getOverride()).isFalse();
		assertThat(step.getRef()).isEqualTo(StepDTO.RefEnum.ENVOI);
		assertThat(step.getState()).isEqualTo(StepDTO.StateEnum.ACCEPTED);
		assertThat(dossierDTO.getSuivi().getNombrePieces()).isNull();
	}

	@Test
	public void etat_des_pieces() throws Exception
	{
		DossierDTO dossierDTO = DtoBuilder.builder().build().initDossier();
		when(pieceService.findPieces(dossierDTO.getNumeroDossier(), PieceTypeEnum.JUSTIFICATIVE)).thenReturn(initPieces());

		StepDTO step = this.stepEnvoi.build(dossierDTO, stepYamlModel.getEtapes().get(1));
		assertThat(dossierDTO.getSuivi().getNombrePieces()).isEqualTo(4);
		assertThat(dossierDTO.getSuivi().getNbValides()).isEqualTo(3);
		assertThat(dossierDTO.getSuivi().getNbTransmises()).isEqualTo(1);
		assertThat(dossierDTO.getSuivi().getNbAtransmettre()).isEqualTo(0);
		assertThat(dossierDTO.getSuivi().getNbCollectes()).isEqualTo(0);
		assertThat(dossierDTO.getSuivi().getNbArenvoyer()).isEqualTo(2);

	}

	private final List<PieceJeuDTO> initValidPieces()
	{
		PieceJeuDTO piece = new PieceJeuDTO();
		PieceDTO option1 = new PieceDTO();
		option1.setConforme(true);
		option1.setDateReception("not null value1");
		PieceDTO option2 = new PieceDTO();
		option2.setConforme(true);
		option2.setDateReception("not null value2");
		piece.setOptions(Arrays.asList(option1, option2));
		return Arrays.asList(piece);
	}

	private final List<PieceJeuDTO> initInValidPieces()
	{

		PieceJeuDTO piece = new PieceJeuDTO();
		PieceDTO option1 = new PieceDTO();
		option1.setConforme(false);
		option1.setDateReception("not null value1");
		PieceDTO option2 = new PieceDTO();
		option2.setConforme(true);
		option2.setDateReception("not null value2");
		piece.setOptions(Arrays.asList(option1, option2));
		return Arrays.asList(piece);
	}

	private final List<PieceJeuDTO> initPieces()
	{

		PieceJeuDTO piece = new PieceJeuDTO();
		PieceDTO option1 = new PieceDTO();
		option1.setDateReception("");
		option1.setConforme(true);

		PieceDTO option2 = new PieceDTO();
		option2.setDateReception("date not null");
		option2.setConforme(false);
		PieceDTO option3 = new PieceDTO();
		option3.setDateReception("date not null");
		option3.setConforme(false);

		PieceDTO option4 = new PieceDTO();
		option4.setDateReception("date not null");
		option4.setConforme(true);
		PieceDTO option5 = new PieceDTO();
		option5.setDateReception("date not null");
		option5.setConforme(true);
		PieceDTO option6 = new PieceDTO();
		option6.setDateReception("date not null");
		option6.setConforme(true);

		PieceDTO option7 = new PieceDTO();
		option7.setTelechargements(Arrays.asList(new TelechargementDTO()));
		piece.setOptions(Arrays.asList(option1, option2, option3, option4, option5, option6, option7));

		return Arrays.asList(piece);
	}
}