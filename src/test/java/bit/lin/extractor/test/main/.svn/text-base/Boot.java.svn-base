package bit.lin.extractor.test.main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;



import bit.lin.extractor.main.ContentDensityBasedExtractor;
import bit.lin.extractor.main.LineDensityBasedExtractor;
import bit.lin.extractor.util.CountType;
import bit.lin.extractor.util.CustomizedCleaner;

public class Boot {

	public void singleCase(String file, CountType type) {

		CustomizedCleaner cleaner = new CustomizedCleaner();
		ContentDensityBasedExtractor extractor = new ContentDensityBasedExtractor(
				cleaner.cleanHtml(file), type, true).setCountWordType(
				type).setDebug(true);

		List<String> result = extractor.getMainContent();
		for (int i = 0; i < result.size(); i++) {
			System.out.println(result.get(i));
		}

		System.out.println("We got " + result.size() + " paragraph here.");
	}

	public void megaCase(CountType type) {
		String[] all = { "yahoo", "cnn", "bbc", "nytimes" };
		String src = "/home/lins/data/*/page/";
		String desC = "/home/lins/data/*/rslt-c/";
		CustomizedCleaner cleaner = new CustomizedCleaner();

		BufferedWriter bw = null;
		ContentDensityBasedExtractor extractor = new ContentDensityBasedExtractor(
				type, false);

		for (String a : all) {
			src = "/home/lins/data/*/page/";
			desC = "/home/lins/data/*/rslt-c/";
			src = src.replaceAll("\\*", a);
			desC = desC.replaceAll("\\*", a);
			File dir = new File(src);
			String[] fName = dir.list();

			for (int i = 0; i < fName.length; i++) {
				try {
					extractor.setResource(cleaner.cleanHtml(src + fName[i]));
					List<String> result = extractor.getMainContent();
					bw = new BufferedWriter(new FileWriter(new File(desC
							+ fName[i])));
					for (int k = 0; k < result.size(); k++) {
						bw.write(result.get(k));
					}
				}  catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bw != null)
							bw.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public void newExtracor() {
		String[] all = { "yahoo", "cnn", "bbc", "nytimes" };
		String src;
		String desA;
		String tmp;
		for (String a : all) {
			src = "/home/lins/data/*/page/".replaceAll("\\*", a);
			desA = "/home/lins/data/*/rslt-a/".replaceAll("\\*", a);
			tmp = "/home/lins/data/*/tmp/".replaceAll("\\*", a);
			File dir = new File(src);
			String[] fName = dir.list();
			for (String n : fName) {
				try {
					LineDensityBasedExtractor ext = new LineDensityBasedExtractor();
					CustomizedCleaner cleaner = new CustomizedCleaner();
					cleaner.formatHtml(src+n, tmp+n);
					ext.getMainContent(tmp+n+".xml");
					
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(desA+n)));
					bw.append(ext.ttl);
					bw.flush();
					bw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 测试抽取器
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Boot boot = new Boot();
		boot.newExtracor();
//		boot.megaCase(CountType.ForEn);
//		boot.singleCase("/home/lins/data/bbc/page/bbc_002",CountType.ForEn);
	}

}
