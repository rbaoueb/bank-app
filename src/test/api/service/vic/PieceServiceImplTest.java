package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.AssuranceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.DossierDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.IntervenantDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.PieceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.enums.PieceTypeEnum;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DecisionDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.PieceJeuDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockParam;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRecord;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WireMockRunner.class)
@WireMockParam(testUrl = "http://localhost:8181/api/v1/")
@WireMockRecord(trace = false, record = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class PieceServiceImplTest
{
	@Autowired
	@Qualifier("PieceService")
	private PieceService pieceService;

	@Autowired
	@Qualifier("DossierService")
	private DossierService dossierService;

	@Test
	public void find_decision_test()
	{
		DecisionDTO reponse = dossierService.findDecision("101375");
		assertThat(reponse).isNotNull();
	}

	@Test
	public void find_reserves_test() throws Exception
	{
		List<PieceJeuDTO> reponse = pieceService.findPieces("101375", PieceTypeEnum.RESERVE);
		assertThat(reponse).isNotNull();
	}

	@Test
	public void find_justif_test() throws Exception
	{
		List<PieceJeuDTO> reponse = pieceService.findPieces("101375", PieceTypeEnum.JUSTIFICATIVE);
		assertThat(reponse).isNotNull();
	}

	@TestConfiguration
	static class ApiTestConfiguration
	{

		@Bean
		@Primary
		public PieceDAO pieceDaoMock()
		{
			return new PieceDAO(WireMockRunner.baseUrl());
		}

		@Bean
		@Primary
		public IntervenantDAO intervenantDaoMock()
		{
			return new IntervenantDAO(WireMockRunner.baseUrl());
		}

		@Bean
		@Primary
		public DossierDAO dossierDaoMock()
		{
			return new DossierDAO(WireMockRunner.baseUrl());
		}
		@Bean
		@Primary
		public AssuranceDAO assuranceDaoMock()
		{
			return new AssuranceDAO(WireMockRunner.baseUrl());
		}
	}
}