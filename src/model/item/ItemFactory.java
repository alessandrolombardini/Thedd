package model.item;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import model.characters.Statistic;

/**
 * 
 *
 */
public final class ItemFactory {

    private static List<Item> database = new ArrayList<>();

    static {
        initDatabase();
        database.stream().forEach(i -> System.out.println(i));
    }

    private ItemFactory() {
    }

    /**
     *  Load the JSON database file and populate the database with objects. 
     */
    public static void initDatabase() {
        try (BufferedReader br = new BufferedReader(new FileReader("res/items.json"))) {
            final String json = br.lines().collect(Collectors.joining());
            final JSONObject jo = new JSONObject(json);
            jo.keySet().stream().forEach(k -> {
                final int itemId = Integer.parseInt(k);
                final String itemName = jo.getJSONObject(k).getString("name");
                final Map<Statistic, Integer> effects = new HashMap<>();
                final JSONArray mods = jo.getJSONObject(k).getJSONArray("effects");
                for (int i = 0; i < mods.length(); i++) {
                    final int index = i;
                    mods.getJSONObject(i).keySet().forEach(k2 -> effects.put(Statistic.valueOf(k2), Integer.parseInt(mods.getJSONObject(index).getString(k2))));
                }
                if (itemId < 0) {
                    database.add(new AbstractEquipableItem(itemId, itemName, effects) { });
                } else {
                    database.add(new AbstractItem(itemId, itemName, effects) { });
                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @return
     *          a random item from the database
     */
    public static Item getRandomItem() {
        return database.get(new Random().nextInt(database.size()));
    }
}
