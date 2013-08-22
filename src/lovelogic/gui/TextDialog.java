package lovelogic.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class TextDialog extends JDialog
{
	private JTextArea textArea;

	public TextDialog(JFrame owner)
	{
		super(owner);

		setTitle("Text");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.MODELESS);

		textArea = new JTextArea(20, 50);
		textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 10));
		add(new JScrollPane(textArea), BorderLayout.CENTER);

		JButton buttonClose = new JButton("Close");
		buttonClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				close();
			}
		});

		JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelButtons.setBorder(BorderFactory.createCompoundBorder(
			BorderFactory.createEmptyBorder(4, 4, 4, 4),
			BorderFactory.createCompoundBorder(
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY),
				BorderFactory.createMatteBorder(1, 0, 0, 0, Color.WHITE)
			)
		));
		panelButtons.add(buttonClose);
		add(panelButtons, BorderLayout.SOUTH);

		pack();
	}

	public void setText(String s)
	{
		textArea.setText(s);
	}

	private void close()
	{
		dispose();
	}
}
