package be.howest.nmct.bmi;

/**
 * Created by Bryan on 13/02/2015.
 */
public enum Category {
GROOT_ONDERGEWICHT(0f,15f),
    ERNSTIG_ONDERGEWICHT(15f,16f),
    ONDERGEWICHT(16f,18.5f),
    NORMAAL(18.5f,25f),
    OVERGEWICHT(25f,30f),
    MATIG_OVERGEWICHT(30f,35f),
    ERNSTIG_OVERGEWICHT(35f,40f),
    ZEER_GROOT_OVERGEWICHT(40f,Float.MAX_VALUE);

    private float lowerBoundary;
    private float upperBoundary;
    private Category(float lowerBoundary, float upperBoundary){
        this.lowerBoundary = lowerBoundary;
        this.upperBoundary = upperBoundary;
    }

    public float getLowerBoundary(){
        return lowerBoundary;
    }

    public float getUpperBoundary(){
        return upperBoundary;
    }

    private Boolean isInBoundary(float value){
        if(this.lowerBoundary < value && this.upperBoundary >= value) return true;
        else return false;
    }

    public static Category getCategory(float index){
        for (Category category:Category.values()){
            if(category.isInBoundary(index))return category;
        }
        return null;
    }

    public String toString(){
        return this.name();
    }

}
