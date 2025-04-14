package project.fronend;

import project.encryptions.aes;
import project.keys.*;
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

enum encryptionType{
	Chaosgame,
	Ksequence,
	TopologicalSort
}

interface FileSelectedListener {
    void onFileSelected(File file);
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

        add(panelContainer);

        setVisible(true);
	}

	public void onFileSelected(File file)
	{
		this.selectedFile = file;
		System.out.println("Main class received file: " + selectedFile.getAbsolutePath());

		boolean alreadyExists = false;
		for (Component comp : panelContainer.getComponents()) {
			if ("Panel 2".equals(panelContainer.getLayout().toString())) {
				alreadyExists = true;
				break;
			}
		}

		if (!alreadyExists) 
			panelContainer.add(new encryptionSelect(this,cardLayout,panelContainer), "Panel 2");
		
		cardLayout.show(panelContainer, "Panel 2");
	}

	public 	void onEncryptionSelected(encryptionType enctype)
	{
		this.selecteEncryption = enctype;
		System.out.println("Main class recived encryption type:" + selecteEncryption.toString());

        boolean alreadyExists = false;
		for (Component comp : panelContainer.getComponents()) {
			if ("Panel 3".equals(panelContainer.getLayout().toString())) {
				alreadyExists = true;
				break;
			}
		}

		if (!alreadyExists) 
			panelContainer.add(new action(selecteEncryption, cardLayout, panelContainer, selectedFile), "Panel 3");

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
    private FileSelectedListener listener;

	public Jopen(FileSelectedListener listener,CardLayout cardLayout, JPanel panelContainer)
	{
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
	}

}

class encryptionSelect extends JPanel implements ActionListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private encryptionTypeListener listener;

	public encryptionSelect(encryptionTypeListener listener,CardLayout cardLayout, JPanel panelContainer)
	{
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
	private int FileSizeEncrypt;
	private Keys key;
	JPanel algoPanel;
	private CardLayout cardLayout;
    private JPanel panelContainer;
	encryptionType type;

	public action(encryptionType type,CardLayout cardLayout, JPanel panelContainer,File FilePtr)
	{
		this.FilePtr = FilePtr;
		this.FileSizeEncrypt = (int)((FilePtr.length() + 15)/ 16) * 16;
		System.out.println("file size: " + FileSizeEncrypt);
		this.type = type;
		this.cardLayout = cardLayout;
		this.panelContainer = panelContainer;
		setLayout(new FlowLayout());
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		
		activateAlgo();

		new Thread(()->{
			//runEncryption();
			runDecryption();
			System.out.println("finished encryption");
		}).start();

		//this.cardLayout.show(this.panelContainer, "Panel 1");
	}

	private void activateAlgo()
	{
		switch (this.type) {
			case Chaosgame:
				algoPanel = ChaosgameActive();
				break;
			case Ksequence:
				KsequenceActive();
				algoPanel = new JPanel();
				break;
			case TopologicalSort:
				algoPanel = TopologicalSortActive();
				break;
			default:
				algoPanel = new JPanel();
				System.out.println("erorr in choosing algoritem");
				break;
		}
		add(algoPanel);
	}

	private JPanel ChaosgameActive()
	{
		int verticesAmount =(int)(FileSizeEncrypt % 4) + 3;
		System.out.println(verticesAmount);
		int seed = (FileSizeEncrypt % 10000) + 1;
		Shape shape = new Shape(seed, verticesAmount, this.WIDTH, this.HEIGHT);
		ChaosgamePanel ch =new ChaosgamePanel(shape);

		this.key = shape;

		return ch;
	}

	private void KsequenceActive()
	{
		int k = (int)(FileSizeEncrypt % 5) + 1;
		int n = (int)(FileSizeEncrypt % 10) + 1;
		int seed = (FileSizeEncrypt % 10000) + 1;
		Ksequence s = new Ksequence(k, n,seed);
		s.findKseqnces();
		s.printSolutions();

		this.key = s;
	}

	private JPanel TopologicalSortActive()
	{
		int nodeCount = (int)(FileSizeEncrypt % 20);
		int averageDegree = (int)((FileSizeEncrypt % 25) / 4) + 1;
		int seed = (FileSizeEncrypt % 10000) + 1;
		DirectedGraph enc = new DirectedGraph("encrypt",nodeCount, averageDegree,seed);

		enc.generateGraph();
		visualizeGraph vis = new visualizeGraph(enc);
		vis.optimizeLayout();
		vis.printLayers();
		GraphVisualizer gh = new GraphVisualizer(vis);

		TopologicalSort t = new TopologicalSort(enc);
		this.key = t;

		return gh;
	}

	private void runEncryption() {
		byte[] key = this.key.getKey16();
		aes a = new aes();
		a.encryptFile(FilePtr, key);
	}
	
	private void runDecryption() {
		byte[] key = this.key.getKey16();
		aes a = new aes();
		a.decryptFile(FilePtr, key);
	}
	
}