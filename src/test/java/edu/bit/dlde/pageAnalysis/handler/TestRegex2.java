package edu.bit.dlde.pageAnalysis.handler;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;

public class TestRegex2 extends TestCase{

	@Test
	public void testRegex2(){
		String txt="<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"+
									"<tr>"+
										"<td class=\"biaozhun\"><img src=\"../../images/fdj.gif\""+
										"	width=\"18\" height=\"18\" /></td>"+
										"<td class=\"biaozhun\">站内搜索</td>"+
										"<form action=\"http://www.bit.edu.cn:8080/search/search\""+
										"	method=\"post\" name=\"dataForm\" target=\"_blank\" id=\"dataForm\""+
										"	onsubmit=\"document.charset='gbk';\" accept-charset=\"gbk\">"+
										"<input name=\"colls\" value=\"5\" type=\"hidden\" /> <input"+
										"	name=\"collId\" type=\"hidden\" value=\"5\" /> <input"+
										"	name=\"maxresults\" size=\"4\" value=\"10\" type=\"hidden\" />"+
										"<td class=\"biaozhun\"><input name=\"query\" type=\"text\""+
										"	class=\"lvbian\" value=\"\" size=\"20\" /></td>"+
										"<td valign=\"middle\" class=\"biaozhun\"><input name=\"image\""+
										"	type=\"image\" src=\"../../images/ss.jpg\" width=\"37\" height=\"19\" />"+
										"</td>"+
										"</form>"+
									"</tr>"+
								"</table>";
		
		String inputClose="<(input|INPUT)[\\s\\S]*?/>";
		String imgClose="<(img|IMG)[\\s\\S]*?>";
		String iframe="(<|&lt;)(iframe|IFRAME).*?(>|&gt;).*?(<|&lt;)/(iframe|IFRAME)(>|&gt;)";
		String style="(<|&lt;)(STYLE|style).*?(>|&gt;)(.|\n)*?(<|&lt;)/(STYLE|style)(>|&gt;)";
		String script="<(script|SCRIPT).*?>[\\s\\S]*?</(script|SCRIPT)>";
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
		System.out.println(txt);
		for(String tmp:regex){
//			pattern=Pattern.compile(tmp);
//			matcher = pattern.matcher(total);
//			total=matcher.replaceAll("");
			
			txt=txt.replaceAll(tmp,"");
			
		}
		
		System.out.println(txt);
		
	}
	
}
