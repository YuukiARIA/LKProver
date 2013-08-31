package lovelogic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class FontDialog extends JDialog
{
	private boolean aproved;
	private JComboBox comboFontName;
	private JSpinner spinnerFontSize;

	public FontDialog(Frame owner)
	{
		super(owner);

		setTitle("Select Font");
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		comboFontName = new JComboBox();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		for (String name : ge.getAvailableFontFamilyNames())
		{
			comboFontName.addItem(name);
		}

		spinnerFontSize = new JSpinner(new SpinnerNumberModel(12, 1, 120, 1));

		JPanel panelCenter = new JPanel();
		panelCenter.add(comboFontName);
		panelCenter.add(spinnerFontSize);
		add(panelCenter, BorderLayout.CENTER);

		JButton buttonOK = new JButton("OK");
		buttonOK.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				aproveClose();
			}
		});
		JButton buttonCancel = new JButton("Cancel");
		buttonCancel.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				close();
			}
		});
		JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBottom.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(4, 4, 4, 4),
			BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE))));
		panelBottom.add(buttonOK);
		panelBottom.add(buttonCancel);
		add(panelBottom, BorderLayout.SOUTH);

		Dimension dim1 = buttonOK.getPreferredSize();
		Dimension dim2 = buttonCancel.getPreferredSize();
		Dimension dim = new Dimension(Math.max(dim1.width, dim2.width), Math.max(dim1.height, dim2.height));
		buttonOK.setPreferredSize(dim);
		buttonCancel.setPreferredSize(dim);

		pack();
		setLocationRelativeTo(owner);
	}

	public void setSelectedFont(Font font)
	{
		comboFontName.setSelectedItem(font.getFamily());
		spinnerFontSize.setValue(font.getSize());
	}

	public Font getSelectedFont()
	{
		String name = (String)comboFontName.getSelectedItem();
		int size = (Integer)spinnerFontSize.getValue();
		return new Font(name, Font.PLAIN, size);
	}

	public void showDialog()
	{
		aproved = false;

		pack();
		setVisible(true);
	}

	public boolean isAproved()
	{
		return aproved;
	}

	private void aproveClose()
	{
		aproved = true;
		close();
	}

	private void close()
	{
		dispose();
	}
}
