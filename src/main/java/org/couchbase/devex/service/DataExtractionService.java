package org.couchbase.devex.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.codehaus.plexus.util.cli.CommandLineException;
import org.codehaus.plexus.util.cli.CommandLineUtils;
import org.codehaus.plexus.util.cli.Commandline;
import org.couchbase.devex.BinaryStoreConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DataExtractionService {

	private final Logger log = LoggerFactory.getLogger(DataExtractionService.class);

	private BinaryStoreConfiguration configuration;

	private final ObjectMapper om = new ObjectMapper();
	private final Tika tika = new Tika();
	
	public DataExtractionService(BinaryStoreConfiguration configuration) {
		this.configuration = configuration;
	}

	public Map<String, Object> extractMetadata(File file) {
		String command = configuration.getExifToolPath();
		System.out.println(command);
		String[] arguments = { "-json", "-n", file.getAbsolutePath() };
		Commandline commandline = new Commandline();
		commandline.setExecutable(command);
		commandline.addArguments(arguments);

		CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
		CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();

		try {
			CommandLineUtils.executeCommandLine(commandline, out, err);
		} catch (CommandLineException e) {
			throw new RuntimeException(e);
		}

		String output = out.getOutput();
		if (!output.isEmpty()) {
			try {
				JsonNode node = om.readTree(output);
				return om.convertValue(node.get(0), new TypeReference<Map<String, Object>>(){});
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		String error = err.getOutput();
		if (!error.isEmpty()) {
			log.error(error);
		}
		return null;
	}

	public String extractText(File file) throws FileNotFoundException, IOException, SAXException, TikaException {
		BodyContentHandler handler = new BodyContentHandler();
		AutoDetectParser parser = new AutoDetectParser();
		Metadata metadata = new Metadata();
		try (InputStream stream = new FileInputStream(file)) {
			parser.parse(stream, handler, metadata);
			return handler.toString();
		}

		// String command = configuration.getPdfToText();
		// String[] arguments = { "-raw", file.getAbsolutePath(), "-" };
		// Commandline commandline = new Commandline();
		// commandline.setExecutable(command);
		// commandline.addArguments(arguments);

		// CommandLineUtils.StringStreamConsumer err = new CommandLineUtils.StringStreamConsumer();
		// CommandLineUtils.StringStreamConsumer out = new CommandLineUtils.StringStreamConsumer();

		// try {
		// 	CommandLineUtils.executeCommandLine(commandline, out, err);
		// } catch (CommandLineException e) {
		// 	throw new RuntimeException(e);
		// }

		// String output = out.getOutput();
		// if (!output.isEmpty()) {
		// 	return output;
		// }

		// String error = err.getOutput();
		// if (!error.isEmpty()) {
		// 	log.error(error);
		// }
		// return null;
	}

}
