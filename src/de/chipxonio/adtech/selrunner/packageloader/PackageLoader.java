package de.chipxonio.adtech.selrunner.packageloader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import de.chipxonio.adtech.selrunner.tests.AbstractTest;
import de.chipxonio.adtech.selrunner.tests.TestSuite;

public class PackageLoader extends ClassLoader {
	private final ZipFile file;

	public PackageLoader(String filename) throws IOException {
		this.file = new ZipFile(filename);
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

	public TestSuite getTestSuite() throws Exception {
		ZipEntry entry = this.file.getEntry("tests.xml");
		// TODO customize exception
		if (entry == null)
			throw new Exception("jar file could not be read");
		InputStream is = this.file.getInputStream(entry);
		TestSuite testSuite = new TestSuite();
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = dbf.newDocumentBuilder();
		Document dom = builder.parse(is);
		NodeList nodes = XPathAPI.selectNodeList(dom, "/tests/test");
		for (int i = 0; i < nodes.getLength(); i++) {
			String testClassName = nodes.item(i).getAttributes().getNamedItem("class").getNodeValue();
			Class<?> c = this.loadClass(testClassName);
			testSuite.addTest((AbstractTest)c.newInstance());
		}
		return testSuite;
	}
}
