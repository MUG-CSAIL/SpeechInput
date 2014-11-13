package edu.mit.kacquah.speech.demo.helloworld;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

public class HelloWorld {

  private static final String GRAMMAR_PATH = "resource:/edu/mit/kacquah/speech/demo/helloworld/";

  public static void main(String[] args) {
    System.out.println("Loading models...");

    Configuration configuration = new Configuration();

    // Set path to acoustic model.
    configuration
        .setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
    // Set path to dictionary.
    configuration
        .setDictionaryPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
    // Set language model.
    //configuration.setLanguageModelPath("models/language/en-us.lm.dmp");
    configuration.setGrammarPath(GRAMMAR_PATH);
    configuration.setUseGrammar(true);
    configuration.setGrammarName("deckviewer");
    
    
    LiveSpeechRecognizer recognizer;
    try {
      recognizer = new LiveSpeechRecognizer(configuration);
      // Start recognition process pruning previously cached data.
      recognizer.startRecognition(true);
      SpeechResult result;
      System.out.println("Recognizer created...");

      while ((result = recognizer.getResult()) != null) {
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
      // Pause recognition process. It can be resumed then with
      // startRecognition(false).
      recognizer.stopRecognition();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }
}
