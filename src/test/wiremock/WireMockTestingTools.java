package fr.harvest.vic.espacesuivi.api.espacesuivi.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.WireMockApp;
import com.github.tomakehurst.wiremock.http.RequestMethod;
import com.github.tomakehurst.wiremock.matching.RequestPatternBuilder;
import com.github.tomakehurst.wiremock.recording.RecordSpecBuilder;
import com.github.tomakehurst.wiremock.recording.RecordingStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WireMockTestingTools
{
	// Header that can be added to differentiate mocks per test methods
	private final String headerPerTest;

	private Map<String, WireMockServer> serverRegistry;

	WireMockTestingTools(Map<String, WireMockServer> serverRegistry, String headerPerTest) {
		this.serverRegistry = serverRegistry;
		this.headerPerTest = headerPerTest;
	}

	static String buildMappingsPath(Class<?> testClass) {
		return "src/test/resources/" + testClass.getCanonicalName().replace('.', '/');
	}

	void setUp(Class<?> testClass, String mappingsPath) {
		List<WireMockParam> wireMockParams = new ArrayList<>();
		this.extractAllWireMockParams(testClass, wireMockParams);

		WireMockRecord forceRecordingAnnotation = testClass.getAnnotation(WireMockRecord.class);
		boolean forceRecording = forceRecordingAnnotation != null && forceRecordingAnnotation.record();
		boolean consoleTracer = forceRecordingAnnotation != null && forceRecordingAnnotation.trace();

		for (WireMockParam wireMockParam : wireMockParams) {
			String mockFilesLocation;
			if (wireMockParams.size() == 1) {
				// do not use default folder if there is only one target (mostly for compatibility with already saved mocks)
				mockFilesLocation = mappingsPath;
			} else {
				mockFilesLocation = mappingsPath + "/" + wireMockParam.name();
			}

			WireMockServer wireMockServer = this.prepareWireMockServer(wireMockParam.name(), consoleTracer, mockFilesLocation);
			if (forceRecording || wireMockParam.record()) {
				// TODO force rejection in case of Jenkins CI run
				this.prepareRecordingMapping(wireMockParam, wireMockServer);
			}
		}
	}

	void cleanUp() {
		serverRegistry.values().forEach(server -> {
			if (server.getRecordingStatus().getStatus() == RecordingStatus.Recording) {
				server.stopRecording();
			}

			server.stop();
		});
		// clear it for the next test as it is static
		serverRegistry.clear();
	}

	Map<String, WireMockServer> getServerRegistry() {
		return serverRegistry;
	}

	private WireMockServer prepareWireMockServer(String serverName, boolean consoleTracer, String mockLocation) {
		SingleRootFileSource fileSource = new SingleRootFileSource(mockLocation);
		fileSource.child(WireMockApp.FILES_ROOT).createIfNecessary();
		fileSource.child(WireMockApp.MAPPINGS_ROOT).createIfNecessary();

		WireMockServer wireMockServer = serverRegistry.get(serverName);

		if (wireMockServer == null) {
			wireMockServer = new WireMockServer(wireMockConfig()
					.port(0)
					.fileSource(new SingleRootFileSource(mockLocation))
					.notifier(new ConsoleNotifier(consoleTracer)));
			wireMockServer.start();
			serverRegistry.put(serverName, wireMockServer);
		}

		return wireMockServer;
	}

	private void extractAllWireMockParams(Class<?> type, List<WireMockParam> wireMockParams) {
		if (!Object.class.equals(type) && type != null) {
			// Look for interfaces
			Class<?>[] interfaces = type.getInterfaces();
			for (Class<?> interfaceType : interfaces) {
				this.extractAllWireMockParams(interfaceType, wireMockParams);
			}

			this.extractAllWireMockParams(type.getSuperclass(), wireMockParams);

			Collections.addAll(wireMockParams, type.getAnnotationsByType(WireMockParam.class));
		}
	}

	private void prepareRecordingMapping(WireMockParam wireMockParam, WireMockServer wireMockServer) {
		RequestPatternBuilder requestPatternBuilder = RequestPatternBuilder.newRequestPattern(RequestMethod.ANY, WireMock.urlPathMatching(wireMockParam.pathPattern()));
		RecordSpecBuilder recordBuilder = new RecordSpecBuilder()
				.forTarget(wireMockParam.testUrl())
				.captureHeader(headerPerTest)
				.onlyRequestsMatching(requestPatternBuilder)
				.allowNonProxied(true);

		wireMockServer.startRecording(recordBuilder);
	}

}
