package fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory;

import fr.harvest.vic.espacesuivi.api.espacesuivi.api.factory.ServiceFactory;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.ApiEntryService;
import fr.harvest.vic.espacesuivi.api.espacesuivi.api.service.ApiService;
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
public class ServiceFactoryTest
{
	@Autowired
	private ServiceFactory serviceFactory;

	@Test
	public void find_entry_service_success_test() throws Exception
	{
		ApiService service = serviceFactory.getService("common");
		assertThat(service).isNotNull();
		assertThat(service).isInstanceOf(ApiEntryService.class);
	}

	@Test(expected = Exception.class)
	public void find_entry_service_error_test() throws Exception
	{
		ApiService service = serviceFactory.getService("blabla");
		assertThat(false);
	}

	@Test(expected = Exception.class)
	public void find_entry_service_with_null_custo_test() throws Exception
	{
		ApiService service = serviceFactory.getService(null);
		assertThat(false);
	}
}
