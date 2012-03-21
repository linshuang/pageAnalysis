package bit.lin.extractor.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class StringTools {
	static Logger _logger = Logger.getLogger(StringTools.class);

	public static String trim(String str) {
		BufferedReader br = new BufferedReader(new StringReader(str));
		String result = "", tmp = null, trim;

		try {
			do {
				tmp = br.readLine();
				trim = StringUtils.trimToNull(tmp);
				if (trim != null) {
					result += trim;
					result += "\n";
				}
			} while (tmp != null);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static String trim(StringBuffer buffer) {
		return trim(buffer.toString());
	}
}
