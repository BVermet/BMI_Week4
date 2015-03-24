package be.howest.nmct.bmi;

/**
 * Created by Bryan on 13/02/2015.
 */
public class BMIInfo {

    private float height = 1.70f;
    private int mass = 70;
    private float bmiIndex;

    public float getBmiIndex() {
        return bmiIndex;
    }


    public void setBmiIndex(float bmiIndex){
        this.bmiIndex = bmiIndex;
    }

    public float getHeight(){
        return height;
    }

    public void setHeight(float height){
        this.height = height;
    }

    public int getMass(){
        return mass;
    }

    public void setMass(int mass){
        this.mass = mass;
    }

    public BMIInfo(){
        super();
        recalculate();
    }
    public static double CalculateBMI(float height,int mass){

        return mass/(height*height);
    }

    private void recalculate(){
        bmiIndex = mass/(height*height);
    }

    public Category returnCategory(){
        recalculate();
        return Category.getCategory(bmiIndex);
    }

    public String toString(){
        Category c = this.returnCategory();
        String cat = c.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("Voor uw gewicht(").append(this.mass)
        .append(")en uw lengte (").append(this.height)
        .append(")bedraagt uw bmi:").append(this.bmiIndex)
                .append(". U valt onder de categorie: ").append(cat);
        return sb.toString();
    }



}
