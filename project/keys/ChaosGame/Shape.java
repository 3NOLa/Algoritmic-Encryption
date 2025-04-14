package project.keys.ChaosGame;
import project.keys.*;

import java.awt.Point;
import java.awt.geom.*;
import java.util.Random;

public class Shape implements Keys{
    
    private int verticesAmount;
    private Point2D[] vertices;
    public Point2D firstPoint;
    private final int Password;
    private Random rand;
    private final int WIDTH;
    private final int HEIGHT;

    public Shape(int Password,int verticesAmount, int WIDTH, int HEIGHT)
    {
        if (verticesAmount <= 2)
            throw new IllegalArgumentException("verticesAmount must be greater than 2");
            
        this.HEIGHT = HEIGHT;
        this.WIDTH = WIDTH;
        this.Password = Password;
        rand = new Random(this.Password);
        this.verticesAmount = verticesAmount;
        this.vertices = new Point2D[this.verticesAmount];

        calculatePositions();
    }

    private void calculatePositions() {
        int cx = this.WIDTH / 2;
        int cy = this.HEIGHT / 2;
        int radius = this.HEIGHT / 4;
        double degree = 360.0 / this.verticesAmount; // Ensure floating-point division
    
        for (int i = 0; i < verticesAmount; i++) {
            double angle = Math.toRadians(degree * i - 90); // Convert to radians
            int xi = cx + (int) (radius * Math.cos(angle));
            int yi = cy + (int) (radius * Math.sin(angle));
    
            this.vertices[i] = new Point(xi, yi);
        }
    }

    public Point2D nextFractalPoint(Point2D point)
    {
        int randomIndex = rand.nextInt(verticesAmount);
        Point2D randomVertex = this.vertices[randomIndex];

        double ratio = getFractalRatio();

        double newX = point.getX() + (randomVertex.getX() - point.getX()) * ratio;
        double newY = point.getY() + (randomVertex.getY() - point.getY()) * ratio;

        return new Point2D.Double(newX, newY);
    }

    public Point2D generateRandomPoint()
    {
        double minX = Double.MAX_VALUE, minY = Double.MAX_VALUE;
        double maxX = Double.MIN_VALUE, maxY = Double.MIN_VALUE;

        for(Point2D p : vertices)
        {
            minX = (p.getX() < minX) ? p.getX() : minX;
            minY = (p.getY() < minY) ? p.getY() : minY;
            maxX = (p.getX() > maxX) ? p.getX() : maxX;
            maxY = (p.getX() > maxY) ? p.getX() : maxY;
        }

        int x,y;
        do{
            x = (int) (minX + rand.nextDouble(maxX - minX));
            y = (int) (minY + rand.nextDouble(maxY - minY));
        }while(!rayCastingAlgo(x, y));

        this.firstPoint = new Point(x,y);
        return this.firstPoint;
    }

    public boolean rayCastingAlgo(int x, int y) {
        int n = vertices.length;
        boolean inside = false;

        for (int i = 0, j = n - 1; i < n; j = i++) {
            double xi = vertices[i].getX(), yi = vertices[i].getY();
            double xj = vertices[j].getX(), yj = vertices[j].getY();

            boolean intersect = ((yi > y) != (yj > y)) &&
                                (x < (xj - xi) * (y - yi) / (yj - yi) + xi);

            if (intersect) {
                inside = !inside; // Flip state on every intersection
            }
        }

        return inside;
    }
    
    public byte[] getKey16(){
        byte key[] = new byte[16];

        if(this.firstPoint == null)this.firstPoint = generateRandomPoint();

        byte first = (byte)((int)((this.firstPoint.getX() + this.firstPoint.getY() / 32) + 1) & 0xFF);
        key[0] =first;

        Point2D currentPoint = this.firstPoint;
        for(int i=1;i<16;i++)
        {
            Point2D p = nextFractalPoint(currentPoint);

            byte k = (byte)((int)((p.getX() + p.getY() / 32) + 1) & 0xFF);
            key[i] = k;

            currentPoint = p;
        }
        
        return key;
    }

    public Point2D[] getVertices(){
        return this.vertices;
    }

    public int getVerticesAmount(){
        return this.verticesAmount;
    }

    private double getFractalRatio() {
        switch (verticesAmount) {
            case 3: return 0.5;        // Classic Sierpinski triangle
            case 4: return 0.666667;   // Better square fractal
            case 5: return 0.618034;   // Golden ratio for pentagon
            case 6: return 0.633333;   // Optimized for hexagon
            default: return 0.5;       // Default to 0.5 for other cases
        }
    }

    public void printVertices()
    {
        for (int i = 0; i < vertices.length; i++) {
            System.out.println("x: " + vertices[i].getX() + " y: " + vertices[i].getY());
        }
    }

}
