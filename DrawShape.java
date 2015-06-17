import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class DrawShape extends JFrame
{

    private DrawPanel drawPanel;
    private JPanel buttonPanel;
    private JButton btnDraw;
    private JButton btnReflectionX;
    private JButton btnReflectionY;

    private JButton btnHorExpContr;

    private JButton btnVertExpanContr;

    private JButton btnHorShear;
    private JButton btnVerShear;
    private JButton btnRotateClockwise;
    private JButton btnRotateCounterClockwise;

    private JButton btnClear;

    //private int x[]={0,0,0,0,0,0,0,0,0,0};
    //private int y[]={0,0,0,0,0,0,0,0,0,0};
    private int x[];
    private int y[];
    private int transformed_x[];
    private int transformed_y[];

    public DrawShape()
    {
        super("Mat261 Project");
        setCoordinatesToShape();
        drawPanel = new DrawPanel();

        buttonPanel = new JPanel();

        btnDraw = new JButton("draw shape");
        btnDraw.addActionListener(new DrawShapeActionListen());
        btnReflectionX =    new JButton("reflection in x");
        btnReflectionX.addActionListener(new DrawReflectionX());
        btnReflectionY =     new JButton("reflection in y");
        btnReflectionY.addActionListener(new DrawReflectionY());
        btnHorExpContr =     new JButton("horizontal expansion/contraction");
        btnHorExpContr.addActionListener(new DrawHorExpContr());
        btnVertExpanContr =  new JButton("vertical expansion/contraction");
        btnVertExpanContr.addActionListener(new DrawVertExpContr());
        btnHorShear =       new JButton("horizontal shear");
        btnHorShear.addActionListener(new DrawHorizontalShear());
        btnVerShear =        new JButton("vertical shear");
        btnVerShear.addActionListener(new DrawVerticalShear());
        btnRotateClockwise = new JButton("rotate clockwise");
        btnRotateClockwise.addActionListener(new DrawRotateClockwise());
        btnRotateCounterClockwise = new JButton("rotate counter clockwise");
		btnRotateCounterClockwise.addActionListener(new DrawRotateCounterClockwise());
        btnClear =      new JButton("clear");
        btnClear.addActionListener(new ClearDrawPanel());

        drawPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        drawPanel.setPreferredSize(new Dimension(320, 320));

        buttonPanel.setLayout(new GridLayout(0, 1));
        // buttonPanel.setPreferredSize(new Dimension(210, 320));
        buttonPanel.add(btnDraw);
        buttonPanel.add(btnReflectionX);
        buttonPanel.add(btnReflectionY);
        buttonPanel.add(btnHorExpContr);
        buttonPanel.add(btnVertExpanContr);
        buttonPanel.add(btnHorShear);
        buttonPanel.add(btnVerShear);
        buttonPanel.add(btnRotateClockwise);
        buttonPanel.add(btnRotateCounterClockwise);
        buttonPanel.add(btnClear);
        buttonPanel.setBackground(Color.BLUE);

        add(drawPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        setSize(600, 400);
        setVisible(true);
    }


     public void transform(Matrix transformMatrix)
     {

         // Matrix transformMatrix = new Matrix(new double[][] {{1,0},{.5,1}});
         for ( int i = 0; i < x.length; i++)
         {
             Matrix tempMatrix = new Matrix(new double[][] {{x[i]},{y[i]}});
             Matrix resultMatrix = transformMatrix.multiply(tempMatrix);

             System.err.println(x[i] + ", " + y[i] + " -> " + resultMatrix.get(0,0) + ", " + resultMatrix.get(1,0));

             transformed_x[i] = (int) resultMatrix.get(0,0);
             transformed_y[i] = (int) resultMatrix.get(1,0);
         }
     }

    private void setCoordinatesToShape()
    {
        int xW[] = { 1 , 19, 24, 29, 48, 33, 39, 24, 10, 15 };
        int yW[] = { -14, -14, -1, -14, -14, -23, -37, -28, -37, -23 };
        x = xW;
        y = yW;
    }

    private void resetCoordinates()
    {
        for (int i = 0 ; i < x.length; i++)
        {
            x[i] = 0;
            y[i] = 0;
            transformed_x[i] = 0;
            transformed_y[i] = 0;
        }
    }

    private class DrawPanel extends JPanel
    {
        private Font font;
        public DrawPanel()
        {
            font =  new Font("Courier New", Font.BOLD, 14);
            transformed_x = new int[x.length];
            transformed_y = new int[y.length];
        }


        public void paintComponent( Graphics g )
        {
            super.paintComponent(g);
            setBackground(Color.WHITE);

            // draw x axis
            g.drawLine(0, getHeight()/ 2, getWidth(),getHeight()/2);
            // draw y axis
            g.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight());
            g.setFont(font);
            g.drawString("Y", getWidth()/2, 10);
            g.drawString("X", 10, getHeight()/2);

            g.setColor(Color.BLUE);
            drawPolygon(g, x, y, getWidth() / 2, getHeight() / 2);
            g.setColor(Color.RED);
            drawPolygon(g, transformed_x, transformed_y, getWidth() / 2, getHeight() / 2);

        }

        private void drawPolygon(Graphics g, int x[], int y[], int offsetX, int offsetY)
        {
            int px = x[0];
            int py = y[0];
            for(int i = 1; i < x.length; i++)
            {
                g.drawLine(px+offsetX, offsetY-py, x[i]+offsetX, offsetY-y[i]);
                px=x[i];
                py=y[i];
            }
            g.drawLine(px+offsetX, offsetY-py, x[0]+offsetX, offsetY-y[0]);
        }
    }


    class DrawShapeActionListen implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("draw shape button pressed");
            setCoordinatesToShape();
            drawPanel.repaint();
        }
    }

    class DrawHorizontalShear implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("horizontal shear button");

            Matrix transformMatrix = new Matrix(new double[][] {{1,-.5},{0,1}});
            transform(transformMatrix);
            drawPanel.repaint();
        }
    }

    class DrawVerticalShear implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.err.println("draw vertical shear");

            Matrix transformMatrix = new Matrix(new double[][] {{1,0},{.5,1}});
            transform(transformMatrix);
            drawPanel.repaint();
        }
    }


    class DrawReflectionX implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.err.println("reflection in x");

            Matrix transformMatrix = new Matrix(new double[][] {{1,0},{0,-1}});
            transform(transformMatrix);
            drawPanel.repaint();
        }
    }



    class DrawReflectionY implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.err.println("reflection in y");

            //resetCoordinates();
            //setCoordinatesToShape();
            Matrix transformMatrix = new Matrix(new double[][] {{-1,0},{0,1}});

            transform(transformMatrix);

            drawPanel.repaint();
        }
    }


    class DrawHorExpContr implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.err.println("horizontal expansion/contraction");

            double k  = 2; //Double.parseDouble(JOptionPane.showInputDialog("please input value for k"));

            Matrix transformMatrix = new Matrix(new double[][] {{k,0},{0,1}});
            transform(transformMatrix);
            drawPanel.repaint();

        }
    }

    class DrawVertExpContr implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.err.println("vertical expansion/contraction");
            double k  =  2; //Double.parseDouble(JOptionPane.showInputDialog("please input value for k"));
            transform(new Matrix(new double[][] {{1,0},{0,k}}));
            drawPanel.repaint();
        }
    }

    class ClearDrawPanel implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            resetCoordinates();
            drawPanel.repaint();
        }
    }

    class DrawRotateClockwise implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {

            int theta = 45; //Integer.parseInt(JOptionPane.showInputDialog("please input a value for theta"));

            Matrix transformMatrix = new Matrix(new double[][] {{Math.cos(theta),-Math.sin(theta)},{Math.sin(theta),Math.cos(theta)}});
            transform(transformMatrix);

            drawPanel.repaint();
        }
    }
	
	class DrawRotateCounterClockwise implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
        {

            int theta = -45; //Integer.parseInt(JOptionPane.showInputDialog("please input a value for theta"));

            Matrix transformMatrix = new Matrix(new double[][] {{Math.cos(theta),-Math.sin(theta)},{Math.sin(theta),Math.cos(theta)}});
            transform(transformMatrix);

            drawPanel.repaint();
        }
		
		
	}
}