package SpeechToText;

import com.google.api.gax.rpc.ClientStream;
import com.google.api.gax.rpc.ResponseObserver;
import com.google.api.gax.rpc.StreamController;
import com.google.cloud.speech.v1.*;
import com.google.cloud.speech.v1p1beta1.AdaptationClient;
import com.google.cloud.speech.v1p1beta1.LocationName;
import com.google.protobuf.ByteString;
import views.AdventureGameView;

import javax.sound.sampled.*;
import javax.sound.sampled.DataLine.Info;
import java.util.ArrayList;

/**
 * Google API based Speech-To-Text.
 * Developed by Google.
 *
 * This code is available on the documentation website.
 */
public class SpeechToText {

    /** Performs microphone streaming speech recognition with a duration of 1 minute. */
    public static String streamingMicRecognize(ArrayList<String> phrases) {

        ResponseObserver<StreamingRecognizeResponse> responseObserver = null;
        final String[] output = new String[1];
        output[0] = "";
        try (SpeechClient client = SpeechClient.create()) {

            PhraseSet.Builder phraseSet = PhraseSet.newBuilder();
            for (String phraseString : phrases) {
                PhraseSet.Phrase.Builder phrase = PhraseSet.Phrase.newBuilder().setValue(phraseString);
                phrase.setBoost(1000);
                phraseSet.addPhrases(phrase);
            }

            SpeechAdaptation adaptation = SpeechAdaptation.newBuilder().addPhraseSets(phraseSet).build();


            responseObserver =
                    new ResponseObserver<StreamingRecognizeResponse>() {
                        public void onStart(StreamController controller) {}

                        public void onResponse(StreamingRecognizeResponse response) {
                            StreamingRecognitionResult result = response.getResultsList().get(0);
                            SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                            output[0] = alternative.getTranscript();
                        }

                        public void onComplete() {}

                        public void onError(Throwable t) {
                            System.out.println(t);
                        }
                    };

            ClientStream<StreamingRecognizeRequest> clientStream =
                    client.streamingRecognizeCallable().splitCall(responseObserver);

            RecognitionConfig recognitionConfig =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
                            .setLanguageCode("en-US")
                            .setSampleRateHertz(16000)
                            .setAdaptation(adaptation)
                            .build();
            StreamingRecognitionConfig streamingRecognitionConfig =
                    StreamingRecognitionConfig.newBuilder().setConfig(recognitionConfig).build();

            StreamingRecognizeRequest request =
                    StreamingRecognizeRequest.newBuilder()
                            .setStreamingConfig(streamingRecognitionConfig)
                            .build(); // The first request in a streaming call has to be a config

            clientStream.send(request);
            // SampleRate:16000Hz, SampleSizeInBits: 16, Number of channels: 1, Signed: true,
            // bigEndian: false
            AudioFormat audioFormat = new AudioFormat(16000, 16, 1, true, false);
            DataLine.Info targetInfo =
                    new Info(
                            TargetDataLine.class,
                            audioFormat); // Set the system information to read from the microphone audio stream

            if (!AudioSystem.isLineSupported(targetInfo)) {
                System.out.println("Microphone not supported");
                System.exit(0);
            }
            // Target data line captures the audio stream the microphone produces.
            TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetDataLine.open(audioFormat);
            targetDataLine.start();
            System.out.println("Start speaking");
            long startTime = System.currentTimeMillis();
            // Audio Input Stream
            AudioInputStream audio = new AudioInputStream(targetDataLine);
            while (true) {

                long estimatedTime = System.currentTimeMillis() - startTime;
                byte[] data = new byte[6400];
                audio.read(data);

                if (estimatedTime > 6000 || !output[0].isEmpty()) { // 6 seconds
                    System.out.println("Stop speaking.");
                    targetDataLine.stop();
                    targetDataLine.close();
                    break;
                }

                request = StreamingRecognizeRequest.newBuilder().setAudioContent(ByteString.copyFrom(data)).build();
                clientStream.send(request);

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return output[0];
    }
}
