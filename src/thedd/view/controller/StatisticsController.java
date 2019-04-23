package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import thedd.model.character.statistics.Statistic;
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
    private AnchorPane playerImage;
    @FXML
    private AnchorPane constitutionImage;
    @FXML
    private AnchorPane strengthImage;
    @FXML
    private AnchorPane agilityImage;
    @FXML
    private AnchorPane lifePointsImage;
    @FXML
    private TableColumn<Status, String> column;
    @FXML
    private TableView<Status> table;

    private static final double BACKGROUND_WIDTH_PERCENTAGE = 1.0;
    private static final double BACKGROUND_HEIGHT_PERCENTAGE = 1.0;
    private final ImageLoader imageFactory = new ImageLoaderImpl();

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        table.getItems().setAll(this.getController().getStatisticsInformation().getCharacterStatuses());
        // Set the Statistics
        this.healthValue.setText(this.getController().getStatisticsInformation().getHealthPointValue() + " / "
                + this.getController().getStatisticsInformation().getHealthPointMaxValue());
        this.agilityValue.setText(this.getController().getStatisticsInformation().getAgilityValue());
        this.constitutionValue.setText(this.getController().getStatisticsInformation().getConstitutionValue());
        this.strengthValue.setText(this.getController().getStatisticsInformation().getStrengthValue());
        // Set the Character's image.
        this.playerImage.setBackground(setBackgroundImage(
                loadStatProfileImage(this.getController().getStatisticsInformation().getCharacterType())));
        // Set Statistic's category image.
        this.agilityImage.setBackground(setBackgroundImage(loadStatCategoryImage(Statistic.AGILITY)));
        addTooltip(agilityImage, Statistic.AGILITY.toString());
        this.lifePointsImage.setBackground(setBackgroundImage(loadStatCategoryImage(Statistic.HEALTH_POINT)));
        addTooltip(lifePointsImage, Statistic.HEALTH_POINT.toString());
        this.constitutionImage.setBackground(setBackgroundImage(loadStatCategoryImage(Statistic.CONSTITUTION)));
        addTooltip(constitutionImage, Statistic.CONSTITUTION.toString());
        this.strengthImage.setBackground(setBackgroundImage(loadStatCategoryImage(Statistic.STRENGTH)));
        addTooltip(strengthImage, Statistic.STRENGTH.toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
//        container.setBackground(new Background(new BackgroundImage(new Image("/images/actionselector/box.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
//                BackgroundPosition.CENTER,
//                new BackgroundSize(BACKGROUND_WIDTH_PERCENTAGE, BACKGROUND_HEIGHT_PERCENTAGE, true, true, false, false))));
        table.setPlaceholder(new Label("Empty"));
        table.setSelectionModel(null);
        column.setSortable(false);
        column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        update();
    }

    private Background setBackgroundImage(final Image img) {
        final BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BACKGROUND_WIDTH_PERCENTAGE, BACKGROUND_HEIGHT_PERCENTAGE, true, true, true, false));
        // Third boolean, if false ignore image's ratio, otherwise keep it.
        return new Background(bg);
    }

    private Image loadStatCategoryImage(final Statistic type) {
        return imageFactory.loadSingleImage(DirectoryPicker.STATISTICS_CATEGORIES, type.toString());
    }

    private Image loadStatProfileImage(final String name) {
        return imageFactory.loadSingleImage(DirectoryPicker.CHARACTERS_CLOSEUP, name);
    }

    private void addTooltip(final Node node, final String text) {
        Tooltip.install(node, new Tooltip(text));
    }
}
