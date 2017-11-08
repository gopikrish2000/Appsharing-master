package com.rbricks.appsharing.architecture.OpenGLES2.shapes.triangle;

import android.opengl.GLES20;

import com.rbricks.appsharing.architecture.OpenGLES2.utils.OpenGLUtils;

/**
 * Created by gopi on 24/10/17.
 */

public class TriangleShape {

    private final String vertexShaderCode =
            "attribute vec4 vPosition;" +
                    "void main() {" +
                    "  gl_Position = vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";
    private int mProgram;
    private int mPositionHandle;
private int mColorHandle;

//private final int vertexCount = triangleCoords.length / 3;
private final int vertexStride = 3 * 4; // 4 bytes per vertex



    public void initViews() {

        int vertexShader = OpenGLUtils.loadShader(GLES20.GL_VERTEX_SHADER,
                                        vertexShaderCode);
        int fragmentShader = OpenGLUtils.loadShader(GLES20.GL_FRAGMENT_SHADER,
                                        fragmentShaderCode);

        // create empty OpenGL ES Program
        mProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(mProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(mProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(mProgram);

    }

    public void draw() {

        // Add program to OpenGL ES environment
    GLES20.glUseProgram(mProgram);

    // get handle to vertex shader's vPosition member
    mPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");

    // Enable a handle to the triangle vertices
    GLES20.glEnableVertexAttribArray(mPositionHandle);

    // Prepare the triangle coordinate data
   /* GLES20.glVertexAttribPointer(mPositionHandle, 3,
                                 GLES20.GL_FLOAT, false,
                                 vertexStride, vertexBuffer);*/

    // get handle to fragment shader's vColor member
    mColorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");

    // Set color for drawing the triangle
//    GLES20.glUniform4fv(mColorHandle, 1, color, 0);

    // Draw the triangle
//    GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertexCount);

    // Disable vertex array
    GLES20.glDisableVertexAttribArray(mPositionHandle);


    }
}
