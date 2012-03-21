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

import org.apache.log4j.Logger;

/**
 * Efficient Web Page Main Text Extraction towards Online News Analysis
 * 算法：每行内容/最大的行内容
 */
public class ContentLengthExtractor {
	static Logger _logger = Logger.getLogger(ContentLengthExtractor.class);

	private List<String> _resource;
	private double[] _n;
	private double _max = 0.0;
	private double _min = 0.0;
	private double _average = 0.0;
	private double _long = 0.0;
	private double _short = 0.0;
	private double _omega1 = 2.0;
	private double _omega2 = 2.0;
	private boolean _isDebug = true;

	private Reader reader = null;
	private String title = "";
	private String content = "";
	public String total = "";
	private BufferedWriter bw = null;

	public boolean isDebug() {
		return _isDebug;
	}

	/**
	 * 设置输入
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public ContentLengthExtractor setDebug(boolean isDebug) {
		this._isDebug = isDebug;
		return this;
	}

	public List<String> getResource() {
		return _resource;
	}

	public ContentLengthExtractor setResource(List<String> _resource) {
		this._resource = _resource;
		resetAll();
		init();
		return this;
	}

	private void resetAll() {
		_max = 0.0;
		_min = 0.0;
		_average = 0.0;
		_long = 0.0;
		_short = 0.0;
		_omega1 = 2.0;
		_omega2 = 2.0;
	}

	public double getOmega1() {
		return _omega1;
	}

	public ContentLengthExtractor setOmega1(double _omega1) {
		this._omega1 = _omega1;
		return this;
	}

	public double getOmega2() {
		return _omega2;
	}

	public ContentLengthExtractor setOmega2(double _omega2) {
		this._omega2 = _omega2;
		return this;
	}

	public ContentLengthExtractor(List<String> resource, boolean isDebug) {
		this._resource = resource;
		this._isDebug = isDebug;
		init();
	}

	public ContentLengthExtractor(boolean isDebug) {
		this._isDebug = isDebug;
	}

	public void init() {
		first();
		normalize();
		smooth();
		setBoundary();
	}

	private void first() {
		_n = new double[_resource.size()];
		for (int i = 0; i < _n.length; i++) {
			double tmp = _n[i] = getWordCount(_resource.get(i));

			if (_isDebug)
				_logger.debug("After the first initial, n[" + i + "] is "
						+ _n[i] + " now.");
			if (tmp > _max) {
				_max = tmp;
			}
		}
	}

	private int getWordCount(String str) {
		return str.split(" ").length;
	}

	private void normalize() {
		for (int i = 0; i < _n.length; i++) {
			_n[i] /= _max;

			if (_isDebug)
				_logger.debug("After the normalize, n[" + i + "] is " + _n[i]
						+ " now.");
		}
	}

	private void smooth() {
		double tmp = _min = _max = _n[0] = (_omega1 * _n[0] + _n[1])
				/ (_omega1 + 1);
		double sum = tmp;
		int i;
		if (_isDebug)
			_logger.debug("After the smooth, n[" + 0 + "] is " + _n[0]
					+ " now.");

		for (i = 1; i < _n.length - 1; i++) {
			tmp = _n[i] = (_n[i - 1] + _omega1 * _n[i] + _n[i + 1])
					/ (1 + _omega1 + 1);
			if (tmp > _max) {
				_max = tmp;
			}
			if (tmp < _min) {
				_min = tmp;
			}
			sum += tmp;
			if (_isDebug)
				_logger.debug("After the smooth, n[" + i + "] is " + _n[i]
						+ " now.");
		}

		tmp = _n[i] = (_n[i - 1] + _omega1 * _n[i]) / (1 + _omega1);
		if (_isDebug)
			_logger.debug("After the smooth, n[" + i + "] is " + _n[i]
					+ " now.");
		if (tmp > _max) {
			_max = tmp;
		}
		if (tmp < _min) {
			_min = tmp;
		}
		sum += tmp;

		_average = sum / _n.length;
	}

	private void setBoundary() {
		_long = (_omega2 * _max + _average) / (_omega2 + 1);
		if (_isDebug)
			_logger.debug("The long boundary is " + _long + ".");
		_short = (_omega2 * _min + _average) / (_omega2 + 1);
		if (_isDebug)
			_logger.debug("The short boundary is " + _short + ".");
	}

	/**
	 * 通过抽取器活得正文
	 * 
	 * @return List 网页正文集合
	 */
	public void extract() {
		int i, j;
		boolean existLong = false;

		for (i = 0, j = 0; i < _n.length; i++) {
			// max以上
			if (_n[i] >= _long) {
				existLong = true;
			} else {
				// max和min之间
				if (_n[i] >= _short) {
					// do nothing
				} else {
					// min之下的
					if (existLong) {
						store(j, i);
						existLong = false;
					}
					j = i + 1;
				}
			}
		}
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

	private void store(int j, int i) {
		StringBuilder sb = new StringBuilder();
		for (; j < i; j++) {
			sb.append(_resource.get(j)).append("\n");
		}
		total = sb.toString();
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		ContentLengthExtractor cle= new ContentLengthExtractor(false);
		BufferedReader br = new BufferedReader(
				new FileReader(new File("/home/lins/data/yahoo/page/yahoo_1")));
		cle.setReader(br);
		cle.extract();
		cle.serialize("test00");
	}
}
