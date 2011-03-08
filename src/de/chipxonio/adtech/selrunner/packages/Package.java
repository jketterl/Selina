package de.chipxonio.adtech.selrunner.packages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class Package extends ClassLoader implements TreeModel {
	private Vector<TreeModelListener> listeners = new Vector<TreeModelListener>();
	private final ZipFile file;
	private String name;
	private Preferences preferences;

	public Package(String filename) throws IOException, PackageLoaderException {
		this(new File(filename));
	}
	
	public Package(File file) throws IOException, PackageLoaderException {
		this.file = new ZipFile(file);
		try {
			NodeList nodes = XPathAPI.selectNodeList(this.getTestIndex(), "/package");
			if (nodes.getLength() != 1)
				throw new PackageLoaderException("Multiple or no package definitions found");
			this.name = nodes.item(0).getAttributes().getNamedItem("name").getNodeValue();
		} catch (TransformerException e) {
			throw new PackageLoaderException("XPath Engine could not find package definition", e);
		}
	}

	public Package(Preferences node) throws IOException, PackageLoaderException {
		this(new File(node.get("fileName", "")));
		this.preferences = node;
	}
	
	public void setPreferences(Preferences prefs) {
		this.preferences = prefs;
		this.preferences.put("fileName", this.file.getName());
	}
	
	public Preferences getPreferences() {
		return this.preferences;
	}
	
	public boolean hasPreferences() {
		return this.preferences != null;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		ZipEntry entry = this.file.getEntry(name.replace('.', '/') + ".class");
		if (entry == null) {
			throw new ClassNotFoundException(name);
		}
		try {
			byte[] array = new byte[1024];
			InputStream in = this.file.getInputStream(entry);
			ByteArrayOutputStream out = new ByteArrayOutputStream(array.length);
			int length = in.read(array);
			while (length > 0) {
				out.write(array, 0, length);
				length = in.read(array);
			}
			return defineClass(name, out.toByteArray(), 0, out.size());
		} catch (IOException exception) {
			throw new ClassNotFoundException(name, exception);
		}
	}
	
	private Document getTestIndex() throws PackageLoaderException {
		ZipEntry entry = this.file.getEntry("package.xml");
		if (entry == null)
			throw new PackageLoaderException("package file does not contain a test index XML (package.xml missing)");
		try {
			InputStream is = this.file.getInputStream(entry);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			return builder.parse(is);
		} catch (IOException e) {
			throw new PackageLoaderException("test index XML could not be extracted", e);
		} catch (ParserConfigurationException e) {
			throw new PackageLoaderException("could not parse test index XML", e);
		} catch (SAXException e) {
			throw new PackageLoaderException("could not parse test index XML", e);
		}
	}

	// TODO i don't like to suppress warnings, but i don't know a way to work this out properly atm
	@SuppressWarnings("unchecked")
	public TestDefinition[] getTests() throws PackageLoaderException {
		// pretty much to go wrong in this method... phew
		try {
			NodeList nodes = XPathAPI.selectNodeList(this.getTestIndex(), "/package/test");
			TestDefinition[] tests = new TestDefinition[nodes.getLength()];
			for (int i = 0; i < nodes.getLength(); i++) {
				String testClassName = nodes.item(i).getAttributes().getNamedItem("class").getNodeValue();
				String testName = nodes.item(i).getAttributes().getNamedItem("name").getNodeValue();
				try {
					Class<?> c = this.loadClass(testClassName);
					if (!AbstractTest.class.isAssignableFrom(c))
						throw new PackageLoaderException("'" + testClassName + "' does not inherit from AbstractTest");
					tests[i] = new TestDefinition(testName, (Class<AbstractTest>) c);
				} catch (ClassNotFoundException e) {
					throw new PackageLoaderException("class '" + testClassName + "' defined in test index XML could not be found in JAR", e);
				}
			}
			return tests;
		} catch (TransformerException e) {
			throw new PackageLoaderException("XPATH engine could not determine classes to be loaded", e);
		}
	}
	
	public String toString() {
		return this.name + " (" + this.file.getName() + ")";
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		this.listeners.add(l);
	}

	@Override
	public Object getChild(Object parent, int index) {
		if (parent == this) {
			try {
				return this.getTests()[index];
			} catch (PackageLoaderException e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return ((TreeModel) parent).getChild(parent, index);
		}
	}

	@Override
	public int getChildCount(Object parent) {
		if (parent == this) {
			try {
				return this.getTests().length;
			} catch (PackageLoaderException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return ((TreeModel) parent).getChildCount(parent);
		}
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getRoot() {
		return null;
	}

	@Override
	public boolean isLeaf(Object node) {
		if (node == this) {
			return false;
		} else {
			return ((TreeModel) node).isLeaf(node);
		}
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		this.removeTreeModelListener(l);
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}
}
