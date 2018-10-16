package pkg1;

public class A {
    protected int number;
    String name;

    public A(int _number, String _name){
        number = _number;
        name = _name;
    }

    public void callDecrement(){
        decrement();
    }
    public void callChangeName(String new_name){
        changeName(new_name);
    }
    public void callIncrement(){
        increment();
    }
    private void increment(){
        number++;
    }
    protected void decrement(){
        number--;
    }
    void changeName(String new_name){
        name = new_name;
    }
}
