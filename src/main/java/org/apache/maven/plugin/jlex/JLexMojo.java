package org.apache.maven.plugin.jlex;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.File;
import java.io.IOException;

import jlex.LexGenerator;
import jlex.LexOutput;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

/**
 * Plugin class to run the Jlex java lexical analyzer generator
 *
 * @goal generate
 * @phase generate-sources
 *
 * @author <a href="mailto:kevin.a.conaway@gmail.com">Kevin Conaway</a>
 */
public class JLexMojo extends AbstractMojo {

    /**
     * Pairs of input/output files to run the lex tool on
     * @parameter
     * @required
     */
    private FilePair [] files;

    /**
     * Run the generator
     */
    public void execute() throws MojoExecutionException {
    	Log log = getLog();
    	LexOutput lexout = new LoggerLexOutput(log);
    	
    	for (int i = 0; i <files.length; i++) {
    		FilePair pair = files[i];
    		if (pair == null) {
    			throw new MojoExecutionException("File pair is empty");
    		}
    		File inFile = pair.getInputFile();
    		File outFile = pair.getOutputFile();
    		
    		if (inFile == null || !inFile.exists()) {
    			throw new MojoExecutionException("Input file is empty or doesn't exist");
    		}
    		
    		if (outFile == null) {
    			throw new MojoExecutionException("Output file is empty");
    		}
    		
    		String in = inFile.getPath();
    		String out = outFile.getPath();
	            
	        log.info("jlex input<"+in+"> output<"+out+">");
	        
	        try {
	        	LexGenerator lg = new LexGenerator(in, out, lexout);
				lg.generate();
	        } catch (IOException ex) {
	        	throw new MojoExecutionException("An error occurred running jlex", ex);
	        } catch (Error ex) {
	        	throw new MojoExecutionException("An error occured running jlex", ex);
	        }
    	}
    }
    
    /**
     * Lex output logger
     */
    private static class LoggerLexOutput implements LexOutput {
    	
    	Log log;
    	StringBuffer buf = new StringBuffer();
    	
    	public LoggerLexOutput(Log log) {
    		this.log = log;
    	}
    	
    	public void println(Object o) {
    		if (buf.length() > 0) {
    			log.info(buf.toString());
    			buf = new StringBuffer();
    		}
    		log.info(o.toString());
    	}
    	
    	public void print(Object o) {
    		buf.append(o);
    	}
    }
    
    /**
     * File pair
     */
    public static class FilePair {
    	File inputFile;
    	File outputFile;

		public File getInputFile() {
			return inputFile;
		}

		public void setInputFile(File inputFile) {
			this.inputFile = inputFile;
		}

		public File getOutputFile() {
			return outputFile;
		}

		public void setOutputFile(File outputFile) {
			this.outputFile = outputFile;
		}
    	
    }
}
