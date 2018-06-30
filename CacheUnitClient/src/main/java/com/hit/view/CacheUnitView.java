package com.hit.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hit.util.DataStat;
import com.hit.util.ObserMessage;

import javax.swing.*;
import javax.swing.plaf.LabelUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Observable;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CacheUnitView extends Observable implements View
{
	private JLabel textLabel;
    @SuppressWarnings("unused")
	private LabelUI labelUI;
    private JPanel panel2;
    private JFrame frame;


    public CacheUnitView()
    {
        javax.swing.SwingUtilities.invokeLater (new Runnable ()
        {
            @Override
            public void run()
            {
                start ();
            }
        });

    }

    @SuppressWarnings("serial")
	@Override
    public void start()
    {
        frame = new JFrame ("MMU Project");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        
        //adding icon to the window, for some reason still shows default :/
        Image im = Toolkit.getDefaultToolkit().getImage("FightPokemon.png");
        frame.setIconImage(im);
        
        frame.setLocation(400, 200); //sets where on screen window opens
        frame.setLayout (new BorderLayout ());
        
        JPanel panel1 = new JPanel ();
        panel1.setOpaque (true);
       

        panel2 = new JPanel ();
        panel2.setLayout (new BoxLayout (panel2,BoxLayout.Y_AXIS));
        panel2.setOpaque (true);
        
        textLabel = new JLabel (".... Waiting For Input ....");
        
        JButton loadReq_Btn = new JButton ("Load a Request");

        loadReq_Btn.addActionListener (
        new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setChanged ();
                notifyObservers (new ObserMessage ("view", "load"));
            }
        } );

        JButton statistics_Btn = new JButton ("Show Statistics");
        statistics_Btn.addActionListener (new AbstractAction ()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                setChanged ();
                notifyObservers (new ObserMessage ("view", "stats"));
            }
        });


        panel1.add (loadReq_Btn);
        panel1.add (statistics_Btn);
        
        
        panel2.add (textLabel);
        panel2.validate ();

        JPanel jPanelpic = new JPanel();
        JLabel jpic = new JLabel();
        jpic.setIcon(new ImageIcon(this.getClass().getResource("MemoryLogo2.png")));
        jPanelpic.add(jpic);
        jPanelpic.validate();
        
        frame.add (panel1, BorderLayout.NORTH);
        frame.add (panel2, BorderLayout.CENTER);
        frame.add (jPanelpic,  BorderLayout.PAGE_END);
        frame.validate();
        
        //frame.pack (); //can use this instead of setting size manually - will generate size auto
        frame.setSize(600, 400);
        frame.setVisible (true);

    }
    
    @Override
    public <T> void updateUIData(T t)
    {
        panel2.removeAll ();
        panel2.updateUI ();

        ObserMessage tMsg = (ObserMessage) t;
        String labelString = null;

       if (tMsg.getSentIdentifier ().equals ("load"))
        {
            String inputString = tMsg.getMessege ();
            labelString = "Failed"; //if req didnt reach server

            
          //  if ( !(inputString.isEmpty()) ) //rape the prog to tell Succeeded

           if (inputString.equals ("true")) //can be true only at get/del or something
            {
                labelString = "Succeeded";
            }
            
            panel2.add (new JLabel (labelString));
            panel2.validate ();
        }
        
        
        else if(tMsg.getSentIdentifier ().equals ("stats"))
        {
            Gson gson = new GsonBuilder ().create ();

            DataStat dataStats = gson.fromJson (tMsg.getMessege (), DataStat.class);

            panel2.add (new JLabel ("Capacity: "+dataStats.getData ().get ("capacity")));
            panel2.add (new JLabel ("Algorithm: "+dataStats.getCacheAlgo ()));
            panel2.add (new JLabel ("Total Number Of Request: "+dataStats.getData ().get ("totalReqs")));
            panel2.add (new JLabel ("Total Number Of Data Models: "+dataStats.getData ().get ("modelReqs")));
            panel2.add (new JLabel ("Total Number Of Data Models Swaps: "+dataStats.getData ().get ("modelSwap")));

            panel2.validate ();
        }
    }
}
