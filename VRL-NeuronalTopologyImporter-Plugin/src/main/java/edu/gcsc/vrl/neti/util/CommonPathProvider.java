/// package's name
package edu.gcsc.vrl.neti.util;

/// imports
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;

/**
 * @brief a common path provider, may be used for different plugins with a shared resource folder
 * Otherwise, you need to subclass from PathProvider
 * @see NETIPathProvider for instance
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "CommonPathProvider", category = "/UG4/VRL-Plugins/Neuro/NeuronalTopologyImporter")
public class CommonPathProvider extends PathProvider {
	/**
	 * @brief may override plugin name and resource folder path
	 * @see Pathprovider
	 */
	public CommonPathProvider() {
		/// PLUGIN_NAME = "Foo";
		/// RESOURCE_FOLDER = "Bar";
	}
	
	/**
	 * @brief get a resource file
	 * @param fName
	 * @return 
	 */
	@MethodInfo(name = "get resource file", valueName = "file")
	@Override
	public File getResourceFile(
		@ParamInfo(name = "file name") 
			String fName) {
			return super.getResourceFile(fName);
	}

	/**
	 * @brief set a resource file
	 * @param fName
	 * @return 
	 */
	@MethodInfo(name = "set resource file", valueName = "file")
	@Override
	public File setResourceFile(
    		@ParamInfo(name = "file name")
		String fName) {
		return super.setResourceFile(fName);
	}
}
