package bit.lin.block;

import java.io.File;

public class Boot {
	static String src = "/home/lins/data/yahoo/page";
	static String rslt_ws = "/home/lins/data/yahoo/result-ws";
	static String rslt_ws_dt = "/home/lins/data/yahoo/result-ws-dt";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File page = new File(src);
		for (String name : page.list()) {
			System.out.println("Extract from file<---- " + src + "/" + name);

			GetBlocksV3 getB = new GetBlocksV3(src + "/" + name);
			getB.serialize(rslt_ws + "/" + name);

			System.out.println("Result to file-------> " + rslt_ws + "/" + name);
		}
	}
}
