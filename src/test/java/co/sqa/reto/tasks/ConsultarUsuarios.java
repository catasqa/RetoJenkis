package co.com.sqa.reto.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;
import static net.serenitybdd.screenplay.Tasks.instrumented;

public class ConsultarUsuarios implements Task {

    private final int pagina;

    public ConsultarUsuarios(int pagina) {
        this.pagina = pagina;
    }

    public static ConsultarUsuarios deLaPagina(int pagina) {
        return instrumented(ConsultarUsuarios.class, pagina);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource("/api/users")
                        .with(request -> request
                                .header("Accept", "application/json")
                                .header("x-api-key", "free_user_3GY598Zw05yBV9OiDjNZEUxqpbr") // <-- ¡AQUÍ LO COLOCAS!
                                .header("Content-Type", "application/json")
                                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36")
                                .queryParam("page", pagina)
                                .relaxedHTTPSValidation()
                                .log().all() // <-- Esto imprimirá los detalles de lo que enviamos
                        )
        );
    }
}