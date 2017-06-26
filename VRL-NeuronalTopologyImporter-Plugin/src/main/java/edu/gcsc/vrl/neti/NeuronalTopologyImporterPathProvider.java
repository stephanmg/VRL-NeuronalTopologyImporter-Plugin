/// package's name
package edu.gcsc.vrl.neti;

/// imports
import java.io.File;
import java.io.Serializable;
import edu.gcsc.vrl.neti.util.PathProvider;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;

/**
 * @brief NeuronalTopologyImporter path provider component
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "NeuronalTopologyImporterPathProvider", category = "/UG4/VRL-Plugins/Neuro/NeuronalTopologyImporter")
public class NeuronalTopologyImporterPathProvider extends PathProvider implements Serializable {
	/// private static final members
	private static final long serialVersionUID = 1L;

	/**
	 * @brief override plugin name and resource folder 
	 * Note: This is not required but convenient 
	 */
	@SuppressWarnings("static-access")
	public NeuronalTopologyImporterPathProvider() {
		super.PLUGIN_NAME = "NeuronalTopologyImporter";
		super.RESOURCE_FOLDER = "resources";
	}
	
	/**
	 * @brief get a resource file
	 * @param fName file name for the resource
	 * @return the file for the resources
	 * @see File
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
	 * @param fName file name for the resource
	 * @return the file for the resource
	 * @see File
	 */
	@MethodInfo(name = "set resource file", valueName = "file")
	@Override
	public File setResourceFile(
    		@ParamInfo(name = "file name")
		String fName) {
		return super.setResourceFile(fName);
	}
}