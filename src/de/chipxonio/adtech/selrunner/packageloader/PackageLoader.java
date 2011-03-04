package de.chipxonio.adtech.selrunner.packageloader;

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

public class PackageLoader extends ClassLoader {
	private final ZipFile file;

	public PackageLoader(String filename) throws IOException {
		this.file = new ZipFile(filename);
	}
	
	public PackageLoader(File file) throws IOException {
		this.file = new ZipFile(file);
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

	public TestSuite getTestSuite() throws PackageLoaderException {
		// pretty much to go wrong in this method... phew
		ZipEntry entry = this.file.getEntry("tests.xml");
		if (entry == null)
			throw new PackageLoaderException("package file does not contain a test index XML (tests.xml missing)");
		try {
			InputStream is = this.file.getInputStream(entry);
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = dbf.newDocumentBuilder();
			Document dom = builder.parse(is);
			TestSuite testSuite = new TestSuite();
			NodeList nodes = XPathAPI.selectNodeList(dom, "/tests/test");
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
		} catch (IOException e1) {
			throw new PackageLoaderException("test index XML could not be extracted", e1);
		} catch (ParserConfigurationException e2) {
			throw new PackageLoaderException("could not parse test index XML", e2);
		} catch (SAXException e3) {
			throw new PackageLoaderException("could not parse test index XML", e3);
		} catch (TransformerException e4) {
			throw new PackageLoaderException("XPATH engine could not determine classes to be loaded", e4);
		}
	}
}
