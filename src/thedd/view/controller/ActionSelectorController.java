package thedd.view.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import thedd.model.combat.action.Action;
import thedd.utils.observer.Observer;
import thedd.view.actionselector.Command;
import thedd.view.actionselector.DescriptionPane;
import thedd.view.actionselector.IconsPane;
import thedd.view.actionselector.VisualAction;
import thedd.view.actionselector.VisualCategory;

/**
 * The controller of the ActionSelector super component.
 */
public class ActionSelectorController extends ViewNodeControllerImpl implements Observer<Command> {

    @FXML
    private GridPane grid;

    private static final double ICONSPANE_WIDTH_PERCENTAGE = 20;
    private static final double DESCRIPTIONPANE_WIDTH_PERCENTAGE = 100 - ICONSPANE_WIDTH_PERCENTAGE;
    private final IconsPane iconsPane;
    private final DescriptionPane descriptionPane;
    private final List<VisualCategory> categories = new ArrayList<>();
    private int selectedCategoryIndex;
    private boolean categorySelected;
    private boolean actionSelected;

    /**
     * Public constructor.
     */
    public ActionSelectorController() {
        super();
        selectedCategoryIndex = 0;
        categorySelected = false;
        actionSelected = false;
        iconsPane = new IconsPane();
        descriptionPane = new DescriptionPane();
    }

    /**
     * Sets the {@link Action}s to be displayed.
     * @param items the actions to be displayed
     */
    public void passItems(final List<Action> items) {
        fillLists(items);
        final VisualCategory inventoryCategory = new VisualCategory("Inventory", Collections.emptyList());
        categories.add(inventoryCategory);
        descriptionPane.showCategory(categories.get(0), 0, categories.size());
        iconsPane.passItems(categories.stream().map(c -> c.getImage()).collect(Collectors.toList()));
    }

    /**
     * Updates the {@link DescriptionPane} component.
     */
    public void updateDescription() {
        if (!categorySelected) {
            descriptionPane.showCategory(categories.get(iconsPane.getSelectedIndex()), iconsPane.getSelectedIndex(), iconsPane.getItemsCount());
        } else {
            final VisualAction action = categories.get(selectedCategoryIndex)
                                                  .getActions()
                                                  .get(iconsPane.getSelectedIndex());
            descriptionPane.showAction(action, iconsPane.getSelectedIndex(), iconsPane.getItemsCount());
        }
    }

    private void fillLists(final List<Action> actions) {
        actions.stream()
               .map(a -> a.getCategory())
               .distinct()
               .forEach(c -> {
                   final List<VisualAction> actionsByCategory = actions.stream()
                                                    .filter(a -> a.getCategory() == c)
                                                    .map(a -> new VisualAction(a))
                                                    .collect(Collectors.toList());
                   final String categoryName = c.name().toLowerCase(Locale.ENGLISH);
                   categories.add(new VisualCategory(categoryName, actionsByCategory));
               });
    }

    @Override
    public void update() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void initView() {
        final ColumnConstraints constr1 = new ColumnConstraints();
        final ColumnConstraints constr2 = new ColumnConstraints();
        final RowConstraints constr3 = new RowConstraints();
        constr1.setPercentWidth(ICONSPANE_WIDTH_PERCENTAGE);
        constr2.setPercentWidth(DESCRIPTIONPANE_WIDTH_PERCENTAGE);
        constr3.setPercentHeight(100);

        grid.getColumnConstraints().addAll(constr1, constr2);
        grid.getRowConstraints().add(constr3);

        grid.getChildren().addAll(iconsPane, descriptionPane);
        GridPane.setColumnIndex(iconsPane, 0);
        GridPane.setRowIndex(iconsPane, 0);
        GridPane.setColumnIndex(descriptionPane, 1);
        GridPane.setRowIndex(descriptionPane, 0);
        descriptionPane.bindObserver(this);
        iconsPane.bindObserver(this);
        passItems(getController().getPlayerInformation().getPlayerActions());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void trigger(final Optional<Command> message) {
        switch (message.get()) {
        case NEXT:
            iconsPane.scrollDown();
            updateSelectButton();
            break;
        case PREVIOUS:
            iconsPane.scrollUp();
            updateSelectButton();
            break;
        case RETURN:
            undoSelection();
            break;
        case SELECT:
            selectItem();
            if (!actionSelected && selectedCategoryIndex != categories.size() - 1) {
                updateSelectButton();
            }
            break;
        case UPDATE:
            updateDescription();
            break;
        default:
            break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void disableInteraction() {
        descriptionPane.setUndoDisable(true);
    }

    private void selectItem() {
        if (!categorySelected) {
            categorySelected = true;
            selectedCategoryIndex = iconsPane.getSelectedIndex();
            if (selectedCategoryIndex >= categories.size() - 1) {
                getView().showInventory();
                return;
            } else {
                iconsPane.passItems(categories.get(selectedCategoryIndex).getActions().stream()
                        .map(c -> c.getImage()).collect(Collectors.toList()));
                updateDescription();
            }
        } else {
            this.getView().hideMessage();
            actionSelected = true;
            iconsPane.setDisable(true);
            descriptionPane.setSelectionAndMovement(true);
            final int selectedActionIndex = iconsPane.getSelectedIndex(); 
            final Action selectedAction = categories.get(selectedCategoryIndex)
                                                    .getActions()
                                                    .get(selectedActionIndex)
                                                    .getAction();
            getController().selectAction(selectedAction);
        }
    }

    private void undoSelection() {
        if (actionSelected) {
            actionSelected = false;
            iconsPane.setDisable(false);
            descriptionPane.setSelectionAndMovement(false);
            getController().undoActionSelection();
        } else {
            iconsPane.passItems(categories.stream()
                    .map(c -> c.getImage()).collect(Collectors.toList()));
            categorySelected = false;
        }
        updateDescription();
        updateSelectButton();
    }

    private void updateSelectButton() {
        if (categorySelected) {
            final VisualAction selectedAction = categories.get(selectedCategoryIndex)
                                                          .getActions()
                                                          .get(iconsPane.getSelectedIndex());
            descriptionPane.setSelectDisable(!selectedAction.canSelect());
            iconsPane.getChildren().get(iconsPane.getSelectedIndex()).setDisable(!selectedAction.canSelect());
        } else {
            descriptionPane.setSelectDisable(false);
            iconsPane.getChildren().get(iconsPane.getSelectedIndex()).setDisable(false);
        }
    }

}
