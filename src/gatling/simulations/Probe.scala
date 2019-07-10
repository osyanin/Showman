import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class Probe extends Simulation {

  //val httpConf = http.baseUrl("")
  val httpConf: HttpProtocolBuilder = http.baseUrl("https://")

  val scn: ScenarioBuilder = scenario("Basic Simulation")
    .exec(http("request_1")
      .get("/"))
    .pause(5)

  setUp(
    scn.inject(atOnceUsers(1000))
  ).protocols(httpConf)
}
