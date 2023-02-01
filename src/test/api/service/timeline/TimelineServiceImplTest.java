package fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.AssuranceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.DossierDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.IntervenantDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.dao.PieceDAO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.AssuranceDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.DossierDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.SuiviDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.TimelineDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockParam;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRecord;
import fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock.WireMockRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(WireMockRunner.class)
@WireMockParam(testUrl = "http://localhost:8181/api/v1/")
@WireMockRecord(trace = false, record = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TimelineServiceImplTest
{

	@Autowired
	private TimelineService timelineService;

	@Test
	public void timeline_builder_test()
	{
		DossierDTO dossierDTO = initDossier();
		TimelineDTO timeline = this.timelineService.build(dossierDTO);
		assertThat(timeline.getSteps()).isNotNull();
		assertThat(timeline.getSteps().size()).isGreaterThan(0);
	}

	private final DossierDTO initDossier()
	{
		DossierDTO dto = new DossierDTO();
		dto.setNumeroDossier("101375");
		dto.setSuivi(new SuiviDTO());
		dto.setAssurances(Arrays.asList(new AssuranceDTO()));
		return dto;
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