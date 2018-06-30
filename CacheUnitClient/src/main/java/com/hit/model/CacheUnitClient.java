package com.hit.model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class CacheUnitClient
{
    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;

    public CacheUnitClient()
    {

    }

    public String send(String request)
    {
        try
        {
            socket  = new Socket (InetAddress.getLocalHost ().getHostAddress (), 1234);
            outputStream = new ObjectOutputStream (socket.getOutputStream ());
            inputStream = new ObjectInputStream (socket.getInputStream ());
        } catch (IOException e)
        {
            e.printStackTrace ();
        }


        String o = "";


        try
        {
            outputStream.writeObject (request);

            o = (String) inputStream.readObject ();

        } catch (IOException e)
        {
            e.printStackTrace ();
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace ();
        }

        try
        {
            socket.close ();
            inputStream.close ();
            outputStream.close ();
        } catch (IOException e)
        {
            e.printStackTrace ();
        }

        return o;
    }

}