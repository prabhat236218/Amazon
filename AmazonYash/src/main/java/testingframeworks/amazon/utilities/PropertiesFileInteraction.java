package testingframeworks.amazon.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesFileInteraction {
	
	private PropertiesFileInteraction() {
		
	}
	
	public static void setProperty(String key, String value, String fileName) {
		try(InputStream input = PropertiesFileInteraction.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            prop.setProperty(key, value);
        } catch (IOException ex) {
            Log.inFatalAdd("PEOPERTY FILE EXCEPTION: "+ex.getMessage());
        }
	}
	
	
	public static String getProperty(String key, String fileName) {
		try(InputStream input = PropertiesFileInteraction.class.getClassLoader().getResourceAsStream(fileName)) {
            Properties prop = new Properties();
            prop.load(input);
            return prop.getProperty(key);
        } catch (IOException ex) {
            Log.inFatalAdd("PEOPERTY FILE EXCEPTION: "+ex.getMessage());
        }
		return "";
	}

}
