package ui.view.pane.settings;

import settings.repository.RemoteRepositoryConfig;
import settings.repository.RemoteRepositorySettings;
import ui.view.listener.SingleHandlerDocumentListener;

import javax.swing.*;
import java.awt.*;

import static java.awt.GridBagConstraints.FIRST_LINE_START;

class RemoteRepositorySettingsSubComponent extends JPanel {
    private final JLabel descriptionLabelSecondLine;
    private final JLabel descriptionLabelThirdLine;
    private final JLabel repoNameDescription;
    private final JLabel repoUrlDescription;
    private final JLabel apiKeyDescription = new JLabel("API key (only needed if it is a private repo)");
    private final JTextField repoNameField = new JTextField();
    private final JTextField repoUrlField = new JTextField();
    private final JTextField apiKeyField = new JTextField();

    private final RemoteRepositorySettings repositorySettings;

    RemoteRepositorySettingsSubComponent(RemoteRepositorySettings repositorySettings, RemoteRepositoryConfig repositoryConfig) {
        this.repositorySettings = repositorySettings;

        initialiseUi();
        repoNameDescription = new JLabel(repositoryConfig.nameDescription);
        repoUrlDescription = new JLabel(repositoryConfig.urlDescription);
        descriptionLabelSecondLine = new JLabel("If the repo isn't public, you'll need to specify an API key too. You can look at %s's documentation to find out how to create one.".formatted(repositoryConfig.repositoryName));
        descriptionLabelThirdLine = new JLabel("If you're using the same API key across multiple applications, you might exceed %s's rate limit, meaning that this extension will no longer work until the rate limit resets.".formatted(repositoryConfig.repositoryName));
    }

    private void initialiseUi() {
        setupLayout();

        setupRepoNameField();
        setupRepoURLField();
        setupApiKeyField();

        EventQueue.invokeLater(this::addElements);
    }

    private void setupLayout() {
        GridBagLayout layout = new GridBagLayout();
        layout.columnWidths = new int[]{0, 20, 0};
        layout.rowHeights = new int[]{0, 5, 0, 30, 0, 15, 0, 15, 0};

        setLayout(layout);
    }

    private void setupRepoNameField() {
        repoNameField.setText(repositorySettings.repositoryName());
        repoNameField.setColumns(40);
        repoNameField.getDocument().addDocumentListener(
                new SingleHandlerDocumentListener(e -> repositorySettings.setRepositoryName(repoNameField.getText()))
        );
    }

    private void setupRepoURLField() {
        repoUrlField.setText(repositorySettings.repositoryUrl());
        repoUrlField.setColumns(40);
        repoUrlField.getDocument().addDocumentListener(
                new SingleHandlerDocumentListener(e -> repositorySettings.setRepositoryUrl(repoUrlField.getText()))
        );
    }

    private void setupApiKeyField() {
        apiKeyField.setText(repositorySettings.apiKey());
        apiKeyField.setColumns(40);
        apiKeyField.getDocument().addDocumentListener(
                new SingleHandlerDocumentListener(e -> repositorySettings.setApiKey(apiKeyField.getText()))
        );
    }

    private void addElements() {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 0;
        constraints.gridwidth = 3;
        add(descriptionLabelSecondLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        add(descriptionLabelThirdLine, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 4;
        add(repoUrlDescription, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 4;
        constraints.weightx = 1;
        add(repoUrlField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 6;
        add(repoNameDescription, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 6;
        constraints.weightx = 1;
        add(repoNameField, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridy = 8;
        add(apiKeyDescription, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = FIRST_LINE_START;
        constraints.gridx = 2;
        constraints.gridy = 8;
        constraints.insets = new Insets(0, 0, 10, 0);

        add(apiKeyField, constraints);
    }
}
