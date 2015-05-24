package silver.starbound.data;

import java.io.File;

/**
 * An object in the starbound game.
 * 
 * @author SilverFishCat
 *
 */
public abstract class StarboundObject {
	private transient File _file;

	/**
	 * Create a new starbound object with no file associated with it.
	 */
	protected StarboundObject() {
		this(null);
	}
	/**
	 * Create a new starbound for the associated file.
	 * 
	 * @param file The associated file
	 */
	protected StarboundObject(File file){
		setFile(file);
	}

	/**
	 * Get the file that holds this object.
	 * 
	 * @return The file that holds this object
	 */
	public File getFile(){
		return _file;
	}
	
	/**
	 * Set the file for this object.
	 * 
	 * @param file
	 */
	public void setFile(File file){
		_file = file;
	}
}
