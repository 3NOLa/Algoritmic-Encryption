package project.keys.ChaosGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;


public class ChaosgamePanel extends JPanel implements ActionListener{

    private final int WIDTH = 800;
    private final int HEIGHT = 700;
    private final int FRACTAL_POINTS_PER_STEP = 100;

    private Timer timer;
    private Shape shape;
    private Point2D currentPoint;
    private BufferedImage canvas;
    
    
    public ChaosgamePanel(Shape shape)
    {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.shape = shape;
        shape.printVertices();
        
        this.currentPoint = (this.shape.firstPoint != null)? this.shape.firstPoint : this.shape.generateRandomPoint();

        this.canvas = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        drawFractalToImage();

        timer = new Timer(30, this);
        timer.start();
    }

    private void drawFractalToImage() {
        Graphics2D g2 = canvas.createGraphics();
        g2.setColor(Color.RED);
        
        drawShape(g2);
        drawFractal(g2);
    
        g2.dispose();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(canvas, 0, 0, null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Graphics2D g2 = canvas.createGraphics();
        g2.setColor(Color.RED);
        drawFractal(g2);   // Add more points each tick
        g2.dispose();

        repaint();         // Show the new points
    }


    private void drawShape(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));
        for (Point2D p : shape.getVertices()) {
            g.fillOval((int)p.getX() - 5, (int)p.getY() - 5, 10, 10);
        }

        Point2D[] vertices =  shape.getVertices();
        for (int i = 0; i < shape.getVerticesAmount(); i++) {
            Point2D vertex1 = vertices[i];
            Point2D vertex2 = vertices[(i + 1) % shape.getVerticesAmount()];

            g.drawLine((int)vertex1.getX(),(int) vertex1.getY(),(int) vertex2.getX(),(int) vertex2.getY());
        }
    }

    private void drawFractal(Graphics g)
    {
        g.setColor(Color.RED);
        for (int i = 0; i < FRACTAL_POINTS_PER_STEP; i++) {
            g.fillOval((int)currentPoint.getX(), (int) currentPoint.getY(), 2, 2);
            currentPoint = this.shape.nextFractalPoint(currentPoint);
        }
    }

    public static void displayChaosGame() {
        JFrame frame = new JFrame("Chaos Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChaosgamePanel(new Shape(1298, 3, 800,700)));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    
}

class main3{
    public static void main(String[] args) {
        ChaosgamePanel.displayChaosGame();
    }
}
