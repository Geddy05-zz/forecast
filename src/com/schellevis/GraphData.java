package com.schellevis;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by geddy on 19/06/16.
 */
public class GraphData extends JPanel {
        private static final int MAX_SCORE = 400;
        private static final int PREF_W = 800;
        private static final int PREF_H = 650;
        private static final int BORDER_GAP = 30;
        private static final Color GRAPH_COLOR = Color.green;
        private static final Color GRAPH_POINT_COLOR = new Color(150, 50, 50, 180);
        private static final Stroke GRAPH_STROKE = new BasicStroke(3f);
        private static final int GRAPH_POINT_WIDTH = 12;
        private static final int Y_HATCH_CNT = 30;
        private ArrayList<Double> scores;

        public GraphData(ArrayList<Double> scores) {
            this.scores = scores;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            double xScale = ((double) getWidth() - 1 * BORDER_GAP) / (scores.size());
            double yScale = ((double) getHeight() - 1 * BORDER_GAP) / (MAX_SCORE);

            ArrayList<Point> graphPoints = new ArrayList<Point>();
            for (int i = 0; i < scores.size(); i++) {
                int x1 = (int) (i * xScale + BORDER_GAP);
                int y1 = (int) ((MAX_SCORE - scores.get(i)) * yScale + BORDER_GAP);
                graphPoints.add(new Point(x1, y1));
            }

            // create x and y axes
            g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, BORDER_GAP, BORDER_GAP);
            g2.drawLine(BORDER_GAP, getHeight() - BORDER_GAP, getWidth() - BORDER_GAP, getHeight() - BORDER_GAP);

            // create hatch marks for y axis.
            for (int i = 0; i < Y_HATCH_CNT; i++) {
                int x0 = BORDER_GAP;
                int x1 = GRAPH_POINT_WIDTH + BORDER_GAP;
                int y0 = getHeight() - (((i + 1) * (getHeight() - BORDER_GAP * 2)) / Y_HATCH_CNT + BORDER_GAP);
                int y1 = y0;

//                if (scores.size() > 0) {
                    g2.setColor(Color.cyan);
//                    g2.drawLine(padding + labelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
                    g2.setColor(Color.BLACK);
                    String yLabel = (i* (MAX_SCORE/Y_HATCH_CNT)+10)+ "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(yLabel);
                    g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
//                }

                g2.drawLine(x0, y0, x1, y1);

            }

            // and for x axis
            for (int i = 0; i < scores.size() - 1; i++) {
                int x0 = (i + 1) * (getWidth() - BORDER_GAP * 2) / (scores.size() - 1) + BORDER_GAP;
                int x1 = x0;
                int y0 = getHeight() - BORDER_GAP;
                int y1 = y0 - GRAPH_POINT_WIDTH;

                if( i % 2 == 0) {
                    g2.setColor(Color.cyan);
//                g2.drawLine(x0, getHeight() - padding - labelPadding - 1 - pointWidth, x1, padding);
                    g2.setColor(Color.BLACK);
                    String xLabel = i + "";
                    FontMetrics metrics = g2.getFontMetrics();
                    int labelWidth = metrics.stringWidth(xLabel);
                    g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
                }
                g2.drawLine(x0, y0, x1, y1);
            }

            Stroke oldStroke = g2.getStroke();
            g2.setColor(GRAPH_COLOR);
            g2.setStroke(GRAPH_STROKE);
            for (int i = 0; i < graphPoints.size() - 1; i++) {
                if(i > 35){
                    break;
                }
                int x1 = graphPoints.get(i).x;
                int y1 = graphPoints.get(i).y;
                int x2 = graphPoints.get(i + 1).x;
                int y2 = graphPoints.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
            g2.setColor(Color.blue);
            g2.setStroke(GRAPH_STROKE);
            if (graphPoints.size() > 36){
                for (int i = 36; i < graphPoints.size() - 1; i++) {
                int x1 = graphPoints.get(i).x;
                int y1 = graphPoints.get(i).y;
                int x2 = graphPoints.get(i + 1).x;
                int y2 = graphPoints.get(i + 1).y;
                g2.drawLine(x1, y1, x2, y2);
            }
            }

            g2.setStroke(oldStroke);
            g2.setColor(GRAPH_POINT_COLOR);
            for (int i = 0; i < graphPoints.size(); i++) {
                int x = graphPoints.get(i).x - GRAPH_POINT_WIDTH / 2;
                int y = graphPoints.get(i).y - GRAPH_POINT_WIDTH / 2;;
                int ovalW = GRAPH_POINT_WIDTH;
                int ovalH = GRAPH_POINT_WIDTH;
                g2.fillOval(x, y, ovalW, ovalH);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(PREF_W, PREF_H);
        }

        public static void createAndShowGui() {
            ArrayList<Double> scores = new ArrayList<Double>();

            ArrayList<Integer> items = ReadData.readCSV();

            SES ses = createBestSES(items);

            DES des = createBestDES(items);

//            GraphData mainPanel = new GraphData(ses.bestPredication);

            GraphData mainPanel = new GraphData(des.predications);

            JFrame frame = new JFrame("DrawGraph");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(mainPanel);
            frame.pack();
            frame.setLocationByPlatform(true);
            frame.setVisible(true);


        }

    private static SES createBestSES(ArrayList<Integer> items){
        SES ses = new SES(items);

        for (double alpha = 0.0; alpha < 1.00 ; alpha += 0.01) {
            ses.setSmoothingLevel(alpha);
            ses.doPrediction(12);
        }
        return ses;
    }

    private static DES createBestDES(ArrayList<Integer> items){
        DES des = new DES(items);
        des.doPrediction(12);
        return des;
    }
}
