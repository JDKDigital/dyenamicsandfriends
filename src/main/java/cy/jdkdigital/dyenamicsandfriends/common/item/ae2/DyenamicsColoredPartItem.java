package cy.jdkdigital.dyenamicsandfriends.common.item.ae2;

import appeng.api.parts.IPart;
import appeng.api.util.AEColor;
import appeng.items.parts.ColoredPartItem;
import cy.jdkdigital.dyenamics.core.util.DyenamicDyeColor;

import java.util.function.Function;

public class DyenamicsColoredPartItem extends ColoredPartItem<IPart>
{
    public final DyenamicDyeColor color;

    public DyenamicsColoredPartItem(Properties properties, Class partClass, Function factory, DyenamicDyeColor color) {
        super(properties, partClass, factory, AEColor.fromDye(color.getAnalogue()));
        this.color = color;
    }
}
