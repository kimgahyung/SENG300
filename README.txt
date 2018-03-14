=========================================================================
Name
=========================================================================
Zahra Al Ibrahim 
Tylor Chow
Ga Hyung Kim
=========================================================================
Instruction
=========================================================================
In the cmd
- java TypeCounter directoryPath qualifiedNameOfType
=========================================================================
Files
=========================================================================
TypeCounter.java
- Main class that takes the commandline arguments for directory path and
qualified name of type to be looked for and counts the number of 
declarations and references of that type
TestTypeCounter.java
- Test class that does unit testing and integration testing of class
TypeCounter
=========================================================================
Known bugs
=========================================================================
- When counting references, it also counts the case when it's been 
declared
- When finding references of type with fully qualified name, it only
counts the number of references of fully qualified name and does not 
count the number of references of simple name. For example, when the
type is "java.lang.String", it counts the references of "java.lang.String"
but not "String".