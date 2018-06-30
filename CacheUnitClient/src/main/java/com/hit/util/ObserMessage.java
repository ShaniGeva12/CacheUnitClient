package com.hit.util;

public class ObserMessage
{
    private String sentIdentifier;
    private String messege;

    public ObserMessage(String sentId, String msg)
    {
        this.sentIdentifier = sentId;
        this.messege = msg;
        //this.messege = "true";
    }

    public String getSentIdentifier()
    {
        return sentIdentifier;
    }

    public String getMessege()
    {
        return messege;
    }
}