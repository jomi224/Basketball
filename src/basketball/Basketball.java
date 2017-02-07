package basketball;

public class Basketball {
	
	
	private float currentSpeed;
	private float xPos,yPos,zPos;
	private float xOrig, yOrig,zOrig;
	
	private float ang2;
	private float xVec, yVec, zVec;
	
	private float horizSpeed, vertSpeed;
	
	private float rad;
	private boolean isShooting;
	
	private int count=0;
	
	private float fence,fenceY;
	
	
	float backBoardX,backBoardY,backBoardZ, backBoardHeight,backBoardWidth;
	float rimX,rimY,rimZ, rimR;
	
	float courtY=0f;
	
	private boolean score=false;
	
	boolean in=false,out=false;
	
	private boolean disappeared=true;
	private boolean shotOver=false;
	private boolean failSound = false;
	
	
	public Basketball(float x, float y, float z,float r){
		
		xPos=x;
		yPos=y;
		zPos=z;
		xOrig=x;
		yOrig=y;
		zOrig=z;
		
		rad=r;
		isShooting=false;
	}
	
	public void endShot(){
		isShooting=false;
		disappeared=true;
		shotOver=true;	
		failSound = false;
		score=false;
		count=0;
		resetBall();
	}
	public void setFence(float z,float y){
		fence=z;
		fenceY=y;
	}
	public void shootBasketball(float xStart,float yStart,float zStart,float x, float y, float z, float intensity){
		if (!isShooting){
			shotOver=false;
			
			disappeared=false;
			score=false;
			in=false;
			isShooting=true;
			failSound=false;
		
			xPos=xStart;
			yPos=yStart-3f;
			zPos=zStart;

			
			xOrig=xPos;
			yOrig=yPos;
			zOrig=zPos;
			
			
			xVec=x;
			yVec=y;
			zVec=z;
			
			float horizVec=(float)Math.sqrt((xVec*xVec+zVec*zVec));
			float fullVec=(float) Math.sqrt(yVec*yVec+horizVec*horizVec);
			
			float theta= (float) Math.asin(yVec/fullVec);
			if (xVec>0){
				ang2= (float) Math.asin(zVec/horizVec);
			}else{
				ang2=(float) Math.PI-(float) Math.asin(zVec/horizVec);
			}
			
			horizSpeed= intensity*(float) Math.cos(theta);
			vertSpeed= intensity*(float) Math.sin(theta);
			
		}
		
	}	
	
	
	
	public void resetBall(){
		xPos=xOrig;
		yPos=yOrig;
		zPos=zOrig;
	}
	
	
	
	public void SetBackBoard(float originX, float originY,float originZ, float height, float width){
		
		backBoardX=originX;
		backBoardY=originY;
		backBoardZ=originZ;
		
		backBoardHeight=height;
		backBoardWidth=width;
		
	}
	
	public void SetRim(float rX, float rY,float rZ, float rR){
		
		rimX=rX;
		rimY=rY;
		rimZ=rZ;
		rimR=rR;
		
		
	}

	
	public void updatePosition(float slowMo){
		
		
		if (isShooting){
			
			zPos+=horizSpeed*Math.sin(ang2)*slowMo;
			xPos-=horizSpeed*Math.cos(ang2)*slowMo;
			
			yPos+=vertSpeed*slowMo;
			
			vertSpeed-=0.0005f*slowMo;
			
			count+=1;
			checkForCollisions();
			//if (horizSpeed<0.005f && vertSpeed<=0.005f){
			
			if (count>700/slowMo){
				disappeared=true;
				if (!score && !failSound) {
					JoglEventListener.sounds.shotMissed();
					failSound = true;
				}
				count=0;
				isShooting=false;
				resetBall();
			}
		}
		else if (!isShooting){
			
			zPos=-10000f;
		}
	}
	public void SetCourt(float courtYSet){
		courtY=courtYSet;
	}
	
	public void checkForCollisions(){
		
		//Collision for backboard
		if (zPos+rad>=fence && zPos-rad<=fence){
			if (yPos-rad<=fenceY){
				ang2=-ang2;
				
				horizSpeed=horizSpeed*0.5f;
				vertSpeed=vertSpeed*0.8f;
				zPos=fence-rad;
				
				JoglEventListener.sounds.backboardCol();
			}
		}
		if (zPos+rad>=backBoardZ && zPos-rad<=backBoardZ){
			if (xPos<= backBoardX + backBoardWidth && xPos>=backBoardX && yPos<=backBoardY+backBoardHeight && yPos>=backBoardY){
				ang2=-ang2;
				
				horizSpeed=horizSpeed*0.5f;
				vertSpeed=vertSpeed*0.8f;
				if (zPos>=backBoardZ){
					zPos=backBoardZ+rad;
				}else{
					zPos=backBoardZ-rad;

				}
				
				JoglEventListener.sounds.backboardCol();
			}
			else if (xPos>= backBoardX + backBoardWidth*(4f/10f) && xPos<=backBoardX+backBoardWidth*(6f/10f) && yPos<=backBoardY){
				
				ang2=-ang2;
				
				horizSpeed=horizSpeed*0.5f;
				vertSpeed=vertSpeed*0.8f;
				if (zPos>=backBoardZ){
					zPos=backBoardZ+rad;
				}else{
					zPos=backBoardZ-rad;

				}
				JoglEventListener.sounds.backboardCol();
			}
		}
		
		else if (yPos-rad<=rimY &&yPos+rad>=rimY){
			
			float rimWidth=0.1f;
			
			float dist= (float) Math.sqrt(Math.pow(rimX-xPos, 2)+Math.pow(rimZ-zPos,2));
			if (dist<rimR-rimWidth){
				if (!in){ 
					JoglEventListener.sounds.shotMade();
					ang2=-ang2;
					horizSpeed=horizSpeed*0.5f;
					vertSpeed=vertSpeed*0.95f;
					xPos=rimX;
					zPos=rimZ;
					score=true;
					in=true;					
				}
				
			}else if (dist>=rimR-rimWidth && dist<=rimR+rimWidth){
				if (!in){
				horizSpeed=horizSpeed*0.9f;
				vertSpeed=-vertSpeed*0.68f;
				yPos=rimY+rad;
				}
				
			}else if (dist<rimR+rad/2){
				if (!in && !out){
				ang2=-ang2;
				horizSpeed=horizSpeed*0.5f;
				vertSpeed=vertSpeed*0.8f;
				out=true;
				}
			}
		}
		
		else if (yPos-rad<=courtY){
			
				JoglEventListener.sounds.bounce();
				
				horizSpeed=horizSpeed*0.9f;
				vertSpeed=-vertSpeed*0.68f;
				yPos=courtY+rad;
				
				zPos+=horizSpeed*Math.sin(ang2);
				xPos-=horizSpeed*Math.cos(ang2);
				
				yPos+=vertSpeed;				
		}
		else if (in){
			float dist= (float) Math.sqrt(Math.pow(rimX-xPos, 2)+Math.pow(rimZ-zPos,2));
			float rimWidth=0.1f;

			if (dist<rimR-rimWidth && yPos>rimY-2f){

					ang2=-ang2;
					horizSpeed=horizSpeed*0.9f;
					xPos=rimX;
					zPos=rimZ;
					in=true;
			}
		}
		
		
	}
	
	
	public float getX(){return xPos;}
	public float getY(){return yPos;}
	public float getZ(){return zPos;}
	public float getR(){return rad;}
	public boolean getScore(){return score;}
	public boolean getDisappeared(){return disappeared;}
	public boolean getShooting(){return isShooting;}
	public boolean getOver(){return shotOver;}
	
}
