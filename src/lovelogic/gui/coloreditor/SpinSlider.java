package lovelogic.gui.coloreditor;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SpinSlider extends JComponent
{
	private GradationSlider slider;
	private JSpinner spinner;

	public SpinSlider(int value, int min, int max, int step)
	{
		slider = new GradationSlider().setMinimum(min).setMaximum(max);
		slider.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				spinner.setValue(slider.getValue());
				dispatchChangeEvent();
			}
		});
		spinner = new JSpinner(new SpinnerNumberModel(value, min, max, step));
		spinner.addChangeListener(new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				slider.setValue((Integer)spinner.getValue());
				dispatchChangeEvent();
			}
		});

		GroupLayout gl = new GroupLayout(this);
		setLayout(gl);
		gl.setHorizontalGroup(gl.createSequentialGroup()
			.addComponent(slider)
			.addComponent(spinner)
		);
		gl.setVerticalGroup(gl.createParallelGroup(Alignment.CENTER)
			.addComponent(slider)
			.addComponent(spinner, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
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
		slider.setLeftColor(c1).setRightColor(c2);
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
