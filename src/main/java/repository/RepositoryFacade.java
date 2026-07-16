package repository;

import data.Item;
import settings.repository.RepositorySettingsReader;

import java.util.List;

public class RepositoryFacade<T extends Item> implements Repository<T> {
    private final FileSystemRepository<T> fileSystemRepository;
    private final RemoteRepository<T> gitHubRepository;
    private final RemoteRepository<T> gitLabRepository;
    private final RepositorySettingsReader repositorySettingsReader;

    public RepositoryFacade(
            RepositorySettingsReader repositorySettingsReader,
            RemoteRepository<T> gitHubRepository,
            RemoteRepository<T> gitLabRepository,
            FileSystemRepository<T> fileSystemRepository) {
        this.fileSystemRepository = fileSystemRepository;
        this.gitHubRepository = gitHubRepository;
        this.gitLabRepository = gitLabRepository;
        this.repositorySettingsReader = repositorySettingsReader;
    }

    @Override
    public List<T> loadAllItems() {
        Repository<T> repository =  switch (repositorySettingsReader.repositoryType()) {
            case FILESYSTEM -> fileSystemRepository;
            case GITHUB -> gitHubRepository;
            case GITLAB -> gitLabRepository;
        };

        return repository.loadAllItems();
    }
}
