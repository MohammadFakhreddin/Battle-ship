import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MoveAction;

public class Finals
{
    public static String player_name =null;
    public static final int port_number=1119;
    public static final int  broadcast_rate=2000;
    public static final String broadcast_auth="AP_Game";
    public static Command_executer terminal;
    public static Client client;
    
    public static int nation=-1;
    
    public static final Dimension zoom_point = new Dimension(2, 2);// scrolling the map makes this element change

    // preview
    public static boolean isDay = true;
    public static boolean season_changed = true;

    public static void changeDayOrNight()
    {
        if (isDay)
            isDay = false;
        else
            isDay = true;
    }

    public static void changeSeason()
    {
        System.out.println("changing season");
        switch (season)
        {
        case "Spring":
            season = "Summer";
            break;
        case "Summer":
            season = "Fall";
            break;
        case "Fall":
            season = "Winter";
            break;
        case "Winter":
            season = "Spring";
            break;
        }
        reAddress();
        season_changed = true;
    }

    public static String season = "Spring";
    public static String Image_Type = "RealMode";

    public static void first_Address()
    {
        season="Spring";
        WORKING_DIRECTORY=System.getProperty("user.dir")+"/Images/"+"RealMode"+"/"+ "Spring"+"/";
        for(int i=0 ;i<4;i++){
            String string = String.format(WORKING_DIRECTORY+"tree%d.png",i+1);
            pre_tree_icon[i] = new ImageIcon(string);
        }
        for(int i=0 ;i<sand_icon.length;i++){
            String string = String.format(WORKING_DIRECTORY+"sand%d.png",i);
            pre_sand_icon[i]= new ImageIcon(string);
        }

        for(int i=0 ;i<grass_icon.length;i++){
            String string = String.format(WORKING_DIRECTORY+"grass%d.png",i);
            pre_grass_icon[i]= new ImageIcon(string);
        }
        pre_water_shallow = new ImageIcon(WORKING_DIRECTORY+"FrozenWater.png");
        pre_water_shallow_gif = new ImageIcon(WORKING_DIRECTORY+"FrozenWater.gif");
        pre_deep_water = new ImageIcon(WORKING_DIRECTORY+"DeepWater.png");
        pre_deep_water_gif=new ImageIcon(WORKING_DIRECTORY+"DeepWater.gif");
        pre_tree_icon_image = new ImageIcon(WORKING_DIRECTORY+"Big_Tree.png");
        
            for(int i=0 ;i<tree_icon.length;i++){
                String string = String.format("tree%d.png",i+1);
                tree_icon[i] = new ImageIcon(string);
            }
            
            for(int i=0 ;i<Finals.sand_icon.length;i++){
                String string = String.format("sand%d.png",i);
                sand_icon[i]= new ImageIcon(string);
            }
            
            for(int i=0 ;i<Finals.grass_icon.length;i++){
                String string = String.format("grass%d.png",i);
                grass_icon[i]= new ImageIcon(string);
            }
            
            for(int i=0 ;i<moving_objects[0].length ;i++){
                String string  = String.format("Images//Ships//fisher_ship//ship%d.png",i);
                moving_objects[0][i]= new ImageIcon(string);
            }
            for(int i=0 ;i<moving_objects[1].length ; i++){
                String string  = String.format("Images//Ships//war_ship//ship%d.png",i);
                moving_objects[1][i]= new ImageIcon(string);
            }
            for(int i=0 ;i<moving_objects[2].length ; i++){
                String string  = String.format("Images//human//human%d.gif",i);
                moving_objects[2][i]= new ImageIcon(string);
            }
            for(int i=0 ;i<moving_objects_death[0].length ; i++){
                String string = String.format("Images//Ships//fisher_ship//sink//ship%d.gif",i);
                moving_objects_death[0][i] = new  ImageIcon(string); 
            }
            for(int i=0 ;i<moving_objects_death[1].length ; i++){
                String string = String.format("Images//Ships//war_ship//sink//ship%d.gif",i);
                moving_objects_death[1][i] = new  ImageIcon(string); 
            }
            for(int i=0 ;i<moving_objects_death[2].length ; i++){
                String string = String.format("Images//Ships//human//death//human%d.gif",i);
                moving_objects_death[2][i] = new  ImageIcon(string); 
            }
            
//              for(int i=0 ;i<ships1_shapes.length;i++){
//                String string  = String.format("Images//Ships//war_ship//ship%d.png",i);
//                ships1_shapes[i]= new ImageIcon(string);
//            }
//            for(int i=0 ; i<ship1_sink.length ;i++){
//                String string = String.format("Images//Ships//war_ship//sink//ship%d.gif",i);
//                ship1_sink[i] = new ImageIcon(string);
//            }
            
    }

    public static void reAddress()
    {
        WORKING_DIRECTORY = System.getProperty("user.dir") + "/Images/" + Image_Type + "/" + season + "/";
        for (int i = 0; i < 4; i++)
        {
            String string = String.format(WORKING_DIRECTORY + "tree%d.png", i + 1);
            pre_tree_icon[i] = new ImageIcon(string);
        }
        for (int i = 0; i < sand_icon.length; i++)
        {
            String string = String.format(WORKING_DIRECTORY + "sand%d.png", i);
            pre_sand_icon[i] = new ImageIcon(string);
        }

        for (int i = 0; i < grass_icon.length; i++)
        {
            String string = String.format(WORKING_DIRECTORY + "grass%d.png", i);
            pre_grass_icon[i] = new ImageIcon(string);
        }
        pre_water_shallow = new ImageIcon(WORKING_DIRECTORY + "FrozenWater.png");
        pre_water_shallow_gif = new ImageIcon(WORKING_DIRECTORY + "FrozenWater.gif");
        pre_deep_water = new ImageIcon(WORKING_DIRECTORY + "DeepWater.png");
        pre_deep_water_gif = new ImageIcon(WORKING_DIRECTORY + "DeepWater.gif");
        pre_tree_icon_image = new ImageIcon(WORKING_DIRECTORY + "Big_Tree.png");
    }

    public static final long DAY_LENGTH_IN_MILLISECOND_preview = 10;// changed for preview please reset to 50 for game
    public static final long DAY_LENGTH_IN_MILLISECOND_inGame= 1000;// changed for preview please reset to 50 for game
    
    public static String WORKING_DIRECTORY = System.getProperty("user.dir") + "/Images/" + Image_Type + "/" + season
            + "/";
    // ////////////////////////////////

    public static final Dimension MENU_SIZE = new Dimension(800, 600);
    public static final Point MENU_LOCATION = new Point(Toolkit.getDefaultToolkit().getScreenSize().width / 2
            - MENU_SIZE.width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2 - MENU_SIZE.height / 2);

    public static final Dimension BUTTON_SIZE = new Dimension(100, 100);

    public static Dimension PIC_SIZE = new Dimension(50, 50);
    public static Dimension tree_size = new Dimension(100, 100);
    public static Dimension iron_mountain_size = new Dimension(150, 150);

    public static Dimension MINI_MAP_SIZE = new Dimension(200, 200);
    public static Dimension MINI_MAP_PIC_SIZE = new Dimension(10, 10);
    public static Dimension MINI_MAP_TREE_SIZE = new Dimension(20, 20);
    public static Dimension MINI_MAP_IRON_MOUNTAIN_SIZE = new Dimension(40, 40);

    public static final Dimension MAP_EDITOR_SIZE = new Dimension(1200, 1000);
    public static final Point MAP_EDITOR_LOCATION = new Point(Toolkit.getDefaultToolkit().getScreenSize().width / 2
            - MAP_EDITOR_SIZE.width / 2, Toolkit.getDefaultToolkit().getScreenSize().height / 2
            - MAP_EDITOR_SIZE.height / 2);

    public static Dimension MAP_SIZE;

    public static final Point BOARD_LOCATION = new Point(100, 2 * Finals.BUTTON_SIZE.height);
    public static final Dimension BOARD_SIZE = new Dimension(1000, 700);
    // *new change for game frame *
    public static Point first_selected_point = new Point(0, 0);
    public static Point final_selected_point = new Point(0, 0);
    public static Point move_point = new Point(0, 0);
    public static boolean second_click;
    public static int[][] pixels_moving_objects = new int[1000][1000];
   
    //

    // 0==deep water //1==sand // 2==big tree //3==grass //4==deep water // 5==fish group //6==mountain //7==mine
    public static Integer[][] pixeles;// must_change//user_input_size
    public static Integer[][] pixeles_mode;
    
    public static Integer[][] move_posibilities_sea_unit ;// they_are_intiliazed_in_game_frame
    public static Integer[][] move_posibilities_land_unit;
    
    
    // public static void reset_map (int width,int height , boolean reset_pix){
    // if(reset_pix){
    // pixeles = new Integer[width][height];
    // pixeles_mode=new Integer[width][height];
    // for(int i=0 ;i<pixeles.length;i++){
    // for(int j=0 ;j<pixeles[i].length;j++){
    // pixeles[i][j]=4;
    // pixeles_mode[i][j]=0;
    // }
    // }
    // MAP_SIZE = new Dimension(PIC_SIZE.width*pixeles.length,PIC_SIZE.height*pixeles[0].length);
    // }
    //
    // reAddress();
    //
    // // //make the tree icons
    // for(int i=0 ;i<4;i++){
    // String string = String.format("tree%d.png",i+1);
    // tree_icon[i] = new ImageIcon(string);
    // }
    //
    // for(int i=0 ;i<sand_icon.length;i++){
    // String string = String.format("sand%d.png",i);
    // sand_icon[i]= new ImageIcon(string);
    // }
    //
    // for(int i=0 ;i<grass_icon.length;i++){
    // String string = String.format("grass%d.png",i);
    // grass_icon[i]= new ImageIcon(string);
    // }
    //
    // // //return pixels;
    // }
    // ///for save and load and reseting the map

    public static boolean in_board_panel = false;// indicates that the mouse in inside the map_board or not
   // public static boolean mini_map_exist = false; // shows that mini_map exist or not for avoiding openning more than
                                                  // one mini map

    // Image icons
    public static ImageIcon water_shallow = new ImageIcon("FrozenWater.png");
    public static ImageIcon[] sand_icon = new ImageIcon[16];
    public static ImageIcon[] tree_icon = new ImageIcon[4];
    public static ImageIcon deep_water = new ImageIcon("DeepWater.png");
    public static ImageIcon[] grass_icon = new ImageIcon[16];
    public static ImageIcon tree_icon_image = new ImageIcon("Big_Tree.png");
    public static ImageIcon fish_group_icon = new ImageIcon("fish_icon.png");
    public static ImageIcon fish_group = new ImageIcon("fishgroup.gif");
    public static ImageIcon iron_mountain = new ImageIcon("iron_mountain.png");
    public static ImageIcon mine = new ImageIcon("mine.png");
    public static ImageIcon Base_HQ_image = new ImageIcon("Base.png");

    public static ImageIcon pre_water_shallow = new ImageIcon(WORKING_DIRECTORY + "FrozenWater.png");
    public static ImageIcon pre_water_shallow_gif = new ImageIcon(WORKING_DIRECTORY + "FrozenWater.gif");
    public static ImageIcon[] pre_sand_icon = new ImageIcon[16];
    public static ImageIcon[] pre_tree_icon = new ImageIcon[4];
    public static ImageIcon pre_deep_water = new ImageIcon(WORKING_DIRECTORY + "DeepWater.png");
    public static ImageIcon pre_deep_water_gif = new ImageIcon(WORKING_DIRECTORY + "DeepWater.gif");
    public static ImageIcon[] pre_grass_icon = new ImageIcon[16];
    public static ImageIcon pre_tree_icon_image = new ImageIcon(WORKING_DIRECTORY + "Big_Tree.png");

    public static final ImageIcon spining_war_ship = new ImageIcon("Images//Ships//war_ship//spining.gif");//war_ship alone 
    public static final ImageIcon spining_fisher_ship = new ImageIcon("Images//Ships//fisher_ship//spining.gif");//fisher_ship alone
    public static final ImageIcon spining_human = new ImageIcon("Images//human//spining.gif");
    public static final ImageIcon fisher_and_war_ship = new ImageIcon("Images//Ships//war_and_fisher_ship.png");//war_ship and fisher_ship
    public static final ImageIcon explosion = new ImageIcon("exp.gif"); // units fight gif
    
    public static final ImageIcon border =  new ImageIcon("border.png");// it is selected unit border
    public static final ImageIcon selected_info_background = new ImageIcon("selected_info_background.jpg");
    public static final ImageIcon exit_button = new ImageIcon("exit_button.png");
    public static ImageIcon[][] moving_objects = new ImageIcon[3][13];
    public static ImageIcon[][] moving_objects_death = new ImageIcon[3][13];
    // public static ImageIcon[] ships1_shapes = new ImageIcon[13];
    // public static ImageIcon[] ship1_sink = new ImageIcon[13];
    // //music part
    public static File clicksound = new File("click.wav");
    public static File menusound = new File("main_music.wav");

    // maximum health
    public static final int[] maximum_health = new int[] { 500, 1000, 10 ,5000};
    // 1-fisher_ship 2-battle_ship 3-human
    // hit points
    public static final int[] hit_damage = new int[] { 10, 100, 2 , 500 };
    // like upper one
    public static final int[] range = new int[] {100,200,50};
    public static final Dimension[] unit_size = new Dimension[]{new Dimension(50,50),new Dimension(100,100),new Dimension(50,50)};
    public static final Dimension health_bar = new Dimension(50, 7);
    
    
}
