package com.max.harrax.audio;

import static org.lwjgl.stb.STBVorbis.stb_vorbis_decode_filename;
import static org.lwjgl.system.MemoryStack.stackMallocInt;
import static org.lwjgl.system.MemoryStack.stackPush;

import java.nio.IntBuffer;

import org.lwjgl.openal.AL10;
import org.lwjgl.system.MemoryStack;

public class Source {

	private int sourceId;

	public Source() {
		sourceId = AL10.alGenSources();
		AL10.alSourcef(sourceId, AL10.AL_GAIN, 0.5f);
	}

	public void setGain(float gain) {
		AL10.alSourcef(sourceId, AL10.AL_GAIN, gain);
	}

	public void play(int buffer) {
		AL10.alSourcei(sourceId, AL10.AL_BUFFER, buffer);
		AL10.alSourcePlay(sourceId);
	}

	public void stop() {
		AL10.alSourceStop(sourceId);
	}

	public boolean isPlaying() {
		int state;
		try (MemoryStack stack = stackPush()) {
			IntBuffer b = stackMallocInt(1);
			AL10.alGetSourcei(sourceId, AL10.AL_SOURCE_STATE, b);
			state = b.get();
		}
		return (state == AL10.AL_PLAYING);
	}

	public void delete() {
		AL10.alDeleteSources(sourceId);
	}

}
