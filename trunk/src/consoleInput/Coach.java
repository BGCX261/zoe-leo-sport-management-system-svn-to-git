package consoleInput;

import java.io.Serializable;

public class Coach implements Serializable {
  String name;
  int id;
  int phoneNumber;
  
  public Coach(String n,int i,int p) {
	// TODO Auto-generated constructor stub
	  name=n;
	  id=i;
	  phoneNumber=p;
  }
  public void printDetail(){
	  System.out.println("-------------------------------------------");
	
	  System.out.println("Coach's id: "+id);
	  System.out.println("Coach's name: "+name);
	  System.out.println("Coahch's telephone number:" +phoneNumber);
  }
}
