package silver.starbound.data;

import io.gsonfire.PostProcessor;
import io.gsonfire.annotations.ExposeMethodResult;
import io.gsonfire.annotations.ExposeMethodResult.ConflictResolutionStrategy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import silver.starbound.util.JsonUtil;
import silver.starbound.util.PathUtil;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

/**
 * A starbound item.
 * 
 * @author SilverFishCat
 *
 */
public class Item extends StarboundObject {
	/**
	 * A rarity value for starbound items.
	 * 
	 * @author SilverFishCat
	 *
	 */
	@JsonAdapter(Rarity.RarityJsonAdapter.class)
	public enum Rarity{
		/**
		 * Purple legendary rarity.
		 */
		LEGENDARY,
		/**
		 * Blue rare rarity.
		 */
		RARE,
		/**
		 * Green uncommon rarity.
		 */
		UNCOMMON,
		/**
		 * Gray common rarity.
		 */
		COMMON;
		
		/**
		 * A json adapter to convert enum values into proper
		 * json strings.
		 * 
		 * @author SilverFishCat
		 *
		 */
		public static class RarityJsonAdapter extends TypeAdapter<Rarity>{
			@Override
			public void write(JsonWriter out, Rarity value) throws IOException {
				if(value == null)
					out.nullValue();
				else{
					String name = value.name();
					out.value(name.toLowerCase());
				}
			}

			@Override
			public Rarity read(JsonReader in) throws IOException {
				if(in.peek() == JsonToken.STRING){
					String stringValue = in.nextString().toUpperCase();
					return Rarity.valueOf(stringValue);
				}
				else{
					return null;
				}
			}
		}
	}
	
	@SerializedName("itemName")					private String _itemName;
	@SerializedName("rarity")					private Rarity _rarity;
												private transient File _inventoryIconFile;
												private transient String _inventoryIconFileName;
	@SerializedName("description")				private String _description;
	@SerializedName("shortDescription")			private String _shortDescription;
	@SerializedName("learnBlueprintsOnPickup")	private Collection<String> _blueprintsLearnedOnPickup;

	/**
	 * Create a blank starbound item.
	 */
	public Item() {
		this(null, "", Rarity.COMMON, null, "", "", null);
	}
	/**
	 * Create a plain starbound item.
	 * 
	 * @param file The file associated with this item
	 * @param itemName The name of the item 
	 * @param rarity The rarity of the item
	 * @param inventoryIconFile The inventory icon file of this item
	 * @param description The description of this item
	 * @param shortDescription The short description of this item
	 * @param blueprintsLearnedOnPickup The blueprints learned on pickup
	 */
	public Item(File file, String itemName, Rarity rarity, File inventoryIconFile,
			String description, String shortDescription, Collection<String> blueprintsLearnedOnPickup) {
		super(file);
		
		setItemName(itemName);
		setRarity(rarity);
		setInventoryIconFile(inventoryIconFile);
		setDescription(description);
		setShortDescription(shortDescription);
		setBlueprintsLearnedOnPickup(blueprintsLearnedOnPickup);
	}

	/**
	 * Get the name of the item.
	 * 
	 * @return The name of the item
	 */
	public String getItemName() {
		return _itemName;
	}
	/**
	 * Get the rarity value of this item.
	 * 
	 * @return The rarity value of this item
	 */
	public Rarity getRarity() {
		return _rarity;
	}
	/**
	 * Get the inventory icon file.
	 * 
	 * @return The inventory icon file
	 */
	public File getInventoryIconFile() {
		return _inventoryIconFile;
	}
	/**
	 * Get the description of the item.
	 * The descrption is the flavor text for the item.
	 * 
	 * @return The description of the item
	 */
	public String getDescription() {
		return _description;
	}
	/**
	 * Get the item's short description.
	 * The item's short description is the subtitle shown
	 * underneath the item name.
	 * 
	 * @return The item's short description
	 */
	public String getShortDescription() {
		return _shortDescription;
	}
	/**
	 * Get the blueprints that are learned when this item is picked up.
	 * 
	 * @return The blueprints learned when the item is picked up
	 */
	public Collection<String> getBlueprintsLearnedOnPickup(){
		return _blueprintsLearnedOnPickup;
	}
	/**
	 * Get the relative path to the icon file.
	 * 
	 * @return The relative path to the icon file
	 */
	@ExposeMethodResult(conflictResolution = ConflictResolutionStrategy.OVERWRITE, value = "inventoryIcon")
	public String getInventoryIcon(){
		String iconFileName;
		if(getFile() != null && getInventoryIconFile() != null)
			iconFileName = PathUtil.getRelativePath(getFile(), getInventoryIconFile());
		else
			iconFileName = _inventoryIconFileName;
		
		if(iconFileName != null){
			// Starbound does not allow .. path operators
			if(iconFileName.contains("..") && getInventoryIconFile() != null)
				iconFileName = getInventoryIconFile().getAbsolutePath();
			
			// The character \ is invalid as far as starbound cares
			iconFileName = iconFileName.replace("\\", "/");
		}
		
		return iconFileName;
	}

	@Override
	public void setFile(File file) {
		boolean setInventoryName = getInventoryIconFile() == null;
		String inventoryIconFileName = getInventoryIcon();
		
		super.setFile(file);
		
		if(setInventoryName)
			setInventoryIcon(inventoryIconFileName);
	}
	/**
	 * Set the name of the item.
	 * 
	 * @param itemName The name of the item
	 */
	public void setItemName(String itemName) {
		this._itemName = itemName;
	}
	/**
	 * Set the rarity value of this item.
	 * 
	 * @param rarity The rarity value of this item
	 */
	public void setRarity(Rarity rarity) {
		this._rarity = rarity;
	}
	/**
	 * Set the inventory icon file.
	 * 
	 * @param inventoryIconFile The inventory icon file
	 */
	public void setInventoryIconFile(File inventoryIconFile) {
		this._inventoryIconFile = inventoryIconFile;
	}
	/**
	 * Set the description of the item.
	 * The descrption is the flavor text for the item.
	 * 
	 * @param description The description of the item
	 */
	public void setDescription(String description) {
		this._description = description;
	}
	/**
	 * Set the item's short description.
	 * The item's short description is the subtitle shown
	 * underneath the item name.
	 * 
	 * @param shortDescription The item's short description
	 */
	public void setShortDescription(String _shortDescription) {
		this._shortDescription = _shortDescription;
	}
	/**
	 * Set the blueprints that are learned when this item is picked up.
	 * 
	 * @param blueprintsLearnedOnPickup The blueprints learned when the item is picked up
	 */
	public void setBlueprintsLearnedOnPickup(Collection<String> blueprintsLearnedOnPickup){
		if(blueprintsLearnedOnPickup == null)
			blueprintsLearnedOnPickup = new ArrayList<String>();
		this._blueprintsLearnedOnPickup = blueprintsLearnedOnPickup;
	}
	/**
	 * Set the relative path to the icon file.
	 * 
	 * @param inventoryIcon The relative path to the icon file
	 */
	public void setInventoryIcon(String inventoryIcon){
		File file = getFile();
		if(file != null){
			if(!file.isDirectory())
				file = file.getParentFile();
			setInventoryIconFile(new File(file, inventoryIcon));
		}
		else{
			_inventoryIconFileName = inventoryIcon;
		}
	}

	/**
	 * A convience method for loading an item from file.
	 * 
	 * @param file The file where the item is stored
	 * @throws IOException If there was an error in the file
	 */
	public static Item loadFromFile(File file) throws IOException{
		if(file == null)
			throw new NullPointerException("File is null");
		
		if(!file.isFile())
			throw new IllegalArgumentException("Given path is not a file");
		
		try {
			Item result = JsonUtil.getGsonInstance().fromJson(new FileReader(file), Item.class);
			result.setFile(file);
			return result;
		} catch (JsonSyntaxException | JsonIOException | FileNotFoundException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
	}
	
	/**
	 * A json item post processor to expose set inventory icon.
	 * 
	 * @author SilverFishCat
	 *
	 */
	public static class ItemPostProcessor implements PostProcessor<Item>{
		@Override
		public void postDeserialize(Item result, JsonElement src, Gson gson) {
			JsonObject srcObject = src.getAsJsonObject();
			if(srcObject.has("inventoryIcon")){
				result.setInventoryIcon(srcObject.get("inventoryIcon").getAsString());
			}
		}

		@Override
		public void postSerialize(JsonElement result, Item src, Gson gson) { }
	}
}
