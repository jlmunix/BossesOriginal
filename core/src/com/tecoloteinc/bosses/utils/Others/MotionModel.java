package com.tecoloteinc.bosses.utils.Others;

/**
 * Created by oscarmar on 6/12/16.
 */
public interface MotionModel {

    void setTargetPosition(Point2f pt);
    void setTargetPosition(float x, float y);

    void setSpeed(float speed);

    Point2f getCurrPosition();

    Point2f getTargetPosition();

    Point2f getStep();

    void updateModel(float deltaTime);

    boolean reachedTarget();

}
