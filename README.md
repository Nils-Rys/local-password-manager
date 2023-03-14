# local-password-manager

For getting the program running we utilize the java editor Intellij. 
- First go to File->Project Structure and set the Project SDK and Project Language Level to your java version. (We Utilize Java 11) (Not selecting your computers java version will result in a JNI error)
- Go to Build->Build Artifacts and then select the build action.
- The created Jar will be located in your out/artifacts folder.
- To run the Jar file, in the terminal go into the jar file location and run the command:  java -jar filename.jar