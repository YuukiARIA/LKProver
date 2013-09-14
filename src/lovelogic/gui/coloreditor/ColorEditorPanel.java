package lovelogic.gui.coloreditor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class ColorEditorPanel extends JPanel
{
	private ColorBox colorBox;
	private SpinSlider sliderR;
	private SpinSlider sliderG;
	private SpinSlider sliderB;

	public ColorEditorPanel()
	{
		sliderR = new SpinSlider(0, 0, 255, 1);
		sliderR.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				updateGradation();
			}
		});
		add(sliderR);

		sliderG = new SpinSlider(0, 0, 255, 1);
		sliderG.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				updateGradation();
			}
		});
		add(sliderG);

		sliderB = new SpinSlider(0, 0, 255, 1);
		sliderB.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				updateGradation();
			}
		});
		add(sliderB);

		updateGradation();
	}

	private void updateGradation()
	{
		Color c = new Color(sliderR.getValue(), sliderG.getValue(), sliderB.getValue());
		sliderR.setBarColor(new Color(0, c.getGreen(), c.getBlue()), new Color(255, c.getGreen(), c.getBlue()));
		sliderG.setBarColor(new Color(c.getRed(), 0, c.getBlue()), new Color(c.getRed(), 255, c.getBlue()));
		sliderB.setBarColor(new Color(c.getRed(), c.getGreen(), 0), new Color(c.getRed(), c.getGreen(), 255));
		repaint();
	}

	public static void main(String[] args)
	{
		try
		{
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
