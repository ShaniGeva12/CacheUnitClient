package com.hit.controller;

import com.google.gson.Gson;
import com.hit.model.Model;
import com.hit.util.ObserMessage;
import com.hit.view.View;

import java.util.Observable;

@SuppressWarnings("unused")
public class CacheUnitController implements Controller
{

    private Model model;
    private View view;
    private static String id;

    public CacheUnitController(Model model, View view)
    {
        this.model = model;
        this.view = view;
    }

    @Override
    public void update(Observable o, Object arg)
    {
        id = null;
        ObserMessage update = (ObserMessage) arg;

        if(update.getSentIdentifier ().equals ("view"))
        {
            if(update.getMessege ().equals ("load"))
            {
                model.updateModelData ("load");
            }else
            {
                model.updateModelData ("stats");
            }
        }

        if(update.getSentIdentifier ().equals ("model-load"))
        {
            if(!update.getMessege ().equals ("false"))
            {
                view.updateUIData (new ObserMessage ("load",update.getMessege ()));
            }else
            {
                if(update.getMessege ().length () > 4)
                {

                }
            }
        }

        if(update.getSentIdentifier ().equals ("stats"))
        {
            view.updateUIData (update);
        }


    }

}