package order;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ingredients {

    private List<String> ingredients;

    public Ingredients(){}

    public Ingredients(List<String> ingredients ) {
        this.ingredients =  ingredients;
    }

    public static Ingredients makeIngredients(){
            return new Ingredients(
                new ArrayList<>(Arrays.asList("61c0c5a71d1f82001bdaaa70", "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa72"))
        );
    }
    public static Ingredients makeIngredientsWithWrongHash(){
        return new Ingredients(
                new ArrayList<>(Arrays.asList(RandomStringUtils.randomAlphanumeric(24), RandomStringUtils.randomAlphanumeric(24), RandomStringUtils.randomAlphanumeric(24)))
                );

    }


    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    @Override
    public String toString(){
        return ingredients.toString();
    }
}
