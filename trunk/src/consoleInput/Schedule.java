package consoleInput;

import java.io.Serializable;
import java.util.Date;

import javax.xml.stream.events.StartElement;


public class Schedule implements Serializable {

	String fitName;
	String coachName;
	int coachId;
	String room;
	int weekday;
	Date startTime;
	
	Schedule(String f,int c ,String r,int w ,int h , int m,String n){
		fitName=f;
		coachId = c;
		coachName=n;
		room = r;
		weekday =w ;
		startTime = new Date(2011, 1,w, h, m);
		
	}
	
	void printDetail(){
		  
		  
		  System.out.println(coachName+" will teach this course.");
		  System.out.println("Fitness program's name:"+fitName);
		  System.out.println("The fitness program is in room: "+room);
		  System.out.println("The fitness program is on weekday : "+weekday);
		  System.out.println("The fitness program begin at :" + startTime.getHours() + ":"+startTime.getMinutes());
		  System.out.println("--------------------------------------------");
	}
	
}
