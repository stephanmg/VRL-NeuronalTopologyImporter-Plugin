/// package's name
package edu.gcsc.vrl.neti.test;

/// imports
import edu.gcsc.vrl.neti.NeuronalTopologyImporterPluginConfigurator;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @brief NETI integration tests
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class NETIIntegration {
	/// private static members
	private final static String CONSOLE_APP_EXE
		= "/Users/stephan/Temp/console_apps/test3/run.sh";
	
	/**
	 * @brief def ctor
	 */
	public NETIIntegration() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}

	/**
	 * @brief simple integration test
	 */
	@Test
	@SuppressWarnings("CallToPrintStackTrace")
	public void integration() {
		try {
			Process ps = Runtime.getRuntime().exec(CONSOLE_APP_EXE);
			try {
				ps.waitFor();
			} catch (InterruptedException ex) {
				Logger.getLogger(NETIIntegration.class.getName()).log(Level.SEVERE, null, ex);
				Logger.getLogger(NETIIntegration.class.getName()).log(Level.SEVERE, null, ex);
			}
			assertEquals(ps.exitValue(), 0);
		} catch (IOException ioe) {
			Logger.getLogger(NETIIntegration.class.getName()).log(Level.SEVERE, null, ioe);
			Logger.getLogger(NETIIntegration.class.getName()).log(Level.SEVERE, null, ioe.getStackTrace());
		}
	}
}
