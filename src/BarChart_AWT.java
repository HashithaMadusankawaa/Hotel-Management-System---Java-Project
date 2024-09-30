import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.*;
import java.awt.*;

public class BarChart_AWT extends ApplicationFrame {

    public BarChart_AWT(String applicationTitle, String chartTitle) {
        super(applicationTitle);

        // Create the chart
        JFreeChart barChart = ChartFactory.createBarChart(
                chartTitle,
                "Category",
                "Score",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        // Customize the chart to have a macOS-like light theme
        customizeChart(barChart);
        
     

        // Create a panel to display the chart
        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        setContentPane(chartPanel);

        // Add mouse hover event listener
        addMouseHoverListener(chartPanel);
    }

    private CategoryDataset createDataset() {
        final String fiat = "FIAT";
        final String audi = "AUDI";
        final String ford = "FORD";
        final String speed = "Speed";
        final String millage = "Millage";
        final String userrating = "User Rating";
        final String safety = "Safety";
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, fiat, speed);
        dataset.addValue(3.0, fiat, userrating);
        dataset.addValue(5.0, fiat, millage);
        dataset.addValue(5.0, fiat, safety);

        dataset.addValue(5.0, audi, speed);
        dataset.addValue(6.0, audi, userrating);
        dataset.addValue(10.0, audi, millage);
        dataset.addValue(4.0, audi, safety);

        dataset.addValue(4.0, ford, speed);
        dataset.addValue(2.0, ford, userrating);
        dataset.addValue(3.0, ford, millage);
        dataset.addValue(6.0, ford, safety);

        return dataset;
    }

    private void customizeChart(JFreeChart chart) {
        CategoryPlot plot = chart.getCategoryPlot();

        // Background color for the chart (light macOS theme)
        plot.setBackgroundPaint(Color.WHITE);  // White background for a clean look

        // Gridline color
        plot.setRangeGridlinePaint(Color.LIGHT_GRAY);  // Light gray gridlines for subtle effect

        // BarRenderer for customizing the bars
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);  // No bar outlines
        renderer.setShadowVisible(false);   // No shadows
        renderer.setBarPainter(new StandardBarPainter());  // Use standard painter for flat look

        // Set macOS-like soft colors for the bars
        renderer.setSeriesPaint(0, new Color(255, 149, 0));  // Soft orange for FIAT
        renderer.setSeriesPaint(1, new Color(44, 44, 46));   // Dark gray for AUDI
        renderer.setSeriesPaint(2, new Color(88, 86, 214));  // Soft purple for FORD

        // Set rounded corners for the bars
        renderer.setDefaultShadowsVisible(false);  // Remove default shadows for flat look
        renderer.setMaximumBarWidth(0.1);  // Adjust bar width for aesthetic proportions

        // Enable tooltips using setDefaultToolTipGenerator
        renderer.setDefaultToolTipGenerator(new StandardCategoryToolTipGenerator());

        // Set a custom font that looks like macOS (San Francisco/Helvetica)
        Font labelFont = new Font("Helvetica", Font.PLAIN, 14);
        plot.getDomainAxis().setLabelFont(labelFont);  // Font for X-axis label
        plot.getRangeAxis().setLabelFont(labelFont);   // Font for Y-axis label
        plot.getDomainAxis().setTickLabelFont(labelFont);  // Font for category labels
        plot.getRangeAxis().setTickLabelFont(labelFont);   // Font for value labels

        // Remove axis lines and tick marks for a cleaner design
        plot.getDomainAxis().setAxisLineVisible(false);  // Remove X-axis line
        plot.getDomainAxis().setTickMarksVisible(false); // Remove X-axis tick marks
        plot.getRangeAxis().setAxisLineVisible(false);   // Remove Y-axis line
        plot.getRangeAxis().setTickMarksVisible(false);  // Remove Y-axis tick marks
    }

    private void addMouseHoverListener(ChartPanel chartPanel) {
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                // Do nothing on click for now
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                if (entity instanceof CategoryItemEntity) {
                    CategoryItemEntity item = (CategoryItemEntity) entity;
                    // Use getRowKey() and getColumnKey() methods to get series and category names
                    String category = item.getColumnKey().toString(); // Correctly getting the category
                    String series = item.getRowKey().toString(); // Getting the series
                    Number value = item.getDataset().getValue(item.getRowKey(), item.getColumnKey());

                    // Display tooltip as additional feedback
                    String tooltip = String.format("%s - %s: %.2f", series, category, value.doubleValue());
                    chartPanel.setToolTipText(tooltip);
                } else {
                    chartPanel.setToolTipText(null); // Clear tooltip if not hovering over a bar
                }
            }
        });
    }

    public static void main(String[] args) {
        BarChart_AWT chart = new BarChart_AWT("Car Usage Statistics", "Which car do you like?");
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}
