/**
 * This class takes a pathname and name of Java Type and counts the number 
 * of declarations of that type within that directory and counts the number 
 * of references to each occurrence of that type within that directory.
 * 
 * @author 
 *
 */
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class TypeCounter {
	
	
	/***
 	* @param directoryPath
 	* @return an array of strings with the paths of .java files in directoryPath
 	* @throws FileNotFoundException
 	* @throws IOException
 	* @author Zahra Al Ibrahim
 	*/
	public static String[] getFilesPaths(String directoryPath) throws FileNotFoundException, IOException
	{
		File dir = new File(directoryPath);
	
		File[] files = dir.listFiles();
		String filePath = null;
		String fileName = null;
		
		 int javaFileCounter = 0; 
		 for (File f : files) 
		 {
			 fileName = f.getName();
			 
			 if(f.isFile() && fileName.endsWith(".java"))
			 {
				 javaFileCounter += 1; 
			 }
		
		 }
		 
		 String[] paths = new String[javaFileCounter];
		 int i = 0;
		 for (File f : files) 
		 {
			 filePath = f.getAbsolutePath();
			 fileName = f.getName();
			 
			 if(f.isFile() && fileName.endsWith(".java"))
			 {
				 paths[i] = filePath;
				 i = i + 1;
			 }
		
		 }
		 
		 return paths;
	}



	/***
 	* 
 	* @param filePath
 	* @return the content of file as a string
 	* @throws FileNotFoundException
 	* @throws IOException
 	* @author Zahra Al Ibrahim
 	*/
	public static String getFileContent(String filePath) throws FileNotFoundException, IOException
	{
		BufferedReader buff = new BufferedReader(new FileReader(filePath));
		StringBuilder builder = new StringBuilder();
		String line = buff.readLine();
		while(line != null)
		{
			builder.append(line);
			builder.append(System.lineSeparator());
			line = buff.readLine();
		
		}
	
		String fileContent = builder.toString();
		return fileContent;
		
	}
	
		
	/**
	 * This method parses java source file and creates AST and returns 
	 * the tree.
	 * 
	 * @author
	 */
	public void parseFiles() {
		
	}
	
	/**
	 * This method takes AST and counts the number of declarations of that 
	 * type with the given directory and counts the number of references to 
	 * each occurrences of that type within that directory. 
	 * And print to the console.
	 * 
	 * @author
	 */
	public void count() {
		
	}
	
	/**
	 * Main method for the class TypeCounter. 
	 * Takes user inputs for the pathname and the type.
	 * 
	 * @author
	 * @param args
	 */
	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		// testing getFilePaths and getFileContent
		String[] paths = getFilesPaths("C:\\Users\\Zahra\\Desktop\\Java\\Pong");
		String content = null;
		for(int i=0 ; i < paths.length; i++)
		{
		 	content = getFileContent(paths[i]);
		 	//System.out.println(content);
		}
		
	}

}
