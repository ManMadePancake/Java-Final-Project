//Andres Arzola
//Final Project

import java.util.*;
import java.io.*;
import java.nio.file.*;


class IdException extends Exception {
    @Override
    public String getMessage() {
        return "Invalid id ....";
    }
}

public class ProjectDriver {

    public static String mainMenu() {
        String option = "0";

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("-----------------------------");
        System.out.println("Main Menu\n");
        
        System.out.println("1 : Student Management");
        System.out.println("2 : Course Management");
        System.out.println("0 : Exit\n");

        System.out.print("Enter your selection: ");
        option = scanner.nextLine();
        System.out.println("\n");
    
    return option;
    }

    public static String SMMenu() {
        String option = "X";

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("-----------------------------");
        System.out.println("Student Management Menu\n");
        System.out.println("Choose one of:\n");
        
        System.out.println("    A - Add a student");
        System.out.println("    B - Delete a student");
        System.out.println("    C - Print fee invoice");
        System.out.println("    D - Print list of students");
        System.out.println("    X - Back to main menu\n");

        System.out.print("Enter your selection: ");
        option = scanner.nextLine();
        System.out.println("\n");
    
    return option;
    }

    public static String CMMenu() {
        String option = "X";

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("-----------------------------");
        System.out.println("Course Management Menu\n");
        System.out.println("Choose one of:");
        
        System.out.println("    A - Search for a class or lab using the class/lab number");
        System.out.println("    B - Delete a class");
        System.out.println("    C - Add a lab to a class");
        System.out.println("    X - Back to main menu\n");

        System.out.print("Enter your selection: ");
        option = scanner.nextLine();
        System.out.println("\n");
    
    return option;
    }

    public static void main(String[] args) {
        
        File file = new File("lect.txt");

        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
        }

        College valenceCollege = new College();
        ArrayList <UGCourse> UGCourses = new ArrayList<>();
        ArrayList <GCourse> GCourses = new ArrayList<>();

        Course courses = new Course(UGCourses, GCourses);

        UndergraduateStudent student = new UndergraduateStudent(UGCourses, "Andres Arzola", "an7989", 3.92, true);
        student.addCrns(66636);
        student.addCrns(56980);
        student.addCrns(98123);

        valenceCollege.enrollStudent(student);
        valenceCollege.addUGStudent(student);

        

        String line = "";
        String [] items = null;

        int index = 0;
        String checker = "";

        while(fileScanner.hasNextLine()) {

            line = fileScanner.nextLine();
            items = line.split(",");

            //if F2F or Mixed
            if(items.length == 8 && items[6].compareToIgnoreCase("Yes") == 0) {
                if(items[3].stripLeading().compareToIgnoreCase("Undergraduate") == 0) {
                    UGCourses.add(new UGCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], items[5], Integer.parseInt(items[7])));

                    checker = items[3];
                    int crn = Integer.parseInt(items[0]);
                    index = courses.getUGIndex(crn);
                    //while loop is checking if the length of the array is 2 while still in the array with length 8
                    
                }
                if(items[3].stripLeading().compareToIgnoreCase("Graduate") == 0) {
                    GCourses.add(new GCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], items[5], Integer.parseInt(items[7])));

                    checker = items[3];
                    int crn = Integer.parseInt(items[0]);
                    index = courses.getGIndex(crn);

                    
                }
            }  
            if(items.length == 8 && items[6].compareToIgnoreCase("Yes") != 0) {
                if(items[3].stripLeading().compareToIgnoreCase("Undergraduate") == 0) {
                    UGCourses.add(new UGCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], items[5], Integer.parseInt(items[7])));
                    //int crn, String prefix, String title, String modality, String location, int creditHours
                }

                if(items[3].stripLeading().compareToIgnoreCase("Graduate") == 0) {
                    GCourses.add(new GCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], items[5], Integer.parseInt(items[7])));
                    //create new graduate course with no labs
                }
            }  
            //if online
            if(items.length == 6) {
                if(items[3].stripLeading().compareTo("Undergraduate") == 0) {
                    UGCourses.add(new UGCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], null, Integer.parseInt(items[5])));
                    //create new online undergraduate course
                }
                if(items[3].stripLeading().compareTo("Graduate") == 0) {
                    GCourses.add(new GCourse(Integer.parseInt(items[0]), items[1], items[2], items[4], null, Integer.parseInt(items[5])));
                    //create new online graduate course
                }
            }
            if(items.length == 2){
                if(checker.stripLeading().compareToIgnoreCase("Undergraduate") == 0) {
                    
                    UGCourses.get(index).addLabs(new Labs(Integer.parseInt(items[0]), items[1])); 
                    
                }
                if(checker.stripLeading().compareToIgnoreCase("Graduate") == 0) {
                    
                    GCourses.get(index).addLabs(new Labs(Integer.parseInt(items[0]), items[1])); 
                    
                }
            }
        }

        fileScanner.close();

        Scanner scanner = new Scanner(System.in);

        String selection = mainMenu();
        String subSelection;

        String Id, studentType;
        if(selection.compareTo("0") ==0) System.out.println("Goodbye!");
        while(selection.compareTo("0") !=0){
            switch( selection ) {
            
                case "0":
                    break;
        
                case "1" :  
                    subSelection = SMMenu();
                    while(subSelection.compareTo("X") != 0) {
                        
                        switch(subSelection) {

                            case "X": //back to main menu
                            
                                break;//end of case X
//____________________________________________________________________________________________________________________________________
                            case "A" : //Add student

                            System.out.println("\nEnter Student's ID(Format: xx####): ");
                            Id = scanner.nextLine();
                            if(valenceCollege.checkId(Id) == true) {

                                System.out.println("Student Type (PhD, MS or Undergrad): ");
                                studentType = scanner.nextLine();
                                if(studentType.compareToIgnoreCase("PhD") == 0) {
                                    //String advisor, String researchSub, String name, String id, double gpa, Boolean inState
                                    String advisor, researchSub, name;
                                    double gpa = -1;
                                    Boolean inState = null;

                                    System.out.println("Student's Name: ");
                                    name = scanner.nextLine();
                                    System.out.println("Student Advisor: ");
                                    advisor = scanner.nextLine();
                                    System.out.println("Research Subject: ");
                                    researchSub = scanner.nextLine();
                                    System.out.println("Please input the lab numbers the student looks over (input -1 when done): ");

                                    int crn = 0;
                                    PhdStudent phdStudent = new PhdStudent(advisor, researchSub, name, Id, gpa, inState);
                                    
                                    while (true) {
                                        try {
                                            crn = scanner.nextInt();  
                                            scanner = new Scanner(System.in);
                            
                                            if (crn == -1) {
                                                System.out.println("Exiting lab addition...");
                                                break;  // Exit the loop if the sentinel value -1 is entered
                                            }
                            
                                            if (courses.searchCrn(crn)) { 
                                                phdStudent.addLabCrns(crn);  
                                                System.out.println("Lab successfully added!");
                                            } else {
                                                System.out.println("Sorry, that lab does not exist...");  // Print error message if CRN doesn't exist
                                            }
                                        } catch (InputMismatchException ime) {
                                            System.out.println("Invalid input: Please enter a numeric CRN.");  // Catch non-integer inputs
                                            scanner.nextLine();  // Consume the bad input that caused the exception
                                        }
                                    }

                                    valenceCollege.enrollStudent(phdStudent);
                                    valenceCollege.addPhDStudent(phdStudent);
                                    System.out.println("[" + name + "] added!");
                                    break;
                                }
                                else if(studentType.compareToIgnoreCase("MS") == 0) {
                                    //ArrayList<GCourse> GCourses, String name, String id, double gpa, Boolean inState
                                    String name;
                                    double gpa = -1;
                                    Boolean inState = null;

                                    System.out.println("Student's Name: ");
                                    name = scanner.nextLine();
                                    System.out.println("Please enter the student's GPA (a value between 0 and 4): ");
                                    int determinant = 0;
                                    while (determinant != -1) {
                                        try {
                                            gpa = scanner.nextDouble();
                                            
                                            if (gpa < 0 || gpa > 4) {
                                                System.out.println("Invalid input, please input a value between 0 and 4!");
                                            } else {
                                                determinant = -1;
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Invalid input detected, please input a numeric value between 0 and 4.");
                                            scanner.nextLine(); 
                                        } 
                                    }
                                    scanner = new Scanner(System.in);
                                    
                                    System.out.println("Please input the courses the student is taking using the crn (input -1 when done): ");

                                    
                                    MsStudent MsStudent = new MsStudent(GCourses, name, Id, gpa, inState);
                                    
                                    while (true) {
                                        try {
                                            int crn = scanner.nextInt();  
                                            scanner = new Scanner(System.in);
                            
                                            if (crn == -1) {
                                                System.out.println("Exiting course addition...");
                                                break;  // Exit the loop if the sentinel value -1 is entered
                                            }
                            
                                            if (courses.searchCrn(crn)) { 
                                                MsStudent.addCrns(crn);  
                                                System.out.println("Course successfully added!");
                                            } else {
                                                System.out.println("Sorry, that course does not exist...");  // Print error message if CRN doesn't exist
                                            }
                                        } catch (InputMismatchException ime) {
                                            System.out.println("Invalid input: Please enter a numeric CRN.");  // Catch non-integer inputs
                                            scanner.nextLine();  // Consume the bad input that caused the exception
                                        }
                                    }

                                    valenceCollege.enrollStudent(MsStudent);
                                    valenceCollege.addMSStudent(MsStudent);
                                    System.out.println("[" + name + "] added!");
                                    break;
                                }
                                else if(studentType.compareToIgnoreCase("Undergrad") == 0) {
                                    //ArrayList<UGCourse> UGCourses, String name, String id, double gpa, Boolean inState
                                    String name;
                                    double gpa = -1;
                                    Boolean inState = null;

                                    System.out.println("Student's Name: ");
                                    name = scanner.nextLine();
                                    System.out.println("Please enter the student's GPA (a value between 0 and 4): ");
                                    int determinant = 0;
                                    while (determinant != -1) {
                                        try {
                                            gpa = scanner.nextDouble();
                                            
                                            if (gpa < 0 || gpa > 4) {
                                                System.out.println("Invalid input, please input a value between 0 and 4!");
                                            } else {
                                                determinant = -1;
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("Non-numeric input detected, please input a numeric value between 0 and 4.");
                                            scanner.nextLine();  // Clear the invalid input from the scanner buffer
                                        } 
                                    }
                                    
                                    scanner = new Scanner(System.in);
                                    System.out.println("Is the student a resident of Florida: ");
                                    int i = 0;
                                    while(i != -1) {
                                        String response = scanner.nextLine();
                                        if(response.compareToIgnoreCase("Yes") == 0) {inState = true; i=-1;}
                                        else if(response.compareToIgnoreCase("No") == 0)  {inState = false; i=-1;}
                                        else System.out.println("Invalid response...\nPlease input Yes or No!");
                                    }
                                    
                                    System.out.println("Please input the courses the student is taking using the crn (input -1 when done): ");

                                    UndergraduateStudent undergraduateStudent = new UndergraduateStudent(UGCourses, name, Id, gpa, inState);
                                    
                                    while (true) {
                                        try {
                                            int crn = scanner.nextInt();  
                                            scanner = new Scanner(System.in);
                            
                                            if (crn == -1) {
                                                System.out.println("Exiting course addition...");
                                                break;  // Exit the loop if the sentinel value -1 is entered
                                            }
                            
                                            if (courses.searchCrn(crn)) { 
                                                undergraduateStudent.addCrns(crn);  
                                                System.out.println("Course successfully added!");
                                            } else {
                                                System.out.println("Sorry, that course does not exist...");  // Print error message if CRN doesn't exist
                                            }
                                        } catch (InputMismatchException ime) {
                                            System.out.println("Invalid input: Please enter a numeric CRN.");  // Catch non-integer inputs
                                            scanner.nextLine();  // Consume the bad input that caused the exception
                                        }
                                    }

                                    valenceCollege.enrollStudent(undergraduateStudent);
                                    valenceCollege.addUGStudent(undergraduateStudent);
                                    System.out.println("[" + name + "] added!");
                                    break;
                                }
                                else {
                                    System.out.println("Invalid input...");
                                }

                            }
                            if(valenceCollege.checkId(Id) == false) {
                                System.out.println("Invalid id format or ID already exists\nTry again later!");
                            }
                            break;//end of case A
//____________________________________________________________________________________________________________________________________
                            case "B" : //Delete student

                            System.out.println("\nEnter Student's ID(Format: xx####): ");
                            Id = scanner.nextLine();
                            if(valenceCollege.checkIdDuplicates(Id) == true) {
                                valenceCollege.deleteStudent(Id);
                                System.out.println("Student Successfully Deleted!");
                                if(valenceCollege.checkUGStudent(Id)) {
                                    valenceCollege.removeUGStudent(Id);
                                }
                                if(valenceCollege.checkMSStudent(Id)) {
                                    valenceCollege.removeMSStudent(Id);
                                }
                                if(valenceCollege.checkPhDStudent(Id)) {
                                    valenceCollege.removePhDStudent(Id);
                                }
                            }
                            else if(valenceCollege.checkIdDuplicates(Id) == false) {
                                System.out.println("Student not found...");
                            }
                            break;//end of case B
//____________________________________________________________________________________________________________________________________

                            case "C" : //Print fee invoice using student id

                            System.out.println("\nEnter Student's ID(Format: xx####): ");
                            Id = scanner.nextLine();
                            if(valenceCollege.searchById(Id)) {
                                System.out.println("");
                                valenceCollege.printInvoice(Id);
                            }
                            else System.out.println("Student not found...");
                            
                            break;//end of case C

                            case "D" : //Print list of students
                            System.out.println("PhD Students\n________________________");
                            System.out.println(valenceCollege.getPhDStudents());
                            System.out.println("MS Students\n________________________");
                            System.out.println(valenceCollege.getMSStudents());
                            System.out.println("Undergraduate Students\n________________________");
                            System.out.println(valenceCollege.getUGStudents());
                            break;
                        }
                        subSelection = SMMenu();

                    }
                    break;//end of case D
//____________________________________________________________________________________________________________________________________
                case "2" :
                    subSelection = CMMenu();
                    while(subSelection.compareTo("X") != 0) {
                        
                        switch(subSelection) {
                            
                            case "X": //back to main menu
                            
                                break;//end of case X
//____________________________________________________________________________________________________________________________________
                            case "A" : //Search for a class or lab using class/lab number

                                System.out.println("Enter the Class/Lab Number: ");

                                try{
                                    int crn = scanner.nextInt();
                                    scanner = new Scanner(System.in);
                                    if(courses.searchCrn(crn)) {
                                        System.out.println("Course/Lab has been found!");
                                    }
                                    else System.out.println("Course/Lab not found...");
                                } catch (InputMismatchException ime) {
                                    System.out.println("Invalid input...");
                                }
                                break; //end of case A
//____________________________________________________________________________________________________________________________________
                            case "B" : //Delete a lecture, lectures with lab can't be deleted

                                //first find if lecture exists *Create new method to find lectures only*
                                //then check if lecture has labs
                                //then search for course in file and delete the line
                                //then remove from courses
                                System.out.println("Enter the Class Number: ");
                                int crn = scanner.nextInt();
                                scanner = new Scanner(System.in);
                                
                                if(courses.searchCourses(crn) && courses.checkLabs(crn) == false) {

                                    fileScanner = null;
                                    try {
                                        fileScanner = new Scanner(file);
                                    } catch (FileNotFoundException e) {
                                        System.out.println("File not found...");
                                    }

                                    line = "";
                                    items = null;
                                    int deleteLine = 1;

                                    while(fileScanner.hasNextLine()) {
                                        line = fileScanner.nextLine();
                                        items = line.split(",");

                                        if(Integer.parseInt(items[0]) == crn) {
                                            break;
                                        }

                                        deleteLine++;
                                    }

                                    fileScanner.close();
                                    String filepath = "lect.txt";
                                    String tempFile = "temp.txt";
                                    File oldFile = new File(filepath);
                                    File newFile = new File(tempFile);
                                    //search for line of crn and make that deleteLine
                                    //or make array of each line and if crn matches then skip line while writing

                                    int Line = 0;
                                    String currentline;

                                    try{
                                        FileWriter fw = new FileWriter(tempFile, true);
                                        BufferedWriter bw = new BufferedWriter(fw);
                                        PrintWriter pw = new PrintWriter(bw);

                                        FileReader fr = new FileReader(filepath);
                                        BufferedReader br = new BufferedReader(fr);

                                        while ((currentline = br.readLine()) != null) {
                                            Line++;

                                            if(deleteLine != Line) {
                                                pw.println(currentline);
                                            }
                                        }

                                        pw.flush();
                                        pw.close();
                                        fr.close();
                                        br.close();
                                        bw.close();
                                        fw.close();

                                        Path p = Paths.get(filepath);
                                        Files.delete(p);
                                        File dump = new File(filepath);
                                        newFile.renameTo(dump);

                                        courses.removeCourse(crn);

                                        System.out.println("Course successfully deleted!");
                                    } catch(Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else {
                                    System.out.println("Sorry, lecture not found or cannot be deleted...");
                                }
                                break; //end of case B
//____________________________________________________________________________________________________________________________________
                            case "C" : //Add a lab to a class, only to those with existing labs
                                
                                System.out.println("Enter the Lecture Number to add a Lab to: ");
                                int CRN = scanner.nextInt();
                                scanner = new Scanner(System.in);

                                if(courses.checkLabs(CRN) == true) {

                                    System.out.println("Input 5 Digit Lab Number(#####): ");
                                    int labCrn = scanner.nextInt();
                                    scanner = new Scanner(System.in);

                                    System.out.println("Input Location(XXX-###): ");
                                    String location = scanner.nextLine();

                                    fileScanner = null;
                                    try {
                                        fileScanner = new Scanner(file);
                                    } catch (FileNotFoundException e) {
                                        System.out.println("File not found...");
                                    }

                                    line = "";
                                    items = null;
                                    int printLine = 1;

                                    while(fileScanner.hasNextLine()) {
                                        line = fileScanner.nextLine();
                                        items = line.split(",");

                                        if(Integer.parseInt(items[0]) == CRN) {
                                            break;
                                        }

                                        printLine++;
                                    }

                                    fileScanner.close();

                                    String filepath = "lect.txt";
                                    String tempFile = "temp.txt";
                                    File oldFile = new File(filepath);
                                    File newFile = new File(tempFile);
                                    //search for line of crn and make that deleteLine
                                    //or make array of each line and if crn matches then skip line while writing

                                    int Line = 0;
                                    String currentline;

                                    try{
                                        FileWriter fw = new FileWriter(tempFile, true);
                                        BufferedWriter bw = new BufferedWriter(fw);
                                        PrintWriter pw = new PrintWriter(bw);

                                        FileReader fr = new FileReader(filepath);
                                        BufferedReader br = new BufferedReader(fr);

                                        while ((currentline = br.readLine()) != null) {
                                            Line++;
                                            pw.println(currentline);
                                            if(printLine == Line) {
                                                pw.println(labCrn + "," + location);
                                            }
                                        }

                                        pw.flush();
                                        pw.close();
                                        fr.close();
                                        br.close();
                                        bw.close();
                                        fw.close();

                                        Path p = Paths.get(filepath);
                                        Files.delete(p);
                                        File dump = new File(filepath);
                                        newFile.renameTo(dump);

                                        courses.addLab(CRN, labCrn, location);
                                    } catch(Exception e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                                else {
                                    System.out.println("Course not found or does not have labs...");
                                }

                                break; //end of case C
                        }
                        subSelection = CMMenu();

                    }
                    break; //end of case 2

        }
        selection = mainMenu();
    }
    scanner.close();
    }
}
//__________________________________________________________________________________________
class College {
    private ArrayList<Student> list;
    private ArrayList<UndergraduateStudent> UGStudents;
    private ArrayList<MsStudent> MSStudents;
    private ArrayList<PhdStudent> PhDStudents;

    public College () {
        list = new ArrayList<Student>();
        UGStudents = new ArrayList<UndergraduateStudent>();
        MSStudents = new ArrayList<MsStudent>();
        PhDStudents = new ArrayList<PhdStudent>();
    }

    //method to enroll student
    public void enrollStudent(Student e) {
        list.add(e);
    }

    public void deleteStudent(String e) {
        list.remove(getIndex(e));
    }

    public void addUGStudent(UndergraduateStudent e){
        UGStudents.add(e);
    }
    public void addMSStudent(MsStudent e){
        MSStudents.add(e);
    }
    public void addPhDStudent(PhdStudent e){
        PhDStudents.add(e);
    }

    public void removeUGStudent(String e) {
        UGStudents.remove(getUGIndex(e));
    }
    public void removeMSStudent(String e) {
        MSStudents.remove(getMSIndex(e));
    }
    public void removePhDStudent(String e) {
        PhDStudents.remove(getPhDIndex(e));
    }

    public Boolean checkUGStudent(String id) {
        Boolean output = false;
        for(UndergraduateStudent e : UGStudents){
            if(e.getId().compareToIgnoreCase(id) == 0) output = true; //returns true if id found on list
        }
        return output;
    }
    public Boolean checkMSStudent(String id) {
        Boolean output = false;
        for(MsStudent e : MSStudents){
            if(e.getId().compareToIgnoreCase(id) == 0) output = true; //returns true if id found on list
        }
        return output;
    }
    public Boolean checkPhDStudent(String id) {
        Boolean output = false;
        for(PhdStudent e : PhDStudents){
            if(e.getId().compareToIgnoreCase(id) == 0) output = true; //returns true if id found on list
        }
        return output;
    }

    //method to search if a student is on the list using their id
    public boolean searchById(String Id) {
        for(Student e : list){
            if(e.getId().compareToIgnoreCase(Id) == 0) return true; //returns true if id found on list
        }
        return false;
    }

    //method to get the index for a specific student
    public int getIndex(String id) {
        
        int index = -1;
        int size = list.size();

        for (int i = 0 ; i < size ; i++) {

            if (list.get(i).getId().compareToIgnoreCase(id) == 0){
                index = i;
                break;
            } // end of if

        }//end of for

        return index;
    }
    public int getUGIndex(String id) {
        
        int index = -1;
        int size = UGStudents.size();

        for (int i = 0 ; i < size ; i++) {

            if (UGStudents.get(i).getId().compareToIgnoreCase(id) == 0){
                index = i;
                break;
            } // end of if

        }//end of for

        return index;
    }
    public int getMSIndex(String id) {
        
        int index = -1;
        int size = MSStudents.size();

        for (int i = 0 ; i < size ; i++) {

            if (MSStudents.get(i).getId().compareToIgnoreCase(id) == 0){
                index = i;
                break;
            } // end of if

        }//end of for

        return index;
    }
    public int getPhDIndex(String id) {
        
        int index = -1;
        int size = PhDStudents.size();

        for (int i = 0 ; i < size ; i++) {

            if (PhDStudents.get(i).getId().compareToIgnoreCase(id) == 0){
                index = i;
                break;
            } // end of if

        }//end of for

        return index;
    }

    public void printInvoice(String studentId) {
        int index = getIndex(studentId);

        list.get(index).printInvoice();
    }


    public Boolean checkId(String Id) {
        char[] arr = Id.toCharArray();
        Boolean output = false;
        if(arr.length > 6) {
            
        }
        try{
            if(arr.length > 6 || arr.length <6) {
                throw new IdException();
            }
            else if(Character.isLetter(arr[0]) && Character.isLetter(arr[1]) && Character.isDigit(arr[2]) && Character.isDigit(arr[3]) 
            && Character.isDigit(arr[4]) && Character.isDigit(arr[5])) { /*chars 0,1 letters, chars 2-5 integers */ //isDigit, isLetter
                output = true;
            }
            else if(searchById(Id)) {
                throw new IdException();
            }
            else 
                throw new IdException();
        } catch (IdException e){
        }
        
        return output;
    }

    public Boolean checkIdDuplicates(String Id) {
        char[] arr = Id.toCharArray();
        Boolean output = false;
        if(arr.length > 6) {
            
        }
        try{
            if(arr.length > 6 || arr.length <6) {
                throw new IdException();
            }
            else if(Character.isLetter(arr[0]) && Character.isLetter(arr[1]) && Character.isDigit(arr[2]) && Character.isDigit(arr[3]) 
            && Character.isDigit(arr[4]) && Character.isDigit(arr[5]) && searchById(Id)) { /*chars 0,1 letters, chars 2-5 integers */ //isDigit, isLetter
                output = true;
            }
            else 
                throw new IdException();
        } catch (IdException e){
        }
        
        return output;
    }

    public String returnName(String studentId) {
        int index = getIndex(studentId);
        return list.get(index).getName();
    }
    //____________________________________________
    public ArrayList<Student> getList() {
        return list;
    }
    public ArrayList<MsStudent> getMSStudents() {
        return MSStudents;
    }
    public ArrayList<PhdStudent> getPhDStudents() {
        return PhDStudents;
    }
    public ArrayList<UndergraduateStudent> getUGStudents() {
        return UGStudents;
    }
    //_____________________________________________
    public void setList(ArrayList<Student> list) {
        this.list = list;
    }
    public void setMSStudents(ArrayList<MsStudent> mSStudents) {
        MSStudents = mSStudents;
    }
    public void setPhDStudents(ArrayList<PhdStudent> phDStudents) {
        PhDStudents = phDStudents;
    }
    public void setUGStudents(ArrayList<UndergraduateStudent> uGStudents) {
        UGStudents = uGStudents;
    }
}
//__________________________________________________________________________________________
abstract class Student {

    private String name;
    private String id;
    private double gpa;
    private Boolean inState; 

    public Student(String name, String id, double gpa, Boolean inState){
        this.name = name;
        this.id = id;
        this.gpa = gpa;
        this.inState = inState;
    }

    public Student() {
        name = "";
        id = "";
        gpa = -1;
        inState = null;
    }

    abstract public void printInvoice();

    public String toString() {
        return name;
    }
    //_________________________________________________
    public void setGpa(double gpa) {
        this.gpa = gpa;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setInState(Boolean inState) {
        this.inState = inState;
    }
    public void setName(String name) {
        this.name = name;
    }
    //__________________________________________________
    public double getGpa() {
        return gpa;
    }
    public String getId() {
        return id;
    }
    public Boolean getInState() {
        return inState;
    }  
    public String getName() {
        return name;
    }

}
//_________________________________________________________________________________________
class UndergraduateStudent extends Student {

    private ArrayList<Integer> crns;
    private ArrayList<UGCourse> UGCourses;

    public UndergraduateStudent(ArrayList<UGCourse> UGCourses, String name, String id, double gpa, Boolean inState) {
        super(name, id, gpa, inState);
        crns = new ArrayList<Integer>();
        this.UGCourses = UGCourses;
    }

    @Override
    public void printInvoice() {
        System.out.println("VALENCE COLLEGE");
        System.out.println("ORLANDO FL 10101");
        System.out.println("------------------------\n");

        System.out.println("Fee Invoice Prepared for Student:");
        System.out.println(super.getId() + "-" + super.getName());

        System.out.println("\n1 Credit Hour = $120.25\n");

        System.out.println("CRN\t\tCR_PREFIX\tCR_HOURS");
        for(Integer e : crns) {
            System.out.println(e + "\t\t" + getPrefix(e, UGCourses) + "\t\t" + getCreditHours(e, UGCourses) + "\t\t$" + getPayment(e, UGCourses));
        }

        System.out.println("\n\t\t\t    Health & id fees    $35.0");

        System.out.println("__________________________________________________________________\n");
        if(super.getGpa()>=3.5 && getPaymentNoDiscount(crns, UGCourses) > 500) {
            System.out.println("\t\t\t\t\t\t$ " + getPaymentNoDiscount(crns, UGCourses));
            System.out.println("\t\t\t\t\t       -$ " + getPaymentNoDiscount(crns, UGCourses)*.25+"\n");
        }

        System.out.println("\t\t\t\tTotal Payments\t$" + (getTotalPayment(crns, UGCourses)));
    }

    public void addCrns(int crn) {
        crns.add(crn);
    }

    public void removeCrns(int crn) {
        crns.remove(getIndex(crn));
    }

    public int getIndex(int crn) {
        int index = 0;
        for(int i = 0; i<crns.size(); i++) {
            if(crns.get(i) == crn) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getCreditHours(int crn, ArrayList <UGCourse> UGCourses) {

        for(UGCourse e: UGCourses) {
            if(crn == e.getCrn()) {
                return e.getCreditHours();
            }
        }
        return 0;
    }

    public int getTotalHours(ArrayList<Integer> crns, ArrayList <UGCourse> UGCourses) {
        int totalHours = 0;
        for(int e: crns) {
            totalHours = totalHours + getCreditHours(e, UGCourses);
        }
        return totalHours;
    }

    public String getPrefix(int crn, ArrayList <UGCourse> UGCourses) {
        for(UGCourse e: UGCourses) {
            if(crn == e.getCrn()) {
                return e.getPrefix();
            }
        }
        return null;
    }

    public double getPayment(int crn, ArrayList <UGCourse> UGCourses) {
        double Payment = 0;
        if(super.getInState()) {
            Payment = getCreditHours(crn, UGCourses)*120.25;
        }
        else {
            Payment = getCreditHours(crn, UGCourses)*120.25*2;
        }
        return Payment;
    }

    public double getTotalPayment(ArrayList<Integer> crns, ArrayList <UGCourse> UGCourses) {
        double totalPayment = 0;
        if(super.getInState()) {
            totalPayment = getTotalHours(crns, UGCourses)*120.25 + 35;
        }
        else {
            totalPayment = getTotalHours(crns, UGCourses)*120.25*2 + 35;
        }
        if(super.getGpa()>=3.5 && totalPayment>500) {
            totalPayment = totalPayment*.75;
        }
        return totalPayment;
    }
    public double getPaymentNoDiscount(ArrayList<Integer> crns, ArrayList <UGCourse> UGCourses) {
        double totalPayment = 0;
        if(super.getInState()) {
            totalPayment = getTotalHours(crns, UGCourses)*120.25 + 35;
        }
        else {
            totalPayment = getTotalHours(crns, UGCourses)*120.25*2 + 35;
        }
        return totalPayment;
    }
    //____________________________________________________
    public ArrayList<Integer> getCrns() {
        return crns;
    }
    public ArrayList<UGCourse> getUGCourses() {
        return UGCourses;
    }
    //____________________________________________________
    public void setCrns(ArrayList<Integer> crns) {
        this.crns = crns;
    }
    public void setUGCourses(ArrayList<UGCourse> uGCourses) {
        UGCourses = uGCourses;
    }
}
//_________________________________________________________________________________________
abstract class GraduateStudent extends Student{

    public GraduateStudent(String name, String id, double gpa, Boolean inState) {
        super(name, id, gpa, inState);
    }
}
//___________________________________________________________________________________________
class MsStudent extends GraduateStudent{

    private ArrayList<Integer> crns;
    private ArrayList<GCourse> GCourses;

    public MsStudent(ArrayList<GCourse> GCourses, String name, String id, double gpa, Boolean inState) {
        super(name, id, gpa, inState);
        crns = new ArrayList<Integer>();
        this.GCourses = GCourses;
    }

    @Override
    public void printInvoice() {
        System.out.println("VALENCE COLLEGE");
        System.out.println("ORLANDO FL 10101");
        System.out.println("------------------------\n");

        System.out.println("Fee Invoice Prepared for Student:");
        System.out.println(super.getId() + "-" + super.getName());

        System.out.println("\n1 Credit Hour = $120.25\n");

        System.out.println("CRN\t\tCR_PREFIX\tCR_HOURS");
        for(Integer e : crns) {
            System.out.println(e + "\t\t" + getPrefix(e, GCourses) + "\t\t" + getCreditHours(e, GCourses) + "\t\t$" + getPayment(e, GCourses));
        }

        System.out.println("\n\t\t\t    Health & id fees    $35.0");

        System.out.println("__________________________________________________________________\n");

        System.out.println("\t\t\t\tTotal Payments\t$" + (getTotalPayment(crns, GCourses)));
    }

    public void addCrns(int e) {
        crns.add(e);
    }

    public void removeCrns(int crn) {
        crns.remove(getIndex(crn));
    }

    public int getIndex(int crn) {
        int index = 0;
        for(int i = 0; i<crns.size(); i++) {
            if(crns.get(i) == crn) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getCreditHours(int crn, ArrayList <GCourse> GCourses) {

        for(GCourse e: GCourses) {
            if(crn == e.getCrn()) {
                return e.getCreditHours();
            }
        }
        return 0;
    }

    public int getTotalHours(ArrayList<Integer> crns, ArrayList <GCourse> GCourses) {
        int totalHours = 0;
        for(int e: crns) {
            totalHours = totalHours + getCreditHours(e, GCourses);
        }
        return totalHours;
    }

    public String getPrefix(int crn, ArrayList <GCourse> GCourses) {

        for(GCourse e: GCourses) {
            if(crn == e.getCrn()) {
                return e.getPrefix();
            }
        }
        return null;
    }

    public double getPayment(int crn, ArrayList <GCourse> GCourses) {
        double Payment = getCreditHours(crn, GCourses)*300;
        return Payment;
    }

    public double getTotalPayment(ArrayList<Integer> crns, ArrayList <GCourse> GCourses) {
        double totalPayment = getTotalHours(crns, GCourses)*300 + 35;
        return totalPayment;
    }
    //_________________________________________
    public ArrayList<Integer> getCrns() {
        return crns;
    }
    public ArrayList<GCourse> getGCourses() {
        return GCourses;
    }
    //_________________________________________
    public void setCrns(ArrayList<Integer> crns) {
        this.crns = crns;
    }
    public void setGCourses(ArrayList<GCourse> gCourses) {
        GCourses = gCourses;
    }

}
//____________________________________________________________________________________________
class PhdStudent extends GraduateStudent{

    String advisor;
    String researchSub;
    private ArrayList<Integer> labCrns;


    public PhdStudent(String advisor, String researchSub, String name, String id, double gpa, Boolean inState) {
        super(name, id, gpa, inState);
        this.advisor = advisor;
        this.researchSub = researchSub;
        labCrns = new ArrayList<Integer>();
    }

    @Override
    public void printInvoice(){
        System.out.println("VALENCE COLLEGE");
        System.out.println("ORLANDO FL 10101");
        System.out.println("------------------------\n");

        System.out.println("Fee Invoice Prepared for Student:");
        System.out.println(super.getId() + "-" + super.getName());

        System.out.println("\n1 Credit Hour = $120.25\n");

        System.out.println("RESEARCH");
        System.out.println(researchSub + "\t\t\t\t$" + (getTotalPayment()-35));

        System.out.println("\n\t\t\t    Health & id fees    $35.0");

        System.out.println("__________________________________________________________________\n");

        System.out.println("\t\t\t\tTotal Payments\t$" + (getTotalPayment()));
    }

    public void addLabCrns(Integer e) {
        labCrns.add(e);
    }

    public void removeCrns(int crn) {
        labCrns.remove(getIndex(crn));
    }

    public int getIndex(int crn) {
        int index = 0;
        for(int i = 0; i<labCrns.size(); i++) {
            if(labCrns.get(i) == crn) {
                index = i;
                break;
            }
        }
        return index;
    }

    public double getTotalPayment() {
        double totalPayment = 700;
        if(labCrns.size() == 2) {
            totalPayment = 700/2;
        }
        if(labCrns.size() >= 3) {
            totalPayment = 0;
        }
        totalPayment = totalPayment + 35;
        return totalPayment;
    }

    //_________________________________________________
    public String getAdvisor() {
        return advisor;
    }
    public ArrayList<Integer> getLabCrns() {
        return labCrns;
    }
    public String getResearchSub() {
        return researchSub;
    }
    //__________________________________________________
    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }
    public void setLabCrns(ArrayList<Integer> labCrns) {
        this.labCrns = labCrns;
    }
    public void setResearchSub(String researchSub) {
        this.researchSub = researchSub;
    }
}
//_____________________________________________________________________________________
class Course{

    private ArrayList<UGCourse> UGCourses;
    private ArrayList<GCourse> GCourses;

    public Course (ArrayList<UGCourse> UGCourses, ArrayList<GCourse> GCourses) {
        this.GCourses = GCourses;
        this.UGCourses = UGCourses;
    }

    public String toString() {
        return "Under Graduate Courses\n__________________________\n" + UGCourses + "\n\nGraduate Courses\n__________________________\n" + GCourses;
    }

    public void removeCourse(int crn) {
        if(checkLabs(crn) == false) {
            for(UGCourse e : UGCourses){
                if(e.getCrn() == crn) {
                    UGCourses.remove(e);
                }
            }
            for(GCourse e : GCourses){
                if(e.getCrn() == crn)  {
                    GCourses.remove(e);
                }
            }
        }
    }

    public void addLab(int crn, int labCrn, String location) {
        if(checkLabs(crn) == true) {
            for(UGCourse e : UGCourses){
                if(e.getCrn() == crn) {
                    e.addLabs(new Labs(labCrn, location));
                }
            }
            for(GCourse e : GCourses){
                if(e.getCrn() == crn)  {
                    e.addLabs(new Labs(labCrn, location));
                }
            }
        }
    }

    public int getUGIndex (int crn) {
        int index = -1;
        int size = UGCourses.size();

        for (int i = 0 ; i < size ; i++) {

            if (UGCourses.get(i).getCrn() == crn){
                index = i;
                break;
            } // end of if

        }//end of for
        return index;
    }
    public int getGIndex (int crn) {
        int index = -1;
        int size = GCourses.size();

        for (int i = 0 ; i < size ; i++) {

            if (GCourses.get(i).getCrn() == crn){
                index = i;
                break;
            } // end of if

        }//end of for
        return index;
    }
    public Boolean searchCrn(int crn) {
        Boolean output = false;
        for(UGCourse e : UGCourses){
            if(e.getCrn() == crn) output = true; //returns true if id found on list
        }
        for(GCourse e : GCourses){
            if(e.getCrn() == crn) output = true; //returns true if id found on list
        }
        for(UGCourse e : UGCourses){
            if(e.searchLabs(crn)) output = true; //returns true if id found on list
        }
        for(GCourse e : GCourses){
            if(e.searchLabs(crn)) output = true; //returns true if id found on list
        }
        return output;
    }

    public Boolean searchCourses(int crn) {
        Boolean output = false;
        for(UGCourse e : UGCourses){
            if(e.getCrn() == crn) output = true; //returns true if id found on list
        }
        for(GCourse e : GCourses){
            if(e.getCrn() == crn) output = true; //returns true if id found on list
        }
        return output;
    }

    public Boolean checkLabs(int crn) {
        //find course then check if it has labs
        Boolean output = false;
        for(UGCourse e : UGCourses){
            if(e.getCrn() == crn) {
                if(e.getLabs().size() == 0) {
                    output = false;
                }
                else output = true;
            } 
        }
        for(GCourse e : GCourses){
            if(e.getCrn() == crn) {
                if(e.getLabs().size() == 0) {
                    output = false;
                }
                else output = true;
            } 
        }
        return output; //returns false if it has no labs
    }
    //______________________________________
    public ArrayList<GCourse> getGCourses() {
        return GCourses;
    }
    public ArrayList<UGCourse> getUGCourses() {
        return UGCourses;
    }
    //_______________________________________
    public void setGCourses(ArrayList<GCourse> gCourses) {
        GCourses = gCourses;
    }
    public void setUGCourses(ArrayList<UGCourse> uGCourses) {
        UGCourses = uGCourses;
    }
}
//___________________________________________________________________________________
class UGCourse {

    private int crn;
    private String prefix;
    private String title;
    private String modality;
    private String location;
    private ArrayList<Labs> labs;
    private int creditHours;

    public UGCourse (int crn, String prefix, String title, String modality, String location, int creditHours){
        this.crn = crn;
        this.prefix = prefix;
        this.title = title;
        this.modality = modality;
        this.location = location;
        labs = new ArrayList<Labs>();
        this.creditHours = creditHours;
    }

    @Override
    public String toString () {
        return "\n" + crn + ", " + prefix + ", " + title + ", " + modality + ", " + location + ", " + creditHours + labs;
    }

    public void printLabs() {
        for(Labs e : labs) {
            System.out.println("\t" + e.getCrn() + ", " + e.getLocation());
        }
    }

    public void addLabs(Labs e) {
        labs.add(e);
    }
    public Boolean searchLabs(int crn) {
        for(Labs e : labs){
            if(e.getCrn() == crn) return true; //returns true if id found on list
        }
        return false;
    }

    //___________________________________________

    public int getCreditHours() {
        return creditHours;
    }
    public int getCrn() {
        return crn;
    }
    public ArrayList<Labs> getLabs() {
        return labs;
    }
    public String getLocation() {
        return location;
    }   
    public String getModality() {
        return modality;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getTitle() {
        return title;
    }
    //____________________________________________
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    public void setCrn(int crn) {
        this.crn = crn;
    }
    public void setLabs(ArrayList<Labs> labs) {
        this.labs = labs;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setModality(String modality) {
        this.modality = modality;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public void setTitle(String title) {
        this.title = title;
    }


}
//____________________________________________________________________________________________
class GCourse {

    private int crn;
    private String prefix;
    private String title;
    private String modality;
    private String location;
    private ArrayList<Labs> labs;
    private int creditHours;

    public GCourse (int crn, String prefix, String title, String modality, String location, int creditHours){
        this.crn = crn;
        this.prefix = prefix;
        this.title = title;
        this.modality = modality;
        this.location = location;
        labs = new ArrayList<Labs>();
        this.creditHours = creditHours;
    }

    @Override
    public String toString () {
        return "\n" + crn + ", " + prefix + ", " + title + ", " + modality + ", " + location + ", " + creditHours + labs;
    }

    public void printLabs() {
        for(Labs e : labs) {
            System.out.println("\t" + e.getCrn() + ", " + e.getLocation());
        }
    }

    public void addLabs(Labs e) {
        labs.add(e);
    }

    public Boolean searchLabs(int crn) {
        for(Labs e : labs){
            if(e.getCrn() == crn) return true; //returns true if id found on list
        }
        return false;
    }

    //___________________________________________

    public int getCreditHours() {
        return creditHours;
    }
    public int getCrn() {
        return crn;
    }
    public ArrayList<Labs> getLabs() {
        return labs;
    }
    public String getLocation() {
        return location;
    }   
    public String getModality() {
        return modality;
    }
    public String getPrefix() {
        return prefix;
    }
    public String getTitle() {
        return title;
    }
    //____________________________________________
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    public void setCrn(int crn) {
        this.crn = crn;
    }
    public void setLabs(ArrayList<Labs> labs) {
        this.labs = labs;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public void setModality(String modality) {
        this.modality = modality;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public void setTitle(String title) {
        this.title = title;
    }

}
//___________________________________________________________________________________________
class Labs{

    private int crn;
    private String location;

    public Labs(int crn, String location) {
        this.crn = crn;
        this.location = location;
    }

    public String toString() {
        return "\n\t" + crn + ", " + location;
    }

    //____________________________________________
    public int getCrn() {
        return crn;
    }
    public String getLocation() {
        return location;
    }
    //_____________________________________________
    public void setCrn(int crn) {
        this.crn = crn;
    }
    public void setLocation(String location) {
        this.location = location;
    }

}