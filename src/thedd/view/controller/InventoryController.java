package thedd.view.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import thedd.model.item.Item;
import thedd.model.item.equipableitem.EquipableItem;
import thedd.model.item.equipableitem.EquipableItemImpl;
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
    private static final String CANCEL_LABEL = "Cancel";

    /**
     * Method that handle the Use button.
     */
    @FXML
    public final void handleUseButton() {
        final Item selection = table.getSelectionModel().getSelectedItem();
        if (this.useButton.getText().equals(USE_BUTTON_LABEL)) {
            this.getController().useItem(selection);
            this.useButton.setText(CANCEL_LABEL);
            this.backButton.setDisable(true);
            this.deleteButton.setDisable(true);
            this.table.setDisable(true);
        } else if (this.useButton.getText().equals(EQUIP_BUTTON_LABEL)) {
            final EquipableItem selected = new EquipableItemImpl(selection.getId(), selection.getBaseName(),
                    ((EquipableItem) selection).getType(), selection.getRarity(), selection.getDescription());
            ((EquipableItem) selection).getActionEffects().forEach(ae -> selected.addActionEffect(ae));
            ((EquipableItem) selection).getAdditionalActions().forEach(a -> selected.addAdditionalAction(a));
            this.getController().equipItem(selected);
        } else if (this.useButton.getText().equals(CANCEL_LABEL)) {
            this.getController().undoActionSelection();
            this.useButton.setText(USE_BUTTON_LABEL);
            this.deleteButton.setDisable(false);
            this.backButton.setDisable(false);
            this.table.setDisable(false);
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
        this.getView().showActionSelector();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update() {
        table.getItems().setAll(this.getController().getPlayerInformation().getAllItemsList());
        if (!this.getController().isCombatActive()) {
            table.setDisable(false);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableInteraction() {
        this.table.setDisable(true);
        this.backButton.setDisable(true);
        this.deleteButton.setDisable(true);
        this.useButton.setDisable(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        table.setPlaceholder(new Label("Empty"));
        column.setResizable(true);
        column.setSortable(false);
        column.setCellValueFactory(
                cellData -> new SimpleStringProperty(addTag(cellData.getValue()) + cellData.getValue().getName()));
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
                this.content.setText(item.getName()
                        + (this.getController().getPlayerInformation().isEquipped(item) ? " [Currently Equipped]" : "")
                        + (!this.getController().getPlayerInformation().isItemEquipableOnEquipment(item)
                                && !this.getController().getPlayerInformation().isEquipped(item)
                                        ? "\nYou cannot equip this item: maximum slot limit reached!"
                                        : "")
                        + "\n\nDescription: \n" + item.getDescription() + "\n\nEffects: \n"
                        + item.getEffectDescription() + "\nType: Equipable Item."
                        + (!this.getController().getPlayerInformation().isEquipped(item) ? "\n\nYou have "
                                + this.getController().getPlayerInformation().getInventoryItemQuantity(item)
                                + " of them in your inventory." : ""));
            } else {
                this.content.setText(item.getName() + "\n\nDescription: \n" + item.getDescription() + "\n\nEffects: \n"
                        + item.getEffectDescription() + "\n\nType: Usable Item. \n\nYou have "
                        + this.getController().getPlayerInformation().getInventoryItemQuantity(item)
                        + " of them in your inventory.");
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
                    final UsableItem usable = (UsableItem) item;
                    this.useButton.setText(USE_BUTTON_LABEL);
                    this.useButton.setDisable(!((usable.isUsableInCombat() && this.getController().isCombatActive())
                            || (usable.isUsableOutOfCombat() && !this.getController().isCombatActive())));
                } else {
                    this.useButton.setDisable(this.getController().isCombatActive()
                            || !this.getController().getPlayerInformation().isItemEquipableOnEquipment(item));
                    this.useButton.setText(EQUIP_BUTTON_LABEL);
                }
                this.deleteButton.setDisable(false);
            }
        } else {
            this.useButton.setVisible(false);
            this.deleteButton.setVisible(false);
        }
    }

    private String addTag(final Item item) {
        if (this.getController().getPlayerInformation().isEquipped(item)) {
            return "(E) ";
        } else {
            return "";
        }
    }
}
