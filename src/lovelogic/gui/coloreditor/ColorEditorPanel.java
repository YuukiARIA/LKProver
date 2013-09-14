package lovelogic.gui.coloreditor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
class ColorBox extends JComponent
{
	private Color color = Color.BLACK;

	public Color getColor()
	{
		return color;
	}

	public void setColor(Color color)
	{
		this.color = color;
	}

	protected void paintComponent(Graphics g)
	{
		g.setColor(color);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}

@SuppressWarnings("serial")
public class ColorEditorPanel extends JPanel
{
	private ColorBox colorBox;
	private SpinSlider sliderR;
	private SpinSlider sliderG;
	private SpinSlider sliderB;

	public ColorEditorPanel()
	{
		colorBox = new ColorBox();

		ChangeListener updateHandler = new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				updateGradation();
			}
		};

		sliderR = new SpinSlider(0, 0, 255, 1);
		sliderR.addChangeListener(updateHandler);
		sliderG = new SpinSlider(0, 0, 255, 1);
		sliderG.addChangeListener(updateHandler);
		sliderB = new SpinSlider(0, 0, 255, 1);
		sliderB.addChangeListener(updateHandler);

		GroupLayout gl = new GroupLayout(this);
		setLayout(gl);
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		gl.setHorizontalGroup(gl.createSequentialGroup()
			.addComponent(colorBox, 64, 64, 64)
			.addGroup(gl.createParallelGroup()
				.addComponent(sliderR)
				.addComponent(sliderG)
				.addComponent(sliderB)
			)
		);
		gl.setVerticalGroup(gl.createParallelGroup(Alignment.CENTER)
			.addComponent(colorBox, 64, 64, 64)
			.addGroup(gl.createSequentialGroup()
				.addComponent(sliderR)
				.addComponent(sliderG)
				.addComponent(sliderB)
			)
		);

		updateGradation();
	}

	public Color getColor()
	{
		int r = sliderR.getValue();
		int g = sliderG.getValue();
		int b = sliderB.getValue();
		return new Color(r, g, b);
	}

	public void setColor(int r, int g, int b)
	{
		sliderR.setValue(r);
		sliderG.setValue(g);
		sliderB.setValue(b);
		colorBox.setColor(new Color(r, g, b));
	}

	private void updateGradation()
	{
		Color c = getColor();
		colorBox.setColor(c);
		sliderR.setBarColor(new Color(0, c.getGreen(), c.getBlue()), new Color(255, c.getGreen(), c.getBlue()));
		sliderG.setBarColor(new Color(c.getRed(), 0, c.getBlue()), new Color(c.getRed(), 255, c.getBlue()));
		sliderB.setBarColor(new Color(c.getRed(), c.getGreen(), 0), new Color(c.getRed(), c.getGreen(), 255));
		repaint();
	}

	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(new ColorEditorPanel());
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
