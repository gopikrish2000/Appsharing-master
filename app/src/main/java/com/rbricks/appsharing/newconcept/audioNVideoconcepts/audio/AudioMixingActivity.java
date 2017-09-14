package com.rbricks.appsharing.newconcept.audioNVideoconcepts.audio;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rbricks.appsharing.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.Bitstream;
import javazoom.jl.decoder.BitstreamException;
import javazoom.jl.decoder.Decoder;
import javazoom.jl.decoder.DecoderException;
import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.SampleBuffer;

public class AudioMixingActivity extends AppCompatActivity {

    private static String TAG = AudioMixingActivity.class.getSimpleName();
    AudioTrack audioTrack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_mixing);
        mixSoundFinal();
    }

    private void mixSound() {
        try {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, 44100, AudioTrack.MODE_STREAM);

            /*InputStream in1 = getResources().openRawResource(R.raw.unnatu);
            InputStream in2 = getResources().openRawResource(R.raw.adiga);

            byte[] music1 = null;
            music1 = new byte[in1.available()];
            music1 = convertStreamToByteArray(in1);
            in1.close();


            byte[] music2 = null;
            music2 = new byte[in2.available()];
            music2 = convertStreamToByteArray(in2);
            in2.close();*/

//            String filePath1 = "android.resource://" + getPackageName() + "/" + R.raw.unnatu;
//            String filePath2 = "android.resource://" + getPackageName() + "/" + R.raw.adiga;

            byte[] music1 = decode("unnatu", 0, 20000);

            byte[] music2 = decode("adiga", 0, 20000);


            int min = Math.min(music1.length, music2.length);

            byte[] output = new byte[min];

            audioTrack.play();


            for (int i = 0; i < output.length; i++) {

                float samplef1 = music1[i] / 128.0f;      //     2^7=128
                float samplef2 = music2[i] / 128.0f;


                float mixed = samplef1 + samplef2;
                // reduce the volume a bit:
                mixed *= 0.8;
                // hard clipping
                if (mixed > 1.0f) mixed = 1.0f;

                if (mixed < -1.0f) mixed = -1.0f;

                byte outputSample = (byte) (mixed * 128.0f);
                output[i] = outputSample;

            }   //for loop
            audioTrack.write(output, 0, output.length);

            audioTrack.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mixSound1() {
        try {

            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, 44100, AudioTrack.MODE_STREAM);
            byte[] music1 = decode("adiga", 0, 20000);
//            byte[] output = new byte[music1.length];

            audioTrack.play();
            audioTrack.write(music1, 0, music1.length);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void mixSoundFinal() {
        try {
            audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT, 44100, AudioTrack.MODE_STREAM);

            byte[] music1 = decode("unnatu", 0, 20000);

            byte[] music2 = decode("adiga", 0, 20000);


            int min = Math.min(music1.length, music2.length);

            byte[] output = new byte[min];

            audioTrack.play();


            for (int i = 0; i < output.length; i++) {

                byte b1 = music1[i];
                byte b2 = music2[i];
                byte outputSample = (byte) ((b1 + b2)/2);   //Average it.
                output[i] = outputSample;

            }   //for loop
            audioTrack.write(output, 0, output.length);

            audioTrack.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] convertStreamToByteArray(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buff = new byte[10240];
        int i = Integer.MAX_VALUE;
        while ((i = is.read(buff, 0, buff.length)) > 0) {
            baos.write(buff, 0, i);
        }

        return baos.toByteArray(); // be sure to close InputStream in calling function
    }

    public  byte[] decode(String path, int startMs, int maxMs)
            throws IOException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(1024);
//        Uri uri=Uri.parse(path);

        float totalMs = 0;
        boolean seeking = true;

//        File file = new File(uri);
        InputStream inputStream = getResources().openRawResource(
            getResources().getIdentifier(path,
            "raw", getPackageName()));
//        InputStream inputStream = new BufferedInputStream(new FileInputStream(file), 8 * 1024);
        try {
            Bitstream bitstream = new Bitstream(inputStream);
            Decoder decoder = new Decoder();

            boolean done = false;
            while (!done) {
                Header frameHeader = bitstream.readFrame();
                if (frameHeader == null) {
                    done = true;
                } else {
                    totalMs += frameHeader.ms_per_frame();

                    if (totalMs >= startMs) {
                        seeking = false;
                    }

                    if (!seeking) {
                        SampleBuffer output = (SampleBuffer) decoder.decodeFrame(frameHeader, bitstream);

                        if (output.getSampleFrequency() != 44100
                                || output.getChannelCount() != 2) {
                            throw new RuntimeException("mono or non-44100 MP3 not supported");
                        }

                        short[] pcm = output.getBuffer();
                        for (short s : pcm) {
                            outStream.write(s & 0xff);
                            outStream.write((s >> 8) & 0xff);
                        }
                    }

                    if (totalMs >= (startMs + maxMs)) {
                        done = true;
                    }
                }
                bitstream.closeFrame();
            }

            return outStream.toByteArray();
        } catch (BitstreamException e) {
            throw new IOException("Bitstream error: " + e);
        } catch (DecoderException e) {
            Log.w(TAG, "Decoder error", e);
            throw new RuntimeException(e);
        } finally {
//			IOUtils.safeClose(inputStream);
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        audioTrack.release();
    }
}
