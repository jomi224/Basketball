package basketball;

import basketball.Basketball;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;

import java.io.File;
import com.jogamp.openal.*;
import com.jogamp.openal.util.*;
import java.nio.ByteBuffer;
import basketball.Sounds;

import com.jogamp.opengl.*;
import com.jogamp.opengl.util.texture.*;
import com.jogamp.*;




public class JoglEventListener implements GLEventListener, KeyListener, MouseListener, MouseMotionListener {
	
	
	float bottom=0f;
	float boxLength=10000f;
	float section= (1-bottom)/3;
	float backrgb[] = new float[4]; 
	Texture texture=null,texture2=null,texture3=null,jordanBody=null,roof=null,door=null,map=null,backboard=null,scorel=null,replay=null,slow1=null,slow2=null,slow3=null,stadium=null,jordan=null;
	Texture jordanBack=null,red=null,jordanHead=null,jordanSide=null,profile=null,basketball=null,brick=null,fence=null;
	int texID,tex2ID,tex3,texHouse,texRoof,texDoor,texMap,texBack,texScore,texReplay,texS1,texS2,texS3,texStadium,texJordan,texBody,texProf,texBall;
	int texJBack, texRed,texHead, texSide, texBrick, texFence;
	int windowWidth, windowHeight;
	float orthoX=40;
	boolean off=false;
	float playerX=0f,playerY=0f,playerZ=25f;

	
	int mouseX0, mouseY0;	
	float picked_x = 0.0f, picked_y = 0.0f;
	
	float focalLength = 30.0f;
	
	boolean holdingDown=false;
	
	//angle of rotation
	float rotateY = 0.0f; // 
	float rotateX= 0.0f;
	float repY=0.0f;
	float repX=180f;
	
	float theta=-90f;
	
	float shift=0f;
	
	float pointClick[]={0f,0f};
	
	boolean diffuse_flag  = false;
	boolean specular_flag = false;
	
	boolean smooth_flag = true;

    private GLU glu = new GLU();
	
    private GLUT glut = new GLUT();
    
    private Basketball myBall;
	
	public static Sounds sounds;
    
	float floorLength=25f;
	float floorwidth=94f/2f;
	float playerHeight=6f;
	
	float backWidth=72f/24f;
	float backHeight=42f/12f;
	float hoopHeight=9.6f;
	float rimHeight=10f;
	float rimRadius=0.75f;
	
	float intensity=0.0f;
	float ballRot=0f;
	private boolean replayMode=false;
	private boolean overBasket=false;

	private boolean tooIntense=false;
	private float lookAt=25f;
	
	private float oldx,oldy,oldz,oldintense;
	private float buttonCoords[]= new float[4*4];
	private int buttons=4;
	private float slow;
	private boolean firstShot=false;
	private boolean printFlag=false;
	
	private float timeScale=1f;
	
	    public void displayChanged(GLAutoDrawable gLDrawable, boolean modeChanged, boolean deviceChanged) {
		 
	    }

	    /** Called by the drawable immediately after the OpenGL context is
	     * initialized for the first time. Can be used to perform one-time OpenGL
	     * initialization such as setup of lights and display lists.
	     * @param gLDrawable The GLAutoDrawable object.
	     */
	    public void init(GLAutoDrawable gLDrawable) {
	        GL2 gl = gLDrawable.getGL().getGL2();
	        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    // Black Background
	        gl.glClearDepth(1.0f);                      // Depth Buffer Setup
	        gl.glEnable(GL.GL_DEPTH_TEST);              // Enables Depth Testing
	        //gl.glDepthFunc(GL.GL_LEQUAL);    // The Type Of Depth Testing To Do
			
			ALut.alutInit();
			sounds = new Sounds();

	        
	        myBall=new Basketball(playerX,playerY,playerZ+15f,0.4f);
	        myBall.SetBackBoard(-backWidth, -playerHeight+hoopHeight, floorwidth, backHeight, 2*backWidth);
	        myBall.SetCourt(-playerHeight);
	        myBall.SetRim(0f, -playerHeight+rimHeight, floorwidth-rimRadius, rimRadius);
	        
	        try {
	        	
	        	 texture = TextureIO.newTexture(new File("skybox4.png"), false);
	        	 texture3= TextureIO.newTexture(new File("court2.png"), false);
	        	 backboard= TextureIO.newTexture(new File("backboard.jpg"), false);
	        	 map= TextureIO.newTexture(new File("graph.jpg"), false);
	        	 scorel= TextureIO.newTexture(new File("score.png"), false);
	        	 replay= TextureIO.newTexture(new File("replay.png"), false);
	        	 slow1= TextureIO.newTexture(new File("1x.png"), false);
	        	 slow2= TextureIO.newTexture(new File("4x.png"), false);
	        	 slow3= TextureIO.newTexture(new File("10x.png"), false);	
	        	 jordan=TextureIO.newTexture(new File("jordan2.jpg"), false);
	        	 jordanBody=TextureIO.newTexture(new File("jordanbody.jpg"), false);
	        	 jordanBack=TextureIO.newTexture(new File("jordanback.jpg"), false);
	        	 red=TextureIO.newTexture(new File("red.jpg"), false);
	        	 jordanHead=TextureIO.newTexture(new File("jordanbackhead.jpg"), false);
	        	 jordanSide=TextureIO.newTexture(new File("black.jpg"), false);
	        	 profile=TextureIO.newTexture(new File("jordanside.jpg"), false);
	        	 basketball=TextureIO.newTexture(new File("basketball.png"), false);
	        	 brick=TextureIO.newTexture(new File("brick.jpg"),false);
	        	 fence=TextureIO.newTexture(new File("fence.png"),false);
	        	 
	        	 texID = texture.getTextureObject();
	        	 tex3=texture3.getTextureObject();
	        	 texBack=backboard.getTextureObject();
	        	 texMap=map.getTextureObject();
	        	 texScore=scorel.getTextureObject();
	        	 texReplay=replay.getTextureObject();
	        	 texS1=slow1.getTextureObject();
	        	 texS2=slow2.getTextureObject();
	        	 texS3=slow3.getTextureObject();
	        	 texJordan=jordan.getTextureObject();
	        	 texBody=jordanBody.getTextureObject();
	        	 texJBack=jordanBack.getTextureObject();
	        	 texRed=red.getTextureObject();
	        	 texHead=jordanHead.getTextureObject();
	        	 texSide=jordanSide.getTextureObject();
	        	 texProf=profile.getTextureObject();
	        	 texBall=basketball.getTextureObject();
	        	 texBrick=brick.getTextureObject();
	        	 texFence=fence.getTextureObject();


	        	 
	             gl.glEnable(GL.GL_TEXTURE_2D);
		             texture.bind(gl);
		             texture3.bind(gl);
		             backboard.bind(gl);
		             map.bind(gl);
		             scorel.bind(gl);
		             replay.bind(gl);
		             slow1.bind(gl);
		             slow2.bind(gl);
		             slow3.bind(gl);
		             jordan.bind(gl);
		             jordanBody.bind(gl);
		             jordanBack.bind(gl);
		             red.bind(gl);
		             jordanHead.bind(gl);
		             jordanSide.bind(gl);
		             profile.bind(gl);
		             basketball.bind(gl);
		             brick.bind(gl);
		             fence.bind(gl);



	             
	             
	        } catch (Exception ex) {
	        	ex.printStackTrace();
	        }
			
			sounds.loop();

	        gl.glMatrixMode(GL2.GL_MODELVIEW);
	        gl.glLoadIdentity();
	        
	        
	        
	    }


	    
	    public void reshape(GLAutoDrawable gLDrawable, int x, int y, int width, int height) {
	    	windowWidth = width;
	    	windowHeight = height;
	        final GL2 gl = gLDrawable.getGL().getGL2();

	        if (height <= 0) // avoid a divide by zero error!
	            height = 1;
	        final float h = (float) width / (float) height;
	        gl.glViewport(0, 0, width, height);
	        gl.glMatrixMode(GL2.GL_PROJECTION);
	        gl.glLoadIdentity();
	       // gl.glOrtho(-orthoX*0.5, orthoX*0.5, -orthoX*0.5*height/width, orthoX*0.5*height/width, -100, 100);
	        glu.gluPerspective(60.0f, h, 1, 100000.0);

	    }
	    
	    public void drawBall(final GL2 gl, Basketball ball, int circles){
	    	
	    	if (replayMode)
	    	ballRot-=0.04f*slow;
	    	else ballRot-=0.04f;
	    	
	    	float x=ball.getX();
	    	float y=ball.getY();
	    	float z=ball.getZ();
	    	float r=ball.getR();
	    	float diff= r/circles*2;
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
	    	float cosTheta,sinTheta;
	    	cosTheta=(float)Math.cos(ballRot);
	    	sinTheta=(float)Math.sin(ballRot);
	    	
	    	gl.glBindTexture(GL.GL_TEXTURE_2D, texBall);
	    	gl.glBegin(GL.GL_TRIANGLE_STRIP);

	    	
	    	for (int i=-(circles)/2; i<=(circles)/2;i++){
		    	float h1= (float) Math.sqrt(Math.pow(r, 2)-Math.pow(diff*(i),2));
		    	float h2= (float) Math.sqrt(Math.pow(r, 2)-Math.pow(diff*(i+1),2));
	    		for (int j=0;j<=circles;j++){
		    		float newX= (float) Math.sin(2*Math.PI*j/(circles));
		    		float newZ= (float) Math.cos(2*Math.PI*j/(circles));
		    		float x1= newX*h1;
		    		float z1=newZ*h1;
		    		float x2= newX*h2;
		    		float z2=newZ*h2;
		    		
		    		float theta, alpha1,alpha2;
		    		
		    		theta=(float) 2*((float)Math.PI)*j/(circles);
		    		
		    		alpha1=(float) Math.asin((i*diff)/r)+(float)Math.PI/2;
		    		alpha2=(float) Math.asin((i+1)*diff/r)+(float)Math.PI/2;
		    		
		    		float xThet=theta/(2*(float)Math.PI);
		    		
		    		float yAlph1= alpha1/((float)Math.PI);
		    		float yAlph2= alpha2/((float)Math.PI);

		    		
		    		gl.glTexCoord2f(xThet,  (shift+yAlph1)%1f);
	    			gl.glVertex3f(x+i*diff, y+z1*cosTheta-x1*sinTheta, z+x1*cosTheta+z1*sinTheta);
         			//System.out.println(alpha1);
	    			
		    		gl.glTexCoord2f(xThet, (shift+yAlph2)%1f);
	    			gl.glVertex3f(x+(i+1)*diff, y+z2*cosTheta-x2*sinTheta, z+x2*cosTheta+z2*sinTheta);


	    			

	    		}
	    	}
    		gl.glEnd();


	    }

	    public float[] ballPosition(int time){
	    	
	    	float position[]=new float[3];
	    	return position;
	    }
	    
		@Override
		public void display(GLAutoDrawable gLDrawable) {
			// TODO Auto-generated method stub
			final GL2 gl = gLDrawable.getGL().getGL2();
			
			if (holdingDown){
				
				intensity+=0.01f;
				if (intensity>=1f){
					holdingDown=false;
					intensity=0f;
					off=true;
					//tooIntense=true;
				}
			}

			gl.glClearColor(1f, 0, 0, 1);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
	    	gl.glMatrixMode(GL2.GL_MODELVIEW);
	    	gl.glLoadIdentity();

			if (!replayMode){
			glu.gluLookAt( playerX, playerY, playerZ,playerX+Math.sin(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f),playerY-Math.sin(rotateX*Math.PI/180f), playerZ+Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f),0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 
			} else { 
		        glu.gluLookAt(lookAt*Math.sin(repY*Math.PI/180f)*Math.cos(repX*Math.PI/180f)+playerX,-lookAt*Math.sin(repX*Math.PI/180f)+playerY, lookAt*Math.cos(repY*Math.PI/180f)*Math.cos(repX*Math.PI/180f)+playerZ+10f, playerX, playerY, playerZ+10f, 0, 1, 0);
		        if (!myBall.getShooting()){
		        	replay(slow);
		        }
			}
			
			//glu.gluLookAt( playerX, playerY, playerZ,(float)(Math.sin(rotateY*Math.PI/180f)),-(float)(Math.sin(rotateX*Math.PI/180f)*Math.cos(rotateY*Math.PI/180f)),(float)(Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f)) ,0.0, 1.0, 0.0); // eye point, x, y, z, looking at x, y, z, Up direction 

	    	//glu.gluPerspective(45.0f, 1, 0.5, 15);

			if (replayMode){myBall.updatePosition(slow*timeScale);}
			else{
				myBall.updatePosition(timeScale);
			}
	    	drawBall(gl,myBall,16);
			gl.glEnable(GL2.GL_TEXTURE_2D);

	    	gl.glBindTexture(GL.GL_TEXTURE_2D, texID);
	    	
	    	
	    	//draw skybox
	    	
	    	drawSkybox(gl);
	    	
	    	//gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);

	    	
	    	
	    	
	    	//backboard
	    
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texBack);
	    	
	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f, 1f, 1f,1f);
		    		gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth);
		    		gl.glTexCoord2f(0f, 0f);	    	
		    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth);
		    		gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight+backHeight, floorwidth);
		    		gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight+backHeight, floorwidth);
	    	gl.glEnd();
	    	
		    	gl.glBegin(GL2.GL_QUADS);	    	
			    gl.glColor4f(0f, 0f, 0f,0f);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth);    	
			    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth);
			    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight, floorwidth+.3f);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth+.3f);
		    	gl.glEnd();			   
		    	
		    	gl.glBegin(GL2.GL_QUADS);	    	
			    gl.glColor4f(0f, 0f, 0f,0f);
			    	gl.glVertex3f(backWidth,-playerHeight+hoopHeight, floorwidth+.3f);    	
			    	gl.glVertex3f(backWidth,-playerHeight+hoopHeight, floorwidth);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight+backHeight, floorwidth);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight+backHeight, floorwidth+.3f);
		    	gl.glEnd();		
		    	
		    	gl.glBegin(GL2.GL_QUADS);	    	
			    gl.glColor4f(0f, 0f, 0f,0f);
			    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth+.3f);    	
			    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth);
			    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight+backHeight, floorwidth);
			    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight+backHeight, floorwidth+.3f);
		    	gl.glEnd();		
		    	
		    	gl.glBegin(GL2.GL_QUADS);	    	
			    gl.glColor4f(0f, 0f, 0f,0f);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth);    	
			    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth);
			    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight, floorwidth+.3f);
			    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth+.3f);
		    	gl.glEnd();		
		    	
	    	gl.glBegin(GL2.GL_QUADS);	    	
		    gl.glColor4f(1f, 1f, 1f,1f);
		    		gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, floorwidth+.3f);
		    		gl.glTexCoord2f(0f, 0f);	    	
		    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, floorwidth+.3f);
		    		gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight+backHeight, floorwidth+.3f);
		    		gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight+backHeight, floorwidth+.3f);
	    	gl.glEnd();
	    	
	    	
	    	drawRim(0f,-playerHeight+rimHeight, floorwidth-0.75f, 0.75f, gl);
	    	
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glColor3f(0.2f, 0.2f, 0.2f);
	    	gl.glVertex3f(backWidth/10f, -playerHeight, floorwidth);
	    	
	    	gl.glVertex3f(-backWidth/10f,-playerHeight, floorwidth);

	    	gl.glVertex3f(-backWidth/10f, -playerHeight+hoopHeight+backHeight, floorwidth);

	    	gl.glVertex3f(backWidth/10f, -playerHeight+hoopHeight+backHeight, floorwidth);
	    	
	    	gl.glEnd();
	    	
	    	
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texBack);
	    	
	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f, 1f, 1f,1f);
	    	gl.glTexCoord2f(0f, 0f);

	    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight, -floorwidth);
	    	gl.glTexCoord2f(1f, 0f);
	    	
	    	gl.glVertex3f(-backWidth,-playerHeight+hoopHeight, -floorwidth);
	    	gl.glTexCoord2f(1f, 1f);

	    	gl.glVertex3f(-backWidth, -playerHeight+hoopHeight+backHeight, -floorwidth);
	    	gl.glTexCoord2f(0f, 1f);

	    	gl.glVertex3f(backWidth, -playerHeight+hoopHeight+backHeight, -floorwidth);

	    	gl.glEnd();
	    	
	    	
	    	drawRim(0f,-playerHeight+rimHeight, -floorwidth+0.75f, 0.75f, gl);
	    	
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	
	    	gl.glColor3f(0.2f, 0.2f, 0.2f);
	    	gl.glVertex3f(backWidth/10f, -playerHeight, -floorwidth);
	    	
	    	gl.glVertex3f(-backWidth/10f,-playerHeight, -floorwidth);

	    	gl.glVertex3f(-backWidth/10f, -playerHeight+hoopHeight+backHeight, -floorwidth);

	    	gl.glVertex3f(backWidth/10f, -playerHeight+hoopHeight+backHeight, -floorwidth);
	    	
	    	gl.glEnd();
	    	//Court
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, tex3);

	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f, 1f, 1f,1f);
		    	gl.glTexCoord2f(1f, 0f);
	
		    	gl.glVertex3f(-floorLength, -playerHeight, -floorwidth);
		    	gl.glTexCoord2f(0f, 0f);
		    	
		    	gl.glVertex3f(-floorLength, -playerHeight, floorwidth);
		    	gl.glTexCoord2f(0f, 1f);
	
		    	gl.glVertex3f(floorLength, -playerHeight, floorwidth);
		    	gl.glTexCoord2f(1f, 1f);
	
		    	gl.glVertex3f(floorLength, -playerHeight, -floorwidth);
	    	
	    	gl.glEnd();
	    	//gl.glFlush();
	    	
	    	gl.glBegin(GL2.GL_QUADS);
	    	gl.glColor4f(.05f, .6f, .2f, 1f);
	    		gl.glVertex3f(-1000, -10, -1000);
	    		gl.glVertex3f( 1000, -10, -1000);
	    		gl.glVertex3f( 1000, -10, 1000);
	    		gl.glVertex3f(-1000, -10, 1000);
	    	gl.glEnd();	    	
	    	
	    	drawDisplay(gl);
	    	drawAvatar(gl);
	    	
	    	drawFence(gl);
		}

		public void drawRim(float x, float y, float z, float r,final GL2 gl){
			
			gl.glLineWidth(4f);
			gl.glBegin(GL2.GL_LINE_LOOP);
			gl.glColor3f(1f, 0f, 0f);
			int circles=50;
			
			for (int i=0;i<circles;i++){
				
	    		float newX= r*(float) Math.sin(2*Math.PI*i/(circles));
	    		float newZ= r*(float) Math.cos(2*Math.PI*i/(circles));
				gl.glLineWidth(20f);

				gl.glVertex3f(newX+x, y, newZ+z);
				
			}
			gl.glEnd();
			
			
			float netHeight=2f;
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);
			
			gl.glLineWidth(2f);
			gl.glColor3f(0.9f, 0.9f, 0.9f);
		
			
			float shrink=0.03f;
			int rings=8;
			for (int j=0;j<rings;j+=1){
				gl.glBegin(GL2.GL_TRIANGLE_STRIP);

				int cir=15;
				for (int i=0;i<cir;i++){
					
		    		float newX= (r-j*shrink)*(float) Math.sin(2*Math.PI*i/(cir));
		    		float newZ= (r-(j+1)*shrink)*(float) Math.cos(2*Math.PI*(i)/(cir));
		    		
		    		float newX2= (r-j*shrink)*(float) Math.sin(2*Math.PI*(i+0.5f)/(cir));
		    		float newZ2= (r-(j+1)*shrink)*(float) Math.cos(2*Math.PI*(i+0.5f)/(cir));
		    		
		    		gl.glVertex3f(newX+x, y-netHeight*(j)/rings   , newZ+z);
		    		
		    		gl.glVertex3f(newX2+x, y-netHeight*(j+1)/rings   , newZ2+z);



				}
				gl.glEnd();

			}
			gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);


		}
		
		public void drawDisplay(final GL2 gl){
			
			gl.glDisable(GL2.GL_TEXTURE_2D);
	    	//gl.glBindTexture(GL2.GL_TEXTURE_2D, texMap);
			
			gl.glBegin(GL2.GL_QUADS);
			
			gl.glColor3f(0.1f, 0.1f,0.7f);

			
			float startX=-0.7f;
			float startY=-0.3f;
			float endX=-0.8f;
			float endY=-0.7f;
			
			float diff=(startY-endY);
			
			float barEndY=endY;
			float barStartY=barEndY+intensity*diff;


			if (!replayMode){
			drawElement(gl, texScore, startX,endX,barStartY,barEndY,false,0f,0f,1f,false,0);


			drawElement(gl, texScore, startX,endX,startY,endY,false,0.1f,0.1f,0.1f,false,0);
			}else{
				drawElement(gl, texScore, startX,endX,barStartY-1000f,barEndY-1000f,false,0f,0f,1f,false,0);


				drawElement(gl, texScore, startX,endX,startY-1000f,endY-1000f,false,0.1f,0.1f,0.1f,false,0);
			}
			if (myBall.getScore() && !replayMode){
				
				startX=0.8f;
				startY=1.15f-0.3f;
				endX=0.4f;
				endY=1.15f-0.4f;

				drawElement(gl, texScore, startX,endX,startY,endY,true,1f,1f,1f,false,0);
			}
			
			if (!myBall.getShooting() && firstShot && !replayMode && !myBall.getOver()){
								
				startX=0.4f;
				startY=1.15f-0.3f;
				endX=0f;
				endY=1.15f-0.4f;
				
				drawElement(gl, texReplay, startX,endX,startY,endY,true,0.6f,0.6f,0.6f,false,0);
				startX=0.4f;
				startY=1.05f-0.3f;
				endX=0.266f;
				endY=1.05f-0.4f;
				
				drawElement(gl, texS1, startX,endX,startY,endY,true,0.1f,0.1f,0.8f,true,0);
				makeButton(170,40,211,70,0);
				startX=0.2666f;
				startY=1.05f-0.3f;
				endX=0.1333f;
				endY=1.05f-0.4f;
				
				drawElement(gl, texS2, startX,endX,startY,endY,true,0.8f,.1f,.1f,true,1);
				makeButton(215,40,250,70,1);
				startX=0.1333f;
				startY=1.05f-0.3f;
				endX=0f;
				endY=1.05f-0.4f;
				
				drawElement(gl, texS3, startX,endX,startY,endY,true,1f,1f,1f,true,2);
				makeButton(260, 40, 300, 70,2);

			}

		}
		
		public void makeButton(float x1,float y1, float x2, float y2,int b){
			//System.out.println("Made button: "+x1+" "+y1+" "+x2+" "+y2);
			buttonCoords[4*b]=x1;
			buttonCoords[4*b+1]=y1;
			buttonCoords[4*b+2]=x2;
			buttonCoords[4*b+3]=y2;			
		}
		public void getButtonCommand(int x, int y){
			
			for (int i=0;i<buttons*4;i+=4){
				float x1=buttonCoords[i];
				float y1=buttonCoords[i+1];
				float x2=buttonCoords[i+2];
				float y2= buttonCoords[i+3];
				
				if (printFlag) { 
					//System.out.println("Get Button: "+x1+" "+y1+" "+x2+" "+y2);
					printFlag = !printFlag;
				}
				if (x>=x1 && x<=x2 && y>=y1 && y<=y2){
					switch ((int) i/4){
						case 0:
							replay(1f);
							break;
						case 1:
							replay(1/4f);
							break;
						case 2:
							replay(1/10f);
							break;
						default:
							break;
							
					}
				}
				
			}
		}
		public void drawElement(final GL2 gl, int texQ, float startX,float endX, float startY,float endY,boolean enableTex,float red, float green, float blue, boolean buttonFlag, int buttonNum){
			
			float xZ=1.5f*(float)(Math.sin(rotateY*Math.PI/180f))*(float)Math.cos(rotateX*Math.PI/180f);
			float yZ=-1.5f*(float)(Math.sin(rotateX*Math.PI/180f));
			float zZ=1.5f*(float)(Math.cos(rotateY*Math.PI/180f)*Math.cos(rotateX*Math.PI/180f));
			
			float xY=(float)(Math.sin(rotateY*Math.PI/180f))*(float)Math.sin(rotateX*Math.PI/180f);
			float yY=(float)Math.cos(rotateX*Math.PI/180f);
			float zY=(float)Math.sin(rotateX*Math.PI/180f)*(float)Math.cos(rotateY*Math.PI/180f);;
			
			
			float xX=(float)Math.cos(rotateY*Math.PI/180f);
			float yX=0f;
			float zX=-(float)Math.sin(rotateY*Math.PI/180f);
			
			if (buttonFlag) {
				//makeButton((playerX+xZ+startY*xY+startX*xX), (playerY+yZ+startY*yY+startX*yX), (playerX+xZ+endY*xY+startX*xX), (playerY+yZ+endY*yY+startX*yX), buttonNum);
			
				/*
				System.out.println((playerX+xZ+startY*xY+startX*xX));
				System.out.println((playerY+yZ+startY*yY+startX*yX));
				System.out.println((playerX+xZ+endY*xY+startX*xX));
				System.out.println((playerY+yZ+endY*yY+startX*yX));
				*/
			}
			
			if (enableTex) gl.glEnable(GL2.GL_TEXTURE_2D);
			else gl.glDisable(GL2.GL_TEXTURE_2D);			
			
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texQ);

			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(red, green, blue);
			//gl.glColor4f(1f, 1f, 1f,1f);			
		    	gl.glTexCoord2f(0f, 1f);
				gl.glVertex3f(playerX+xZ+startY*xY+startX*xX, playerY+yZ+startY*yY+startX*yX, playerZ+zZ+startY*zY+startX*zX);
		    	gl.glTexCoord2f(0f, 0f);
				gl.glVertex3f(playerX+xZ+endY*xY+startX*xX, playerY+yZ+endY*yY+startX*yX, playerZ+zZ+endY*zY+startX*zX);			
		    	gl.glTexCoord2f(1f, 0f);
				gl.glVertex3f(playerX+xZ+endY*xY+endX*xX, playerY+yZ+endY*yY+endX*yX, playerZ+zZ+endY*zY+endX*zX);			
		    	gl.glTexCoord2f(1f, 1f);
				gl.glVertex3f(playerX+xZ+startY*xY+endX*xX, playerY+yZ+startY*yY+endX*yX, playerZ+zZ+startY*zY+endX*zX);			
			gl.glEnd(); 
		}
		
		public void drawAvatar(final GL2 gl){
			
	    	//gl.glBindTexture(GL2.GL_TEXTURE_2D, texBack);
	    	
			float playerWidth=1f;
			float playerLength=1f;
			
			float diff=0.35f;
			
			gl.glEnable(GL2.GL_TEXTURE_2D);
			
		/* AVATAR FACE */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texJordan);	    	
	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f,1f,1f,1f);
		    	gl.glTexCoord2f(0f,0f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ-diff);	    	
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,playerY-1f, playerZ-diff);	    	
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY+1f,playerZ-diff);
		    	gl.glTexCoord2f(0f, 1f);	    	
		    	gl.glVertex3f(playerX+playerWidth,  playerY+1f, playerZ-diff);	    	
	    	gl.glEnd();  	
	    	
	    /* AVATAR BACK HEAD */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texHead);
	    	gl.glBegin(GL2.GL_QUADS);	    	
	    	gl.glColor4f(1f,1f,1f,1f);	    	
		    	gl.glTexCoord2f(0f,0f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ-playerLength+diff);	    	
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,playerY-1f, playerZ-playerLength+diff);	    	
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY+1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(0f, 1f);	    	
		    	gl.glVertex3f(playerX+playerWidth,  playerY+1f, playerZ-playerLength+diff);	    	
	    	gl.glEnd();
	    	
	    /* AVATAR FRONT OF BODY */ // -------------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texBody);
	    	gl.glBegin(GL2.GL_QUADS);		    	
		    	gl.glTexCoord2f(1f,0f);
		    	gl.glVertex3f(playerX+playerWidth, -playerHeight, playerZ);	    	
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,-playerHeight, playerZ);
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ);
		    	gl.glTexCoord2f(1f, 1f);	    	
		    	gl.glVertex3f(playerX+playerWidth,  playerY-1f, playerZ);
	    	gl.glEnd();
	    	
	    /* AVATAR BACK OF BODY */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texJBack);
	    	gl.glBegin(GL2.GL_QUADS);	
	    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX+playerWidth, -playerHeight, playerZ-playerLength);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,-playerHeight, playerZ-playerLength);
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ-playerLength);
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX+playerWidth,  playerY-1f, playerZ-playerLength);
	    	gl.glEnd();
	    	
	    /* SIDES OF HEAD */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texProf);
	    	gl.glBegin(GL2.GL_QUADS);	    	
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX+playerWidth, -playerHeight, playerZ);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth,-playerHeight, playerZ-playerLength);
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ-playerLength);
		    	gl.glTexCoord2f(0f, 1f);	    	
		    	gl.glVertex3f(playerX+playerWidth,  playerY-1f, playerZ);
	    	
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX-playerWidth, -playerHeight, playerZ);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,-playerHeight, playerZ-playerLength);
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ-playerLength);	    	
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ);
		    	
	    	gl.glEnd();
	    	
	    /* RED TEXTURE ON BODY */ // ------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texRed);
	    	gl.glBegin(GL2.GL_QUADS);		    	
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ);
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,playerY-1f, playerZ-playerLength);
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ-playerLength);	    	
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ);	    	
	    	gl.glEnd();
	    	
	    /* SIDES OF BODY */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texSide);
	    	gl.glBegin(GL2.GL_QUADS);		    	
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth, playerY-1f, playerZ-diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth,playerY-1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth, playerY+1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth,  playerY+1f,playerZ-diff);
		    	
		    	gl.glTexCoord2f(1f, 0f);    	
		    	gl.glVertex3f(playerX-playerWidth, playerY-1f, playerZ-diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,playerY-1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth, playerY+1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,  playerY+1f, playerZ-diff);
		    	
		    	gl.glTexCoord2f(1f, 0f);    	
		    	gl.glVertex3f(playerX+playerWidth, playerY+1f, playerZ-diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth,playerY+1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth, playerY+1f, playerZ-playerLength+diff);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth,  playerY+1f, playerZ-diff);
		    	
		    	
	    	gl.glEnd();
	    	
	    /* AVATAR ARMS */ //--------------------------------------------------
	    	gl.glBindTexture(GL2.GL_TEXTURE_2D, texSide);
	    	gl.glBegin(GL2.GL_QUADS);	
	    	
	    	if (theta >-25){
	    		theta-=2f*slow*timeScale;
	    	}
	    	
	    	float cosTheta=(float) Math.cos(theta/180f*Math.PI);
	    	float sinTheta=(float) Math.sin(theta/180f*Math.PI);	
	    	
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX+playerWidth+0.01f, playerY-1.2f, playerZ-playerLength/2);	    	
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX+playerWidth+0.01f,playerY-1.2f-2*playerLength*sinTheta, playerZ-playerLength/2+2*playerLength*cosTheta);	    	
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX+playerWidth+0.01f, playerY-1.2f-0.5f*cosTheta-2*playerLength*sinTheta, playerZ-playerLength/2+2*playerLength*cosTheta-0.5f*sinTheta);
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX+playerWidth+0.01f,  playerY-1.2f-0.5f*cosTheta, playerZ-playerLength/2-0.5f*sinTheta);
		    	
		    	gl.glTexCoord2f(1f, 1f);
		    	gl.glVertex3f(playerX-playerWidth-0.01f, playerY-1.2f, playerZ-playerLength/2);	    	
		    	gl.glTexCoord2f(0f, 1f);
		    	gl.glVertex3f(playerX-playerWidth-0.01f,playerY-1.2f-2*playerLength*sinTheta, playerZ-playerLength/2+2*playerLength*cosTheta);	    	
		    	gl.glTexCoord2f(0f, 0f);
		    	gl.glVertex3f(playerX-playerWidth-0.01f, playerY-1.2f-0.5f*cosTheta-2*playerLength*sinTheta, playerZ-playerLength/2+2*playerLength*cosTheta-0.5f*sinTheta);	    	
		    	gl.glTexCoord2f(1f, 0f);
		    	gl.glVertex3f(playerX-playerWidth-0.01f,  playerY-1.2f-0.5f*cosTheta, playerZ-playerLength/2-0.5f*sinTheta);
	    	gl.glEnd();
	    	
	    	
		}
		
		public void replay(float slowmo){
			slow=slowmo;
			myBall.shootBasketball(playerX, playerY, playerZ, oldx, oldy, oldz, oldintense);
			theta=60f;
			replayMode=true;
		}

		
		public void drawFence(final GL2 gl) {
			int borderSize = 7;
			
			gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
			gl.glBindTexture(GL2.GL_TEXTURE_2D, texBrick);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);			
			myBall.setFence(floorwidth+borderSize, 15f);
				gl.glBegin(GL2.GL_QUADS);
				// Short Side Front pt1 ----------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		floorwidth + borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					floorwidth + borderSize);
						gl.glTexCoord2f(3f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					floorwidth + borderSize);
						gl.glTexCoord2f(3f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		floorwidth + borderSize);
				// Short Side Front pt2 ------------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		floorwidth + borderSize + 1);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					floorwidth + borderSize + 1);
						gl.glTexCoord2f(3f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					floorwidth + borderSize + 1);
						gl.glTexCoord2f(3f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		floorwidth + borderSize + 1);

				// Short Side Back pt1 -------------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		- floorwidth - borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(3f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(3f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		- floorwidth - borderSize);
				// Short Side Back pt2 -------------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		- floorwidth - borderSize - 1);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					- floorwidth - borderSize - 1);
						gl.glTexCoord2f(3f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					- floorwidth - borderSize - 1);
						gl.glTexCoord2f(3f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		- floorwidth - borderSize - 1);
					
				// Long Side Right pt1 ---------------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		floorwidth + borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					floorwidth + borderSize);
						gl.glTexCoord2f(5f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(5f, 1f);	gl.glVertex3f(-floorLength - borderSize, 		-playerHeight, 		- floorwidth - borderSize);
				// Long Side Right pt2 ---------------------
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(-floorLength - borderSize - 1, 	-playerHeight, 		floorwidth + borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(-floorLength - borderSize - 1, 	0, 					floorwidth + borderSize);
						gl.glTexCoord2f(5f, 0f);	gl.glVertex3f(-floorLength - borderSize - 1, 	0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(5f, 1f);	gl.glVertex3f(-floorLength - borderSize - 1, 	-playerHeight, 		- floorwidth - borderSize);

				// Long Side Left pt1 ---------------------	
						gl.glTexCoord2f(0f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		floorwidth + borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					floorwidth + borderSize);
						gl.glTexCoord2f(5f, 0f);	gl.glVertex3f(floorLength + borderSize, 		0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(5f, 1f);	gl.glVertex3f(floorLength + borderSize, 		-playerHeight, 		- floorwidth - borderSize);
					
				// Long Side Left pt2 -----------------------
						gl.glTexCoord2f(0f, 1f); 	gl.glVertex3f(floorLength + borderSize + 1, 	-playerHeight, 		floorwidth + borderSize);
						gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize + 1, 	0, 					floorwidth + borderSize);
						gl.glTexCoord2f(5f, 0f);	gl.glVertex3f(floorLength + borderSize + 1, 	0, 					- floorwidth - borderSize);
						gl.glTexCoord2f(5f, 1f);	gl.glVertex3f(floorLength + borderSize + 1, 	-playerHeight, 		- floorwidth - borderSize);
				gl.glEnd();
				
				// Top of walls -----------------------------				
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor3f(0.545f, 0.f, 0.f);
					gl.glVertex3f(floorLength + borderSize, 	0, 			floorwidth + borderSize);
					gl.glVertex3f(floorLength + borderSize, 	0, 			- floorwidth - borderSize);
					gl.glVertex3f(floorLength + borderSize + 1, 0, 			- floorwidth + borderSize);
					gl.glVertex3f(floorLength + borderSize + 1, 0, 			floorwidth + borderSize);
				
					gl.glVertex3f(-floorLength - borderSize, 		0, 			floorwidth + borderSize);
					gl.glVertex3f(-floorLength - borderSize, 		0, 			- floorwidth - borderSize);
					gl.glVertex3f(-floorLength - borderSize - 1, 	0, 			- floorwidth + borderSize);
					gl.glVertex3f(-floorLength - borderSize - 1, 	0, 			floorwidth + borderSize);

					gl.glVertex3f(floorLength + borderSize, 	0, 			- floorwidth - borderSize - 1);
					gl.glVertex3f(-floorLength - borderSize, 	0, 			- floorwidth - borderSize - 1);
					gl.glVertex3f(-floorLength - borderSize, 	0, 			- floorwidth - borderSize);
					gl.glVertex3f(floorLength + borderSize, 	0, 			- floorwidth - borderSize);
					
					gl.glVertex3f(floorLength + borderSize, 	0, 			floorwidth + borderSize + 1);
					gl.glVertex3f(-floorLength - borderSize, 	0, 			floorwidth + borderSize + 1);
					gl.glVertex3f(-floorLength - borderSize, 	0, 			floorwidth + borderSize);
					gl.glVertex3f(floorLength + borderSize, 	0, 			floorwidth + borderSize);					
				gl.glEnd();
				
				// gl.glGenerateMipmap(GL2.GL_TEXTURE_2D);
				gl.glBindTexture(GL2.GL_TEXTURE_2D, texFence);
				gl.glEnable (GL2.GL_BLEND); 
				gl.glBlendFunc (GL2.GL_ONE, GL2.GL_ONE_MINUS_SRC_ALPHA); 
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_REPEAT);
				gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_REPEAT);
				
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor4f(0.f, 0.f, 0.f, 1.f);
					gl.glTexCoord2f(0f, 15f);	gl.glVertex3f(floorLength + borderSize, 		0, 			floorwidth + borderSize + .5f);
					gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		15, 		floorwidth + borderSize + .5f);
					gl.glTexCoord2f(70f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		15, 		floorwidth + borderSize + .5f);
					gl.glTexCoord2f(70f, 15f);	gl.glVertex3f(-floorLength - borderSize, 		0, 			floorwidth + borderSize + .5f);
					
					gl.glTexCoord2f(0f, 15f);	gl.glVertex3f(floorLength + borderSize, 		0, 			- floorwidth - borderSize - .5f);
					gl.glTexCoord2f(0f, 0f);	gl.glVertex3f(floorLength + borderSize, 		15, 		- floorwidth - borderSize - .5f);
					gl.glTexCoord2f(70f, 0f);	gl.glVertex3f(-floorLength - borderSize, 		15, 		- floorwidth - borderSize - .5f);
					gl.glTexCoord2f(70f, 15f);	gl.glVertex3f(-floorLength - borderSize, 		0, 			- floorwidth - borderSize - .5f);
				
					gl.glTexCoord2f(0f, 15f);		gl.glVertex3f(-floorLength - borderSize - .5f, 	0, 		floorwidth + borderSize);
					gl.glTexCoord2f(0f, 0f);		gl.glVertex3f(-floorLength - borderSize - .5f, 	15, 	floorwidth + borderSize);
					gl.glTexCoord2f(131.6f, 0f);	gl.glVertex3f(-floorLength - borderSize - .5f, 	15, 	- floorwidth - borderSize);
					gl.glTexCoord2f(131.6f, 15f);	gl.glVertex3f(-floorLength - borderSize - .5f, 	0, 		- floorwidth - borderSize);

					gl.glTexCoord2f(0f, 15f); 		gl.glVertex3f(floorLength + borderSize + .5f, 	0, 		floorwidth + borderSize);
					gl.glTexCoord2f(0f, 0f);		gl.glVertex3f(floorLength + borderSize + .5f, 	15, 	floorwidth + borderSize);
					gl.glTexCoord2f(131.6f, 0f);	gl.glVertex3f(floorLength + borderSize + .5f, 	15, 	- floorwidth - borderSize);
					gl.glTexCoord2f(131.6f, 15f);	gl.glVertex3f(floorLength + borderSize + .5f, 	0, 		- floorwidth - borderSize);
				gl.glEnd();
				gl.glDisable(GL2.GL_BLEND);
				
				gl.glBegin(GL2.GL_QUADS);
				gl.glColor3f(.753f, .753f, .753f);
					gl.glVertex3f(floorLength + borderSize, 			15, 	floorwidth + borderSize + .5f	);
					gl.glVertex3f(floorLength + borderSize + .5f,		15, 	floorwidth + borderSize			);
					gl.glVertex3f(floorLength + borderSize + .5f,		0,	 	floorwidth + borderSize			);
					gl.glVertex3f(floorLength + borderSize, 			0,  	floorwidth + borderSize + .5f	);
					
					gl.glVertex3f(- floorLength - borderSize, 			15, 	floorwidth + borderSize + .5f	);
					gl.glVertex3f(- floorLength - borderSize - .5f,		15, 	floorwidth + borderSize			);
					gl.glVertex3f(- floorLength - borderSize - .5f,		0,	 	floorwidth + borderSize			);
					gl.glVertex3f(- floorLength - borderSize, 			0,  	floorwidth + borderSize + .5f	);
					
					gl.glVertex3f(floorLength + borderSize, 			15, 	- floorwidth - borderSize - .5f	);
					gl.glVertex3f(floorLength + borderSize + .5f,		15, 	- floorwidth - borderSize		);
					gl.glVertex3f(floorLength + borderSize + .5f,		0,	 	- floorwidth - borderSize		);
					gl.glVertex3f(floorLength + borderSize, 			0,  	- floorwidth - borderSize - .5f	);
					
					gl.glVertex3f(- floorLength - borderSize, 			15, 	- floorwidth - borderSize - .5f	);
					gl.glVertex3f(- floorLength - borderSize - .5f,		15, 	- floorwidth - borderSize		);
					gl.glVertex3f(- floorLength - borderSize - .5f,		0,	 	- floorwidth - borderSize		);
					gl.glVertex3f(- floorLength - borderSize, 			0,  	- floorwidth - borderSize - .5f	);
					
				gl.glEnd();
					
					
					
		
		}
		
		@Override
		public void dispose(GLAutoDrawable arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
		
		    char key= e.getKeyChar();

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (!replayMode&&myBall.getDisappeared()){
			if (e.getKeyCode()==KeyEvent.VK_SPACE &&!holdingDown&&!off){
				
					holdingDown=true;

			}
			switch(e.getKeyCode())
			{
			case KeyEvent.VK_UP:
				
				rotateX-=0.6f;
				if (rotateX>=90)rotateX=85;
				
				break;
				
			case KeyEvent.VK_DOWN:
				
				rotateX+=0.6f;
				if (rotateX<=-90)rotateX=-85;
				
				break;
			case KeyEvent.VK_RIGHT:
				rotateY-=0.6f;
				if (rotateY<0)rotateY+=360;
				break;
			case KeyEvent.VK_LEFT:
				rotateY+=0.6f;
				if( rotateY>360)rotateY-=360;
				
				break;
			case KeyEvent.VK_A:
				playerX+=1f;
				break;
			case KeyEvent.VK_D:
				playerX-=1f;
				break;
			case KeyEvent.VK_1:
				replay(1f);
				break;
			case KeyEvent.VK_2:
				replay(1/4f);
				break;
			case KeyEvent.VK_3:
				replay(1/10f);
				break;
			default:
				
				break;
			
			}
			}else{
				if (e.getKeyCode()==81){
					replayMode=false;
					myBall.endShot();
					firstShot=true;
				}
/////////////////
				else if (e.getKeyCode()==79){
					printFlag = true;
				}
			}			
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub
			if (!replayMode &&myBall.getDisappeared()){
			if (e.getKeyCode()==KeyEvent.VK_SPACE && (holdingDown||off)){
				float rotX=(float) (-rotateX+50f);
				
				float xLook=-(float)(Math.sin(rotateY*Math.PI/180f)*Math.cos(rotX*Math.PI/180f));
				float yLook=(float)(Math.sin(rotX*Math.PI/180f));
				float zLook= (float) (Math.cos(rotateY*Math.PI/180f)*Math.cos(rotX*Math.PI/180f));

				if (!off)myBall.shootBasketball(playerX,playerY,playerZ,xLook,yLook,zLook, 0.04f+0.15f*intensity);
				oldx=xLook;
				oldy=yLook;
				oldz=zLook;
				oldintense=0.04f+0.15f*intensity;
				if (!off)firstShot=true;

				off=false;
				holdingDown=false;
				intensity=0.0f;
				
			}
			}

		}

		@Override
		public void mouseDragged(MouseEvent e) {
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			if (replayMode){
			if (YY<pointClick[1]+pointClick[0]-XX && YY>pointClick[1]-pointClick[0]+XX){
				repY+=0.6f;
				//if( repY>360)repY-=360;
			}
			else if (YY>pointClick[1]+pointClick[0]-XX && YY<pointClick[1]-pointClick[0]+XX){
				repY-=0.6f;
				//if (repY<0)repY+=360;
			}else if (YY>pointClick[1]){
				repX-=0.6f;
				//if (repX>=90)repX=85;
			}
			else if (YY<pointClick[1]){
				repX+=0.6f;
				//if (repX<=-90)repX=-85;
			}	
			}
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			/*
			 * Coordinates printout
			 */
			picked_x = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			picked_y = -(e.getY()-windowHeight*0.5f)*orthoX/windowHeight;
			
			
			mouseX0 = e.getX();
			mouseY0 = e.getY();
						
			
			getButtonCommand(mouseX0,mouseY0);
			
			//System.out.println(e.getX()+" "+e.getY());
			float XX = (e.getX()-windowWidth*0.5f)*orthoX/windowWidth;
			float YY = -(e.getY()-windowHeight*0.5f)*orthoX/windowWidth;
			pointClick[0]=XX;
			pointClick[1]=YY;
			
			if(e.getButton()==MouseEvent.BUTTON1) {	// Left button
								
			}
			else if(e.getButton()==MouseEvent.BUTTON3) {	// Right button
				
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) { // cursor enter the window
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) { // cursor exit the window
			// TODO Auto-generated method stub
			
		}
		
		public void drawSkybox(final GL2 gl){
			
			gl.glBegin(GL2.GL_QUADS);
	    	gl.glColor4f(1f, 1f, 1f, 1f);
	    	
	    	gl.glTexCoord2f(0.25f, 2f/3f);
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.0f, 2/3f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.0f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ+boxLength);

	    	gl.glTexCoord2f(0.25f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ+boxLength);

	    	
	    	gl.glTexCoord2f(0.25f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(0.25f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.5f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.5f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    
	  
	    	gl.glTexCoord2f(0.5f, bottom+section*2);
	    	gl.glVertex3f(playerX-boxLength, playerY+boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.75f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.75f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);

	    	gl.glTexCoord2f(0.5f,0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);

	    
	    	gl.glTexCoord2f(1f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(1f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength,playerZ+ boxLength);
	    	
	    	gl.glTexCoord2f(0.75f, bottom+section*2);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.75f,0.34f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);
	  
	    	//bottom face
	    	gl.glTexCoord2f(0.499f,0.01f);
	    	
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.499f, 0.34f);
	    	gl.glVertex3f(playerX-boxLength, playerY-boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.251f, 0.34f);
	    	gl.glVertex3f(playerX-boxLength,playerY -boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.251f,0.01f);
	    	gl.glVertex3f(playerX+boxLength, playerY-boxLength, playerZ+boxLength);
	    	
	    	//Top face
	    	gl.glTexCoord2f(0.499f,2/3f);
	    	
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.499f, 1f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ-boxLength);
	    	
	    	gl.glTexCoord2f(0.251f, 1f);
	    	gl.glVertex3f(playerX+boxLength, playerY+boxLength, playerZ+boxLength);
	    	
	    	gl.glTexCoord2f(0.251f,2/3f);
	    	gl.glVertex3f(playerX-boxLength,playerY+ boxLength, playerZ+boxLength);
	    	gl.glEnd();
		}

}



