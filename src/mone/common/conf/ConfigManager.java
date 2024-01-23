package mone.common.conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 *
 * @author iby
 *
 */
public class ConfigManager {
	// 로그
	private static final Logger logger = LogManager.getLogger(ConfigManager.class);
	// 설정 정보
	private static Properties _props;

	/**
	 * constructor
	 */
	public ConfigManager() { }

	/**
	 *
	 * @param key
	 * @return
	 */
	public static String getProps(String key) {
		return _props.getProperty(key);
	}

	/**
	 * initialize
	 * 설정 파일 로드
	 * @return
	 * @throws IOException
	 */
	public boolean init() throws IOException {

		boolean result = false;
		FileInputStream fis = null;
		String configFile =  ConstDef.USER_DIR + ConstDef.CONF_PROPS_PATH;


		fis = new FileInputStream(configFile);

		_props = new Properties();
		_props.load(fis);

		result = true;
		fis.close();

		return result;
	}
}
