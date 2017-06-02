import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

public class SinglePlayerFrame extends JFrame implements ActionListener
{

    JButton start;
    JButton selectmap;
    JButton setting;
    JButton exit;
    JLabel label;
    Background_label backgroundlabel;
    JLabel allsettings;
    Map_preview_for_loading_for_game pre_mini_map;
    Vector<JButton> allbuttons;
    JScrollPane scroll;
    JRadioButton music_on, music_off;
    int settingcounter = 1, mapcounter = 1; // for opening & closing setting
    Menu menu;
    Font font;
    boolean music_on_turn = true;
    File_chooser_save_load file_load;
    JComboBox<Integer> Selected_number_of_players;
    
    public SinglePlayerFrame(Menu menu)
    {
        setLayout(null);
        this.menu = menu;
        font = new Font("Tahoma", 20, 20);
        allsettings = new JLabel();
        music_on_turn = menu.get_music_on_off();// it indicates that music is on or off
        if (music_on_turn)
        {
            music_on = new JRadioButton("On", true);
            music_off = new JRadioButton("Off", false);
        }
        else
        {
            music_on = new JRadioButton("On", false);
            music_off = new JRadioButton("Off", true);
        }
        music_off.addActionListener(this);
        music_on.addActionListener(this);
        allbuttons = new Vector<>();
        start = new JButton("Start");
        selectmap = new JButton("Select Map");
        setting = new JButton("Setting");
        exit = new JButton("Exit");
        allbuttons.add(start);
        allbuttons.add(selectmap);
        allbuttons.add(setting);
        allbuttons.add(exit);
        label = new JLabel();
        backgroundlabel = new Background_label();
        label.setBounds(Toolkit.getDefaultToolkit().getScreenSize().width - 400, 0, 400, 200);
        label.setLayout(new GridLayout(4, 1, 0, 10));
        System.out.println(label.getSize().width);
        for (int i = 0; i < allbuttons.size(); i++)
        {
            allbuttons.elementAt(i).addActionListener(this);
            label.add(allbuttons.elementAt(i));
        }
        file_load = new File_chooser_save_load();
        selectmap.addActionListener(file_load.getLoadActionListner());
        getContentPane().add(label);
        getContentPane().add(backgroundlabel);
        set_font();
        setLocation(0, 0);
        setSize(Toolkit.getDefaultToolkit().getScreenSize());
        backgroundlabel.setSize(this.getSize());
        setVisible(true);
    }

    private void setMapSelect()
    {

        if (file_load.get_load_result())
        {
            label.setLayout(null);
            pre_mini_map = new Map_preview_for_loading_for_game(new Dimension(400, 400));
            pre_mini_map.setBounds(selectmap.getLocation().x, selectmap.getLocation().y + 50, 400, 400);
            pre_mini_map.setLocation(selectmap.getLocation().x, selectmap.getLocation().y + 50);
            label.setSize(label.getWidth(), label.getHeight() + 1000);
            label.add(pre_mini_map);
        }
    }

    private void setSetting()
    {
        label.setLayout(null);
        allsettings.setBounds(setting.getLocation().x, setting.getLocation().y + setting.getSize().height, 400, 900);
        // /

        music_on.setBounds(200, 10, 50, 30);
        music_off.setBounds(250, 10, 50, 30);
        ButtonGroup bg = new ButtonGroup();
        bg.add(music_on);
        bg.add(music_off);

        allsettings.add(music_on);
        allsettings.add(music_off);
        // /
        JLabel music = new JLabel("<html><font size=3>Music</font></html>");
        music.setBounds(50, 10, 100, 30);
        allsettings.add(music);
        // /
        Selected_number_of_players = new JComboBox();
        Selected_number_of_players.addItem(2);
        Selected_number_of_players.addItem(3);
        Selected_number_of_players.addItem(4);
        Selected_number_of_players.setBounds(music_on.getLocation().x, music_on.getLocation().y + 50, 100, 30);
        allsettings.add(Selected_number_of_players);
        // /
        JLabel player = new JLabel("Number of Players");
        player.setBounds(music.getLocation().x, Selected_number_of_players.getLocation().y - 20, 200, 50);
        allsettings.add(player);
        // /
        JComboBox<String> difficulty = new JComboBox();
        difficulty.addItem("Easy");
        difficulty.addItem("Medium");
        difficulty.addItem("Hard");
        difficulty.setBounds(Selected_number_of_players.getLocation().x, Selected_number_of_players.getLocation().y + 110, 100, 30);
        allsettings.add(difficulty);
        // /
        JLabel difficult = new JLabel("Set Difficulty");
        difficult.setBounds(music.getLocation().x, difficulty.getLocation().y - 20, 100, 50);
        allsettings.add(difficult);
        // /
        exit.setBounds(exit.getLocation().x, exit.getLocation().y + 300, exit.getWidth(), exit.getHeight());
        label.setSize(label.getWidth(), label.getHeight() + 900);

        label.add(allsettings);

        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource().equals(exit))
        {
            this.dispose();
        }
        else if (e.getSource().equals(start))
        {
            if (file_load.get_load_result())
            {
                //dipatch event for locating load button
                int num_of_players;
                if(Selected_number_of_players==null)
                    num_of_players = 2;
                else
                num_of_players=Integer.parseInt(Selected_number_of_players.getSelectedItem().toString().trim());
                Vector<CartesianPoint> HQ_points = (new Base_finder(num_of_players)).get_HQ_points();
                try
                {
                    UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                }
                catch (Exception arg0)
                {
                    // TODO Auto-generated catch block
                    arg0.printStackTrace();
                }
                new Game_frame(HQ_points,true);
                this.dispose();
                
            }
        }
        else if (e.getSource().equals(setting))
        {
            if (settingcounter == 1)
                setSetting();
            else if (settingcounter % 2 == 1)
            {
                label.add(allsettings);
                exit.setBounds(exit.getLocation().x, exit.getLocation().y + 300, exit.getWidth(), exit.getHeight());
            }
            else
            {
                label.remove(allsettings);
                exit.setBounds(exit.getLocation().x, exit.getLocation().y - 300, exit.getWidth(), exit.getHeight());

            }
            settingcounter++;
            repaint();
        }
        else if (e.getSource().equals(selectmap) && file_load.get_load_result())
        {

            if (mapcounter == 1)
            {
                setting.setBounds(setting.getLocation().x, setting.getLocation().y + 400, setting.getWidth(),
                        setting.getHeight());
                if (settingcounter > 1)
                {
                    allsettings.setBounds(setting.getLocation().x, setting.getLocation().y + setting.getSize().height,
                            400, 900);
                }
                exit.setLocation(exit.getLocation().x, exit.getLocation().y + 400);
                setMapSelect();

            }
            repaint();
            mapcounter++;
        }

        else if ((e.getSource().equals(music_off) && music_on_turn))
        {
            menu.dispatchEvent(new ComponentEvent(SinglePlayerFrame.this, Messages.music_off_on));
            music_on_turn = false;
        }
        else if ((e.getSource().equals(music_on) && !music_on_turn))
        {
            menu.dispatchEvent(new ComponentEvent(SinglePlayerFrame.this, Messages.music_off_on));
            music_on_turn = true;
        }
    }

    private void set_font()
    {// for changing the font //it uses all_buttons();
        for (int i = 0; i < allbuttons.size(); i++)
        {
            allbuttons.elementAt(i).setFont(font);
        }
    }
}

class Background_label extends JLabel
{

    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage((new ImageIcon("spframebackground.jpg")).getImage(), 0, 0, this.getSize().width - 400,
                this.getSize().height, null);
    }

}
