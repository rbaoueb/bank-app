package fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.internal.runners.statements.Fail;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;
import org.springframework.test.annotation.ProfileValueUtils;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

public class WireMockRunner extends SpringJUnit4ClassRunner
{
	private static Map<String, WireMockServer> SERVERS_BY_NAME = new HashMap<>();

	public static final String HEADER_PER_TEST = "Wiremock-test";

	private final WireMockTestingTools delegate;
	private final String mappingsPath;

	public WireMockRunner(Class<?> testClass) throws InitializationError {
		super(testClass);

		this.delegate = new WireMockTestingTools(SERVERS_BY_NAME, HEADER_PER_TEST);
		this.mappingsPath = WireMockTestingTools.buildMappingsPath(testClass);
	}

	public static String baseUrl() {
		return baseUrl(WireMockParam.DEFAULT_SERVER_NAME);
	}

	public static String baseUrl(String serverName) {
		return SERVERS_BY_NAME.get(serverName).baseUrl() + "/";
	}

	public static WireMockServer wiremockServer() {
		return SERVERS_BY_NAME.get(WireMockParam.DEFAULT_SERVER_NAME);
	}


	@Override
	public void run(RunNotifier notifier) {
		try {
			if (!ProfileValueUtils.isTestEnabledInThisEnvironment(this.getTestClass().getJavaClass())) {
				notifier.fireTestIgnored(this.getDescription());
			} else {
				delegate.setUp(getTestClass().getJavaClass(), mappingsPath);
				super.run(notifier);
			}
		} finally {
			delegate.cleanUp();
		}
	}

	@Override
	protected void runChild(final FrameworkMethod method, RunNotifier notifier) {
		Description description = this.describeChild(method);
		if (this.isTestMethodIgnored(method)) {
			notifier.fireTestIgnored(description);
		} else {
			Object statement;
			try {
				delegate.getServerRegistry().values().forEach(WireMockServer::resetScenarios);
				statement = this.methodBlock(method);
			} catch (Throwable var6) {
				statement = new Fail(var6);
			}

			this.runLeaf((Statement)statement, description, notifier);
		}


//		super.runChild(method, notifier);
	}
}
