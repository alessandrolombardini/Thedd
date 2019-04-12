package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import thedd.model.item.Item;
import thedd.view.extensions.AdaptiveLabel;

/**
 * Class that handle the Inventory View.
 */
public class InventoryController extends ViewNodeControllerImpl {
    @FXML
    private TableView<Item> table;
    @FXML
    private TableColumn<Item, String> column;
    @FXML
    private AdaptiveLabel content;
    @FXML
    private Button useButton;
    @FXML
    private Button deleteButton;
    private static final String EQUIP_BUTTON_LABEL = "Equip";
    private static final String UNEQUIP_BUTTON_LABEL = "Unequip";
    private static final String USE_BUTTON_LABEL = "Use";

    /**
     * Inventory Controller Constructor.
     */
    public InventoryController() {
    }

    /**
     * Initializes the class. This method is automatically called after the fxml
     * file has been loaded.
     */
    @FXML
    private void initialize() {
        content.setStyle("-fx-background-color: #444;");
        column.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName() + addTag(cellData.getValue())));
        showItemDetails(null);
        table.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
    }

    /**
     * Method that handle the Use button.
     */
    @FXML
    public final void handleUseButton() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.table.getItems().setAll(this.getController().getCharacterInformations().getAllItemsList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        update();
    }

    /**
     * Fill the description label to show details about the item. If the specified
     * item is null, the label is set as default.
     * 
     * @param item the item or null
     */
    public void showItemDetails(final Item item) {
        if (item != null) {
            if (this.getController().getCharacterInformations().isEquipped(item)) {
                content.setText(item.getName() + " is in your equipments.\n\nDescription: " + item.getDescription());
                this.useButton.setText(UNEQUIP_BUTTON_LABEL);
            } else {
                content.setText(
                        item.getName() + ": a " + (item.isUsable() ? "Usable" : "Equipable") + " item. You have "
                                + this.getController().getCharacterInformations().getInventoryItemQuantity(item)
                                + " of them.\n\nDescription: \n" + item.getDescription());
                if (item.isUsable()) {
                    this.useButton.setText(USE_BUTTON_LABEL);
                } else {
                    this.useButton.setText(EQUIP_BUTTON_LABEL);
                }
            }
            this.useButton.setVisible(true);
        } else {
            content.setText("Select an Item first.");
            this.useButton.setVisible(false);
        }
    }

    private String addTag(final Item item) {
        if (this.getController().getCharacterInformations().isEquipped(item)) {
            return "(E)";
        } else {
            return "";
        }
    }
}
