package settings.repository;

public enum RemoteRepositoryConfig {
    GITHUB( "https://api.github.com", "github_settings.api_key", ""),
    GITLAB("https://gitlab.com", "gitlab_settings.api_key", "gitlab_");

    final String apiUrl;
    final String apiKey;
    final String preferencesPrefix;

    RemoteRepositoryConfig(String apiUrl, String apiKey, String preferencesPrefix) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.preferencesPrefix = preferencesPrefix;
    }
}
