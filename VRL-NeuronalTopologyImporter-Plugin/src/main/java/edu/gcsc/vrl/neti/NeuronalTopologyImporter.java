/// package's name
package edu.gcsc.vrl.neti;

/// imports
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import java.io.File;
import eu.mihosoft.vrl.annotation.MethodInfo;
import edu.gcsc.vrl.ug.api.*;
import eu.mihosoft.vrl.system.VMessage;
import java.io.Serializable;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name="NeuronalTopologyImporter", category="/UG4/VRL-Plugins/Neuro/NeuronalTopologyImporter")
public class NeuronalTopologyImporter implements Serializable {
	/// private members
	private String selection = "";
	private boolean correctExp2Syn;
	private boolean correctAlphaSyn;
	
	/// private static members
	private static final long serialVersionUID = 1L;
	
	/**
	 * @brief setup the importer
	 * @param selection 
	 * @param correctExp2Syn
	 * @param correctAlphaSyn
	 */
	@MethodInfo(name="Advanced options", hide=true)
	public void advanced_options(
		@ParamInfo(name  = "File type", typeName = "Filetype", 
			   style = "selection", options = "value=[\"NGX\", \"HOC\", \"SWC\"]")
		String selection,
		
		@ParamInfo(name    = "Correct Exp2Syn", typeName = "Corrects the Exp2Syns")
		boolean correctExp2Syn,
		
		@ParamInfo(name    = "Correct AlphaSyn", typeName = "Corrects the AlphaSyns")
		boolean correctAlphaSyn

	)  {
		this.selection = selection;
		this.correctExp2Syn = correctExp2Syn;
		this.correctAlphaSyn = correctAlphaSyn;
	}
	
	/**
	 * @brief import the geometry
	 * @param file 
	 */
	@MethodInfo(name="Import Geometry", hide=false)
	public void import_geometry(
	@ParamInfo(name  = "Input file", typeName = "Location of the input file", 
		   style = "load-dialog", options = "endings=[\"ngx\", "
			   + "\"hoc\", \"swc\"]; description=\"NGX (NeuGen XML),"
			   + "HOC (NEURON) or SWC (NeuroMorpho) files\"")
	File file
	) {
		if(file.exists() && !file.isDirectory()) {
			I_NeuronalTopologyImporter importer = new NeuronalTopologyImporterProvider().getDefaultNeuronalTopologyImporter();
			importer.correct_alpha_synapses(this.correctAlphaSyn);
			importer.correct_exp2_synapses(this.correctExp2Syn);
			String extension = FilenameUtils.getExtension(file.toString());
			if (selection.isEmpty()) {
				if ("ngx".equalsIgnoreCase(extension)) {
					importer.import_ngx(file.toString());
				} else if ("hoc".equalsIgnoreCase(extension)) {
					VMessage.warning("NeuronalTopologyImporter", "NEURON file type (.hoc) currently not supported.");
				} else if ("swc".equalsIgnoreCase(extension)) {
					importer.import_geometry(file.toString(), extension.toLowerCase());
				} 
			} else {
				if ("ngx".equalsIgnoreCase(selection)) {
					importer.import_ngx(file.toString());
				} else if ("hoc".equalsIgnoreCase(selection)) {
					VMessage.warning("NeuronalTopologyImporter", "NEURON file type (.hoc) currently not supported.");
				} else if ("swc".equalsIgnoreCase(selection)) {
					importer.import_geometry(file.toString(), selection.toLowerCase());
				} 
			}
		} else {
			VMessage.warning("NeuronalTopologyImporter", "Input file not found.");
		}
	}
}
