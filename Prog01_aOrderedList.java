package prog01_aorderedlist;

/**
* CSC 1351 Programming Project No 001
* Section 002
* @author Khánh Giang "Gerald" Lê
* @since 10/03/2024
* @Compiled on JDK 20
*/

import java.util.Scanner;
import java.util.Arrays;
import java.io.*;
import java.io.FileNotFoundException;


public class Prog01_aOrderedList {
        /**
        * main method includes part of:
        *   Step 2: Input Car Data
        *   Step 4: Populating aOrderedList
        *   Step 7: Operational Script
        *   Step 8: Extend our aOrderedList class
        * 
        * CSC 1351 Programming Project No 001
        * Section 002
        * @author Khánh Giang "Gerald" Lê
        * @param args
        * @since 15/03/2024
        */
	public static void main(String[] args) {
		Scanner in;
                // Part of Step 2: Input Car Data
                // method in main that prompts the user for the filename of a text input file
		while(true){ 
			try{
				in = GetInputFile("Enter input filename: ");              //calls the method GetInputFile below
				break;
			}
			catch(FileNotFoundException e){
				String message = e.getMessage();
				for(int i = 0; i < message.length(); i++){
					if(message.charAt(i) == '('){
						message = message.substring(0,i-1);       //reads the name of the incorrect file to be displayed below
						break;
					}
				}
				System.out.print("File specified <"+message+"> does not exist. Would you like to continue? <Y/N>  ");   //notify the user that the value entered is incorrect
                                                                                                                                        //prompts the user to reenter a corrected value
				Scanner confirm = new Scanner(System.in);
				String input = confirm.next();
				if(input.equalsIgnoreCase("N")){                        //terminates the program and prints a message for the user
					System.out.println("\nProgram terminated by user.\n");  
					System.exit(0);
				}
				else if(!input.equalsIgnoreCase("Y")){                  //accomodating for cases of typos and return the user to the initial input stage
					System.out.println("\nUnclear command.\n");
				}
			}
		}
                
                //Part of Step 4: Populating aOrderedList
		aOrderedList list = new aOrderedList(); //object aOrderedList added
                
                //Part of Step 7: Operational Script
                //Part of  Step 8: Extend our aOrderedList class
		while(in.hasNextLine()){                                        //reads from the input list to see if the program can continue
			String carDescriptor = in.nextLine();                   //if so, get the details from the next input       
			String[] fields = carDescriptor.split(",");        //split the fields of input with a comma
			if(fields[0].equals("A")){                       //only adding that input with "A" at the first (0) field
				Car c = new Car(fields[1], Integer.parseInt(fields[2]), Integer.parseInt(fields[3])); 
                                //extract the fields of input: 1st field is a string, and 2nd and 3rd is a number
                                //therefore Integer.parseInt must be used to extract the value from the initial input string
				list.add(c);                            //add the object c with 3 separate parameters into the list
			}
                        
                        //Explicit Delete Input
                        //for anything not containing "A"
			else{
				if(fields.length == 2){                                                 
					int index = Integer.parseInt(fields[1]);                            //reads the year value from the 2nd column
					list.remove(index);                                                 //then remove the corresponding indexed object
				}
                                else {
					String make = fields[1];                                            //reads the make value from the 2nd column
					int year = Integer.parseInt(fields[2]);                             //reads the year value from the 3rd column
					for(int i = 0; i < list.size(); i++){
						Car c = (Car)list.get(i);
						if(make.equals(c.getMake()) && year == c.getYear()){ //the corresponding object will be deleted only if the make and year value matches
							list.remove(i);
							break; 
						}
					}
				}
                                
			}
                        //everything else is skipped and ignored
		}
		in.close();
                //Part of Step 4: Populating aOrderedList
		PrintWriter pw;
		while(true){
			try{
				pw = GetOutputFile("Enter output filename: ");              //calls the method GetOutputFile below
				break;
			}
			catch(FileNotFoundException e){
				String message = e.getMessage();
				for(int i = 0; i < message.length(); i++){
					if(message.charAt(i) == '('){
						message = message.substring(0,i-1);         //reads the name of the incorrect file to be displayed below
						break;
					}
				}
				System.out.print("Filename entered <"+message+"> is not valid. Please press Y to type a different name, or press N to exit the program. <Y/N>  "); 
                                //notify the user that the value entered is invalid
                                //prompts the user to reenter a valid value
                                
				Scanner confirm = new Scanner(System.in);
				String input = confirm.next();
				if(input.equalsIgnoreCase("N")){                        //terminates the program
					System.out.println("\nProgram terminated by user.\n");  
					System.exit(0);
				}
				else if(!input.equalsIgnoreCase("Y")){                  //accomodating for cases of typos and return the user to the previous input stage
					System.out.println("\nUnclear command.\n");
				}
			}
		}
		pw.println(list.toString());              //print the result of the aOrderedList toString method to the file below
		pw.flush();                                 //flush the writer method from whatever is written above
		pw.close();                                 //close the writer method
                
                //Part of Step 7: Operational Script
		System.out.println("Number of cars:\t"+list.size()+"\n");   //modified to output the content of the array in a special format 
                                                                            //outputs the size of the array
		for(int i = 0; i < list.size(); i++){                       //repeat until i reaches the end of list
			Car c = (Car)list.get(i);
			int price = c.getPrice();
			String moneyFormat = "";
			int count = 0;
                        
                        //inverse division to make the money separated with a comma
			while(price != 0){                                      
				count++;
				String lastDigit = String.valueOf(price % 10);  //the remains of price divided by 10 to string
				price /= 10;                                    //divides the price by 10
				moneyFormat = lastDigit + moneyFormat;          //adds the lastDigit (s) together
				if(count % 3 == 0){                             //when count gets to 3
					moneyFormat = "," + moneyFormat;        //adds a comma in front of the numbers
				}
			}
                        moneyFormat = "$" + moneyFormat;                         //adds dollar sign to string moneyFormat
                        //Outputs the formatted content of the array as follows:
                        //  Make:   (string)
                        //  Year:   (decimal integer)
                        //  Price:  (string)
			System.out.printf("Make:\t%12s\nYear:\t%12d\nPrice:\t%12s\n\n", c.getMake(), c.getYear(), moneyFormat);
                        
		}
	}
//Part of Step 2: Input Car Data
	public static Scanner GetInputFile(String UserPrompt) throws FileNotFoundException{
		System.out.print(UserPrompt);           //method to print commanding instructions for user
		Scanner in = new Scanner(System.in); 
		String input = in.nextLine();             //reads from the user's input
		File inputFile = new File(input); 
		return new Scanner(inputFile);      //return Scanner object ready for input
	}
        
//Part of Step 4: Populating aOrderedList
	public static PrintWriter GetOutputFile(String UserPrompt) throws FileNotFoundException{
		System.out.print(UserPrompt);           //method to print commanding instructions for user
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();             //reads from the user's input
		File outputFile = new File(input);
		return new PrintWriter(outputFile);   //return printwrite
	}
}
/**
* Step 3: aOrderedList class
* Includes part of:
*   Step 5: Managing Array Size
*   Step 6: Completion of aOrderedList Class
*   Step 8: Extend aOrderedList class
*   
* CSC 1351 Programming Project No 001
* Section 002
* @author Khánh Giang "Gerald" Lê
* @since 16/03/2024
*/
class aOrderedList {
    
        //Private members:
	final int SIZEINCREMENTS = 20;  //size of increments for increasing ordered list
	
	private Comparable[] oList;     //the ordered list
                                        //changed to be an array of any type of objects
	private int listSize;           //the size of the ordered list
	private int numObjects;         //number of objects in the ordered list
	
	private int curr;               //Part of Step 8 - Private Members:
                                        //index of current element accessed via iterator methods
        
        //Public members:
	public aOrderedList(){                      //constructor that:
		numObjects = 0;                     //sets numObjects to 0
		listSize = SIZEINCREMENTS;          //sets listSize to the value of SIZEINCREMENTS
		oList = new Comparable[listSize];   //instantiates the array oList to an array of size SIZEINCREMENTS
	}
	
        public String toString(){ 
		String result = "";                         //starts off as blank
		for(int i = 0; i < numObjects; i++){
			if(i > 0){
				result += ",";              //separate each results with a comma each time a new result is added
			}
			Comparable c = oList[i];            //calls the toString of Car objects in the oList array to construct
                                                            //the return value of the method
			result += "[" + c.toString() + "]"; //separated by commas, delimited by brackets
		}
		return result;                              //returns toString values of the list objects
	}
        
	public void add(Comparable newObject){              //Changed from Car newCar to Comparable newObject
                                                            //as Part of Step 8: Extend our aOrderedList class
                                                            //to accommodate any types of objects chosen
                                                            //Changed this public method to newObject 
                                                            //add object to the intended location on the list
		//moderate list size to not error
                //Part of Step 5: Managing Array Size
		if(numObjects == listSize){
			listSize += SIZEINCREMENTS;                                             //+20 to listSize
			Comparable[] newArray = Arrays.copyOf(oList, listSize); //Creates a temporary newArray that has 20 more slots than the previous
			oList = newArray;                                                       //and returns that newArray to oList
		}
                //Binary Search algorithm to insert the object to the correct position
		int start = 0;
		int end = numObjects;                                   //search is initiated from the beginning of the list to the end of the list, or numObjects
		int mid = 0;
		while(end > start){                                     //repeated until index to add is found
			mid = ((end - start) / 2) + start;              //assign value for mid point by dividing (endpoint value minus starting point value) by 2, 
                                                                        //then add starting point value
			Comparable c = oList[mid];
			int result = newObject.compareTo(c);
			if(result < 0){
				end = mid;                              //assign the mid value as an endpoint
			}
			else if(result > 0){                            //assign mid value +1 as a starting point
				start = mid+1;

			}
			else{
				add(newObject, mid);                //add newObject to the index point of mid value
				return;
			}
		}
		add(newObject, start);                              //if all else fails, add newObject to the index point of starting value
	}
	
	//Part of Step 6: Completion of aOrderedList Class
	public int size(){
		return numObjects;      //returns the number of elements in this list
	}
	
	public Comparable get(int index){   //Changed from Car get to Comparable get
                                            //as Part of Step 8: Extend our aOrderedList class
                                            //to accommodate any types of objects chosen
		return oList[index];        //returns the element at the specified position in this list.
	}
	
	public void remove(int index){      //removes the last element returned by the next() method
		for(int i = index; i < numObjects-1; i++){
			oList[i] = oList[i+1];
		}
		numObjects--;
	}
	//Part of Step 8: Extend our aOrderedList class
        //Adding public methods to manage an iterator on the array
	public void reset(){                //resets iterator parameters so that the “next” element 
                                            //is the first element in the array, if any
		curr = 0;
	}
	
	public Comparable next(){           //returns the next element in the iteration and increments the iterator parameters
		curr++;
                return oList[curr];
	}
	
	public boolean hasNext(){           //returns true if the iteration has more elements to iterate through
		return curr + 1 < numObjects;
	}
	
	public void remove(){               //removes the last element returned by the next() method
		this.remove(curr);
		curr = 0;
	}
	//method to read object and add them to the indexed position on oList
	private void add(Comparable newObject, int index){              
		for(int i = numObjects; i > index; i--){            //ensuring objects are added to the amount of numObjects
			oList[i] = oList[i-1];                      //pushes every oList value down by 1
		}
		oList[index] = newObject;                           //adds the new object value to the new index number
		numObjects++;                                       //increasing the object counter by 1
	}
}

/**
* Step 1: Establishing Car Class.
* Includes part of Step 8: Extend our aOrderedList class
* 
* CSC 1351 Programming Project No 001
* Section 002
* @author Khánh Giang "Gerald" Lê
* @since 15/03/2024
*/

class Car implements Comparable<Car> {                  //adds Comparable interface in the class description
        //Private members
	private String make;
	private int year;
	private int price;
	
        //Public methods
	public Car(String make, int year, int price){   //constructor that:
		this.make = make;                       //sets the value of make
		this.year = year;                       //sets the value of year
		this.price = price;                     //sets the value of price
	}

	public String getMake() { return make; }    //returns the value of make
	public int getYear() { return year; }       //returns the value of year
	public int getPrice() { return price; }     //returns the value of price
	
	public int compareTo(Car other){            //comparing cars based on make, then year
		int result = make.compareTo(other.getMake());
		if(result != 0){
			return result;
		}
		return year - other.getYear();
	}
	
	public String toString(){                   //returns the make, year and price with some texts attached
		return String.format("Make: %s, Year: %d, Price: %d;", make, year, price);
	}
}
