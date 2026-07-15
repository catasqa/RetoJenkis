package co.sqa.reto.runners; 

import co.sqa.reto.questions.ValidarListaUsuarios; 
import co.sqa.reto.tasks.ConsultarUsuarios;       
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SerenityJUnit5Extension.class)
public class RetoApiRunner {

    private final String baseUrl = "https://reqres.in";

    @BeforeEach
    public void configurarActor() {
        // Preparamos el escenario e inicializamos al actor con su nombre
        OnStage.setTheStage(Cast.ofStandardActors());
        OnStage.theActorCalled("Analista QA");
    }

    @Test
    public void consultarListaDeUsuariosExitosamente() {
        // GIVEN: Dado que el actor en escena tiene la habilidad de consumir la API REST
        givenThat(theActorInTheSpotlight()).can(CallAnApi.at(baseUrl));

        // WHEN: Cuando el actor realiza la acción de consultar los usuarios de la página 2
        when(theActorInTheSpotlight()).attemptsTo(
                ConsultarUsuarios.deLaPagina(2)
        );

        // THEN: Entonces el actor valida que las respuestas cumplan con los criterios esperados
        then(theActorInTheSpotlight()).should(
                seeThat(ValidarListaUsuarios.codigoDeRespuesta(), equalTo(200)),
                seeThat(ValidarListaUsuarios.laPaginaRetornada(), equalTo(2)),
                seeThat(ValidarListaUsuarios.elTotalDePaginas(), equalTo(2))
        );
    }
}