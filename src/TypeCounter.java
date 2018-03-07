import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import java.awt.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class takes a pathname and name of Java Type and counts the number 
 * of declarations of that type within that directory and counts the number 
 * of references to each occurrence of that type within that directory.
 * 
 * @author Zahra Al Ibrahim, Tylor Chow, Ga Hyung Kim
 *
 */
public class TypeCounter {
	
	static int declarationCounter;
	static int referenceCounter;
	
	// Constructor
	TypeCounter() {
		declarationCounter = 0;
		referenceCounter = 0;
	}

	
	/**
	 * This method gets directory as an argument and returns an array of strings with the path 
	 * of .java files in directory.
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

	/**
	 * This method reads the file and returns the file contents as string.
	 * @param filePath
	 * @return the content of file as a string
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author Zahra Al Ibrahim
	 */
	public static String getFileContent(String filePath) throws FileNotFoundException, IOException
	{
		BufferedReader buffer = new BufferedReader(new FileReader(filePath));
		StringBuilder builder = new StringBuilder();
		String line = buffer.readLine();
		while(line != null)
		{
			builder.append(line);
			builder.append(System.lineSeparator());
			line = buffer.readLine();
		
		}
	
		String fileContent = builder.toString();
		return fileContent;
	}


		
	/**
	 * This method parses java source file and creates AST and returns the root of AST.
	 * 
	 * @author Ga Hyung Kim
	 * @return cu CompilationUnit of created AST
	 */
	public CompilationUnit parseFiles(String sourceString) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(sourceString.toCharArray());
		parser.setResolveBindings(true);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);
		
		return cu;
	}
	
	/**
	 * This method takes AST and counts the number of declarations of that 
	 * type with the given directory and counts the number of references to 
	 * each occurrences of that type within that directory. 
	 * 
	 * @author
	 * @param cu CompilationUnit of the created AST
	 */
	public void count(CompilationUnit cu) {
		
		
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
