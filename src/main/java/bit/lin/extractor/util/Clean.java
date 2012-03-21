package bit.lin.extractor.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Clean {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String desC = "/home/lins/data/yahoo/rslt-c/";
		File f = new File(desC);
		String[] fName = f.list();
		for (String n : fName) {
			String total = "";
			BufferedReader br = new BufferedReader(new FileReader(new File(desC
					+ n)));
			String tmp = br.readLine();
			while (tmp != null) {
				total += tmp;
				total += "\n";
				tmp = br.readLine();
			}
			total = total.replaceAll("\\[.*\\]", "");
			br.close();
			System.out.format("file %s replaced\n", n);
			BufferedWriter bw = new BufferedWriter(new FileWriter(new File(desC
					+ n)));
			bw.write(total);
			bw.close();
			System.out.format("file %s done\n", n);
		}
	}

}
