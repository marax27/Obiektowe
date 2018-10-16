package Figury;

public class Kwadrat {
    Kwadrat(double _a){
        setA(_a);
    }

    public double getA(){ return a; }

    public void setA(double _a){
        if(_a <= 0.0) {
            throw new IllegalArgumentException("a must be > 0");
        }
        a = _a;
    }

    public double area(){
        return a*a;
    }

    public boolean isBigger(Kwadrat k){
        return this.area() <= k.area();
    }

    protected double a;
}
