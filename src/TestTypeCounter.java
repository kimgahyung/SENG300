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
	
	// Array of pathnames 
	// {no file, nonexisting directory, Directory with one file, multiple files, various files}
	private String[] pathNames = {"\\Desktop\\SENG300-groupProj\\ExampleFiles\\noFile", 
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\nonExistingFile",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\oneFile",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\moreThanOneFiles",
						"\\Desktop\\SENG300-groupProj\\ExampleFiles\\variousJavaFiles"
	};
	
	private String NO_FILE_DIR;
	private String NONEXISTING_DIR;
	private String ONE_FILE_DIR;
	private String MORE_THAN_ONE_FILE_DIR;
	private String VARIOUS_FILES;
	
	private String TYPE = "type";
	private static String[] args;	
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

	@Test(expected = NullPointerException.class)
	public void testCountJavaFilesForNonexistingDirectory() throws FileNotFoundException, IOException {
		int result = tc.countJavaFiles(NONEXISTING_DIR);		
	}
	
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
	public void testGetFilesNamesForNoFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(NO_FILE_DIR, 0);
		assertEquals(0, result.length);
	}
	
	@Test 
	public void testGetFilesNamesForOneFile() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(ONE_FILE_DIR,  1);
		assertEquals(1, result.length);
	}
	
	@Test(expected = NullPointerException.class)
	public void testGetFilesNamesForNonexistingFiles() throws FileNotFoundException, IOException {
		String[] result = tc.getFilesNames(NONEXISTING_DIR, 0);
	}

	/* Integration testing */
	
	@Test
	public void testGetFileContentForOneFile() throws FileNotFoundException, IOException {
		int javaFilesCounter = tc.countJavaFiles(ONE_FILE_DIR);
		String[] paths = tc.getFilesPaths(ONE_FILE_DIR, javaFilesCounter);
		String content = tc.getFileContent(paths[0]);
		assertNotNull(content);
	}
	
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
	public void testCountTypesForOneClassDeclaration() throws FileNotFoundException, IOException {
		String fileName = "OneClassDeclaration.java";
		String content = tc.getFileContent(VARIOUS_FILES);
		CompilationUnit cu = tc.parseFiles(content, fileName, new String[] {VARIOUS_FILES});
		//tc.countTypes(cu, );
		
	}

}