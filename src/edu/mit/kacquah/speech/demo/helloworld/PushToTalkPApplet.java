package edu.mit.kacquah.speech.demo.helloworld;

import java.io.IOException;
import java.util.logging.Logger;

import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import edu.mit.kacquah.speech.demo.engine.SpeechEngine;
import edu.mit.kacquah.speech.demo.engine.SpeechEngine.ISpeechEventListener;
import processing.core.PApplet;

public class PushToTalkPApplet extends PApplet implements ISpeechEventListener {
  // App utils
  private static Logger LOGGER = Logger.getLogger(PushToTalkPApplet.class
      .getName());

  // Speech
  SpeechEngine speechEngine;
  private static final String GRAMMAR_PATH = "resource:/edu/mit/kacquah/speech/demo/helloworld/";
  private static final String GRAMMAR_NAME = "deckviewer";

  public void setup() {
    size(400, 400);
    background(0);

    initSpeech();
  }

  /****************************************************************************/
  /* Processing Methods******************************************************** */
  /****************************************************************************/

  public void draw() {
    // Update the app.
    long elapsedTime = System.currentTimeMillis() % 1000;
    update(elapsedTime);
    // render the app.
    render(this);
  }

  public void update(long elapsedTime) {

  }

  public void render(PApplet p) {

  }

  public void keyPressed() {

    System.out.println("Key pressed:\"" + key + "\"");

    if (keyCode == CONTROL) {

    }
  }

  public void keyReleased() {

    System.out.println("Key released:\"" + key + "\"");

    if (keyCode == CONTROL) {

    }
  }

  /****************************************************************************/
  /* Additional Methods******************************************************** */
  /****************************************************************************/

  private void initSpeech() {
    speechEngine = new SpeechEngine();
    speechEngine.setGrammarPath(GRAMMAR_PATH);
    speechEngine.setGrammarName(GRAMMAR_NAME);
    speechEngine.setSpeechListener(this);
    speechEngine.initRecognition();
    speechEngine.startRecognition();
  }

  @Override
  public void handleSpeechResult(SpeechResult result) {
    System.out.println("...");

    System.out.format("Hypothesis: %s\n", result.getHypothesis());

    System.out.println("List of recognized words and their times:");
    for (WordResult r : result.getWords()) {
      System.out.println(r);
    }

    System.out.println("Best 3 hypothesis:");
    for (String s : result.getNbest(10))
      System.out.println(s);

    System.out.println("Lattice contains "
        + result.getLattice().getNodes().size() + " nodes");    
  }

  public static void main(String[] args) {
    String[] newArgs = new String[] { "edu.mit.kacquah.speech.demo.helloworld.PushToTalkPApplet" };
    PApplet.main(newArgs);
  }



}
