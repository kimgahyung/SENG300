import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;


/**
 * This class does the unit testing and integration testing for the class TypeCounter.
 * @author Ga Hyung Kim
 *
 */
public class TestTypeCounter {
	
	private static String BASEDIR = "C:\\Users\\kimga";
	private String SEPARATE_CHAR = "\\";
	
	// Array of pathnames 
	// {no file, nonexisting directory, Directory with one file, multiple files, various files}
	private String[] pathNames = {"\\Desktop\\SENG300-groupProj\\ExampleFiles\\noFile", 
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\nonExistingFile",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\oneFile",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\moreThanOneFile",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\variousJavaFiles"
	};
	
	private String NO_FILE_DIR;
	private String NONEXISTING_DIR;
	private String ONE_FILE_DIR;
	private String MORE_THAN_ONE_FILE_DIR;
	private String VARIOUS_FILES;
	private String TYPE;
	private TypeCounter tc;	
	
	@Before
	public void initialize() {
		tc = new TypeCounter();
		NO_FILE_DIR = BASEDIR.concat(pathNames[0]);
		NONEXISTING_DIR = BASEDIR.concat(pathNames[1]);
		ONE_FILE_DIR = BASEDIR.concat(pathNames[2]);
		MORE_THAN_ONE_FILE_DIR = BASEDIR.concat(pathNames[3]);
		VARIOUS_FILES = BASEDIR.concat(pathNames[4]);
	}
	
	/* Unit testing */
	
	/*
	 * Test countJavaFiles method with directories with no file, one file, more than one 
	 * file, and nonexisting directory.
	 */
	@Test
	public void testCountJavaFilesForNoFile() throws FileNotFoundException, IOException {
		int result = tc.countJavaFiles(NO_FILE_DIR);
		assertEquals(0, result);
	}
	
	@Test
	public void testCountJavaFilesForOneFile() throws FileNotFoundException, IOException {
		int result = tc.countJavaFiles(ONE_FILE_DIR);
		assertEquals(1, result);
	}
	
	@Test
	public void testCountJavaFilesForMoreThanOneFile() throws FileNotFoundException, IOException {
		int result = tc.countJavaFiles(MORE_THAN_ONE_FILE_DIR);
		assertEquals(5, result);
	}
	
	@Test(expected = NullPointerException.class)
	public void testCountJavaFilesForNonexistingDirectory() throws FileNotFoundException, IOException {
		int result = tc.countJavaFiles(NONEXISTING_DIR);	
	}
	
	/*
	 * Test getFilesPaths method with directories with no file, one file, more than one 
	 * file, and nonexisting directory.
	 */
	@Test
	public void testGetFilesPathsForNoFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesPaths(NO_FILE_DIR, 0);
		assertEquals(0, result.length);
	}
	
	@Test
	public void testGetFilesPathsForOneFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesPaths(ONE_FILE_DIR, 1);
		assertEquals(1, result.length);
	}
	
	@Test
	public void testGetFilesPathsForMoreThanOneFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesPaths(MORE_THAN_ONE_FILE_DIR, 5);
		assertEquals(5, result.length);		
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetFilesPathsForNonexistingDirectory() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesPaths(NONEXISTING_DIR, 0);
	}
	
	/*
	 * Test getFilesNames method with directories with no file, one file, more than one
	 * file, and nonexisting directory.
	 */
	@Test
	public void testGetFilesNamesForNoFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(NO_FILE_DIR, 0);
		assertEquals(0, result.length);
	}
	
	@Test 
	public void testGetFilesNamesForOneFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(ONE_FILE_DIR,  1);
		assertEquals(1, result.length);
	}
	
	@Test
	public void testGetFilesNamesForMoreThanOneFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(MORE_THAN_ONE_FILE_DIR, 5);
		assertEquals(5, result.length);
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetFilesNamesForNonexistingFiles() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(NONEXISTING_DIR, 0);
	}

	/* Integration testing */
	
	/*
	 * Test getFileContent method with directories with one file, and more than one file.
	 * See if the method works correctly assuming methods countJavaFiles and getFilePaths
	 * returns correct values.
	 */	
	@Test
	public void testGetFileContentForOneFile() throws FileNotFoundException, IOException {
		int javaFilesCounter = tc.countJavaFiles(ONE_FILE_DIR);
		String[] paths = tc.getFilesPaths(ONE_FILE_DIR, javaFilesCounter);
		String content = tc.getFileContent(paths[0]);
		
		assertNotNull(content);
	}
	
	@Test
	public void testGetFileContentForMoreThanOneFile() throws FileNotFoundException, IOException {
		int javaFilesCounter = tc.countJavaFiles(MORE_THAN_ONE_FILE_DIR);
		String[] paths = tc.getFilesPaths(MORE_THAN_ONE_FILE_DIR, javaFilesCounter);
		String content = null;
		for (int i = 0; i < javaFilesCounter; i++) {
			content = tc.getFileContent(paths[i]);
			
			assertNotNull(content);
		}	
	}
	
	/*
	 * Test parseFiles method with directories with one file and more than one file.
	 * See if the method works correctly assuming methods countJavaFiles, getFilesPaths,
	 * and getFileContent returns correct values.
	 */	
	@Test
	public void testParseFilesForOneFile() throws FileNotFoundException, IOException {
		int javaFilesCounter = tc.countJavaFiles(ONE_FILE_DIR);
		String[] paths = tc.getFilesPaths(ONE_FILE_DIR, javaFilesCounter);
		String[] names = tc.getFilesNames(ONE_FILE_DIR, javaFilesCounter);
		String content = tc.getFileContent(paths[0]);
		CompilationUnit cu = tc.parseFiles(content, names[0],  new String[] {ONE_FILE_DIR});

		assertNotNull(cu);
	}
	
	@Test 
	public void testParseFilesForMoreThanOneFile() throws FileNotFoundException, IOException {
		int javaFilesCounter = tc.countJavaFiles(MORE_THAN_ONE_FILE_DIR);
		String[] paths = tc.getFilesPaths(MORE_THAN_ONE_FILE_DIR, javaFilesCounter);
		String[] names = tc.getFilesNames(MORE_THAN_ONE_FILE_DIR, javaFilesCounter);
		String content = null;
		for (int i = 0; i < javaFilesCounter; i++) {
			content = tc.getFileContent(paths[i]);
			
			assertNotNull(content);
		}	
	}

	/*
	 * Test countTypes method with specific number of declarations/references of type.
	 * Assumes that methods getFileContent, parseFiles return correct values.
	 */
	@Test
	public void testCountTypesForOneClassDeclaration() throws FileNotFoundException, IOException {
		String fileName = "OneClassDeclaration.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content, fileName, new String[] {VARIOUS_FILES});
		tc.countTypes(cu, "OneClassDeclaration");
		
		assertEquals(1, tc.getDeclarationCounter());
	}
	
	@Test
	public void testCountTypesForOneEnumDeclaration() throws FileNotFoundException, IOException {
		String fileName = "OneEnumDeclaration.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content,  fileName,  new String[] {VARIOUS_FILES});
		tc.countTypes(cu,  "Color");
		
		assertEquals(1, tc.getDeclarationCounter());
	}

	@Test
	public void tesetCountTypesForOneAnnotationDeclaration() throws FileNotFoundException, IOException {
		String fileName = "OneAnnotationDeclaration.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content, fileName, new String[] {VARIOUS_FILES});
		tc.countTypes(cu, "InterfaceExample");
		
		assertEquals(1, tc.getDeclarationCounter());
	}

	@Test
	public void testCountTypesForOneInterfaceDeclaration() throws FileNotFoundException, IOException {
		String fileName = "OneInterfaceDeclaration.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content, fileName, new String[] {VARIOUS_FILES});
		tc.countTypes(cu, "CreateInterfaceType");
				
		assertEquals(1, tc.getDeclarationCounter());
	}
	
	
	@Test
	public void testCountTypesForOneStringReference() throws FileNotFoundException, IOException {
		String fileName = "OneReference.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content, fileName,  new String[] {VARIOUS_FILES});
		tc.countTypes(cu, "java.lang.String");
		
		assertEquals(1, tc.getReferenceCounter());
	}
	
	
	@Test
	public void testCountTypesForEmptyFile() throws FileNotFoundException, IOException {
		String fileName = "Nothing.java";
		String pathName = VARIOUS_FILES + SEPARATE_CHAR + fileName;
		String content = tc.getFileContent(pathName);
		CompilationUnit cu = tc.parseFiles(content, fileName, new String[] {VARIOUS_FILES});
		tc.countTypes(cu, null);
		
		assertEquals(0, tc.getDeclarationCounter());
		assertEquals(0, tc.getReferenceCounter());
	}
	
	/*
	 * Test the program TypeCounter.
	 * All the methods work correctly if executed separately.
	 */	
	@Test
	public void testTypeCounterForOneFile() throws FileNotFoundException, IOException {
		// Type = "int" 
		String[] args = {ONE_FILE_DIR, "int"};
		tc.main(args);
		assertEquals(8, tc.getReferenceCounter());
		
		/*
		// Type = "java.lang.String"
		args[1] = "java.lang.String";
		tc.main(args);
		assertEquals(4, tc.getReferenceCounter());
		*/

		// Type = "Example"
		args[1] = "Example";
		tc.main(args);
		assertEquals(1, tc.getDeclarationCounter());
	}
	
	@Test
	public void testTypeCounterForMoreThanOneFile() throws FileNotFoundException, IOException {
		
	}
	
	@Test
	public void testTypeCounterForNonexistingDirectory() throws FileNotFoundException, IOException {
		
	}
}