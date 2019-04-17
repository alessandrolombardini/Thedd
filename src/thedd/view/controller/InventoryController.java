package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import thedd.model.item.Item;
import thedd.model.item.usableitem.UsableItem;
import thedd.view.extensions.AdaptiveFontButton;
import thedd.view.extensions.ScrollableText;

/**
 * Class that handle the Inventory View.
 */
public class InventoryController extends ViewNodeControllerImpl {
    @FXML
    private TableView<Item> table;
    @FXML
    private TableColumn<Item, String> column;
    @FXML
    private ScrollableText content;
    @FXML
    private AdaptiveFontButton useButton;
    @FXML
    private AdaptiveFontButton deleteButton;
    @FXML
    private AdaptiveFontButton backButton;
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
        final Item selection = table.getSelectionModel().getSelectedItem();
        if (this.useButton.getText().equals(USE_BUTTON_LABEL)) {
            this.getController().useItem(selection);
        } else if (this.useButton.getText().equals(EQUIP_BUTTON_LABEL)) {
            this.getController().equipItem(selection);
        } else {
            this.getController().unequipItem(selection);
        }
    }

    /**
     * Method that handle the Delete button.
     */
    @FXML
    public final void handleDeleteButton() {
        this.getController().deleteItem(table.getSelectionModel().getSelectedItem());
    }

    /**
     * Method that handle the Back button.
     */
    @FXML
    public final void handleBackButton() {
        // TO-DO
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        this.table.getItems().setAll(this.getController().getInventoryInformations().getAllItemsList());
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
            this.useButton.setVisible(true);
            this.deleteButton.setVisible(true);
            if (this.getController().getInventoryInformations().isEquipped(item)) {
                this.content.setText(item.getName() + " is in your equipments.\n\nDescription: \n"
                        + item.getDescription() + "\n\nEffects: \n" + item.getEffectDescription());
                this.useButton.setText(UNEQUIP_BUTTON_LABEL);
                if (this.getController().isCombatActive()) { // Check in order to unequip only out of a combat
                    this.useButton.setDisable(true);
                } else {
                    this.useButton.setDisable(false);
                }
                this.deleteButton.setDisable(true); // You can't delete an item when this is equipped
            } else {
                this.content.setText(
                        item.getName() + ": a " + (item.isUsable() ? "Usable" : "Equipable") + " item. You have "
                                + this.getController().getInventoryInformations().getInventoryItemQuantity(item)
                                + " of them.\n\nDescription: \n" + item.getDescription() + "\n\nEffects: \n"
                                + item.getEffectDescription());
                if (item.isUsable()) {
                    UsableItem usable = (UsableItem) item;
                    this.useButton.setText(USE_BUTTON_LABEL);
                    if (usable.isUsableInCombat() && this.getController().isCombatActive()) {
                        this.useButton.setDisable(false);
                    } else if (usable.isUsableOutOfCombat() && !this.getController().isCombatActive()) {
                        this.useButton.setDisable(false);
                    } else {
                        this.useButton.setDisable(true);
                    }
                } else {
                    if (this.getController().isCombatActive()) { // Check in order to equip only out of a combat
                        this.useButton.setDisable(true);
                    } else {
                        this.useButton.setDisable(false);
                    }
                    this.useButton.setText(EQUIP_BUTTON_LABEL);
                }
            }
        } else {
            this.content.setText("Select an Item first.");
            this.deleteButton.setVisible(false);
            this.useButton.setVisible(false);
        }
    }

    private String addTag(final Item item) {
        if (this.getController().getInventoryInformations().isEquipped(item)) {
            return "(E)";
        } else {
            return "";
        }
    }
}
