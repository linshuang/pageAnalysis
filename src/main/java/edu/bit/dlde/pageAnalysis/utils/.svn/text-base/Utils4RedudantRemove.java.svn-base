package edu.bit.dlde.pageAnalysis.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;


import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.jdom.Content;
import org.jdom.Element;

public class Utils4RedudantRemove {
	static BasicDataSource ds = new BasicDataSource();

	static {
		Properties p = new Properties();
		p.setProperty("driverClassName", "com.mysql.jdbc.Driver");
		p.setProperty("url", "jdbc:mysql://localhost:3306/lins");
		p.setProperty("password", "690514");
		p.setProperty("username", "root");
		try {
			ds = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void remove(Element blocks, String url) {
		try {
			// 创建连接
			Connection conn = ds.getConnection();
			PreparedStatement ps4q = conn
					.prepareStatement("select url from blocks where content=?;");
			PreparedStatement ps4i = conn
					.prepareStatement("insert into blocks(content,url) values(?,?);");

			// 找到重复的并删除
			ArrayList<Content> delete = new ArrayList<Content>();
			for (Object o : blocks.getChildren()) {
				Element e = (Element) o;
				String content = e.getValue();
				ps4q.setString(1, content);
				ResultSet rs = ps4q.executeQuery();
				if (rs.first() && sameSite(rs, url)) {
					if (!rs.getString(1).equals(url))
						delete.add(e);
				} else {
					ps4i.setString(1, content);
					ps4i.setString(2, url);
					ps4i.execute();
				}
			}
			for (Content c : delete) {
				blocks.removeContent(c);
			}
			conn.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

	}

	private static boolean sameSite(ResultSet rs, String url_2) throws SQLException {
		boolean flag = false;
		while(rs.next()) {
			String url_1 = rs.getString("url");
			int i = url_1.lastIndexOf("/"), j = url_2.lastIndexOf("/");
			String tmp_1, tmp_2;
			if (i != -1) {
				tmp_1 = url_1.substring(0, i);
			} else {
				tmp_1 = url_1;
			}
			if (j != -1) {
				tmp_2 = url_2.substring(0, j);
			} else {
				tmp_2 = url_2;
			}
			flag = tmp_1.equals(tmp_2);
			if(flag)
				break;
		}
		return flag;
	}

	public static void main(String[] args) {

	}
}
