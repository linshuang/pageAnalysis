package bit.lin.block;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jdom.Content;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Text;

import bit.lin.extractor.util.CustomizedCleaner;

// 
public class GetBlocksV3 {
	static CustomizedCleaner cleaner = new CustomizedCleaner();
	private Element blocks;
	private BufferedWriter bw = null;

	public GetBlocksV3(String fileName) {
		// 构建xml文件的树
		Document doc = cleaner.cleanHtml2Doc(fileName);
		blocks = Utils4GetBlock.findBlock(doc.getRootElement());
		Utils4Shrink.shrink(blocks);
	}

	public Element getSgmtRslt() {
		return blocks;
	}

	public void display() {
		display(blocks);
	}

	private void display(Element e) {
		if (e.getParent() == blocks)
			System.out.println("");
		
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			Content c = (Content) itr.next();
			String str;
			if (c instanceof Text) {
				str = c.getValue();
				if (!str.trim().equals(""))
					System.out.print(str.trim());
			}
			if (c instanceof Element) {
				System.out.println("<" + ((Element) c).getName() + ">");
				display((Element) c);
				System.out.println("</" + ((Element) c).getName() + ">");
			}
		}
	}

	public void serialize(String str) {
		File f = new File(str);
		if (!f.exists()) {
			try {
				f.createNewFile();
				bw = new BufferedWriter(new FileWriter(f));
				serialize(blocks);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void serialize(Element e) throws IOException {
		Iterator itr = e.getContent().iterator();
		while (itr.hasNext()) {
			Content c = (Content) itr.next();
			String str;
			if (c instanceof Text) {
				str = c.getValue();
				if (!str.trim().equals("")){
					bw.append(str.trim());
					bw.append("\n");
				}
			}
			if (c instanceof Element) {
				serialize((Element) c);
			}
		}
	}

	public static void main(String[] args) {
		GetBlocksV3 getB = new GetBlocksV3("src/test/resources/bbc1");
		getB.display();
	}
}
