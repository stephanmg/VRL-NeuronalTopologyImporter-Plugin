/// package's name
package edu.gcsc.vrl.neti;

/// imports
import java.io.Serializable;
import java.io.File;
import org.apache.commons.io.FilenameUtils;
import edu.gcsc.vrl.ug.api.I_NeuronalTopologyImporter;
import edu.gcsc.vrl.ug.api.NeuronalTopologyImporterProvider;
import eu.mihosoft.vrl.annotation.ComponentInfo;
import eu.mihosoft.vrl.annotation.ParamInfo;
import eu.mihosoft.vrl.annotation.MethodInfo;
import eu.mihosoft.vrl.system.VMessage;

/**
 * @brief NeuronalTopologyImporter main component 
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
@ComponentInfo(name = "NeuronalTopologyImporter", category = "/UG4/VRL-Plugins/Neuro/NeuronalTopologyImporter")
public class NeuronalTopologyImporter implements Serializable {
	/// private members
	private String selection = "";
	private boolean correctExp2Syn;
	private boolean correctAlphaSyn;
	private I_NeuronalTopologyImporter importer;

	/// private static members
	private static final long serialVersionUID = 1L;

	/**
	 * @brief optionally prepare the importer 
	 * Pre-select a file type and enable synapse correction mechanisms
	 *
	 * @param selection
	 * @param correctExp2Syn
	 * @param correctAlphaSyn
	 */
	@MethodInfo(name = "Advanced Options", hide = true)
	@Deprecated
	public void advanced_options(
		@ParamInfo(name = "File type", typeName = "Filetype",
			style = "selection", options = "value=[\"ngx\", \"hoc\", \"swc\"]") String selection,
		@ParamInfo(name = "Correct Exp2Syn", typeName = "Corrects the Exp2Syns") boolean correctExp2Syn,
		@ParamInfo(name = "Correct AlphaSyn", typeName = "Corrects the AlphaSyns") boolean correctAlphaSyn
	) {
		this.selection = selection;
		this.correctExp2Syn = correctExp2Syn;
		this.correctAlphaSyn = correctAlphaSyn;
	}

	/**
	 * @brief import the geometry 
	 * File is a path on local filesystem which is schedule for import
	 * Note that hoc and txt are currently not supported 
	 * @param file
	 */
	@Deprecated
	@MethodInfo(name = "Import Geometry", hide = true)
	public void import_geometry(
		@ParamInfo(name = "Input file", typeName = "Location of the input file",
			style = "load-dialog", options = "endings=[\"ngx\", "
			+ "\"hoc\", \"swc\", \"txt\"]; description=\"NGX (NeuGen XML),"
			+ "HOC (NEURON), SWC (NeuroMorpho) or TXT (NeuGen TXT) files\"") File file
	) {
		if (file.exists() && !file.isDirectory()) {
			importer = new NeuronalTopologyImporterProvider().getDefaultNeuronalTopologyImporter();

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
				} else if ("txt".equalsIgnoreCase(extension)) {
					VMessage.warning("NeuronalTopologyImporter", "NeuGen TXT file type (.txt) currently not supported.");
				}
			} else {
				if ("ngx".equalsIgnoreCase(selection)) {
					importer.import_ngx(file.toString());
				} else if ("hoc".equalsIgnoreCase(selection)) {
					VMessage.warning("NeuronalTopologyImporter", "NEURON file type (.hoc) currently not supported.");
				} else if ("swc".equalsIgnoreCase(selection)) {
					importer.import_geometry(file.toString(), selection.toLowerCase());
				} else if ("txt".equalsIgnoreCase(selection)) {
					VMessage.warning("NeuronalTopologyImporter", "NeuGen TXT file type (.txt) currently not supported.");
				}
			}
		} else {
			VMessage.warning("NeuronalTopologyImporter", "Input file not found.");
		}
	}

	/**
	 * @brief generates and writes the ug4 compatible computational grid
	 * Note that the grid is stored in the same place where the input
	 * geometry is stored and written with the same basename but with the
	 * ug4 extension for grids which reads .ugx
	 * @param file input geometry file which should be converted
	 *
	 */
	@MethodInfo(name = "Convert File", hide = false)
	public void convert_file(
		@ParamInfo(name = "Input file", typeName = "Location of the input file",
			style = "load-dialog", options = "endings=[\"ngx\", "
			+ "\"hoc\", \"swc\", \"txt\"]; description=\"SWC (Neuromorpho.org),"
			+ "HOC (NEURON), NGX (NeuGen XML) or TXT (NeuGen TXT) files\"") File file
	) {
		if (file.exists() && !file.isDirectory()) {
			String extension = FilenameUtils.getExtension(file.toString());
			if ("hoc".equalsIgnoreCase(extension)) {
				VMessage.warning("NeuronalTopologyImporter",
					"NEURON file type (.hoc) currently not supported.");
			} else {
				importer = new NeuronalTopologyImporterProvider().getDefaultNeuronalTopologyImporter();
				/// Note: This determines the file extension automatically
				importer.import_geometry(file.toString(), "");
				importer.generate_grid();
			}
		} else {
			VMessage.warning("NeuronalTopologyImporter", "Input file not found.");
		}
	}
}