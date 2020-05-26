package test;

public class doc {

	public void test1() {
		/* valid */
    	System.out.println("aa");
	}

	public void test2(int a) {
    	System.out.println("bb");
    	if (a > 0) {
		/* valid */
      	System.out.println("cc");
		}
	}

	/* valid */
	public void test3() {
		System.out.println("aa");
	}
}
