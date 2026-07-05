package settings.repository;

import burp.api.montoya.persistence.Preferences;
import data.RepositoryMetadata;
import settings.AbstractSettings;

public class RemoteRepositorySettings extends AbstractSettings implements RemoteRepositorySettingsReader {
    private final RepositoryMetadata repositoryMetadata;
    private final RemoteRepositoryConfig repository;

    public RemoteRepositorySettings(Preferences preferences, RepositoryMetadata repositoryMetadata, RemoteRepositoryConfig repository) {
        super(preferences);
        this.repositoryMetadata = repositoryMetadata;
        this.repository = repository;
    }

    @Override
    public String repositoryUrl() {
        return loadStringFromPreferences(repository.preferencesPrefix + repositoryMetadata.getRepositoryUrlKey(), repository.apiUrl);
    }

    public void setRepositoryUrl(String repositoryUrl) {
        saveStringToPreferences(repository.preferencesPrefix + repositoryMetadata.getRepositoryUrlKey(), repositoryUrl);
    }

    @Override
    public String repositoryName() {
        return loadStringFromPreferences(repository.preferencesPrefix + repositoryMetadata.getRepositoryNameKey(), repositoryMetadata.getDefaultRepositoryNameValue());
    }

    public void setRepositoryName(String repositoryName) {
        saveStringToPreferences(repository.preferencesPrefix + repositoryMetadata.getRepositoryNameKey(), repositoryName);
    }

    @Override
    public String apiKey() {
        return loadStringFromPreferences(repository.apiKey, null);
    }

    public void setApiKey(String apiKey) {
        saveStringToPreferences(repository.apiKey, apiKey);
    }
}
