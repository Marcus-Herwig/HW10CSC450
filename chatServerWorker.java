import java.io.DataInput;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class chatServerWorker extends Thread
{
    private Socket theClientSocket;
    private PrintStream clientOutput;
    private Scanner clientInput;

    public chatServerWorker(Socket theClientSocket)
    {
        try 
        {
            System.out.println("Connection Established...");
            this.theClientSocket = theClientSocket;
            this.clientOutput = new PrintStream(this.theClientSocket.getOutputStream());    
            //System.out.println("About to add a printstream");
            CORE.addClientThreadPrintStream(this.clientOutput);

            this.clientInput = new Scanner(this.theClientSocket.getInputStream());
        } 
        catch (Exception e) 
        {
            System.err.println("Bad things happened in thread!!!!!");
            e.printStackTrace();
        }
        
    }

    public void run()
    {
        //this is what the thread does
        this.clientOutput.println("What is your name?");
        String name = clientInput.nextLine();
        CORE.broadcastMessage(name + " has joined!");
        
        String message;
        while(true)
        {
            message = clientInput.nextLine();
            if(message.equals("/quit"))
            {
                CORE.broadcastMessage(name + " has left the server!");
                CORE.removeClientThreadPrintStream(this.clientOutput);
                break;
            }
            else if(message.equals("/send"))
            {
                this.clientOutput.println("Enter a file path...");
                String path = clientInput.nextLine();
                CORE.sendFile(path);
            }
            else if(message.equals("/receive"))
            {
                try
                {
                    this.clientOutput.println("Enter the path of where to save the file...");
                    String path = clientInput.nextLine();
                    File receivedFile = new File(path);
                    FileOutputStream data = new FileOutputStream(receivedFile);
                    byte[] file = CORE.receiveFile();
                    data.write(file); 
                }
                catch (Exception e) 
                {
                    
                }
                
            }
            CORE.broadcastMessage(message);
        }
    }
}