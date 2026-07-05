package client;

public interface HttpClient {
    byte[] downloadRepoAsZip(String repositoryUrl, String repositoryId, String apiKey);
}
