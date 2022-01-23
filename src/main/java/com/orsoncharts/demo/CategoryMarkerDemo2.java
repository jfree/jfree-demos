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

import java.awt.BorderLayout;
import java.awt.Color;
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
import org.jfree.chart3d.axis.StandardCategoryAxis3D;
import org.jfree.chart3d.data.DefaultKeyedValues;
import org.jfree.chart3d.data.KeyedValues3DItemKey;
import org.jfree.chart3d.data.KeyedValues3DItemKeys;
import org.jfree.chart3d.data.category.CategoryDataset3D;
import org.jfree.chart3d.data.category.StandardCategoryDataset3D;
import org.jfree.chart3d.graphics3d.Object3D;
import org.jfree.chart3d.graphics3d.RenderedElement;
import org.jfree.chart3d.graphics3d.swing.DisplayPanel3D;
import org.jfree.chart3d.interaction.Chart3DMouseEvent;
import org.jfree.chart3d.interaction.Chart3DMouseListener;
import org.jfree.chart3d.interaction.InteractiveElementType;
import org.jfree.chart3d.interaction.KeyedValues3DItemSelection;
import org.jfree.chart3d.interaction.StandardKeyedValues3DItemSelection;
import org.jfree.chart3d.label.StandardCategoryItemLabelGenerator;
import org.jfree.chart3d.legend.LegendAnchor;
import org.jfree.chart3d.marker.CategoryMarker;
import org.jfree.chart3d.plot.CategoryPlot3D;
import org.jfree.chart3d.renderer.category.BarRenderer3D;
import org.jfree.chart3d.style.ChartStyler;

/**
 * A demo showing category markers on a 3D bar chart plus many elements of
 * chart interactivity.
 */
public class CategoryMarkerDemo2 extends JFrame {

    static class CustomDemoPanel extends DemoPanel
            implements ActionListener, Chart3DMouseListener {

        private String selectedRowKey;

        private String selectedColumnKey;

        private final JCheckBox itemLabelCheckBox;

        public CustomDemoPanel(LayoutManager layout) {
            super(layout);
            JPanel controlPanel = new JPanel(new FlowLayout());
            this.itemLabelCheckBox = new JCheckBox("Show item labels?");
            itemLabelCheckBox.addActionListener(this);
            controlPanel.add(itemLabelCheckBox);
            this.selectedRowKey = "Datadog";
            this.selectedColumnKey = "Q3/21";
            add(controlPanel, BorderLayout.SOUTH);
        }

        @SuppressWarnings("unchecked")
        private void updateColorSource(String selectedRow,
                                       String selectedColumn) {
            HighlightCategoryColorSource colorSource = (HighlightCategoryColorSource)
                    getRenderer().getColorSource();
            int rowIndex = getPlot().getDataset().getRowIndex(selectedRow);
            int columnIndex = getPlot().getDataset().getColumnIndex(
                    selectedColumn);
            colorSource.setHighlightRowIndex(rowIndex);
            colorSource.setHighlightColumnIndex(columnIndex);
        }

        @SuppressWarnings("unchecked")
        private void updateItemSelection(String selectedRow,
                                         String selectedColumn) {
            StandardKeyedValues3DItemSelection itemSelection
                    = (StandardKeyedValues3DItemSelection) getItemSelection();
            itemSelection.clear();
            if (this.itemLabelCheckBox.isSelected()) {
                itemSelection.addAll(KeyedValues3DItemKeys.itemKeysForColumn(
                        getPlot().getDataset(), selectedColumn));
                itemSelection.addAll(KeyedValues3DItemKeys.itemKeysForRow(
                        getPlot().getDataset(), selectedRow));
            }
        }

        private CategoryPlot3D getPlot() {
            Chart3D chart = getChartPanel().getChart();
            return (CategoryPlot3D) chart.getPlot();
        }

        private BarRenderer3D getRenderer() {
            return (BarRenderer3D) getPlot().getRenderer();
        }

        private KeyedValues3DItemSelection getItemSelection() {
            StandardCategoryItemLabelGenerator generator
                    = (StandardCategoryItemLabelGenerator)
                    getRenderer().getItemLabelGenerator();
            return generator.getItemSelection();
        }

        private void handleSelectItem(Comparable<?> rowKey, Comparable<?> columnKey) {
            Chart3D chart = getChartPanel().getChart();
            chart.setNotify(false);
            CategoryPlot3D plot = getPlot();
            StandardCategoryAxis3D rowAxis
                    = (StandardCategoryAxis3D) plot.getRowAxis();
            CategoryMarker rowMarker = rowAxis.getMarker("RM1");
            if (rowMarker == null) {
                rowMarker = new CategoryMarker("");
                rowMarker.receive(new ChartStyler(chart.getStyle()));
            }
            StandardCategoryAxis3D columnAxis
                    = (StandardCategoryAxis3D) plot.getColumnAxis();
            CategoryMarker columnMarker = columnAxis.getMarker("CM1");
            if (columnMarker == null) {
                columnMarker = new CategoryMarker("");
                columnMarker.receive(new ChartStyler(chart.getStyle()));
            }
            this.selectedRowKey = rowKey.toString();
            this.selectedColumnKey = columnKey.toString();
            rowMarker.setCategory(this.selectedRowKey);
            columnMarker.setCategory(this.selectedColumnKey);
            updateColorSource(this.selectedRowKey, this.selectedColumnKey);
            updateItemSelection(this.selectedRowKey, this.selectedColumnKey);
            chart.setNotify(true);
        }

        private void handleSelectRow(Comparable<?> rowKey) {
            handleSelectItem(rowKey, this.selectedColumnKey);
        }

        private void handleSelectColumn(Comparable<?> columnKey) {
            handleSelectItem(this.selectedRowKey, columnKey);
        }

        @Override
        public void chartMouseClicked(Chart3DMouseEvent event) {
            RenderedElement element = event.getElement();
            if (element == null) {
                return;
            }
            // first handle clicks on data items
            KeyedValues3DItemKey key = (KeyedValues3DItemKey)
                    element.getProperty(Object3D.ITEM_KEY);
            if (key != null) {
                handleSelectItem(key.getRowKey(), key.getColumnKey());
            } else {
                if (InteractiveElementType.CATEGORY_AXIS_TICK_LABEL.equals(
                        element.getType())) {
                    String label = (String) element.getProperty("label");
                    String axisStr = (String) element.getProperty("axis");
                    if (axisStr.equals("row")) {
                        handleSelectRow(label);
                    } else { // column axis
                        handleSelectColumn(label);
                    }
                } else if (InteractiveElementType.LEGEND_ITEM.equals(
                        element.getType())) {
                    Comparable<?> seriesKey = (Comparable<?>)
                            element.getProperty(Chart3D.SERIES_KEY);
                    // the row keys are the same as the series keys in this chart
                    handleSelectRow(seriesKey);
                }
            }
        }

        @Override
        public void chartMouseMoved(Chart3DMouseEvent event) {
            // we'll do nothing here
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // there is only the item label checkbox
            updateItemSelection(this.selectedRowKey, this.selectedColumnKey);
            getChartPanel().getChart().setNotify(true);
        }
    }

    /**
     * Creates a new test app.
     *
     * @param title  the frame title.
     */
    public CategoryMarkerDemo2(String title) {
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
        CustomDemoPanel content = new CustomDemoPanel(new BorderLayout());
        content.setPreferredSize(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        CategoryDataset3D<String, String, String> dataset = createDataset();
        Chart3D chart = createChart(dataset);
        Chart3DPanel chartPanel = new Chart3DPanel(chart);
        chartPanel.setMargin(0.30);
        chartPanel.getViewPoint().panLeftRight(-0.30);
        chartPanel.getViewPoint().moveUpDown(-0.12);
        chartPanel.getViewPoint().roll(-Math.PI / 60);
        content.setChartPanel(chartPanel);
        chartPanel.zoomToFit(OrsonChartsDemo.DEFAULT_CONTENT_SIZE);
        chartPanel.addChartMouseListener(content);
        content.add(new DisplayPanel3D(chartPanel));
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

        DefaultKeyedValues<String, Double> s1 = new DefaultKeyedValues<>();
        s1.put("Q2/19", 141.0);
        s1.put("Q3/19", 146.0);
        s1.put("Q4/19", 153.0);
        s1.put("Q1/20", 160.0);
        s1.put("Q2/20", 163.0);
        s1.put("Q3/20", 166.0);
        s1.put("Q4/20", 166.0);
        s1.put("Q1/21", 173.0);
        s1.put("Q2/21", 180.0);
        s1.put("Q3/21", 196.0);
        dataset.addSeriesAsRow("New Relic", s1);

        DefaultKeyedValues<String, Double> s3 = new DefaultKeyedValues<>();
        s3.put("Q2/19", 123.0);
        s3.put("Q3/19", 129.0);
        s3.put("Q4/19", 143.0);
        s3.put("Q1/20", 151.0);
        s3.put("Q2/20", 156.0);
        s3.put("Q3/20", 169.0);
        s3.put("Q4/20", 183.0);
        s3.put("Q1/21", 197.0);
        s3.put("Q2/21", 210.0);
        s3.put("Q3/21", 226.0);
        dataset.addSeriesAsRow("Dynatrace", s3);

        DefaultKeyedValues<String, Double> s2 = new DefaultKeyedValues<>();
        s2.put("Q2/19", 83.0);
        s2.put("Q3/19", 96.0);
        s2.put("Q4/19", 114.0);
        s2.put("Q1/20", 131.0);
        s2.put("Q2/20", 140.0);
        s2.put("Q3/20", 155.0);
        s2.put("Q4/20", 178.0);
        s2.put("Q1/21", 199.0);
        s2.put("Q2/21", 234.0);
        s2.put("Q3/21", 270.0);
        dataset.addSeriesAsRow("Datadog", s2);

        DefaultKeyedValues<String, Double> s4 = new DefaultKeyedValues<>();
        s4.put("Q2/19", 517.0);
        s4.put("Q3/19", 626.0);
        s4.put("Q4/19", 791.0);
        s4.put("Q1/20", 434.0);
        s4.put("Q2/20", 492.0);
        s4.put("Q3/20", 559.0);
        s4.put("Q4/20", 745.0);
        s4.put("Q1/21", 502.0);
        s4.put("Q2/21", 606.0);
        s4.put("Q3/21", 665.0);
        dataset.addSeriesAsRow("Splunk", s4);

        return dataset;
    }

    /**
     * Creates a bar chart using the supplied dataset.
     *
     * @param dataset  the dataset.
     *
     * @return A bar chart.
     */
    private static Chart3D createChart(CategoryDataset3D<String, String, String> dataset) {
        Chart3D chart = Chart3DFactory.createBarChart("Quarterly Revenues",
                "Application & Performance Monitoring Companies", dataset, null, "Quarter",
                "$million Revenues");
        chart.setChartBoxColor(new Color(255, 255, 255, 127));
        chart.setLegendAnchor(LegendAnchor.BOTTOM_RIGHT);
        CategoryPlot3D plot = (CategoryPlot3D) chart.getPlot();
        plot.setGridlinePaintForValues(Color.BLACK);
        StandardCategoryAxis3D rowAxis
                = (StandardCategoryAxis3D) plot.getRowAxis();
        rowAxis.setMarker("RM1", new CategoryMarker("Apple"));
        StandardCategoryAxis3D columnAxis
                = (StandardCategoryAxis3D) plot.getColumnAxis();
        columnAxis.setMarker("CM1", new CategoryMarker("Q4/12"));
        BarRenderer3D renderer = (BarRenderer3D) plot.getRenderer();
        StandardCategoryItemLabelGenerator itemLabelGenerator =
                new StandardCategoryItemLabelGenerator(
                        StandardCategoryItemLabelGenerator.VALUE_TEMPLATE);
        StandardKeyedValues3DItemSelection itemSelection
                = new StandardKeyedValues3DItemSelection();
        itemLabelGenerator.setItemSelection(itemSelection);
        renderer.setItemLabelGenerator(itemLabelGenerator);
        HighlightCategoryColorSource colorSource
                = new HighlightCategoryColorSource();
        colorSource.setHighlightRowIndex(3);
        colorSource.setHighlightColumnIndex(6);
        renderer.setColorSource(colorSource);
        return chart;
    }

    /**
     * Starting point for the app.
     *
     * @param args  command line arguments (ignored).
     */
    public static void main(String[] args) {
        CategoryMarkerDemo2 app = new CategoryMarkerDemo2(
                "OrsonCharts: CategoryMarkerDemo2.java");
        app.pack();
        app.setVisible(true);
    }
}
