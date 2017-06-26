/// package's name
package edu.gcsc.vrl.neti;

/// imports
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;
import eu.mihosoft.vrl.io.IOUtil;
import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.*;

/**
 * @brief NeuronalTopologyImporter plugin configurator
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class NeuronalTopologyImporterPluginConfigurator extends VPluginConfigurator {
	/// project templates (default and resource)
	private File templateProjectSrc;
	private File templateProjectSrcWithResource;
	private final String templateProjectName = "neti_template.vrlp";
	private final String templateProjectNameWithResource = "neti_template_resource.vrlp";
	private final String templateDataName = "template.ngx";

	/**
	 * @brief ctor
	 */
	public NeuronalTopologyImporterPluginConfigurator() {
		// specify the plugin name and version
		setIdentifier(new PluginIdentifier("VRL-NeuronalTopologyImporter-Plugin", "0.1.0"));

		// export some packages
		exportPackage("edu.gcsc.vrl.neti");

		// describe the plugin
		setDescription("Converts neuronal morphologies in the HOC, SWC and NGX format to UGX format.");

		// get the license text
		String license_str = "";
		try {
			license_str = IOUtils.toString(new FileInputStream(new File(getClass().getClassLoader().getResource("/edu/gcsc/vrl/neti/lgpl-3.0.txt").getFile())));
		} catch (IOException ioe) {
			System.err.println(ioe);
		} catch (Exception e) {
			System.err.println(e);
		}

		// copyright info
		setCopyrightInfo("VRL-NeuronalTopologyImporter-Plugin",
			"(c) stephanmg <grein@temple.edu>",
			"www.neurobox.eu", "LGPLv3", license_str);

		// specify dependencies
		addDependency(new PluginDependency("VRL", "0.4.2", "0.4.2"));
		addDependency(new PluginDependency("VRL-UG", "0.2", "0.2"));
		addDependency(new PluginDependency("VRL-UG-API", "0.2", "0.2"));
	}

	/**
	 * @brief 
	 * @param api 
	 * @see PluginAPI
	 */
	@Override
	public void register(PluginAPI api) {
		// register plugin with canvas
		if (api instanceof VPluginAPI) {
			VPluginAPI vapi = (VPluginAPI) api;
			/// NeuronalTopologyImporterPathProvider.set_path_to_plugin(getInitAPI().getResourceFolder());

			vapi.addComponent(NeuronalTopologyImporter.class);
			vapi.addComponent(NeuronalTopologyImporterPathProvider.class);
			vapi.addComponentSearchFilter(new NeuronalTopologyImporterVFilter());
		}
	}

	/**
	 * @brief install plugins
	 * @param iApi
	 * @see InitPLuginAPI
	 */
	@Override
	public void install(InitPluginAPI iApi) {
		// ensure template projects are updated
		new File(iApi.getResourceFolder(), "neti_template.vrlp").delete();
		new File(iApi.getResourceFolder(), "neti_template_resource.vrlp").delete();
	}

	/**
	 * @brief
	 * @param api 
	 * @see PluginAPI
	 */
	@Override
	public void unregister(PluginAPI api) {
		// nothing to unregister
	}

	/**
	 * @brief 
	 * @param iApi 
	 * @see InitPluginAPI
	 */
	@Override
	public void init(InitPluginAPI iApi) {
		CompletionUtil.registerClassesFromJar(
			VJarUtil.getClassLocation(NeuronalTopologyImporterPluginConfigurator.class));

		NeuronalTopologyImporterPathProvider.set_path_to_plugin(getInitAPI().getResourceFolder());

		initTemplateProject(iApi);
		initTemplateProjectWithResource(iApi);
		initTemplateData(iApi);

	}

	/**
	 * @brief init template data
	 * @param iAPI
	 * @see InitPluginAPI
	 */
	private void initTemplateData(InitPluginAPI iAPI) {
		final File templateData = new File(iAPI.getResourceFolder(), templateDataName);
		if (!templateData.exists()) {
			try {
				InputStream in = NeuronalTopologyImporterPluginConfigurator.class.getResourceAsStream(
					File.separator + getClass().getPackage().getName().replace(".", File.separator) + File.separator + templateDataName);
				IOUtil.saveStreamToFile(in, templateData);
			} catch (FileNotFoundException ex) {
				Logger.getLogger(VRLPlugin.class.getName()).
					log(Level.SEVERE, null, ex);
			} catch (IOException ex) {
				Logger.getLogger(VRLPlugin.class.getName()).
					log(Level.SEVERE, null, ex);
			}
		}
	}

	/**
	 * @brief inits the template projects
	 * @param iApi
	 */
	private void initTemplateProject(InitPluginAPI iApi) {
		System.err.println(getClass().getPackage());
		templateProjectSrc = new File(iApi.getResourceFolder(), templateProjectName);

		if (!templateProjectSrc.exists()) {
			saveProjectTemplate();
		}

		iApi.addProjectTemplate(new ProjectTemplate() {
			@Override
			public String getName() {
				return "NeuronalTopologyImporter - Template #1 (Default)";
			}

			@Override
			public File getSource() {
				return templateProjectSrc;
			}

			@Override
			public String getDescription() {
				return "NeuronalTopologyImporter template for "
					+ "converting neuronal topologies from HOC, "
					+ "SWC and NGX to UGX";
			}

			@Override
			public BufferedImage getIcon() {
				return null;
			}
		});
	}

	/**
	 * @brief save the project default template 
	 */
	private void saveProjectTemplate() {
		try {
			InputStream in = NeuronalTopologyImporterPluginConfigurator.class.getResourceAsStream(File.separator + getClass().getPackage().getName().replace(".", File.separator) + File.separator + templateProjectName);
			IOUtil.saveStreamToFile(in, templateProjectSrc);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}
	}

	/**
	 * @brief init the with resource template project
	 * @param iApi
	 * @see InitPluginAPI
	 */
	private void initTemplateProjectWithResource(InitPluginAPI iApi) {
		templateProjectSrcWithResource = new File(iApi.getResourceFolder(), templateProjectNameWithResource);
		if (!templateProjectSrcWithResource.exists()) {
			saveProjectTemplateWithResource();
		}

		iApi.addProjectTemplate(new ProjectTemplate() {
			@Override
			public String getName() {
				return "NeuronalTopologyImporter - Template #2 (Resource)";
			}

			@Override
			public File getSource() {
				return templateProjectSrcWithResource;
			}

			@Override
			public String getDescription() {
				return "NeuronalTopologyImporter template for "
					+ "converting neuronal topologies from HOC, "
					+ "SWC and NGX to UGX resource example";
			}

			@Override
			public BufferedImage getIcon() {
				return null;
			}
		});
	}

	/**
	 * @brief save the with resource project template
	 */
	private void saveProjectTemplateWithResource() {
		try {
			InputStream in = NeuronalTopologyImporterPluginConfigurator.class.getResourceAsStream(File.separator + getClass().getPackage().getName().replace(".", File.separator) + File.separator + templateProjectNameWithResource);
			IOUtil.saveStreamToFile(in, templateProjectSrcWithResource);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}
	}
}
