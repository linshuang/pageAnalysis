package bit.lin.ad;

public class Utils4Entropy {
	public static double getEntropy(double[] inputs) {
		double rslt = 0.0;
		for (double in : inputs) {
			rslt -= in * Math.log(in);
		}
		return rslt;
	}

	public static double getMaxEntropy(int i) {
		double rslt = 0.0;
		double p = 1.0 / i;
		for (; i > 0; i--) {
			rslt -= p * Math.log(p);
		}
		return Math.max(0.1, rslt);
	}

	public static double getEntropyRatio(double[] inputs) {
		return getEntropy(inputs) / getMaxEntropy(inputs.length);
	}

	public static void main(String[] args) {
		double[] inputs = { 984 * 3, 205 * 21, 600 * 66, 974 * 33, 640 * 4615,
				336 * 2251, 984 };
		double sum = 0.0;
		for (int i = 0; i < inputs.length; i++) {
			sum += inputs[i];
		}
		for (int i = 0; i < inputs.length; i++) {
			inputs[i] /= sum;
		}
		System.out.println(Utils4Entropy.getEntropy(inputs));
		System.out.println(Utils4Entropy.getMaxEntropy(inputs.length));
		System.out.println(Utils4Entropy.getEntropy(inputs)
				/ Utils4Entropy.getMaxEntropy(inputs.length));
	}
}
