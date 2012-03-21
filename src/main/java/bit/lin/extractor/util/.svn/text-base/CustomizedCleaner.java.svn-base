package bit.lin.extractor.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.CommentNode;
import org.htmlcleaner.CompactHtmlSerializer;
import org.htmlcleaner.CompactXmlSerializer;
import org.htmlcleaner.ContentNode;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.HtmlNode;
import org.htmlcleaner.JDomSerializer;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.TagNodeVisitor;
import org.jdom.Document;

public class CustomizedCleaner {
	static Logger _logger = Logger.getLogger(CustomizedCleaner.class);
	static ArrayList<String> _list;
	final List<String> _tagToDel = Arrays.asList("SCRIPT", "STYLE", "IMG",
			"INPUT", "BUTTON");

	private void deleteTag(TagNode parentNode, HtmlNode htmlNode) {
		TagNode tag = (TagNode) htmlNode;
		String tagName = tag.getName();
		if (_tagToDel.contains(tagName.toUpperCase())) {
			parentNode.removeChild(tag);
			// _logger.debug("Remove tag <" + tagName
			// + "> from the source html file.");
		}
	}

	private String replaceToTruth(String str) {
		return StringUtils.replace(str, "&apos;", "'").replace("&gt;", ">")
				.replace("&lt;", "<");
	}

	private String replaceToFake(String str) {
		return Pattern.compile("&[a-z1-9#]*;").matcher(str).replaceAll("#");
	}

	/**
	 * 用来对html文件进行清理的方法。采用了{@link HtmlCleaner}进行清理。
	 * 
	 * @param file
	 *            文件路径
	 * @return 网页段落集
	 */
	public List<String> cleanHtml(String file) {
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setNamespacesAware(false);
		_list = new ArrayList<String>();

		File f = new File(file);
		if (!f.exists()) {
			throw new RuntimeException("File to clean not found!");
		}

		TagNode node;
		try {
			node = (TagNode) cleaner.clean(f)
					.getElementListByName("body", false).get(0);

			// 遍历所有标签删除特定的一些标签，注释节点
			node.traverse(new TagNodeVisitor() {
				public boolean visit(TagNode parentNode, HtmlNode htmlNode) {
					if (htmlNode instanceof TagNode) {
						deleteTag(parentNode, htmlNode);

					} else {
						if (htmlNode instanceof CommentNode) {
							parentNode.removeChild(htmlNode);
							// _logger.debug("Remove comment from the source html file.");
						}
					}
					return true;
				}
			});

			CompactXmlSerializer serializer = new CompactXmlSerializer(
					cleaner.getProperties());
//			CompactHtmlSerializer s = new CompactHtmlSerializer(
//					cleaner.getProperties());
//			s.writeToFile(node, file + "out");
			// serializer.writeToFile(node, "/home/lins/document/corpus/out"
			// + Thread.currentThread().getId());

			// 获得纯净的xml文件
			TagNode t = cleaner.clean(serializer.getAsString(node));
			// 遍历xml节点存入list
			t.traverse(new TagNodeVisitor() {
				public boolean visit(TagNode parentNode, HtmlNode htmlNode) {
					if (htmlNode instanceof ContentNode) {
						String tmp = StringUtils
								.trimToNull(((ContentNode) htmlNode)
										.getContent().toString());
						if (tmp != null && tmp != " ") {
							_list.add(replaceToFake(tmp));
							// _logger.debug(replaceToFake(tmp));
						}
					}
					return true;
				}
			});

		} catch (IOException e1) {
			e1.printStackTrace();
		}

		return _list;
	}

	/**
	 * 用来对html文件进行格式化的方法。采用了{@link HtmlCleaner}进行清理。并直接保存为文件名+.xml。
	 * 
	 * @param file
	 *            文件路径
	 * @return 
	 * 			一个JDOM对象
	 */
	public Document cleanHtml2Doc(String file) {
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setNamespacesAware(false);
		_list = new ArrayList<String>();

		File f = new File(file);
		if (!f.exists()) {
			throw new RuntimeException("File to clean not found!");
		}

		TagNode node = null;
		JDomSerializer s = null;
		try {
			node = (TagNode) cleaner.clean(f)
					.getElementListByName("body", false).get(0);
			
			// 遍历所有标签删除特定的一些标签，注释节点
			node.traverse(new TagNodeVisitor() {
				public boolean visit(TagNode parentNode, HtmlNode htmlNode) {
					if (htmlNode instanceof TagNode) {
						deleteTag(parentNode, htmlNode);

					} else {
						if (htmlNode instanceof CommentNode) {
							parentNode.removeChild(htmlNode);
							// _logger.debug("Remove comment from the source html file.");
						}
					}
					return true;
				}
			});

			s = new JDomSerializer(cleaner.getProperties());

		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if (s != null && node != null)
			return s.createJDom(node);
		else
			return null;
	}

	/**
	 * 用来对html文件进行格式化的方法。采用了{@link HtmlCleaner}进行清理。并直接保存为文件名+.xml。
	 * 
	 * @param p1
	 *            文件路径
	 */
	public void formatHtml(String p1,String p2) {
		HtmlCleaner cleaner = new HtmlCleaner();
		CleanerProperties props = cleaner.getProperties();
		props.setNamespacesAware(false);
		_list = new ArrayList<String>();

		File f = new File(p1);
		if (!f.exists()) {
			throw new RuntimeException("File to clean not found!");
		}

		TagNode node;
		try {
			node = (TagNode) cleaner.clean(f)
					.getElementListByName("body", false).get(0);

			// 遍历所有标签删除特定的一些标签，注释节点
			node.traverse(new TagNodeVisitor() {
				public boolean visit(TagNode parentNode, HtmlNode htmlNode) {
					if (htmlNode instanceof TagNode) {
						deleteTag(parentNode, htmlNode);

					} else {
						if (htmlNode instanceof CommentNode) {
							parentNode.removeChild(htmlNode);
							// _logger.debug("Remove comment from the source html file.");
						}
					}
					return true;
				}
			});

			CompactHtmlSerializer s = new CompactHtmlSerializer(
					cleaner.getProperties());
			s.writeToFile(node, p2 + ".xml");

		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
		}
	}
}
