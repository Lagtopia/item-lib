package me.csdad.lagtopia.itemlib.Items;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.csdad.lagtopia.corelib.Chat.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemFactory {

    private final Material material; // Material of the item
    private final int amount; // Amount of the item
    private String displayName; // Display name of the item (not colorized)
    private List<String> lore = new ArrayList<>(); // List of all strings in the item's lore
    private Map<String, String> nbtTags = new HashMap<>(); // Map of all nbt tags for the item
    private String id; // the declarative ID of the item

    // the actual item;
    private final ItemStack item;
    // meta for the item
    private final ItemMeta itemMeta;

    public ItemFactory(Material material, int amount, String id) {
        this.material = material;
        this.amount = amount;

        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();

        // init our nbtTags
        nbtTags = new HashMap<>();

        // declare the id
        this.id = id;
    }

    /**
     * Overload as a way to serialize an item in a player inventory BACK into a factory
     */
    public ItemFactory(ItemStack itemToDeserialize) {
        this.material = itemToDeserialize.getType();
        this.amount = itemToDeserialize.getAmount();
        this.displayName = itemToDeserialize.getItemMeta().getDisplayName();
        this.lore = itemToDeserialize.getItemMeta().getLore();

        // init the nbtTags
        nbtTags = new HashMap<>();

        // deserialize
        deserializeFromNBT(itemToDeserialize);

        this.id = this.getTag("id");


        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }


    /**
     * Method to set the item's lore
     * Strings do not have to be natively colorized.
     * @param lore A list of strings to set the lore to.
     */
    public void setLore(List<String> lore) {
        this.itemMeta.setLore(
                lore.stream().map(Color::translate).toList()
        );
        this.item.setItemMeta(itemMeta);


    }

    /**
     * Method to get the lore of the item
     * @return lore
     */
    public List<String> getLore() {
        return this.lore;
    }

    /**
     * Method to set the item's display name.
     * Strings do not have to be natively colorized.
     * @param displayName The display name to set.
     */
    public void setDisplayName(String displayName) {
        this.itemMeta.setDisplayName(Color.translate(displayName));
        this.item.setItemMeta(itemMeta);
    }

    /**
     * Method to get the item's display name.
     * The display name is in its raw, non-colorized form.
     * @return displayName
     */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
     * Method to add a tag to the NBT store.
     * @param key The key for the KVP.
     * @param value The value for the KVP.
     */
    public void addTag(String key, String value) {
        nbtTags.put(key, value);
    }

    /**
     * Method to query the NBT store.
     * Key is the string that corresponds to the value.
     * @param key The key to query for.
     * @return The value
     */
    public String getTag(String key) {
        return nbtTags.get(key);
    }

    /**
     * Method to get all de-serialized NBT tags
     * @return nbtTags
     */
    public Map<String, String> getAllTags() {
        return nbtTags;
    }

    /**
     * Method to remove an NBT tag from the store.
     * @param key The key of the tag to remove.
     */
    public void removeTag(String key) {
        nbtTags.remove(key);
    }

    /**
     * Method to build and bind NBT data to the item.
     * @return Built item
     */
    public ItemStack build() {

        ItemStack build = this.item;

        if(nbtTags.isEmpty()) return build; // this is meant to apply nbt tags to an item, no need to continue

        NBTItem nbti = new NBTItem(build);

        this.nbtTags.forEach(nbti::setString); // set the strings


        return nbti.getItem();

    }

    /* Private Utility Methods */

    /**
     * Method to deserialize NBT data from an ItemStack object
     * Deserialize data placed into nbtTags object.
     * @param source The itemstack
     */
    private void deserializeFromNBT(ItemStack source) {
        NBTItem item = new NBTItem(source); // get a new nbt item

        // map and loop
        item.getKeys().stream()
                .map(key -> Map.entry(key, item.getString(key))) // remap with kvp
                .filter(entry -> entry.getValue() != null) // prevent null kvps
                .forEach(entry -> this.nbtTags.put(entry.getKey(), entry.getValue())); // map the filtered data to our nbt tags.

    }


}
