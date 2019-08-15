package com.max.harrax.graphics;

import static org.lwjgl.opengl.GL40.*;

public enum ShaderDataType {
	None(0, 0, 0), Float(java.lang.Float.BYTES, GL_FLOAT, 1), Float2(java.lang.Float.BYTES * 2, GL_FLOAT, 2), Float3(java.lang.Float.BYTES * 3, GL_FLOAT, 3), Float4(java.lang.Float.BYTES * 4, GL_FLOAT, 4),
	Int(java.lang.Integer.BYTES, GL_INT, 1), Int2(java.lang.Integer.BYTES * 2, GL_INT, 2), Int3(java.lang.Integer.BYTES * 3, GL_INT, 3), Int4(java.lang.Integer.BYTES * 4, GL_INT, 4),
	Bool(1, GL_BOOL, 1),
	Mat3(java.lang.Float.BYTES * 3 * 3, GL_FLOAT, 3 * 3), Mat4(java.lang.Float.BYTES * 4 * 4, GL_FLOAT, 4 * 4);

	private int bytes;
	private int openGLType;
	private int componentCount;

	ShaderDataType(int bytes, int openGLType, int componentCount) {
		this.bytes = bytes;
		this.openGLType = openGLType;
		this.componentCount = componentCount;
	}

	int bytesSize() {
		return bytes;
	}
	
	int openGLType() {
		return openGLType;
	}
	
	int componentCount() {
		return componentCount;
	}
}