import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;

public class Selected_Info extends JPanel implements ActionListener
{

    JLabel/* imagelabel, */details, additional;
    Border unit_border_layout;
    Background_label_selected_info background_label;
    Game_panel game_panel;
    JLabel list_of_buttons;
    JButton human_maker;
    JButton war_ship_maker;
    JButton fisher_ship_maker;
    Font font = new Font("Algerian", Font.PLAIN, 30);
    JButton exit_button;
    Game_frame game_frame;//it is dispatch event please send data for exit button

    public Selected_Info(Point location, Dimension size, final Game_frame game_frame)
    { // mode: human=1 , war_ship=2 fisher_ship=3

        setLayout(null);

        this.game_frame=game_frame;
        ToolTipManager.sharedInstance().setInitialDelay(0);

        Selected_Info.this.setSize(size);
        Selected_Info.this.setLocation(location.x, location.y);

        unit_border_layout = new Border(new Point(0, 0), new Dimension(this.getHeight(), this.getHeight()));


        details = new JLabel();
        details.setFont(font);
        details.setLocation(unit_border_layout.getSize().width + 20, unit_border_layout.getLocation().y);
        details.setSize(this.getHeight() * 4, this.getHeight());

        additional = new JLabel();
        additional.setFont(font);
        additional.setBounds(details.getLocation().x, details.getLocation().y + 45, this.getHeight() * 4,
                this.getHeight());

        list_of_buttons = new JLabel();
        list_of_buttons.setLayout(new GridLayout(1, 3));
        list_of_buttons.setSize(3 * this.getHeight(), this.getHeight());
        list_of_buttons.setLocation(this.getHeight() * 2, 0);
        
        human_maker = new JButton();
        human_maker.setToolTipText("Needs 2000 food, 1000 wood");
        human_maker.addActionListener(this);
        human_maker.setIcon(Finals.spining_human);
        human_maker.setBorderPainted(false);
        human_maker.setContentAreaFilled(false);
        list_of_buttons.add(human_maker);

        war_ship_maker = new JButton();
        war_ship_maker.setToolTipText("Needs 3000 iron, 500 wood,1000 food");
        war_ship_maker.setIcon(Finals.spining_war_ship);
        war_ship_maker.addActionListener(this);
        war_ship_maker.setBorderPainted(false);
        war_ship_maker.setContentAreaFilled(false);
        list_of_buttons.add(war_ship_maker);

        fisher_ship_maker = new JButton();
        fisher_ship_maker.setBorderPainted(false);
        fisher_ship_maker.setContentAreaFilled(false);
        fisher_ship_maker.setToolTipText("Needs 500 iron, 500 wood");
        fisher_ship_maker.setIcon(Finals.spining_fisher_ship);
        fisher_ship_maker.addActionListener(this);
        list_of_buttons.add(fisher_ship_maker);
        

        exit_button = new JButton("Exit");
        exit_button.setSize(Finals.BUTTON_SIZE);
        exit_button.setBorderPainted(false);
        exit_button.setContentAreaFilled(false);
        exit_button.setLocation(this.getWidth()-Finals.BUTTON_SIZE.width-Finals.MINI_MAP_SIZE.width,0);
        exit_button.setFont(font);
        //exit_button.setIcon(Finals.exit_button);
        exit_button.addActionListener(new ActionListener()
        {
            
            @Override
            public void actionPerformed(ActionEvent arg0)
            {
               change_look_and_feel(); 
               game_frame.dispose(); 
            }
        });
        add(exit_button);
        
        if(!game_frame.get_is_single_player()){
       //Panel chat_box=Finals.client.getChatbox();// need debbug
        //chat_box.setLocation(this.getWidth()-chat_box.getSize().width-Finals.MINI_MAP_SIZE.width,Finals.BUTTON_SIZE.height);
        //add(chat_box);
        }
        background_label = new Background_label_selected_info(new Point(0, 0), new Dimension(this.getWidth(),this.getHeight()));
        add(background_label);
        
        invalidate();
        
        setVisible(true);
    }

    public void game_panel_setter(Game_panel game_panel)
    {// for calling a method in Game_panel for making new human
        this.game_panel = game_panel;
    }

    public void disableSelection()
    {

        Selected_Info.this.remove(Selected_Info.this.unit_border_layout);
        details.setText("");
        additional.setText("");

        Selected_Info.this.remove(list_of_buttons);
        Selected_Info.this.remove(details);
        Selected_Info.this.remove(additional);
        // Selected_Info.this.remove(human_maker);
        // Selected_Info.this.remove(war_ship_maker);
        // Selected_Info.this.remove(fisher_ship_maker);

        invalidate();
        repaint();
    }

    @Override
    public void paint(Graphics arg0)
    {
        super.paint(arg0);
    }
    
    public void humanSelected(int number_of)
    {
        disableSelection();// removes last toolbar
        unit_border_layout.setShape(Finals.spining_human);
        add(unit_border_layout);

        details.setText("Human: " + number_of + " selected....each Damage: " + Finals.hit_damage[2]);
        add(details);
        add(background_label);
        invalidate();
    }

    public void warShipSelected(int number_of)
    {
        disableSelection();

        unit_border_layout.setShape(Finals.spining_war_ship);
        add(unit_border_layout);

        details.setText("War_Ship: " + number_of + " selected....each Damage:" + Finals.hit_damage[1]);
        add(details);
        add(background_label);
        invalidate();
    }

    public void fisherShipSelected(int number_of)
    {
        disableSelection();

        unit_border_layout.setShape(Finals.spining_fisher_ship);
        add(unit_border_layout);

        details.setText("Fisher_Ship: " + number_of + " selected....each Damage:" + Finals.hit_damage[0]);
        add(details);
        add(background_label);
        invalidate();
    }

    public void fisher_And_War_Ship(Vector<unit_controller> selected_units)
    {
        disableSelection();

        unit_border_layout.setShape(Finals.fisher_and_war_ship);
        add(unit_border_layout);

        int fisher_shipes = 0, war_ships = 0;
        for (int i = 0; i < selected_units.size(); i++)
        {
            if (selected_units.elementAt(i).mode == 0)
                fisher_shipes++;
            else if (selected_units.elementAt(i).mode == 1)
                war_ships++;
        }

        details.setText("War_Ship: " + war_ships + " selected....each Damage: 100");
        add(details);

        additional.setText("Fisher_Ship: " + fisher_shipes + " selected....each Damage: 10");
        add(additional);
        add(background_label);
        invalidate();
    }

    public void HQSelected(HQ_Main_Base HQ)
    {// HQ nation is HQ number
        disableSelection();
        unit_border_layout.setShape(Finals.Base_HQ_image);
        //unit_border_layout.setVisible(true);
        
        // details.setVisible(true);
        String details_text = "<html>Ressourses :<br>";
        details_text += "Wood     " + HQ.get_wood() + "<br>";
        details_text += "Iron     " + HQ.get_iron() + "<br>";
       // details_text += "Gold     " + HQ.get_gold() + "<br>";
        details_text += "Food     " + HQ.get_food() + "<br></html>";
        details.setText(details_text);
 
        this.add(details);
        this.add(list_of_buttons);
        this.add(unit_border_layout);
        this.add(exit_button);
        this.add(background_label);
        
        invalidate();
    }

    @Override
    public void actionPerformed(ActionEvent arg0)
    {
        if (arg0.getSource().equals(human_maker))
        {
            if(!game_frame.get_is_single_player()){
            Finals.client.sendMassage("make human "+game_panel.selected_HQ_number);
            }else{
                Finals.terminal.commandExecutor("make human "+game_panel.selected_HQ_number);
            }
                //            game_panel.makeHuman();
        }
        else if (arg0.getSource().equals(war_ship_maker))
        {
            if(!game_frame.get_is_single_player())
            Finals.client.sendMassage("make war_ship "+game_panel.selected_HQ_number);    
            else {
                Finals.terminal.commandExecutor("make war_ship "+game_panel.selected_HQ_number); 
            }
//            game_panel.make_ship(1);
        }
        else if (arg0.getSource().equals(fisher_ship_maker))
        {
            if(!game_frame.get_is_single_player())
            Finals.client.sendMassage("make fisher_ship "+game_panel.selected_HQ_number);
            else {
                Finals.terminal.commandExecutor("make fisher_ship "+game_panel.selected_HQ_number);
            }
            //game_panel.make_ship(0);
        }

    }

    private void change_look_and_feel()
    {
        try
        {
            UIManager.setLookAndFeel("com.jtattoo.plaf.texture.TextureLookAndFeel");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
}

class Border extends JLabel
{
    ImageIcon border_inside_shape;
    JLabel inside_border_labal;

    public Border(Point location, Dimension size)
    {
        setLocation(location);
        setSize(size);
        //setBackground(Color.white);
        border_inside_shape = new ImageIcon();
        inside_border_labal = new JLabel();
        inside_border_labal.setLocation(50, 50);
        inside_border_labal.setSize(this.getSize().width - 100, this.getSize().height - 100);
        add(inside_border_labal);
    }

    public void setShape(ImageIcon new_image)
    {
        inside_border_labal.setLocation(50, 50);
        inside_border_labal.setSize(this.getSize().width - 100, this.getSize().height - 100);
        border_inside_shape = new_image;
        inside_border_labal.setIcon(border_inside_shape);
        repaint();
    }

    public void paint(Graphics g)
    {
        super.paint(g);
        // g.fillRect(0, 0, this.getWidth(),this.getHeight());
        g.drawImage(Finals.border.getImage(), 0, 0, this.getSize().width, this.getSize().height, null);
    }
}

class Background_label_selected_info extends JPanel
{

    public Background_label_selected_info(Point location, Dimension size)
    {
        this.setLocation(location);
        this.setSize(size);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.drawImage(Finals.selected_info_background.getImage(), 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
