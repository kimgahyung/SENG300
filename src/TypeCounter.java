
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.PrimitiveType;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class TypeCounter
{
		
		static int declarationCounter;
		static int referenceCounter ;
		
		// Constructor
		TypeCounter() {
			declarationCounter = 0;
			referenceCounter = 0;
		}
		
		/***
		 * This method takes the directory path as an argument and counts the number
		 * of .java files in that directory
		 */
		public int countJavaFiles(String directoryPath) throws FileNotFoundException, IOException
		{
			int javaFilesCounter = 0;
			File dir = new File(directoryPath);			
			File[] files = dir.listFiles();
			
			String fileName = null;
			
			
			 for (File f : files) 
			 {
				 fileName = f.getName();
				 
				 if(f.isFile() && fileName.endsWith(".java"))
				 {
					 javaFilesCounter += 1; 
				 }
			
			 }
			 return javaFilesCounter;
			
			
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
		public String[] getFilesPaths(String directoryPath, int javaFiles) throws FileNotFoundException, IOException
		{
			File dir = new File(directoryPath);
		
			File[] files = dir.listFiles();
			String filePath = null;
			String fileName = null;
			
			 
			 String[] paths = new String[javaFiles];
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
		
		
		public String[] getFilesNames(String directoryPath , int javaFiles) throws FileNotFoundException, IOException
		{
			File dir = new File(directoryPath);
		
			File[] files = dir.listFiles();
			String fileName = null;
			
			 
			 String[] names = new String[javaFiles];
			 int i = 0;
			 for (File f : files) 
			 {
				 fileName = f.getName();
				 
				 if(f.isFile() && fileName.endsWith(".java"))
				 {
					 names[i] = fileName;
					 i = i + 1;
				 }
			
			 }
			 
			 return names;
		}
	

		/**
		 * This method reads the file and returns the file contents as string.
		 * @param filePath
		 * @return the content of file as a string
		 * @throws FileNotFoundException
		 * @throws IOException
		 * @author Zahra Al Ibrahim
		 */
		public String getFileContent(String filePath) throws FileNotFoundException, IOException
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
		 * @author Ga Hyung Kim , Zahra Al Ibrahim
		 * @return cu CompilationUnit of created AST
		 */
		public CompilationUnit parseFiles(String sourceString, String fileName, String[] dirPath) 
		{
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setEnvironment(null, dirPath, null, false);
			parser.setUnitName(fileName);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setSource(sourceString.toCharArray());
			parser.setResolveBindings(true);
			parser.setBindingsRecovery(true);
			parser.setStatementsRecovery(true);
			
			
			Map options = JavaCore.getOptions();
			JavaCore.setComplianceOptions(JavaCore.VERSION_1_8, options);
			parser.setCompilerOptions(options);

			CompilationUnit cu = (CompilationUnit) parser.createAST(null);
			
			if (cu.getAST().hasBindingsRecovery()) {
			
				System.out.println("Binding activated.");
			}
			
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
		public void countTypes(CompilationUnit cu, String type) {
			cu.accept(new ASTVisitor() {
				
			public boolean visit(AnnotationTypeDeclaration  node) {
					
					ITypeBinding iType = node.resolveBinding();
					String name = iType.getQualifiedName();
					
					if (name.equals(type))
					{
						System.out.println("annotaion");
						 declarationCounter += 1;
					}
					
					return true;
				}
			
				public boolean visit(TypeDeclaration node) {
					
					ITypeBinding iType = node.resolveBinding();
					String name = iType.getQualifiedName();
					
					if (name.equals(type))
					{
						System.out.println("Class or interface");
						 declarationCounter += 1;
					}
					
					return true;
				}
				
				
				public boolean visit(QualifiedName node) {
										
					if (node.getFullyQualifiedName().equals(type))
					{
						System.out.println("Qualified name");
						 referenceCounter += 1;
					}
					
					return true;
				}
				
				
				public boolean visit(PrimitiveType node) {
					
					ITypeBinding iType = node.resolveBinding();
					String name = iType.getQualifiedName();
					
					
					if (name.equals(type))
					{
						System.out.println("primitive type");
						 referenceCounter += 1;
					}
					
					return true;
				}
			
				
				public boolean visit(SimpleName node) {
										
					if (node.getFullyQualifiedName().equals(type))
					{
						System.out.println("Simple name");
						 referenceCounter += 1;
					}
					
					return true;
				}
				
			
				
	
				
				public boolean visit(EnumDeclaration node) {
					
					ITypeBinding iType = node.resolveBinding();
					String name = iType.getQualifiedName();
					
					if (name.equals(type))
					{
						System.out.println("enum");
						 declarationCounter += 1;
					}
					
					
					return true;
				}
				
				

			});

		} 
		
		/**
		 * Main method for the class TypeCounter. 
		 * Takes user inputs for the pathname and the type.
		 * 
		 * @author Zahra Al Ibrahim, Ga Hyung Kim
		 * @param args
		 */
		public static void main(String[] args) throws FileNotFoundException, IOException {
			TypeCounter p = new TypeCounter();
			Scanner keyboard = new Scanner(System.in);
			
			// Take the user input for the pathname
			System.out.print("Enter the pathname: ");
			//String pathInput = keyboard.next();
			String pathInput = "C:\\Users\\Zahra\\eclipse-workspace\\Test\\src\\NewTest";

			// Take the user input for the type 
			System.out.print("Enter the fully qualified name of a Java type: ");
			String typeInput = keyboard.next();
			
			// testing getFilePaths and getFileContent
			int javaFilesCounter = p.countJavaFiles(pathInput);
			String[] paths = p.getFilesPaths(pathInput, javaFilesCounter );
			String[] names = p.getFilesNames(pathInput, javaFilesCounter );
			String content = null;
			for(int i=0 ; i < paths.length; i++)
			{
			 	content = p.getFileContent(paths[i]);
			 	CompilationUnit cu = p.parseFiles(content, names[i], new String[] {pathInput});
			 	p.countTypes(cu , typeInput);
			}
			System.out.println("Declarations: " +declarationCounter);
			System.out.println("References: " + referenceCounter);
			
		}

	}


