package de.chipxonio.adtech.selrunner.packages;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;
import de.chipxonio.adtech.selrunner.tests.TestSuite;

public class Package extends ClassLoader {
	private final ZipFile file;
	private String name;

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

	public TestSuite getTestSuite() throws PackageLoaderException {
		// pretty much to go wrong in this method... phew
		try {
			TestSuite testSuite = new TestSuite();
			NodeList nodes = XPathAPI.selectNodeList(this.getTestIndex(), "/package/test");
			for (int i = 0; i < nodes.getLength(); i++) {
				String testClassName = nodes.item(i).getAttributes().getNamedItem("class").getNodeValue();
				try {
					Class<?> c = this.loadClass(testClassName);
					testSuite.addTest((AbstractTest)c.newInstance());
				} catch (ClassNotFoundException e) {
					throw new PackageLoaderException("class '" + testClassName + "' defined in test index XML could not be found in JAR", e);
				} catch (InstantiationException e) {
					throw new PackageLoaderException("class '" + testClassName + "' could not be instantiated", e);
				} catch (IllegalAccessException e) {
					throw new PackageLoaderException("class '" + testClassName + "' could not be accessed", e);
				}
			}
			return testSuite;
		} catch (TransformerException e4) {
			throw new PackageLoaderException("XPATH engine could not determine classes to be loaded", e4);
		}
	}
	
	public String toString() {
		return this.name + " (" + this.file.getName() + ")";
	}
}
