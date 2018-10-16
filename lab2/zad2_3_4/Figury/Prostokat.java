package Figury;

public class Prostokat extends Kwadrat {
    Prostokat(double _a, double _b){
        super(_a);
        setB(_b);
    }

    public double getB(){ return b; }

    public void setB(double _b){
        if(_b <= 0.0) {
            throw new IllegalArgumentException("b must be > 0");
        }
        b = _b;
    }

    public double area(){
        return a*b;
    }

    public boolean isBigger(Prostokat p){
        return this.area() <= p.area();
    }

    protected double b;
}
