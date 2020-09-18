package SimpleNN;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.*;
import javax.swing.JPanel;
import javax.swing.JFrame;

import java.lang.*;
import javax.swing.*;

import SimpleNN.*;

public class FrameMove extends JPanel {

    private Point position;
	protected boolean grabbed;
	//protected final AppWindow parent;
	protected final JFrame parent;
	
	//public FrameMove(final AppWindow newParent){	
	public FrameMove(final JFrame newParent){	
		this.parent = newParent;
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				grabbed = true;
				position = e.getPoint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter(){
			@Override
			public void mouseDragged(MouseEvent e){
				grabbedMove(e);
			}
		});
		
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseReleased(MouseEvent e){
				grabbed = false;
			}
		});
	}
		
	private void grabbedMove(MouseEvent e){
		int thisX = parent.getLocation().x;
		int thisY = parent.getLocation().y;
		int X = thisX + e.getX() - position.x;
		int Y = thisY + e.getY() - position.y;
		parent.setLocation(X, Y);
	}
}