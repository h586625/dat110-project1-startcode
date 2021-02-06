package no.hvl.dat110;

public class TODO {

	public static String method() {
		
		String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
		
		return "Metoden " + methodName + " er ikke implementert";
	}
	
	
	public static String construtor(String className) {
				
	   return "Konstruktoeren for klassen " + className + " er ikke implementert";
		
	}

}
