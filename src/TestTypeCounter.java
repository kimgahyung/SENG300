import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.junit.Before;
import org.junit.Test;


/**
 * This class does the integration testing and the unit testing
 * for the class TypeCounter.
 * @author Ga Hyung Kim
 *
 */
public class TestTypeCounter {
	
	// private static String BASEDIR = "C:\\Users\\kimga";
	private String NO_FILE_DIR = "C:\\Users\\kimga\\Desktop\\noFile";
	private String NONEXISTING_DIR = "C:\\Users\\kimga\\Desktop\\nonExistingFile";
	private String ONE_FILE_DIR = "C:\\Users\\kimga\\Desktop\\oneFile";
	
	private String TYPE = "type";
	private static String[] args;	
	private TypeCounter tc;	
	
	@Before
	public void initialize() {
		tc = new TypeCounter();
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

}