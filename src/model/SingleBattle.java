package model;

import com.google.gson.annotations.Expose;

public class SingleBattle {
    @Expose
    public int id;
    @Expose
    public int star;
    @Expose
    public int availGold;
    @Expose
    public int availElixir;

    public SingleBattle(int id, int star, int availGold, int availElixir) {
        this.id = id;
        this.star = star;
        this.availGold = availGold;
        this.availElixir = availElixir;
    }


}
