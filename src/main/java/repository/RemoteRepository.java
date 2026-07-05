package repository;

import client.HttpClient;
import data.Item;
import data.ItemFactory;
import data.RepositoryMetadata;
import file.finder.FileFinder;
import file.temp.TempFileCreator;
import file.zip.ZipExtractor;
import settings.repository.RemoteRepositorySettingsReader;

import java.nio.file.Path;
import java.util.List;

public class RemoteRepository<T extends Item> implements Repository<T> {
    private final HttpClient httpClient;
    private final TempFileCreator tempFileCreator;
    private final ZipExtractor zipExtractor;
    private final FileFinder fileFinder;
    private final RemoteRepositorySettingsReader gitLabSettings;
    private final RepositoryMetadata repositoryMetadata;
    private final ItemFactory<T> itemFactory;

    public RemoteRepository(
            ItemFactory<T> itemFactory,
            HttpClient httpClient,
            TempFileCreator tempFileCreator,
            ZipExtractor zipExtractor,
            FileFinder fileFinder,
            RemoteRepositorySettingsReader gitLabSettings,
            RepositoryMetadata repositoryMetadata) {
        this.itemFactory = itemFactory;
        this.httpClient = httpClient;
        this.tempFileCreator = tempFileCreator;
        this.zipExtractor = zipExtractor;
        this.fileFinder = fileFinder;
        this.gitLabSettings = gitLabSettings;
        this.repositoryMetadata = repositoryMetadata;
    }

    @Override
    public List<T> loadAllItems() {
        Path downloadLocation = tempFileCreator.createTempDirectory(repositoryMetadata.getTempDirectoryPrefix());
        byte[] itemsAsZip = httpClient.downloadRepoAsZip(
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