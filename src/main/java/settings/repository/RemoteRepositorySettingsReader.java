package settings.repository;

public interface RemoteRepositorySettingsReader {
    String repositoryUrl();

    String repositoryName();

    String apiKey();
}
