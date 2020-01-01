/*
 *This software is made by Dhruvin Bharatbhai Desai.
 */

import java.awt.Color;
import java.awt.Component;
import java.awt.MouseInfo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Dhruvin
 */

public class TaskTimer extends JFrame
{

    
    protected JPanel content_panel;
    private String set_task = null;
    private String set_date = null;
    private String set_time = null;
    private Mainframe MainObj;
    protected long count_time=0;
    protected JLabel time_label;
    protected JTextArea area;
    protected Timer time;
    protected JButton edit_button,delete_button;
    private boolean HOVER_ACTION_CONTROL_FLAG = true;
    
    public TaskTimer(){
        
    }
    
    public TaskTimer(Mainframe MainObj, String set_task, String set_date, String set_time){
        this.set_date = set_date;
        this.set_time = set_time;
        this.set_task = set_task;
        this.MainObj = MainObj;
    }
    
    protected void TTSetVariable(String set_task, String set_date, String set_time){
        this.set_date = set_date;
        this.set_time = set_time;
        this.set_task = set_task;
    }
    
    protected String DataToStr(){
        // Task\0date\0time\0count_time
        return (this.set_task+'\0'+this.set_date+'\0'+this.set_time+'\0'+Long.toString(count_time));
    }
    
    protected void StrToData(Mainframe MainObj, String data){
        String temp[] = data.split("\0");
        this.set_task = temp[0];
        this.set_date = temp[1];
        this.set_time = temp[2];
        
        Date dt=null;
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            dt = df.parse(set_date);
            Calendar today = Calendar.getInstance();
            this.count_time = dt.getTime() - today.getTimeInMillis();
        } catch (ParseException ex) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex);
        }  
        this.MainObj = MainObj;
    }
    
    public JPanel countpanel()
    {
        
        
        JScrollPane text_pane;
        GroupLayout panel_layout;
        SimpleDateFormat time;
        content_panel = new JPanel();
        //content_panel.setBackground(Color.red);
        time_label = new JLabel("Calculating.  .  .  .  .");
        area = new JTextArea();
        edit_button = new JButton();
        edit_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        edit_button.setText("Edit");
        
        delete_button = new JButton();
        delete_button.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        delete_button.setText("Delete");

        edit_button.addMouseListener(new MouseAdapter() {
            Mainframe mfobj;
            TaskTimer ttobj;
            public MouseAdapter Init(Mainframe mf, TaskTimer tt){
                this.mfobj = mf;
                this.ttobj = tt;
                return this;
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Add edit event.
                ttobj.EditTask();
            }
        }.Init(this.MainObj, this));
        
        delete_button.addMouseListener(new MouseAdapter() {
            Mainframe mfobj;
            TaskTimer ttobj;
            public MouseAdapter Init(Mainframe mf, TaskTimer tt){
                this.mfobj = mf;
                this.ttobj = tt;
                return this;
            }
            
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Add delete event.
                ttobj.DeleteTask();
            }
        }.Init(this.MainObj, this));
        
        //System.out.println("Content_panel Height: "+ content_panel.getHeight() +" Content_panel Width: "+ content_panel.getWidth());
        // Content_panel Height: 120 Content_panel Width: 383
        //content_panel.setSize(400, 150);
        area.setRows(2);
        area.setColumns(30);

        area.setText(set_task);
        area.setEditable(false);
        text_pane = new JScrollPane();
        text_pane.setViewportView(area);
        panel_layout = new GroupLayout(content_panel);
        panel_layout.setAutoCreateContainerGaps(true);
        content_panel.setLayout(panel_layout);

        panel_layout.setHorizontalGroup(panel_layout.createSequentialGroup()
                .addGroup(panel_layout.createParallelGroup()
                        .addGap(50)
                        .addGap(50))
                .addGroup(panel_layout.createParallelGroup()
                        .addComponent(text_pane)
                        .addComponent(time_label))
                .addGroup(panel_layout.createParallelGroup()
                        .addGap(50)
                        .addGap(50))
        );

        panel_layout.setVerticalGroup(panel_layout.createSequentialGroup()
                .addGroup(panel_layout.createParallelGroup()
                        .addGap(50)
                        .addGap(50)
                        .addGap(50))
                .addGroup(panel_layout.createParallelGroup()
                        .addGap(50)
                        .addComponent(text_pane)
                        .addGap(50))
                .addGroup(panel_layout.createParallelGroup()
                        .addGap(50)
                        .addComponent(time_label)
                        .addGap(50))

        );
        
        content_panel.addMouseListener(new MouseAdapter(){
            Mainframe mobj;
            
            public MouseAdapter Init(Mainframe mf){
                this.mobj = mf;
                return this;
            }
            
            public void mouseEntered(MouseEvent me) {
                //System.out.println("Entered...");
                //System.out.println("Content_panel Height: "+ content_panel.getHeight() +" Content_panel Width: "+ content_panel.getWidth());
                if(HOVER_ACTION_CONTROL_FLAG){
                    content_panel.removeAll();
                    
                    panel_layout.setHorizontalGroup(panel_layout.createSequentialGroup()
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(133)
                                    .addGap(133)
                                    .addGap(133))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addComponent(edit_button)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addComponent(delete_button)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(133)
                                    .addGap(133)
                                    .addGap(133))
                    );

                    panel_layout.setVerticalGroup(panel_layout.createSequentialGroup()
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addGap(50)
                                    .addGap(50)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addComponent(edit_button)
                                    .addComponent(delete_button)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addGap(50)
                                    .addGap(50)
                                    .addGap(50))

                    );
                    content_panel.revalidate();
                    HOVER_ACTION_CONTROL_FLAG = false;
                }
                
             }
             public void mouseExited(MouseEvent me) {
                //System.out.println("Exited...");
                //System.out.println("Content_panel Height: "+ content_panel.getHeight() +" Content_panel Width: "+ content_panel.getWidth());
                if(MouseInfo.getPointerInfo().getLocation().x >= content_panel.getLocationOnScreen().x
                    && MouseInfo.getPointerInfo().getLocation().x <= content_panel.getLocationOnScreen().x + content_panel.getWidth()
                    && MouseInfo.getPointerInfo().getLocation().y >= content_panel.getLocationOnScreen().y
                    && MouseInfo.getPointerInfo().getLocation().y <= content_panel.getLocationOnScreen().y + content_panel.getHeight()){
                    HOVER_ACTION_CONTROL_FLAG = false;
                }
                else{
                    HOVER_ACTION_CONTROL_FLAG = true;
                    content_panel.removeAll();
                    
                    panel_layout.setHorizontalGroup(panel_layout.createSequentialGroup()
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addComponent(text_pane)
                                    .addComponent(time_label))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addGap(50))
                    );

                    panel_layout.setVerticalGroup(panel_layout.createSequentialGroup()
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addGap(50)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addComponent(text_pane)
                                    .addGap(50))
                            .addGroup(panel_layout.createParallelGroup()
                                    .addGap(50)
                                    .addComponent(time_label)
                                    .addGap(50))

                    );
                    content_panel.revalidate();  
                    
                }    
                
             }
        }.Init(this.MainObj));
        
        CountDownTimer();
        
        return content_panel;
    }
    
    void ending()
    {
        //System.out.println("Disposing content...................");
        this.dispose();
        //System.out.println("Object disposed");
        
    }
    
    public static Comparator<TaskTimer> sort_by_time = new Comparator<TaskTimer>() {

	public int compare(TaskTimer tt1, TaskTimer tt2) {

	   long timediff1 = tt1.count_time;
	   long timediff2 = tt2.count_time;

	   /*For ascending order*/
	   return (int) (timediff1-timediff2);

	   /*For descending order*/
	   //timediff2-timediff1;
        }
    };
    
    public void CountDownTimer(){
        time = new Timer();
        TimerTask ct = new CustomTimer(this);
        time.scheduleAtFixedRate(ct, 1000, 1000);
    }
    
    protected void EditTask(){
        this.time.cancel();
        Date dt=null;
        DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
        try {
            dt = df.parse(set_date);
            add_page editpg = new add_page(this.MainObj, this, true, this.set_task, dt);
            editpg.setVisible(true);
        } catch (ParseException ex) {
            Logger.getLogger(TaskTimer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    protected void DeleteTask(){
        this.time.cancel();
        this.time = null;
        this.MainObj.TaskListDelete(this);
        dispose();
        Destroyer();
    }
    
    protected void BulkDelete(){
        this.time.cancel();
        this.time = null;
        dispose();
        Destroyer();
    }
    protected void Destroyer(){
        //System.out.println("Destroying all TaskTimer swing object.");
        
        try{
            for(Component comp : content_panel.getComponents()){
                for(ComponentListener complist : comp.getComponentListeners()){
                    comp.removeComponentListener(complist);
                }
                comp = null;
            }
            for(ComponentListener complist : content_panel.getComponentListeners()){
                content_panel.removeComponentListener(complist);
            }
            content_panel.removeAll();
            content_panel = null;    
        }
        catch(Exception finalerr){
            System.out.println("Not able to remove all swing components from TaskTimer");
        }
        
        //System.out.println("All TaskTimer swing object destroyed.");
    }
    
    public void finalize(){
        Destroyer();
    }
    
    public String getAppletInfo()
    {
        return "Made By Dhruvin Desai";
    }
    
    
}

class CustomTimer extends TimerTask{
    
    long timercount = 0;
    protected TaskTimer tt;
    
    public CustomTimer(TaskTimer ttobj){
        this.timercount = ttobj.count_time;
        this.tt = ttobj;
    }
    @Override
    public void run() {
        if(this.timercount <= 0){
            this.tt.time.cancel();
            this.tt.content_panel.setBackground(Color.red);
        }
        this.timercount = this.timercount - 1000;
        this.tt.count_time = this.timercount;
        long days = TimeUnit.MILLISECONDS.toDays(this.timercount);
        long hours = TimeUnit.MILLISECONDS.toHours(this.timercount) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(this.timercount));
        long minutes = TimeUnit.MILLISECONDS.toMinutes(this.timercount) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(this.timercount));
        long seconds = TimeUnit.MILLISECONDS.toSeconds(this.timercount) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this.timercount));
        this.tt.time_label.setText("Time Left--> "+days+" : "+hours+" : "+minutes+" : "+seconds);
    }
    
}