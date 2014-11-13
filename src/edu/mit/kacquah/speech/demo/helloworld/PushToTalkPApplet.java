package edu.mit.kacquah.speech.demo.helloworld;

import java.io.IOException;
import java.util.logging.Logger;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;
import processing.core.PApplet;

public class PushToTalkPApplet extends PApplet {
  // App utils
  private static Logger LOGGER = Logger.getLogger(PushToTalkPApplet.class
      .getName());

  // Speech
  Configuration configuration;
  LiveSpeechRecognizer recognizer;
  SpeechResult result;

  boolean useLanguage = false;


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
      // Start recognition process pruning previously cached data.
      recognizer.startRecognition(true);
      System.out.println("Recognizer running...");
    }
  }

  public void keyReleased() {

    System.out.println("Key released:\"" + key + "\"");

    if (keyCode == CONTROL) {
      System.out.println("Reading result.");
      result = recognizer.getResult();

      if (result != null) {
        System.out.println("...");

        System.out.format("Hypothesis: %s\n", result.getHypothesis());

        System.out.println("List of recognized words and their times:");
        for (WordResult r : result.getWords()) {
          System.out.println(r);
        }

        System.out.println("Best 3 hypothesis:");
        for (String s : result.getNbest(3))
          System.out.println(s);

        System.out.println("Lattice contains "
            + result.getLattice().getNodes().size() + " nodes");
      } else {
        System.out.println("Result = null");
      }

      recognizer.stopRecognition();
      System.out.println("Recognizer stopped.");
    }
  }

  /****************************************************************************/
  /* Additional Methods******************************************************** */
  /****************************************************************************/

  private void initSpeech() {
    System.out.println("Loading models...");

    configuration = new Configuration();

    // Set path to acoustic model.
    configuration
        .setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
    // Set path to dictionary.
    configuration
        .setDictionaryPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
    // Set language model.
    if (useLanguage) {
      configuration.setLanguageModelPath("models/language/en-us.lm.dmp");
    } else {
      configuration.setGrammarPath(GRAMMAR_PATH);
      configuration.setUseGrammar(true);
      configuration.setGrammarName("pushtotalk");
    }
    
    try {
      recognizer = new LiveSpeechRecognizer(configuration);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }


    System.out.println("Done init speech!");
  }

  public static void main(String[] args) {
    String[] newArgs = new String[] { "edu.mit.kacquah.demo.pushtotalk.PushToTalkPApplet" };
    PApplet.main(newArgs);
  }

}
