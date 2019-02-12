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
        try (BufferedReader br = new BufferedReader(new FileReader("res/items.json"))) {
            String json = br.lines().collect(Collectors.joining());
            JSONObject jo = new JSONObject(json);
            jo.keySet().stream().forEach(k -> {
                int itemId = Integer.parseInt(k);
                String itemName = jo.getJSONObject(k).getString("name");
                Map<Statistic, Integer> effects = new HashMap<>();
                JSONArray mods = jo.getJSONObject(k).getJSONArray("effects");
                for (int i = 0; i < mods.length(); i++) {
                    final int index = i;
                    mods.getJSONObject(i).keySet().forEach(k2 -> effects.put(Statistic.valueOf(k2), Integer.parseInt(mods.getJSONObject(index).getString(k2))));
                }
                database.add(new AbstractItem(itemId, itemName, effects) {
                });
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.stream().forEach(i -> System.out.println(i));
    }

    private ItemFactory() {
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
