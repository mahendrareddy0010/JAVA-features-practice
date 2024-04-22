package mistakes;

public class ExcessiveGarbageCollection {
	public static void main(String[] args) {
		int MAX = 100000;
		long start = System.nanoTime();
		String sampleString = "";
		for (int i = 0; i < MAX; i = i + 1) {
			sampleString = sampleString + "Hello";
		}
		System.out.println("(Naive way) Time took : " + (System.nanoTime() - start) / 1000000);
		sampleString = "";

		start = System.nanoTime();
		StringBuilder sampleStringBuilder = new StringBuilder();
		for (int i = 0; i < MAX; i = i + 1) {
			sampleStringBuilder.append("Hello");
		}
		System.out.println("(String Builder) Time took : " + (System.nanoTime() - start) / 1000000);

	}
}
