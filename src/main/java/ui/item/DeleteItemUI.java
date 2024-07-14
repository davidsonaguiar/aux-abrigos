package ui.item;

import com.compass.item.ItemController;
import ui.Component;

public class DeleteItemUI {
    private final ItemController itemController;
    private final Component component;

    public DeleteItemUI(ItemController itemController, Component component) {
        this.itemController = itemController;
        this.component = component;
    }

    public void execute() {}
}
