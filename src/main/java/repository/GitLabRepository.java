package repository;

import client.GitLabClient;
import data.Item;
import data.ItemFactory;
import data.RepositoryMetadata;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.repository.gitlab.GitLabSettingsReader;

import java.nio.file.Path;
import java.util.List;

public class GitLabRepository<T extends Item> implements Repository<T> {
    private final GitLabClient gitLabClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final FileFinder fileFinder;
    private final GitLabSettingsReader gitLabSettings;
    private final RepositoryMetadata repositoryMetadata;
    private final ItemFactory<T> itemFactory;

    public GitLabRepository(
            ItemFactory<T> itemFactory,
            GitLabClient gitLabClient,
            TempFileCreator tempFileCreator,
            ZipExtractor zipExtractor,
            FileFinder fileFinder,
            GitLabSettingsReader gitLabSettings,
            RepositoryMetadata repositoryMetadata) {
        this.itemFactory = itemFactory;
        this.gitLabClient = gitLabClient;
        this.tempFileCreator = tempFileCreator;
        this.zipExtractor = zipExtractor;
        this.fileFinder = fileFinder;
        this.gitLabSettings = gitLabSettings;
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public List<T> loadAllItems() {
        Path downloadLocation = tempFileCreator.createTempDirectory(repositoryMetadata.getTempDirectoryPrefix());
        byte[] itemsAsZip = gitLabClient.downloadRepoAsZip(
                gitLabSettings.repositoryUrl(),
                gitLabSettings.repositoryName(),
                gitLabSettings.apiKey()
        );

        zipExtractor.extractZip(itemsAsZip, downloadLocation);

        return fileFinder.find(downloadLocation, repositoryMetadata.getFileExtension())
                .stream()
                .map(itemFactory::fromFile)
                .toList();
    }
}