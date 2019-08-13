package com.max.harrax.audio;

import static org.lwjgl.stb.STBVorbis.*;

import static org.lwjgl.system.MemoryStack.*;

import org.lwjgl.openal.*;
import org.lwjgl.stb.STBVorbis;
import org.lwjgl.system.*;

import java.io.InputStream;
import java.nio.*;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.openal.AL10.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.stb.STBVorbis.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.libc.LibCStdlib.*;

public class SoundMaster {

	private static List<Integer> buffers = new ArrayList<Integer>();

	private static long context;
	private static long device;

	public static void init() {
		System.out.println("Initialising OpenAL.");

		String defaultDeviceName = ALC10.alcGetString(0, ALC10.ALC_DEFAULT_DEVICE_SPECIFIER);
		device = ALC10.alcOpenDevice(defaultDeviceName);

		int[] attributes = { 0 };
		context = ALC10.alcCreateContext(device, attributes);
		ALC10.alcMakeContextCurrent(context);

		ALCCapabilities alcCapabilities = ALC.createCapabilities(device);
		ALCapabilities alCapabilities = AL.createCapabilities(alcCapabilities);

		System.out.println("OpenAL initialised.");
	}

	public static int loadSound(String file) {
		ShortBuffer rawAudioBuffer;

		int channels;
		int sampleRate;

		try (MemoryStack stack = stackPush()) {
			IntBuffer channelsBuffer = stackMallocInt(1);
			stackPush();
			IntBuffer sampleRateBuffer = stackMallocInt(1);
			
			rawAudioBuffer = stb_vorbis_decode_filename(file, channelsBuffer, sampleRateBuffer);

			channels = channelsBuffer.get();
			sampleRate = sampleRateBuffer.get();
		}

		int format = -1;
		if (channels == 1) {
			format = AL10.AL_FORMAT_MONO16;
		} else if (channels == 2) {
			format = AL10.AL_FORMAT_STEREO16;
		}

		int buffer = AL10.alGenBuffers();
		buffers.add(buffer);

		// Send the data to OpenAL
		AL10.alBufferData(buffer, format, rawAudioBuffer, sampleRate);

		// Free the memory allocated by STB
		free(rawAudioBuffer);

		return buffer;
	}

	public static void destroy() {
		for (int buffer : buffers) {
			AL10.alDeleteBuffers(buffer);
		}
		ALC10.alcDestroyContext(context);
		ALC10.alcCloseDevice(device);
	}

}
