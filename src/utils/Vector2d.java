package utils;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x , int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return String.format("(%s,%s)" , x , y );
    }

    public boolean precedes(Vector2d other){
        return (this.x<other.x && this.y<other.y);
    }

    public boolean follows(Vector2d other){
        return (this.x>=other.x && this.y>=other.y);
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x , other.x) , Math.max(this.y , other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x , other.x) , Math.min(this.y , other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(this.x+other.x , this.y+other.y);
    }

    public Vector2d multiply(int scalar) {return new Vector2d(this.x*scalar, this.y*scalar);}

    public Vector2d subtract(Vector2d other){
        return new Vector2d(this.x-other.x , this.y-other.y);
    }

    @Override
    public boolean equals(Object other){
        if(other instanceof Vector2d){
            Vector2d o = (Vector2d)other;
            return this.x==o.x && this.y==o.y;
        }
        return false;
    }

    @Override
    public int hashCode() {
        final int p1 = 11587;
        final int p2 = 17321;
        return (p1*this.x) ^ (p2*this.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-this.x , -this.y);
    }

}
