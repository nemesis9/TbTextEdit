/*
 *  TbTextEdit.java
 */ 

package tbtextedit;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;
import tbtextedit.TbDocListener;
import tbtextedit.TbMouseListener;
import tbtextedit.TbChangeListener;

/* TbTextEdit.java can use images/middle.gif if desired */
//650x460

/****************************

    TbTextEdit

****************************/
public class TbTextEdit implements ActionListener {    
    static JFrame mainFrame;
    TabManager tabMgr;
    JTextArea output = null;
    boolean Debug = false;


    public JMenuBar createMenuBar() {
        JMenuBar menuBar;
        JMenu menu, submenu;
        JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        JCheckBoxMenuItem cbMenuItem;

        //Create the menu bar.
        menuBar = new JMenuBar();

        //Build the first menu.
        menu = new JMenu("Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
                "The only menu in this program that has menu items");
        menuBar.add(menu);

        //a group of JMenuItems
        menuItem = new JMenuItem("New File",
                                 KeyEvent.VK_N);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_1, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This item opens a new file");
        menuItem.setActionCommand("NewFile");
        menuItem.addActionListener(this);
        menu.add(menuItem);


        menuItem = new JMenuItem("Open File",
                                 KeyEvent.VK_O);
        //menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_2, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This item opens a spec file");
        menuItem.setActionCommand("OpenFile");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Quit", KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_3, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This item quits the program");
        menuItem.setActionCommand("Quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        menuItem = new JMenuItem("Help", KeyEvent.VK_H);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_4, ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
                "This item brings up help for the program");
        menuItem.setActionCommand("Help");
        menuItem.addActionListener(this);
        menu.add(menuItem);


        //I've taken these out for now
        //ImageIcon icon = createImageIcon("images/middle.gif");
        //menuItem = new JMenuItem("Both text and icon", icon);
        //menuItem.setMnemonic(KeyEvent.VK_B);
        //menu.add(menuItem);

        //menuItem = new JMenuItem(icon);
        //menuItem.setMnemonic(KeyEvent.VK_D);
        //menu.add(menuItem);

        //a group of radio button menu items
        //menu.addSeparator();
        //ButtonGroup group = new ButtonGroup();

        //rbMenuItem = new JRadioButtonMenuItem("A radio button menu item");
        //rbMenuItem.setSelected(true);
        //rbMenuItem.setMnemonic(KeyEvent.VK_R);
        //group.add(rbMenuItem);
        //menu.add(rbMenuItem);

        //rbMenuItem = new JRadioButtonMenuItem("Another one");
        //rbMenuItem.setMnemonic(KeyEvent.VK_O);
        //group.add(rbMenuItem);
        //menu.add(rbMenuItem);

        //a group of check box menu items
        //menu.addSeparator();
        //cbMenuItem = new JCheckBoxMenuItem("A check box menu item");
        //cbMenuItem.setMnemonic(KeyEvent.VK_C);
        //menu.add(cbMenuItem);

        //cbMenuItem = new JCheckBoxMenuItem("Another one");
        //cbMenuItem.setMnemonic(KeyEvent.VK_H);
        //menu.add(cbMenuItem);

        //a submenu
        //menu.addSeparator();
        //submenu = new JMenu("A submenu");
        //submenu.setMnemonic(KeyEvent.VK_S);

        //System.out.println("Building the submenu\n");
        //menuItem = new JMenuItem("An item in the submenu");
        //menuItem.setAccelerator(KeyStroke.getKeyStroke(
        //        KeyEvent.VK_2, ActionEvent.ALT_MASK));
        //submenu.add(menuItem);

        //menuItem = new JMenuItem("Another item");
        //submenu.add(menuItem);
        //menu.add(submenu);

        //Build second menu in the menu bar.
        //menu = new JMenu("Another Menu");
        //menu.setMnemonic(KeyEvent.VK_N);
        //menu.getAccessibleContext().setAccessibleDescription(
        //        "This menu does nothing");
        //menuBar.add(menu);

        return menuBar;
    }

    /********************************
    state changed (user selected different tab)
    *******************************/
    public void stateChanged(int index)
    {
        tabMgr.setCurrent(index);
        File fl = tabMgr.getCurrentFile();
        if (null != fl) {
            String st = fl.getAbsolutePath();
            writeStat(tabMgr.getCurrentFile().getAbsolutePath());
        }
        else {
            writeStat("New file: Untitled");
        }
    }

    /*******************************
    write to status field

    *******************************/
    public void writeStat(String s)
    {
       if (output != null)
       {
          output.setText(s);
       } 
    }

    /***************************
    OpenFile

    ****************************/
    public File OpenFile()
    {
       JFileChooser chooser = new JFileChooser(tabMgr.getCurrentDirectory());
       FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
       chooser.setFileFilter(filter);
       int returnVal = chooser.showOpenDialog(mainFrame);
       if (returnVal == JFileChooser.APPROVE_OPTION) 
       {
          if (Debug)
             writeStat("Opening file: " + chooser.getSelectedFile().getName());

          tabMgr.setCurrentDirectory(chooser.getCurrentDirectory().getAbsolutePath());
          return chooser.getSelectedFile();
       }

       return null;
    }


    /****************************
    createTextTab (for New menu option)

    ****************************/
    public boolean createTextTab()
    {
       boolean retVal = true;

       JTextArea textArea = new JTextArea(); 

       //get the listeners
       TbDocListener docListener = new TbDocListener(this);      
       TbMouseListener mouseListener = new TbMouseListener(this, textArea);
       //add the listeners    
       textArea.getDocument().addDocumentListener(docListener);
       textArea.addMouseListener(mouseListener);
  
       //make it editable
       textArea.setEditable(true);

       //create a scroll pane for the text area
       JScrollPane scrollPane = new JScrollPane(textArea);
       //create a tab and add the scroll pane to it
       tabMgr.addTab("Untitled", scrollPane, textArea);

       return true;
    }





    /****************************
    createTextTab

    ****************************/
    public boolean createTextTab(File f)
    {
       if (Debug)
          System.out.println("createTextTab: got File " + f.getAbsolutePath());

       boolean retVal = true;

       //open the file and read it into the text area
       RandomAccessFile randomAccessFile;
       JTextArea textArea = new JTextArea(); 
       try {
          randomAccessFile = new RandomAccessFile(f, "rw");
       }
       catch (FileNotFoundException exc)
       {
          System.err.println("FileNotFoundException: " + exc.toString() + "\n");
          return false;
       }

       byte b = 0;
       while (true)  
       {
          try {
             b = randomAccessFile.readByte();
          }
          catch (IOException ioexc) {
             if (ioexc.toString().contains("EOF"))
             {
                break;
             }
             else
             {
                System.err.println("IOException: " + ioexc.toString() + "\n");    
                return false;
             }
          }
          Character charac = new Character((char)b);
          textArea.append(charac.toString()); 
       }     
 
       try {
          randomAccessFile.close();
       }
       catch (IOException ioexc)
       {
          System.err.println("IOException: " + ioexc.toString() + "\n");    
          return false;
       }

       //get the listeners
       TbDocListener docListener = new TbDocListener(this);      
       TbMouseListener mouseListener = new TbMouseListener(this, textArea);
       //add the listeners    
       textArea.getDocument().addDocumentListener(docListener);
       textArea.addMouseListener(mouseListener);
  
       //make it editable
       textArea.setEditable(true);

       //create a scroll pane for the text area
       JScrollPane scrollPane = new JScrollPane(textArea);
       //create a tab and add the scroll pane to it
       tabMgr.addTab(f.getName(), scrollPane, textArea, f);

       return true;
    }


    /****************************

    closeCurrentFile

    ****************************/
    public void closeCurrentFile()
    {
        tabMgr.closeCurrentFile(); 
    } 

    /****************************

    saveCurrentFile

    ****************************/
    public void saveCurrentFile()
    {
        tabMgr.saveCurrentFile();
    }

    /****************************

    actionPerformed

    ****************************/
    public void actionPerformed(ActionEvent e)
    {
       //Quit, check this first
       if (e.getActionCommand().equals("Quit"))
       {
          System.exit(0);          
       }
       
       else if (e.getActionCommand().equals("NewFile"))
       {
          if (Debug)
             System.out.println("Got the NewFile command\n");

          createTextTab();
       }
    
 
       //Open File
       else if (e.getActionCommand().equals("OpenFile"))
       {
          if (Debug)
             System.out.println("Got the OpenFile command\n");

          File myfile = OpenFile();
          if (myfile != null)
          {
             if (Debug)
                System.out.println("actionPerformed: got filename " + myfile.getAbsolutePath());

             tabMgr.setCurrentFile(myfile);
             createTextTab(myfile);
          }
          else
          {
             writeStat("No file was given\n");
          }
       }
    
       //Save File
       else if (e.getActionCommand().equals("SaveFile"))
       {
          //Files get saved from the right click menu (mouselistener)
          if (Debug) {
             System.out.println("Got the SaveFile command from popup\n");
             System.out.println("Question: Is the File to save " + tabMgr.getCurrentFile().getAbsolutePath()  + "\n");
          }
       }   

       else if (e.getActionCommand().equals("Help"))
       {
          if (Debug) {
             System.out.println("Got the Help command from popup\n");
          }
          JOptionPane.showMessageDialog(mainFrame, "TbTextEdit 1.0\n\nOpen a file from the Menu\nRight click to SAVE/CLOSE a file");
          
       }   


    }

    /***************************

    createContentPane

    ***************************/
    public Container createContentPane() 
    {
        //Create the content-pane.
        Container contentPane = mainFrame.getContentPane();
        GridBagLayout gridbag = new GridBagLayout();
        contentPane.setLayout(gridbag);

        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 1;
        c.gridheight = 10;

        //Create a tabbed pane
        JTabbedPane tpane = new JTabbedPane();
        TbChangeListener chgListener = new TbChangeListener(tpane, this);
        tpane.addChangeListener(chgListener);
        c.gridx = 0;
	    c.gridy = 0;
        c.weightx = 1.0;
        c.weighty = 9.0;
        c.gridwidth = 1;
        c.gridheight = 9;
        c.fill = GridBagConstraints.BOTH;
        //c.fill = GridBagConstraints.VERTICAL;
        c.anchor = GridBagConstraints.NORTHWEST;
        gridbag.setConstraints(tpane, c);
        contentPane.add(tpane);
        //contentPane.setOpaque(true);
        createTabMgr(tpane);

        //Create a text area.
        output = new JTextArea(1, 10);
        output.setForeground(Color.blue);
        output.append("Status");
        output.setEditable(false);
        //c.gridheight = GridBagConstraints.REMAINDER; 
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx=0;
        c.gridy=30;
        //c.gridwidth = 10;
        //c.gridheight = 1;
        c.weightx = 0;
        c.weighty = 0;
        c.anchor = GridBagConstraints.WEST;
        gridbag.setConstraints(output, c);
        contentPane.add(output);
       

        return contentPane;
    }


    /****************************

    createTabMgr

    ****************************/
    public void createTabMgr(JTabbedPane pane)
    {
       //Create the tab manager
       tabMgr = new TabManager(pane);
    }

    /***************************

    createImageIcon

    ***************************/
    /** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = TbTextEdit.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    /***************************

     createAndShowGUI

    ***************************/
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        mainFrame = new JFrame("TbTextEdit");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        TbTextEdit ml = new TbTextEdit();
        mainFrame.setJMenuBar(ml.createMenuBar());
        mainFrame.setContentPane(ml.createContentPane());

        //Display the window.
        mainFrame.setResizable(true);
        mainFrame.setSize(800, 600);
        mainFrame.setLocation(200, 200);
        mainFrame.setVisible(true);
    }


/***************************

    class TabManager

***************************/
class TabManager extends AbstractCollection<TabEntry>
{
       JTabbedPane pane;
       int currentIndex = 0;
       File currentFile = null;
       JTextArea currentTextArea;
       String currentDirectory = ".";
       int numTabs = 0;
       JTextArea[] jt_array;
       Set<TabEntry> collect;

       public TabManager(JTabbedPane tabPane)
       {
          collect = new HashSet<TabEntry>();
          numTabs = 0;
          pane = tabPane;
       }

       //Sun says one should provide this contructor, but we have
       //    no use for this, since we never initialize a TabManager
       //    from an already existing set
       //public TabManager(Collection c)
       //{
          //numTabs = c.size();
          //collect = new HashSet<TabEntry>();
          //if (c.getClass().getName().equals("components.TabEntry"))
          //collect.addAll((Set<TabEntry>)c);

          //if (c.getClass().isInstance(collect))
             //collect.addAll((Set<TabEntry>)c);
            
       //}

       public void clear()
       {
       }

       public boolean all(Collection<TabEntry> c)
       {
           return true;
       }

       public boolean remove(Object ob)
       {
           if (ob.getClass().getName().equals("TabEntry"))
           {
              if (numTabs > 0)
              {
                 boolean result = collect.remove((TabEntry)ob);
                 if (result == true)
                    numTabs--;
                 
                 return result;
              }
           }
           return false;
       }

       public boolean add(TabEntry ob)
       {
           collect.add(ob);
           numTabs++;
           return true;
       }

       public Iterator<TabEntry> iterator()
       {
           return null;
       }

       public boolean contains(Object ob)
       {
           return false;
       }

       public boolean isEmpty()
       {
           Iterator<TabEntry> i = collect.iterator();
           if (i.hasNext())
           {
              return false;
           }
           return true;
       } 

       public int size()
       {
          return numTabs;
       }

       public void addTab(String handle, JScrollPane jsp, JTextArea textArea, File f)
       {
          pane.addTab(handle, jsp);
          TabEntry tabEntry = new TabEntry(jsp, textArea, f);  
          this.currentFile = f;
          this.currentTextArea = textArea;
          add(tabEntry);
          int index = pane.indexOfTab(handle);
          if (index != -1 ) {
             pane.setSelectedIndex(index);
             return;
          } 
          else {
             return;
          }
             
       }

       //this is for NEW file when there is no file (yet)
       public void addTab(String handle, JScrollPane jsp, JTextArea textArea)
       {
          pane.addTab(handle, jsp);
          TabEntry tabEntry = new TabEntry(jsp, textArea);  
          this.currentTextArea = textArea;
          this.currentFile = null;
          add(tabEntry);
          int index = pane.indexOfTab(handle);
          if (index != -1 ) {
             pane.setSelectedIndex(index);
             return;
          } 
          else {
             return;
          }
             
       }

       //public JTextArea getTextAreaForFile(File f)
       //{
           //Iterator i = collect.iterator();
           //while (i.hasNext())
           //{
              //Object ob = i.next();
              //TabEntry te = (TabEntry)ob; 
              //File tmpF = te.getFile();
              //if (tmpF.equals(f)) {
                 //System.out.println("The file for this tab is " + te.getFile().getAbsolutePath() + "\n");
                 //return te.getTextArea();
              //}
           //}
           //return null;

       //}

    public void setCurrent(int index)
    {
        if (index == -1) return;
        Iterator<TabEntry> i = collect.iterator();
        JScrollPane spane = new JScrollPane(); 
        spane = (JScrollPane)pane.getComponentAt(index); 
        if (spane == null) { 
           System.err.println("spane is null: OUT OF MEMORY\n");
           System.exit(0);
        }
        this.currentIndex = index;
        while (i.hasNext())
        {
           Object ob = i.next();
           TabEntry te = (TabEntry)ob; 
           JScrollPane tmpSP = te.getScrollPane();
           if (tmpSP.equals(spane)) {
              if (null != te.getFile()) {
                  this.currentFile = te.getFile();
              } 
              else {
                  this.currentFile = null;
              }
              this.currentTextArea = te.getTextArea();
           }
        }
    }

    public void saveCurrentFile()
    {
          
       JTextArea ta = this.currentTextArea;
       
       if (null != currentFile) {

           try {
              BufferedWriter out = new BufferedWriter(new FileWriter(currentFile));
              ta.write(out);
              out.flush();
              out.close();
           } catch (IOException e1) {
              System.err.println("IOException: dumping stack");
              e1.printStackTrace();
           }
       }
       else {
            //The tab was created as a New file, so we need a File Save dialog.
            JFileChooser saveFile = new JFileChooser(getCurrentDirectory());
            saveFile.showSaveDialog(mainFrame);
            File f = saveFile.getSelectedFile();
            if (null != f) {
                try {
                    BufferedWriter out = new BufferedWriter(new FileWriter(f));
                    ta.write(out);
                    out.flush();
               } catch (IOException e1) {
                  System.err.println("IOException: dumping stack");
                  e1.printStackTrace();
               }
               pane.setTitleAt(currentIndex, f.getName());
               writeStat(f.getName());
               currentFile = f;
            }
            else { //cancelled
            } 
       }
    }


    public void closeCurrentFile()
    {
        pane.remove(this.currentIndex); 
    } 

    public void setCurrentFile(File f)
    {
       this.currentFile = f;
    }

    public File getCurrentFile()
    {
       return this.currentFile;
    }

    public void setCurrentDirectory(String cd)
    {
       currentDirectory = cd; 
    }
  
    public String getCurrentDirectory()
    {
       return this.currentDirectory;
    }
 }


class TabEntry
{
    String tabType;
    JScrollPane scrollPane;
    JTextArea textArea;
    File f;
    
    public TabEntry()
    {
       this.tabType = null;
       this.f = null;
    }

    public TabEntry(JScrollPane pane, JTextArea textArea, File f)
    {
       this.tabType = "File";
       this.scrollPane = pane;
       this.f = f;
       this.textArea = textArea;
    }

    public TabEntry(JScrollPane pane, JTextArea textArea)
    {
       this.tabType = "File";
       this.scrollPane = pane;
       this.f = null;
       this.textArea = textArea;
    }


    //It's not a tab containing a file, as opposed to the ones above
    public TabEntry(String type)
    {
       this.tabType = type;
       this.f = null;
       this.textArea = null;
    }

    public String getType()
    {
        return tabType;
    }

    public File getFile()
    {
       if (tabType.equals("File"))
       {
          return f;
       }
       else
          return null;
    }

    public JScrollPane getScrollPane()
    {
       return scrollPane; 
    }

    public JTextArea getTextArea()
    {
       return textArea;
    }

}

/******************************

    main

******************************/
    public static void main(String[] args) {
            //Schedule a job for the event-dispatching thread:
            //creating and showing this application's GUI.
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    createAndShowGUI();
                }
            });
    }
} //class TbTextEdit


