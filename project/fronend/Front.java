package project.fronend;

import project.encryptions.wrapper;
import project.encryptions.QuadraticEWrapper;
import project.encryptions.QuadraticEquationEncryption;
import project.encryptions.aes;
import project.encryptions.aesUIWrapper;
import project.encryptions.encryption;
import project.keys.*;
import project.keys.ChaosGame.*;
import project.keys.ChaosGame.Shape;
import project.keys.Kseq.*;
import project.keys.graphs.*;

import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

enum keysType{
	Chaosgame,
	Ksequence,
	TopologicalSort
}

interface FileSelectedListener {
    void onFileSelected(File file);
}

interface keysTypeListener{
	void onkeysSelected(keysType enctype);
}

public class Front extends JFrame implements FileSelectedListener,keysTypeListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 800;
	private JPanel mainPanel;
	private CardLayout cardLayout;
    private JPanel panelContainer;
	private File selectedFile;
	private keysType selectetKey;
	private encryption encryptionType;
	private wrapper encWrapper;
	private boolean encrypt = true;

	public Front()
	{
		setTitle("encryption alon");
		setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
		
		cardLayout = new CardLayout();
        panelContainer = new JPanel(cardLayout);

		createMainPanel();

		panelContainer.add(mainPanel, "Panel 1");

        add(panelContainer);
        setVisible(true);
	}

	private void createMainPanel()
	{
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setPreferredSize(new Dimension(WIDTH,HEIGHT));

		Jopen selectFileMenu = new Jopen(this);
		mainPanel.add(selectFileMenu);

		JPanel optionPanel = createOptionPanel();
		mainPanel.add(optionPanel);

		keysSelect keysMenu = new keysSelect(this);
		mainPanel.add(keysMenu);

		JPanel encMenu = createEncOptionPanel();
		mainPanel.add(encMenu);

		JPanel startPanel = createStartPanel();
		mainPanel.add(startPanel);
	}

	private JPanel createStartPanel()
	{
		JPanel startPanel = new JPanel(new BorderLayout());
		startPanel.setPreferredSize(new Dimension(WIDTH,160));

		JButton startButton = new JButton("Start");
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Component existing = null;
				for (Component comp : panelContainer.getComponents()) {
					if (panelContainer.getComponentZOrder(comp) != -1) {
						if ("Panel 3".equals(panelContainer.getClientProperty(comp))) {
							existing = comp;
							break;
						}
					}
				}

				if (existing == null) 
					panelContainer.add(new action(selectetKey, cardLayout, panelContainer, selectedFile,encryptionType, encWrapper, encrypt), "Panel 3");

				cardLayout.show(panelContainer, "Panel 3");
			}
		});

		startPanel.add(startButton,BorderLayout.CENTER);

		return startPanel;
	}

	private JPanel createOptionPanel()
	{
		JPanel optionPanel = new JPanel();
		optionPanel.setPreferredSize(new Dimension(WIDTH,160));

		JPanel gridPanel = new JPanel(new GridLayout(1,2));

		JButton encryptButton = new JButton("Encrypt");
		JButton decryptButton = new JButton("Decrypt");
		
		ActionListener Option = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();

				if(b.getText().equals("Encrypt"))
					encrypt = true;
				else
					encrypt = false;
			}
			
		};
		encryptButton.addActionListener(Option);
		decryptButton.addActionListener(Option);

		gridPanel.add(encryptButton);
		gridPanel.add(decryptButton);

		optionPanel.add(gridPanel);
		return optionPanel;
	}

	private JPanel createEncOptionPanel()
	{
		JPanel optionPanel = new JPanel();
		optionPanel.setPreferredSize(new Dimension(WIDTH,160));

		JPanel gridPanel = new JPanel(new GridLayout(1,2));

		JButton aes = new JButton("aes Encryption");
		JButton Quadratic = new JButton("Quadratic Equation Encryption");
		
		ActionListener EncOption = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();

				if(b.getText().equals("aes Encryption"))
				{
					encryptionType = new aes();
					encWrapper = new aesUIWrapper(encryptionType, null, b, null, selectedFile, encrypt);
				}
				else
				{
					encryptionType = new QuadraticEquationEncryption(selectedFile);
					encWrapper = new QuadraticEWrapper(encryptionType, null, b, null, selectedFile, encrypt);
				}
			}
			
		};

		aes.addActionListener(EncOption);
		Quadratic.addActionListener(EncOption);

		gridPanel.add(aes);
		gridPanel.add(Quadratic);

		optionPanel.add(gridPanel);
		return optionPanel;
	}

	public void onFileSelected(File file)
	{
		this.selectedFile = file;
		System.out.println("Main class received file: " + selectedFile.getAbsolutePath());
	}

	public 	void onkeysSelected(keysType enctype)
	{
		this.selectetKey = enctype;
		System.out.println("Main class recived encryption type:" + selectetKey.toString());
	}
	
	public static void main(String[] args) 
	{
		new Front();
	}
}

class Jopen extends JPanel implements ActionListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 160;
    private FileSelectedListener listener;

	public Jopen(FileSelectedListener listener)
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

class keysSelect extends JPanel implements ActionListener
{
	private final int WIDTH = 800;
    private final int HEIGHT = 160;
    private keysTypeListener listener;

	public keysSelect(keysTypeListener listener)
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
		for (keysType type : keysType.values())
		{
			JButton button = new JButton(type.name());
    		button.putClientProperty("keysType", type);
    		button.addActionListener(this);
    		choices.add(button);
		}
	}

	public void actionPerformed(ActionEvent e)
	{
		JButton clicked = (JButton) e.getSource();
		if (listener != null) {
			listener.onkeysSelected((keysType)clicked.getClientProperty("keysType"));
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
	private JPanel algoPanel;
	private CardLayout cardLayout;
    private JPanel panelContainer;
	private keysType type;
	public JButton backButton;
	public JProgressBar bar;
	public encryption enc;
	public wrapper encWrapper;
	public boolean encrypt;
	public Thread t;
	

	public action(keysType type,CardLayout cardLayout, JPanel panelContainer,File FilePtr,encryption enc, wrapper encWrapper,boolean encrypt)
	{
		this.cardLayout = cardLayout;
		this.panelContainer = panelContainer;
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(WIDTH,HEIGHT));

		this.FilePtr = FilePtr;
		this.FileSizeEncrypt = (encrypt)?(int)((FilePtr.length() + 16)/ 16) * 16:(int)FilePtr.length();
		this.type = type;
		this.encrypt = encrypt;

		createUpperMenu();
		activateAlgo();

		this.enc = enc;
		this.encWrapper = encWrapper;
		this.encWrapper.setParmeters(bar, backButton, key, this.FilePtr, encrypt);

		this.t = new Thread(this.encWrapper);
		this.t.start();
	}

	private void createUpperMenu()
	{
		JPanel upperMenu = new JPanel(new BorderLayout());
		this.backButton = new JButton("HomePage");
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(panelContainer, "Panel 1");
				try {
					t.join();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		backButton.setEnabled(false);
		upperMenu.add(backButton,BorderLayout.EAST);

		bar = new JProgressBar();
		bar.setValue(0);
		bar.setStringPainted(true);
		upperMenu.add(bar,BorderLayout.CENTER);
		
		add(upperMenu,BorderLayout.NORTH);
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
		add(algoPanel,BorderLayout.SOUTH);
		revalidate();
    	repaint();
	}

	private JPanel ChaosgameActive()
	{
		int verticesAmount =(int)(FileSizeEncrypt % 4) + 3;
		System.out.println(verticesAmount);
		int seed = (FileSizeEncrypt % 10000) + 1;
		Shape shape = new Shape(seed, verticesAmount, this.WIDTH, 700);
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
}