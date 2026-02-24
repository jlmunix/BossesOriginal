package com.tecoloteinc.bosses.utils.Others;

/**
 * Created by oscarmar on 6/13/16.
 */
public class SimpleMotionModel implements com.tecoloteinc.bosses.utils.Others.MotionModel {

    static final float epsilon = .1f;

    private float maxSpeed;
    private float initDist;

    private float distance;
    private float speed;

    private com.tecoloteinc.bosses.utils.Others.Point2f position;
    private com.tecoloteinc.bosses.utils.Others.Point2f oldPosition;

    private com.tecoloteinc.bosses.utils.Others.Point2f velocity;
    private com.tecoloteinc.bosses.utils.Others.Point2f targetPos;




    public SimpleMotionModel(float maxSpd){
        maxSpeed = maxSpd;

        position = new com.tecoloteinc.bosses.utils.Others.Point2f(0,0);
        oldPosition = new com.tecoloteinc.bosses.utils.Others.Point2f(0,0);
        velocity = new com.tecoloteinc.bosses.utils.Others.Point2f(0,0);
        targetPos = new com.tecoloteinc.bosses.utils.Others.Point2f(0,0);
    }

    @Override
    public void setTargetPosition(com.tecoloteinc.bosses.utils.Others.Point2f pt) {

        distance = (float) com.tecoloteinc.bosses.utils.Others.Point2f.norm(com.tecoloteinc.bosses.utils.Others.Point2f.diff(pt,position));
        initDist = distance;
        targetPos = pt;

    }

    @Override
    public void setTargetPosition(float x, float y){
        setTargetPosition(new com.tecoloteinc.bosses.utils.Others.Point2f(x,y));
    }

    @Override
    public void setSpeed(float maxSpd) {
        maxSpeed = maxSpd;
    }

    @Override
    public com.tecoloteinc.bosses.utils.Others.Point2f getCurrPosition() {
        return position;
    }

    @Override
    public com.tecoloteinc.bosses.utils.Others.Point2f getTargetPosition() {
        return targetPos;
    }

    @Override
    public com.tecoloteinc.bosses.utils.Others.Point2f getStep() {
        return com.tecoloteinc.bosses.utils.Others.Point2f.diff(position, oldPosition);
    }

    @Override
    public void updateModel(float deltaTime) {

        distance = (float) com.tecoloteinc.bosses.utils.Others.Point2f.diff(targetPos,position).norm();

        if (distance<initDist/4.f){
            speed = 4.f*maxSpeed*distance/initDist;
            if (initDist == 0)
            System.out.print("initDist =" + initDist);
        }
        else if (distance>initDist*3.f/4.f){
            speed = 4.f*maxSpeed*(initDist-distance+epsilon)/initDist;
            if (initDist == 0)
                System.out.print("initDist =" + initDist);
        }
        else{
            speed = maxSpeed;
        }

        if (speed<maxSpeed*.05){
            speed = maxSpeed*.05f;
        }

        oldPosition = position;

        com.tecoloteinc.bosses.utils.Others.Point2f diff = com.tecoloteinc.bosses.utils.Others.Point2f.diff(targetPos,position);

        position = com.tecoloteinc.bosses.utils.Others.Point2f.sum(position, diff.scale(100*speed*deltaTime/(float)diff.norm()));
        System.out.print("initDist =" + (float)diff.norm());


//        System.out.println("distance: " + distance + ", speed: " + speed);
    }

    @Override
    public boolean reachedTarget() {
        return com.tecoloteinc.bosses.utils.Others.Point2f.diff(targetPos,position).norm()<20.f*epsilon;
    }


}
