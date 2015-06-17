/// package's name
package edu.gcsc.vrl.neti;

/// imports
import eu.mihosoft.vrl.io.IOUtil;
import eu.mihosoft.vrl.io.VJarUtil;
import eu.mihosoft.vrl.lang.visual.CompletionUtil;
import eu.mihosoft.vrl.system.InitPluginAPI;
import eu.mihosoft.vrl.system.PluginAPI;
import eu.mihosoft.vrl.system.PluginDependency;
import eu.mihosoft.vrl.system.PluginIdentifier;
import eu.mihosoft.vrl.system.ProjectTemplate;
import eu.mihosoft.vrl.system.VPluginAPI;
import eu.mihosoft.vrl.system.VPluginConfigurator;
import eu.mihosoft.vrl.system.VRLPlugin;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.IOUtils;

/**
 * @brief configurator for the NeuronalTopologyImporter VRL plugin
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class NeuronalTopologyImporterPluginConfigurator extends VPluginConfigurator {
	/// private members
	private File templateProjectSrc;

    public NeuronalTopologyImporterPluginConfigurator() {
        //specify the plugin name and version
       setIdentifier(new PluginIdentifier("VRL-NeuronalTopologyImporter-Plugin", "0.0.2"));
	
       // export some packages
       // exportPackage("com.your.package");

       // describe the plugin
       setDescription("Transforms neuronal topologies in the HOC, SWC and NGX format to UGX format.");

       // get the license text
       String license_str = "";
       try {
		license_str =  IOUtils.toString(new FileInputStream(new File(getClass().getClassLoader().getResource("/edu/gcsc/vrl/neti/lgpl-3.0.txt").getFile())));
       } catch (IOException ioe) {
	       System.err.println(ioe);
       } catch (Exception e) {
	       System.err.println(e);
       }
	       
       // copyright info
       setCopyrightInfo("VRL-NeuronalTopologyImporter-Plugin",
               "(c) stephanmg",
               "www.syntaktischer-zucker.de", "LGPLv3", license_str);
      
       // specify dependencies
       addDependency(new PluginDependency("VRL", "0.4.2", "0.4.2"));
       addDependency(new PluginDependency("VRL-UG", "0.2", "0.2"));
       addDependency(new PluginDependency("VRL-UG-API", "0.2", "0.2"));
    }
    
    @Override
    public void register(PluginAPI api) {

       // register plugin with canvas
       if (api instanceof VPluginAPI) {
           VPluginAPI vapi = (VPluginAPI) api;
           vapi.addComponent(NeuronalTopologyImporter.class);

       }
   }
    
   	 @Override
	/**
	 * @brief install plugins
	 * @param iApi
	 */
	public void install(InitPluginAPI iApi) {
		// ensure template projects are updated
		new File(iApi.getResourceFolder(), "neti_template.vrlp").delete();
	}


    @Override
   public void unregister(PluginAPI api) {
       // nothing to unregister
   }

    @Override
    public void init(InitPluginAPI iApi) {
	    CompletionUtil.registerClassesFromJar(
			VJarUtil.getClassLocation(NeuronalTopologyImporterPluginConfigurator.class));

	    initTemplateProject(iApi);
   }
    
    	/**
	 * @brief inits the template projects
	 * @param iApi
	 */
	private void initTemplateProject(InitPluginAPI iApi) {
		templateProjectSrc = new File(iApi.getResourceFolder(), "neti_template.vrlp");

		if (!templateProjectSrc.exists()) {
			saveProjectTemplate();
		}

		iApi.addProjectTemplate(new ProjectTemplate() {

			@Override
			public String getName() {
				return "NeuronalTopologyImporter - Template #1";
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
	 * @brief saves the project templates
	 */
	private void saveProjectTemplate() {
		InputStream in = NeuronalTopologyImporterPluginConfigurator.class.getResourceAsStream(
			"/edu/gcsc/vrl/neti/neti_template.vrlp");
		try {
			IOUtil.saveStreamToFile(in, templateProjectSrc);
		} catch (FileNotFoundException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(VRLPlugin.class.getName()).
				log(Level.SEVERE, null, ex);
		}
	}
 }
