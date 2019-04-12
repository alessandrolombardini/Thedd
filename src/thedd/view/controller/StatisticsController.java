package thedd.view.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import thedd.view.extensions.AdaptiveLabel;

/**
 * Class that handle the Statistics View.
 */
public class StatisticsController extends ViewNodeControllerImpl {
    @FXML
    private AdaptiveLabel healthValue;
    @FXML
    private Label constitutionValue;
    @FXML
    private Label strengthValue;
    @FXML
    private Label agilityValue;
    @FXML
    private AnchorPane imageContent;
    @FXML
    private AnchorPane container;

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
        this.healthValue.setText(this.getController().getCharacterInformations().getHealthPointValue() + " / "
                + this.getController().getCharacterInformations().getHealthPointMaxValue());
        this.agilityValue.setText(this.getController().getCharacterInformations().getAgilityValue());
        this.constitutionValue.setText(this.getController().getCharacterInformations().getConstitutionValue());
        this.strengthValue.setText(this.getController().getCharacterInformations().getStrengthValue());
        // Set the Character's image.
        Image img = new Image("images/character_image.png");
        BackgroundImage bg = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(1.0, 1.0, true, true, true, false));
        // Third boolean, if false ignore image's ratio dell'immagine, otherwise keep
        // it.
        this.imageContent.setBackground(new Background(bg));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        update();
    }

}
