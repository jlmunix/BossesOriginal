package com.tecoloteinc.bosses.utils.Others;

import  java.lang.*;


/**
 * Created by oscarmar on 6/13/16.
 */
public class Point2f {

    public float pX;
    public float pY;

    public Point2f(float x, float y){
        pX = x;
        pY = y;
    }

    static public Point2f sum(Point2f pt1, Point2f pt2)
    {
        Point2f res;
        res = new Point2f(pt1.pX + pt2.pX, pt1.pY + pt2.pY);

        return res;
    }

    static public  Point2f diff(Point2f pt1, Point2f pt2){
        Point2f res;
        res = new Point2f(pt1.pX - pt2.pX, pt1.pY - pt2.pY);

        return res;
    }

    static public Point2f scale(Point2f pt, float scale){
        return new Point2f(pt.pX*scale, pt.pY*scale);
    }

    public Point2f scale(float scale){
        return Point2f.scale(this, scale);
    }

    static public float dot(Point2f pt1, Point2f pt2){
        return pt1.pX*pt2.pX + pt1.pY*pt2.pY;
    }

    static public float cross(Point2f pt1, Point2f pt2){
        return pt1.pX*pt2.pY - pt1.pY*pt2.pX;
    }

    public double norm(){
        return norm(this);
    }

    static public double norm(Point2f pt){
        return Math.sqrt(pt.pX*pt.pX + pt.pY*pt.pY);
    }

}
