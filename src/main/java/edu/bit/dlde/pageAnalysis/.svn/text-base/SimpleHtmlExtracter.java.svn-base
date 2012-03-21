package edu.bit.dlde.pageAnalysis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.bit.dlde.pageAnalysis.handler.IPreTreatment;
import edu.bit.dlde.pageAnalysis.handler.impl.FixHtmlErrorHandler;

public class SimpleHtmlExtracter {

	private Log log = LogFactory.getLog(SimpleHtmlExtracter.class);

	private IPreTreatment preTreatHandler = new FixHtmlErrorHandler();

	private String title;
	private String content;

	private Reader reader;

	public void extract() {
		List<String> lines = new ArrayList<String>();

		String html;

		if (reader == null) {
			log.error("no reader available~~");
			return;
		}
		if (preTreatHandler != null) {
			html = preTreatHandler.readFromReader(reader);
			this.title = preTreatHandler.extractTitle(html);
			// html=preTreatHandler.removeTags(html);
			// System.out.println(html);
			lines = preTreatHandler.preTreat(html);
		} else {
			System.out.println("error");
		}
		StringBuilder sb = new StringBuilder();
		for (String line : lines) {
			int totaLength = line.length();
			String content = line.replaceAll("<(.|\n)*?>", "").trim();
			int contentLength = content.length();
			float den = (contentLength + 0.0f) / totaLength;
			if (den > 0.5) {
				// System.out.println(content+"  den: "+den);
				sb.append(content + "\n");
			}
		}
		this.content = sb.toString();
	}

	public IPreTreatment getPreTreatHandler() {
		return preTreatHandler;
	}

	public void setPreTreatHandler(IPreTreatment preTreatHandler) {
		this.preTreatHandler = preTreatHandler;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public static void main(String[] args) {
		String[] all = { "yahoo", "cnn", "bbc", "nytimes" };
		String src;
		String desA;
		for (String a : all) {
			src = "/home/lins/data/*/page/".replaceAll("\\*", a);
			desA = "/home/lins/data/*/rslt-a/".replaceAll("\\*", a);
			File dir = new File(src);
			String[] fName = dir.list();
			for (String n : fName) {
				try {
					SimpleHtmlExtracter e = new SimpleHtmlExtracter();
					BufferedReader br = new BufferedReader(new FileReader(
							new File(src + n)));
					e.setReader(br);
					e.extract();
					br.close();
					File f = new File(desA + n);
					if (!f.exists()) {
						f.createNewFile();
					}
					BufferedWriter bw = new BufferedWriter(new FileWriter(f));
					bw.append(e.getTitle() + "\n" + e.getContent());
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
}
