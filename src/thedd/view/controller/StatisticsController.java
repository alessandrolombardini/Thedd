package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import thedd.model.combat.status.Status;
import thedd.view.extensions.AdaptiveFontLabel;
import thedd.view.imageloader.DirectoryPicker;
import thedd.view.imageloader.ImageLoader;
import thedd.view.imageloader.ImageLoaderImpl;

/**
 * Class that handle the Statistics View.
 */
public class StatisticsController extends ViewNodeControllerImpl {
    @FXML
    private AdaptiveFontLabel healthValue;
    @FXML
    private AdaptiveFontLabel constitutionValue;
    @FXML
    private AdaptiveFontLabel strengthValue;
    @FXML
    private AdaptiveFontLabel agilityValue;
    @FXML
    private AnchorPane imageContent;
    @FXML
    private TableColumn<Status, String> column;
    private static final double BACKGROUND_WIDTH_PERCENTAGE = 1.0;
    private static final double BACKGROUND_HEIGHT_PERCENTAGE = 1.0;
    private final ImageLoader imageFactory = new ImageLoaderImpl();

    /**
     * Statistics controller constructor.
     */
    public StatisticsController() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        // Set the Statistics
        this.healthValue.setText(this.getController().getStatisticsInformation().getHealthPointValue() + " / "
                + this.getController().getStatisticsInformation().getHealthPointMaxValue());
        this.agilityValue.setText(this.getController().getStatisticsInformation().getAgilityValue());
        this.constitutionValue.setText(this.getController().getStatisticsInformation().getConstitutionValue());
        this.strengthValue.setText(this.getController().getStatisticsInformation().getStrengthValue());
        // Set the Character's image.
        Image img = imageFactory.loadSingleImage(DirectoryPicker.STATISTICS_PROFILES, this.getController().getStatisticsInformation().getCharacterType());
        BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BACKGROUND_WIDTH_PERCENTAGE, BACKGROUND_HEIGHT_PERCENTAGE, true, true, true, false));
        // Third boolean, if false ignore image's ratio, otherwise keep it.
        this.imageContent.setBackground(new Background(bg));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        update();
        column.setSortable(false);
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
    }
}
