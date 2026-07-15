package co.com.sqa.reto.runners;

import co.com.sqa.reto.questions.ValidarListaUsuarios;
import co.com.sqa.reto.tasks.ConsultarUsuarios;
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
        // Usamos Cast.ofStandardActors() que no requiere dependencias de WebDriver (ideal para APIs)
        OnStage.setTheStage(Cast.ofStandardActors());
        
        // Creamos y asociamos la habilidad REST al actor de forma segura en el Stage
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