
package tbtextedit;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import tbtextedit.TbTextEdit;

public class TbChangeListener implements ChangeListener
{
   boolean Debug = false;
   JTabbedPane pane;
   TbTextEdit mLook;

public TbChangeListener(JTabbedPane pane, TbTextEdit ml)
{
   this.pane = pane;
   this.mLook = ml;
}

public void stateChanged(ChangeEvent ce)
{
   Object ob = ce.getSource();
   Class<?> cl = ob.getClass();
   

   if (Debug) {
      System.out.println("MyChangeListener: got stateChanged: Object: " + ob.toString() + "\n");
      System.out.println("MyChangeListener: got stateChanged: Class: " + cl.getName() + "\n");
   }

   if (cl.getName().equals("javax.swing.JTabbedPane"))
   {
      if (pane == ce.getSource())
      {
          int index = pane.getSelectedIndex();
          mLook.stateChanged(index);
      }      
      else {
          System.out.println("The source does NOT equal pane\n");
      }
   }

}

//public void mouseEntered(MouseEvent de)
//{
   //if (Debug)
      //System.out.println("MyMouseListener: got mouseEntered\n");
 
   //this.menuLook.setFileChanged();
//}

//public void mouseReleased(MouseEvent de)
//{
   //if (Debug)
      //System.out.println("MyMouseListener: got mouseReleased\n");

   //this.menuLook.setFileChanged();
//}

//public void mousePressed(MouseEvent de)
//{
   //if (Debug)
      //System.out.println("MyMouseListener: got mousePressed\n");

   //this.menuLook.setFileChanged();
//}

//public void mouseClicked(MouseEvent de)
//{
   //if (Debug)
      //System.out.println("MyMouseListener: got mouseClicked\n");

   //if (de.getButton() == 1)
   //{
      //if (Debug)
         //System.out.println("Got Mouse Button 1\n");
   //}
   //else if (de.getButton() == 2)
   //{
      //if (Debug)
         //System.out.println("Got Mouse Button 2\n");
   //}
   //else if (de.getButton() == 3)
   //{
      //if (Debug)
         //System.out.println("Got Mouse Button 3\n");
   //}

   //this.menuLook.setFileChanged();
//}


}


