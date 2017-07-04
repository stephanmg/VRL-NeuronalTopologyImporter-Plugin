/// package's name
package edu.gcsc.vrl.neti.util;

/// imports
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * @brief finds all classes within a jar or folder
 * @author stephanmg <stephan@syntaktischer-zucker.de>
 */
public class ClassFinder {
	/**
	 * @brief finds all classes within a given package directory
	 * @param packageName
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IOException 
	 */
	private static Class<?>[] getClassesFromPackageDirectory(String packageName)
		throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', File.separator.charAt(0));
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			dirs.add(new File(resource.getFile()));
			System.err.println(new File(resource.getFile()));
		}
		List<Class<?>> classes = new ArrayList<Class<?>>();
		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class<?>[classes.size()]);
	}

	/**
	 * @brief recursive method used to find all classes in a given directory and
	 * subdirs.
	 *	 
	 * @param directory The base directory
	 * @param packageName The package name for classes found inside the base
	 * directory
	 * @return The classes
	 * @throws ClassNotFoundException
	 */
	private static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
				System.err.println("package name: " + packageName + '.' + file.getName().substring(0, file.getName().length() - 6));
			}
		}
		return classes;
	}


	/**
	 * @brief finds all classes within given jars in a folder
	 * @param folder
	 * @return 
	 */
	public static ArrayList<Class<?>> getClassesFromJar(File folder) {
		FilenameFilter jarFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jar");
			}
		};

		ArrayList<Class<?>> clazzes = new ArrayList<Class<?>>();
		File[] files = folder.listFiles(jarFilter);
		for (File file : files) {
			if (!file.isDirectory()) {
				System.out.print("     file:");
				
				URL jar = null;
				try {
					jar = file.toURI().toURL();
				} catch (MalformedURLException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}
				ZipInputStream zip = null;
				try {
					zip = new ZipInputStream(jar.openStream());
				} catch (IOException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}
				ZipEntry ze = null;

				List<String> list = new ArrayList<String>();
				try {
					while ((ze = zip.getNextEntry()) != null) {
						String entryName = ze.getName();
						if (entryName.contains("neti") && entryName.endsWith(".class")) {
							list.add(entryName);
							System.err.println("entry: " + entryName.replace(File.separator, "."));
							try {
								System.err.println("to load: "+ entryName.substring(0, entryName.length()-6));
								clazzes.add(Class.forName(entryName.substring(0, entryName.length()-6).replace(File.separator, ".")));
							/// may be thrown if class not loaded by class loader
							} catch (ClassNotFoundException ex) {
								Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
							/// may be thrown if we have interface method
							} catch (ClassCastException cce) {
								Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, cce);
							}
						}
					}
				} catch (IOException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		}
		return clazzes;
	}

	
	/**
	 * @brief ctor
	 */
	public ClassFinder() {

	}

	/**
	 * @brief helper
	 * @param pack
	 * @return
	 */
	public Class<?>[] findAllClasses(String pack) {
		Class<?>[] clazzes = null;
		try {
			clazzes = getClassesFromPackageDirectory(pack);
		} catch (ClassNotFoundException cnfe) {

		} catch (IOException ioe) {

		}
		return clazzes;
	}

	/**
	 * @brief some tests with output
	 */
	public void test() {
		System.err.println("Package name: " + getClass().getPackage().getName());
		for (Class<?> clazz : findAllClasses(getClass().getPackage().getName())) {
			System.err.println("class: " + clazz);
		}
	}

	/**
	 * @brief finds all classes in the given jar file
	 */
	@SuppressWarnings("NestedAssignment")
	public void findAll() {
		File f = new File("/Users/stephan/Temp/console_apps/test27/.application/property-folder/plugins");

		FilenameFilter jarFilter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jar");
			}
		};

		File[] files = f.listFiles(jarFilter);
		for (File file : files) {
			if (!file.isDirectory()) {
				System.out.print("     file:");
				
				URL jar = null;
				try {
					jar = file.toURI().toURL();
				} catch (MalformedURLException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}
				ZipInputStream zip = null;
				try {
					zip = new ZipInputStream(jar.openStream());
				} catch (IOException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}
				ZipEntry ze = null;

				List<String> list = new ArrayList<String>();
				try {
					while ((ze = zip.getNextEntry()) != null) {
						String entryName = ze.getName();
						if (entryName.contains("neti") && entryName.endsWith(".class")) {
							list.add(entryName);
							System.err.println("entry: " + entryName.replace(File.separator, "."));
							try {
								System.err.println("to load: "+ entryName.substring(0, entryName.length()-6));
								Class.forName(entryName.substring(0, entryName.length()-6).replace(File.separator, "."));
							} catch (ClassNotFoundException ex) {
								Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
								System.err.println("error!");
							}
						}
					}
				} catch (IOException ex) {
					Logger.getLogger(ClassFinder.class.getName()).log(Level.SEVERE, null, ex);
				}

			}
		}

	}

	/**
	 * @brief main (some tests)
	 * @param args 
	 */
	public static void main(String... args) {
		new ClassFinder().test();
		new ClassFinder().findAll();
		
		/*System.err.println("test with jar!");
		ClassFinder.getClassesFromJar(new File("/Users/stephan/Temp/console_apps/test27/.application/property-folder/plugins"));
		*/
	}
}
