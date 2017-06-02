/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class SinglePlayerPanel extends JPanel implements ActionListener{

    JButton start;
    JLabel background;
    Menu menu;
    public SinglePlayerPanel(Menu menu) {
        setLayout(null);
        this.menu=menu;
        //setBackground(Color.WHITE);
        background = new JLabel();
        background.setBounds(0, 0, 400, 600);
        background.setIcon(new ImageIcon("sppanelbackground.png"));
        add(background);
        start = new JButton("<html><font face='Niagara Solid' size=10>Let's Battle</font></html>");
        start.setLocation(Finals.MENU_SIZE.width/2,Finals.MENU_SIZE.height-400);
        start.setSize(Finals.BUTTON_SIZE.width+100,Finals.BUTTON_SIZE.height-50);
        start.addActionListener(this);
        add(start);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(start)){
            new SinglePlayerFrame(menu);
        }
        
    }
    
}
