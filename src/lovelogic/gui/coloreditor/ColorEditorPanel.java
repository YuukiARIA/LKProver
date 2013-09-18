package lovelogic.gui.coloreditor;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

@SuppressWarnings("serial")
public class ColorEditorPanel extends JPanel
{
	private JPanel colorBox;
	private SpinSlider sliderR;
	private SpinSlider sliderG;
	private SpinSlider sliderB;
	private JTextField textCode;
	private boolean sync;

	public ColorEditorPanel()
	{
		colorBox = new JPanel();
		colorBox.setOpaque(true);

		ChangeListener updateHandler = new ChangeListener()
		{
			public void stateChanged(ChangeEvent e)
			{
				if (!sync)
				{
					sync = true;
					updateColors();
					syncColorCode();
					sync = false;
				}
			}
		};

		sliderR = new SpinSlider(0, 0, 255, 1);
		sliderR.addChangeListener(updateHandler);
		sliderG = new SpinSlider(0, 0, 255, 1);
		sliderG.addChangeListener(updateHandler);
		sliderB = new SpinSlider(0, 0, 255, 1);
		sliderB.addChangeListener(updateHandler);

		textCode = new JTextField();
		textCode.getDocument().addDocumentListener(new DocumentListener()
		{
			public void removeUpdate(DocumentEvent e)
			{
				if (!sync)
				{
					sync = true;
					modifyColorCode();
					sync = false;
				}
			}

			public void insertUpdate(DocumentEvent e)
			{
				if (!sync)
				{
					sync = true;
					modifyColorCode();
					sync = false;
				}
			}

			public void changedUpdate(DocumentEvent e)
			{
			}
		});

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
				.addComponent(textCode)
			)
		);
		gl.setVerticalGroup(gl.createParallelGroup(Alignment.CENTER)
			.addComponent(colorBox, 64, 64, 64)
			.addGroup(gl.createSequentialGroup()
				.addComponent(sliderR)
				.addComponent(sliderG)
				.addComponent(sliderB)
				.addComponent(textCode, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE, GroupLayout.PREFERRED_SIZE)
			)
		);

		setColor(Color.RED);
		updateColors();
		syncColorCode();
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
		updateColors();
	}

	public void setColor(Color color)
	{
		setColor(color.getRed(), color.getGreen(), color.getBlue());
	}

	private void updateColors()
	{
		Color c = getColor();
		colorBox.setBackground(c);

		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		sliderR.setBarColor(new Color(0, g, b), new Color(255, g, b));
		sliderG.setBarColor(new Color(r, 0, b), new Color(r, 255, b));
		sliderB.setBarColor(new Color(r, g, 0), new Color(r, g, 255));

		repaint();
	}

	private void syncColorCode()
	{
		Color c = getColor();
		textCode.setText(String.format("%06x", c.getRGB() & 0x00FFFFFF));
	}

	private void modifyColorCode()
	{
		String code = textCode.getText();
		try
		{
			int h = Integer.parseInt(code, 16);
			int r = (h >>> 16) & 0xFF;
			int g = (h >>> 8) & 0xFF;
			int b = h & 0xFF;
			setColor(r, g, b);
		}
		catch (NumberFormatException e)
		{
		}
	}
}
