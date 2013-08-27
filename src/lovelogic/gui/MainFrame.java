package lovelogic.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import lovelogic.config.Config;
import lovelogic.gui.figure.ProofFigure;
import lovelogic.gui.figure.ProofFigureBuilder;
import lovelogic.latex.LaTeXStringBuilder;
import lovelogic.prover.ProofStep;
import lovelogic.prover.Prover;
import lovelogic.sequent.Sequent;
import lovelogic.syntax.Formula;
import lovelogic.syntax.parser.exception.ParserException;
import util.MTree;

@SuppressWarnings("serial")
public class MainFrame extends JFrame
{
	private MTree<ProofStep> proof;
	private ProofFigurePanel pfPanel;
	private JTextField textInput;
	private JButton buttonProve;
	private JCheckBox checkMinimize;
	private JCheckBox checkIntuition;

	public MainFrame()
	{
		setTitle("LK Prover");

		pfPanel = new ProofFigurePanel();
		JScrollPane jsp = new JScrollPane(pfPanel);
		jsp.setPreferredSize(new Dimension(400, 200));
		add(jsp, BorderLayout.CENTER);

		textInput = new JTextField();
		textInput.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				prove();
			}
		});
		buttonProve = new JButton("Prove");
		buttonProve.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				prove();
			}
		});

		checkMinimize = new JCheckBox("Minimize Depth of Proof Tree");
		checkMinimize.setSelected(Config.getSystemConfig().getDefault("flags.minimize", "false").equals("true"));
		checkMinimize.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Config.getSystemConfig().setBoolean("flags.minimize", checkMinimize.isSelected());
			}
		});

		checkIntuition = new JCheckBox("LJ (Intuitionistic Logic)");
		checkIntuition.setSelected(Config.getSystemConfig().getDefault("flags.lj", "false").equals("true"));
		checkIntuition.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Config.getSystemConfig().setBoolean("flags.lj", checkIntuition.isSelected());
			}
		});

		JPanel panelChecks = new JPanel();
		panelChecks.setLayout(new BoxLayout(panelChecks, BoxLayout.Y_AXIS));
		panelChecks.add(checkMinimize);
		panelChecks.add(checkIntuition);

		JPanel panelBottom = new JPanel(new BorderLayout());
		panelBottom.add(textInput, BorderLayout.CENTER);
		panelBottom.add(buttonProve, BorderLayout.EAST);
		panelBottom.add(panelChecks, BorderLayout.SOUTH);
		add(panelBottom, BorderLayout.SOUTH);

		setJMenuBar(new MainMenuBar());

		pack();
	}

	private void prove()
	{
		String input = textInput.getText();
		boolean intuition = checkIntuition.isSelected();
		boolean minimize = checkMinimize.isSelected();

		try
		{
			Formula x = Formula.parse(input);
			Sequent s = Sequent.createGoal(x);
			proof = Prover.searchProof(s, intuition, minimize);
			if (proof != null)
			{
				ProofFigure pf = ProofFigureBuilder.build(proof);
				pfPanel.setProofFigure(pf);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "Unprovable", "Result", JOptionPane.INFORMATION_MESSAGE);
				pfPanel.setProofFigure(null);
			}
		}
		catch (ParserException e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	private void saveImage()
	{
		RenderedImage image = pfPanel.getDrawingAsImage();

		JFileChooser jfc = new JFileChooser(new File("."));
		jfc.setMultiSelectionEnabled(false);
		if (jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
		{
			File file = jfc.getSelectedFile();
			try
			{
				ImageIO.write(image, "png", file);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	private void close()
	{
		dispose();
	}

	private void convertLaTeX()
	{
		if (proof == null)
		{
			return;
		}

		TextDialog dialog = new TextDialog(this);
		dialog.setText(LaTeXStringBuilder.toLaTeX(proof));
		dialog.setLocationRelativeTo(this);
		dialog.setVisible(true);
	}

	private class MainMenuBar extends JMenuBar
	{
		public MainMenuBar()
		{
			JMenu menuFile = new JMenu("File");
			JMenu menuEdit = new JMenu("Edit");
			JMenuItem itemSaveImg = new JMenuItem("Save Image...");
			JMenuItem itemExit = new JMenuItem("Exit");
			JMenuItem itemConvert = new JMenuItem("Convert to LaTeX");

			menuFile.add(itemSaveImg);
			menuFile.addSeparator();
			menuFile.add(itemExit);
			menuEdit.add(itemConvert);
			add(menuFile);
			add(menuEdit);

			itemSaveImg.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					saveImage();
				}
			});
			itemExit.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					close();
				}
			});
			itemConvert.addActionListener(new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					convertLaTeX();
				}
			});
		}
	}

	public static void main(String[] args)
	{
		System.out.println("GUI test.");

		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			public void run()
			{
				Config.saveSystemConfig();
			}
		});

		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		}

		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				MainFrame f = new MainFrame();
				f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				f.setLocationRelativeTo(null);
				f.setVisible(true);
			}
		});
	}
}
