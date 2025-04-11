package project.fronend;

import project.keys.ChaosGame.*;
import project.keys.ChaosGame.Shape;
import project.keys.Kseq.*;
import project.keys.graphs.*;

import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;

import java.awt.datatransfer.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

interface FileSelectedListener {
    void onFileSelected(File file);
}

enum encryptionType{
	Chaosgame,
	Ksequence,
	TopologicalSort
}

interface encryptionTypeListener{
	void onEncryptionSelected(encryptionType enctype);
}

public class Front extends JFrame implements FileSelectedListener,encryptionTypeListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
	private CardLayout cardLayout;
    private JPanel panelContainer;
	private File selectedFile;
	private encryptionType selecteEncryption;

	public Front()
	{
		setTitle("encryption alon");
		setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

		cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

		panelContainer.add(new Jopen(this,cardLayout,panelContainer), "Panel 1");
        panelContainer.add(new encryptionSelect(this,cardLayout,panelContainer), "Panel 2");

        add(panelContainer);

        setVisible(true);
	}

	public void onFileSelected(File file)
	{
		this.selectedFile = file;
		System.out.println("Main class received file: " + selectedFile.getAbsolutePath());
	}

	public 	void onEncryptionSelected(encryptionType enctype)
	{
		this.selecteEncryption = enctype;
		System.out.println("Main class recived encryption type:" + selecteEncryption.toString());

		JPanel actionPanel = new action(selecteEncryption, cardLayout, panelContainer,selectedFile);
        panelContainer.add(actionPanel, "Panel 3");

        cardLayout.show(panelContainer, "Panel 3");
	}
	
	public static void main(String[] args) 
	{
		new Front();
	}
}

class Jopen extends JPanel implements ActionListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
	private CardLayout cardLayout;
    private JPanel panelContainer;
    private FileSelectedListener listener;

	public Jopen(FileSelectedListener listener,CardLayout cardLayout, JPanel panelContainer)
	{
		this.cardLayout = cardLayout;
        this.panelContainer = panelContainer;
		this.listener = listener;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH,HEIGHT));

		JLabel title = new JLabel("ALGORITMIC ENCRYPTION METHODS",JLabel.CENTER);
		add(title);

		JButton menuchooser = new JButton("File Explorer");
		menuchooser.addActionListener(this);
		add(menuchooser);
	}

	public void actionPerformed(ActionEvent e)
	{
		JFileChooser ch0 = new JFileChooser();
		int r0 = ch0.showOpenDialog(this);
		if (r0 == JFileChooser.APPROVE_OPTION) 
		{
			File f = ch0.getSelectedFile();
            if (listener != null) {
                listener.onFileSelected(f);
            }	
		}
		cardLayout.show(panelContainer, "Panel 2");
	}

}

class encryptionSelect extends JPanel implements ActionListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
	private CardLayout cardLayout;
    private JPanel panelContainer;
    private encryptionTypeListener listener;

	public encryptionSelect(encryptionTypeListener listener,CardLayout cardLayout, JPanel panelContainer)
	{
		this.cardLayout = cardLayout;
        this.panelContainer = panelContainer;
		this.listener = listener;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH,HEIGHT));

		JLabel title = new JLabel("Choose encryption meothod",JLabel.CENTER);
		add(title);

		JPanel choices = new JPanel(new GridLayout(1, 3));
		createButtons(choices);
		add(choices);
	}

	public void createButtons(JPanel choices)
	{
		for (encryptionType type : encryptionType.values())
		{
			JButton button = new JButton(type.name());
    		button.putClientProperty("encryptionType", type);
    		button.addActionListener(this);
    		choices.add(button);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton clicked = (JButton) e.getSource();
		if (listener != null) {
			listener.onEncryptionSelected((encryptionType)clicked.getClientProperty("encryptionType"));
		}
	}

}

class action extends JPanel
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
	private File FilePtr;
	private CardLayout cardLayout;
    private JPanel panelContainer;
	encryptionType type;

	public action(encryptionType type,CardLayout cardLayout, JPanel panelContainer,File FilePtr)
	{
		this.FilePtr = FilePtr;
		this.type = type;
		this.cardLayout = cardLayout;
		this.panelContainer = panelContainer;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		activateAlgo();

		//this.cardLayout.show(this.panelContainer, "Panel 1");
	}

	private void activateAlgo()
	{
		JPanel algo;
		switch (this.type) {
			case Chaosgame:
				algo = ChaosgameActive();
				break;
			case Ksequence:
				KsequenceActive();
				algo = new JPanel();
				break;
			case TopologicalSort:
				algo = TopologicalSortActive();
				break;
			default:
				algo = new JPanel();
				System.out.println("erorr in choosing algoritem");
				break;
		}
		add(algo);
	}

	private JPanel ChaosgameActive()
	{
		int verticesAmount =(int)(FilePtr.length() % 4) + 3;
		Shape shape = new Shape(1234, verticesAmount, this.WIDTH, this.HEIGHT);
		ChaosgamePanel ch =new ChaosgamePanel(shape);
		return ch;
	}

	private void KsequenceActive()
	{
		int k = (int)(FilePtr.length() % 5) + 1;
		int n = (int)(FilePtr.length() % 5) + 1;
		Ksequence s = new Ksequence(k, n);
		s.findKseqnces();
		s.printSolutions();
		s.findKiterative();
	}

	private JPanel TopologicalSortActive()
	{
		int nodeCount = (int)(FilePtr.length() % 25);
		int averageDegree = (int)(FilePtr.length() % 5);
		DirectedGraph enc = new DirectedGraph("encrypt",nodeCount, averageDegree);

		enc.generateGraph();
		visualizeGraph vis = new visualizeGraph(enc);
		vis.optimizeLayout();
		vis.printLayers();

		GraphVisualizer gh = new GraphVisualizer(vis);
		return gh;
	}
}