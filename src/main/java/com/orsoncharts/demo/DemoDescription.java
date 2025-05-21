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

/**
 * A description of a demo application.
 */
public class DemoDescription {

    private final String className;
    
    private final String fileName;
    
    private final String descriptionFileName;
    
    /**
     * Creates a new description.
     * 
     * @param demoClassName  the class name.
     * @param fileName  the file name.
     */
    public DemoDescription(String demoClassName, String fileName,
            String descriptionFileName) {
        this.className = demoClassName;
        this.fileName = fileName;
        this.descriptionFileName = descriptionFileName;
    }
    
    /**
     * Returns the class name.
     * 
     * @return The class name.
     */
    public String getClassName() {
        return this.className;
    }
    
    /**
     * Returns the file name.
     * 
     * @return The file name.
     */
    public String getFileName() {
        return this.fileName;
    }
    
    public String getDescriptionFileName() {
        return this.descriptionFileName;
    }
    
    /**
     * Returns the class description.
     * 
     * @return The class description.
     */
    @Override
    public String toString() {
        return this.fileName;
    }
    
}