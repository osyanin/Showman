import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class Beacons extends Simulation {

  private val httpProtocol = http.baseUrl("http://localhost:80/api/v1")

  val scn: ScenarioBuilder = scenario("Basic Simulation")
    .exec(http("request_1").get("/users"))

  setUp(
    scn.inject(atOnceUsers(600))
  ).protocols(httpProtocol)
}
