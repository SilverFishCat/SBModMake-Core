//The MIT License (MIT)
//
//Copyright (c) 2015 , SilverFishCat@GitHub
//
//Permission is hereby granted, free of charge, to any person obtaining a copy
//of this software and associated documentation files (the "Software"), to deal
//in the Software without restriction, including without limitation the rights
//to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
//copies of the Software, and to permit persons to whom the Software is
//furnished to do so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
//SOFTWARE.

package silver.starbound.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * A mod info object that represents the mod info file.
 * 
 * @author SilverFishCat
 *
 */
public class ModInfo {
	@SerializedName("name")		private String mModName;
	@SerializedName("requires")	private Collection<String> mRequires;
	@SerializedName("includes")	private Collection<String> mIncludes;
	
	/**
	 * Create a blank mod info object.
	 */
	public ModInfo(){
		this(null, null, null);
	}
	/**
	 * Create a mod info with given values. 
	 * 
	 * @param modName The name of the mod
	 * @param requires A collection of of mod names to be used as requires
	 * @param includes A collection of of mod names to be used as includes
	 */
	public ModInfo(String modName, List<String> requires, List<String> includes){
		setModName(modName);
		setRequires(requires);
		setIncludes(includes);
	}

	/**
	 * Get the name of the mod.
	 * 
	 * @return The name of the mod
	 */
	public String getModName(){
		return mModName;
	}
	/**
	 * Get the names of the mods this mod requires.
	 * 
	 * @return The names of the mods this mod requires
	 */
	public Collection<String> getRequires() {
		return mRequires;
	}
	/**
	 * Get the names of the mods this mod includes.
	 * 
	 * @return The names of the mods this mod includes
	 */
	public Collection<String> getIncludes() {
		return mIncludes;
	}
	
	/**
	 * Set the name of the mod.
	 * 
	 * @param modName The new mod name
	 */
	public void setModName(String modName){
		if(modName == null)
			modName = "";
		mModName = modName;
	}
	/**
	 * Set the names of the mods this mod requires.
	 * 
	 * @param requires The requires to set
	 */
	public void setRequires(Collection<String> requires) {
		if(requires == null)
			requires = new ArrayList<>();
		this.mRequires = requires;
	}
	/**
	 * Set the names of the mods this mod includes.
	 * 
	 * @param includes The includes to set
	 */
	public void setIncludes(Collection<String> includes) {
		if(includes == null)
			includes = new ArrayList<>();
		this.mIncludes = includes;
	}
}
