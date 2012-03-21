package edu.bit.dlde.pageAnalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;

import edu.bit.dlde.pageAnalysis.utils.CustomizedCleaner;
import edu.bit.dlde.pageAnalysis.utils.Utils4GetBlock;
import edu.bit.dlde.pageAnalysis.utils.Utils4MarkNum;
import edu.bit.dlde.pageAnalysis.utils.Utils4RedudantRemove;
import edu.bit.dlde.pageAnalysis.utils.Utils4Shrink;

/**
 * 一个采用密度的方式进行抽取的算法。算法先找到自定义的block，然后对每个block进行求取密度进行抽取。
 * 平滑的原因：ne最好不丢失，启发式难以解决，所以就用平滑
 * 
 * @author lins
 * @version 1.3
 */
public class BlockExtractor {
	private boolean isDynamicThreshold = false;
	private boolean isUsingSmoothing = false;
	private boolean isUsingRedudantRemove = false;

	public boolean isUsingRedudantRemove() {
		return isUsingRedudantRemove;
	}

	public void setUsingRedudantRemove(boolean isUsingRedudantRemove) {
		this.isUsingRedudantRemove = isUsingRedudantRemove;
	}

	public boolean isDynamicThreshold() {
		return isDynamicThreshold;
	}

	public void setDynamicThreshold(boolean isDynamicThreshold) {
		this.isDynamicThreshold = isDynamicThreshold;
	}

	public boolean isUsingSmoothing() {
		return isUsingSmoothing;
	}

	public void setUsingSmoothing(boolean isUsingSmoothing) {
		this.isUsingSmoothing = isUsingSmoothing;
	}

	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	static CustomizedCleaner cleaner = new CustomizedCleaner();
	private final List<String> titleTag = Arrays.asList("h1", "h2", "h3", "h4",
			"h5", "h6");
	private final List<String> blk = Arrays.asList("div", "center", "p", "pre",
			"address", "h1", "h2", "h3", "h4", "h5", "h6", "blockquote", "li",
			"ul", "ol", "dir", "menu", "dl", "dt", "dd");

	private Element blocks;
	private BufferedWriter bw = null;
	private Reader reader = null;
	private String title = "";
	private String content = "";
	public String total = "";

	public BlockExtractor() {

	}

	public BlockExtractor(double threshold) {
		Utils4Shrink.threshold = threshold;
	}
	
	public void setT(double threshold){
		Utils4Shrink.threshold = threshold;
	}
	
	public BlockExtractor(boolean isDynamicThreshold, boolean isUsingSmoothing,
			boolean isUsingRedudantRemove) {
		this.isDynamicThreshold = isDynamicThreshold;
		this.isUsingSmoothing = isUsingSmoothing;
		this.isUsingRedudantRemove = isUsingRedudantRemove;
	}

	public BlockExtractor(Reader reader) {
		this.reader = reader;
	}

	/**
	 * 进行抽取:1.寻找到block；2对每个block计算密度；3根据密度获得最后的结果
	 */
	public void extract() {
		title = "";
		content = "";
		total = "";
		// 构建xml文件的树
		Document doc = cleaner.cleanHtml2Doc(reader);
		// Utils4MarkNum.mark(doc.getRootElement());
		blocks = Utils4GetBlock.getBlock(doc.getRootElement());
		Utils4Shrink.setDynamicThreshold(isDynamicThreshold);
		Utils4Shrink.setUsingSmoothing(isUsingSmoothing);
		Utils4Shrink.shrink(blocks);
		if (isUsingRedudantRemove)
			Utils4RedudantRemove.remove(blocks, url);
		setRslt(blocks);
	}

	/**
	 * 拿到标题
	 * 
	 * @return String 标题，假如extractor认为有多个标题的话，所有的标题被直接链接起来
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 拿到正文
	 * 
	 * @return string 正文，每个block间存在空行
	 */
	public String getContent() {
		return content;
	}

	public Reader getReader() {
		return reader;
	}

	/**
	 * 设置输入
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Element getSgmtRslt() {
		return blocks;
	}

	private void setRslt(Element e) {
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			Content c = (Content) itr.next();
			String str;
			if (c instanceof Text) {
				str = c.getValue().trim();
				str = str.replaceAll("\r|\n", " ");
				if (!str.equals("") && verify(str)) {
					String pname = c.getParentElement().getName().toLowerCase();
					if (titleTag.contains(pname)) {
						title += str;
						title += "\n";
						total += str;
						total += "\n";
					} else {
						content += (" " + str);
						total += (" " + str);
					}
				}
			}
			if (c instanceof Element) {
				String tmp = content;
				setRslt((Element) c);
				String pname = ((Element) c).getName().toLowerCase();
				if (blk.contains(pname) && !tmp.equals(content)) {
					content += "\n";
					total += "\n";
				}
				if ("br".equals(pname)) {
					content += "\n";
					total += "\n";
				}
			}
		}
	}

	private boolean verify(String str) {
		return !str.replaceAll("\n", "").matches("^\\[.*\\]$");
	}

	/**
	 * 将结果序列化到文件
	 * 
	 * @param str
	 *            文件名
	 */
	public void serialize(String str) {
		File f = new File(str);

		try {
			if (!f.exists()) {
				f.createNewFile();
			}
			bw = new BufferedWriter(new FileWriter(f));
			bw.append(total);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
