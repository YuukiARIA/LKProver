package lovelogic.gui.coloreditor;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
class GradationBar extends JComponent
{
	private Color c1 = Color.BLACK;
	private Color c2 = Color.BLACK;
	private int position;

	public void setColor(Color c1, Color c2)
	{
		this.c1 = c1;
		this.c2 = c2;
	}

	public void setPosition(int position)
	{
		this.position = position;
	}

	protected void paintComponent(Graphics g)
	{
		draw((Graphics2D)g);
	}

	private void draw(Graphics2D g)
	{
		Paint p = new GradientPaint(0, 0, c1, getWidth(), 0, c2);
		g.setPaint(p);
		g.fillRect(0, 0, getWidth(), getHeight());

		int x = getWidth() * position / 255;
		g.setPaint(null);
		g.setColor(Color.WHITE);
		g.drawLine(x, 0, x, getHeight());
	}
}

@SuppressWarnings("serial")
public class SpinSlider extends JComponent
{
	private JSlider slider;
	private JSpinner spinner;
	private GradationBar bar;

	public SpinSlider(int value, int min, int max, int step)
	{
		slider = new JSlider(min, max, value);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				spinner.setValue(slider.getValue());
				bar.setPosition(slider.getValue());
				dispatchChangeEvent();
			}
		});
		spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
		spinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				slider.setValue((Integer)spinner.getValue());
				bar.setPosition(slider.getValue());
				dispatchChangeEvent();
			}
		});
		bar = new GradationBar();

		GroupLayout gl = new GroupLayout(this);
		setLayout(gl);
		gl.setAutoCreateContainerGaps(true);
		gl.setAutoCreateGaps(true);
		gl.setHorizontalGroup(gl.createSequentialGroup()
			.addGroup(gl.createParallelGroup()
				.addComponent(slider)
				.addComponent(bar)
			)
			.addComponent(spinner)
		);
		gl.setVerticalGroup(gl.createSequentialGroup()
			.addGroup(gl.createParallelGroup(Alignment.CENTER)
				.addComponent(slider)
				.addComponent(spinner, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			)
			.addComponent(bar, 8, 8, 8)
		);
	}

	public int getValue()
	{
		return slider.getValue();
	}

	public void setValue(int value)
	{
		slider.setValue(value);
		spinner.setValue(value);
	}

	public void setBarColor(Color c1, Color c2)
	{
		bar.setColor(c1, c2);
	}

	public void addChangeListener(ChangeListener l)
	{
		listenerList.add(ChangeListener.class, l);
	}

	public void removeChangeListener(ChangeListener l)
	{
		listenerList.remove(ChangeListener.class, l);
	}

	private void dispatchChangeEvent()
	{
		ChangeEvent e = new ChangeEvent(this);
		for (ChangeListener l : listenerList.getListeners(ChangeListener.class))
		{
			l.stateChanged(e);
		}
	}
}
