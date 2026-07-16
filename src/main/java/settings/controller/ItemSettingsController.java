package settings.controller;

import burp.api.montoya.persistence.Preferences;
import data.ItemMetadata;
import settings.defaultsavelocation.DefaultSaveLocationSettings;
import settings.repository.RemoteRepositorySettings;
import settings.repository.RepositorySettings;
import settings.repository.filesystem.FileSystemRepositorySettings;

import static settings.repository.RemoteRepositoryConfig.GITHUB;
import static settings.repository.RemoteRepositoryConfig.GITLAB;

public class ItemSettingsController {
    private final DefaultSaveLocationSettings defaultSaveLocationSettings;
    private final RepositorySettings repositorySettings;
    private final RemoteRepositorySettings gitHubSettings;
    private final RemoteRepositorySettings gitLabSettings;
    private final FileSystemRepositorySettings fileSystemRepositorySettings;

    public ItemSettingsController(Preferences preferences, ItemMetadata itemMetadata) {
        this.defaultSaveLocationSettings = new DefaultSaveLocationSettings(preferences, itemMetadata);
        this.repositorySettings = new RepositorySettings(preferences, itemMetadata);
        this.gitHubSettings = new RemoteRepositorySettings(preferences, itemMetadata, GITHUB);
        this.gitLabSettings = new RemoteRepositorySettings(preferences, itemMetadata, GITLAB);
        this.fileSystemRepositorySettings = new FileSystemRepositorySettings(preferences, itemMetadata);
    }

    public DefaultSaveLocationSettings defaultSaveLocationSettings() {
        return defaultSaveLocationSettings;
    }

    public RepositorySettings repositorySettings() {
        return repositorySettings;
    }

    public RemoteRepositorySettings gitHubSettings() {
        return gitHubSettings;
    }

    public RemoteRepositorySettings gitLabSettings() {
        return gitLabSettings;
    }

    public FileSystemRepositorySettings fileSystemRepositorySettings() {
        return fileSystemRepositorySettings;
    }
}
