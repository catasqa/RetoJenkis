package co.com.sqa.reto.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Question;

public class ValidarListaUsuarios {

    public static Question<Integer> codigoDeRespuesta() {
        return Question.about("Código de respuesta HTTP")
                .answeredBy(actor -> SerenityRest.lastResponse().statusCode());
    }

    public static Question<Integer> laPaginaRetornada() {
        return Question.about("La página en la respuesta")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("page"));
    }

    public static Question<Integer> elTotalDePaginas() {
        return Question.about("El total de páginas en la respuesta")
                .answeredBy(actor -> SerenityRest.lastResponse().jsonPath().getInt("total_pages"));
    }
}