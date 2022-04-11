import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class HW7Client 
{
    public static void main(String[] args) throws Exception
    {
        Socket s = new Socket("localhost", 2222); 
        Scanner clientInput = new Scanner(s.getInputStream());
        String question = clientInput.nextLine();
        System.out.println(question);
        Scanner localInput = new Scanner(System.in);
        PrintStream clientOutput = new PrintStream(s.getOutputStream());
        

        Thread lt = new Thread() {
            public void run()
            {
                String line;
                while(true)
                {
                    line = clientInput.nextLine();
                    System.out.println(line);
                }
            }
        };

        lt.start();

        String line;
        while(true)
        {
            line = localInput.nextLine();
            clientOutput.println(line);
            if(line.equals("/quit"))
            {
                break;
            }
        }
        System.out.println("Goodbye");
        System.exit(0);
        
    }
}