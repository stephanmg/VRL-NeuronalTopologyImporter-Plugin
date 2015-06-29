/// package's name
package edu.gcsc.vrl.neti.util;

/// imports
import java.io.File;

/**
 * @brief PathProvider interface
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public interface IPathProvider {
	File getResourceFile(String rsc);
	File setResourceFile(String rsc);
}
