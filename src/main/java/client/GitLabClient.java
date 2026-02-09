package client;

import network.RequestSender;

import java.util.Map;

import static java.util.Collections.emptyMap;

public class GitLabClient {
    private static final String REPOSITORY_ZIPBALL_URL_TEMPLATE = "%s/api/v4/projects/%s/repository/archive.zip";

    private final RequestSender requestSender;

    public GitLabClient(RequestSender requestSender) {
        this.requestSender = requestSender;
    }

    public byte[] downloadRepoAsZip(String repositoryUrl, String repositoryId, String apiKey) {
        String trimmedRepositoryUrl = repositoryUrl.trim().replaceAll("/$", "");
        String url = REPOSITORY_ZIPBALL_URL_TEMPLATE.formatted(trimmedRepositoryUrl, repositoryId);

        Map<String, String> headers = apiKey != null && !apiKey.isBlank() ?
                Map.of("PRIVATE-TOKEN", apiKey) :
                emptyMap();

        return requestSender.sendRequest(url, headers).body().getBytes();
    }
}