package co.sqa.reto.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class ValidarListaUsuarios {

    // Evitamos instanciar la clase utilitaria
    private ValidarListaUsuarios() {}

    public static Question<Integer> codigoDeRespuesta() {
        return Question.about("Código de respuesta HTTP")
                .answeredBy(actor -> {
                    return SerenityRest.lastResponse().statusCode();
                });
    }

    public static Question<Integer> laPaginaRetornada() {
        return Question.about("La página en la respuesta")
                .answeredBy(actor -> {
                    return SerenityRest.lastResponse().path("page");
                });
    }

    public static Question<Integer> elTotalDePaginas() {
        return Question.about("El total de páginas en la respuesta")
                .answeredBy(actor -> {
                    return SerenityRest.lastResponse().path("total_pages");
                });
    }
}