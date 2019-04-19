package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import thedd.model.item.Item;
import thedd.model.item.usableitem.UsableItem;
import thedd.view.extensions.AdaptiveFontButton;
import thedd.view.extensions.AdaptiveFontScrollableText;

/**
 * Class that handle the Inventory View.
 */
public class InventoryController extends ViewNodeControllerImpl {
    @FXML
    private TableView<Item> table;
    @FXML
    private TableColumn<Item, String> column;
    @FXML
    private AdaptiveFontScrollableText content;
    @FXML
    private AdaptiveFontButton useButton;
    @FXML
    private AdaptiveFontButton deleteButton;
    @FXML
    private AdaptiveFontButton backButton;
    private static final String EQUIP_BUTTON_LABEL = "Equip";
    private static final String UNEQUIP_BUTTON_LABEL = "Unequip";
    private static final String USE_BUTTON_LABEL = "Use";
    private static final String ERROR = "ERROR";
    private static final String EQUIP_ERROR_DESCRIPTION = "You can't equip this item because its slot is already full.\n"
            + "Please liberate the slot before to equip this item.";

    /**
     * Method that handle the Use button.
     */
    @FXML
    public final void handleUseButton() {
        final Item selection = table.getSelectionModel().getSelectedItem();
        if (this.useButton.getText().equals(USE_BUTTON_LABEL)) {
            this.getController().useItem(selection);
        } else if (this.useButton.getText().equals(EQUIP_BUTTON_LABEL)) {
            if (!this.getController().equipItem(selection)) {
               System.out.println(this.getDialogFactory().createErrorDialog(ERROR, EQUIP_ERROR_DESCRIPTION).toString());
            }
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
        table.getItems().setAll(this.getController().getPlayerInformation().getAllItemsList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        column.setResizable(true);
        column.setSortable(false);
        column.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getName() + addTag(cellData.getValue())));
        showItemDetails(null);
        table.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showItemDetails(newValue));
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
            if (item.isEquipable()) {
                this.content.setText(item.getName() + " is an Equipable Item"
                                + (this.getController().getPlayerInformation().isEquipped(item)
                                        ? " [Currently Equipped]"
                                        : ".\nYou have " + this.getController().getPlayerInformation()
                                                .getInventoryItemQuantity(item) + " of them")
                                + ".\n\nDescription: \n" + item.getDescription() + "\n\nEffects: \n"
                                + item.getEffectDescription());
            } else {
                this.content.setText(item.getName() + ": an Usable Item.\nYou have "
                        + this.getController().getPlayerInformation().getInventoryItemQuantity(item)
                        + " of them.\n\nDescription: \n" + item.getDescription() + "\n\nEffects: \n"
                        + item.getEffectDescription());
            }
        } else {
            this.content.setText("Select an Item first.");
        }
        setButtonsVisibility(item);
    }

    private void setButtonsVisibility(final Item item) {
        this.backButton.setVisible(this.getController().isCombatActive());
        if (item != null) {
            this.useButton.setVisible(true);
            this.deleteButton.setVisible(true);
            if (this.getController().getPlayerInformation().isEquipped(item)) {
                this.useButton.setText(UNEQUIP_BUTTON_LABEL);
                this.useButton.setDisable(this.getController().isCombatActive());
                this.deleteButton.setDisable(true);
            } else {
                if (item.isUsable()) {
                    UsableItem usable = (UsableItem) item;
                    this.useButton.setText(USE_BUTTON_LABEL);
                    this.useButton.setDisable(!((usable.isUsableInCombat() && this.getController().isCombatActive())
                            || (usable.isUsableOutOfCombat() && !this.getController().isCombatActive())));
                } else {
                    this.useButton.setDisable(this.getController().isCombatActive());
                    this.useButton.setText(EQUIP_BUTTON_LABEL);
                }
                this.deleteButton.setDisable(this.getController().isCombatActive());
            }
        } else {
            this.useButton.setVisible(false);
            this.deleteButton.setVisible(false);
        }
    }

    private String addTag(final Item item) {
        if (this.getController().getPlayerInformation().isEquipped(item)) {
            return " (E)";
        } else {
            return "";
        }
    }
}
