package consoleInput;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.Scanner;

class coachHelp implements Serializable {
	int coNum; //use for account for coaches number
	Coach[] coachs = new Coach[100];// the coaches array

	public coachHelp() {
		// TODO Auto-generated constructor stub
		coNum = 0;  //if coach file not exit,built the object again,or read from ReadHelper
	}

	public void printCoaches() {
		//print all coaches information 
		System.out.println();
		System.out.println("---------Print out coaches---------------");
		if (coNum == 0) {
			
			System.out.println("You havn't add coach!Please add coach first");
			System.out.println();
		} else {
			for (int i = 0; i < coNum; i++) {
				coachs[i].printDetail();
			}
		}

	}
	/*
	 * According the id return coach's name
	 * if there is no the id, return  null
	 */
	public String searchCoach(int id){
		for(int i=0;i<coNum;i++){
			if(id==coachs[i].id){
				return coachs[i].name;
			}
		}
		return "Null";
	}
}

class fitProHelp implements Serializable{
	
	int fitNum;//used for count the number of fitness program
	FitnessProgram [] fitPros=new FitnessProgram[100];//the upper limitation is 100
	
	public fitProHelp() {
		// TODO Auto-generated constructor stub
		fitNum=0;
	}
	
	public void printFitPro() {
		System.out.println();
		System.out.println("---------Print out Fitness Program---------------");
		if (fitNum == 0) {
			System.out.println("You havn't add Fitness program!Please add fitness Program first");
			System.out.println();
		} else {
			for (int i = 0; i < fitNum; i++) {
				fitPros[i].printDetail();
			}
		}
	}
	
	public boolean searchFitPro(String name){//use for search the fitness program,if exit return true
		for(int i=0;i<fitNum;i++){
			if(fitPros[i].name.equals(name)){
				return true;
			}
		}
		return false;
	}
	public int getFitProTime(String name){//use for return the mintutes of the fitness program.
		for(int i=0;i<fitNum;i++){
			if(fitPros[i].name.equals(name)){
				return fitPros[i].duringInMin*60000;//change minute to millisecond.
			}
		}
		return 0;
	}
}
class scheProHelp implements Serializable{
	int schNum;// use for account number of schedule
	Schedule []schPros=new Schedule[100];
	
	public scheProHelp(){
		schNum=0;
	}
	public void printSchPro(){
		System.out.println();
		System.out.println("---------Print out schedule Program---------------");
		if (schNum == 0) {
			System.out.println("You havn't arrange Fitness program!Please add fitness Program first");
			System.out.println();
		} else {
			for (int i = 0; i < schNum; i++) {
				schPros[i].printDetail();
			}
		}
	}
	
	public boolean checkRoom(String room, int weekday, Date time,
			fitProHelp fitprohelp, String name) {//use for check room is available or not
		boolean okRoom = true;
		for (int i = 0; i < schNum; i++) {

			if (schPros[i].room.equals(room))
				if (schPros[i].weekday == weekday) {//if exit the room,then check the day.if day is same check time
					if (schPros[i].startTime.getTime() <= time.getTime()) {//compare the start time 
						if ((schPros[i].startTime.getTime() + fitprohelp
								.getFitProTime(schPros[i].fitName)) > time
								.getTime())// there exit the room use in the same time
							return false;
					    } else {
						if ((time.getTime() + fitprohelp.getFitProTime(name)) > schPros[i].startTime
								.getTime())
							return false;// there exit the room use in the same time
					}
				}
		}
		return okRoom;
	}
	
	public boolean checkCoach(int id, int weekday, Date time,
								fitProHelp fitprohelp, String name) {//use for check for coach is ok or not
		boolean okCoach = true;
		for (int i = 0; i < schNum; i++) {
			if (schPros[i].coachId == id)
				if (schPros[i].weekday == weekday)//if coach is same,check the time
				{
					if (schPros[i].startTime.getTime() <= time.getTime())//compare the start time ,then check whether time overlap
					{
						if ((schPros[i].startTime.getTime() + fitprohelp
								.getFitProTime(schPros[i].fitName)) > time
								.getTime())
							return false;
					} else {
						if ((time.getTime() + fitprohelp.getFitProTime(name)) > schPros[i].startTime
								.getTime())
							return false;
					}
				}
		}
		return okCoach;
	}
}
public class index {
	//Serialization documents stored under the root directory of the project.
	final static String COACHESURI="coaches";
	final static String FITPROURI="fitProFile";
	final static String SCHEDULEURI="scheduleFile";
	
	public static void main(String args[]) throws IOException, Exception {
		
		
		String choose = null;
		
	    coachHelp coachhelp;
	    fitProHelp fitprohelp = null;
	    scheProHelp scheprohelp=null;
	    
		// read storage file
		File coachFile = new File(COACHESURI);
		File fitProFile = new File(FITPROURI);
		File scheduleFile = new File(SCHEDULEURI);
		// check whether these storage file exist
		// if not ,initial them
		if (!coachFile.exists()) {
			 coachhelp = new coachHelp();
		}else{
			coachhelp=(coachHelp)ReadHelp("c");
		}
		
		if (!fitProFile.exists()) {
			// TODO: check file exists
			fitprohelp=new fitProHelp();
		}else{
			fitprohelp=(fitProHelp)ReadHelp("f");
		}
		if (!scheduleFile.exists()) {
			// TODO: check file exists
			scheprohelp=new scheProHelp();
		}else{
			scheprohelp=(scheProHelp)ReadHelp("s");
		}

		//
		
		do {
			indexMenu();
			InputStreamReader stdin = new InputStreamReader(System.in);// input to choice function
			BufferedReader bufin = new BufferedReader(stdin);
			try {
				choose = bufin.readLine();
			} catch (IOException E) {
				System.out.println("ERROR!Input or Output error!!! ");
			}
			if (choose.equals("1")) {
				addCoachModule(coachhelp);
			} else if(choose.equals("2")){
				addFitModule(fitprohelp);
			} else if(choose.equals("3")){
				arrangeFitnessProgram(scheprohelp, coachhelp,fitprohelp);
			}else if(choose.equals("4")){
				scheprohelp.printSchPro();
			}
			else if (choose.equals("5")) {
				coachhelp.printCoaches();
			}else if(choose.equals("7")){
				//just for test 
				//print all fitness program
				fitprohelp.printFitPro();
			}
		} while (!choose.equals("6"));//press 6 to quite
	}

	static public void indexMenu() {

		System.out
				.println("----------------Welcome to Leorowe sport club manange system--------------------");
		System.out.println("1.Add Coach");
		System.out.println("2.Add fitness program");
		System.out.println("3.Assign coach to fitness program");
		System.out.println("4.Print fitness programs");
		System.out.println("5.Print coach information");
		System.out.println("6.Quit");
		System.out.print("What do you want to do?   ");

	}

	// 1.Add Coach Module--------------------
	static public void addCoachModule(coachHelp coachhelp) throws IOException {
		String cName;// coach's Name
		int id;// coach's id
		int phoneNum;// coach's telephone number
		String chooseState;
		System.out
				.println("--------------Add Coach----------------------");
		do {
			System.out.println("Coach's Name:  ");
			Scanner s = new Scanner(System.in);
			cName = s.nextLine();
			System.out.println("Coach's ID");
			id = s.nextInt();
			s.nextLine();
			System.out.println("Coach's phone Number");
			phoneNum = Integer.parseInt(s.nextLine());
			
			coachhelp.coachs[coachhelp.coNum++] = new Coach(cName, id, phoneNum);//creat a new coach program
			
			System.out.println("Do you want to add another coach?      y/n  ");
			chooseState = s.nextLine();

		} while (!chooseState.equals("n"));
		//after Adding process,store the data to file
		SaveHelp(coachhelp,"c");

	}
	
	//2.Add Fitness Program---------------------------------
	static public void addFitModule(fitProHelp fitprohelp) throws IOException{
		String fitName;
		String description;
		int duringInMIn;
		
		String chooseState=null;
		Scanner s=new Scanner(System.in);
		
		System.out.println("--------------Add Fitness Program------------");
		do{
			System.out.println("Fitness Program's Name:  ");
			fitName=s.nextLine();
			System.out.println("Fitness Program's description: ");
			description=s.nextLine();
			System.out.println("Fitness Program's during in Minutes");
			duringInMIn=s.nextInt();
			System.out.println("Do you want to add another fitness program?    y/n");
			s.nextLine();
			fitprohelp.fitPros[fitprohelp.fitNum++]=new FitnessProgram(fitName, description, duringInMIn); //create a new fitness program
			chooseState=s.nextLine();
			
		}while(!chooseState.equals("n"));
		//save process
		SaveHelp(fitprohelp,"f");
	}

	// 3.Arrange Fitness Program Module----------------
	static public void arrangeFitnessProgram(scheProHelp scheprohelp,coachHelp coachhelp,fitProHelp fitprohelp) throws IOException, Exception {
		int id;//coach's id
		String name;//fitness program's name
		String coachName;
		String room;
		int weekday;
		int hour,minutes;
		Date startTime;
		boolean mark=false;
		
		String chooseState="y";
		Scanner s=new Scanner(System.in);
		
		System.out.println("--------------Arrange Fitness Program------------- ");
		do{
			//check whether there is such coach?
			do {
				System.out.println("Coach ID:");
				id = s.nextInt();
				s.nextLine();
				coachName=coachhelp.searchCoach(id);
				if (coachName=="Null") {
					System.out
							.println("ERROR!The coach ID is not exit, not accept ask!");
					mark=true;
				}else{
					mark=false;
					System.out.println("You choose the coach: "+coachName);
				}
					

			} while (mark);
			
			
			//check whether there is such fitness program
			do {
				System.out.println("Fitness program's name");
				name = s.nextLine();
				if (!fitprohelp.searchFitPro(name)) {
					
						System.out
								.println("ERROR!The fitness program is not exit, not accept ask!");
						mark=true;
					}
					else{
						mark=false;
					}
			} while (mark);
		
			//check whether this arrangement is available
			
			do{
				System.out.println("Fitness program's room:");
				room = s.nextLine();
				//check the input weekday is right
				do{
					System.out.println("Fitness program's weekday:(choose the number:1-7)");
					weekday = s.nextInt();
					s.nextLine();
					if(weekday<=7&&weekday>=1){
						mark=false;
					}else{
						mark=true;
						System.out.println("ERROR!Please input the number 0-7.");
					}
				}while(mark);
				//check input hours is right
				do {
					System.out.println("Fitness program's startTime:");
					System.out.println("Start time hour is:");
					hour = s.nextInt();
					s.nextLine();
					if(hour>=0&&hour<24){
						mark=false;
					}else{
						mark=true;
						System.out.println("ERROR!Please input the number 0-24.");
					 }	
					} while (mark);
				//check input minutes is right
				do {
					System.out.println("Start time minute is:");
					minutes = s.nextInt();
					s.nextLine();
					if(minutes>=0&&minutes<60){
						mark=false;
					}else{
						mark=true;
						System.out.println("ERROR!Please input the number 0-60");
					}
				} while (mark);
				
				startTime=new Date(2011, 1, weekday, hour, minutes);// new start time according to input information
				
				if (checkOK(id, scheprohelp, room, weekday, startTime,fitprohelp, name))//checkOK is a method to 
				{
					mark = false;
					scheprohelp.schPros[scheprohelp.schNum++] = new Schedule(
							name, id, room, weekday, hour, minutes, coachName);// create a new schedule
				} else {
					System.out
							.println("ERROR!There has exit program,the time is not available.");
					mark = true;
				}

			}while(mark);	
			
			System.out.println("Do you add another fitness program? y/n");
			chooseState=s.nextLine();
		}while(!chooseState.equals("n"));
		SaveHelp(scheprohelp,"s");
	}
	
	public static boolean checkOK(int id,scheProHelp scheprohelp,String room,int weekday,Date startTime,fitProHelp fitprohelp,String name) throws IOException, Exception{

		boolean checkOk=true;
		if(!scheprohelp.checkRoom(room,weekday,startTime, fitprohelp,name))	//check the room ;
				checkOk = false;
		if(!scheprohelp.checkCoach(id, weekday, startTime, fitprohelp,name))//check the coach;
		        checkOk=false;
		return checkOk;
	}

	static public void SaveHelp(Object coachhelp,String type) throws IOException{//use for save data
		String URI=null;
		
		if(type.equals("c")){
			URI=COACHESURI;
		}else if(type.equals("f")){
			URI=FITPROURI;
		}else if(type.equals("s")){
			URI=SCHEDULEURI;
		}
		if (URI != null) {
			FileOutputStream fos = new FileOutputStream(URI);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(coachhelp);
			oos.flush();
			oos.close();
		}else
			System.out.println("ERROR!Some error happen in save process");
		
		
	}
	static public Object ReadHelp(String type) throws IOException, Exception{//use for read data
		String URI = null;
		if(type.equals("c")){
			URI=COACHESURI; //coach 
		}else if(type.equals("f")){
			URI=FITPROURI; //fitness program
		}else if(type.equals("s")){
			URI=SCHEDULEURI;  //schedule
		}
		if (URI != null) {
			FileInputStream fis = new FileInputStream(URI);
			ObjectInputStream ois = new ObjectInputStream(fis);
			return ois.readObject();
		}else
			return null;
	}
}




