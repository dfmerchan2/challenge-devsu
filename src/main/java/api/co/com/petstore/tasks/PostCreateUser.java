package api.co.com.petstore.tasks;

import api.co.com.petstore.models.User;
import io.restassured.http.ContentType;
import lombok.RequiredArgsConstructor;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.thucydides.core.annotations.Step;

import static api.co.com.petstore.utilities.EndPoints.CREATE_USER;
import static net.serenitybdd.screenplay.Tasks.instrumented;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.apache.http.HttpStatus.SC_OK;

@RequiredArgsConstructor
public class PostCreateUser implements Task {

    private int statusCode = SC_OK;
    private final User userRequest;

    @Override
    @Step("{0} create a new user")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(CREATE_USER.getValue())
                        .with(
                                request -> request
                                        .contentType(ContentType.JSON)
                                        .body(userRequest)
                                        .log().all())
        );
        SerenityRest.lastResponse().print();
        actor.should(seeThatResponse(response -> response.statusCode(statusCode)));
    }

    public static PostCreateUser with(User userRequest) {
        return instrumented(PostCreateUser.class, userRequest);
    }

    public PostCreateUser withStatusCode(int statusCode) {
        this.statusCode = statusCode;
        return this;
    }
}
