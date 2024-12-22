package secure_pacs_simulator;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Kesia ISL
 */

/**
 * This program demonstrates how to draw line chart with CategoryDataset
 * using JFreechart library.
 * @author www.codejava.net
 * https://www.jfree.org/jfreechart/samples.html
 */
public class LineChartDemo extends JFrame {
 
    public LineChartDemo() {
        super("Line Chart Example with JFreechart");
 
        JPanel chartPanel = createChartPanel2();
        add(chartPanel, BorderLayout.CENTER);
 
        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
 
    private JPanel createChartPanel() {
        // creates a line chart object
        // returns the chart panel
            String chartTitle = "Programming Languages Trends";
        String categoryAxisLabel = "Interest over time";
        String valueAxisLabel = "Popularity";

        CategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, dataset);

        return new ChartPanel(chart);
    }
    
    private JPanel createChartPanel2() {
        // creates a line chart object
        // returns the chart panel
        String chartTitle = "LFSR Key trends";
        String categoryAxisLabel = "Key index";
        String valueAxisLabel = "Key index value";

        CategoryDataset dataset = createDataset();

        JFreeChart chart = ChartFactory.createLineChart(chartTitle,
                categoryAxisLabel, valueAxisLabel, dataset);
        
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(2, Color.GREEN);
        renderer.setSeriesPaint(3, Color.darkGray);
        renderer.setSeriesPaint(4, Color.PINK);
        plot.setRenderer(renderer);
        

        return new ChartPanel(chart);
    }
        
    private CategoryDataset createDataset() {
        // creates chart dataset...
        // returns the dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        

        String data1 =	 "1001100001101010101000100011111010101001000101100101100001001101111101000110111001101100111100011101101010111010011011101"; 
        String data2 =	 "1111011010010001111010101100101111001100111100000010000001011101110110101000001100101101011001110110100100001110111010011"; 
        String data3 =  "0001111100011110011001011111100100110000011010000001010001010111111100010111101110010000101011110011111010010001101000010" ;
        String data4 = 	 "1011111001000001010001010101101110011101101001101100011100110100011010110100111000110111101101001110010011100011110100001"; 
        String data5 =	 "0110111011110110001111100100010010111111111110010011101110101001101111111001000111111111010010110100000101000011011100010"; 
        
        String series1 = "Key 1";
        String series2 = "Key 2";
        String series3 = "Key 3";
        String series4 = "Key 4";
        String series5 = "Key 5";
        String c = "";
//        String series1 = "Java";
//        String series2 = "PHP";
//        String series3 = "C++";
//        String series4 = "C#";
        for(int i = 0; i<data1.length(); i++){
             c = ""+i+1;
            if(data1.charAt(i) == '1'){               
                dataset.addValue(1.0, series1, c);
            }else{
                dataset.addValue(0.0, series1, c);
            }
            
        }
        for(int i = 0; i<data2.length(); i++){
             c = ""+i+1;
            if(data2.charAt(i) == '1'){               
                dataset.addValue(1.0, series2, c);
            }else{
                dataset.addValue(0.0, series2, c);
            }
            
        }
        
         for(int i = 0; i<data3.length(); i++){
             c = ""+i+1;
            if(data3.charAt(i) == '1'){               
                dataset.addValue(1.0, series3, c);
            }else{
                dataset.addValue(0.0, series3, c);
            }
        }
         
        for(int i = 0; i<data4.length(); i++){
             c = ""+i+1;
            if(data4.charAt(i) == '1'){               
                dataset.addValue(1.0, series4, c);
            }else{
                dataset.addValue(0.0, series4, c);
            }
        }
        
         for(int i = 0; i<data5.length(); i++){
             c = ""+i+1;
            if(data5.charAt(i) == '1'){               
                dataset.addValue(1.0, series5, c);
            }else{
                dataset.addValue(0.0, series5, c);
            }
        }       
//        dataset.addValue(5.0, series1, "2005");
//        dataset.addValue(4.8, series1, "2006");
//        dataset.addValue(4.5, series1, "2007");
//        dataset.addValue(4.3, series1, "2008");
//        dataset.addValue(4.0, series1, "2009");
//        dataset.addValue(4.1, series1, "2010");
//        dataset.addValue(4.2, series1, "2011");
//        dataset.addValue(4.2, series1, "2012");
//        dataset.addValue(4.0, series1, "2013");

//        dataset.addValue(5.0, series1, "2005");
//        dataset.addValue(4.8, series1, "2006");
//        dataset.addValue(4.5, series1, "2007");
//        dataset.addValue(4.3, series1, "2008");
//        dataset.addValue(4.0, series1, "2009");
//        dataset.addValue(4.1, series1, "2010");
//        dataset.addValue(4.2, series1, "2011");
//        dataset.addValue(4.2, series1, "2012");
//        dataset.addValue(4.0, series1, "2013");
        
//        dataset.addValue(4.0, series2, "2005");
//        dataset.addValue(4.2, series2, "2006");
//        dataset.addValue(3.8, series2, "2007");
//        dataset.addValue(3.6, series2, "2008");
//        dataset.addValue(3.4, series2, "2009");
//        dataset.addValue(3.4, series2, "2010");
//        dataset.addValue(3.3, series2, "2011");
//        dataset.addValue(3.1, series2, "2012");
//        dataset.addValue(3.2, series2, "2013");
//
//        dataset.addValue(3.6, series3, "2005");
//        dataset.addValue(3.4, series3, "2006");
//        dataset.addValue(3.5, series3, "2007");
//        dataset.addValue(3.2, series3, "2008");
//        dataset.addValue(3.2, series3, "2009");
//        dataset.addValue(3.0, series3, "2010");
//        dataset.addValue(2.8, series3, "2011");
//        dataset.addValue(2.8, series3, "2012");
//        dataset.addValue(2.6, series3, "2013");
//
//        dataset.addValue(3.2, series4, "2005");
//        dataset.addValue(3.2, series4, "2006");
//        dataset.addValue(3.0, series4, "2007");
//        dataset.addValue(3.0, series4, "2008");
//        dataset.addValue(2.8, series4, "2009");
//        dataset.addValue(2.7, series4, "2010");
//        dataset.addValue(2.6, series4, "2011");
//        dataset.addValue(2.6, series4, "2012");
//        dataset.addValue(2.4, series4, "2013");

        return dataset;
    }
 

    
 
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LineChartDemo().setVisible(true);
            }
        });
    }
}