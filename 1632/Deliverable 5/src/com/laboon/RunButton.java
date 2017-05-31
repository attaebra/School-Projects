package com.laboon;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RunButton extends JButton {

    private MainPanel _m;
    
    /**
	 *Constructor
	 *Create the run button and adds action listener for panel
	 *@param m - main panel
	 */
	
	public RunButton(MainPanel m) {
	super("Run");
	_m = m;
	addActionListener(new RunButtonListener());
    }

    class RunButtonListener implements ActionListener {

	public void actionPerformed(ActionEvent e) {
	    _m.run();
	}
    }    
    
}
