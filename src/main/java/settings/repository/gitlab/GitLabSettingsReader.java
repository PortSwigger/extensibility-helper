package settings.repository.gitlab;

public interface GitLabSettingsReader {
    String repositoryUrl();

    String repositoryName();

    String apiKey();
}
