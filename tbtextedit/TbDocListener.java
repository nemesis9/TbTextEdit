
package tbtextedit;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.*;

import tbtextedit.TbTextEdit;

public class TbDocListener implements DocumentListener
{
   boolean Debug = false;
   TbTextEdit menuLook;

public TbDocListener(TbTextEdit ml)
{
   this.menuLook = ml;
}

public void changedUpdate(DocumentEvent de)
{
   if (Debug)
      System.out.println("MyDocListener: got changedUpdate\n");
}

public void removeUpdate(DocumentEvent de)
{
   if (Debug)
      System.out.println("MyDocListener: got removeUpdate\n");
 
   //this.menuLook.setFileChanged();
}

public void insertUpdate(DocumentEvent de)
{
   if (Debug)
      System.out.println("MyDocListener: got insertUpdate\n");

   //this.menuLook.setFileChanged();
}


}


