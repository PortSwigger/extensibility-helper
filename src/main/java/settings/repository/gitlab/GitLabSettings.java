package settings.repository.gitlab;

import burp.api.montoya.persistence.Preferences;
import data.RepositoryMetadata;
import settings.AbstractSettings;

public class GitLabSettings extends AbstractSettings implements GitLabSettingsReader {
    private static final String GITLAB_API_URL = "https://gitlab.com";
    private static final String API_KEY_KEY = "gitlab_settings.api_key";
    private final RepositoryMetadata repositoryMetadata;

    public GitLabSettings(Preferences preferences, RepositoryMetadata repositoryMetadata) {
        super(preferences);
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences("gitlab_" + repositoryMetadata.getRepositoryUrlKey(), GITLAB_API_URL);
    }

    public void setRepositoryUrl(String repositoryUrl) {
        saveStringToPreferences("gitlab_" + repositoryMetadata.getRepositoryUrlKey(), repositoryUrl);
    }

    @Override
    public String repositoryName() {
        return loadStringFromPreferences("gitlab_" + repositoryMetadata.getRepositoryNameKey(), repositoryMetadata.getDefaultRepositoryNameValue());
    }

    public void setRepositoryName(String repositoryName) {
        saveStringToPreferences("gitlab_" + repositoryMetadata.getRepositoryNameKey(), repositoryName);
    }

    @Override
    public String apiKey() {
        return loadStringFromPreferences(API_KEY_KEY, null);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(API_KEY_KEY, apiKey);
    }
}
