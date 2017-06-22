/// package's name
package edu.gcsc.vrl.neti.test;

/// imports
import edu.gcsc.vrl.ug.api.AlgebraType;
import edu.gcsc.vrl.ug.api.F_InitUG;
import edu.gcsc.vrl.ug.api.I_NeuronalTopologyImporter;
import edu.gcsc.vrl.ug.api.NeuronalTopologyImporterProvider;
import eu.mihosoft.vrl.system.VRL;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @brief NETI tests
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class NETITest {
	/// private static members
	private final static String VRL_PROPERTY_FOLDER
		= "/Users/stephan/Temp/vrl2/";
	
	private final static int dim 
		= 3;
	
	/**
	 * @brief def ctor
	 */
	public NETITest() {
	}
	
	/**
	 * @brief
	 */
	@BeforeClass
	public static void setUpClass() {
	}
	
	/**
	 * @brief
	 */
	@AfterClass
	public static void tearDownClass() {
	}
	
	/**
	 * @brief
	 */
	@Before
	public void setUp() {
	}
	
	/**
	 * @brief
	 */
	@After
	public void tearDown() {
	}

	/// static initializer for all required VRL plugins
	static {
		VRL.initAll(new String[]{"-install-plugin-help", "no", "-property-folder", VRL_PROPERTY_FOLDER});

	}


	/**
	 * @brief simple test of transformator and mapper
	 */
	@Test
	public void test() {
		F_InitUG.invoke(dim, new AlgebraType("CPU", 1));
		I_NeuronalTopologyImporter importer = new NeuronalTopologyImporterProvider().getDefaultNeuronalTopologyImporter();
		importer.import_ngx(VRL_PROPERTY_FOLDER + "/resources/test.ngx");
	}
}
