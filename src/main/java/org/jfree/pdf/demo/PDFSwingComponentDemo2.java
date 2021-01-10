/* =====================================================================
 * JFreePDF : a fast, light-weight PDF library for the Java(tm) platform
 * =====================================================================
 * 
 * (C)opyright 2013-2021, by Object Refinery Limited.  All rights reserved.
 *
 * Project Info:  http://www.object-refinery.com/orsonpdf/index.html
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
 * OrsonPDF library.
 * 
 */

package org.jfree.pdf.demo;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import org.jfree.pdf.PDFDocument;
import org.jfree.pdf.PDFGraphics2D;
import org.jfree.pdf.Page;

/**
 * Renders a Swing component to a PDF file, but via an image.
 */
public class PDFSwingComponentDemo2 extends JFrame {
    
    public PDFSwingComponentDemo2(String title) {
        super(title);
        JPanel content = new JPanel(new BorderLayout());
        content.add(new JButton("Hello"));
        content.add(new JLabel("This is a label"), BorderLayout.EAST);
        setContentPane(content);
    }
    
    public static void main(String[] args) {
        PDFSwingComponentDemo1 demo = new PDFSwingComponentDemo1(
                "PDFSwingComponentDemo1.java");
        demo.pack();
        demo.setVisible(true);
        PDFDocument pdf = new PDFDocument();
        JComponent c = (JComponent) demo.getContentPane();
        int w = c.getWidth();
        int h = c.getHeight();
        BufferedImage img = new BufferedImage(w, h, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D img2 = img.createGraphics();
        demo.getContentPane().paint(img2);
        Page page = pdf.createPage(new Rectangle(w, h));
        PDFGraphics2D g2 = page.getGraphics2D();
        g2.drawImage(img, 0, 0, null); 
        pdf.writeToFile(new File("PDFSwingComponentDemo2.pdf"));
    }
}
