package consoleInput;

import java.io.Serializable;

public class FitnessProgram implements Serializable {
	String name;
	String description;
	int duringInMin;
	
	public FitnessProgram(String n,String d,int dm) {
		// TODO Auto-generated constructor stub
		name=n;
		description=d;
		duringInMin=dm;
	}
	
	 public void printDetail(){
		  System.out.println("-------------------------------------------");
		
		  System.out.println("Fitness's name: "+name);
		  System.out.println("Description : "+description);
		  System.out.println("Course during in Minutes:" +duringInMin);
	  }
}
