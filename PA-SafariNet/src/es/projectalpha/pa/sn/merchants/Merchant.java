package es.projectalpha.pa.sn.merchants;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.inventory.ItemStack;

public class Merchant {

    @Getter @Setter private ItemStack needed;
    @Getter @Setter private ItemStack other;
    @Getter @Setter private ItemStack result;

    public Merchant(ItemStack needed, ItemStack other, ItemStack result) {
        setNeeded(needed);
        if (other == null) setOther(other);
        setResult(result);
    }

    @Override
    public String toString() {
        return needed.serialize().toString() + "#" + (other != null ? other.serialize().toString() : "") + "#" + result.serialize().toString();
    }

    public static Merchant getMerchant(String serialized) {
        String[] g = serialized.split("#");
        ItemStack i;
        ItemStack i2;
        ItemStack i3;

/*        for (String s : g) {
            i = ItemStack.deserialize(s);
        }

        return new Merchant(i, i2, i3);*/

        return null;
    }
}
