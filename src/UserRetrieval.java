import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class UserRetrieval {

	private ArrayList<String> id_list;
	private ArrayList<String> in_ektron;
	
	public UserRetrieval(){
		id_list = new ArrayList<String>();
		in_ektron = new ArrayList<String>();
	}
	
	
    /*Parse's through text and finds the USER IDs and adds them to a list*/
	public void getID(File file) throws IOException {

		String line;
		BufferedReader input = null;

		try{
			input = new BufferedReader(new FileReader(file));

			while((line = input.readLine()) != null){

				String[] wordArray = line.split("[ ]+");
				if(wordArray.length > 0){

					String word = wordArray[0].trim();
					if(!word.isEmpty()){

						if(word.equals("UserID:")){

							String id = wordArray[1];

							if(!id_list.contains(id)){
								id_list.add(id);
							}
						}
					}
				}
			}
		}

		catch(FileNotFoundException e){

			System.out.println("File Not Found");

		} catch (IOException e) {

			System.out.println("File is not readable");

		}finally{

			input.close();

		}
	}
	
	public void compare(File file) throws IOException{
		String line;
		BufferedReader input = null;

		try{
			input = new BufferedReader(new FileReader(file));

			while((line = input.readLine()) != null){


				String word = line.trim().toUpperCase();
				if(!word.isEmpty()){
							
					if(id_list.contains(word)){
						in_ektron.add(word);
						id_list.remove(word);
					}

				}
			}

		}

		catch(FileNotFoundException e){

			System.out.println("File Not Found");

		} catch (IOException e) {

			System.out.println("File is not readable");

		}finally{

			input.close();

		}
	}

	/* Outputs list of User id's onto a new file */
	public void Output(File inEktron) throws IOException{

		PrintWriter writer = null;

		try{

			FileWriter output = new FileWriter(inEktron);
			
			writer = new PrintWriter(output);


			for(int i = 0; i < in_ektron.size(); i++){
				writer.println(in_ektron.get(i));
				writer.println();
			}
			writer.println("NOT IN EKTRON");
			for(int i = 0; i < id_list.size(); i++){
				writer.println(id_list.get(i));
				writer.println();
			}

		}finally{
			writer.close();
		}
	}

	public static void main(String[] args) throws IOException {

		File file = new File("email.txt");
		File compareFile = new File("allusers.txt");
		File newfile = new File("ban-report.txt");
		UserRetrieval test = new UserRetrieval();
		test.getID(file);
		test.compare(compareFile);
		test.Output(newfile);

	}
}