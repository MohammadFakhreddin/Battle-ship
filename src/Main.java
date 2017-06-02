import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class Main
{

    public static void main(String[] args)
    {   
        String input;
        changeLookAndFeel();
        do{
          //  JOptionPane.showInputDialog(null,"Enter Your Name").==JOptionPane.CANCEL_OPTION)
            input= JOptionPane.showInputDialog(null,"Enter Your Name");
            if(input==null){
                return;
            }
        }while(input.contains(" ")||"".equals(input));
        Finals.player_name=input;
        new Menu();
    }
    private static void changeLookAndFeel()
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
