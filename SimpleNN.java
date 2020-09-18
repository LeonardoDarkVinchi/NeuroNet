package SimpleNN;
import SimpleNN.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;
import javax.swing.*;
import java.awt.event.*;

public class SimpleNN {
	
    public static void main(String[] args) throws IOException {
		menu();
//        Init.dots();
//        digits();
    }
	
	private static void menu() {
		JFrame menuFrame = new JFrame("SimpleNeuroNet");
		FrameMove menuPanel = new FrameMove(menuFrame);
		menuFrame.setBounds(100, 150, 200, 296);
		menuFrame.setResizable(false);
		menuFrame.setDefaultCloseOperation(3);
		
		JLabel localJLabel1 = new JLabel("Запустить: ");
		JButton localJButton1 = new JButton("ТОЧКИ");
		JButton localJButton2 = new JButton("ЧИСЛА");
		JLabel localJLabel2 = new JLabel("");
		JLabel localJLabel3 = new JLabel("");
		JProgressBar progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setMinimum(0);
		progressBar.setMaximum(100);
		progressBar.setVisible(false);
		
		menuPanel.setLayout(new java.awt.GridLayout(0, 1, 0, 0));
		menuPanel.add(localJLabel1);
		menuPanel.add(localJButton1);
		menuPanel.add(localJButton2);
		menuPanel.add(progressBar);
		menuPanel.add(localJLabel2);
		menuPanel.add(localJLabel3);
		
		menuFrame.add(menuPanel);
		
		localJButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				try {
					Init.dots();
				}
				catch (NumberFormatException localNumberFormatException) {}
			}
		});
		
		localJButton2.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent paramAnonymousActionEvent) {
				if (localJButton2.getText() == "ЧИСЛА")
				{
					localJButton2.setText("СТОП");
					localJLabel2.setText("");
					localJLabel3.setText("");
					try {
						new InitDigitsCommand(localJLabel3, localJLabel2, localJButton2, progressBar);
					}
					catch (Exception ex) {}
				} 
				else
				{
					localJButton2.setText("ОСТАНОВКА");
					localJButton2.setEnabled(false);
				}
			}
		});
		menuFrame.setVisible(true);
	}
	
	public static class InitDigitsCommand extends Thread {
		JLabel transferLabel;
		JLabel transferLabel2;
		JButton transferButton;
		JProgressBar transferBar;
		// Конструктор
		InitDigitsCommand(JLabel localLabel, JLabel localLabel2, JButton localButton, JProgressBar progressBar) throws Exception {
			transferLabel = localLabel;
			transferLabel2 = localLabel2;
			transferButton = localButton;
			transferBar = progressBar;
			start(); // Запускаем поток
		}
		public void run() {
			try {
				Init.digits(transferLabel, transferLabel2, transferButton, transferBar);
			}
			catch (Exception ex) {
				transferLabel.setText("Error!!!" + ex.getMessage());
			}
		}
	}
}