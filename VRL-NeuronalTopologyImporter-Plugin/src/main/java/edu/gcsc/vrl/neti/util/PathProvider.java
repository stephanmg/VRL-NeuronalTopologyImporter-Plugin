/// package's name
package edu.gcsc.vrl.neti.util;

/// imports
import eu.mihosoft.vrl.system.VMessage;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/**
 * @brief abstract PathProvider implementation
 * We guess the plugin location if not specified.
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public abstract class PathProvider implements IPathProvider {
	/// public non-final static members
	@SuppressWarnings("PublicField")
	public static String RESOURCE_FOLDER = "resources";
	@SuppressWarnings("PublicField")
	public static String PLUGIN_NAME;
	@SuppressWarnings("PublicField")
	public static File PATH_TO_PLUGIN;

	/**
	 * @brief get a resource file
	 * @param fName
	 * @return
	 */
	@Override
	public File getResourceFile(String fName) {
		/// in case we run as a console app
		if (PATH_TO_PLUGIN == null) {
			File f = new File("property-folder/plugins");
			String plugin_name = PLUGIN_NAME;
			ArrayList<Class<?>> clazzes = ClassFinder.getClassesFromJar(f);

			for (Class<?> clazz : clazzes) {
				System.err.println("class: " + clazz);

				if (clazz.isInterface()) {
					continue;
				}
				
				if (clazz.getSuperclass().equals(VPluginConfigurator.class)) {
					String complete_name = clazz.getName();
					String[] splitted = complete_name.split("\\.");
					plugin_name = splitted[splitted.length - 1];
					plugin_name = plugin_name.replace("PluginConfigurator", "");
				}
				System.err.println("right after class!");
			}

			System.err.println("we are here!");
			PATH_TO_PLUGIN = new File("dummy");
			String[] s = PATH_TO_PLUGIN.getAbsolutePath().split(File.separator);
			File f1 = new File(new File(StringUtils.join(Arrays.copyOf(s, s.length - 1),
				File.separator)).getAbsolutePath()
				+ File.separator + "property-folder" + File.separator + "plugins"
				+ File.separator + "VRL-" + plugin_name + "-Plugin"
				+ File.separator + RESOURCE_FOLDER + File.separator + fName);

			if (!f1.exists()) {
				System.err.println("File not found: " + f1.getAbsolutePath());
			} else {
				System.err.println(f1.getAbsolutePath());
				System.err.println(f1.getName());
				return f1;
			}

			System.err.println("and now here!");
				System.err.println(f1.getAbsolutePath());
				System.err.println(f1.getName());
			f1 = new File(new File(StringUtils.join(Arrays.copyOf(s, s.length - 1),
				File.separator)).getAbsolutePath()
				+ File.separator + "property-folder" + File.separator + "plugins"
				+ File.separator + "VRL-" + plugin_name
				+ File.separator + RESOURCE_FOLDER + File.separator + fName);

			if (!f1.exists()) {
				System.err.println("File also not found: " + f1.getAbsolutePath());
				System.err.println("Is you plugin registered with a cryptic name?");
			} else {
				return f1;
			}

			f1 = new File(new File(StringUtils.join(Arrays.copyOf(s, s.length - 1),
				File.separator)).getAbsolutePath()
				+ File.separator + "property-folder" + File.separator + "plugins"
				+ File.separator + PLUGIN_NAME
				+ File.separator + RESOURCE_FOLDER + File.separator + fName);

			if (!f1.exists()) {
				System.err.println("File also not found... plugin installed? " + f1.getAbsolutePath());
				return null;
			} else {
				return f1;
			}

			/// in case we run as a GUI app
		} else {
			File f2 = new File(PATH_TO_PLUGIN.getAbsolutePath()
				+ File.separator + RESOURCE_FOLDER + File.separator, fName);

			if (!f2.exists()) {
				VMessage.exception("File not found",
					"The file '" + PATH_TO_PLUGIN.getAbsolutePath()
					+ File.separator + RESOURCE_FOLDER + File.separator
					+ fName + " can not be found.");
				return null;
			} else {
				return f2;
			}
		}
	}

	/**
	 * @brief get's the path to the plugin
	 * @return
	 */
	public static File get_path_to_plugin() {
		return PATH_TO_PLUGIN;
	}

	/**
	 * @brief set's the path to the plugin
	 * @param f
	 */
	public static void set_path_to_plugin(File f) {
		String[] s = f.getAbsolutePath().split(File.separator);
		PATH_TO_PLUGIN = new File(StringUtils.join(Arrays.copyOf(s, s.length - 1), File.separator));
	}
	
	/**
	 * @brief set's a resource file 
	 * @todo implement
	 * @param rsc
	 * @return
	 */
	@Override
	public File setResourceFile(String rsc) {
		return new File("dummy");
	}

}
