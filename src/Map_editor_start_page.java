//import java.awt.Font;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.ComponentEvent;
//
//import javax.swing.JButton;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.SwingConstants;
//
//
//public class Map_editor_start_page extends JPanel
//{
// 
//    private static final long serialVersionUID = 1L;
//    JButton Start;
// //   JButton use_exiting_map;
//  //  JButton Load_map;
//    JLabel introduction;
//    Font font ;
//    public Map_editor_start_page(final Menu menu)
//    {
//        setLayout(null);
//        
//        
//        JPanel panel1;
//        panel1 = new JPanel();
//        panel1.setLocation(0,0);
//        panel1.setSize(800,300);
//        
//        font = new Font("Tahoma",20,20);
//        introduction = new JLabel();
//        introduction.setLocation(0,0);
//        introduction.setSize(800,100);
//        introduction.setHorizontalTextPosition(SwingConstants.CENTER);
//        introduction.setVerticalTextPosition(SwingConstants.CENTER);
//        introduction.setText("Create your own map for having an interesting game have fun!");
//        introduction.setFont(font);
//        
//        JPanel panel2;
//        panel2 = new JPanel();
//        panel2.setSize(800,150);
//        panel2.setLayout(new GridLayout(1,1));
//        panel2.setLocation(0,panel1.getSize().height);
//        
//        Start = new JButton("Start");
//        Start.addActionListener(new ActionListener()
//        {
//            
//            @Override
//            public void actionPerformed(ActionEvent arg0)
//            {
//                menu.dispatchEvent(new ComponentEvent(Map_editor_start_page.this,Messages.click_sound));
//                Finals.reset_map();
//                new MapEditor(menu);
//               // menu.setVisible(false);
//            }
//        });
//        Start.setFont(font);
//        Start.setHorizontalTextPosition(SwingConstants.CENTER);
//        Start.setVerticalTextPosition(SwingConstants.CENTER);
//        
//        panel2.add(Start);
//        panel1.add(introduction);
//        add(panel1);
//        add(panel2);
//        setVisible(true);
//    }
//    
//}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.ImageIcon;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Map_editor_start_page extends JPanel implements ActionListener, KeyListener {

    JLabel jl, jl2, jl3;
    JButton start;
    Menu menu;
    JTextField h, w;
    
    boolean proper_height=false;
    boolean proper_width=false;
    
    public Map_editor_start_page(Menu menu) {
        setLayout(null);
        this.menu = menu;
        jl = new JLabel();
        jl.setSize(600, 500);
        jl.setIcon(new ImageIcon("mapbackground.jpg"));
        start = new JButton("<html> <font face=Algerian size=5> Start </font></html>");
        start.setBounds(500, 100, 120, 50);
        start.addActionListener(this);
        h = new JTextField();
        w = new JTextField();
        h.addKeyListener(this);
        w.addKeyListener(this);
        h.setBounds(20, 20, 150, 30);
        w.setBounds(20, 50, 150, 30);
        jl2 = new JLabel();
        jl2.setBounds(180, 20, 200, 30);
        jl3 = new JLabel();
        jl3.setBounds(180, 50, 200, 30);
        add(h);
        add(w);
        add(jl2);
        add(jl3);
        add(start);
        add(jl);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(proper_width&&proper_height){
            Finals.pixeles = new Integer[Integer.parseInt(w.getText().trim())][Integer.parseInt(h.getText().trim())];
            new MapEditor(menu,Integer.parseInt(w.getText().trim()),Integer.parseInt(h.getText().trim()));
            menu.dispatchEvent(new ComponentEvent(Map_editor_start_page.this,Messages.click_sound));      
        }
        else {
            new MapEditor(menu,40,20);
            menu.dispatchEvent(new ComponentEvent(Map_editor_start_page.this,Messages.click_sound)); 
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
         
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9' || e.getKeyCode()==8) {
            String hs = h.getText();
            String ws = w.getText();
            if (!hs.isEmpty()) {
                if (Integer.parseInt(hs) > 100) {
                    jl2.setText("<html><font color=red>Too High</font></html>");
                    proper_height=false;
                }
                else if(Integer.parseInt(hs) < 20){
                    proper_height=false;
                    jl2.setText("<html><font color=red>Too short</font></html>");
                }else if(Integer.parseInt(hs) >= 20 && Integer.parseInt(hs)<=100){
                    proper_height=true;
                    jl2.setText("<html><font color=green>Proper hieght</font></html>");
                }
            }
            if (!ws.isEmpty()) {
                if (Integer.parseInt(ws) >100) {
                    proper_width=false;
                    jl3.setText("<html><font color=red>Too wide</font></html>");
                }
                else if(Integer.parseInt(ws) < 20){
                    proper_width=false;
                    jl3.setText("<html><font color=red>Too thin</font></html>");
                } else if(Integer.parseInt(ws) >= 20 && Integer.parseInt(ws)<=100){
                    jl3.setText("<html><font color=green>Proper width</font></html>");
                    proper_width=true;
                }
            }
        } else {
            e.consume();
        }

    }

    @Override
    protected void processEvent(AWTEvent e) {
        super.processEvent(e); 
    }
    
    
    

}

