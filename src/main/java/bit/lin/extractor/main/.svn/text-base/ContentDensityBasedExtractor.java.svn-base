package bit.lin.extractor.main;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import bit.lin.extractor.util.CountType;

/**
 * Efficient Web Page Main Text Extraction towards Online News Analysis
 * 算法：每行内容/最大的行内容
 */
public class ContentDensityBasedExtractor {
	static Logger _logger = Logger
			.getLogger(ContentDensityBasedExtractor.class);

	private CountType _countWordType = CountType.ForEn;
	private List<String> _resource;
	private Reader reader = null;
	private double[] _n;
	private double _max = 0.0;
	private double _min = 0.0;
	private double _average = 0.0;
	private double _long = 0.0;
	private double _short = 0.0;
	private double _omega1 = 2.0;
	private double _omega2 = 2.0;
	private boolean _isDebug = true;

	public boolean isDebug() {
		return _isDebug;
	}

	/**
	 * 设置输入
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public ContentDensityBasedExtractor setDebug(boolean isDebug) {
		this._isDebug = isDebug;
		return this;
	}

	public List<String> getResource() {
		return _resource;
	}

	public ContentDensityBasedExtractor setResource(List<String> _resource) {
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

	public CountType getCountWordType() {
		return _countWordType;
	}

	public ContentDensityBasedExtractor setCountWordType(
			CountType _countWordType) {
		this._countWordType = _countWordType;
		return this;
	}

	public double getOmega1() {
		return _omega1;
	}

	public ContentDensityBasedExtractor setOmega1(double _omega1) {
		this._omega1 = _omega1;
		return this;
	}

	public double getOmega2() {
		return _omega2;
	}

	public ContentDensityBasedExtractor setOmega2(double _omega2) {
		this._omega2 = _omega2;
		return this;
	}

	public ContentDensityBasedExtractor(List<String> resource, CountType type,
			boolean isDebug) {
		this._resource = resource;
		this._countWordType = type;
		this._isDebug = isDebug;
		init();
	}

	public ContentDensityBasedExtractor(CountType type, boolean isDebug) {
		this._countWordType = type;
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
		switch (_countWordType) {
		case ForEn:
			return str.split(" ").length;
		case ForCh:
			return str.length();
		default:
			return str.split(" ").length;
		}
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
	public List<String> getMainContent() {
		List<String> result = new ArrayList<String>();
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
						store(j, i, result);
						existLong = false;
					}
					j = i + 1;
				}
			}
		}
		if (existLong) {
			store(j, i - 1, result);
		}
		return result;
	}

	private void store(int j, int i, List<String> result) {
		StringBuilder sb = new StringBuilder();
		for (; j < i; j++) {
			sb.append(_resource.get(j)).append("\n");
		}
		result.add(sb.toString());
	}
}
