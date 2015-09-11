
package tbtextedit;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import tbtextedit.TbTextEdit;

public class TbMouseListener implements MouseListener, ActionListener
{
    boolean Debug = false;
    TbTextEdit menuLook;
    Container tainer;

    public TbMouseListener(TbTextEdit ml, Container ctain)
    {
       this.menuLook = ml;
       this.tainer = ctain;
    }

    //The events below are ignored but they could possibly be used
    public void mouseExited(MouseEvent de)
    {
       //if (Debug)
          //System.out.println("MyMouseListener: got mouseExited\n");
    }

    public void mouseEntered(MouseEvent de)
    {
       //if (Debug)
          //System.out.println("MyMouseListener: got mouseEntered\n");
    }

    public void mouseReleased(MouseEvent de)
    {
       //if (Debug)
          //System.out.println("MyMouseListener: got mouseReleased\n");
    }

    public void mousePressed(MouseEvent de)
    {
       //if (Debug)
          //System.out.println("MyMouseListener: got mousePressed\n");
    }

    public void mouseClicked(MouseEvent de)
    {

       if (de.getButton() == 1)
       {
       }
       else if (de.getButton() == 2)
       {
       }
       else if (de.getButton() == 3)
       {
          if (Debug)
             System.out.println("Got Mouse Button 3\n");

          JPopupMenu pmenu = new JPopupMenu("File Menu");
          JMenuItem menuItem;
          menuItem = new JMenuItem("Save File", KeyEvent.VK_S);
          menuItem.setActionCommand("SaveFile");
          menuItem.addActionListener(this);
          pmenu.add(menuItem);

          menuItem = new JMenuItem("Close File", KeyEvent.VK_C);
          menuItem.setActionCommand("CloseFile");
          menuItem.addActionListener(this);
          pmenu.add(menuItem);

          pmenu.setVisible(true);
          Point p = de.getPoint();
          pmenu.show(this.tainer, p.x, p.y);
       }

       //this.menuLook.setFileChanged();
    }


    public void actionPerformed(ActionEvent e)
    {
           //SaveFile
       if (e.getActionCommand().equals("SaveFile"))
       {
          if (Debug)
             System.out.println("Got SaveFile action event");

          menuLook.saveCurrentFile();
       }
       else if (e.getActionCommand().equals("CloseFile"))
       {
          menuLook.closeCurrentFile();
       }

    }

}


