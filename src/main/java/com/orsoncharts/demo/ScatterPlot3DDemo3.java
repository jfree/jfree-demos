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

import java.awt.Font;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart3d.Chart3D;
import org.jfree.chart3d.Chart3DFactory;
import org.jfree.chart3d.Chart3DPanel;
import org.jfree.chart3d.Colors;
import org.jfree.chart3d.Orientation;
import org.jfree.chart3d.data.DataUtils;
import org.jfree.chart3d.data.JSONUtils;
import org.jfree.chart3d.data.KeyedValues3D;
import org.jfree.chart3d.data.xyz.XYZDataset;
import org.jfree.chart3d.graphics3d.ViewPoint3D;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.legend.LegendAnchor;
import org.jfree.chart3d.plot.XYZPlot;
import org.jfree.chart3d.renderer.xyz.ScatterXYZRenderer;
import org.jfree.chart3d.style.StandardChartStyle;
import org.jfree.chart3d.table.TextElement;

/**
 * A demonstration of a scatter plot in 3D.
 */
@SuppressWarnings("serial")
public class ScatterPlot3DDemo3 extends JFrame {

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public ScatterPlot3DDemo3(String title) {
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
        DemoPanel content = new DemoPanel(new GridLayout(2, 2));
        //content.setMinimumSize(new Dimension(40, 40));
        
        XYZDataset[] datasets = createDatasets();
        Chart3D chart1 = createChart("Iris Dataset : Combination 1", 
                datasets[0], "Sepal Length", "Sepal Width", "Petal Length");
        Chart3DPanel chartPanel1 = new Chart3DPanel(chart1);
        chartPanel1.setPreferredSize(new Dimension(10, 10));
        chartPanel1.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel1.setMargin(0.35);
        
        Chart3D chart2 = createChart("Iris Dataset : Combination 2", 
                datasets[1], "Sepal Length", "Sepal Width", "Petal Width");
        Chart3DPanel chartPanel2 = new Chart3DPanel(chart2);
        chartPanel2.setPreferredSize(new Dimension(10, 10));
        chartPanel2.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel2.setMargin(0.35);
        
        Chart3D chart3 = createChart("Iris Dataset : Combination 3", 
                datasets[2], "Sepal Length", "Petal Width", "Petal Length");
        Chart3DPanel chartPanel3 = new Chart3DPanel(chart3);
        chartPanel3.setPreferredSize(new Dimension(10, 10));
        chartPanel3.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel3.setMargin(0.35);
        
        Chart3D chart4 = createChart(
                "Iris Dataset : Combination 4", datasets[3], 
                "Sepal Width", "Petal Width", "Petal Length");
        Chart3DPanel chartPanel4 = new Chart3DPanel(chart4);
        chartPanel4.setPreferredSize(new Dimension(10, 10));
        chartPanel4.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(255, 255, 255, 0)), 
                BorderFactory.createLineBorder(Color.DARK_GRAY)));
        chartPanel4.setMargin(0.35);
        
        content.add(new DisplayPanel3D(chartPanel1, false, true));
        content.addChartPanel(chartPanel1);
        content.add(new DisplayPanel3D(chartPanel2, false, true));
        content.addChartPanel(chartPanel2);
        content.add(new DisplayPanel3D(chartPanel3, false, true));
        content.addChartPanel(chartPanel3);
        content.add(new DisplayPanel3D(chartPanel4, false, true));
        content.addChartPanel(chartPanel4);
        content.setPreferredSize(new Dimension(400, 400));
        return content;
    }

    private static XYZDataset[] createDatasets() {
        XYZDataset[] datasets = new XYZDataset[4];
        datasets[0] = createDataset("sepal length", "sepal width", 
                "petal length");
        datasets[1] = createDataset("sepal length", "sepal width", 
                "petal width");
        datasets[2] = createDataset("sepal length", "petal width", 
                "petal length");
        datasets[3] = createDataset("sepal width", "petal width", 
                "petal length");
        return datasets;
    }
    
    private static Chart3D createChart(String title, XYZDataset dataset, 
            String xLabel, String yLabel, String zLabel) {
        Chart3D chart = Chart3DFactory.createScatterChart(null, null, 
                dataset, xLabel, yLabel, zLabel);
        TextElement title1 = new TextElement(title);
        title1.setFont(StandardChartStyle.createDefaultFont(Font.PLAIN, 16));
        chart.setTitle(title1);
        chart.setLegendAnchor(LegendAnchor.BOTTOM_LEFT);
        chart.setLegendOrientation(Orientation.VERTICAL);
        XYZPlot plot = (XYZPlot) chart.getPlot();
        ScatterXYZRenderer renderer = (ScatterXYZRenderer) plot.getRenderer();
        renderer.setSize(0.15);
        renderer.setColors(Colors.createIntenseColors());
        chart.setViewPoint(ViewPoint3D.createAboveLeftViewPoint(40));
        return chart;
    }
    
    /**
     * Reads a dataset from the file iris.txt.
     * 
     * @return A sample dataset.
     */
    private static XYZDataset<String> createDataset(String xKey, String yKey, 
            String zKey) {
        Reader in = new InputStreamReader(
                ScatterPlot3DDemo3.class.getResourceAsStream("iris.txt"));
        KeyedValues3D<String, String, String, Number> data;
        try {
            data = JSONUtils.readKeyedValues3D(in);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return DataUtils.extractXYZDatasetFromColumns(data, xKey, yKey, zKey);
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        ScatterPlot3DDemo3 app = new ScatterPlot3DDemo3(
                "OrsonCharts : ScatterPlot3DDemo3.java");
        app.pack();
        app.setVisible(true);
    }
}
