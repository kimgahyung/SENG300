public class Example {
	
	public int add(int x, int y) {
		System.out.println("You are adding two numbers");
		return x + y;
	}
	
	public int sub(int x, int y) {
		System.out.println("You are subtracting two numbers");
		return x - y;
	}
	
	public String concatStrings(String a, String b) {
		return a.concat(b);
	}
	
	public static void main(String[] args) {
		Example ex = new Example();
		int sum = ex.add(1, 2);
		int diff = ex.sub(10, 5);
		String concat = ex.concatStrings("Hi", " hello");
		
		System.out.println(sum);
		System.out.println(diff);
		System.out.println(concat);
	}
}