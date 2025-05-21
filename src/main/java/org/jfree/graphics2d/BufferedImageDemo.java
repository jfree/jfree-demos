/* ======================
 * BufferedImageDemo.java
 * ======================
 *
 * Copyright 2013-present, by David Gilbert. All rights reserved.
 *
 * https://github.com/jfree/jfree-demos
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * [Oracle and Java are registered trademarks of Oracle and/or its affiliates. 
 * Other names may be trademarks of their respective owners.]
 * 
 * If you do not wish to be bound by the terms of the AGPL, an alternative
 * commercial license can be purchased.  For details, please see visit the
 * JFreeSVG home page:
 * 
 * http://www.jfree.org/jfreesvg
 * 
 */

package org.jfree.graphics2d;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * A basic test class for writing to a BufferedImage via Graphics2D.  This is
 * used as a reference implementation (we can assume that the output is correct
 * for Java2D, then compare how it looks when using JFreeSVG or OrsonPDF).
 */
public class BufferedImageDemo {
    
    /**
     * Starting point for the demo.
     * 
     * @param args  ignored.
     * 
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        BufferedImage image = new BufferedImage(600, 400, 
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = image.createGraphics();
        ImageIcon icon = new ImageIcon(BufferedImageDemo.class.getResource("jfree_chart_1.jpg"));
        g2.rotate(Math.PI / 12);
        g2.setStroke(new BasicStroke(2.0f));
        g2.setPaint(Color.WHITE);
        g2.fill(new Rectangle(0, 0, 600, 400));
        g2.setPaint(Color.RED);
        g2.draw(new Rectangle(0, 0, 600, 400));
        g2.drawImage(icon.getImage(), 10, 20, null);
        ImageIO.write(image, "png", new File("image-test.png"));
    }
}
