/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package play;



import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;


/**
 *
 * @author Peter
 */
public class FXMLDocumentController implements Initializable {
   
    @FXML
    private Label label;
    @FXML
    private AnchorPane pane;
  
    @FXML
    private Pane lol;
    @FXML
    public ListView<String> scroll;
    @FXML
    private Label total;
    @FXML
    private Button play;
    @FXML
    private Button Home;
     public Button Start;
    boolean flag=false;
    boolean volumeflag=false; //true means mute 
    static String filePath;
    @FXML
    private MediaView mediaVeiw= new MediaView();
     private MediaPlayer mediaPlayer;
     List<MediaPlayer> players = new ArrayList<MediaPlayer>();
     private ChangeListener<Duration> progressChangeListener;
    @FXML
    private ProgressBar progress;
    @FXML
    private Button back;
    @FXML
    private Button stop;
    @FXML
    private Button replay;
    @FXML
    private Button skip;
    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Label locate;
    @FXML
    private Slider slider;
    @FXML
    private Label volumeIcon;
    @FXML
    private Label volumeIcon1;
   
    public void seleectdirectory(){
        int count=0;
           Path currentRelativePath = Paths.get("");
     ObservableList<String> items =FXCollections.observableArrayList ();
     filePath=currentRelativePath.toAbsolutePath().toString();
File folder = new File(currentRelativePath.toAbsolutePath().toString());
File[] listOfFiles = folder.listFiles();
     
     for (int i = 0; i < listOfFiles.length; i++) {
      // if (listOfFiles[i].isFile()) {
     if (listOfFiles[i].isDirectory()) {
       items.add(listOfFiles[i].getName());
       count++;
    }}
     total.setText(String.valueOf(count));
     locate.setText("Home");
scroll.setItems(items);
     }
       public void seleectfile(String directory){
            int count=0;
        //   Path currentRelativePath = Paths.get("");
        
     ObservableList<String> items =FXCollections.observableArrayList ();
   filePath=filePath+"\\"+ directory;
File folder = new File(filePath);


if (!folder.isDirectory() &&directory.endsWith("mp3")){
   
  
 // filePath=folder.getPath();
  
  File dir=new File(folder.getParent());
  if (!dir.exists() && dir.isDirectory()) {
       label.setText("Cannot find audio source directory: " + dir);
    }
  
  // create some media players.
  for (String file : dir.list(new FilenameFilter() {
        
      @Override public boolean accept(File dir, String name) {
        return name.endsWith(".mp3");
      }
    })) players.add(createPlayer("file:///" + (dir + "\\" + file).replace("\\", "/").replaceAll(" ", "%20")));
    if (players.isEmpty()) {
      label.setText("No audio" + dir);
      return;
      
    }
  
    mediaVeiw = new MediaView(players.get(0));
    

    // play each audio file in turn.
    for (int i = 0; i < players.size(); i++) {
       MediaPlayer player     = players.get(i);
       MediaPlayer nextPlayer = players.get((i + 1) % players.size());
   
      player.setOnEndOfMedia(new Runnable() {
        @Override public void run() {
      // player.currentTimeProperty().removeListener(progressChangeListener);
         // mediaVeiw.setMediaPlayer(nextPlayer);
         // nextPlayer.play();
         mediaVeiw.getMediaPlayer().pause();
         // play.setText("Play");
          
         }
      });
    }
     mediaVeiw.mediaPlayerProperty().addListener(new ChangeListener<MediaPlayer>() {
      @Override public void changed(ObservableValue<? extends MediaPlayer> observableValue, MediaPlayer oldPlayer, MediaPlayer newPlayer) {
        setCurrentlyPlaying(newPlayer);
      }
    });
   mediaVeiw.setMediaPlayer(players.get(0));
    mediaVeiw.getMediaPlayer().play();
    setCurrentlyPlaying(mediaVeiw.getMediaPlayer());
    
  
 slider.setValue(mediaVeiw.getMediaPlayer().getVolume()*100);
         slider.valueProperty().addListener(new InvalidationListener() {
               @Override
               public void invalidated(Observable observable) {
                   
              mediaVeiw.getMediaPlayer().setVolume(slider.getValue()/100);
              
               
               }
           });
  
  scroll.setDisable(true);
   Home.setFocusTraversable(false);
         button1.setFocusTraversable(false);
         button2.setFocusTraversable(false);
         back.setFocusTraversable(false);
         replay.setFocusTraversable(false);
         stop.setFocusTraversable(false);
         skip.setFocusTraversable(false);
}
else {
    File[] listOfFiles = folder.listFiles();
     
     for (int i = 0; i < listOfFiles.length; i++) {
        
       items.add(listOfFiles[i].getName());
       count++;
    }
     total.setText(String.valueOf(count));
     locate.setText(directory);
scroll.setItems(items);
     }
  
     }
      @FXML
    private void Home(ActionEvent event) {
        try{
       seleectdirectory();
          scroll.setDisable(false);  
          MediaPlayer currentPlayer = mediaVeiw.getMediaPlayer();
          currentPlayer.stop();
          players.clear();
          label.setText("");
          }catch(Exception e){
        }
    }
   
     @FXML
    private void Skip(ActionEvent event) {
        try{
        MediaPlayer currentPlayer = mediaVeiw.getMediaPlayer();
        MediaPlayer nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        mediaVeiw.setMediaPlayer(nextPlayer);
        currentPlayer.currentTimeProperty().removeListener(progressChangeListener);
        currentPlayer.stop();
        nextPlayer.play();
        back.setDisable(false); 
        mediaVeiw.getMediaPlayer().setVolume(slider.getValue()/100);  
        }catch(Exception e){
        }
  
    }
    @FXML
    private void Replay(ActionEvent event) {
        try{
        MediaPlayer curPlayer = mediaVeiw.getMediaPlayer();
       mediaVeiw.getMediaPlayer().setVolume(slider.getValue()/100);
     progress.setProgress(0);
         curPlayer.stop();
  curPlayer.seek(Duration.ZERO);
      curPlayer.play();
          }catch(Exception e){
        }
         }
    /** @play and pause function  */
     @FXML
    private void Stop(ActionEvent event) {
        try {
         mediaVeiw.getMediaPlayer().stop();
         flag=true;
            }catch(Exception e){
        }
       
    }
    /** @play and pause function  */
     @FXML
    private void Play(ActionEvent event) {
        try {
         if (flag==false) {
          mediaVeiw.getMediaPlayer().pause();
          flag=true;
        } 
        else {
          mediaVeiw.getMediaPlayer().play();
          flag=false;
        }
        }catch(Exception e){
        }
    }
    /** @change to previous track  */
     @FXML
    private void Back(ActionEvent event) {
           try
      {
         MediaPlayer curPlayer = mediaVeiw.getMediaPlayer();
        if(players.indexOf(curPlayer)==0){ 
             Replay(event);
        }
        else {
        MediaPlayer backPlayer = players.get((players.indexOf(curPlayer) - 1) % players.size());
        mediaVeiw.setMediaPlayer(backPlayer);
        curPlayer.currentTimeProperty().removeListener(progressChangeListener);
        curPlayer.stop();
        backPlayer.play();
         back.setDisable(false);
         mediaVeiw.getMediaPlayer().setVolume(slider.getValue()/100);  
        }
       }catch(Exception e)
      {
          
      }
    }
    
     @FXML
    private void handleButtonAction(ActionEvent event) throws IOException{ 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }   
    /** @key functions */
@FXML
    private void key(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
        MediaPlayer currentPlayer = mediaVeiw.getMediaPlayer();
        MediaPlayer nextPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
        mediaVeiw.setMediaPlayer(nextPlayer);
        currentPlayer.currentTimeProperty().removeListener(progressChangeListener);
        currentPlayer.stop();
        nextPlayer.play();
        }
        if (event.getCode().equals(KeyCode.SPACE)){
         mediaVeiw.getMediaPlayer().pause();    }
        if (event.getCode().equals(KeyCode.BACK_SPACE)) {
        MediaPlayer currentPlayer = mediaVeiw.getMediaPlayer();
        if(players.indexOf(currentPlayer)==0){ 
            MediaPlayer currentlyPlayer = mediaVeiw.getMediaPlayer();
            progress.setProgress(0);
            currentlyPlayer.stop();
            currentlyPlayer.seek(Duration.ZERO);
            currentlyPlayer.play();
        }
        else {
        MediaPlayer backPlayer = players.get((players.indexOf(currentPlayer) - 1) % players.size());
        mediaVeiw.setMediaPlayer(backPlayer);
        currentPlayer.currentTimeProperty().removeListener(progressChangeListener);
        currentPlayer.stop();
        backPlayer.play();
         back.setDisable(false);
        }
        }
    }
 /** @default home page  */
    @FXML
    private void selectfile(KeyEvent event) {
        if (event.getCode().equals(KeyCode.ENTER)) {
             label.setText(scroll.getSelectionModel().getSelectedItem());
              
        seleectfile(scroll.getSelectionModel().getSelectedItem());
        }
    }
/** @change directory  */
    @FXML
    private void selectedfile(MouseEvent event) {
         try
      {
         if (scroll.getSelectionModel().getSelectedItem()!=null)
        seleectfile(scroll.getSelectionModel().getSelectedItem());
      }catch(Exception e)
      {
           
      }
    }
    
    
    /** @return a MediaPlayer for the given source which will report any errors it encounters */
  private MediaPlayer createPlayer(String aMediaSrc) {
    final MediaPlayer player = new MediaPlayer(new Media(aMediaSrc));
    player.setOnError(new Runnable() {
      @Override public void run() {
         label.setText("Media error occurred: " + player.getError());
      }
    });
    return player;
  }
  /** @change lable1 name and setprogress on */
      private void setCurrentlyPlaying(MediaPlayer newPlayer) {
    progress.setProgress(0);
    
    progressChangeListener = new ChangeListener<Duration>() {
      @Override public void changed(ObservableValue<? extends Duration> observableValue, Duration oldValue, Duration newValue) {
     
          progress.setProgress(1.0 * newPlayer.getCurrentTime().toMillis() / newPlayer.getTotalDuration().toMillis());
      }
    };
    newPlayer.currentTimeProperty().addListener(progressChangeListener);

    String source = newPlayer.getMedia().getSource();
    source = source.substring(0, source.length() - ".mp3".length());
    source = source.substring(source.lastIndexOf("/") + 1).replaceAll("%20", " ");
    label.setTextFill(Color.web("#000000"));
    label.setText("Now Playing: " + source);
   
  }
 /** @change Icon and setvolume on */
    @FXML
    private void setvloumeon(MouseEvent event) {
        try{
         volumeIcon1.setVisible(false);
            volumeIcon.setVisible(true);
            mediaVeiw.getMediaPlayer().setVolume(1.0);
            slider.setValue(100);
            }catch(Exception e)
      {
                 }
    }
/** @change Icon and setvolume off */
    @FXML
    private void setvloumeoff(MouseEvent event) {
        try{
         volumeIcon1.setVisible(true);
            volumeIcon.setVisible(false);
            mediaVeiw.getMediaPlayer().setVolume(0.0);
            slider.setValue(0);
            }catch(Exception e)
      {
           
      }
    }
/** @change Icon and use mouse drag slider */
    @FXML
    private void changevolume(MouseEvent event) {
        if(slider.getValue()==0){
            volumeIcon1.setVisible(true);
            volumeIcon.setVisible(false);
        }
        else{
             volumeIcon1.setVisible(false);
            volumeIcon.setVisible(true);
        }
        
    }

  
  }
