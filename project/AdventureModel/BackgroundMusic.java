package AdventureModel;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * BackgroundMusic class.
 */
public class BackgroundMusic{
    protected double volume = 0.3;
    protected int fadeLenght = 3;
    public boolean musicPlaying = false;
    public MediaPlayer musicPlayer;

    /**
     * Play the music at the music path
     */
    public void playMusic(String musicPath){

        if (musicPlaying == true){
            fadeOut(musicPlayer);
        }

        try {
            Media musicMedia = new Media(new File(musicPath).toURI().toString());
            musicPlayer = new MediaPlayer(musicMedia);
            fadeIn(musicPlayer);
            musicPlaying = true;
        } catch (MediaException e) {
            musicPlaying = false;
        }
    }

    /**
     * Fade in the music
     */
    private void fadeOut(MediaPlayer musicPlayer){
        KeyValue keyValue = new KeyValue(musicPlayer.volumeProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(this.fadeLenght), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    /**
     * Fade out the music
     */
    private void fadeIn(MediaPlayer musicPlayer){
        musicPlayer.setVolume(0);
        musicPlayer.play();
        KeyValue keyValue = new KeyValue(musicPlayer.volumeProperty(), volume);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(this.fadeLenght), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    /**
     * Set the volume of the music
     */
    public void setVolume(double volume){
        this.volume = volume;
    }

    /**
     * Set the length of the fades in/out
     */
    public void setFadeLength(int length){
        this.fadeLenght = length;
    }
}


