package fr.harvest.vic.espacesuivi.api.espacesuivi.api.collector;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.mapper.AssuranceMapper;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.dto.AssuranceInfoVicDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.dto.AssuranceVicDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.dto.AssuranceVicResponseDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.AssuranceDTO;
import fr.harvest.vic.espacesuivi.api.espacesuivi.desc.core.model.server.StepDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DetailAssuranceCollectorTest
{

	@Autowired
	private AssuranceMapper assuranceMapper;

	@Test
	public void step_acces_test()
	{
		AssuranceVicResponseDTO intervenantInfo = buildAssuranceVicResponseDto();
		List<AssuranceDTO> asurances = assuranceMapper.assuranceInfoVicListToAssuranceDtoList(intervenantInfo.getAssurances());
		assertThat(asurances).hasSize(1);
		assertThat(asurances.get(0).getAssurancesCategorie()).hasSize(4);
	}

	private final AssuranceVicResponseDTO buildAssuranceVicResponseDto()
	{
		AssuranceVicResponseDTO intervenantInfo = new AssuranceVicResponseDTO();
		intervenantInfo.setAssurances(buildAssuranceInfoVicDto());
		return intervenantInfo;
	}

	private final List<AssuranceInfoVicDTO> buildAssuranceInfoVicDto()
	{
		AssuranceInfoVicDTO assurInfoVic = new AssuranceInfoVicDTO();
		assurInfoVic.setAssurances(buildAssuranceVicDto());
		return Arrays.asList(assurInfoVic);
	}

	private final List<AssuranceVicDTO> buildAssuranceVicDto()
	{
		AssuranceVicDTO assurance1 = new AssuranceVicDTO();
		assurance1.setStatut(AssuranceVicDTO.StatutEnum.ACCORDE);
		assurance1.setType(AssuranceVicDTO.TypeEnum.GROUPE);
		AssuranceVicDTO assurance2 = new AssuranceVicDTO();
		assurance2.setStatut(AssuranceVicDTO.StatutEnum.EN_ATTENTE);
		assurance2.setType(AssuranceVicDTO.TypeEnum.GROUPE);
		AssuranceVicDTO assurance3 = new AssuranceVicDTO();
		assurance3.setStatut(AssuranceVicDTO.StatutEnum.EN_ATTENTE);
		assurance3.setType(AssuranceVicDTO.TypeEnum.GROUPE);
		AssuranceVicDTO assurance4 = new AssuranceVicDTO();
		assurance4.setStatut(AssuranceVicDTO.StatutEnum.EN_ATTENTE);
		assurance4.setType(AssuranceVicDTO.TypeEnum.DELEGATION);
		AssuranceVicDTO assurance5 = new AssuranceVicDTO();
		assurance5.setStatut(AssuranceVicDTO.StatutEnum.REFUSE);
		assurance5.setType(AssuranceVicDTO.TypeEnum.DELEGATION);

		AssuranceVicDTO assurance6 = new AssuranceVicDTO();
		assurance6.setStatut(AssuranceVicDTO.StatutEnum.ACCORDE);
		assurance6.setType(null);
		return Arrays.asList(assurance1, assurance2, assurance3, assurance4, assurance5, assurance6);
	}
}