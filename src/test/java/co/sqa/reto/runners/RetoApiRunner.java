package co.com.sqa.reto.runners;

import co.com.sqa.reto.questions.ValidarListaUsuarios;
import co.com.sqa.reto.tasks.ConsultarUsuarios;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

// === ¡NUEVAS IMPORTACIONES PARA EVITAR EL ERROR! ===
import java.util.ArrayList;
import java.util.List;
import io.restassured.filter.Filter;
// ===================================================

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
        // Ahora sí compilará perfectamente usando listas mutables
        List<Filter> filters = new ArrayList<>();
        filters.add(new RequestLoggingFilter());
        filters.add(new ResponseLoggingFilter());
        RestAssured.filters(filters);
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