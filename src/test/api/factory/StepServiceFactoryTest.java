package fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.model.StepYamlModel;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.ApiService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.timeline.step.StepService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StepServiceFactoryTest
{
	@Autowired
	private StepServiceFactory stepServiceFactory;

	@Autowired
	private StepYamlModel stepYamlModel;

	@Test
	public void find_entry_service_success_test()
	{
		this.stepYamlModel.getEtapes().forEach(e -> {
			StepService service  = stepServiceFactory.wireService(e.getQualifier());
			assertThat(service).isNotNull();
		});
	}

	@Test(expected = Exception.class)
	public void find_entry_service_error_test()
	{
		StepService service = stepServiceFactory.wireService("blabla");
		assertThat(false);
	}

	@Test(expected = Exception.class)
	public void find_entry_service_with_null_step_test() throws Exception
	{
		StepService service = stepServiceFactory.wireService(null);
		assertThat(false);
	}
}