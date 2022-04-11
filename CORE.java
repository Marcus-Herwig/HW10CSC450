import java.io.FileInputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class CORE 
{
    public static byte[] file;
    private static ArrayList<PrintStream> theClientStreams = new ArrayList<PrintStream>();

    public static byte[] receiveFile()
    {
        return file;
    }
    
    public static synchronized void addClientThreadPrintStream(PrintStream ps)
    {
        System.out.println("adding client thread");
        CORE.theClientStreams.add(ps);
    }

    public static synchronized void removeClientThreadPrintStream(PrintStream ps)
    {
        CORE.theClientStreams.remove(ps);
    }
    
    public static void broadcastMessage(String message)
    {
        System.out.println("About to broadcast....");
        for (PrintStream ps : CORE.theClientStreams)
        {
            ps.println(message);
        }
    }

    public static void sendFile(String path)
    {
        try
        {
            FileInputStream fileInput = new FileInputStream(path);
            fileInput.read(file);
        }
        catch(Exception e)
        {
            System.out.print("something went wrong");
        }
        
    }
}