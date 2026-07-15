package co.com.sqa.reto.runners;

import co.com.sqa.reto.questions.ValidarListaUsuarios;
import co.com.sqa.reto.tasks.ConsultarUsuarios;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class RetoApiRunner {

    private final Actor analistaQA = Actor.named("Analista QA");
    private final String baseUrl = "https://reqres.in";

    @BeforeEach
    public void configurarActor() {
        // Serenity se encarga de acoplar de forma segura la API al actor
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