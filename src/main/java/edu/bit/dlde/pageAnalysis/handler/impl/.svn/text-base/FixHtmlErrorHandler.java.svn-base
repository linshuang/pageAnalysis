package edu.bit.dlde.pageAnalysis.handler.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.bit.dlde.pageAnalysis.SimpleHtmlExtracter;
import edu.bit.dlde.pageAnalysis.handler.IPreTreatment;

public class FixHtmlErrorHandler implements IPreTreatment{
	private Log log = LogFactory.getLog(FixHtmlErrorHandler.class);
	public String refineHtml(String html) {
		
		String[] lines=html.split("\n");
		StringBuffer sb=new StringBuffer();
		for(String line:lines){
			int left=0;
			int right=0;
			String buffer="";
			if(buffer.length()==0){
				left=StringUtils.countMatches(line,"<");
				right=StringUtils.countMatches(line,">");
				
				if(left==right){
					sb.append(line+"\n");
				}else{
					buffer=buffer+line;
				}
			}else{
				buffer=buffer+line;
				
				left=StringUtils.countMatches(buffer,"<");
				right=StringUtils.countMatches(buffer,">");
				
				if(left==right){
					sb.append(buffer+"\n");
					buffer="";
				}else{
					buffer=buffer+line;
				}
			}
		}
		
		return sb.toString();
	}

	public List<String> preTreat(String html) {
		String total= removeTags(refineHtml(html));
//		System.out.println(html);
		
		String[] lines=total.split("\n");
		List<String> array=new ArrayList<String>();
		for(String line:lines){
			int left=0;
			int right=0;
			String buffer="";
			log.debug("line:"+line);
			line=line.trim();
			if(buffer.length()==0){
				left=StringUtils.countMatches(line,"<");
				right=StringUtils.countMatches(line,">");
				log.debug("left: "+left +"  right:"+right);
				if(left==right){
					array.add(line);
				}else{
					buffer=line;
				}
			}else{
				buffer=buffer+line;
				left=StringUtils.countMatches(buffer,"<");
				right=StringUtils.countMatches(buffer,">");
				log.debug("left: "+left +"  right:"+right);
				if(left==right){
					array.add(buffer);
					buffer="";
				}else{
					buffer=buffer+line;
				}
			}
		}
		
		return array;
	}


	public String removeTags(String html) {
		
//		Pattern pattern=null;
//		Matcher matcher=null;
		String inputClose="<(input|INPUT)[\\s\\S]*?/>";
		String imgClose="<(img|IMG)[\\s\\S]*?>";
		String iframe="(<|&lt;)(iframe|IFRAME)[\\s\\S]*?(>|&gt;)[\\s\\S]*?(<|&lt;)/(iframe|IFRAME)(>|&gt;)";
		String style="(<|&lt;)(STYLE|style)[\\s\\S]*?(>|&gt;)[\\s\\S]*?(<|&lt;)/(STYLE|style)(>|&gt;)";
		String script="<(script|SCRIPT)[\\s\\S]*?>[\\s\\S]*?</(script|SCRIPT)>";
		String comment="<!--.*?-->";
		String code="&[a-zA-Z]*?;";
		
		List<String> regex=new ArrayList<String>();
		regex.add(inputClose);
		regex.add(imgClose);
		regex.add(iframe);
		regex.add(style);
		regex.add(script);
		regex.add(comment);
		regex.add(code);
		
		for(String tmp:regex){
//			pattern=Pattern.compile(tmp);
//			matcher = pattern.matcher(total);
//			total=matcher.replaceAll("");
			
			html=html.replaceAll(tmp,"");
		}
		return html;
	}
	
	public String readFromReader(Reader reader){
		BufferedReader br=(BufferedReader)reader;
		String line;
		StringBuffer sb=new StringBuffer();
		try {
			line = br.readLine();
			
			while(line!=null){
				sb.append(line+"\n");
				line=br.readLine();
			}
		}catch(Exception e){
			
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString();
	}


	public String extractTitle(String html) {
		String titleRegex="<(title|TITLE)>.*?</(title|TITLE)>";
		Pattern pattern=null;
		Matcher matcher=null;
		pattern=Pattern.compile(titleRegex);
		matcher=pattern.matcher(html);
		String title="";
		if(matcher.find()){
			title=matcher.group();
			title=title.replaceAll("<.*?>",	"");
		}
		log.debug("title:"+title);
		return title;
	}

	
}
