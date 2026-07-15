package co.com.sqa.reto.runners;

import co.com.sqa.reto.questions.ValidarListaUsuarios;
import co.com.sqa.reto.tasks.ConsultarUsuarios;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll; // <-- ¡Esta importación hacía falta!
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;

@ExtendWith(SerenityJUnit5Extension.class)
public class RetoApiRunner {

    private final Actor analistaQA = Actor.named("Analista QA");
    private final String baseUrl = "https://reqres.in";

    @BeforeAll
    public static void setup() {
        // Esto obliga a RestAssured a volcar TODO el Request y Response en la consola estándar
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @BeforeEach
    public void configurarActor() {
        analistaQA.can(CallAnApi.at(baseUrl));
    }

    @Test
    public void consultarListaDeUsuariosExitosamente() {
        analistaQA.attemptsTo(
                ConsultarUsuarios.deLaPagina(2)
        );

        analistaQA.should(
                seeThat(ValidarListaUsuarios.codigoDeRespuesta(), equalTo(200)),
                seeThat(ValidarListaUsuarios.laPaginaRetornada(), equalTo(2)),
                seeThat(ValidarListaUsuarios.elTotalDePaginas(), equalTo(2))
        );
    }
}