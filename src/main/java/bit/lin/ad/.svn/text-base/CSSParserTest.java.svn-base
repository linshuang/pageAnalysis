package bit.lin.ad;

import com.steadystate.css.parser.CSSOMParser;
import org.w3c.css.sac.InputSource;
import org.w3c.dom.css.CSSStyleSheet;
import org.w3c.dom.css.CSSRuleList;
import org.w3c.dom.css.CSSRule;
import org.w3c.dom.css.CSSStyleRule;
import org.w3c.dom.css.CSSStyleDeclaration;
import java.io.*;

public class CSSParserTest {
	protected static CSSParserTest oParser;

	public static void main(String[] args) {
		oParser = new CSSParserTest();
		if (oParser.Parse("/home/lins/workspace/density-based/design.css")) {
			System.out.println("Parsing completed OK");
		} else {
			System.out.println("Unable to parse CSS");
		}
	}

	public boolean Parse(String cssfile) {
		FileOutputStream out = null;
		PrintStream ps = null;
		boolean rtn = false;

		try {
			// cssfile accessed as a resource, so must be in the pkg (in src
			// dir).
			InputStream stream = new FileInputStream(new File(cssfile));

			// overwrites and existing file contents
			out = new FileOutputStream("log.txt");
			// log file
			ps = new PrintStream(out);
			System.setErr(ps); // redirects stderr to the log file as well

			InputSource source = new InputSource(new InputStreamReader(stream));
			CSSOMParser parser = new CSSOMParser();
			// parse and create a stylesheet composition
			CSSStyleSheet stylesheet = parser.parseStyleSheet(source);
			// ANY ERRORS IN THE DOM WILL BE SENT TO STDERR HERE!!
			// now iterate through the dom and inspect.
			CSSRuleList ruleList = stylesheet.getCssRules();
			ps.println("Number of rules: " + ruleList.getLength());
			for (int i = 0; i < ruleList.getLength(); i++) {
				CSSRule rule = ruleList.item(i);
				if (rule instanceof CSSStyleRule) {
					CSSStyleRule styleRule = (CSSStyleRule) rule;
					ps.println("selector:" + i + ": "
							+ styleRule.getSelectorText());
					CSSStyleDeclaration styleDeclaration = styleRule.getStyle();

					for (int j = 0; j < styleDeclaration.getLength(); j++) {
						String property = styleDeclaration.item(j);
						ps.println("property: " + property);
						ps.println("value: "
								+ styleDeclaration
										.getPropertyCSSValue(property)
										.getCssText());
						ps.println("priority: "
								+ styleDeclaration
										.getPropertyPriority(property));
					}

				}// end of StyleRule instance test
			} // end of ruleList loop

			if (out != null)
				out.close();
			if (stream != null)
				stream.close();
			rtn = true;
		} catch (IOException ioe) {
			System.err.println("IO Error: " + ioe);
		} catch (Exception e) {
			System.err.println("Error: " + e);
		} finally {
			if (ps != null)
				ps.close();
		}
		return rtn;
	}

}
