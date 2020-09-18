package SimpleNN;
import SimpleNN.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.UnaryOperator;
import javax.swing.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class Init {

	public static void dots() {
        FormDots f = new FormDots();
        new Thread(f).start();
    }

    public static void digits(JLabel progressLabel, JLabel timeLeftLabel, JButton activationButton, JProgressBar progressBar) throws IOException {
		progressBar.setVisible(true);
        UnaryOperator<Double> sigmoid = x -> 1 / (1 + Math.exp(-x));
        UnaryOperator<Double> dsigmoid = y -> y * (1 - y);
        NeuralNetwork nn = new NeuralNetwork(0.001, sigmoid, dsigmoid, 784, 512, 128, 32, 10);

        int samples = 60000;
        BufferedImage[] images = new BufferedImage[samples];
        int[] digits = new int[samples];
		timeLeftLabel.setText("Loading images...");
        File[] imagesFiles = new File("./train").listFiles();
        for (int i = 0; i < samples; i++) {
            images[i] = ImageIO.read(imagesFiles[i]);
            digits[i] = Integer.parseInt(imagesFiles[i].getName().charAt(10) + "");
			progressBar.setValue((int)(i * 100 / samples));
        }
		
		
		timeLeftLabel.setText("Sorting images...");
        double[][] inputs = new double[samples][784];
        for (int i = 0; i < samples; i++) {
            for (int x = 0; x < 28; x++) {
                for (int y = 0; y < 28; y++) {
                    inputs[i][x + y * 28] = (images[i].getRGB(x, y) & 0xff) / 255.0;
					progressBar.setValue((int)(i * 100 / samples));
                }
            }
        }
		
		Timestamp startTimeStamp = new Timestamp(System.currentTimeMillis());
		Timestamp currentTimeStamp = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd. HH:mm:ss");
		long timeLeft;
        int epochs = 10000;
		int i;
        for (i = 1; i < epochs; i++) {
            int right = 0;
            double errorSum = 0;
            int batchSize = 100;
            for (int j = 0; j < batchSize; j++) {
                int imgIndex = (int)(Math.random() * samples);
                double[] targets = new double[10];
                int digit = digits[imgIndex];
                targets[digit] = 1;

                double[] outputs = nn.feedForward(inputs[imgIndex]);
                int maxDigit = 0;
                double maxDigitWeight = -1;
                for (int k = 0; k < 10; k++) {
                    if(outputs[k] > maxDigitWeight) {
                        maxDigitWeight = outputs[k];
                        maxDigit = k;
                    }
                }
                if(digit == maxDigit) right++;
                for (int k = 0; k < 10; k++) {
                    errorSum += (targets[k] - outputs[k]) * (targets[k] - outputs[k]);
                }
                nn.backpropagation(targets);
            }
			currentTimeStamp = new Timestamp(System.currentTimeMillis());
			timeLeft = ((currentTimeStamp.getTime() - startTimeStamp.getTime())/i) * (epochs - i);
            System.out.println("epoch: " + i + ". correct: " + right + ". error: " + errorSum);
			progressBar.setValue((int)(i * 100 / epochs));
			timeLeftLabel.setText("<html>Learn started at: <br>" + sdf.format(startTimeStamp) + "<br>Time left: " + formatter.format(timeLeft) + "</html>");
			progressLabel.setText("<html>corrects: " + right + "%<br>errors: " + String.format("%.2f", errorSum) + "</html>");
			if (activationButton.getText() == "ОСТАНОВКА") break;
        }
		
		activationButton.setText("ЧИСЛА");
		activationButton.setEnabled(true);
		progressBar.setVisible(false);
		//progressLabel.setText("");
		timeLeftLabel.setText("<html>Пройдено шагов : " + i + "<br>Затрачено времени: " + formatter.format(currentTimeStamp.getTime() - startTimeStamp.getTime()) + "</html>");
        FormDigits f = new FormDigits(nn);
        new Thread(f).start();
    }

}