package com.hit.model;

import com.hit.util.ObserMessage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Observable;

public class CacheUnitModel extends Observable implements Model
{

    CacheUnitClient cacheUnitClient;

    public CacheUnitModel()
    {
        this.cacheUnitClient = new CacheUnitClient ();
    }

    @Override
    public <T> void updateModelData(T t)
    {
        String requst = (String) t;

        if(requst.equals ("load"))
        {
            ObjectInputStream inputStream = null;
            String[] req = null;

            try
            {
                inputStream = new ObjectInputStream (new FileInputStream ("data.txt"));
                req = (String[]) inputStream.readObject ();
            } catch (IOException e)
            {
                e.printStackTrace ();
            } catch (ClassNotFoundException e)
            {
                e.printStackTrace ();
            }


            for(String s: req)
            {
                String send = cacheUnitClient.send (s);
                setChanged ();
                notifyObservers (new ObserMessage ("model-load",send));
            }
        }
        
        else if(requst.equals ("stats"))
        {
            String dataFromServer = cacheUnitClient.send ("stats");
            setChanged ();
            notifyObservers (new ObserMessage ("stats",dataFromServer));

        }

    }
}