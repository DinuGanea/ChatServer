package server.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.yaml.snakeyaml.Yaml;

public class YamlUtil {
	
	private File yamlFl;
	
	public YamlUtil(File yamlFl) {
		this.yamlFl = yamlFl;
	}
	
	
	public Map<String, Object> loadYaml() throws FileNotFoundException {
		InputStream config;
		Yaml yaml = new Yaml();
		Map<String, Object> values = new HashMap<>();

		config = new FileInputStream(yamlFl);
		// Unchecked cast to Map<String, String>! Have to be modified.
		values = (Map<String, Object>) yaml.load(config);
		
		return values;
	}

}