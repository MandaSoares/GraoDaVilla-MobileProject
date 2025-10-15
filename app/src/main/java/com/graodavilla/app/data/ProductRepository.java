package com.graodavilla.app.data;

import com.graodavilla.app.R;
import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    //Bebidas quentes
    public static List<Product> getHotDrinks() {
        return Arrays.asList(
                new Product("Cappuccino", 12, R.drawable.cappuccino,
                        "Espresso encorpado com leite vaporizado e espuma cremosa. Doce na medida."),
                new Product("Macchiato", 14, R.drawable.macchiato,
                        "Espresso marcado com um toque de leite vaporizado. Intenso e aromático."),
                new Product("Espresso", 7, R.drawable.espresso,
                        "Dose clássica de espresso: curto, forte e com crema dourada.")
        );
    }

    //Salgados
    public static List<Product> getSnacks() {
        return Arrays.asList(
                new Product("Coxinha", 12, R.drawable.coxinha,
                        "Massa macia recheada com frango temperado, empanada e crocante."),
                new Product("Esfirra", 12, R.drawable.esfirra,
                        "Clássica esfirra de carne, massa leve e recheio suculento."),
                new Product("Quibe", 8, R.drawable.quibe,
                        "Trigo e carne bem temperada com hortelã, crocante por fora e macio por dentro.")
        );
    }

    //Doces
    public static List<Product> getDesserts() {
        return Arrays.asList(
                new Product("Brigadeiro", 6, R.drawable.brigadeiro,
                        "Tradicional brigadeiro de chocolate belga, finalizado com granulado."),
                new Product("Pudim", 10, R.drawable.pudim,
                        "Pudim de leite condensado com calda de caramelo brilhante."),
                new Product("Torta de Limão", 12, R.drawable.torta_de_limao,
                        "Base crocante, creme cítrico de limão e cobertura de merengue.")
        );
    }
}
