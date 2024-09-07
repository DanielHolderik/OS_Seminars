import java.io.*;
import java.util.*;
import java.lang.*;


public class S1T1 {

public void createThreadProcess (String command){
    RunnableCommand runnableCommand = new RunnableCommand(command);

    Thread thread = new Thread(runnableCommand);
    thread.start();
}

   

class RunnableCommand implements Runnable  {
    private String command;

    public RunnableCommand(String command){
        this.command = command;
    }

    @Override
    public void run() {
    
        List<String> input = Arrays.asList(command.split(" "));

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
            System.err.println("Error");
            System.err.println(ioe);
        } finally {
            if (bufferReader != null) {
                try {
                    bufferReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    } 
    public static void main(String[] args) throws java.io.IOException {
        S1T1 cmd = new S1T1();
        String commandLine;
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\n***** Welcome to the Java Command Shell *****");
        System.out.println("If you want to exit the shell, type END and press RETURN.\n");
    
        while (true) {
               System.out.print("jsh>");
            commandLine = scanner.nextLine();
          
            if (commandLine.equals("")) {
                continue;
            }
            if (commandLine.toLowerCase().equals("end")) { //User wants to end shell
                System.out.println("\n***** Command Shell Terminated. See you next time. BYE for now. *****\n");
                scanner.close();
                System.exit(0);
            }
            cmd.createThreadProcess(commandLine);
        }   
    }
}
