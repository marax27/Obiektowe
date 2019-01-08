package ServerPackage;


public class Winner{
    public Winner(String winner_name){
        id = null;
        name = winner_name;
    }

    public Winner(){
        name = "Anonymous";
    }

    public void setId(Integer s){ id = s; }
    public Integer getId(){ return id; }

    public void setName(String s){ name = s; }
    public String getName(){ return name; }

    private Integer id;
    private String name;
}

