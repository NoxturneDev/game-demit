package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    Clip clip;
    URL soundURL[] = new URL[100];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public Sound() {
//        GAME SOUNDS
        soundURL[0] = getClass().getResource("/sounds/backsound/backsound-ambience.wav");
        soundURL[1] = getClass().getResource("/sounds/backsound/backsound-menu.wav");
        soundURL[2] = getClass().getResource("/sounds/backsound/backsound-tutorial.wav");
        soundURL[3] = getClass().getResource("/sounds/SFX/menu/click.wav");
        soundURL[4] = getClass().getResource("/sounds//SFX/keris-stab.wav");
        soundURL[5] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[6] = getClass().getResource("/sounds/SFX/menu/click.wav");
        soundURL[7] = getClass().getResource("/sounds/SFX/keris-stab.wav");
        soundURL[8] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[9] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[10] = getClass().getResource("/sounds/backsound/backsound-ambience.wav");
        soundURL[11] = getClass().getResource("/sounds/backsound/backsound-menu.wav");
        soundURL[12] = getClass().getResource("/sounds/backsound/backsound-tutorial.wav");
        soundURL[13] = getClass().getResource("/sounds/SFX/menu/click.wav");
        soundURL[14] = getClass().getResource("/sounds/SFX/keris-stab.wav");
        soundURL[15] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[16] = getClass().getResource("/sounds/SFX/menu/click.wav");
        soundURL[17] = getClass().getResource("/sounds/SFX/keris-stab.wav");
        soundURL[18] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[19] = getClass().getResource("/sounds/SFX/keris-slash.wav");
        soundURL[20] = getClass().getResource("/sounds/SFX/keris-slash.wav");

//        CUTSCENE SOUNDS
//        prolog 1
        soundURL[21] = getClass().getResource("/sounds/cutscenes/prolog1-lanang/cs-panel1-lanang.wav");
        soundURL[22] = getClass().getResource("/sounds/cutscenes/prolog1-lanang/cs-panel2-lanang.wav");
        soundURL[23] = getClass().getResource("/sounds/cutscenes/prolog1-lanang/cs-panel3-lanang1.wav");
//        prolog 2
        soundURL[24] = getClass().getResource("/sounds/cutscenes/prolog2_pocong/cs-panel1-pocong.wav");
        soundURL[25] = getClass().getResource("/sounds/cutscenes/prolog2_pocong/cs-panel2-pocong.wav");
        soundURL[26] = getClass().getResource("/sounds/cutscenes/prolog2_pocong/cs-panel3-pocong.wav");
//        prolog 3
        soundURL[27] = getClass().getResource("/sounds/cutscenes/prolog3_flashback/cs-panel1-flashback.wav");
        soundURL[28] = getClass().getResource("/sounds/cutscenes/prolog3_flashback/cs-panel2-flashback.wav");
        soundURL[29] = getClass().getResource("/sounds/cutscenes/prolog3_flashback/cs-panel3-flashback.wav");
//        prolog 4
        soundURL[30] = getClass().getResource("/sounds/cutscenes/prolog4_keris/cs-panel1-keris.wav");
        soundURL[31] = getClass().getResource("/sounds/cutscenes/prolog4_keris/cs-panel2-keris.wav");
        soundURL[32] = getClass().getResource("/sounds/cutscenes/prolog4_keris/cs-panel3-keris.wav");
//        prolog 5
        soundURL[33] = getClass().getResource("/sounds/cutscenes/prolog5_raden-wijaya/cs-panel1-radenwijaya.wav");
        soundURL[34] = getClass().getResource("/sounds/cutscenes/prolog5_raden-wijaya/cs-panel2-radenwijaya.wav");
//        GAME SOUNDS

    }

    public void setFile(int i)           // Java Sound File Opening Format
    {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN); //pass value for clip // -80f to 6f // 6 is max. -80f = 0
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.start();
    }

    public void playSE() {

    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}
