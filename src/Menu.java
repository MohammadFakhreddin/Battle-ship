import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


public class Menu extends JFrame
{
    JTabbedPane tabs;
    Map_editor_start_page MESP;
    MultiPlayer multi_player;
    int statechangecounter;
    
    public Menu()
    {
        setResizable(false);
        
        Finals.first_Address();// it loads new pictures at start of program
        
        
        
        MESP = new Map_editor_start_page(this);
        tabs = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        multi_player = new MultiPlayer();
        multi_player.setSize(Finals.MENU_SIZE);
        multi_player.setLocation(0,0);
        SinglePlayerPanel single_player_panel = new SinglePlayerPanel(this);
        
        JLabel contentpane = new JLabel();
        contentpane.setIcon(new ImageIcon("background.jpg"));
        add(contentpane);
        tabs = new JTabbedPane(JTabbedPane.TOP,JTabbedPane.SCROLL_TAB_LAYOUT);
        tabs.setSize(370, 50);
        tabs.setForeground(Color.black);
        tabs.insertTab("<html><body width=100 height=50><font face=Algerian size=5> Welcome </font></body></html>", null, contentpane, null, 0);
        tabs.setEnabledAt(0, false);
        tabs.insertTab("<html><body width=100 height=50><font face=Algerian size=5> SinglePlayer </font></body></html>", null,single_player_panel, "<html><img src=\""+Menu.class.getResource("vsbot.jpg")+"\"</img></html>", 1);
        tabs.insertTab("<html><body width=100 height=50><font face=Algerian size=5> MultiPlayer </font></body></html>", null, multi_player, "<html><img src=\""+Menu.class.getResource("multiplayer.jpg")+"\"</img></html>", 2);
        tabs.insertTab("<html><body width=100 height=50><font face=Algerian size=5> MapEditor </font></body></html>", null, MESP, "<html><img src=\""+Menu.class.getResource("mapicon.jpg")+"\"</img></html>",3);

        add(tabs);
        
        music(); 
        
        ChangeListener cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                
                clickSound();
                
                if(statechangecounter<1){
                    statechangecounter++;
                    tabs.remove(0);
                }
                
            }
        };
        tabs.addChangeListener(cl);
        setSize(Finals.MENU_SIZE);
        setLocation(Finals.MENU_LOCATION);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
      
        setVisible(true);
    }
    
    boolean music_on=true;//checks for disable and enabling the music
    Clip background_music_sound;
    private void music() {
        try {
            AudioInputStream ais ;
            ais = AudioSystem.getAudioInputStream(Finals.menusound);
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            background_music_sound = (Clip)AudioSystem.getLine(info);
            background_music_sound.open(ais);
            background_music_sound.start();
            background_music_sound.loop(100);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Finals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Finals.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Finals.class.getName()).log(Level.SEVERE, null, ex);
        }
        

    }
    
    private void clickSound() { 
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(Finals.clicksound);
            DataLine.Info info = new DataLine.Info(Clip.class, ais.getFormat());
            Clip sound_click = (Clip) AudioSystem.getLine(info);
            sound_click.open(ais);
            sound_click.start();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void processEvent(AWTEvent arg0)
    {
        if (arg0.getID()==Messages.click_sound){
            clickSound();
        }
        else if(arg0.getID()==Messages.music_off_on){
            if(music_on){
                music_on=false;
                background_music_sound.stop();
            }
            else if (!music_on)
            {
                music_on=true;
                background_music_sound.start();
            }
        }
        super.processEvent(arg0);
    }
    public boolean get_music_on_off(){
        return music_on;
    }
}
