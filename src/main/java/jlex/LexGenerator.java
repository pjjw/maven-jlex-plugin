package jlex;

import java.io.IOException;

/**
 * Public wrapper class for the lex generator
 *
 * @author Kevin Conaway
 */
public class LexGenerator {
	
	private String _inputFile;
	
	private String _outputFile;
	
	private LexOutput _out;

	/**
	 * Constructor
	 * @param inputFile
	 * @param outputFile
	 * @param out
	 */
	public LexGenerator(String inputFile, String outputFile, LexOutput out) {
		_inputFile = inputFile;
		_outputFile = outputFile;
		_out = out;
	}
	
	/**
	 * Run the generator
	 * @throws IOException
	 */
	public void generate() throws IOException {
		CLexGen generator = new CLexGen(_inputFile, _outputFile, _out);
		generator.generate();
	}
}
