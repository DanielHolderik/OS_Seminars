import java.io.*;
import java.util.*;

public class S1T2 {

    static void createProcess(String command) throws java.io.IOException {

        List<String> input = Arrays.asList(command.split(" "));
        List<String> showerrlog = Arrays.asList(command);

        ProcessBuilder processBuilder = new ProcessBuilder(input);
        BufferedReader bufferReader = null;
        try {

            Process proc = processBuilder.start();
            InputStream inputStream = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            bufferReader = new BufferedReader(isr);

            String line;
            while ((line = bufferReader.readLine()) != null) {
                System.out.println(line );
            }
            bufferReader.close();
        } catch (java.io.IOException ioe) {
            System.err.println(" Invalid Command ");
            showerrlog.add(command + "; ");
        } finally {
            if (bufferReader != null) {
                bufferReader.close();
            }
        }
    }

    public static void main(String[] args) throws java.io.IOException {
        String commandLine;
        List<String> showerrlog = Arrays.asList();
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");
    
        while (true) {
               System.out.print("jsh>");
            commandLine = scanner.nextLine();
          
            if (commandLine.equals("")) {
                continue;
            
            }
            if (commandLine.toLowerCase().equals("showerrlog")){
                for (String string : showerrlog) {
                    System.err.println(string + "\n");
                }
            }
            if (commandLine.toLowerCase().equals("end")) {
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            createProcess(commandLine);
        }   
    }
   
}
