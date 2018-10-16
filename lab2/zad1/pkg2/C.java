package pkg2;

import pkg1.B;

public class C extends B{
    void changeName(String new_name){
        super.callChangeName("[[" + new_name + "]]");
    }
}
