package Labb3;

import java.util.ArrayList;

public class Superfigur {
    private String alias = "N/A";
    private String fnamn = "N/A";
    private String enamn = "N/A";
    private String inriktning = "N/A";
    private String beskrivning = "N/A";
    private String nuvarandeOrg = "N/A";
    private String universum = "N/A";
    private ArrayList<String> tidigareOrg = new ArrayList<>();
    private ArrayList<String> skapareArr = new ArrayList<>();

    public Superfigur(){
    }

    public Superfigur(String alias, String fnamn, String enamn, String inriktning, String beskrivning, String nuvarandeOrg, ArrayList<String> tidigareOrg, ArrayList<String> skapareArr) {
        this.alias = alias;
        this.fnamn = fnamn;
        this.inriktning = inriktning;
        this.beskrivning = beskrivning;
        this.nuvarandeOrg = nuvarandeOrg;
        this.tidigareOrg = tidigareOrg;
        this.skapareArr = skapareArr;
    }

    public Superfigur(String alias, String fnamn, String enamn, String inriktning, String beskrivning, String universum) {
        this.alias = alias;
        this.fnamn = fnamn;
        this.enamn = enamn;
        this.inriktning = inriktning;
        this.beskrivning = beskrivning;
        this.universum = universum;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getFnamn() {
        return fnamn;
    }

    public void setFnamn(String namn) {
        this.fnamn = namn;
    }

    public String getEnamn() {
        return enamn;
    }

    public void setEnamn(String enamn) {
        this.enamn = enamn;
    }

    public String getInriktning() {
        return inriktning;
    }

    public void setInriktning(String inriktning) {
        this.inriktning = inriktning;
    }

    public String getBeskrivning() {
        return beskrivning;
    }

    public void setBeskrivning(String beskrivning) {
        this.beskrivning = beskrivning;
    }

    public String getNuvarandeOrg() {
        return nuvarandeOrg;
    }

    public void setNuvarandeOrg(String nuvarandeOrg) {
        this.nuvarandeOrg = nuvarandeOrg;
    }

    public String getUniversum() {
        return universum;
    }

    public void setUniversum(String universum) {
        this.universum = universum;
    }

    public ArrayList<String> getTidigareOrg() {
        return tidigareOrg;
    }

    public void setTidigareOrg(ArrayList<String> tidigareOrg) {
        this.tidigareOrg = tidigareOrg;
    }

    public void addTidigareOrg(String tidigareOrg){
        this.tidigareOrg.add(tidigareOrg);
    }

    public ArrayList<String> getSkapareArr() {
        return skapareArr;
    }

    public void setSkapareArr(ArrayList<String> skapareArr) {
        this.skapareArr = skapareArr;
    }

    public void addSkapare(String skapare){
        this.skapareArr.add(skapare);
    }
}
