package pkg1;

public class B extends A {
    public B(){
        super(1337, "#B object#");
    }

    protected void decrement(){
        super.callDecrement();
    }

    void changeName(String new_name){
        super.changeName("(" + new_name + ")");
    }

    private void increment(){
        super.callIncrement();
    }
}
