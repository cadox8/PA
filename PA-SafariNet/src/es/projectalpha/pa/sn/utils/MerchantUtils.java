package es.projectalpha.pa.sn.utils;

import org.bukkit.inventory.MerchantRecipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MerchantUtils {

    public static List<MerchantRecipe> stringToRecipe(String s){
        List<MerchantRecipe> recipes = new ArrayList<>();
        List<String> recipe = Arrays.asList(s.replace("[", "").replace("]", "").split(","));

        return recipes;
    }
}
