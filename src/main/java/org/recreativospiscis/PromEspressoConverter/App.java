package org.recreativospiscis.PromEspressoConverter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

import org.recreativospiscis.PromEspressoConverter.devices.PROMSpecs;

/**
 * PROM ESPRESSO CONVERTER
 */
public class App {

	public static void main(String[] args) throws Exception {
		if (args.length == 2 || args.length == 3) {
			String promDevice = args[0];
			Class<?> specsClass = Class.forName(
					"org.recreativospiscis.PromEspressoConverter.devices.PROM" + args[0].toUpperCase() + "Specs");
			PROMSpecs promSpecs = (PROMSpecs) specsClass.getConstructor().newInstance(new Object[] {});
			byte[] inputPromDump = readFile(args[1]);
			String outputFilename = args.length == 3 ? args[2] : args[1] + "_espresso.txt";
			byte[] outputEspressoData = processPromDumpData(promSpecs, inputPromDump);
			writeFile(outputFilename, outputEspressoData);
			System.out.println(
					"Successfully extracted espresso data from " + promDevice + " into file: " + outputFilename);
		} else {
			System.err.println(
					"Invalid number of arguments. Usage: prom-espresso-converter.jar <prom_type> <input_filename> [output_filename]");
			return;
		}
	}

	private static byte[] readFile(String fileName) throws IOException {
		if (!Files.exists(Paths.get(fileName))) {
			throw new IOException("Input file not exist: " + fileName);
		}
		return Files.readAllBytes(Paths.get(fileName));
	}

	private static void writeFile(String fileName, byte[] content) throws IOException {
		if (Files.exists(Paths.get(fileName))) {
			throw new IOException("Output file still exist, do not overwrite it: " + fileName);
		}
		Files.write(Paths.get(fileName), content);
	}

	public static byte[] processPromDumpData(PROMSpecs promSpecs, byte[] promData) throws Exception {
		if (promData.length != promSpecs.getByteSize()) {
			throw new Exception("Invalid Dump file size (" + promData.length + " bytes) for device: "
					+ promSpecs.toString() + " (it should be: " + promSpecs.getByteSize() + ")");
		}

		StringBuilder espressoFileContent = new StringBuilder();

		// set header:
		espressoFileContent.append(createEspressoHeader(promSpecs));

		// set pin assignment comment:
		espressoFileContent.append(createPinAssignmentComment(promSpecs));

		// set output enable equations comment:
		espressoFileContent.append(createOutputEnableComment(promSpecs));

		// set content:
		for (int i = 0; i < promSpecs.getByteSize(); i++) {
			espressoFileContent
					.append(String
							.format("%-" + promSpecs.getPinCount_IN() + "s",
									new StringBuilder(Integer.toBinaryString(i)).reverse())
							.replace(' ', '0'));
			espressoFileContent.append(" ");
			espressoFileContent
					.append(String
							.format("%-" + promSpecs.getPinCount_O() + "s",
									new StringBuilder(Integer.toBinaryString(promData[i] & 0xFF)).reverse())
							.replace(' ', '0'));
			espressoFileContent.append("\n");
		}

		// set end:
		espressoFileContent.append(".e");

		return espressoFileContent.toString().getBytes();
	}

	private static String createEspressoHeader(PROMSpecs promSpecs) {
		StringBuilder espressoHeader = new StringBuilder();

		// number of inputs:
		espressoHeader.append(".i ").append(promSpecs.getPinCount_IN());
		espressoHeader.append("\n");

		// number of outputs:
		espressoHeader.append(".o ").append(promSpecs.getPinCount_O());
		espressoHeader.append("\n");

		// names of the inputs:
		espressoHeader.append(".ilb ").append(String.join(" ",
				Arrays.stream(promSpecs.getLabels_IN()).filter(Objects::nonNull).sorted().toArray(String[]::new)));
		espressoHeader.append("\n");

		// names of the outputs:
		espressoHeader.append(".ob ").append(String.join(" ",
				Arrays.stream(promSpecs.getLabels_O()).filter(Objects::nonNull).sorted().toArray(String[]::new)));
		espressoHeader.append("\n");

		return espressoHeader.toString();
	}

	private static String createPinAssignmentComment(PROMSpecs promSpecs) {
		StringBuilder pinAssignmentComment = new StringBuilder();

		pinAssignmentComment.append("# --- DEVICE: ").append(promSpecs.toString()).append(" ---\n");

		pinAssignmentComment.append("# --- INPUTS ---\n");
		for (int i = 0; i < promSpecs.getLabels_IN().length; i++) {
			if (promSpecs.getLabels_IN()[i] != null) {
				int pinIndex = (i + 1) < (promSpecs.getPinCount_Total() / 2) ? i + 1 : i + 2;
				pinAssignmentComment.append("# PIN ").append(pinIndex).append(" = ").append(promSpecs.getLabels_IN()[i])
						.append(";\n");
			}
		}

		pinAssignmentComment.append("# --- OUTPUTS ---\n");
		for (int i = 0; i < promSpecs.getLabels_O().length; i++) {
			if (promSpecs.getLabels_O()[i] != null) {
				int pinIndex = (i + 1) < (promSpecs.getPinCount_Total() / 2) ? i + 1 : i + 2;
				pinAssignmentComment.append("# PIN ").append(pinIndex).append(" = ").append(promSpecs.getLabels_O()[i])
						.append(";\n");
			}
		}

		pinAssignmentComment.append("# --- OUTPUT-ENABLES ---\n");
		for (int i = 0; i < promSpecs.getLabels_OE().length; i++) {
			if (promSpecs.getLabels_OE()[i] != null) {
				int pinIndex = (i + 1) < (promSpecs.getPinCount_Total() / 2) ? i + 1 : i + 2;
				pinAssignmentComment.append("# PIN ").append(pinIndex).append(" = ").append(promSpecs.getLabels_OE()[i])
						.append(";\n");
			}
		}

		return pinAssignmentComment.toString();
	}

	private static String createOutputEnableComment(PROMSpecs promSpecs) {
		StringBuilder outputEnableComment = new StringBuilder();

		outputEnableComment.append("# --- OUTPUT ENABLE EQUATIONS ---\n");

		String[] outputsSorted = Arrays.stream(promSpecs.getLabels_O()).filter(Objects::nonNull).sorted()
				.toArray(String[]::new);

		String outputEnableEq = String.join(" & ", Arrays.stream(promSpecs.getLabels_OE()).filter(Objects::nonNull)
				.sorted().map(o -> "!".concat(o)).toArray(String[]::new));

		for (String output : outputsSorted) {
			outputEnableComment.append("# ").append(output).append(".oe = ").append(outputEnableEq).append(";\n");
		}

		return outputEnableComment.toString();
	}
}