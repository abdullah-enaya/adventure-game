package AdventureModel;

import java.io.File;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class BackgroundMusic{
    protected double volume = 0.5;
    protected int fadeLenght = 3;
    protected boolean musicPlaying = false;
    private MediaPlayer musicPlayer;

    public void playMusic(String musicPath){

        if (musicPlaying == true){
            fadeOut(musicPlayer);
        }

        Media musicMedia = new Media(new File(musicPath).toURI().toString());  
        musicPlayer = new MediaPlayer(musicMedia);
        musicPlayer.setVolume(volume);
        fadeIn(musicPlayer);
        musicPlaying = true;
    }

    private void fadeOut(MediaPlayer musicPlayer){
        KeyValue keyValue = new KeyValue(musicPlayer.volumeProperty(), 0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(this.fadeLenght), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    private void fadeIn(MediaPlayer musicPlayer){
        musicPlayer.setVolume(0);
        musicPlayer.play();
        KeyValue keyValue = new KeyValue(musicPlayer.volumeProperty(), 1.0);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(this.fadeLenght), keyValue);
        Timeline timeline = new Timeline(keyFrame);
        timeline.play();
    }

    public void setVolume(double volume){
        this.volume = volume;
    }

    public void setFadeLenght(int lenght){
        this.fadeLenght = lenght;
    }

    public static void main(String[] args) {
        
    }
}


