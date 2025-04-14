package project.keys.ChaosGame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.geom.*;


public class ChaosgamePanel extends JPanel implements ActionListener{

    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private final int FRACTAL_POINTS_PER_STEP = 100;

    private Timer timer;
    private Shape shape;
    private Point2D currentPoint;
    private int count = 0;
    
    
    public ChaosgamePanel(Shape shape)
    {
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        this.shape = shape;
        shape.printVertices();

        currentPoint = (this.shape.firstPoint != null)? this.shape.firstPoint : this.shape.generateRandomPoint();

        timer = new Timer(30, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        if (count++ < 2)
            drawShape(g2d);
        drawFractal(g2d);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); 
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
        frame.add(new ChaosgamePanel(new Shape(1298, 3, 800,800)));
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
