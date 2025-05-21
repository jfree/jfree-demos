/* ===================
 * Orson Charts - Demo
 * ===================
 *
 * Copyright 2013-present, by David Gilbert. All rights reserved.
 *
 * https://github.com/jfree/jfree-demos
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *   - Neither the name of the JFree organisation nor the
 *     names of its contributors may be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL OBJECT REFINERY LIMITED BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * Note that the above terms apply to the demo source only, and not the 
 * Orson Charts library.
 * 
 */

package com.orsoncharts.demo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.LayoutManager;

import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Chart3DFactory;
import org.jfree.chart3d.Chart3DPanel;
import org.jfree.chart3d.axis.ValueAxis3D;
import org.jfree.chart3d.data.DefaultKeyedValues;
import org.jfree.chart3d.data.category.CategoryDataset3D;
import org.jfree.chart3d.data.category.StandardCategoryDataset3D;
import org.jfree.chart3d.graphics3d.ViewPoint3D;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.label.StandardCategoryItemLabelGenerator;
import org.jfree.chart3d.plot.CategoryPlot3D;
import org.jfree.chart3d.renderer.category.AreaRenderer3D;

/**
 * A test for changes to the value axis range on an area chart.
 */
@SuppressWarnings("serial")
public class AxisRangeDemo1 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ChangeListener {
        
        private final JSlider slider1;
        
        private final JSlider slider2;
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            this.slider1 = new JSlider(-1000, 0);
            this.slider1.setValue(-500);
            this.slider2 = new JSlider(0, 1000);
            this.slider2.setValue(500);
            this.slider1.addChangeListener(this);
            this.slider2.addChangeListener(this);
            JPanel sliderPanel = new JPanel(new FlowLayout());
            sliderPanel.add(new JLabel("Value axis lower bound: "));
            sliderPanel.add(this.slider1);
            sliderPanel.add(new JLabel("Upper bound: "));
            sliderPanel.add(this.slider2);
            add(sliderPanel, BorderLayout.SOUTH);
        }    

        @Override
        public void stateChanged(ChangeEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
            ValueAxis3D yAxis = plot.getValueAxis();
            int min = this.slider1.getValue();
            int max = this.slider2.getValue();
            if (min != max) {
                yAxis.setRange(min, max);
            }
        }
    }
    
    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public AxisRangeDemo1(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        DemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D dataset = createDataset();
        Chart3D chart = Chart3DFactory.createAreaChart("AxisRangeDemo1", 
                "A test for rendering with a restricted value range", dataset, 
                "Row", "Category", "Value");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));

        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.getValueAxis().setRange(-500, 500);
        plot.getRowAxis().setVisible(false);
        
        AreaRenderer3D renderer = (AreaRenderer3D) plot.getRenderer();
        renderer.setItemLabelGenerator(new StandardCategoryItemLabelGenerator(
                StandardCategoryItemLabelGenerator.VALUE_TEMPLATE));
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        content.add(new DisplayPanel3D(chartPanel));
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
       
        return content;
    }
  
    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    private static CategoryDataset3D<String, String, String> createDataset() {    

        StandardCategoryDataset3D<String, String, String> dataset 
                = new StandardCategoryDataset3D<>();
        
        DefaultKeyedValues<String, Number> s0 = new DefaultKeyedValues<>();
        s0.put("A", -500);
        s0.put("B", -200);
        s0.put("C", -400);
        s0.put("D", -150);
        dataset.addSeriesAsRow("All Negative", s0);
        
        DefaultKeyedValues<String, Number> s1 = new DefaultKeyedValues<>();
        s1.put("A", -500);
        s1.put("B", 500);
        s1.put("C", 0);
        s1.put("D", -150);
        dataset.addSeriesAsRow("Alternating 1", s1);

        DefaultKeyedValues<String, Number> s2 = new DefaultKeyedValues<>();
        s2.put("A", 500);
        s2.put("B", -500);
        s2.put("C", 0);
        s2.put("D", 150);
        dataset.addSeriesAsRow("Alternating 2", s2);

        DefaultKeyedValues<String, Number> s3 = new DefaultKeyedValues<>();
        s3.put("A", 500);
        s3.put("B", 200);
        s3.put("C", 400);
        s3.put("D", 150);
        dataset.addSeriesAsRow("All Positive", s3);

        return dataset;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        AxisRangeDemo1 app = new AxisRangeDemo1(
                "OrsonCharts: AreaChart3DDemo3.java");
        app.pack();
        app.setVisible(true);
    }

}