package sfedu.xast.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class ConfigurationUtil {

    private static final String DEFAULT_CONFIG_PATH = "./src/main/resources/enviroment.properties";
    private static final Properties configuration = new Properties();

    public ConfigurationUtil() {
    }

    private static Properties getConfiguration() throws IOException {
        if(configuration.isEmpty()){
            loadConfiguration();
        }
        return configuration;
    }


    private static void loadConfiguration() throws IOException{
        File nf = new File(DEFAULT_CONFIG_PATH);
        InputStream in = new FileInputStream(nf);;
        try {
            configuration.load(in);
        } catch (IOException ex) {
            throw new IOException(ex);
        } finally{
            in.close();
        }
    }

    public static String getConfigurationEntry(String key) throws IOException{
        return getConfiguration().getProperty(key);
    }

}
