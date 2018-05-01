package dynamicTT;

import java.io.IOException;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.io.InputStreamReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


	
public class Initialization {
	
	//this class takes all inputs from a file. courseID, courseName, roomID's, subjects and professors associated with course
	//currently hardcoded by taking one course with 6 subjects and 6 teachers
	
	private ArrayList<Subject> subjects=new ArrayList();
	private ArrayList<Professor> professors=new ArrayList();
	private ArrayList<TimeTable> timetables=new ArrayList();
	private ArrayList<Lecture> classes=new ArrayList<>();
	private ArrayList<Combination> combinations=new ArrayList<>();
	private int min;
	String csvFile = "C:\\Users\\govin\\Desktop\\Timetable-scheduling-using-Genetic-Algorithm-master\\src\\dynamicTT\\Classroom.csv";
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
			
	//reads input from a file.
	public int getMinimum()
	{
		return min;
	}

 
	public void readInput() throws IOException{
		
		try
		{
			br = new BufferedReader(new FileReader(csvFile));
    		ArrayList<ClassRoom> classroom=new ArrayList<>();
			while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
//                if(country[0].equals("#"))
//                {
//                	break;                    
//                }
                ClassRoom room = new ClassRoom(country[0], Integer.parseInt(country[1]), Boolean.parseBoolean(country[2]), country[3]);
                classroom.add(room);
//                System.out.println(room.getRoomNo());
            }
			
//			ClassRoom room1 = new ClassRoom("CR1", 110, false, "CS");
//			classroom.add(room1);

			String csvFile = "C:\\Users\\govin\\Desktop\\Timetable-scheduling-using-Genetic-Algorithm-master\\src\\dynamicTT\\Professors.csv";
		    BufferedReader br = null;
		    String line = "";
		    br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] country = line.split(cvsSplitBy);
                professors.add(new Professor(Integer.parseInt(country[0]), country[1], country[2]));
            }
//			professors.add(new Professor(1, "BRC", "DC"));
//			professors.add(new Professor(2, "KC", "SE"));

			createLectures(professors);
			
			TimeTable timetb1=new TimeTable(classroom, classes);//, professors);
			//timetb1.initialization(classroom, classes);
			//TimeTable timetb2=new TimeTable(classroom, classes);
			//TimeTable timetb3=new TimeTable(classroom, classes);
			
			int courseid = 1;
			String courseName="BTech.C.S. Part I";
			System.out.println("reading input.......");
//			subjects.add(new Subject(1,"DC",4,false, "CS"));

			csvFile = "C:\\Users\\govin\\Desktop\\Timetable-scheduling-using-Genetic-Algorithm-master\\src\\dynamicTT\\Subjects.csv";
		    br = null;
//			subjects.add(new Subject(1,"DC",4,false, "CS"));
		    ArrayList<StudentGroups> studentGroups;
		    line = "";
		    String[] country;
		    br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
                // use comma as separator
                country = line.split(cvsSplitBy);
                if(country[0].equals("#"))
                {
                	System.out.println("new course creation.......");
                	String csv = "C:\\Users\\govin\\Desktop\\Timetable-scheduling-using-Genetic-Algorithm-master\\src\\dynamicTT\\Courses.csv";
//        			subjects.add(new Subject(1,"DC",4,false, "CS"));

        		    String lines = "";
        		    BufferedReader course = new BufferedReader(new FileReader(csv));
        		    lines = course.readLine();
        		    String[] temp = lines.split(cvsSplitBy);
        			Course course1 = new Course(Integer.parseInt(temp[0]), temp[1], subjects);
        			course1.createCombination(temp[2], Integer.parseInt(temp[3]));		
        			course1.createStudentGroups();
        			studentGroups = course1.getStudentGroups();
        			timetb1.addStudentGroups(studentGroups);
        			subjects.clear();
        			continue;
                }
           
                {
                    subjects.add(new Subject(Integer.parseInt(country[0]),country[1],Integer.parseInt(country[2]),Boolean.parseBoolean(country[3]), country[4]));

                }
            }
					
			
			//combinations.addAll(course1.getCombinations());
			
			//timetb2.addStudentGroups(studentGroups);
//			subjects.clear();
//			
//			Course course2 = new Course(2, "MSc.I.T. Part II", subjects);
//			course2.createCombination("DM/DAA/SS/ML/UML/MLlab/R/", 20);
//			course2.createStudentGroups();
//			studentGroups = course2.getStudentGroups();
//			timetb1.addStudentGroups(studentGroups);
			//combinations.addAll(course2.getCombinations());
			//timetb2.addStudentGroups(studentGroups);
			//timetb3.addStudentGroups(studentGroups);
			
			System.out.println("Setting tt.......");
			
			System.out.println("adding tt.......");
			timetb1.initializeTimeTable();
			//timetb2.initializeTimeTable();
			//timetb3.initializeTimeTable();
			timetables.add(timetb1);
			//timetable.add(timetb2);
			//timetable.add(timetb3);
			
			
			System.out.println("populating.......");
			
			
			
			//display();
			
			populateTimeTable(timetb1);
			GeneticAlgorithm ge=new GeneticAlgorithm();
			
			//ge.fitness(timetb1);
//			timetb1.createTimeTableGroups(combinations);
			ge.populationAccepter(timetables);
			
			min=ge.getMin();
//			//ge.fitness(timetb2);
			
			//ge.fitness(timetb3);
			
			//populateTimeTable();
		}
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		
	}
	
	public void populateTimeTable(TimeTable timetb1){
		int i=0;
		System.out.println("populating started.......");
		while(i<3){
			TimeTable tempTimetable = timetb1;
			ArrayList<ClassRoom> allrooms = tempTimetable.getRoom();
			Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
			while(allroomsIterator.hasNext()){
				ClassRoom room = allroomsIterator.next();
				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
				Collections.shuffle(weekdays);
				if(!room.isLaboratory()){
					Iterator<Day> daysIterator=weekdays.iterator();
					while(daysIterator.hasNext()){
						Day day = daysIterator.next();
						Collections.shuffle(day.getTimeSlot());
					}
				}				
			}
			timetables.add(tempTimetable);
			i++;
		}
		System.out.println("populating done.......");
		System.out.println("display called.......");
		display();
	}
	
	private void createLectures (ArrayList<Professor> professors) {
		// TODO Auto-generated method stub
		
		java.util.Iterator<Professor> professorIterator=professors.iterator();
		while(professorIterator.hasNext()){
			Professor professor=professorIterator.next();
			ArrayList<String> subjectsTaught = professor.getSubjectTaught();
			Iterator<String> subjectIterator = subjectsTaught.iterator();
			while(subjectIterator.hasNext()){
				String subject = subjectIterator.next();
				classes.add(new Lecture (professor, subject));
			}
		}
	}
	
	//creates another 3 timetable objects for population by taking first yimetable and shuffling it.
	
//	public void populateTimeTable(){
//		int i=0;
//		System.out.println("populating started.......");
//		while(i<6){
//			TimeTable tempTimetable = timetbl;
//			ArrayList<ClassRoom> allrooms = tempTimetable.getRoom();
//			Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
//			while(allroomsIterator.hasNext()){
//				ClassRoom room = allroomsIterator.next();
//				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
//				Iterator<Day> daysIterator=weekdays.iterator();
//				while(daysIterator.hasNext()){
//					Day day = daysIterator.next();
//					Collections.shuffle(day.getTimeSlot());
//				}
//			}
//			timetable.add(tempTimetable);
//			i++;
//		}
//		System.out.println("populating done.......");
//		System.out.println("display called.......");
//		display();
//		
//		GeneticAlgorithm.populationAccepter(timetable);
//	}
	
	//displays all timetables
	
	private void display() {
		// TODO Auto-generated method stub
		int i=1;
		System.out.println("displaying all tt's.......");
		Iterator<TimeTable> timetableIterator = timetables.iterator();
		while(timetableIterator.hasNext()){
			System.out.println("+++++++++++++++++++++++++++++++++++++++++\nTime Table no. "+i);
			TimeTable currentTimetable = timetableIterator.next();
			System.out.println("Score : "+currentTimetable.getFittness());
			ArrayList<ClassRoom> allrooms = currentTimetable.getRoom();
			Iterator<ClassRoom> allroomsIterator = allrooms.iterator();
			while(allroomsIterator.hasNext()){
				ClassRoom room = allroomsIterator.next();
				System.out.println("Room: "+room.getRoomNo());
				ArrayList<Day> weekdays = room.getWeek().getWeekDays();
				Iterator<Day> daysIterator=weekdays.iterator();
				while(daysIterator.hasNext()){
					Day day = daysIterator.next();
					ArrayList<TimeSlot> timeslots = day.getTimeSlot();
					Iterator<TimeSlot> timeslotIterator= timeslots.iterator();
					//System.out.print(""+day.getName()+": ");
					while(timeslotIterator.hasNext()){
						TimeSlot lecture = (TimeSlot) timeslotIterator.next();
						if(lecture.getLecture()!=null){
						//System.out.print(" (Subject: "+lecture.getLecture().getSubject()+" --> Professor: "+lecture.getLecture().getProfessor().getProfessorName()+" GrpName: "+lecture.getLecture().getStudentGroup().getName()+")");
							System.out.print("("+lecture.getLecture().getSubject()+"#"+lecture.getLecture().getProfessor().getProfessorName()+"#"+lecture.getLecture().getStudentGroup().getName().split("/")[0]+")");
						}
						else{
							System.out.print("   free   ");
						}
					}
					System.out.print("\n");
				}
				System.out.print("\n\n");
			}
			i++;
		}
		for(i=0; i<subjects.size();i++)
		{
			System.out.println(subjects.get(i));
		}
	}
}