package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.delegate;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.AssuranceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.DossierDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.IntervenantDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.PieceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.vic.DossierService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DossierDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockParam;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRecord;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRunner;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WireMockRunner.class)
@WireMockParam(testUrl = "http://localhost:8181/api/v1/")
@WireMockRecord(trace = false, record = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EspacesuiviPieceServiceImplTest
{

	@Autowired
	@Qualifier("DossierService")
	private DossierService dossierService;

	@Test
	public void dossier_service_delegate_test()
	{
		DossierDTO timeline = this.dossierService.buildInformationsDossier("101375", null);
		assertThat(timeline).isNotNull();
		assertThat(timeline.getEtapes()).isNotNull();
		assertThat(timeline.getEtapes().getSteps()).hasSize(6);
		assertThat(timeline.getAssurances()).hasSize(1);
		assertThat(timeline.getAssurances().get(0).getAssurancesCategorie()).hasSize(2);
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