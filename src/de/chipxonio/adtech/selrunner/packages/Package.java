package de.chipxonio.adtech.selrunner.packages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.prefs.Preferences;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;

public class Package extends ClassLoader {
	private final ZipFile file;
	private Preferences preferences;
	private TestDefinition rootTest;

	public Package(String filename) throws IOException, PackageLoaderException {
		this(new File(filename));
	}
	
	public Package(File file) throws IOException {
		this.file = new ZipFile(file);
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
	public TestDefinition getRootTest() throws PackageLoaderException {
		if (rootTest == null) try {
			NodeList nodes = XPathAPI.selectNodeList(this.getTestIndex(), "/package/@root");
			if (nodes.getLength() != 1)
				throw new PackageLoaderException("Multiple or no package definitions found");
			rootTest = new TestDefinition((Class<? extends AbstractTest>) this.loadClass(nodes.item(0).getNodeValue()));
		} catch (TransformerException e) {
			throw new PackageLoaderException("XPath Engine could not find package definition", e);
		} catch (DOMException e) {
			throw new PackageLoaderException("XPath Engine could not find package definition", e);
		} catch (ClassNotFoundException e) {
			throw new PackageLoaderException("Package definition is faulty (class not found)", e);
		}
		return rootTest;
	}
	
	private String getName() throws PackageLoaderException {
		return this.getRootTest().getName();
	}
	
	public String toString() {
		try {
			return this.getName() + " (" + this.file.getName() + ")";
		} catch (PackageLoaderException e) {
			return super.toString();
		}
	}
}
