package basketball;

import java.io.*;
import java.nio.ByteBuffer;
import java.net.URL;
import javax.sound.sampled.*;
import com.jogamp.openal.*;
import com.jogamp.openal.util.*;



public class Sounds {
	
	static String filename;
	
	static ALC alc;
	static AL al = ALFactory.getAL();
	
	static final int numBuffers = 5;
	static final int numSources = 5;
	
	static int[] buffer = new int[numBuffers];	
	static int[] source = new int[numSources];
	
	// Position of the source sound.
    static float[] sourcePos = { 0.0f, 0.0f, 0.0f };
    // Velocity of the source sound.
    static float[] sourceVel = { 0.0f, 0.0f, 0.1f };
    // Position of the listener.
    static float[] listenerPos = { 0.0f, 0.0f, 0.0f };
    // Velocity of the listener.
    static float[] listenerVel = { 0.0f, 0.0f, 0.0f };
    // Orientation of the listener. (first 3 elements are "at", second 3 are "up")
    static float[] listenerOri = { 0.0f, 0.0f, -1.0f,  0.0f, 1.0f, 0.0f };
	
    public Sounds() {
    	System.out.println("Made it in sounds");		
        al.alGetError();
      
	}

    public static void playOnce() {
    	if (loadALData() == AL.AL_FALSE)
            System.exit(-1);
    	al.alSourcePlay(source[0]);
    }
    
    public static void loop() {        
        // Load the wav data.
           if (loadALData() == AL.AL_FALSE)
               System.exit(-1);
           
           
           setListenerValues();

           // Setup an exit procedure.
           al.alSourcePlay(source[0]);
           float startTime = System.currentTimeMillis();
           float elapsed = 0;
           float countdown = 0;
           float newTime = 0;
           
           while (elapsed < 10000) {
               elapsed = System.currentTimeMillis() - startTime;            
               if (countdown > 100) {
                   countdown = 0;
                   sourcePos[0] += sourceVel[0];
                   sourcePos[1] += sourceVel[1];
                   sourcePos[2] += sourceVel[2];
                   al.alSourcefv(
                       source[0],
                       AL.AL_POSITION,
                       sourcePos, 0);
               }
               countdown += System.currentTimeMillis() - newTime;
               newTime = System.currentTimeMillis(); 
           }    	
    }
    
    public void backboardCol() {
    	if (loadALData() == AL.AL_FALSE)
            System.exit(-1);
    	al.alSourcePlay(source[1]);
    }
    
    public void bounce() {
    	if (loadALData() == AL.AL_FALSE)
            System.exit(-1);
    	al.alSourcePlay(source[2]);
    }
    
    public void shotMade() {
    	if (loadALData() == AL.AL_FALSE)
    		System.exit(-1);
    	al.alSourcePlay(source[3]); 
    }
    
    public void shotMissed() {
    	if (loadALData() == AL.AL_FALSE)
    		System.exit(-1);
    	al.alSourcePlay(source[4]);    	
    }    
    
    public void thud() {
    	if (loadALData() == AL.AL_FALSE)
    		System.exit(-1);
    	al.alSourcePlay(source[5]); 
    }
   
    
    
    
	public static void init(String file) {
    	
	}
    
    
    static int loadALData() {
    	
    	System.out.println("loading the file");

    	if (al.alGetError() != AL.AL_NO_ERROR)
            return AL.AL_FALSE;
   
        int[] format = new int[1];
        int[] size = new int[1];
        ByteBuffer[] data = new ByteBuffer[1];
        int[] freq = new int[1];
        int[] loop = new int[1];

        // Load wav data into a buffer.
        al.alGenBuffers(numBuffers, buffer, 0);

        ALut.alutLoadWAVFile("crowd.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[0], 
        		format[0], data[0], size[0], freq[0]);
        
        ALut.alutLoadWAVFile("backboard.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[1], 
        		format[0], data[0], size[0], freq[0]);
        
        ALut.alutLoadWAVFile("bounce.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[2], 
        		format[0], data[0], size[0], freq[0]);
        
        ALut.alutLoadWAVFile("success.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[3], 
        		format[0], data[0], size[0], freq[0]);
        
        ALut.alutLoadWAVFile("failure1.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[4], 
        		format[0], data[0], size[0], freq[0]);
        /*
        ALut.alutLoadWAVFile("bounce.wav", 
        		format, data, size, freq, loop);        
        al.alBufferData(buffer[5], 
        		format[0], data[0], size[0], freq[0]);
        		*/
        
     // Bind buffer with a source.
        al.alGenSources(numSources, source, 0);
	        al.alSourcei (source[0], AL.AL_BUFFER,   buffer[0]   );
	        al.alSourcef (source[0], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[0], AL.AL_GAIN,     0.1f     );
	        al.alSourcefv(source[0], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[0], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[0], AL.AL_LOOPING,  AL.AL_TRUE     );
	        
	        al.alSourcei (source[1], AL.AL_BUFFER,   buffer[1]   );
	        al.alSourcef (source[1], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[1], AL.AL_GAIN,     1.0f     );
	        al.alSourcefv(source[1], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[1], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[1], AL.AL_LOOPING,  AL.AL_FALSE     );
	        
	        al.alSourcei (source[2], AL.AL_BUFFER,   buffer[2]   );
	        al.alSourcef (source[2], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[2], AL.AL_GAIN,     1.0f     );
	        al.alSourcefv(source[2], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[2], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[2], AL.AL_LOOPING,  AL.AL_FALSE     );
	      
	        al.alSourcei (source[3], AL.AL_BUFFER,   buffer[3]   );
	        al.alSourcef (source[3], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[3], AL.AL_GAIN,     1.0f     );
	        al.alSourcefv(source[3], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[3], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[3], AL.AL_LOOPING,  AL.AL_FALSE     );
	        
	        al.alSourcei (source[4], AL.AL_BUFFER,   buffer[4]   );
	        al.alSourcef (source[4], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[4], AL.AL_GAIN,     1.0f     );
	        al.alSourcefv(source[4], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[4], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[4], AL.AL_LOOPING,  AL.AL_FALSE     );
	        /*
	        al.alSourcei (source[5], AL.AL_BUFFER,   buffer[5]   );
	        al.alSourcef (source[5], AL.AL_PITCH,    1.0f     );
	        al.alSourcef (source[5], AL.AL_GAIN,     1.0f     );
	        al.alSourcefv(source[5], AL.AL_POSITION, sourcePos, 0);
	        al.alSourcefv(source[5], AL.AL_VELOCITY, sourceVel, 0);
	        al.alSourcei (source[5], AL.AL_LOOPING,  AL.AL_FALSE     );
        */
     // Do another error check and return.
        if(al.alGetError() == AL.AL_NO_ERROR)
            return AL.AL_TRUE;

        return AL.AL_FALSE;
    }
    
    static void setListenerValues() {
        al.alListenerfv(AL.AL_POSITION,	listenerPos, 0);
        al.alListenerfv(AL.AL_VELOCITY,    listenerVel, 0);
        al.alListenerfv(AL.AL_ORIENTATION, listenerOri, 0);
    }
    
    static void killALData() {
        al.alDeleteBuffers(1, buffer, 0);
        al.alDeleteSources(1, source, 0);
        ALut.alutExit();
    }

}
