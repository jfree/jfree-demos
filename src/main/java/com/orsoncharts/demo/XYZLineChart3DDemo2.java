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

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Chart3DFactory;
import org.jfree.chart3d.Chart3DPanel;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.data.xyz.XYZSeries;
import org.jfree.chart3d.data.xyz.XYZSeriesCollection;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.plot.XYZPlot;

/**
 * A demo of a 3D line chart.
 */
@SuppressWarnings("serial")
public class XYZLineChart3DDemo2 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public XYZLineChart3DDemo2(String title) {
        super(title);
        addWindowListener(new ExitOnClose());
        getContentPane().add(createDemoPanel());
    }

    private static XYZDataset createDataset() {
        XYZSeriesCollection<String> dataset = new XYZSeriesCollection<>();
        XYZSeries<String> series = new XYZSeries<>("Series 1");
        series.add(5, 5, 5);
        series.add(15, 5, 5);
        series.add(15, 15, 5);
        series.add(5, 15, 5);
        series.add(5, 5, 5);
        series.add(5, 5, 15);
        series.add(15, 5, 15);
        series.add(15, 15, 15);
        series.add(5, 15, 15);
        series.add(5, 5, 15);
        series.add(5, 15, 15);
        series.add(5, 15, 5);
        series.add(15, 15, 5);
        series.add(15, 15, 15);
        series.add(15, 5, 15);
        series.add(15, 5, 5);
        dataset.add(series);
    
        
        return dataset;
    }
    
    private static Chart3D createChart(XYZDataset dataset) {
        Chart3D chart = Chart3DFactory.createXYZLineChart("XYZ Line Chart Demo", 
                "Orson Charts", dataset, "X", "Y", "Z");
        chart.setChartBoxColor(new Color(255, 255, 255, 128));
        XYZPlot plot = (XYZPlot) chart.getPlot();
        plot.getXAxis().setRange(0, 20);
        plot.getYAxis().setRange(0, 20);
        plot.getZAxis().setRange(0, 20);
        return chart;    
    }
    
    /**
     * Returns a panel containing the content for the demo.  This method is
     * used across all the individual demo applications to allow aggregation 
     * into a single "umbrella" demo (OrsonChartsDemo).
     * 
     * @return A panel containing the content for the demo.
     */
    public static JPanel createDemoPanel() {
        DemoPanel content = new DemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        XYZDataset dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        content.add(new DisplayPanel3D(chartPanel));
        return content;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        XYZLineChart3DDemo2 app = new XYZLineChart3DDemo2(
                "OrsonCharts: XYZLineChart3DDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}
