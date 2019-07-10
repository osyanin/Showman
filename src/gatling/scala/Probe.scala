

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class Probe extends Simulation {

  val httpProtocol = http
    .baseUrl("https://bash.im")

  val scn = scenario("BasicSimulation")
    .exec(http("request_1")
    .get("/"))
    .pause(5)

  setUp(
    scn.inject(
      nothingFor(4 seconds), // 1
      atOnceUsers(10), // 2
      rampUsers(10) during (5 seconds), // 3
      constantUsersPerSec(20) during (15 seconds), // 4
      constantUsersPerSec(20) during (15 seconds) randomized, // 5
      rampUsersPerSec(10) to 20 during (10 minutes), // 6
      rampUsersPerSec(10) to 20 during (10 minutes) randomized, // 7
      heavisideUsers(1000) during (20 seconds) // 8
    ).protocols(httpProtocol)
  )
}
