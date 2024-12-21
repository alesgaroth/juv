package com.alesgaroth.zuv.textrep;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;

import com.alesgaroth.zuv.design.Algorithm;

public class StepDefinitions {
  Algorithm algorithm;

  @Given("an {word} algorithm")
  public void an_algorithm(String algo) {
     //algorithm = CacheMap.get(algo.shallowCopy());
     algorithm = CacheMap.get(algo);
  }
  @When("I give the following {string}")
  public void i_give_the_following(String commands) {
      new TextRep(algorithm).modify(commands);
  }
  @Then("I get the named {word}")
  public void i_get_the_named(String algo) {
      Algorithm other = CacheMap.get(algo);
      assertTrue(algorithm.equivalentTo(other), () -> algorithm.howDiff(other));
  }
  @Then("I dont get the named {word}")
  public void i_dont_get_the_named(String algo) {
      assertFalse(algorithm.equivalentTo(CacheMap.get(algo)));
  }

}
