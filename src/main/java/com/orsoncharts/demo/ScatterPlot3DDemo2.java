/* ===================
 * Orson Charts - Demo
 * ===================
 *
 * Copyright 2013-2022, by David Gilbert. All rights reserved.
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
 *   - Neither the name of the Object Refinery Limited nor the
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

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Chart3DFactory;
import org.jfree.chart3d.Chart3DPanel;
import org.jfree.chart3d.axis.LabelOrientation;
import org.jfree.chart3d.axis.LogAxis3D;
import org.jfree.chart3d.axis.NumberAxis3D;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.data.xyz.XYZSeries;
import org.jfree.chart3d.data.xyz.XYZSeriesCollection;
import org.jfree.chart3d.graphics3d.Dimension3D;
import org.jfree.chart3d.graphics3d.ViewPoint3D;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.xyz.ScatterXYZRenderer;
import org.jfree.chart3d.style.ChartStyler;

/**
 * A demonstration of a scatter plot in 3D.
 */
@SuppressWarnings("serial")
public class ScatterPlot3DDemo2 extends JFrame {

    static class CustomDemoPanel extends DemoPanel implements ActionListener {
        
        private final JCheckBox checkBox;
        
        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            this.checkBox = new JCheckBox("Logarithmic Scale");
            this.checkBox.setSelected(true);
            this.checkBox.addActionListener(this);
            JPanel controlPanel = new JPanel(new FlowLayout());
            controlPanel.add(this.checkBox);
            add(controlPanel, BorderLayout.SOUTH);
        }    

        @Override
        public void actionPerformed(ActionEvent e) {
            Chart3D chart = (Chart3D) getChartPanel().getDrawable();
            XYZPlot plot = (XYZPlot) chart.getPlot();
            if (this.checkBox.isSelected()) {
                LogAxis3D logAxis = new LogAxis3D("Y (logarithmic scale)");
                logAxis.setTickLabelOrientation(LabelOrientation.PERPENDICULAR);
                logAxis.receive(new ChartStyler(chart.getStyle()));
                plot.setYAxis(logAxis);
            } else {
                NumberAxis3D yAxis = new NumberAxis3D("Y");
                yAxis.setTickLabelOrientation(LabelOrientation.PERPENDICULAR);
                yAxis.receive(new ChartStyler(chart.getStyle()));
                plot.setYAxis(yAxis);
            }
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ScatterPlot3DDemo2(String title) {
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
        XYZDataset dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }
    
    private static Chart3D createChart(XYZDataset dataset) {
        Chart3D chart = Chart3DFactory.createScatterChart("ScatterPlot3DDemo2", 
                null, dataset, "X", "Y", "Z");
        XYZPlot plot = (XYZPlot) chart.getPlot();
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        plot.setDimensions(new Dimension3D(10, 6, 10));
        renderer.setSize(0.1);
        renderer.setColors(new Color(255, 128, 128), new Color(128, 255, 128));
        LogAxis3D yAxis = new LogAxis3D("Y (log scale)");
        yAxis.setTickLabelOrientation(LabelOrientation.PERPENDICULAR);
        yAxis.receive(new ChartStyler(chart.getStyle()));
        plot.setYAxis(yAxis);
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        return chart;
    }

    /**
     * Creates a sample dataset (hard-coded for the purpose of keeping the
     * demo self-contained - in practice you would normally read your data
     * from a file, database or other source).
     * 
     * @return A sample dataset.
     */
    public static XYZDataset<String> createDataset() {
        XYZSeries<String> s1 = new XYZSeries<>("S1");
        for (int i = 0; i < 1000; i++) {
            s1.add(Math.random() * 100, Math.pow(10, Math.random() * 5), 
                    Math.random() * 100);
        }
        XYZSeries<String> s2 = new XYZSeries<>("S2");
        for (int i = 0; i < 1000; i++) {
            s2.add(Math.random() * 100, Math.random() * 100000, 
                    Math.random() * 100);
        }
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
        dataset.add(s1);
        dataset.add(s2);
        return dataset;
    }

    
    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ScatterPlot3DDemo2 app = new ScatterPlot3DDemo2(
                "OrsonCharts : ScatterPlot3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}
