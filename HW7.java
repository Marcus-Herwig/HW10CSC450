import java.io.PrintStream;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
public class HW7
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket s = new ServerSocket(2222);
        ArrayList<chatServerWorker> theThreads = new ArrayList<chatServerWorker>();
        while(true)
        {
            System.out.println("Listenning for Connection...");
            Socket client = s.accept(); //blocks
            chatServerWorker t = new chatServerWorker(client);
            theThreads.add(t);
            t.start();
        }
    }
}