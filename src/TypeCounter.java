
import org.eclipse.jdt.core.IMethod;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.CreationReference;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.ExpressionMethodReference;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MemberRef;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.MethodRef;
import org.eclipse.jdt.core.dom.QualifiedName;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;

import java.awt.List;
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
		//static int exampleCounter = 0;
		static int javaFilesCounter ;
		
		// Constructor
		TypeCounter() {
			declarationCounter = 0;
			referenceCounter = 0;
			javaFilesCounter = 0;
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
		public String[] getFilesPaths(String directoryPath) throws FileNotFoundException, IOException
		{
			File dir = new File(directoryPath);
		
			File[] files = dir.listFiles();
			String filePath = null;
			String fileName = null;
			
			
			 for (File f : files) 
			 {
				 fileName = f.getName();
				 
				 if(f.isFile() && fileName.endsWith(".java"))
				 {
					 javaFilesCounter += 1; 
				 }
			
			 }
			 
			 String[] paths = new String[javaFilesCounter];
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
		
		
		public String[] getFilesNames(String directoryPath) throws FileNotFoundException, IOException
		{
			File dir = new File(directoryPath);
		
			File[] files = dir.listFiles();
			String fileName = null;
			
			 
			 String[] names = new String[javaFilesCounter];
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
		 * @author Ga Hyung Kim
		 * @return cu CompilationUnit of created AST
		 */
		public CompilationUnit parseFiles(String sourceString, String fileName, String[] dirPath) 
		{
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setEnvironment(null, dirPath, null, false);
			//System.out.println(fileName);
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
					String na = iType.getQualifiedName();
					
					if (na.equals(type))
					{
						System.out.println("annotaion");
						 declarationCounter += 1;
					}
					
					return true;
				}
			
				public boolean visit(TypeDeclaration node) {
					
					ITypeBinding iType = node.resolveBinding();
					String na = iType.getQualifiedName();
					
					if (na.equals(type))
					{
						System.out.println("Class or interface");
						 declarationCounter += 1;
					}
					
					return true;
				}
				
				public boolean visit(MethodInvocation  node) {
					
					//IMethodBinding iType = (IMethodBinding) node.resolveMethodBinding();
				//	String na = ((ITypeBinding) iType).getQualifiedName();
				//	System.out.println(node)
					
					//if (na.equals(type))
					{
						
					//	 declarationCounter += 1;
					}
					
					return true;
				}
				
				
				public boolean visit(QualifiedName node) {
					
					//ITypeBinding iType =  (ITypeBinding) node.resolveBinding();
					//String na =  iType.getQualifiedName();
					
					
					if (node.getFullyQualifiedName().equals(type))
					{
						System.out.println("Qualified name");
						 declarationCounter += 1;
					}
					
					return true;
				}
			
				
				public boolean visit(SimpleName node) {
					
					//ITypeBinding iType =  (ITypeBinding) node.resolveBinding();
					//String na =  iType.getQualifiedName();
					
					
					if (node.getFullyQualifiedName().equals(type))
					{
						System.out.println(node.getFullyQualifiedName());
						 declarationCounter += 1;
					}
					
					return true;
				}
				
			
				
	
				
				public boolean visit(EnumDeclaration node) {
					
					ITypeBinding iType = node.resolveBinding();
					String na = iType.getQualifiedName();
					//System.out.println(node.getName());
					
					if (na.equals(type))
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
			String[] paths = p.getFilesPaths(pathInput);
			String[] names = p.getFilesNames(pathInput);
			String content = null;
			for(int i=0 ; i < paths.length; i++)
			{
			 	content = p.getFileContent(paths[i]);
			 	CompilationUnit cu = p.parseFiles(content, names[i], new String[] {pathInput});
			 	p.countTypes(cu , typeInput);
			 	//System.out.println(content);
			}
			System.out.println("Declarations: " +declarationCounter);
			System.out.println("References: " + referenceCounter);
			
		}

	}


