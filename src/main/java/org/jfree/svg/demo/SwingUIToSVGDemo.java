/* =====================
 * SwingUIToSVGDemo.java
 * =====================
 *
 * Copyright 2013-2022, by David Gilbert. All rights reserved.
 *
 * https://github.com/jfree/jfreesvg
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
 */

package org.jfree.svg.demo;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.jfree.svg.SVGGraphics2D;
import org.jfree.svg.SVGUtils;

/**
 * This demo shows how to export a Swing UI to SVG.
 */
public class SwingUIToSVGDemo extends JFrame implements ActionListener {
    
    public SwingUIToSVGDemo(String title) {
        super(title);
        add(createContent());
    }
    
    private JComponent createContent() {
        JPanel content = new JPanel(new BorderLayout());
        JTabbedPane tabs = new JTabbedPane();
        tabs.add("Tab 1", new JButton("First Tab"));
        tabs.add("Tab 2", new JButton("Second Tab"));
        JButton button = new JButton("Save to SVG");
        button.addActionListener(this);
        content.add(tabs);
        content.add(button, BorderLayout.SOUTH);
        return content;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        JComponent c = (JComponent) getContentPane().getComponent(0);
        SVGGraphics2D g2 = new SVGGraphics2D(c.getWidth(), c.getHeight());
        c.paint(g2);
        File f = new File("SwingUIToSVGDemo.svg");
        try {
            SVGUtils.writeToSVG(f, g2.getSVGElement());
        } catch (IOException ex) {
            System.err.println(ex);
        }
    }

    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // just take the default look and feel
        }

        SwingUIToSVGDemo app = new SwingUIToSVGDemo("SwingUIToSVGDemo.java");
        app.pack();
        app.setVisible(true);
        
    }

}
