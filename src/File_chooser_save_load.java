
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.io.*;
import java.util.Random;

public class File_chooser_save_load extends JPanel
{
    boolean load_successful=false;
    Font font = new Font("Tahoma",20,20);
    public JButton loadButton;
    private JButton saveButton;
    private File selected_file ;
    public File_chooser_save_load() 
    {
        this.setLayout(new GridLayout(2,1));
        final JFileChooser filechooser = new JFileChooser();
        final JLabel saveDescribtion = new JLabel();
        saveDescribtion.setSize(200, 20);
        saveDescribtion.setLocation(100, 12);
        final JLabel loadDescribtion = new JLabel();
        loadDescribtion.setSize(300, 20);
        loadDescribtion.setLocation(100, 40);
        this.setSize(375, 100);
        this.setLocation(100, 100);
        saveButton = new JButton("Save");
        saveButton.setFont(font);
        //saveButton.setFocusable(false);
        saveButton.setBackground(Color.white);
        loadButton = new JButton("Load");
        loadButton.setFont(font);
        //loadButton.setFocusable(false);
        loadButton.setBackground(Color.white);
        //saveButton.setSize(70, 20);
        //loadButton.setSize(70, 20);
        //saveButton.setLocation(25, 12);
        //loadButton.setLocation(25, 40);
        saveDescribtion.setVisible(true);
        this.add(saveButton);
        //this.add(saveDescribtion);
        this.add(loadButton);
        //this.add(loadDescribtion);
        saveButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if (filechooser.showDialog(File_chooser_save_load.this, "Save") == JFileChooser.APPROVE_OPTION)
                    saveDescribtion.setText(isCorrectFilePath(filechooser.getSelectedFile(), 1));
                if (saveDescribtion.getText().equalsIgnoreCase("Save Directory Founded"))
                {
                    try
                    {
                        saveMap(filechooser.getSelectedFile(), Finals.pixeles,Finals.pixeles.length,Finals.pixeles[0].length);
                    }
                    catch (Exception error)
                    {
                        System.err.println(error.getMessage());
                    }
                }
            }
        });
        loadButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                FileNameExtensionFilter fef = new FileNameExtensionFilter("Save", "json");
                filechooser.setFileFilter(fef);
                if (filechooser.showDialog(File_chooser_save_load.this, "Load") == JFileChooser.APPROVE_OPTION)
                    loadDescribtion.setText(isCorrectFilePath(filechooser.getSelectedFile(), 2));
                if (loadDescribtion.getText().equalsIgnoreCase("Load File Founded"))
                {
                    try
                    {
                        selected_file = filechooser.getSelectedFile();//new change
                        loadMap(filechooser.getSelectedFile());
                    }
                    catch (Exception error)
                    {
                        System.err.println(error.getMessage());
                    }
                }
            }
        });
        this.setVisible(true);
    }

    private String isCorrectFilePath(File file, int type)  
 // if you want to delete labels do not delete the cases they are needed for loading correctly    
    {
        if (file == null)
            return "No File Selected";
        switch (type)
        {
        case 1:
        {
            if (!(file.isDirectory()))
            {
                return "Save Directory Not Found";
            }
            else
            {
                return "Save Directory Founded";
            }
        }
        case 2:
        {
            String extension = new String();

            int i = file.getAbsolutePath().lastIndexOf('.');
            if (i > 0)
            {
                extension = file.getAbsolutePath().substring(i + 1);
                if (!(extension.equalsIgnoreCase("json")))
                {
                    load_successful=false;
                    return "Load File Extension Not Supported";
                }
                else
                {
                    load_successful=true;
                    return "Load File Founded";
                }
            }
        }
        }
        return " ";
    }

    private Mini_Map mini_map;
    public void mini_map_getter (Mini_Map mini_map){
        this.mini_map=mini_map;
    }
    private Map map;
    public void map_getter(Map map){
        this.map=map;
    }
       
    private void loadMap(File file) throws ParseException, IOException
    {
        int width = 0;
        int height = 0;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonobject = new JSONObject();
        try
        {
            jsonobject = (JSONObject) jsonParser.parse(new FileReader(file));
        }
        catch (FileNotFoundException e)
        {
            throw new FileNotFoundException("Error In Opening File");
        }
        catch (IOException e)
        {
            throw new IOException("Error In Reading From File");
        }
        width = Integer.parseInt(jsonobject.get("width").toString());
        height = Integer.parseInt(jsonobject.get("height").toString());
        JSONArray jsonArray = (JSONArray) jsonobject.get("map");
        Integer[][] load = new Integer[width][height];
        for (int i = 0; i < width; i++)
        {
            JSONArray tempJsonArray = (JSONArray) jsonArray.get(i);
            for (int j = 0; j < height; j++)
            {
                load[i][j] = Integer.parseInt(tempJsonArray.get(j).toString());
            }
        }
        
       
        Finals.pixeles = load.clone();
        Finals.pixeles_mode = new Integer[Finals.pixeles.length][Finals.pixeles[0].length];
//        popup("load");
        ///please do not delete this part
        Random rand = new Random();
        for(int i=0 ;i<Finals.pixeles_mode.length;i++){
            for(int j=0 ;j<Finals.pixeles_mode[i].length;j++){
                if(Finals.pixeles[i][j]==2){
                    Finals.pixeles_mode[i][j]=rand.nextInt(Finals.tree_icon.length);
                    System.out.println("loading");
                }
            }
        }
        try{
        map.dispatchEvent(new ComponentEvent(this,Messages.reset_map));
        mini_map.dispatchEvent(new ComponentEvent(this, Messages.reset_mini_map));
        }catch(Exception e){
            System.out.println("no map or mini map exists");
        }
    }
     
    
    public static void loadMap(String map_String) throws ParseException, IOException
    {
        int width = 0;
        int height = 0;
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonobject = new JSONObject();
        jsonobject = (JSONObject) jsonParser.parse( map_String);
        width = Integer.parseInt(jsonobject.get("width").toString());
        height = Integer.parseInt(jsonobject.get("height").toString());
        JSONArray jsonArray = (JSONArray) jsonobject.get("map");
        Integer[][] load = new Integer[width][height];
        for (int i = 0; i < width; i++)
        {
            JSONArray tempJsonArray = (JSONArray) jsonArray.get(i);
            for (int j = 0; j < height; j++)
            {
                load[i][j] = Integer.parseInt(tempJsonArray.get(j).toString());
            }
        }
        
       
        Finals.pixeles = load.clone();
        Finals.pixeles_mode = new Integer[Finals.pixeles.length][Finals.pixeles[0].length];
//        popup("load");
        ///please do not delete this part
        Random rand = new Random();
        for(int i=0 ;i<Finals.pixeles_mode.length;i++){
            for(int j=0 ;j<Finals.pixeles_mode[i].length;j++){
                if(Finals.pixeles[i][j]==2){
                    Finals.pixeles_mode[i][j]=rand.nextInt(Finals.tree_icon.length);
                    System.out.println("loading");
                }
            }
        }
}
    
    
    
    

    private void saveMap(File file, Integer[][] mapp, int width, int height) throws IOException
    {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < width; i++)
        {
            JSONArray jsonArray1 = new JSONArray();
            for (int j = 0; j < height; j++)
            {
                jsonArray1.add(j, mapp[i][j]);
            }
            jsonArray.add(i, jsonArray1);
        }
        FileWriter fw = null;
        fw = new FileWriter(file.getAbsolutePath() + "/save.json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("width", width);
        jsonObject.put("height", height);
        jsonObject.put("map", jsonArray);
        fw.write(jsonObject.toJSONString());
      //  popup("save");
        fw.flush();
        fw.close();
    }
    public void popup(String in) {
        JPopupMenu jDialog = new JPopupMenu();
        jDialog.setLayout(new FlowLayout());
        jDialog.setSize(250, 75);
        jDialog.setLocation((int)((Toolkit.getDefaultToolkit().getScreenSize().getWidth()-jDialog.getWidth())/2),(int)((Toolkit.getDefaultToolkit().getScreenSize().getHeight()-jDialog.getHeight())/2));
        JProgressBar jProgressBar=new JProgressBar();
        jProgressBar.setMaximum(1000);
        jProgressBar.setMinimum(0);
        jProgressBar.setSize(50,50);
        jDialog.add(jProgressBar);
        JLabel jLabel = new JLabel(in+"...");
        jLabel.setSize(200,100);
        jDialog.add(jLabel);
        jDialog.setVisible(true);
        for(int i=0;i<1000;i++)
        {
            jProgressBar.setValue(i);
            try {
                Thread.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public ActionListener getLoadActionListner()
    {
        return loadButton.getActionListeners()[0];
    }
    public ActionListener getSaveActionListner()
    {
        return saveButton.getActionListeners()[0];
    }
    //new change
    public boolean get_load_result (){
        return load_successful;
    }
    
    public File get_selected_file (){
        return selected_file;
    }
}






