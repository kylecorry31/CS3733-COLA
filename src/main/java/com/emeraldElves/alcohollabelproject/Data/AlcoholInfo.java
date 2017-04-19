package com.emeraldElves.alcohollabelproject.Data;

/**
 * Created by Tarek on 4/1/2017.
 */
public class AlcoholInfo {

    private int alcoholContent;
    private String fancifulName;
    private String brandName;
    private ProductSource origin;
    private String serialNumber;
    private String formula;


    public AlcoholInfo(int alcoholContent, String fancifulName, String brandName, ProductSource origin, AlcoholType alcoholType, Wine wineInfo, String serialNumber, String formula) {
        this.alcoholContent = alcoholContent;
        this.fancifulName = fancifulName;
        this.brandName = brandName;
        this.origin = origin;
        this.alcoholType = alcoholType;
        this.wineInfo = wineInfo;
        this.serialNumber = serialNumber;
        this.formula = formula;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String getFormula() {
        return formula;
    }

    private AlcoholType alcoholType;
    private Wine wineInfo;


    public static class Wine{
        public double pH;
        public int vintageYear;
        public String grapeVarietal;
        public String appellation;

        public Wine(double pH, int vintageYear, String grapeVarietal, String appellation) {
            this.pH = pH;
            this.vintageYear = vintageYear;
            this.grapeVarietal = grapeVarietal;
            this.appellation = appellation;
        }
    }

    public void setAlcoholContent(int alcoholContent) {
        this.alcoholContent = alcoholContent;
    }

    /**
     * Constructor to create a new AlcoholInfo
     *
     * @param alcoholContent Alcohol percentage of beverage
     * @param name           Fanciful name of alcohol
     * @param brandName      Brand name of alcohol
     * @param origin         Whether the alcohol is domestic or imported
     */
    public AlcoholInfo(int alcoholContent, String name, String brandName, ProductSource origin, AlcoholType alcoholType, Wine wineInfo) {
        this.alcoholContent = alcoholContent;
        this.fancifulName = name;
        this.brandName = brandName;
        this.origin = origin;
        this.alcoholType = alcoholType;
        if(alcoholType == AlcoholType.WINE)
        {
            this.wineInfo = wineInfo;
        }
        else if(alcoholType != AlcoholType.WINE)
        {
            this.wineInfo = null;
        }

    }

    /**
     * Gets the alcohol content of the alcohol
     *
     * @return the alcohol percentage as an int
     */
    public int getAlcoholContent() {
        return this.alcoholContent;
    }

    /**
     * Gets the fanciful name of the alcohol
     *
     * @return the alcohol name as a String
     */
    public String getName() {
        return this.fancifulName;
    }

    /**
     * Gets the brand name of the alcohol
     *
     * @return the brand name as a String
     */
    public String getBrandName() {
        return this.brandName;
    }

    /**
     * Gets the origin of the alcohol
     *
     * @return Domestic or Imported
     */
    public ProductSource getOrigin() {
        return this.origin;
    }

    public AlcoholType getAlcoholType(){ return this.alcoholType; }

    public void setAlcoholType(AlcoholType alcoholType) {
        this.alcoholType = alcoholType;
    }

    public Wine getWineInfo()
    {
        return this.wineInfo;
    }


}