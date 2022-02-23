package org.recreativospiscis.PromEspressoConverter;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.recreativospiscis.PromEspressoConverter.devices.PROM82S129Specs;
import org.recreativospiscis.PromEspressoConverter.devices.PROM82S131Specs;
import org.recreativospiscis.PromEspressoConverter.devices.PROM82S135Specs;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for PROM Espresso Converter.
 */
public class PromEspressoConverterTest extends TestCase {

	private final static String PROM_82S129_TEST_FILE = "test_82S129.bin";
	private final static String PROM_82S131_TEST_FILE = "test_82S131.bin";
	private final static String PROM_82S135_TEST_FILE = "test_82S135.bin";

	/**
	 * Create the test case
	 *
	 * @param testName name of the test case
	 */
	public PromEspressoConverterTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(PromEspressoConverterTest.class);
	}

	public void testConvertProm82S135() throws Exception {
		byte[] prom = this.getFileContents(PROM_82S135_TEST_FILE);

		byte[] convertedEspresso = App.processPromDumpData(new PROM82S135Specs(), prom);

		assertTrue("Invalid espresso file", convertedEspresso != null);

		System.out.println(PROM_82S135_TEST_FILE + ":\n" + new String(convertedEspresso, StandardCharsets.UTF_8));
	}

	public void testConvertProm82S129() throws Exception {
		byte[] prom = this.getFileContents(PROM_82S129_TEST_FILE);

		byte[] convertedEspresso = App.processPromDumpData(new PROM82S129Specs(), prom);

		assertTrue("Invalid espresso file", convertedEspresso != null);

		System.out.println(PROM_82S129_TEST_FILE + ":\n" + new String(convertedEspresso, StandardCharsets.UTF_8));
	}

	public void testConvertProm82S131() throws Exception {
		byte[] prom = this.getFileContents(PROM_82S131_TEST_FILE);

		byte[] convertedEspresso = App.processPromDumpData(new PROM82S131Specs(), prom);

		assertTrue("Invalid espresso file", convertedEspresso != null);

		System.out.println(PROM_82S131_TEST_FILE + ":\n" + new String(convertedEspresso, StandardCharsets.UTF_8));
	}

	private byte[] getFileContents(String fileName) throws URISyntaxException, IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return Files.readAllBytes(new File(resource.toURI()).toPath());
		}
	}
}
