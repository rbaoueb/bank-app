package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.delegate;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.AssuranceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.DossierDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.IntervenantDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.PieceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.api.server.EspacesuiviDossierApiDelegate;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DossierDTO;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WireMockRunner.class)
@WireMockParam(testUrl = "http://localhost:8181/api/v1/")
@WireMockRecord(trace = false, record = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class EspacesuiviDossierApiDelegateImplTest
{

	@Autowired
	@Qualifier("EspacesuiviDossierApiDelegate")
	private EspacesuiviDossierApiDelegate espacesuiviDossierApiDelegate;

	@Test
	public void dossier_delegate_find_dossier_test()
	{
		ResponseEntity<DossierDTO> response = this.espacesuiviDossierApiDelegate.findDossier("common", "101375", null);
		assertThat(response).isNotNull();
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
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