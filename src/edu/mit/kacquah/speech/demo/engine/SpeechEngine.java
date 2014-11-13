package edu.mit.kacquah.speech.demo.engine;

import java.io.IOException;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

/**
 * Engine for running Sphinx4 speech recognition and returning the result.
 * @author kojo
 *
 */
public class SpeechEngine implements Runnable {  
  
  /**
   * Listener interface for receiving speech events.
   * @author kojo
   *
   */
  public static interface ISpeechEventListener {
    public void handleSpeechResult(SpeechResult result);
  }
  
  // Constants
//  private static final String GRAMMAR_PATH = "resource:/edu/mit/kacquah/speech/demo/helloworld/";
//  private static final String GRAMMAR_NAME = "deckviewer";

  // Configuration
  Configuration configuration;
//  boolean useLanguage = false;
  private String grammarPath;
  private String grammarName;
  
  // Recognizer
  LiveSpeechRecognizer recognizer;
  SpeechResult result;

  // Threads
  boolean initialized;
  Thread speechRecognitionThread;
  
  // Listeners for events
  ISpeechEventListener speechListener;
  
  public SpeechEngine() {
    this.initialized = false;
  }
  
  public void setGrammarPath(String path) {
    this.grammarPath = path;
  }
  
  public void setGrammarName(String name) {
    this.grammarName = name;
  }
  
  public void setSpeechListener(ISpeechEventListener listener) {
    this.speechListener = listener;
  }

  public void initRecognition() {
    System.out.println("Loading models...");

    configuration = new Configuration();

    // Set path to acoustic model.
    configuration
        .setAcousticModelPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz");
    // Set path to dictionary.
    configuration
        .setDictionaryPath("resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d");
    // Set language model.
    if (grammarName.isEmpty() || grammarPath.isEmpty()) {
      configuration.setLanguageModelPath("models/language/en-us.lm.dmp");
    } else {
      configuration.setGrammarPath(grammarPath);
      configuration.setUseGrammar(true);
      configuration.setGrammarName(grammarName);
    }
    
    try {
      recognizer = new LiveSpeechRecognizer(configuration);
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    initialized = true;
  }
  
  public void startRecognition() {
    if (initialized) {
      speechRecognitionThread = new Thread(this);
      speechRecognitionThread.start();
    } else {
      throw new IllegalStateException("Need to initializes speech.");
    }
  }

  @Override
  public void run() {
    // Start recognition process pruning previously cached data.
    recognizer.startRecognition(true);
    SpeechResult result;
    System.out.println("Recognizer created...");

    // Process speech results and send to listeners.
    while ((result = recognizer.getResult()) != null) {
      if (speechListener != null) {
        speechListener.handleSpeechResult(result);
      }
    }
    
    recognizer.stopRecognition();
    System.out.println("Speech thread terminating");
  }
  

}
