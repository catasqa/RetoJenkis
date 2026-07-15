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
        OnStage.setTheStage(Cast.ofStandardActors());
        OnStage.theActorCalled("Analista QA").can(CallAnApi.at(baseUrl));
    }

    @Test
    public void consultarListaDeUsuariosExitosamente() {
        theActorInTheSpotlight().attemptsTo(
                ConsultarUsuarios.deLaPagina(2)
        );

        theActorInTheSpotlight().should(
                seeThat(ValidarListaUsuarios.codigoDeRespuesta(), equalTo(200)),
                seeThat(ValidarListaUsuarios.laPaginaRetornada(), equalTo(2)),
                seeThat(ValidarListaUsuarios.elTotalDePaginas(), equalTo(2))
        );
    }
}