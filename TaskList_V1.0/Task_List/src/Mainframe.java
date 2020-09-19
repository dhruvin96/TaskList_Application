/*
 *This software is made by Dhruvin Bharatbhai Desai.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dhruvin
 */
public class Mainframe extends JFrame implements ActionListener {

    private ArrayList<TaskTimer> TaskList = new ArrayList<TaskTimer>();
    private JPanel container;
    private GridBagConstraints container_constraints;
    private int taskcount=0;
  
    public static void main(String[] args) throws InterruptedException {
        Mainframe mfobj = new Mainframe();
        mfobj.main_creator();
        System.out.println("\n\nMainframe Running.......\n");
        
    }
    
    protected void TaskListEditDelete(TaskTimer TT){
        this.TaskList.remove(TT);
        this.taskcount--;
    }
    
    protected void TaskListDelete(TaskTimer TT){
        this.TaskList.remove(TT);
        this.taskcount--;
        this.container.removeAll();
        this.container.repaint();
        DrawTaskComponent();
    }
    
    protected void TaskListEditModifier(TaskTimer TT){
        TaskList.add(TT);
        this.taskcount++;
        Collections.sort(TaskList, TaskTimer.sort_by_time);
        this.container.removeAll();
        DrawTaskComponent();
    }
    protected void TaskListModifier(TaskTimer TT){
        JPanel temp_panel = TT.countpanel();
        TaskList.add(TT);
        this.taskcount++;
        Collections.sort(TaskList, TaskTimer.sort_by_time);
        this.container.removeAll();
        DrawTaskComponent();
    }
    
    protected void TaskListAdd(TaskTimer TT){
        TT.countpanel();
        TaskList.add(TT);
        this.taskcount++;
    }
    
    private void DrawTaskComponent(){
        int i = 0;
        for(TaskTimer ttobj : TaskList){
            container_constraints.fill = GridBagConstraints.HORIZONTAL;
            container_constraints.gridx = 0;
            container_constraints.gridy = i;
            i++;
            container_constraints.ipady = 20;
            //container.add_page(ct.countpanel(), container_constraints);
            container.add(ttobj.content_panel, container_constraints);
            container.revalidate();
        }
        
    }
    
    protected void StoreData(){
        String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Local";
        path += File.separator + "TaskList_Dhruvin_app";
        File customDir = new File(path);
        boolean STORE = false;
        
        if (customDir.exists()) {
            //System.out.println(customDir + " already exists");
            STORE = true;
        } else if (customDir.mkdirs()) {
            //System.out.println(customDir + " was created");
            STORE = true;
        } else {
            path = System.getProperty("user.home");
            path += File.separator + "TaskList_Dhruvin_app";
            customDir = new File(path);

            if (customDir.exists()) {
                //System.out.println(customDir + " already exists");
                STORE = true;
            } else if (customDir.mkdirs()) {
                //System.out.println(customDir + " was created");
                STORE = true;
            } else {
                System.out.println(customDir + " was not created");
            }
            
        }
        
        if(STORE){
            try {
                String filepath = path + File.separator + "TaskList.txt";
                FileWriter datafw = new FileWriter(filepath);
                String tempwrite = "";
                for(TaskTimer tt : TaskList){
                    datafw.write(tt.DataToStr());
                    datafw.write("\n");
                }
                datafw.close();
            }
            catch (FileNotFoundException ex) {
                Logger.getLogger(Mainframe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mainframe.class.getName()).log(Level.SEVERE, null, ex);
            }            
        }
        else{
            System.out.println("Not able to store data.");
        }

    }
    
    protected void LoadData(){
        String path = System.getProperty("user.home") + File.separator + "AppData" + File.separator + "Local";
        path += File.separator + "TaskList_Dhruvin_app";
        File customDir = new File(path);
        boolean LOAD = false;
        
        if (customDir.exists()) {
            //System.out.println(customDir + " already exists");
            LOAD = true;
        } else if (customDir.mkdirs()) {
            //System.out.println(customDir + " was created");
            LOAD = true;
        } else {
            path = System.getProperty("user.home");
            path += File.separator + "TaskList_Dhruvin_app";
            customDir = new File(path);

            if (customDir.exists()) {
                //System.out.println(customDir + " already exists");
                LOAD = true;
            } else if (customDir.mkdirs()) {
                //System.out.println(customDir + " was created");
                LOAD = true;
            } else {
                System.out.println(customDir + " was not created");
            }
            
        }
        
        if(LOAD){
            try {
                String filepath = path + File.separator + "TaskList.txt";
                BufferedReader databr = new BufferedReader(new FileReader(filepath));
                
                String tdata = null;
                TaskTimer temptt = null;
                while((tdata = databr.readLine()) != null){
                    temptt = new TaskTimer();
                    temptt.StrToData(this, tdata);
                    TaskListAdd(temptt);
                }
         
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Mainframe.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mainframe.class.getName()).log(Level.SEVERE, null, ex);
            }
            Collections.sort(TaskList, TaskTimer.sort_by_time);
            DrawTaskComponent();
        }

    }
    
    /*private void setIcon()
    {
         setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("D.jpg")));
    }*/
    
    private void main_creator(){
        
        JFrame mainframe = new JFrame("Task Sprint");
        //ImageIcon icons;
        mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainframe.addWindowListener(new java.awt.event.WindowAdapter() {
            Mainframe mf;
            public WindowAdapter Init(Mainframe mo){
                this.mf = mo;
                return this;
            }
            public void windowClosing(java.awt.event.WindowEvent e) {
                this.mf.StoreData();
                this.mf.Destroyer();
                System.out.println("Bye Bye exiting the tasklist application program...");
                System.exit(0);
            }
        }.Init(this));
        mainframe.setBounds(430, 150, 1000, 600);
        //mainframe.setPreferredSize(new Dimension(500,500)); 
//        mainframe.setLayout(null);

        JMenuBar menu = new JMenuBar();
        JMenu add1 = new JMenu("Add");
        JMenu setting1 = new JMenu("Settings");
        JMenuItem add_task, settings;
        add_task = new JMenuItem("Add");
        settings = new JMenuItem("settings");
        // Action listener for add_page.
        add_task.addActionListener(this);
        add1.add(add_task);
        
        setting1.add(settings);
        menu.add(add1);
        menu.add(setting1);
        mainframe.setJMenuBar(menu);
        
        
        Insets insetmain = mainframe.getInsets();
        Dimension dim;
        
        JLabel background = new JLabel();
        background.setLayout(null);
        background.setPreferredSize(new Dimension(1000,600));
        dim = background.getPreferredSize();
        background.setBounds(insetmain.left, insetmain.top, dim.width, dim.height);
        mainframe.revalidate();
        mainframe.add(background);
       
        /*
        JPanel counterp = new JPanel();
        //counterp.setLayout(null);
        JLabel total_task = new JLabel("Pending Tasks : 0");
        counterp.setPreferredSize(new Dimension(280,30));
        dim = counterp.getPreferredSize();
        counterp.setBounds(insetmain.left + 720, insetmain.top + 10, dim.width, dim.height);
        counterp.add_page(total_task);
        mainframe.revalidate();
        mainframe.add_page(counterp);
        */
        

        container = new JPanel();
        container.setLayout(null);
        GridBagLayout container_layout = new GridBagLayout();
        container.setLayout(container_layout);
        container_constraints = new GridBagConstraints();
        JScrollPane pane1 = new JScrollPane(container);
        pane1.getVerticalScrollBar().setUnitIncrement(20);
        dim = pane1.getPreferredSize();
        pane1.setBounds(insetmain.left, insetmain.top, dim.width, dim.height);
        //container.setBounds(insetmain.left, insetmain.top +100, container.getWidth(), container.getHeight());
        mainframe.revalidate();
        mainframe.add(pane1);       
        //icons = new ImageIcon("dhruvin.jpg");
        //mainframe.setIconImage(icons.getImage());
        //mainframe.setIconImage(Toolkit.getDefaultToolkit().getImage("D.jpg"));

        
        //container.setBackground(Color.green);
        
        //Important in every iteration call revalidate() function and of changing colour and somet thing like that call repaint() method
        //Also remember these two methods as it will be usefull to you in your need in these scrollpane.

        
        mainframe.setVisible(true);
        LoadData();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent action_event){
        //System.out.println(action_event.getActionCommand());
        String command = action_event.getActionCommand();
        
        switch(command){
            case "Add":
                System.out.println("Loading add task page....");
                add_page temp = new add_page(this);
                temp.setVisible(true);
                System.out.println("Add task page loaded Succesfully.");
                break;
                
            default:
                System.out.println("\nCommand \""+command+"\" not found.");
                break;
                
        }
    }
    
    public void finalize(){
        Destroyer();
    }
    
    private void Destroyer(){
        //System.out.println("Destroying all MainFrame swing object.");
        
        for(TaskTimer ttob : TaskList){
            ttob.BulkDelete();
        }
        TaskList.clear();
        TaskList = null;
        
        try{
            for(Component comp : container.getComponents()){
                for(ComponentListener complist : comp.getComponentListeners()){
                    comp.removeComponentListener(complist);
                }
                comp = null;
            }
            
            for(ComponentListener complist : container.getComponentListeners()){
                container.removeComponentListener(complist);
            }
            container.removeAll();
            container = null;    
        }
        catch(Exception finalerr){
            System.out.println("Not able to remove all swing components from MainFrame");
        }
        
        //System.out.println("All MainFrame swing object destroyed.");
    }
    
    public String getAppletInfo()
    {
        return "Made By Dhruvin Desai";
    }
    
}
