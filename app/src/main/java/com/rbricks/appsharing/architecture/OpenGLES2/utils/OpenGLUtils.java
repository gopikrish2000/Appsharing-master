package com.rbricks.appsharing.architecture.OpenGLES2.utils;

import android.opengl.GLES20;

/**
 * Created by gopi on 25/10/17.
 */

public class OpenGLUtils {


    public static int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
