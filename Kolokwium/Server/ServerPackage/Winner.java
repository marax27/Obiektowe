package ServerPackage;


public class Winner{
    public Winner(String winner_name){
        name = winner_name;
    }

    public Winner(){
        name = "Anonymous";
    }

    public void setName(String s){ name = s; }
    public String getName(){ return name; }


    private String name;
}

