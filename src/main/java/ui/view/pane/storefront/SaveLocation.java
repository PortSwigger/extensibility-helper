package ui.view.pane.storefront;

import settings.defaultsavelocation.DefaultSaveLocationSettingsReader;
import ui.view.component.filechooser.ChooseMode;
import ui.view.component.filechooser.FileChooser;

import java.awt.*;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Supplier;

import static ui.view.component.filechooser.ChooseMode.SAVE_DIRECTORIES_ONLY;

public class SaveLocation {
    private final DefaultSaveLocationSettingsReader saveLocationSettingsReader;
    private final Supplier<Component> parent;

    public SaveLocation(DefaultSaveLocationSettingsReader saveLocationSettingsReader, Supplier<Component> parent) {
        this.saveLocationSettingsReader = saveLocationSettingsReader;
        this.parent = parent;
    }

    public Optional<Path> find() {
        return find(SAVE_DIRECTORIES_ONLY, null);
    }

    public Optional<Path> find(ChooseMode chooseMode, String defaultFileName) {
        return saveLocationSettingsReader
                .defaultSaveLocation()
                .or(() -> new FileChooser(chooseMode, parent).prompt(defaultFileName));
    }
}
