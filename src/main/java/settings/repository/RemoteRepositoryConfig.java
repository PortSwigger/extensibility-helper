package settings.repository;

public enum RemoteRepositoryConfig {
    GITHUB( "https://api.github.com", "github_settings.api_key", "", "GitHub", "Repo name (in owner/repo format e.g. portswigger/bchecks)", "GitHub API URL"),
    GITLAB("https://gitlab.com", "gitlab_settings.api_key", "gitlab_", "GitLab", "GitLab Project ID (integer value)", "GitLab URL");

    final String apiUrl;
    final String apiKey;
    final String preferencesPrefix;

    public final String repositoryName;
    public final String nameDescription;
    public final String urlDescription;

    RemoteRepositoryConfig(String apiUrl, String apiKey, String preferencesPrefix, String repositoryName, String nameDescription, String urlDescription) {
        this.apiUrl = apiUrl;
        this.apiKey = apiKey;
        this.preferencesPrefix = preferencesPrefix;
        this.repositoryName = repositoryName;
        this.nameDescription = nameDescription;
        this.urlDescription = urlDescription;
    }
}
