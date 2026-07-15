package co.com.sqa.reto.runners;

import co.com.sqa.reto.questions.ValidarListaUsuarios;
import co.com.sqa.reto.tasks.ConsultarUsuarios;
import net.serenitybdd.junit5.SerenityJUnit5Extension;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
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
        // 1. Inicializamos el Stage de Screenplay con un reparto online
        OnStage.setTheStage(new OnlineCast());
        
        // 2. Creamos o llamamos al actor en escena y le asignamos la habilidad REST de manera segura
        OnStage.theActorCalled("Analista QA").can(CallAnApi.at(baseUrl));
    }

    @Test
    public void consultarListaDeUsuariosExitosamente() {
        // 3. Obtenemos al actor que ya está en el centro de atención (Spotlight)
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