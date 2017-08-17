package es.projectalpha.pa.core.logros;

import es.projectalpha.pa.core.utils.ItemUtils;
import lombok.Getter;
import org.bukkit.DyeColor;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class Logro {

    @Getter private int id;
    @Getter private String name;
    @Getter private String desc;

    public Logro(int id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    private List<String> formatLore() {
        String[] splited = desc.split(" ");
        int lin = splited.length / 6;
        String[] lines = new String[lin];

        for (int g = 1; g <= lin; g++) {
            for (int x = 0; x <= 6; x++) {
                lines[g] = lines[g] == null ? splited[x * g] + " " : lines[g] + splited[x * g] + " ";
            }
        }

        return Arrays.asList(lines);
    }

    public ItemStack generate() {
        return ItemUtils.coloredBlock(DyeColor.GREEN, getName(), ItemUtils.ColoredBlock.CLAY, formatLore());
    }

    @Override
    public String toString() {
        return "Logro {ID: " + id + ", Nombre: " + name + ", Desc: " + desc + "}";
    }
}
